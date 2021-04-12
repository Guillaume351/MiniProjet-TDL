/**
 * 
 */
package fr.n7.stl.block.ast.instruction.declaration;

import fr.n7.stl.block.ast.Block;
import fr.n7.stl.block.ast.SemanticsUndefinedException;
import fr.n7.stl.block.ast.expression.value.IntegerValue;
import fr.n7.stl.block.ast.expression.value.StringValue;
import fr.n7.stl.block.ast.instruction.Instruction;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.scope.SymbolTable;
import fr.n7.stl.block.ast.type.AtomicType;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

import java.util.Iterator;
import java.util.List;

/**
 * Abstract Syntax Tree node for a function declaration.
 * @author Marc Pantel
 */
public class FunctionDeclaration implements Instruction, Declaration {

	/**
	 * Name of the function
	 */
	protected String name;
	
	/**
	 * AST node for the returned type of the function
	 */
	protected Type type;
	
	/**
	 * List of AST nodes for the formal parameters of the function
	 */
	protected List<ParameterDeclaration> parameters;
	
	/**
	 * @return the parameters
	 */
	public List<ParameterDeclaration> getParameters() {
		return parameters;
	}

	/**
	 * AST node for the body of the function
	 */
	protected Block body;

	/**
	 * The symbol table for the parameters and local to the function
	 */
	protected SymbolTable localSymbolTableParameters;

	/**
	 * The symbol table for the variables and local to the function
	 */
	protected SymbolTable localSymbolTableVariables;

	/**
	 * The offset summing all the parameters offsets
	 */
	protected int totalOffsetParameters;

	/**
	 * Builds an AST node for a function declaration
	 * @param _name : Name of the function
	 * @param _type : AST node for the returned type of the function
	 * @param _parameters : List of AST nodes for the formal parameters of the function
	 * @param _body : AST node for the body of the function
	 */
	public FunctionDeclaration(String _name, Type _type, List<ParameterDeclaration> _parameters, Block _body) {
		this.name = _name;
		this.type = _type;
		this.parameters = _parameters;
		this.body = _body;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String _result = this.type + " " + this.name + "( ";
		Iterator<ParameterDeclaration> _iter = this.parameters.iterator();
		if (_iter.hasNext()) {
			_result += _iter.next();
			while (_iter.hasNext()) {
				_result += " ," + _iter.next();
			}
		}
		return _result + " )" + this.body;
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.Declaration#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}
	
	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.Declaration#getType()
	 */
	@Override
	public Type getType() {
		return this.type;
	}
	
	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.instruction.Instruction#collect(fr.n7.stl.block.ast.scope.Scope)
	 */
	@Override
	public boolean collect(HierarchicalScope<Declaration> _scope) {
		boolean ok = true;

		// Ajout de la fonction dans la table des symboles
		if (_scope.accepts(this)) {
			_scope.register(this);
		} else {
			Logger.error("FunctionDeclaration : nom de fonction déjà utilisé");
			ok = false;
		}

		// Création de la table des symboles des paramètres
		this.localSymbolTableParameters = new SymbolTable(_scope);

		// Ajout des paramètres dans la table des symboles des paramètres
		for (ParameterDeclaration par : this.parameters){
			if (this.localSymbolTableParameters.accepts(par)){
				this.localSymbolTableParameters.register(par);
			} else {
				Logger.error("FunctionDeclaration : nom de paramètre déjà utilisé");
				ok = false;
			}
		}

		// Ajout de la variable "return" dans la table des symboles des paramètres
		this.localSymbolTableParameters.register(new VariableDeclaration("return", this.getType(), new StringValue("")));

		// Ajout de la variable $parameterslength$ pour pop lors d'un return de la taille des parameters
		int parametersLength = 0;
		for(ParameterDeclaration d : this.getParameters()){
			parametersLength += d.getType().length();
		}
		this.localSymbolTableParameters.register(new VariableDeclaration("$parameterslength$", AtomicType.IntegerType, new IntegerValue(String.valueOf(parametersLength))));


		// Vérification des paramètres et des variables
		//ok = ok && this.body.resolve(this.localSymbolTableParameters);

		// Création de la table des symboles des variables
		this.localSymbolTableVariables = new SymbolTable(this.localSymbolTableParameters);

		return ok && this.body.collect(localSymbolTableVariables);

	}
	
	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.instruction.Instruction#resolve(fr.n7.stl.block.ast.scope.Scope)
	 */
	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {

		//TODO: vérifier que le type doit bien être resolve dans le scope global
		//return this.body.resolve(localSymbolTableParameters) && this.type.resolve(localSymbolTableParameters);

		return this.body.resolve(this.localSymbolTableVariables);
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.instruction.Instruction#checkType()
	 */
	@Override
	public boolean checkType() {
		return this.body.checkType();
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.instruction.Instruction#allocateMemory(fr.n7.stl.tam.ast.Register, int)
	 */
	@Override
	public int allocateMemory(Register _register, int _offset) {

		totalOffsetParameters = 0;

		for (ParameterDeclaration par : this.getParameters()) {
			totalOffsetParameters += par.getType().length();
		}

		int currentOffset = 0;

		for (ParameterDeclaration par : parameters) {
			par.offset = currentOffset - totalOffsetParameters;
			currentOffset += par.getType().length();
		}

		// 3 est une constante qui separe les param du body.
		body.allocateMemory(Register.LB, 3);

		return 0;

	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.instruction.Instruction#getCode(fr.n7.stl.tam.ast.TAMFactory)
	 */
	@Override
	public Fragment getCode(TAMFactory _factory) {
		Fragment fragment = _factory.createFragment();

		fragment.addPrefix(this.name);
		for(ParameterDeclaration par : this.parameters){
			fragment.add(_factory.createLoad(Register.LB, par.offset, par.getType().length()));
		}
		fragment.append(this.body.getCode(_factory));

		// Sécurité si absence de return
		fragment.add(_factory.createLoadL(0));
		fragment.add(_factory.createReturn(1, this.totalOffsetParameters));

		return fragment;
	}

}

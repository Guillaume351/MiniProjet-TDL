/**
 * 
 */
package fr.n7.stl.block.ast.instruction.declaration;

import fr.n7.stl.block.ast.expression.Expression;
import fr.n7.stl.block.ast.instruction.Instruction;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

/**
 * Implementation of the Abstract Syntax Tree node for a constant declaration instruction.
 * @author Marc Pantel
 *
 */
public class ConstantDeclaration implements Instruction, Declaration {

	/**
	 * Name of the constant
	 */
	protected String name;

	/**
	 * AST node for the type of the constant
	 */
	protected Type type;

	/**
	 * AST node for the expression that computes the value of the constant
	 */
	protected Expression value;

	/**
	 * Address register that contains the base address used to store the declared variable.
	 */
	protected Register register;

	/**
	 * Offset from the base address used to store the declared variable
	 * i.e. the size of the memory allocated to the previous declared variables
	 */
	protected int offset;

	/**
	 * Is it the first get code ?
	 */
	protected boolean isDeclarationGetCode = true;

	/**
	 * Builds an AST node for a constant declaration
	 *
	 * @param _name  : Name of the constant
	 * @param _type  : AST node for the type of the constant
	 * @param _value : AST node for the expression that computes the value of the constant
	 */
	public ConstantDeclaration(String _name, Type _type, Expression _value) {
		this.name = _name;
		this.type = _type;
		this.value = _value;
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
	
	/**
	 * Provide the value associated to a name in constant declaration.
	 * @return Value from the declaration.
	 */
	public Expression getValue() {
		return this.value;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "const " + this.type + " " + this.name + " = " + this.value + ";\n";
	}
	
	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.instruction.Instruction#collect(fr.n7.stl.block.ast.scope.Scope)
	 */
	@Override
	public boolean collect(HierarchicalScope<Declaration> _scope) {
		if(_scope.accepts(this)){
			_scope.register(this);
			return true;
		}else{
			Logger.error("ConstantDeclaration.java : Tentative de double ajout / reaffectation d'une const");
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.instruction.Instruction#resolve(fr.n7.stl.block.ast.scope.Scope)
	 */
	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		boolean ok1 = this.getType().resolve(_scope);
		boolean ok2 = this.value.resolve(_scope);
		boolean ok3 = _scope.contains(getName());

		/*if(!ok3) {
			throw new SemanticsUndefinedException("No OK3. Scope doesn't contain");

		} else {

		}*/
		return ok1 && ok2 && ok3;
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.Instruction#checkType()
	 */
	@Override
	public boolean checkType() {
		Type treal = this.value.getType();

		if (!treal.compatibleWith(this.type)){
			Logger.warning("ConstantDeclaration : types incompatibles");
			return false;
		} else {
			return true;
		}
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.Instruction#allocateMemory(fr.n7.stl.tam.ast.Register, int)
	 */
	@Override
	public int allocateMemory(Register _register, int _offset) {
		return 0; // On ne met pas dans la pile
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.Instruction#getCode(fr.n7.stl.tam.ast.TAMFactory)
	 */
	@Override
	public Fragment getCode(TAMFactory _factory) {
		Fragment fragment = _factory.createFragment();

		if(isDeclarationGetCode){
			isDeclarationGetCode = false;
		}else{
			fragment.append(this.getValue().getCode(_factory));
		}

		return fragment;
	}

}

/**
 * 
 */
package fr.n7.stl.block.ast.instruction;

import fr.n7.stl.block.ast.SemanticsUndefinedException;
import fr.n7.stl.block.ast.expression.Expression;
import fr.n7.stl.block.ast.instruction.declaration.VariableDeclaration;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.type.AtomicType;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

/**
 * Implementation of the Abstract Syntax Tree node for a return instruction.
 * @author Marc Pantel
 *
 */
public class Return implements Instruction {

	protected Expression value;

	protected Type returnType;

	protected int parametersSize;

	public Return(Expression _value) {
		this.value = _value;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "return " + this.value + ";\n";
	}
	
	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.instruction.Instruction#collect(fr.n7.stl.block.ast.scope.Scope)
	 */
	@Override
	public boolean collect(HierarchicalScope<Declaration> _scope) {
		if (_scope.contains("return")){
			this.returnType = (_scope.get("return")).getType();
		} else {
			Logger.error("Return: Il y a un soucis! La fonction n'a pas renseigné son type de retour");
		}
		if(_scope.contains("$parameterslength$")){
			// Comme $parameterslength$ ne contient pas d'entier, ça passe ! On a juste a convertir en string,
			// puis retirer tout ce qui ne correspond pas a la valeur...
			// Encore une fois, c'est pas tres propre, mais on a pas besoin de rajouter de getter comme ça...
			// Et on aime pas rajouter de getter.
			// On aurait pu aussi jouer avec l'héritage... Mais bon, tant que ça marche ...:))))
			this.parametersSize = Integer.parseInt(_scope.get("$parameterslength$").toString().replaceAll("[^0-9]",""));;
		}else{
			Logger.error("Return: Il y a un soucis! Je n'ai pas acces a la longueur des parametres :(");
		}
		return this.value.collect(_scope);
	}
	
	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.instruction.Instruction#resolve(fr.n7.stl.block.ast.scope.Scope)
	 */
	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		return this.value.resolve(_scope);
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.Instruction#checkType()
	 */
	@Override
	public boolean checkType() {
		Type te = this.value.getType();

		return te.compatibleWith(this.returnType);

	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.Instruction#allocateMemory(fr.n7.stl.tam.ast.Register, int)
	 */
	@Override
	public int allocateMemory(Register _register, int _offset) {
		return 0;
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.Instruction#getCode(fr.n7.stl.tam.ast.TAMFactory)
	 */
	@Override
	public Fragment getCode(TAMFactory _factory) {
		Fragment fragment = _factory.createFragment();

		int returnSize = this.value.getType().length();

		fragment.add(_factory.createReturn(this.parametersSize, returnSize));

		return fragment;
	}

}

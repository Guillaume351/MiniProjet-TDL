/**
 * 
 */
package fr.n7.stl.block.ast.expression.allocation;

import fr.n7.stl.block.ast.expression.Expression;
import fr.n7.stl.block.ast.expression.value.IntegerValue;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.type.ArrayType;
import fr.n7.stl.block.ast.type.AtomicType;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Library;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

/**
 * @author Marc Pantel
 *
 */
public class ArrayAllocation implements Expression {

	protected Type element;
	protected Expression size;

	public ArrayAllocation(Type _element, Expression _size) {
		this.element = _element;
		this.size = _size;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "new " + this.element + "[ " + this.size + " ]"; 
	}
	
	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.expression.Expression#collect(fr.n7.stl.block.ast.scope.Scope)
	 */
	@Override
	public boolean collect(HierarchicalScope<Declaration> _scope) {
		return this.size.collect(_scope);
	}
	
	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.expression.Expression#resolve(fr.n7.stl.block.ast.scope.Scope)
	 */
	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		boolean ok = this.size.resolve(_scope);

		ok = ok && this.element.resolve(_scope);
		return ok;
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.Expression#getType()
	 */
	@Override
	public Type getType() {
		// Verification du type de la taille
		if(this.size.getType().compatibleWith(AtomicType.IntegerType)){
			return new ArrayType(this.element);
		}else{
			Logger.error("ArrayAllocation : La taille doit être un entier! " +
					"Il est fourni une taille de type : " + this.size.getType());
		}

		return AtomicType.ErrorType;
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.Expression#getCode(fr.n7.stl.tam.ast.TAMFactory)
	 */
	@Override
	public Fragment getCode(TAMFactory _factory) {

		Fragment fragment = _factory.createFragment();

		int elementTypeSize = this.element.length();

		if (!(this.size instanceof IntegerValue)) {
			Logger.error("ArrayAllocation: Ça ne va pas! Il faut que size soit une IntegerValue, " +
					"on ne gere pas autre chose en taille d'array!");
		}
		// C'est pas très LEGIT, mais on a pas envie de rajouter un getter :( Et puis tant que ça marche...:)
		int arraySize = Integer.parseInt(this.size.toString());

		fragment.add(_factory.createLoadL(arraySize));

		fragment.add(_factory.createLoadL(elementTypeSize));

		fragment.add(Library.IMul);

		fragment.add(Library.MAlloc);

		return fragment;
	}

}

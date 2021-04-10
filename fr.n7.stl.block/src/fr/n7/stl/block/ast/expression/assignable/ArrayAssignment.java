/**
 * 
 */
package fr.n7.stl.block.ast.expression.assignable;

import fr.n7.stl.block.ast.SemanticsUndefinedException;
import fr.n7.stl.block.ast.expression.AbstractArray;
import fr.n7.stl.block.ast.expression.Expression;
import fr.n7.stl.block.ast.expression.value.IntegerValue;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Library;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

/**
 * Abstract Syntax Tree node for an expression whose computation assigns a cell in an array.
 * @author Marc Pantel
 */
public class ArrayAssignment extends AbstractArray implements AssignableExpression {

	/**
	 * Construction for the implementation of an array element assignment expression Abstract Syntax Tree node.
	 * @param _array Abstract Syntax Tree for the array part in an array element assignment expression.
	 * @param _index Abstract Syntax Tree for the index part in an array element assignment expression.
	 */
	public ArrayAssignment(AssignableExpression _array, Expression _index) {
		super(_array, _index);
	}
	
	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.impl.ArrayAccessImpl#getCode(fr.n7.stl.tam.ast.TAMFactory)
	 */
	@Override
	public Fragment getCode(TAMFactory _factory) {
		Fragment fragment = _factory.createFragment();

		/*
		int elementTypeSize = this.array.length();

		if (!(this.size instanceof IntegerValue)) {
			Logger.error("ArrayAllocation: Ça ne va pas! Il faut que size soit une IntegerValue, " +
					"on ne gere pas autre chose en taille d'array!");
		}

		// C'est pas très LEGIT, mais on a pas envie de rajouter un getter :( Et puis tant que ça marche...:)
		int arrayIndex = Integer.parseInt(this.index.toString());

		fragment.add(_factory.createLoadL(arrayIndex));

		fragment.add(_factory.createLoadL(elementTypeSize));

		fragment.add(Library.IMul);

		fragment.add(Library.IAdd);

		 */

		return fragment;
		}

	
}

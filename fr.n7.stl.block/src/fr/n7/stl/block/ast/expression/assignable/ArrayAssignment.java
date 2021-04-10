/**
 * 
 */
package fr.n7.stl.block.ast.expression.assignable;

import fr.n7.stl.block.ast.expression.AbstractArray;
import fr.n7.stl.block.ast.expression.Expression;
import fr.n7.stl.block.ast.expression.value.IntegerValue;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Library;
import fr.n7.stl.tam.ast.Register;
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

		int arrayOffset = ((VariableAssignment) this.array).declaration.getOffset();

		Register arrayRegister = ((VariableAssignment) this.array).declaration.getRegister();

		int typeSize = this.array.getType().length();

		fragment.add(_factory.createLoad(arrayRegister, arrayOffset, typeSize));


		if (!(this.index instanceof IntegerValue)) {
			Logger.error("ArrayAllocation: Ça ne va pas! Il faut que index soit une IntegerValue, " +
					"on ne gere pas autre chose en index d'array!");
		}

		int indexValue = Integer.parseInt(this.index.toString());

		fragment.add(_factory.createLoadL(indexValue));
		fragment.add(_factory.createLoadL(typeSize));

		fragment.add(Library.IMul);

		fragment.add(Library.IAdd);

		fragment.add(_factory.createStoreI(typeSize));


		return fragment;
	}

	
}

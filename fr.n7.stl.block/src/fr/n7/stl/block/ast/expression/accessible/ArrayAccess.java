/**
 * 
 */
package fr.n7.stl.block.ast.expression.accessible;

import fr.n7.stl.block.ast.expression.AbstractArray;
import fr.n7.stl.block.ast.expression.Expression;
import fr.n7.stl.block.ast.expression.value.IntegerValue;
import fr.n7.stl.block.ast.instruction.declaration.VariableDeclaration;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Library;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

/**
 * Implementation of the Abstract Syntax Tree node for accessing an array element.
 * @author Marc Pantel
 *
 */
public class ArrayAccess extends AbstractArray implements AccessibleExpression {

	/**
	 * Construction for the implementation of an array element access expression Abstract Syntax Tree node.
	 * @param _array Abstract Syntax Tree for the array part in an array element access expression.
	 * @param _index Abstract Syntax Tree for the index part in an array element access expression.
	 */
	public ArrayAccess(Expression _array, Expression _index) {
		super(_array,_index);
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.Expression#getCode(fr.n7.stl.tam.ast.TAMFactory)
	 */
	@Override
	public Fragment getCode(TAMFactory _factory) {
		Fragment fragment = _factory.createFragment();

		VariableDeclaration laDeclaration = ((IdentifierAccess) this.array).getDeclaration();

		fragment.add(_factory.createLoad(laDeclaration.getRegister(), laDeclaration.getOffset(), 1));
		if (!(this.index instanceof IntegerValue)) {
			Logger.error("ArrayAllocation: Ça ne va pas! Il faut que index soit une IntegerValue, " +
					"on ne gere pas autre chose en index d'array!");
		}

		int indexValue = Integer.parseInt(this.index.toString());
		int typeSize = this.array.getType().length();

		// On multiplie la taille du type par le numero d'index
		fragment.add(_factory.createLoadL(indexValue));
		fragment.add(_factory.createLoadL(typeSize));

		fragment.add(Library.IMul);

		fragment.add(Library.IAdd);

		fragment.add(_factory.createLoadI(typeSize));

		return fragment;
	}

}

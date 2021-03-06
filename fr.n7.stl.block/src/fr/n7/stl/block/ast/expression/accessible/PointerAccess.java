/**
 *
 */
package fr.n7.stl.block.ast.expression.accessible;

import fr.n7.stl.block.ast.expression.AbstractPointer;
import fr.n7.stl.block.ast.expression.Expression;
import fr.n7.stl.block.ast.instruction.declaration.VariableDeclaration;
import fr.n7.stl.block.ast.type.PointerType;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;

/**
 * Implementation of the Abstract Syntax Tree node for a pointer access
 * expression.
 *
 * @author Marc Pantel
 *
 */
public class PointerAccess extends AbstractPointer {

	/**
	 * Construction for the implementation of a pointer content access expression
	 * Abstract Syntax Tree node.
	 *
	 * @param _pointer Abstract Syntax Tree for the pointer expression in a pointer
	 *                 content access expression.
	 */
	public PointerAccess(Expression _pointer) {
		super(_pointer);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see fr.n7.stl.block.ast.Expression#getCode(fr.n7.stl.tam.ast.TAMFactory)
	 */
	@Override
	public Fragment getCode(TAMFactory _factory) {
		Fragment fragment = _factory.createFragment();

		VariableDeclaration laDeclaration = ((IdentifierAccess) this.pointer).getDeclaration();

		fragment.add(
				_factory.createLoad(laDeclaration.getRegister(), laDeclaration.getOffset(), this.pointer.getType().length()));

		fragment.add(_factory.createLoadI(this.pointer.getType().length()));

		return fragment;
	}

	@Override
	public Type getType() {
		return ((PointerType) this.pointer.getType()).getPointedType();
	}
}

/**
 *
 */
package fr.n7.stl.block.ast.instruction;

import fr.n7.stl.block.ast.expression.Expression;
import fr.n7.stl.block.ast.instruction.declaration.minijava.MethodDeclarationElement;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.scope.OwnedHierarchicalScope;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

/**
 * Implementation of the Abstract Syntax Tree node for a return instruction.
 *
 * @author Marc Pantel
 *
 */
public class Return implements Instruction {

	protected Expression value;

	protected Type returnType;

	protected int parametersSize;

	private Declaration owner;

	public Return(Expression _value) {
		this.value = _value;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// return "return " + this.value + ";\n";
		return "";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * fr.n7.stl.block.ast.instruction.Instruction#collect(fr.n7.stl.block.ast.scope
	 * .Scope)
	 */
	@Override
	public boolean collect(HierarchicalScope<Declaration> _scope) {
		if (!(_scope instanceof OwnedHierarchicalScope)) {
			Logger.error("Unexpected return statement.");
			return false;
		}
		owner = ((OwnedHierarchicalScope<?>) _scope).getOwner();
		return value.collect(_scope);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * fr.n7.stl.block.ast.instruction.Instruction#resolve(fr.n7.stl.block.ast.scope
	 * .Scope)
	 */
	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		if (!(owner instanceof MethodDeclarationElement)) {
			Logger.error("Cannot place return here.");
			return false;
		}
		returnType = ((MethodDeclarationElement) owner).getType();
		return this.value.resolve(_scope);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see fr.n7.stl.block.ast.Instruction#checkType()
	 */
	@Override
	public boolean checkType() {
		Type te = this.value.getType();

		if (!te.compatibleWith(this.returnType)) {
			Logger.error("Cannot return type " + te);
			return false;
		}

		return true;

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * fr.n7.stl.block.ast.Instruction#allocateMemory(fr.n7.stl.tam.ast.Register,
	 * int)
	 */
	@Override
	public int allocateMemory(Register _register, int _offset) {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see fr.n7.stl.block.ast.Instruction#getCode(fr.n7.stl.tam.ast.TAMFactory)
	 */
	@Override
	public Fragment getCode(TAMFactory _factory) {
		Fragment fragment = _factory.createFragment();

		fragment.append(this.value.getCode(_factory));

		// fragment.add(_factory.createLoadI(this.value.getType().length()));

		int returnSize = this.value.getType().length();

		fragment.add(_factory.createReturn(this.parametersSize, returnSize));

		return fragment;
	}

}

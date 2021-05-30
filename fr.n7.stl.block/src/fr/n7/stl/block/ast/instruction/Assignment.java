/**
 *
 */
package fr.n7.stl.block.ast.instruction;

import fr.n7.stl.block.ast.expression.Expression;
import fr.n7.stl.block.ast.expression.assignable.AssignableExpression;
import fr.n7.stl.block.ast.instruction.declaration.ConstantDeclaration;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.type.AtomicType;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

/**
 * Implementation of the Abstract Syntax Tree node for an array type.
 *
 * @author Marc Pantel
 *
 */
public class Assignment implements Instruction, Expression {

	protected Expression value;
	protected AssignableExpression assignable;

	/**
	 * Create an assignment instruction implementation from the assignable
	 * expression and the assigned value.
	 *
	 * @param _assignable Expression that can be assigned a value.
	 * @param _value      Value assigned to the expression.
	 */
	public Assignment(AssignableExpression _assignable, Expression _value) {
		this.assignable = _assignable;
		this.value = _value;
		/*
		 * This attribute will be assigned to the appropriate value by the resolve
		 * action
		 */
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.assignable + " = " + this.value.toString() + ";\n";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * fr.n7.stl.block.ast.instruction.Instruction#collect(fr.n7.stl.block.ast.scope
	 * .HierarchicalScope)
	 */
	@Override
	public boolean collect(HierarchicalScope<Declaration> _scope) {
		boolean ok = this.value.collect(_scope);
		if (!ok) {
			Logger.error("Righ-hand side of the assignement cannot be collected");
		}
		ok = ok && this.assignable.collect(_scope);
		if (!ok) {
			Logger.error("Left-hand side of the assignement cannot be collected");
		}
		return ok;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * fr.n7.stl.block.ast.instruction.Instruction#resolve(fr.n7.stl.block.ast.scope
	 * .HierarchicalScope)
	 */
	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		Declaration info = _scope.get(this.assignable.toString().trim());
		boolean ok1 = true;
		boolean ok2 = this.value.resolve(_scope);
		boolean ok3 = this.assignable.resolve(_scope);

		if (info != null) {
			if (info instanceof ConstantDeclaration) {
				ok1 = false;
				Logger.error("Assignment impossible : l'assignable est constant");
			}
		}

		return ok1 && ok2 && ok3;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see fr.n7.stl.block.ast.expression.Expression#getType()
	 */
	@Override
	public Type getType() {
		Type texpr = this.assignable.getType();
		Type tvalue = this.value.getType();

		if (!(texpr.compatibleWith(tvalue))) {
			Logger.error("Assignment : types non compatibles");
			return AtomicType.ErrorType;
		} else {
			return texpr;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see fr.n7.stl.block.ast.Instruction#checkType()
	 */
	@Override
	public boolean checkType() {
		Type texpr = this.assignable.getType();
		Type tvalue = this.value.getType();

		if (!(texpr.compatibleWith(tvalue))) {
			Logger.error("Assignment : types non compatibles entre " + this.assignable + " et " + this.value);
			return false;
		} else {
			return true;
		}
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

		fragment.append(this.assignable.getCode(_factory));

		return fragment;
	}

}

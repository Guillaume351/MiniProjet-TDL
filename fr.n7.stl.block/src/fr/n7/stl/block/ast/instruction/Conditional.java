/**
 * 
 */
package fr.n7.stl.block.ast.instruction;

import fr.n7.stl.block.ast.Block;
import fr.n7.stl.block.ast.expression.Expression;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.type.AtomicType;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

/**
 * Implementation of the Abstract Syntax Tree node for a conditional instruction.
 * @author Marc Pantel
 *
 */
public class Conditional implements Instruction {

	protected Expression condition;
	protected Block thenBranch;
	protected Block elseBranch;

	public Conditional(Expression _condition, Block _then, Block _else) {
		this.condition = _condition;
		this.thenBranch = _then;
		this.elseBranch = _else;
	}

	public Conditional(Expression _condition, Block _then) {
		this.condition = _condition;
		this.thenBranch = _then;
		this.elseBranch = null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "if (" + this.condition + " )" + this.thenBranch + ((this.elseBranch != null)?(" else " + this.elseBranch):"");
	}
	
	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.instruction.Instruction#collect(fr.n7.stl.block.ast.scope.Scope)
	 */
	@Override
	public boolean collect(HierarchicalScope<Declaration> _scope) {
		boolean b1 = condition.collect(_scope);
		boolean b2 = thenBranch.collect(_scope);
		boolean b3 = elseBranch == null || elseBranch.collect(_scope);

		return b1 && b2 && b3;
	}
	
	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.instruction.Instruction#resolve(fr.n7.stl.block.ast.scope.Scope)
	 */
	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		boolean b1 = this.condition.resolve(_scope);
		boolean b2 = this.thenBranch.resolve(_scope);
		boolean b3 = elseBranch == null || this.elseBranch.resolve(_scope);

		return b1 && b2 && b3;
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.Instruction#checkType()
	 */
	@Override
	public boolean checkType() {

		Type tcond = this.condition.getType();

		if (tcond.compatibleWith(AtomicType.BooleanType)){
			if (this.elseBranch != null) {
				return this.thenBranch.checkType() && this.elseBranch.checkType();
			} else {
				return this.thenBranch.checkType();
			}
		} else {
			Logger.warning("Conditional : type cond pas booleen");
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.Instruction#allocateMemory(fr.n7.stl.tam.ast.Register, int)
	 */
	@Override
	public int allocateMemory(Register _register, int _offset) {
		this.thenBranch.allocateMemory(_register, _offset);
		if (this.elseBranch != null) {
			this.elseBranch.allocateMemory(_register, _offset);
		}
		return 0;
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.Instruction#getCode(fr.n7.stl.tam.ast.TAMFactory)
	 */
	@Override
	public Fragment getCode(TAMFactory _factory) {
		String id = String.valueOf(_factory.createLabelNumber());

		Fragment fragment = _factory.createFragment();
		fragment.append(this.condition.getCode(_factory));
		if (this.elseBranch != null) {
			fragment.add(_factory.createJumpIf("else_tag_" + id, 0));
			fragment.append(this.thenBranch.getCode(_factory));
			fragment.add(_factory.createJump("end_if_" + id));
			fragment.addSuffix("else_tag_" + id);
			fragment.append(this.elseBranch.getCode(_factory));
		} else {
			fragment.add(_factory.createJumpIf("end_if_" + id, 0));
			fragment.append(this.thenBranch.getCode(_factory));
		}
		fragment.addSuffix("end_if_" +id);

		return fragment;
	}

}

/**
 * 
 */
package fr.n7.stl.block.ast.expression;

import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.type.AtomicType;
import fr.n7.stl.block.ast.type.CoupleType;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

/**
 * Implementation of the Abstract Syntax Tree node  for an expression extracting the second component in a couple.
 * @author Marc Pantel
 *
 */
public class Second implements Expression {

	/**
	 * AST node for the expression whose value must whose second element is extracted by the expression.
	 */
	private Expression target;
	
	/**
	 * Builds an Abstract Syntax Tree node for an expression extracting the second component of a couple.
	 * @param _target : AST node for the expression whose value must whose second element is extracted by the expression.
	 */
	public Second(Expression _target) {
		this.target = _target;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "(snd" + this.target + ")";
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.Expression#getType()
	 */
	@Override
	public Type getType() {

		if(this.target.getType() instanceof CoupleType){
			return ((CoupleType) this.target.getType()).getSecond();
		} else {
			Logger.error("Second : le target n'est pas un couple");
		}
		return AtomicType.ErrorType;
	}
	
	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.expression.Expression#collect(fr.n7.stl.block.ast.scope.Scope)
	 */
	@Override
	public boolean collect(HierarchicalScope<Declaration> _scope) {
		return target.collect(_scope);

	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.expression.Expression#resolve(fr.n7.stl.block.ast.scope.Scope)
	 */
	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		return target.resolve(_scope);
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.Expression#getCode(fr.n7.stl.tam.ast.TAMFactory)
	 */
	@Override
	public Fragment getCode(TAMFactory _factory) {
		Fragment fragment = _factory.createFragment();

		fragment.append(this.target.getCode(_factory));
		//fragment.add(_factory.createLoadI(this.target.getType().length()));
		int firstLen = new First(this.target).getType().length();
		int sndLen = this.getType().length();

		// On pop le first (de taille firstLen) a partir de sndLen
		fragment.add(_factory.createPop(sndLen, firstLen));


		return fragment;
	}

}

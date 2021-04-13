/**
 * 
 */
package fr.n7.stl.block.ast.expression;

import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.type.AtomicType;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

/**
 * Abstract Syntax Tree node for a conditional expression.
 * @author Marc Pantel
 *
 */
public class ConditionalExpression implements Expression {

	/**
	 * AST node for the expression whose value is the condition for the conditional expression.
	 */
	protected Expression condition;
	
	/**
	 * AST node for the expression whose value is the then parameter for the conditional expression.
	 */
	protected Expression thenExpression;
	
	/**
	 * AST node for the expression whose value is the else parameter for the conditional expression.
	 */
	protected Expression elseExpression;
	
	/**
	 * Builds a binary expression Abstract Syntax Tree node from the left and right sub-expressions
	 * and the binary operation.
	 */
	public ConditionalExpression(Expression _condition, Expression _then, Expression _else) {
		this.condition = _condition;
		this.thenExpression = _then;
		this.elseExpression = _else;
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.expression.Expression#collect(fr.n7.stl.block.ast.scope.Scope)
	 */
	@Override
	public boolean collect(HierarchicalScope<Declaration> _scope) {
		boolean b1 = this.condition.collect(_scope);
		boolean b2 = this.thenExpression.collect(_scope);
		boolean b3 = this.elseExpression.collect(_scope);

		return b1 && b2 && b3;
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.expression.Expression#resolve(fr.n7.stl.block.ast.scope.Scope)
	 */
	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		boolean b1 = this.condition.resolve(_scope);
		boolean b2 = this.thenExpression.resolve(_scope);
		boolean b3 = this.elseExpression.resolve(_scope);

		return b1 && b2 && b3;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "(" + this.condition + " ? " + this.thenExpression + " : " + this.elseExpression + ")";
	}
	
	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.Expression#getType()
	 */
	@Override
	public Type getType() {

		if (this.condition.getType().compatibleWith(AtomicType.BooleanType)){
			if(this.thenExpression.getType().compatibleWith(this.elseExpression.getType())){
				// Si les types then et else sont comptatibles, c'est OK, on merge, sinon, erreur
				return this.thenExpression.getType().merge(this.elseExpression.getType());
			} else {
				Logger.warning("ConditionalExpression : Les types de then et else sont incompatibles!");
			}

		}else{
			// Erreur, la condition aurait du être boolean. Pas de checktype ?
			Logger.warning("ConditionalExpression : La condition aurait du être Boolean ! Mais elle est de type :" + this.condition.getType());

		}
		return AtomicType.ErrorType;
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.Expression#getCode(fr.n7.stl.tam.ast.TAMFactory)
	 */
	@Override
	public Fragment getCode(TAMFactory _factory) {
		// On utilise un identifiant pour avoir des tag uniques
		int idCond = _factory.createLabelNumber();

		Fragment fragment = _factory.createFragment();

		fragment.append(this.condition.getCode(_factory));

		// On met le le code pour aller directement au else
		fragment.add(_factory.createJumpIf("else_tag_" + idCond, 0));

		// On met le code du then
		fragment.append(this.thenExpression.getCode(_factory));

		// Si on a lu le code du then alors on saute a la fin du if
		fragment.add(_factory.createJump("endif_tag_" + idCond));

		// On met le tag de debut de else
		fragment.addSuffix("else_tag_" + idCond);

		// On met le code du else
		fragment.append(this.elseExpression.getCode(_factory));


		// On met l'etiquette pour aller directement a la fin
		fragment.addSuffix("endif_tag_" + idCond);


		return fragment;
	}

}

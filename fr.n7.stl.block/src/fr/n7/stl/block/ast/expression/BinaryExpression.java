/**
 * 
 */
package fr.n7.stl.block.ast.expression;

import fr.n7.stl.block.ast.expression.accessible.AccessibleExpression;
import fr.n7.stl.block.ast.expression.accessible.IdentifierAccess;
import fr.n7.stl.block.ast.instruction.declaration.ConstantDeclaration;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.type.AtomicType;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

/**
 * Abstract Syntax Tree node for a binary expression.
 * @author Marc Pantel
 *
 */
/**
 * @author Marc Pantel
 *
 */
public class BinaryExpression implements Expression {

	/**
	 * AST node for the expression whose value is the left parameter for the binary expression.
	 */
	protected Expression left;

	/**
	 * AST node for the expression whose value is the left parameter for the binary expression.
	 */
	protected Expression right;

	/**
	 * Binary operator computed by the Binary Expression.
	 */
	protected BinaryOperator operator;

	protected Declaration leftDeclaration;
	protected Declaration rightDeclaration;

	/**
	 * Builds a binary expression Abstract Syntax Tree node from the left and right sub-expressions
	 * and the binary operation.
	 *
	 * @param _left     : Expression for the left parameter.
	 * @param _operator : Binary Operator.
	 * @param _right    : Expression for the right parameter.
	 */
	public BinaryExpression(Expression _left, BinaryOperator _operator, Expression _right) {
		this.left = _left;
		this.right = _right;
		this.operator = _operator;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "(" + this.left + " " + this.operator + " " + this.right + ")";
	}
	
	@Override
	public boolean collect(HierarchicalScope<Declaration> _scope) {
		boolean _left = this.left.collect(_scope);
		boolean _right = this.right.collect(_scope);

		if (this.left instanceof IdentifierAccess) {
			if ((_scope).knows(((IdentifierAccess) this.left).name)) {
				this.leftDeclaration = _scope.get(((IdentifierAccess) this.left).name);
			} else {
				Logger.error("BinaryExpression:  Impossible de recuperer la declaration de left!");
			}

		} else {
			Logger.warning("BinaryExpression: Left n'est pas un IdentifierAccess. Ouf. C'est : " + this.left);
		}


		if (this.right instanceof IdentifierAccess) {
			if ((_scope).knows(((IdentifierAccess) this.right).name)) {
				this.rightDeclaration = _scope.get(((IdentifierAccess) this.right).name);
			} else {
				Logger.error("BinaryExpression:  Impossible de recuperer la declaration de right!");
			}

		} else {
			Logger.warning("BinaryExpression: Right n'est pas un IdentifierAccess. Ouf.");
		}


		return _left && _right;
	}
	
	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.expression.Expression#resolve(fr.n7.stl.block.ast.scope.Scope)
	 */
	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		boolean _left = this.left.resolve(_scope);
		boolean _right = this.right.resolve(_scope);
		return _left && _right;
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.Expression#getType()
	 */
	@Override
	public Type getType() {
		Type _left = this.left.getType();
		Type _right = this.right.getType();
		Type resultType = _left.merge(_right);
		if (resultType.equals(AtomicType.ErrorType)) {
			Logger.warning("Type error in binary expression : Merged parameters " + _left + " " + _right);
		}
		switch (this.operator) {
			case Add: {
				if (resultType.compatibleWith(AtomicType.FloatingType) 
						|| resultType.compatibleWith(AtomicType.StringType))  {
					return resultType;
				} else {
					Logger.warning("Type error in binary expression : " + this.operator + " parameter " + resultType);
					return AtomicType.ErrorType;
				}
			}
			case Substract:
			case Multiply:
			case Divide: {
				if (resultType.compatibleWith(AtomicType.FloatingType)) {
					return resultType;
				} else {
					Logger.warning("Type error in binary expression : " + this.operator + " parameter " + resultType);
					return AtomicType.ErrorType;
				}
			}
			case Modulo: {
				if (resultType.compatibleWith(AtomicType.IntegerType)) {
					return resultType;
				} else {
					Logger.warning("Type error in binary expression : " + this.operator + " parameter " + resultType);
					return AtomicType.ErrorType;
				}
			}
			case Lesser:
			case Greater:
			case LesserOrEqual:
			case GreaterOrEqual: {
				if (resultType.compatibleWith(AtomicType.FloatingType)) {
					return AtomicType.BooleanType;
				} else {
					Logger.warning("Type error in binary expression : " + this.operator + " parameter " + resultType);
					return AtomicType.ErrorType;
				}				
			}
			case Equals:
			case Different: {
				if (resultType.equals(AtomicType.ErrorType)) {
					return resultType;
				} else {
					return AtomicType.BooleanType;
				}
			}
			default : return AtomicType.ErrorType;
		}
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.Expression#getCode(fr.n7.stl.tam.ast.TAMFactory)
	 */
	@Override
	public Fragment getCode(TAMFactory _factory) {
		Fragment _result = this.left.getCode(_factory);
		//_result.addComment(this.toString());

		// Si c'est une constante, on ne loadI pas
		if (!(this.left instanceof IdentifierAccess) || !(this.leftDeclaration instanceof ConstantDeclaration)) {
			if (this.left instanceof AccessibleExpression) {

				//_result.add(_factory.createLoadI(this.left.getType().length()));
			}
		}

		_result.append(this.right.getCode(_factory));

		// Si c'est une constante, on ne loadI pas
		if (!(this.right instanceof IdentifierAccess) || !(this.rightDeclaration instanceof ConstantDeclaration)) {
			if (this.right instanceof AccessibleExpression) {

				//_result.add(_factory.createLoadI(this.right.getType().length()));
			}
		}

		_result.add(TAMFactory.createBinaryOperator(this.operator));
		return _result;
	}

}

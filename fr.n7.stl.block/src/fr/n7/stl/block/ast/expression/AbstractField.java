package fr.n7.stl.block.ast.expression;

import fr.n7.stl.block.ast.instruction.declaration.minijava.AttributeDeclarationElement;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.type.ClassType;
import fr.n7.stl.block.ast.type.NamedType;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.util.Logger;

/**
 * Common elements between left (Assignable) and right (Expression) end sides of
 * assignments. These elements share attributes, toString and getType methods.
 *
 * @author Marc Pantel
 *
 */
public abstract class AbstractField implements Expression {

	protected Expression record;
	protected String name;
	protected AttributeDeclarationElement field;

	protected Declaration recordDeclaration;

	/**
	 * Construction for the implementation of a record field access expression
	 * Abstract Syntax Tree node.
	 *
	 * @param _record Abstract Syntax Tree for the record part in a record field
	 *                access expression.
	 * @param _name   Name of the field in the record field access expression.
	 */
	public AbstractField(Expression _record, String _name) {
		this.record = _record;
		this.name = _name;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.record + "." + this.name;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * fr.n7.stl.block.ast.expression.Expression#collect(fr.n7.stl.block.ast.scope.
	 * HierarchicalScope)
	 */
	@Override
	public boolean collect(HierarchicalScope<Declaration> _scope) {
		return this.record.collect(_scope);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * fr.n7.stl.block.ast.expression.Expression#resolve(fr.n7.stl.block.ast.scope.
	 * HierarchicalScope)
	 */
	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		if (!this.record.resolve(_scope)) {
			return false;
		}

		Type type = this.record.getType();
		// On descend le type
		if (type instanceof NamedType) {
			type = ((NamedType) type).getType();
		}

		if (!(type instanceof ClassType)) {
			Logger.error("AbstractField : Erreur de type sur le record. C'est de type " + this.record.getType());
			return false;
		}

		if (((ClassType) type).contains(this.name)) {
			this.field = ((ClassType) type).getProperty(this.name);
		} else {
			Logger.error("AbstractField : La classe ne contient pas le field de nom : " + this.name);
		}

		return true;
	}

	/**
	 * Synthesized Semantics attribute to compute the type of an expression.
	 *
	 * @return Synthesized Type of the expression.
	 */
	public Type getType() {
		return this.field.getType();
	}

}

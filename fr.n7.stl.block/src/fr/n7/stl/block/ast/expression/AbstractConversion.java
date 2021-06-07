/**
 *
 */
package fr.n7.stl.block.ast.expression;

import fr.n7.stl.block.ast.SemanticsUndefinedException;
import fr.n7.stl.block.ast.instruction.declaration.TypeDeclaration;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.type.NamedType;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

/**
 * Common elements between left (Assignable) and right (Expression) end sides of
 * assignments. These elements share attributes, toString and getType methods.
 *
 * @author Marc Pantel
 *
 */
public abstract class AbstractConversion<TargetType> implements Expression {

	protected TargetType target;
	protected Type type;
	protected String name;

	public AbstractConversion(TargetType _target, String _type) {
		this.target = _target;
		this.name = _type;
		this.type = null;
	}

	public AbstractConversion(TargetType _target, Type _type) {
		this.target = _target;
		this.name = null;
		this.type = _type;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (this.type == null) {
			return "(" + this.name + ") " + this.target;
		} else {
			return "(" + this.type + ") " + this.target;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see fr.n7.stl.block.ast.Expression#getType()
	 */
	@Override
	public Type getType() {
		throw new SemanticsUndefinedException("Semantics getType undefined in TypeConversion.");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * fr.n7.stl.block.ast.expression.Expression#collect(fr.n7.stl.block.ast.scope.
	 * Scope)
	 */
	@Override
	public boolean collect(HierarchicalScope<Declaration> _scope) {
		if (target instanceof Expression) {
			return ((Expression) this.target).collect(_scope);
		} else {
			Logger.error("AbstractConversion : la target n'est pas une expression !? C'est ; " + this.target);
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * fr.n7.stl.block.ast.expression.Expression#resolve(fr.n7.stl.block.ast.scope.
	 * Scope)
	 */
	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		// La target est necessairement une expression car sinon le collect n'aurait pas
		// fonctionne
		boolean ok = ((Expression) this.target).resolve(_scope);

		// On doit vertifier que la target est un sous type de type ou de NamedType

		Type typeConversion = this.type;

		if (typeConversion == null) {
			// On recupere le type si il n'est pas defini
			Declaration namedTypeDeclaration = _scope.get(this.name);
			if (namedTypeDeclaration instanceof TypeDeclaration) {
				typeConversion = namedTypeDeclaration.getType();
				if (typeConversion instanceof NamedType) {
					this.type = typeConversion; // On met le NamedType dans l'attribut Type
				} else {
					Logger.error("AbstractConversion : Erreur sur le type de la declaration. C'est : " + typeConversion);
				}
			} else {
				ok = false;
				Logger.error("AbstractConversion : Ce n'est pas une TypeDeclaration! C'est : " + namedTypeDeclaration);
			}

		}

		return ok;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see fr.n7.stl.block.ast.Expression#getCode(fr.n7.stl.tam.ast.TAMFactory)
	 */
	@Override
	public Fragment getCode(TAMFactory _factory) {
		Fragment fragment = _factory.createFragment();
		fragment.addComment("Conversion Abstraite. TODO?");
		return fragment;
	}

}

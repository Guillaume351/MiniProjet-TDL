/**
 *
 */
package fr.n7.stl.block.ast.type;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;

/**
 * Implementation of the Abstract Syntax Tree node for a function type.
 * 
 * @author Marc Pantel
 *
 */
public class FunctionType implements Type {

	private Type result;
	private List<Type> parameters;

	public FunctionType(Type _result, Iterable<Type> _parameters) {
		this.result = _result;
		this.parameters = new LinkedList<Type>();
		for (Type _type : _parameters) {
			this.parameters.add(_type);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.n7.stl.block.ast.Type#equalsTo(fr.n7.stl.block.ast.Type)
	 */
	@Override
	public boolean equalsTo(Type _other) {
		boolean ok;

		if (_other instanceof FunctionType) {
			ok = this.result.equalsTo(((FunctionType) _other).result);
			if (this.parameters.size() == ((FunctionType) _other).parameters.size()) {
				for (int i = 0; i < this.parameters.size(); i++) {
					ok = ok && this.parameters.get(i).equalsTo(((FunctionType) _other).parameters.get(i));
				}
			} else {
				ok = false;
			}
		} else {
			ok = false;
		}

		return ok;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.n7.stl.block.ast.Type#compatibleWith(fr.n7.stl.block.ast.Type)
	 */
	@Override
	public boolean compatibleWith(Type _other) {
		boolean ok;

		if (_other instanceof FunctionType) {
			ok = this.result.compatibleWith(((FunctionType) _other).result);
			if (this.parameters.size() == ((FunctionType) _other).parameters.size()) {
				for (int i = 0; i < this.parameters.size(); i++) {
					ok = ok && this.parameters.get(i).compatibleWith(((FunctionType) _other).parameters.get(i));
				}
			} else {
				ok = false;
			}
		} else {
			ok = false;
		}

		return ok;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.n7.stl.block.ast.Type#merge(fr.n7.stl.block.ast.Type)
	 */
	@Override
	public Type merge(Type _other) {
		if (_other instanceof FunctionType) {
			if (this.parameters.size() == ((FunctionType) _other).parameters.size()) {
				List<Type> otherParameters = new LinkedList<Type>();
				for (int i = 0; i < this.parameters.size(); i++) {
					otherParameters.add(this.parameters.get(i).merge(((FunctionType) _other).parameters.get(i)));
				}
				return new FunctionType(this.result.merge(((FunctionType) _other).result), otherParameters);
			} else {
				return AtomicType.ErrorType;
			}
		} else {
			return AtomicType.ErrorType;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.n7.stl.block.ast.Type#length(int)
	 */
	@Override
	public int length() {
		int length = this.result.length();
		for (Type parType : this.parameters) {
			length += parType.length();
		}

		return length;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String _result = "(";
		Iterator<Type> _iter = this.parameters.iterator();
		if (_iter.hasNext()) {
			_result += _iter.next();
			while (_iter.hasNext()) {
				_result += " ," + _iter.next();
			}
		}
		return _result + ") -> " + this.result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.n7.stl.block.ast.type.Type#resolve(fr.n7.stl.block.ast.scope.Scope)
	 */
	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		boolean ok = this.result.resolve(_scope);
		for (Type parType : this.parameters) {
			ok = ok && parType.resolve(_scope);
		}

		return ok;
	}

}

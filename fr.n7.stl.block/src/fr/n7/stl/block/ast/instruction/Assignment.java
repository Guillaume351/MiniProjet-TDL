/**
 * 
 */
package fr.n7.stl.block.ast.instruction;

import fr.n7.stl.block.ast.SemanticsUndefinedException;
import fr.n7.stl.block.ast.expression.Expression;
import fr.n7.stl.block.ast.expression.assignable.AssignableExpression;
import fr.n7.stl.block.ast.instruction.declaration.ConstantDeclaration;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.scope.SymbolTable;
import fr.n7.stl.block.ast.type.AtomicType;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

import javax.lang.model.type.ErrorType;
import java.net.http.HttpHeaders;

/**
 * Implementation of the Abstract Syntax Tree node for an array type.
 * @author Marc Pantel
 *
 */
public class Assignment implements Instruction, Expression {

	protected Expression value;
	protected AssignableExpression assignable;

	/**
	 * Create an assignment instruction implementation from the assignable expression
	 * and the assigned value.
	 * @param _assignable Expression that can be assigned a value.
	 * @param _value Value assigned to the expression.
	 */
	public Assignment(AssignableExpression _assignable, Expression _value) {
		this.assignable = _assignable;
		this.value = _value;
		/* This attribute will be assigned to the appropriate value by the resolve action */
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.assignable + " = " + this.value.toString() + ";\n";
	}
	
	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.instruction.Instruction#collect(fr.n7.stl.block.ast.scope.HierarchicalScope)
	 */
	@Override
	public boolean collect(HierarchicalScope<Declaration> _scope) {
		boolean ok = this.value.collect(_scope);
		ok = ok && this.assignable.collect(_scope);
		return ok;
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.instruction.Instruction#resolve(fr.n7.stl.block.ast.scope.HierarchicalScope)
	 */
	@Override
	public boolean resolve(HierarchicalScope<Declaration> _scope) {
		Declaration info = _scope.get(this.assignable.toString().trim()); //TODO : verifier si y'a pas une fonction pour recup le nom
		boolean ok1 = (info != null);
		boolean ok2 = this.value.resolve(_scope);
		boolean ok3 = this.assignable.resolve(_scope);
		if(!ok1){
			//TODO : cette verification est maintenant redondante a cause du ok3. Retirer
			Logger.error("Assignment impossible : l'assignable n'est pas dans le scope");
		}else{
			if(info instanceof ConstantDeclaration){
				ok1 = false;
				Logger.error("Assignment impossible : l'assignable est constant");
			}

		}

		return ok1 && ok2 && ok3;
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.expression.Expression#getType()
	 */
	@Override
	public Type getType() {
		Type texpr = this.assignable.getType();
		Type tvalue = this.value.getType();

		if (!(texpr.compatibleWith(tvalue))){
			Logger.warning("Assignment : types non compatibles");
			return AtomicType.ErrorType;
		} else {
			return texpr;
		}
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.Instruction#checkType()
	 */
	@Override
	public boolean checkType() {
		Type texpr = this.assignable.getType();
		Type tvalue = this.value.getType();

		if (!(texpr.compatibleWith(tvalue))){
			Logger.warning("Assignment : types non compatibles");
			return false;
		} else {
			return true;
		}
	}
	
	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.Instruction#allocateMemory(fr.n7.stl.tam.ast.Register, int)
	 */
	@Override
	public int allocateMemory(Register _register, int _offset) {
		return 0;
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.Instruction#getCode(fr.n7.stl.tam.ast.TAMFactory)
	 */
	@Override
	public Fragment getCode(TAMFactory _factory) {

		Fragment fragment = _factory.createFragment();
		fragment.append(this.assignable.getCode(_factory));
		fragment.append(this.value.getCode(_factory));

		return fragment;
	}

}

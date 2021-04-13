/**
 * 
 */
package fr.n7.stl.block.ast.expression.assignable;

import fr.n7.stl.block.ast.expression.AbstractField;
import fr.n7.stl.block.ast.type.NamedType;
import fr.n7.stl.block.ast.type.RecordType;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

/**
 * Abstract Syntax Tree node for an expression whose computation assigns a field in a record.
 * @author Marc Pantel
 *
 */
public class FieldAssignment extends AbstractField implements AssignableExpression {

	/**
	 * Construction for the implementation of a record field assignment expression Abstract Syntax Tree node.
	 * @param _record Abstract Syntax Tree for the record part in a record field assignment expression.
	 * @param _name Name of the field in the record field assignment expression.
	 */
	public FieldAssignment(AssignableExpression _record, String _name) {
		super(_record, _name);
	}
	
	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.impl.FieldAccessImpl#getCode(fr.n7.stl.tam.ast.TAMFactory)
	 */
	@Override
	public Fragment getCode(TAMFactory _factory) {
		Fragment fragment = _factory.createFragment();


		Type typeDuRecord = this.record.getType();

		if (typeDuRecord instanceof NamedType) {
			typeDuRecord = ((NamedType) typeDuRecord).getType();
		}

		// Calcul de l'offset associe a FieldAssignment
		RecordType recordDuField = (RecordType) typeDuRecord;

		int offsetDuField = recordDuField.getOffSetForField(this.field.getName());

		// On recupere le register associe
		if (true) {//recordDuField.getDeclaration().getType() instanceof ){
			//	Register register = (()recordDuField.getDeclaration()).getRegister();


			//fragment.add(_factory.createStore(register, offsetDuField, this.getType().length()));
		} else {
			Logger.error("FieldAssignment : La declaration du register n'est pas VariableDeclaration! C'est : " + recordDuField.getDeclaration().getType());
		}

		return fragment;
	}
	
}

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

		Expression expression = this;

		int offsetDuField = 0;

		// Tant qu'on a du FieldAccess, on descend (enfin on remonte) (exemple : segment.point.x)
		while (expression instanceof FieldAssignment) {

			FieldAssignment fieldAssignment = ((FieldAssignment) expression);

			Type typeDuRecordDuField = fieldAssignment.record.getType();

			if (typeDuRecordDuField instanceof NamedType) {
				typeDuRecordDuField = ((NamedType) typeDuRecordDuField).getType();
			}

			RecordType recordType = ((RecordType) typeDuRecordDuField);

			offsetDuField += recordType.getOffSetForField(fieldAssignment.name);

			expression = ((FieldAssignment) expression).record;

		}

		VariableAssignment accesALaVariableDesFields = ((VariableAssignment) expression);

		VariableDeclaration declaration = accesALaVariableDesFields.getDeclaration();

		fragment.add(_factory.createLoadA(declaration.getRegister(), declaration.getOffset()));

		fragment.add(_factory.createLoadL(offsetDuField));

		fragment.add(Library.IAdd);

		fragment.add(_factory.createStoreI(this.field.getType().length()));


		return fragment;
	}
	
}

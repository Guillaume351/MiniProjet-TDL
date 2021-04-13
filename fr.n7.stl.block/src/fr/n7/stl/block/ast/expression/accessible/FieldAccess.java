/**
 * 
 */
package fr.n7.stl.block.ast.expression.accessible;

import fr.n7.stl.block.ast.expression.AbstractField;
import fr.n7.stl.block.ast.expression.Expression;
import fr.n7.stl.block.ast.instruction.declaration.VariableDeclaration;
import fr.n7.stl.block.ast.type.NamedType;
import fr.n7.stl.block.ast.type.RecordType;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;

/**
 * Implementation of the Abstract Syntax Tree node for accessing a field in a record.
 * @author Marc Pantel
 *
 */
public class FieldAccess extends AbstractField implements Expression {

	/**
	 * Construction for the implementation of a record field access expression Abstract Syntax Tree node.
	 * @param _record Abstract Syntax Tree for the record part in a record field access expression.
	 * @param _name Name of the field in the record field access expression.
	 */
	public FieldAccess(Expression _record, String _name) {
		super(_record, _name);
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.Expression#getCode(fr.n7.stl.tam.ast.TAMFactory)
	 */
	@Override
	public Fragment getCode(TAMFactory _factory) {
		Fragment fragment = _factory.createFragment();

		Expression expression = this;

		int offsetDuField = 0;

		// Tant qu'on a du FieldAccess, on descend (enfin on remonte) (exemple : segment.point.x)
		while (expression instanceof FieldAccess) {

			FieldAccess fieldAccess = ((FieldAccess) expression);

			Type typeDuRecordDuField = fieldAccess.record.getType();

			if (typeDuRecordDuField instanceof NamedType) {
				typeDuRecordDuField = ((NamedType) typeDuRecordDuField).getType();
			}

			RecordType recordType = ((RecordType) typeDuRecordDuField);

			offsetDuField += recordType.getOffSetForField(fieldAccess.name);

			expression = ((FieldAccess) expression).record;

		}

		IdentifierAccess accesALaVariableDesFields = ((IdentifierAccess) expression);
		VariableDeclaration declaration = accesALaVariableDesFields.getDeclaration();

		//offsetDuField += declaration.getOffset();

		fragment.add(Library.IAdd);

		fragment.add(_factory.createLoadI(this.field.getType().length()));

		return fragment;
	}

}

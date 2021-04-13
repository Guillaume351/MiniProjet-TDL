/**
 * 
 */
package fr.n7.stl.block.ast.expression.accessible;

import fr.n7.stl.block.ast.expression.AbstractAccess;
import fr.n7.stl.block.ast.instruction.declaration.VariableDeclaration;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;

/**
 * Implementation of the Abstract Syntax Tree node for a variable access expression.
 * @author Marc Pantel
 */
public class VariableAccess extends AbstractAccess {
	
	protected VariableDeclaration declaration;
	
	/**
	 * Creates a variable use expression Abstract Syntax Tree node.
	 * @param _declaration Name of the used variable.
	 */
	public VariableAccess(VariableDeclaration _declaration) {
		this.declaration = _declaration;
	}
	
	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.expression.AbstractUse#getDeclaration()
	 */
	public Declaration getDeclaration() {
		return this.declaration;
	}

	/* (non-Javadoc)
	 * @see fr.n7.stl.block.ast.expression.AbstractUse#getCode(fr.n7.stl.tam.ast.TAMFactory)
	 */
	public Fragment getCode(TAMFactory _factory) {
		Fragment _result = _factory.createFragment();

		// On a remplacé LOAD par LOADA pour fonctionner avec LOADI dans les opérateurs
		_result.add(_factory.createLoadA(
				this.declaration.getRegister(),
				this.declaration.getOffset()
		));

		_result.add(_factory.createLoadI(this.declaration.getType().length()));

		_result.addComment("Acces Variable " + this.declaration.getName());
		return _result;
	}

}

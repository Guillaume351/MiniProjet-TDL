/**
 *
 */
package fr.n7.stl.block.ast.expression.accessible;

import fr.n7.stl.block.ast.SemanticsUndefinedException;
import fr.n7.stl.block.ast.expression.AbstractAccess;
import fr.n7.stl.block.ast.instruction.declaration.ParameterDeclaration;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;

/**
 * Implementation of the Abstract Syntax Tree node for a variable access expression.
 * @author Marc Pantel
 */
public class ParameterAccess extends AbstractAccess {

    protected ParameterDeclaration declaration;

    /**
     * Creates a variable use expression Abstract Syntax Tree node.
     * @param _declaration Declaration of the used variable.
     */
    public ParameterAccess(ParameterDeclaration _declaration) {
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
        Fragment fragment = _factory.createFragment();
        // On accède dans le negatif du registre LB
        fragment.add(_factory.createLoad(Register.LB, this.declaration.getOffset(),
                this.getDeclaration().getType().length()));
		fragment.addComment(this.toString());
        return fragment;
    }

}

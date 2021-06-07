package fr.n7.stl.block.ast.expression.accessible;

import fr.n7.stl.block.ast.expression.Expression;
import fr.n7.stl.block.ast.instruction.declaration.minijava.ClassDeclaration;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.type.ClassType;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

public class PropertyAccess implements Expression {

    /**
     * Expression sur laquelle on essaye d'accéder à une propriété"
     */
    Expression expression;

    String etiquette;

    public PropertyAccess(Expression expression, String etiquette) {
        this.expression = expression;
        this.etiquette = etiquette;
    }

    @Override
    public boolean collect(HierarchicalScope<Declaration> _scope) {
        if (this.expression.collect(_scope)) {
            return true;
        } else {
            Logger.error("PropertyAccess: Impossible de collect");
            return false;
        }
    }

    @Override
    public boolean resolve(HierarchicalScope<Declaration> _scope) {
        if (this.expression.resolve(_scope)) {
            //TODO : Il faut gérer un IdentifierAccess egalement...
            if (this.expression instanceof IdentifierAccess) {
                if (((IdentifierAccess) this.expression).getExpression() != null) {
                    VariableAccess access = (VariableAccess) ((IdentifierAccess) this.expression).getExpression();
                    if (access.getDeclaration().getType() instanceof ClassType) {
                        ClassType typeDeClasse = (ClassType) access.getDeclaration().getType();
                        typeDeClasse.resolve(_scope);
                        ClassDeclaration declaration = typeDeClasse.getClassDeclaration();
                        if (declaration.containsAttributeNamed(etiquette)) {
                            return true;
                        } else {
                            Logger.error("PropertyAccess: L'attribut est introuvable ! : "
                                    + etiquette);
                        }
                    } else {
                        Logger.error("PropertyAccess : ce n'est pas une ClassType! C'est : " + access.getDeclaration().getClass());
                    }
                } else {
                    Logger.error("PropertyAccess : La Declaration est nulle !");
                }

            }
        }
        Logger.error("PropertyAccess: Le resolve passe pas.");
        return false;
    }

    @Override
    public Type getType() {
        throw new RuntimeException("Unimplemented");
    }

    @Override
    public Fragment getCode(TAMFactory _factory) {
        throw new RuntimeException("Unimplemented");
    }

}

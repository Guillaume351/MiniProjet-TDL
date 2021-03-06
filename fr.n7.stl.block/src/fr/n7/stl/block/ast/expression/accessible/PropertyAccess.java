package fr.n7.stl.block.ast.expression.accessible;

import fr.n7.stl.block.ast.expression.Expression;
import fr.n7.stl.block.ast.expression.value.ThisValue;
import fr.n7.stl.block.ast.instruction.declaration.minijava.AttributeDeclarationElement;
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

    private AttributeDeclarationElement attribute;

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
        if (!this.expression.resolve(_scope))
            Logger.error("PropertyAccess: Le resolve passe pas.");

        if (this.expression instanceof IdentifierAccess) {

            if (((IdentifierAccess) this.expression).getExpression() == null)
                Logger.error("PropertyAccess : La Declaration est nulle !");

            VariableAccess access = (VariableAccess) ((IdentifierAccess) this.expression).getExpression();

            if (!(access.getDeclaration().getType() instanceof ClassType))
                Logger.error(
                        "PropertyAccess : ce n'est pas une ClassType! C'est : " + access.getDeclaration().getClass());

            ClassType typeDeClasse = (ClassType) access.getDeclaration().getType();

            // On resolve typeDeClasse pour qu'il récupère la ClassDeclaration
            if (!typeDeClasse.resolve(_scope))
                Logger.error("ClassType failed to resolve");

            if (!typeDeClasse.contains(etiquette))
                Logger.error("PropertyAccess: L'attribut est introuvable ! : " + etiquette);

            attribute = typeDeClasse.getProperty(etiquette);

            return true;

        }

        else if (this.expression instanceof ThisValue) {

            ThisValue self = (ThisValue) this.expression;
            ClassType type = (ClassType) self.getType();

            if (!type.contains(etiquette))
                Logger.error("PropertyAccess: L'attribut est introuvable ! : " + etiquette);

            attribute = type.getProperty(etiquette);
            return true;
        }

        Logger.error("Unexpected expression type " + this.expression);
        return false;
    }

    @Override
    public Type getType() {
        return this.attribute.getType();
    }

    @Override
    public Fragment getCode(TAMFactory _factory) {
        throw new RuntimeException("Unimplemented");
    }

}

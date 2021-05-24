package fr.n7.stl.block.ast.instruction.declaration;

import fr.n7.stl.block.ast.scope.AccessRight;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.type.Type;

public class ClassElement implements Declaration {

    AccessRight accessRight;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Type getType() {
        return null;
    }

    public AccessRight getAccessRight() {
        return accessRight;
    }

    public void setAccessRight(AccessRight accessRight) {
        this.accessRight = accessRight;
    }
}

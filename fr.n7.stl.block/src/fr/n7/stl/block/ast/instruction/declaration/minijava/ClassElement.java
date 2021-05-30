package fr.n7.stl.block.ast.instruction.declaration.minijava;

import fr.n7.stl.block.ast.instruction.Instruction;
import fr.n7.stl.block.ast.scope.AccessRight;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.scope.OwnedHierarchicalScope;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

public abstract class ClassElement implements Instruction, Declaration {

    /**
     * Modificateur d'accès (Private, Public..)
     */
    AccessRight accessRight;

    String name;

    private ClassDeclaration parentClass;

    public ClassElement(String name) {
        this.name = name;
    }

    public ClassDeclaration getParentClass() {
        return parentClass;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public abstract Type getType();

    public AccessRight getAccessRight() {
        return accessRight;
    }

    public void setAccessRight(AccessRight accessRight) {
        this.accessRight = accessRight;
    }

    @Override
    public boolean collect(HierarchicalScope<Declaration> _scope) {
        if (!(_scope instanceof OwnedHierarchicalScope)) {
            Logger.error("ClassElement not in a class member");
            return false;
        }
        this.parentClass = (ClassDeclaration) ((OwnedHierarchicalScope<?>) _scope).getOwner();
        return true;
    }

    @Override
    public abstract boolean resolve(HierarchicalScope<Declaration> _scope);

    @Override
    public abstract boolean checkType();

    @Override
    public abstract int allocateMemory(Register _register, int _offset);

    @Override
    public abstract Fragment getCode(TAMFactory _factory);
}

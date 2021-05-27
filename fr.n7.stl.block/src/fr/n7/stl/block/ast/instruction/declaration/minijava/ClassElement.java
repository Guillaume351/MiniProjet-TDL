package fr.n7.stl.block.ast.instruction.declaration.minijava;

import fr.n7.stl.block.ast.instruction.Instruction;
import fr.n7.stl.block.ast.scope.AccessRight;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;

public abstract class ClassElement implements Instruction, Declaration {

    /**
     * Modificateur d'accès (Private, Public..)
     */
    AccessRight accessRight;

    String name;

    public ClassElement(String name) {
        this.name = name;
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
    public abstract boolean collect(HierarchicalScope<Declaration> _scope);

    @Override
    public abstract boolean resolve(HierarchicalScope<Declaration> _scope);

    @Override
    public abstract boolean checkType();

    @Override
    public abstract int allocateMemory(Register _register, int _offset);

    @Override
    public abstract Fragment getCode(TAMFactory _factory);
}

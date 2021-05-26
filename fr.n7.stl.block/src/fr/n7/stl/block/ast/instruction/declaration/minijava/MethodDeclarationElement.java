package fr.n7.stl.block.ast.instruction.declaration.minijava;

import fr.n7.stl.block.ast.Block;
import fr.n7.stl.block.ast.instruction.declaration.Signature;
import fr.n7.stl.block.ast.scope.AccessRight;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;

public class MethodDeclarationElement extends ClassElement {

    protected Signature signature;

    /**
     * Est-ce que l'attribut est final
     */
    boolean isFinal;

    boolean isStatic;

    boolean isAbstract;

    /**
     * Corps de la fonction
     */
    Block body;

    public MethodDeclarationElement(Signature signature, boolean isFinal, boolean isStatic, boolean isAbstract, Block body) {
        super(signature.getName());
        this.signature = signature;
        this.isFinal = isFinal;
        this.isStatic = isStatic;
        this.isAbstract = isAbstract;
        this.body = body;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public Type getType() {
        return super.getType();
    }

    @Override
    public AccessRight getAccessRight() {
        return super.getAccessRight();
    }

    @Override
    public void setAccessRight(AccessRight accessRight) {
        super.setAccessRight(accessRight);
    }

    @Override
    public boolean collect(HierarchicalScope<Declaration> _scope) {
        return super.collect(_scope);
    }

    @Override
    public boolean resolve(HierarchicalScope<Declaration> _scope) {
        return super.resolve(_scope);
    }

    @Override
    public boolean checkType() {
        return super.checkType();
    }

    @Override
    public int allocateMemory(Register _register, int _offset) {
        return super.allocateMemory(_register, _offset);
    }

    @Override
    public Fragment getCode(TAMFactory _factory) {
        return super.getCode(_factory);
    }
}

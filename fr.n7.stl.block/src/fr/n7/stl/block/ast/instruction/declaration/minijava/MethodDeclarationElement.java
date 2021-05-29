package fr.n7.stl.block.ast.instruction.declaration.minijava;

import fr.n7.stl.block.ast.Block;
import fr.n7.stl.block.ast.instruction.declaration.Signature;
import fr.n7.stl.block.ast.scope.*;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

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

    /**
     * Table de symbole locale
     */
    OwnedSymbolTable localScope;

    public MethodDeclarationElement(Signature signature, boolean isFinal, boolean isStatic, boolean isAbstract,
            Block body) {
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
        return null;
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
        this.localScope = new OwnedSymbolTable(_scope, this);
        if (this.body.collect(_scope)){
            return true;
        }else{
            Logger.error("MethodDeclarationElement : Le collect passe pas!");
            return false;
        }
    }

    @Override
    public boolean resolve(HierarchicalScope<Declaration> _scope) {
        return false;
    }

    @Override
    public boolean checkType() {
        //TODO : verifier que le type de retour du body est celui declare
        return false;
    }

    @Override
    public int allocateMemory(Register _register, int _offset) {
        return 0;
    }

    @Override
    public Fragment getCode(TAMFactory _factory) {
        return null;
    }
}

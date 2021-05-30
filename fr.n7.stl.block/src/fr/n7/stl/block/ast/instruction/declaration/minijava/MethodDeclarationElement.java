package fr.n7.stl.block.ast.instruction.declaration.minijava;

import fr.n7.stl.block.ast.Block;
import fr.n7.stl.block.ast.instruction.declaration.Signature;
import fr.n7.stl.block.ast.scope.AccessRight;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.scope.OwnedSymbolTable;
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
        if (!super.collect(_scope))
            return false;
        this.localScope = new OwnedSymbolTable(_scope, this);
        if (!this.signature.collect(localScope)) {
            Logger.error("MethodDeclarationElement: signature.collect failed");
            return false;
        }
        if (!this.body.collect(localScope)) {
            Logger.error("MethodDeclarationElement: body.collect failed");
            return false;
        }
        return true;
    }

    @Override
    public boolean resolve(HierarchicalScope<Declaration> _scope) {
        return false;
    }

    @Override
    public boolean checkType() {
        // TODO : verifier que le type de retour du body est celui declare
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

package fr.n7.stl.block.ast.instruction.declaration.minijava;

import java.util.ArrayList;
import java.util.List;

import fr.n7.stl.block.ast.Block;
import fr.n7.stl.block.ast.instruction.declaration.ParameterDeclaration;
import fr.n7.stl.block.ast.scope.AccessRight;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.scope.OwnedSymbolTable;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

public class ConstructorDeclarationElement extends ClassElement {

    /**
     * Liste des paramètres du constructeur
     */
    List<ParameterDeclaration> parametres;

    Block body;

    /**
     * Table de symbole locale
     */
    OwnedSymbolTable localScope;

    public ConstructorDeclarationElement(String name, List<ParameterDeclaration> parametres, Block body) {
        super(name);

        if (parametres != null) {
            this.parametres = parametres;
        } else {
            this.parametres = new ArrayList<>();
        }

        this.body = body;
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
        if (this.body.collect(localScope)) {
            return true;
        } else {
            Logger.error("ConstructorDeclarationElement : Le collect passe pas!");
            return false;
        }
    }

    @Override
    public boolean resolve(HierarchicalScope<Declaration> _scope) {
        return false;
    }

    @Override
    public boolean checkType() {
        // TODO : verifier que le type du body colle avec void
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

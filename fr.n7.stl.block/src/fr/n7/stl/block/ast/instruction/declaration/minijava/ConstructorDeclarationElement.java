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
import java.util.Iterator;

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
    public String toString(){
        boolean modifiedAccess = this.accessRight != AccessRight.NOMODIFIER;
        String _result = "";
		Iterator<ParameterDeclaration> _iter = this.parametres.iterator();
		if (_iter.hasNext()) {
			_result += _iter.next();
			while (_iter.hasNext()) {
				_result += ", " + _iter.next();
			}
		}
        return (modifiedAccess ? this.accessRight : "") + " " + this.name + "(" + _result + ")" + this.body + "\n"; 
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
        this.getParentClass().registerConstructor(this);
        if (this.parametres.stream().allMatch(param -> param.collect(localScope)) && this.body.collect(localScope)) {
            return true;
        } else {
            Logger.error("ConstructorDeclarationElement : Le collect passe pas!");
            return false;
        }
    }

    @Override
    public boolean resolve(HierarchicalScope<Declaration> _scope) {
        if (!this.parametres.stream().allMatch(param -> param.resolve(localScope))) {
            Logger.error("ConstructorDeclarationElement : Le resolve des parametres passe pas!");
        }
        if (!this.body.resolve(localScope)) {
            Logger.error("ConstructorDeclarationElement : Le resolve du corps passe pas!");
        }
        return true;
    }

    @Override
    public boolean checkType() {
        return this.body.checkType();
    }

    @Override
    public int allocateMemory(Register _register, int _offset) {
        return 0;
    }

    @Override
    public Fragment getCode(TAMFactory _factory) {
        return null;
    }

    public int getNumberOfArguments() {
        return this.parametres.size();
    }

    public List<ParameterDeclaration> getParametres() {
        return parametres;
    }

}

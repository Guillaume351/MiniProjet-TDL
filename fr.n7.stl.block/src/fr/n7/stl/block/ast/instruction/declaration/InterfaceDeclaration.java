package fr.n7.stl.block.ast.instruction.declaration;

import fr.n7.stl.block.ast.Block;
import fr.n7.stl.block.ast.expression.Expression;
import fr.n7.stl.block.ast.instruction.Instruction;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

import java.util.List;

//TODO
public class InterfaceDeclaration extends AbstractDeclarationElement implements Instruction, Declaration {

    String name;

    //Generics generics;

    //MultipleInherits inherits;

    List<InterfaceElement> elements;

    public InterfaceDeclaration(String name /*,Generics generics, MultipleInherits inherits*/, List<InterfaceElement> elements) {
        this.name = name;
        //this.generics = generics;
        //this.multipleInherits = multipleInherits;
        this.elements = elements;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "interface" + this.name + " { " + this.elements + " } "  + ";\n";
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Type getType() {
        return null;
    }

    @Override
    public boolean collect(HierarchicalScope<Declaration> _scope) {
        if (_scope.accepts(this)) {
            _scope.register(this);
            return true;

        } else {
            Logger.error("InterfaceDeclaration : Tentative de double ajout d'une interface");
            return false;
        }
    }

    @Override
    public boolean resolve(HierarchicalScope<Declaration> _scope) {

        boolean ok = true;
        for (InterfaceElement element : this.elements){
            ok = ok && element.resolve(_scope);
        }
        boolean ok3 = _scope.contains(getName());

        if(!ok3) {
            Logger.error("InterfaceDeclaration : Le scope ne contient pas l'interface' " + this.getName());
        }
        return ok && ok3;
    }

    @Override
    public boolean checkType() {
        boolean ok = true;
        for (InterfaceElement element : this.elements){
            ok = ok && element.checkType();
        }
        return ok;
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

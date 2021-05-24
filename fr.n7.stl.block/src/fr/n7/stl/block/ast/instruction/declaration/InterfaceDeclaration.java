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

import java.util.List;

//TODO
public class InterfaceDeclaration implements Instruction, Declaration {

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

    @Override
    public boolean collect(HierarchicalScope<Declaration> _scope) {
        return false;
    }

    @Override
    public boolean resolve(HierarchicalScope<Declaration> _scope) {
        return false;
    }

    @Override
    public boolean checkType() {
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

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Type getType() {
        return null;
    }
}

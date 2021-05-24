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

public class ClassDeclaration extends AbstractDeclarationElement implements Instruction, Declaration {

    String name;

    /**
     * Liste les identifiants (nom) des interfaces implementees
     */
    List<String> identifiantsInterfaces;

    /**
     * Liste des interfaces implementees par la classe
     */
    List<InterfaceDeclaration> interfacesImplementees; //TODO : verifier si c'est bien une liste d'expressions

    List<ClassElement> bodyElements;

    public ClassDeclaration(String name, List<String> identifiantsInterfaces, List<ClassElement> elements) {
        this.name = name;
        this.identifiantsInterfaces = identifiantsInterfaces;
        this.bodyElements = elements;
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

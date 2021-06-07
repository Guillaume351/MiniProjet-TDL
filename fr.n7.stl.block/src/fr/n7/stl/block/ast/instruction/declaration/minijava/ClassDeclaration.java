package fr.n7.stl.block.ast.instruction.declaration.minijava;

import fr.n7.stl.block.ast.instruction.Instruction;
import fr.n7.stl.block.ast.instruction.declaration.AbstractDeclarationElement;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.scope.OwnedHierarchicalScope;
import fr.n7.stl.block.ast.scope.OwnedSymbolTable;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;

import java.util.ArrayList;
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
    List<String> interfacesImplementees;

    List<ClassElement> bodyElements;

    OwnedHierarchicalScope<Declaration> scope;

    List<ConstructorDeclarationElement> constructors = new ArrayList<>();

    public ClassDeclaration(String name, List<String> identifiantsInterfaces, List<ClassElement> elements) {
        this.name = name;
        this.identifiantsInterfaces = identifiantsInterfaces;
        this.bodyElements = elements;
    }

    @Override
    public boolean collect(HierarchicalScope<Declaration> _scope) {
        _scope.register(this);
        scope = new OwnedSymbolTable(_scope, this);
        return this.bodyElements.stream().allMatch(element -> element.collect(scope));
    }

    @Override
    public boolean resolve(HierarchicalScope<Declaration> _scope) {
        return this.bodyElements.stream().allMatch(element -> element.resolve(scope));
    }

    @Override
    public boolean checkType() {
        return true;
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
        return name;
    }

    @Override
    public Type getType() {
        return null;
    }

    /**
     * Utilisée pour les PropertyAccess
     *
     * @return
     */
    public List<AttributeDeclarationElement> getAllAttributes() {
        List<AttributeDeclarationElement> attributes = new ArrayList<>();
        for (ClassElement classElement : this.bodyElements) {
            if (classElement instanceof AttributeDeclarationElement) {
                attributes.add((AttributeDeclarationElement) classElement);
            }
        }
        return attributes;
    }

    public List<MethodDeclarationElement> getAllMethods() {
        List<MethodDeclarationElement> methods = new ArrayList<>();
        for (ClassElement classElement : this.bodyElements) {
            if (classElement instanceof MethodDeclarationElement) {
                methods.add((MethodDeclarationElement) classElement);
            }
        }
        return methods;
    }

    public boolean containsAttributeNamed(String etiquette) {
        for (AttributeDeclarationElement attribute : this.getAllAttributes()) {
            if ((attribute).getName().equals(etiquette)) {
                return true;
            }
        }
        return false;
    }

    public void registerConstructor(ConstructorDeclarationElement constructorDeclarationElement) {
        constructors.add(constructorDeclarationElement);
    }
}

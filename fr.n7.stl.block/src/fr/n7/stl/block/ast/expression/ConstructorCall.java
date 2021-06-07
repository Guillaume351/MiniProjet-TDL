package fr.n7.stl.block.ast.expression;

import java.util.List;

import fr.n7.stl.block.ast.instruction.Instruction;
import fr.n7.stl.block.ast.instruction.declaration.minijava.ConstructorDeclarationElement;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.scope.OwnedHierarchicalScope;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;

public class ConstructorCall implements Instruction {

  List<Expression> parametres;

  public ConstructorCall(List<Expression> arrayList) {
    this.parametres = arrayList;
  }

  @Override
  public boolean collect(HierarchicalScope<Declaration> _scope) {
    // collecter les arguments
    return this.parametres.stream().allMatch(param -> param.collect(_scope));
  }

  @Override
  public boolean resolve(HierarchicalScope<Declaration> _scope) {
    // Utiliser le scope pour chercher le bon constructeur
    System.out.println(((ConstructorDeclarationElement) ((OwnedHierarchicalScope) _scope).getOwner()).getParentClass()
        .getConstructors());
    throw new RuntimeException();
  }

  @Override
  public boolean checkType() {
    throw new RuntimeException();
  }

  @Override
  public int allocateMemory(Register _register, int _offset) {
    throw new RuntimeException();
  }

  @Override
  public Fragment getCode(TAMFactory _factory) {
    throw new RuntimeException();
  }

}

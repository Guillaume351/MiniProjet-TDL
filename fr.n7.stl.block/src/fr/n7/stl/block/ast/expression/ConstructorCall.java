package fr.n7.stl.block.ast.expression;

import java.util.List;
import java.util.stream.Collectors;

import fr.n7.stl.block.ast.instruction.Instruction;
import fr.n7.stl.block.ast.instruction.declaration.minijava.ConstructorDeclarationElement;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.scope.OwnedHierarchicalScope;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

public class ConstructorCall implements Instruction {

  List<Expression> parametres;
  private ConstructorDeclarationElement constructorToCall;

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
    if (!(_scope instanceof OwnedHierarchicalScope))
      Logger.error("Cannot use `this()` here");

    if (!(((OwnedHierarchicalScope) _scope).getOwner() instanceof ConstructorDeclarationElement))
      Logger.error("Cannot use `this()` here");

    ConstructorDeclarationElement constructor = (ConstructorDeclarationElement) ((OwnedHierarchicalScope) _scope)
        .getOwner();
    List<ConstructorDeclarationElement> constructors = constructor.getParentClass().getConstructors();

    List<ConstructorDeclarationElement> matchingConstructors = constructors.stream()
        .filter(ctr -> ctr.getNumberOfArguments() == this.parametres.size()).collect(Collectors.toList());

    int len = matchingConstructors.size();
    if (len != 1)
      Logger.error(len == 0 ? "No matching constructors found" : "Too many matching constructors found");

    this.constructorToCall = matchingConstructors.get(0);
    return true;
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

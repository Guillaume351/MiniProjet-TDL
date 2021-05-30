package fr.n7.stl.block;

import java.util.List;

import fr.n7.stl.block.ast.expression.Expression;
import fr.n7.stl.block.ast.instruction.Instruction;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;

public class ConstructorCall implements Instruction {

  public ConstructorCall(List<Expression> arrayList) {
    throw new RuntimeException();
  }

  @Override
  public boolean collect(HierarchicalScope<Declaration> _scope) {
    // collecter les arguments
    throw new RuntimeException();
  }

  @Override
  public boolean resolve(HierarchicalScope<Declaration> _scope) {
    // Utiliser le scope pour chercher le bon constructeur
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

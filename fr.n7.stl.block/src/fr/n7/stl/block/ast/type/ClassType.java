package fr.n7.stl.block.ast.type;

import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;

public class ClassType implements Type {

  private String className;

  public ClassType(String className) {
    this.className = className;
  }

  @Override
  public boolean equalsTo(Type _other) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean compatibleWith(Type _other) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public Type merge(Type _other) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int length() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public boolean resolve(HierarchicalScope<Declaration> _scope) {
    // TODO Auto-generated method stub
    return false;
  }

}

package fr.n7.stl.block.ast.expression.value;

import fr.n7.stl.block.ast.expression.assignable.AssignableExpression;
import fr.n7.stl.block.ast.instruction.declaration.minijava.ClassDeclaration;
import fr.n7.stl.block.ast.instruction.declaration.minijava.ClassElement;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.scope.OwnedHierarchicalScope;
import fr.n7.stl.block.ast.type.ClassType;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

public class ThisValue implements Value, AssignableExpression {

  private Declaration method;
  private ClassDeclaration parentClass;
  private ClassType type;

  @Override
  public boolean collect(HierarchicalScope<Declaration> _scope) {
    if (!(_scope instanceof OwnedHierarchicalScope)) {
      Logger.error("`this` not in a class member");
      return false;
    }
    method = ((OwnedHierarchicalScope<Declaration>) _scope).getOwner();
    if (!(method instanceof ClassElement)) {
      Logger.error("`this` not in a class member");
      return false;
    }
    parentClass = ((ClassElement) method).getParentClass();
    type = new ClassType(parentClass.getName());

    return true;
  }

  @Override
  public boolean resolve(HierarchicalScope<Declaration> _scope) {
    return type.resolve(_scope);
  }

  @Override
  public Type getType() {
    return type;
  }

  @Override
  public Fragment getCode(TAMFactory _factory) {
    // TODO Auto-generated method stub
    return null;
  }

}

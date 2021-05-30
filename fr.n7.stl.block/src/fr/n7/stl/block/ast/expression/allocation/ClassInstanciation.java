package fr.n7.stl.block.ast.expression.allocation;

import java.util.List;

import fr.n7.stl.block.ast.expression.Expression;
import fr.n7.stl.block.ast.instruction.declaration.minijava.ClassDeclaration;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.type.ClassType;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

public class ClassInstanciation implements Expression {

  private ClassType classType;
  private List<Expression> params;
  private ClassDeclaration classDeclaration;

  public ClassInstanciation(Type classType, List<Expression> params) {
    if (!(classType instanceof ClassType)) {
      Logger.error("Cannot instantiate something that is not a class (type '" + classType + "')");
    }
    this.classType = (ClassType) classType;
    this.params = params;
  }

  @Override
  public boolean collect(HierarchicalScope<Declaration> _scope) {
    return params.stream().allMatch(param -> param.collect(_scope));
  }

  @Override
  public boolean resolve(HierarchicalScope<Declaration> _scope) {
    if (!_scope.knows(classType.getClassName())
        || !(_scope.get(classType.getClassName()) instanceof ClassDeclaration)) {
      Logger.error("Unknown class '" + classType.getClassName() + "'");
    }
    this.classDeclaration = (ClassDeclaration) _scope.get(classType.getClassName());
    return params.stream().allMatch(param -> param.resolve(_scope));
  }

  @Override
  public Type getType() {
    return classType;
  }

  @Override
  public Fragment getCode(TAMFactory _factory) {
    // TODO Auto-generated method stub
    return null;
  }

  public ClassDeclaration getClassDeclaration() {
    return classDeclaration;
  }

}

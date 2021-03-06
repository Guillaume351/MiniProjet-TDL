package fr.n7.stl.block.ast.expression.allocation;

import fr.n7.stl.block.ast.expression.Expression;
import fr.n7.stl.block.ast.instruction.declaration.minijava.ClassDeclaration;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.type.ClassType;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

import java.util.List;

public class ClassInstanciation implements Expression {

  private final ClassType classType;
  private final List<Expression> params;
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
    this.classType.setClassDeclaration(this.classDeclaration);
    return params.stream().allMatch(param -> param.resolve(_scope)) && this.classDeclaration.matchingConstructorWithParameters(params);
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

  public void setClassDeclaration(ClassDeclaration classDeclaration) {
    this.classDeclaration = classDeclaration;
  }
}

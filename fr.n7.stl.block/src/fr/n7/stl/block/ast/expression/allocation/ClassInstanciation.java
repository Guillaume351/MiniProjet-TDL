package fr.n7.stl.block.ast.expression.allocation;

import fr.n7.stl.block.ast.expression.Expression;
import fr.n7.stl.block.ast.instruction.declaration.minijava.AttributeDeclarationElement;
import fr.n7.stl.block.ast.instruction.declaration.minijava.ClassDeclaration;
import fr.n7.stl.block.ast.instruction.declaration.minijava.MethodDeclarationElement;
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

  public boolean containsEtiquette(String etiquette) {
    for (AttributeDeclarationElement attribute : this.getClassDeclaration().getAllAttributes()) {
      if ((attribute).getName().equals(etiquette)) {
        return true;
      }
    }
    return false;
  }

  public boolean containsMethod(String etiquette, List<Expression> params) {
    List<MethodDeclarationElement> allMethodsOfClass = this.getClassDeclaration().getAllMethods();
    for (MethodDeclarationElement method : allMethodsOfClass) {
      if (method.getName().equals(etiquette)) {
        // Pour le moment, on verifie simplement que le nombre de params colle. dans le checkType, il faut verifier que les types collent.
        if (method.getSignature().getParameters().size() == params.size()) {
          return true;
        }
      }
    }

    return false;
  }

}

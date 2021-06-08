package fr.n7.stl.block.ast.type;

import fr.n7.stl.block.ast.instruction.declaration.minijava.AttributeDeclarationElement;
import fr.n7.stl.block.ast.instruction.declaration.minijava.ClassDeclaration;
import fr.n7.stl.block.ast.instruction.declaration.minijava.MethodDeclarationElement;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.util.Logger;

public class ClassType implements Type {

  private final String className;

  private ClassDeclaration classDeclaration;

  public ClassType(String className) {
    this.className = className;
  }

  public String getClassName() {
    return className;
  }

  @Override
  public boolean equalsTo(Type _other) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean compatibleWith(Type _other) {
    // TODO : gérer les interfaces ?
    if (_other instanceof ClassType) {
      if (((ClassType) _other).getClassDeclaration() == this.getClassDeclaration()) {
        return true;
      } else {
        Logger.error("ClassType : Les types de ClassDeclarations ne matchent pas : " +
                this.getClassDeclaration() + " et " + ((ClassType) _other).getClassDeclaration());
      }
    }
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
    if (!_scope.knows(className)) {
      Logger.error("Class '" + className + "' unknown");
    }
    this.classDeclaration = (ClassDeclaration) _scope.get(className);
    return true;
  }

  public boolean contains(String name) {
    return classDeclaration.containsAttributeNamed(name);
  }

  public MethodDeclarationElement getMethod(String name) {
    return null;
  }

  public AttributeDeclarationElement getProperty(String name) {
    return classDeclaration.getAllAttributes().stream().filter(attr -> attr.getName().equals(name)).findAny().get();
  }

  public ClassDeclaration getClassDeclaration() {
    return classDeclaration;
  }

  public void setClassDeclaration(ClassDeclaration classDeclaration) {
    this.classDeclaration = classDeclaration;
  }
}

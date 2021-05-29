package fr.n7.stl.block.ast.expression.allocation;

import java.util.List;

import fr.n7.stl.block.ast.expression.Expression;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

public class ClassInstanciation implements Expression {

  private Type classType;
  private List<Expression> params;

  public ClassInstanciation(Type classType, List<Expression> params) {
    this.classType = classType;
    this.params = params;
  }

  @Override
  public boolean collect(HierarchicalScope<Declaration> _scope) {
    Logger.error("new Class");
    return false;
  }

  @Override
  public boolean resolve(HierarchicalScope<Declaration> _scope) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public Type getType() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Fragment getCode(TAMFactory _factory) {
    // TODO Auto-generated method stub
    return null;
  }

}

package fr.n7.stl.block.ast.expression;

import java.util.List;

import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;

public class ExpressionFunctionCall implements Expression {

  private Expression callable;
  private List<Expression> params;

  public ExpressionFunctionCall(Expression callable, List<Expression> params) {
    this.callable = callable;
    this.params = params;
  }

  @Override
  public boolean collect(HierarchicalScope<Declaration> _scope) {
    // TODO Auto-generated method stub
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

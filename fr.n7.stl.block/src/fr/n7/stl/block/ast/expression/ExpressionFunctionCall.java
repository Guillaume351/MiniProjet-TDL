package fr.n7.stl.block.ast.expression;

import java.util.List;

import fr.n7.stl.block.ast.expression.assignable.AssignableExpression;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;

public class ExpressionFunctionCall implements AssignableExpression {

  private Expression callable;
  private List<Expression> params;

  public ExpressionFunctionCall(Expression callable, List<Expression> params) {
    this.callable = callable;
    this.params = params;
  }

  @Override
  public boolean collect(HierarchicalScope<Declaration> _scope) {
    return this.callable.collect(_scope) && this.params.stream().allMatch(param -> param.collect(_scope));
  }

  @Override
  public boolean resolve(HierarchicalScope<Declaration> _scope) {
    return this.callable.resolve(_scope) && this.params.stream().allMatch(param -> param.resolve(_scope));
  }

  @Override
  public Type getType() {
    return this.callable.getType();
  }

  @Override
  public Fragment getCode(TAMFactory _factory) {
    // TODO Auto-generated method stub
    return null;
  }

}

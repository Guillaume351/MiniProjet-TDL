package fr.n7.stl.block.ast.instruction;

import fr.n7.stl.block.ast.expression.Expression;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;

public class VoidExpression implements Instruction {

  private Expression expr;

  public VoidExpression(Expression expr) {
    this.expr = expr;
  }

  @Override
  public boolean collect(HierarchicalScope<Declaration> _scope) {
    return this.expr.collect(_scope);
  }

  @Override
  public boolean resolve(HierarchicalScope<Declaration> _scope) {
    return this.expr.resolve(_scope);
  }

  @Override
  public boolean checkType() {
    // L'expression est ignorée, on ne s'intéresse qu'à ses effets de bords
    // Ainsi, la valeur de `f()` est ignorée.
    // (pour `f` définie `function f(){print 1; return 1;}`)
    return true;
  }

  @Override
  public int allocateMemory(Register _register, int _offset) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public Fragment getCode(TAMFactory _factory) {
    // TODO Auto-generated method stub
    return null;
  }

}

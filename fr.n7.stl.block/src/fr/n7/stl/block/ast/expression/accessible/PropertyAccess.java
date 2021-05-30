package fr.n7.stl.block.ast.expression.accessible;

import fr.n7.stl.block.ast.expression.Expression;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;

public class PropertyAccess implements Expression {

  public PropertyAccess(Expression expression, String etiquette) {
  }

  @Override
  public boolean collect(HierarchicalScope<Declaration> _scope) {
    throw new RuntimeException("Unimplemented");
  }

  @Override
  public boolean resolve(HierarchicalScope<Declaration> _scope) {
    throw new RuntimeException("Unimplemented");
  }

  @Override
  public Type getType() {
    throw new RuntimeException("Unimplemented");
  }

  @Override
  public Fragment getCode(TAMFactory _factory) {
    throw new RuntimeException("Unimplemented");
  }

}

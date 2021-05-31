package fr.n7.stl.block.ast.expression.accessible;

import fr.n7.stl.block.ast.expression.Expression;
import fr.n7.stl.block.ast.expression.allocation.ClassInstanciation;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

public class PropertyAccess implements Expression {

  /**
   * Expression sur laquelle on essaye d'accéder à une propriété"
   */
  Expression expression;

  String etiquette;

  public PropertyAccess(Expression expression, String etiquette) {
    this.expression = expression;
    this.etiquette = etiquette;
  }

  @Override
  public boolean collect(HierarchicalScope<Declaration> _scope) {
    if (this.expression.collect(_scope)) {
      return true;
    } else {
      Logger.error("PropertyAccess: Impossible de collect");
      return false;
    }
  }

  @Override
  public boolean resolve(HierarchicalScope<Declaration> _scope) {
    if (this.expression.resolve(_scope)) {
      //TODO : verifier
      if (this.expression instanceof ClassInstanciation) {
        if (((ClassInstanciation) this.expression).containsEtiquette(etiquette)) {
          return true;
        }
      }
    }
    Logger.error("PropertyAccess: Le resolve passe pas.");
    return false;
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

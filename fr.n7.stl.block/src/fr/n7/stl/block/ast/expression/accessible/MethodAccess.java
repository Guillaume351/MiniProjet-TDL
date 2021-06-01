package fr.n7.stl.block.ast.expression.accessible;

import fr.n7.stl.block.ast.expression.Expression;
import fr.n7.stl.block.ast.expression.allocation.ClassInstanciation;
import fr.n7.stl.block.ast.instruction.Instruction;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

import java.util.List;

public class MethodAccess implements Expression, Instruction {

  Expression affectable;

  String etiquette;

  List<Expression> parametres;

  boolean ignoreReturnValue;

  public MethodAccess(Expression expression, String etiquette, List<Expression> parametres) {
    this(expression, etiquette, parametres, false);
  }

  public MethodAccess(Expression expression, String etiquette, List<Expression> parametres, boolean ignoreReturnValue) {
    // TODO : Attention c'est aussi une instruction !
    // if ignoreReturnValue is true, we pop the value resulting from the call,
    // keeping only the side effects.
    this.affectable = expression;
    this.etiquette = etiquette;
    this.parametres = parametres;
    this.ignoreReturnValue = ignoreReturnValue;
  }

  @Override
  public boolean collect(HierarchicalScope<Declaration> _scope) {
    if (this.affectable.collect(_scope) && this.parametres.stream().allMatch(param -> param.collect(_scope))) {
      return true;
    } else {
      Logger.error("MethodAccess: collect impossible");
      return false;
    }
  }

  @Override
  public boolean resolve(HierarchicalScope<Declaration> _scope) {
    if (this.affectable.resolve(_scope)) {
      //TODO : verifier
      if (this.affectable instanceof ClassInstanciation) {
        if (((ClassInstanciation) this.affectable).containsMethod(etiquette, parametres)) {
          return true;
        }
      } else {
        Logger.error("MethodAccess: Il y a autre chose à gérer que ClassInstanciation");
      }
    }
    Logger.error("MethodAccess: Le resolve passe pas.");
    return false;
  }

  @Override
  public Type getType() {
    //TODO : verifier que les types des parametres reels collent avec la signature de la methode
    throw new RuntimeException("Unimplemented");
  }

  @Override
  public Fragment getCode(TAMFactory _factory) {
    throw new RuntimeException("Unimplemented");
  }

  @Override
  public boolean checkType() {
    throw new RuntimeException("Unimplemented");
  }

  @Override
  public int allocateMemory(Register _register, int _offset) {
    throw new RuntimeException("Unimplemented");
  }

}

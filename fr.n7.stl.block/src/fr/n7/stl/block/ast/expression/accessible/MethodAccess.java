package fr.n7.stl.block.ast.expression.accessible;

import java.util.List;

import fr.n7.stl.block.ast.expression.Expression;
import fr.n7.stl.block.ast.instruction.Instruction;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;

public class MethodAccess implements Expression, Instruction {

  public MethodAccess(Expression expression, String etiquette, List<Expression> parametres) {
    this(expression, etiquette, parametres, false);
  }

  public MethodAccess(Expression expression, String etiquette, List<Expression> parametres, Boolean ignoreReturnValue) {
    // TODO : Attention c'est aussi une instruction !
    // if ignoreReturnValue is true, we pop the value resulting from the call,
    // keeping only the side effects.
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

  @Override
  public boolean checkType() {
    throw new RuntimeException("Unimplemented");
  }

  @Override
  public int allocateMemory(Register _register, int _offset) {
    throw new RuntimeException("Unimplemented");
  }

}

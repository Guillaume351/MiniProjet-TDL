package fr.n7.stl.block.ast.instruction.declaration.minijava;

import java.util.ArrayList;
import java.util.List;

import fr.n7.stl.block.ast.Block;
import fr.n7.stl.block.ast.instruction.Instruction;
import fr.n7.stl.block.ast.instruction.declaration.AbstractDeclarationElement;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.type.AtomicType;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;

public class ProgramDeclaration extends AbstractDeclarationElement implements Instruction, Declaration {

  private final Block main;

  private final List<ClassDeclaration> declarations = new ArrayList<>();

  public ProgramDeclaration(Block main) {
    this.main = main;
  }

  public void add(ClassDeclaration declaration) {
    this.declarations.add(declaration);
  }

  @Override
  public String getName() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Type getType() {
    return AtomicType.VoidType;
  }

  @Override
  public boolean collect(HierarchicalScope<Declaration> _scope) {
    return main.collect(_scope) && declarations.stream().allMatch(declaration -> declaration.collect(_scope));
  }

  @Override
  public boolean resolve(HierarchicalScope<Declaration> _scope) {
    return main.resolve(_scope) && declarations.stream().allMatch(declaration -> declaration.resolve(_scope));
  }

  @Override
  public boolean checkType() {
    return main.checkType() && declarations.stream().allMatch(ClassDeclaration::checkType);
  }

  @Override
  public int allocateMemory(Register _register, int _offset) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public Fragment getCode(TAMFactory _factory) {
    throw new RuntimeException("Unimplemented");
  }

}

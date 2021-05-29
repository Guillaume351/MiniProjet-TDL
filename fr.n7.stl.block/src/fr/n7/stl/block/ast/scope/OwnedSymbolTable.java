package fr.n7.stl.block.ast.scope;

/**
 * Represent a symbol table owned by a class or a function.
 */
public class OwnedSymbolTable extends SymbolTable implements OwnedHierarchicalScope<Declaration> {
  private Declaration owner;

  public OwnedSymbolTable(Scope<Declaration> context, Declaration owner) {
    super(context);
    this.owner = owner;
  }

  public Declaration getOwner() {
    return owner;
  }
}

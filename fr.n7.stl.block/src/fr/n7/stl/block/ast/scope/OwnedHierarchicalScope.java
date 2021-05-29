package fr.n7.stl.block.ast.scope;

public interface OwnedHierarchicalScope<D extends Declaration> extends HierarchicalScope<D> {

  /**
   * Get the owner of the scope.
   *
   * @return The declaration of the owner
   */
  public Declaration getOwner();

}

package fr.n7.stl.block.ast.instruction.declaration.minijava;

import fr.n7.stl.block.ast.expression.Expression;
import fr.n7.stl.block.ast.scope.AccessRight;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;
import fr.n7.stl.util.Logger;

public class AttributeDeclarationElement extends ClassElement {

    /**
     * AST node for the type of the declared variable.
     */
    protected Type type;

    /**
     * AST node for the initial value of the declared variable.
     */
    protected Expression value;

    /**
     * Est-ce que l'attribut est final
     */
    boolean isFinal;

    /**
     * Expression avec valeur par defaut
     *
     * @param value
     * @param isFinal
     */
    public AttributeDeclarationElement(String identifiant, Expression value, Type type, boolean isFinal) {
        super(identifiant);
        this.value = value;
        this.type = type;
        this.isFinal = isFinal;
    }

    /**
     * Attribut sans expression de valeur par defaut
     *
     * @param isFinal
     */
    public AttributeDeclarationElement(String identifiant, Type type, boolean isFinal) {
        super(identifiant);
        this.type = type;
        this.isFinal = isFinal;
    }

    public String toString(){
        boolean modifiedAccess = this.accessRight != AccessRight.NOMODIFIER;
        return (modifiedAccess ? this.accessRight : "") + " " + (this.isFinal ? "final " : "") + this.name + " = " + this.value + "\n";    
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public Type getType() {
        return this.type;
    }

    @Override
    public boolean collect(HierarchicalScope<Declaration> _scope) {
        if (!super.collect(_scope))
            return false;
        _scope.register(this);
        if (this.value != null && !this.value.collect(_scope)) {
            Logger.error("AttributeDeclarationElement: cannot collect initial value");
            return false;
        }
        return true;
    }

    @Override
    public boolean resolve(HierarchicalScope<Declaration> _scope) {
        if (this.value != null && !this.value.resolve(_scope)) {
            Logger.error("AttributeDeclarationElement: cannot resolve initial value");
            return false;
        }
        return true;
    }

    @Override
    public boolean checkType() {
        if (this.value != null) {
            if (this.value.getType().compatibleWith(this.getType())) {
                return true;
            } else {
                Logger.error("AttributeDeclarationElement : Le type ne colle pas !");
            }
        }
        return true;
    }

    @Override
    public int allocateMemory(Register _register, int _offset) {
        return 0;
    }

    @Override
    public Fragment getCode(TAMFactory _factory) {
        return null;
    }
}

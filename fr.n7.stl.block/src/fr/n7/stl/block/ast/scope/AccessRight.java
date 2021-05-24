package fr.n7.stl.block.ast.scope;

public class AccessRight {

    public static final int PRIVATE_RIGHT = 0;
    public static final int NOMODIFIER_RIGHT = 1;
    public static final int PROTECTED_RIGHT = 2;
    public static final int PUBLIC_RIGHT = 3;

    int modifierValue;

    public AccessRight(int modifierValue) {
        this.modifierValue = modifierValue;
    }

    public int getModifierValue() {
        return modifierValue;
    }
}

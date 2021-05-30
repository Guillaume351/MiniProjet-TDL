package fr.n7.stl.block.ast.instruction.declaration;

import java.util.List;

import fr.n7.stl.block.ast.instruction.Instruction;
import fr.n7.stl.block.ast.scope.Declaration;
import fr.n7.stl.block.ast.scope.HierarchicalScope;
import fr.n7.stl.block.ast.type.Type;
import fr.n7.stl.tam.ast.Fragment;
import fr.n7.stl.tam.ast.Register;
import fr.n7.stl.tam.ast.TAMFactory;

public class Signature implements Instruction, Declaration {

    /**
     * Name of the signature
     */
    protected String name;

    /**
     * AST node for the returned type of the signature
     */
    protected Type type;

    /**
     * List of AST nodes for the formal parameters of the signature
     */
    protected List<ParameterDeclaration> parameters;

    /**
     * @return the parameters
     */
    public List<ParameterDeclaration> getParameters() {
        return parameters;
    }

    public Signature(String _name, Type _type, List<ParameterDeclaration> _parameters) {
        this.name = _name;
        this.type = _type;
        this.parameters = _parameters;
    }

    @Override
    public boolean collect(HierarchicalScope<Declaration> _scope) {
        return this.parameters.stream().allMatch(param -> param.collect(_scope));
    }

    @Override
    public boolean resolve(HierarchicalScope<Declaration> _scope) {
        return false;
    }

    @Override
    public boolean checkType() {
        return false;
    }

    @Override
    public int allocateMemory(Register _register, int _offset) {
        return 0;
    }

    @Override
    public Fragment getCode(TAMFactory _factory) {
        return null;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Type getType() {
        return this.type;
    }

}

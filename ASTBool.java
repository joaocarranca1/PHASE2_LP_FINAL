public class ASTBool implements ASTNode {
    private final boolean value;

    public ASTBool(boolean value) {
        this.value = value;
    }

    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterError {
        return new VBool(value);
    }

    @Override
    public ASTType typeCheck(Environment<ASTType> env) throws TypeCheckError {
        return new ASTTBool();
    }

    @Override
    public String toStr() {
        return Boolean.toString(value);
    }
}

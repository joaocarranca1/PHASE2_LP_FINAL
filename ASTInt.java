class ASTInt implements ASTNode {
    private final int v;

    ASTInt(int v0) {
        v = v0;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        return new VInt(v);
    }

    @Override
    public ASTType typeCheck(Environment<ASTType> env) throws TypeCheckError {
        return new ASTTInt();
    }

    @Override
    public String toStr() {
        return Integer.toString(v);
    }
}
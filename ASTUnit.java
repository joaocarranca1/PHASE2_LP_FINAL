public class ASTUnit implements ASTNode {
    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterError {
        return VUnit.INSTANCE;
    }

    @Override
    public ASTType typeCheck(Environment<ASTType> env) throws TypeCheckError {
        return new ASTTUnit();
    }

    @Override
    public String toStr() {
        return "()";
    }
}
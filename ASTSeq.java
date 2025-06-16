public class ASTSeq implements ASTNode {
    private final ASTNode first;
    private final ASTNode second;

    public ASTSeq(ASTNode first, ASTNode second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterError {
        first.eval(env);
        return second.eval(env);
    }

    @Override
    public ASTType typeCheck(Environment<ASTType> env) throws TypeCheckError {
       
        first.typeCheck(env);
              
        return second.typeCheck(env);
    }

    @Override
    public String toStr() {
        return "(" + first.toStr() + " ; " + second.toStr() + ")";
    }
}




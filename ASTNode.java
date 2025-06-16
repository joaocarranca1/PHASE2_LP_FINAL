
public interface ASTNode {
    ASTType typeCheck(Environment<ASTType> env) throws TypeCheckError;

    IValue eval(Environment<IValue> e) throws InterpreterError;

    String toStr();
	
}


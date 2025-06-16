public class ASTString implements ASTNode {
    private String value;
    
    public ASTString(String value) {
        this.value = value;
    }
    
    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterError {
        return new VString(value);
    }
    
    @Override
    public ASTType typeCheck(Environment<ASTType> env) throws TypeCheckError {
        return new ASTTString();
    }
    
    @Override
    public String toStr() {
        return "\"" + value + "\"";
    }
}
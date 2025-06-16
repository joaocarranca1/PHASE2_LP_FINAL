public class ASTNil implements ASTNode {
    public ASTNil() {}
  
    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterError {
      return new VList();
    }

    @Override
    public ASTType typeCheck(Environment<ASTType> env) throws TypeCheckError {
        return new ASTTList(new ASTTUnit());
    }
  
    @Override
    public String toStr() {
      return "nil";
    }
  }
  
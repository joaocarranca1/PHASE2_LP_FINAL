public class ASTTInt implements ASTType {

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ASTTInt;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public ASTType typeCheck(Environment<ASTType> env) throws TypeCheckError {
        return this;
    }

    public String toStr() {
        return "int";
    }
}




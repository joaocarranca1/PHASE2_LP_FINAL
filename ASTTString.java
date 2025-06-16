public class ASTTString implements ASTType {

    public ASTTString() {}

    @Override
    public ASTType typeCheck(Environment<ASTType> env) throws TypeCheckError {
        return this;
    }

    public String toStr() {
        return "string";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ASTTString;
    }

    @Override
    public int hashCode() {
        return 3;
    }

}

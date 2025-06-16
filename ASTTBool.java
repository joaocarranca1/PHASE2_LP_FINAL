public class ASTTBool implements ASTType {

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ASTTBool;
    }

    @Override
    public int hashCode() {
        return 2;
    }

    @Override
    public ASTType typeCheck(Environment<ASTType> env) throws TypeCheckError {
        return this;
    }

    public String toStr() {
        return "bool";
    }
}

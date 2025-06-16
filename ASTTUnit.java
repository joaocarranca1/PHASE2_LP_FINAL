class ASTTUnit implements ASTType {
        
    @Override
    public ASTType typeCheck(Environment<ASTType> env) throws TypeCheckError {
        return this;
    }
    
    public String toStr() {
        return "()";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ASTTUnit;
    }

    @Override
    public int hashCode() {
        return 4;
    }
}
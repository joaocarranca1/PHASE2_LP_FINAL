public interface ASTType  {
    ASTType typeCheck(Environment<ASTType> env) throws TypeCheckError;
    String toStr();
    
   
    default boolean isSubtypeOf(ASTType other) {
        return this.equals(other); // Default: only exact matches
    }
}



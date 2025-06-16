public class ASTId implements ASTNode {

    private final String id;

    public ASTId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterError {
        return env.find(id);
    }

    @Override
    public ASTType typeCheck(Environment<ASTType> env) throws TypeCheckError {
        try {
            ASTType type = env.find(id);

            
            return resolveType(type, env);

        } catch (InterpreterError e) {
            throw new TypeCheckError("Error resolving identifier " + id + " in current scope: " + e.getMessage());
        }
    }

    
    private ASTType resolveType(ASTType t, Environment<ASTType> env) throws TypeCheckError {
        if (t instanceof ASTTId) {
            String alias = ((ASTTId) t).getId();
            try {
                ASTType resolved = env.find(alias);
                return resolveType(resolved, env);
            } catch (InterpreterError e) {
                throw new TypeCheckError("Unknown type alias: " + alias);
            }
        } else if (t instanceof ASTTRef) {
            ASTTRef refType = (ASTTRef) t;
            ASTType resolvedInner = resolveType(refType.getType(), env);
            return new ASTTRef(resolvedInner);
        } else if (t instanceof ASTTArrow) {
            ASTTArrow arr = (ASTTArrow) t;
            return new ASTTArrow(
                resolveType(arr.getParamType(), env),
                resolveType(arr.getReturnType(), env)
            );
        } else if (t instanceof ASTTList) {
            ASTTList listType = (ASTTList) t;
            ASTType resolvedElement = resolveType(listType.getElementType(), env);
            return new ASTTList(resolvedElement);
        } else {
            return t;
        }
    }

    @Override
    public String toStr() {
        return id; 
    }
}

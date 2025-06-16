public class ASTDeref implements ASTNode {
    private final ASTNode expr;

    public ASTDeref(ASTNode expr) {
        this.expr = expr;
    }

    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterError {
        IValue ref = expr.eval(env);
        if (!(ref instanceof VBox)) {
            throw new InterpreterError("Attempted to dereference a non-box");
        }
        return ((VBox) ref).get(); 
    }

    @Override
    public ASTType typeCheck(Environment<ASTType> env) throws TypeCheckError {
        ASTType exprType = resolveType(expr.typeCheck(env), env); 
        if (!(exprType instanceof ASTTRef)) {
            throw new TypeCheckError("Dereference requires a reference type, found: " + exprType.toStr());
        }
        return ((ASTTRef) exprType).getType(); 
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
        return "!" + expr.toStr(); 
    }
}


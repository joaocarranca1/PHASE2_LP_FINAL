public class ASTNot implements ASTNode {
    private final ASTNode expr;

    public ASTNot(ASTNode expr) {
        this.expr = expr;
    }

    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterError {
        IValue val = expr.eval(env);
        if (!(val instanceof VBool)) {
            throw new InterpreterError("Expected boolean in NOT, got: " + val.toStr());
        }
        boolean result = !((VBool) val).getValue();
        return new VBool(result);
    }

    @Override
    public ASTType typeCheck(Environment<ASTType> env) throws TypeCheckError {
        ASTType exprType = resolveType(expr.typeCheck(env), env);
        if (!(exprType instanceof ASTTBool)) {
            throw new TypeCheckError("Expected type 'bool' in NOT, got: " + exprType.toStr());
        }
        return new ASTTBool();
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
        return "(~" + expr.toStr() + ")";
    }
}

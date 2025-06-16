public class ASTNeg implements ASTNode {

    private final ASTNode exp;

    public ASTNeg(ASTNode exp) {
        this.exp = exp;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError { 
        IValue v0 = exp.eval(e); 
        if (v0 instanceof VInt) { 
            return new VInt(-((VInt) v0).getval()); 
        } else { 
            throw new InterpreterError("Illegal types to neg operator"); 
        }
    }

    @Override
    public ASTType typeCheck(Environment<ASTType> env) throws TypeCheckError {
        ASTType expType = resolveType(exp.typeCheck(env), env); // Resolve type aliases
        if (!(expType instanceof ASTTInt)) {
            throw new TypeCheckError("Expected type 'int' for negation, found: " + expType.toStr());
        }
        return new ASTTInt();
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
        return "(-" + exp.toStr() + ")";
    }
}


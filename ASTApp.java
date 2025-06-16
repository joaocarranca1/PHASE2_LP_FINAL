import java.util.List;

public class ASTApp implements ASTNode {
    private final ASTNode fnExpr; 
    private final List<ASTNode> argExprs;

    public ASTApp(ASTNode fnExpr, List<ASTNode> argExprs) {
        this.fnExpr = fnExpr;
        this.argExprs = argExprs;
    }

    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterError {
        IValue fnVal = fnExpr.eval(env);
        if (!(fnVal instanceof VClosure)) {
            throw new InterpreterError("Not a function: " + fnVal.toStr());
        }
        
      
        IValue currentValue = fnVal;
        for (ASTNode argExpr : argExprs) {
            if (!(currentValue instanceof VClosure)) {
                throw new InterpreterError("Too many arguments for function");
            }
            
            VClosure closure = (VClosure) currentValue;
            IValue argValue = argExpr.eval(env);
            
           
            Environment<IValue> newEnv = closure.getEnv().beginScope();
            List<String> params = closure.getParams();
            int argsBound = closure.getArgsBound();
            
           
            if (argsBound >= params.size()) {
                throw new InterpreterError("Too many arguments for function");
            }
            String currentParam = params.get(argsBound);
            newEnv.assoc(currentParam, argValue);
            
           
            currentValue = closure.apply(List.of(argValue));
        }
        
        return currentValue;
    }

    @Override
    public ASTType typeCheck(Environment<ASTType> env) throws TypeCheckError {
        try {
            ASTType currentType = resolveType(fnExpr.typeCheck(env), env);

           

            for (ASTNode argExpr : argExprs) {
                currentType = resolveType(currentType, env); 
                if (!(currentType instanceof ASTTArrow)) {
                    throw new TypeCheckError(
                        "Expected a function type, found: " + currentType.toStr() +
                        " while checking " + fnExpr.toStr());
                }

                ASTTArrow arrow = (ASTTArrow) currentType;
                ASTType expectedType = resolveType(arrow.getParamType(), env);
                ASTType actualType = resolveType(argExpr.typeCheck(env), env);

              

                if (!actualType.equals(expectedType)) {
                    throw new TypeCheckError(
                        "Type mismatch in application of " + fnExpr.toStr() +
                        ": expected " + expectedType.toStr() +
                        ", found " + actualType.toStr());
                }

                currentType = arrow.getReturnType();
            }

            return resolveType(currentType, env);
        } catch (TypeCheckError e) {
            throw new TypeCheckError(
                "Error in function application " + this.toStr() + ": " + e.getMessage());
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
            ASTType resolvedInner = resolveType(refType.getInnerType(), env);
            return new ASTTRef(resolvedInner);
        } else if (t instanceof ASTTArrow) {
            ASTTArrow arr = (ASTTArrow) t;
            return new ASTTArrow(
                resolveType(arr.getParamType(), env),
                resolveType(arr.getReturnType(), env)
            );
        } else {
            return t;
        }
    }

    @Override
    public String toStr() {
        StringBuilder sb = new StringBuilder(fnExpr.toStr() + "(");
        for (int i = 0; i < argExprs.size(); i++) {
            sb.append(argExprs.get(i).toStr());
            if (i < argExprs.size() - 1) sb.append(", ");
        }
        sb.append(")");
        return sb.toString();
    }
}

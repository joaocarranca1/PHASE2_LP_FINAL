import java.util.List;

public class ASTFn implements ASTNode {
    private final List<String> params;
    private final List<ASTType> paramTypes;
    private final ASTNode body;

    public ASTFn(List<String> params, List<ASTType> paramTypes, ASTNode body) {
        if (params.size() != paramTypes.size()) {
            throw new IllegalArgumentException("Number of parameters must match number of types");
        }
        if (params.isEmpty()) {
            throw new IllegalArgumentException("Function must have at least one parameter");
        }
        this.params = params;
        this.paramTypes = paramTypes;
        this.body = body;
    }

    @Override
    public IValue eval(Environment<IValue> env) {
        return new VClosure(env, params, body, paramTypes);
    }

    @Override
    public ASTType typeCheck(Environment<ASTType> env) throws TypeCheckError {
        Environment<ASTType> extendedEnv = env.beginScope();

        try {
            for (int i = 0; i < params.size(); i++) {
                if (paramTypes.get(i) == null) {
                    throw new TypeCheckError("Parameter " + params.get(i) + " has no type");
                }
                ASTType paramType = resolveType(paramTypes.get(i), env); // resolve alias
                extendedEnv.assoc(params.get(i), paramType);
            }

           
            ASTType bodyType = body.typeCheck(extendedEnv);

            
            ASTType currentType = bodyType;
            for (int i = params.size() - 1; i >= 0; i--) {
                ASTType paramType = resolveType(paramTypes.get(i), env); 
                currentType = new ASTTArrow(paramType, currentType);
            }

            return currentType;
        } catch (InterpreterError e) {
            throw new TypeCheckError("Error in function: " + e.getMessage());
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
        StringBuilder sb = new StringBuilder();
        
        
        if (params.size() == 1) {
            sb.append("fn ").append(params.get(0))
              .append(":").append(paramTypes.get(0).toStr());
        } else {
           
            sb.append("fn ");
            for (int i = 0; i < params.size(); i++) {
                sb.append(params.get(i))
                  .append(":").append(paramTypes.get(i).toStr());
                if (i < params.size() - 1) sb.append(", ");
            }
        }
        sb.append(" => ").append(body.toStr());
        return sb.toString();
    }
}

import java.util.List;

public class ASTLet implements ASTNode {
    private final List<Bind> decls; 
    private final ASTNode body;

    public ASTLet(List<Bind> decls, ASTNode body) {
        this.decls = decls;
        this.body = body;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        
        
        
        for (Bind binding : decls) {
            IValue value = binding.getExp().eval(e); 
            e.assoc(binding.getId(), value); 
        }

        return body.eval(e); 
    }

    @Override
    public ASTType typeCheck(Environment<ASTType> env) throws TypeCheckError {
     
        
        for (Bind binding : decls) {
            ASTType declaredType = binding.getType();
            if (declaredType != null) {
                env.preDeclareBind(binding.getId(), declaredType);
                        }
        }

        
        for (Bind binding : decls) {
            try {
                

                ASTType declaredType = binding.getType(); 
              
                ASTType bindingType = resolveType(binding.getExp().typeCheck(env), env); 
                

                if (declaredType != null) {
                    ASTType resolvedDeclaredType = resolveType(declaredType, env); 
                    if (!bindingType.isSubtypeOf(resolvedDeclaredType)) {
                        throw new TypeCheckError("Type mismatch for " + binding.getId() +
                            ": declared " + resolvedDeclaredType.toStr() +
                            " but got " + bindingType.toStr() + 
                            " (not a subtype)");
                    }
                  
                    env.assoc(binding.getId(), resolvedDeclaredType);
                } else {
                   
                    env.assoc(binding.getId(), bindingType);
                }

              

            } catch (InterpreterError e) {
                throw new TypeCheckError("Error while type checking binding: " + binding.getId() + " - " + e.getMessage());
            }
        }

       
        
        return body.typeCheck(env);
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
        StringBuilder sb = new StringBuilder("let ");
        for (Bind binding : decls) {
            sb.append(binding.getId()).append(" = ").append(binding.getExp().toStr()).append("; ");
        }
        sb.append("in ").append(body.toStr());
        return sb.toString();
    }
}


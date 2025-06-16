public class ASTWhile implements ASTNode {
    private final ASTNode condition;
    private final ASTNode body;

    public ASTWhile(ASTNode condition, ASTNode body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterError {
        IValue condVal = condition.eval(env);
        if (condVal == null) {
            throw new InterpreterError("Condition evaluation returned null.");
        }
        if (!(condVal instanceof VBool)) {
            throw new InterpreterError("Condition must evaluate to a boolean.");
        }

        while (((VBool) condVal).getValue()) {
            body.eval(env);
            condVal = condition.eval(env);
            if (!(condVal instanceof VBool)) {
                throw new InterpreterError("Condition must evaluate to a boolean.");
            }
        }
        return null;
    }

    @Override
    public ASTType typeCheck(Environment<ASTType> env) throws TypeCheckError {
        ASTType condType = resolveType(condition.typeCheck(env), env);
        if (!(condType instanceof ASTTBool)) {
            throw new TypeCheckError("Condition in 'while' must be of type 'bool', found: " + condType.toStr());
        }

        body.typeCheck(env); 
        return new ASTTUnit(); 
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
        return "while (" + condition.toStr() + ") { " + body.toStr() + " }";
    }
}


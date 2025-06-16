public class ASTIf implements ASTNode {
    private final ASTNode condition;
    private final ASTNode thenBranch;
    private final ASTNode elseBranch;

    public ASTIf(ASTNode condition, ASTNode thenBranch, ASTNode elseBranch) {
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterError {
        IValue condVal = condition.eval(env);
        if (!(condVal instanceof VBool)) {
            throw new InterpreterError("Condition must evaluate to a boolean.");
        }
        if (((VBool) condVal).getValue()) {
            return thenBranch.eval(env);
        } else {
            return elseBranch.eval(env);
        }
    }

    @Override
    public String toString() {
        return "if (" + condition + ") { " + thenBranch + " } else { " + elseBranch + " }";
    }

    @Override
    public ASTType typeCheck(Environment<ASTType> env) throws TypeCheckError {
        ASTType condType = resolveType(condition.typeCheck(env), env);
        if (!(condType instanceof ASTTBool)) {
            throw new TypeCheckError("Condition in 'if' must be of type 'bool', found: " + condType.toStr());
        }

        ASTType thenType = resolveType(thenBranch.typeCheck(env), env);
        ASTType elseType = resolveType(elseBranch.typeCheck(env), env);

        if (!thenType.equals(elseType)) {
            throw new TypeCheckError("Branches of 'if' must have the same type, found: " + thenType.toStr() + " and " + elseType.toStr());
        }

        return thenType;
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
        return "if (" + condition.toStr() + ") { " + thenBranch.toStr() + " } else { " + elseBranch.toStr() + " }";
    }

}

public class ASTMatch implements ASTNode {
    private final ASTNode expr;
    private final String nilCaseName;
    private final ASTNode nilCaseExpr;
    private final String headName;
    private final String tailName;
    private final ASTNode consCaseExpr;

    public ASTMatch(ASTNode expr, String nilCaseName, ASTNode nilCaseExpr,
                    String headName, String tailName, ASTNode consCaseExpr) {
        if (expr == null || nilCaseExpr == null || headName == null || tailName == null || consCaseExpr == null) {
            throw new IllegalArgumentException("Arguments to ASTMatch cannot be null");
        }
        this.expr = expr;
        this.nilCaseName = nilCaseName;
        this.nilCaseExpr = nilCaseExpr;
        this.headName = headName;
        this.tailName = tailName;
        this.consCaseExpr = consCaseExpr;
    }

    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterError {
        IValue val = expr.eval(env);
        if (val instanceof VLazyList) {
            val = ((VLazyList) val).force();
        }
        if (val == null) {
            throw new RuntimeException("Match expression evaluated to null");
        }
        if (!(val instanceof VList)) {
            throw new RuntimeException("Match expression must evaluate to a list, but got: " + val.getClass().getSimpleName());
        }

        VList list = (VList) val;
        if (list.isEmpty()) {
            Environment<IValue> newEnv = new Environment<>(env);
            newEnv.assoc(nilCaseName, val);
            return nilCaseExpr.eval(newEnv);
        } else {
            Environment<IValue> newEnv = new Environment<>(env);
            IValue head = list.getHead();
            VList tail = list.getTail();

            if (head == null) {
                throw new RuntimeException("Head of the list is null in match expression");
            }
            if (tail == null) {
                throw new RuntimeException("Tail of the list is null in match expression");
            }

            newEnv.assoc(headName, head);
            newEnv.assoc(tailName, tail);
            return consCaseExpr.eval(newEnv);
        }
    }

    @Override
    public ASTType typeCheck(Environment<ASTType> env) throws TypeCheckError {
        try {
            ASTType exprType = resolveType(expr.typeCheck(env), env);
            if (!(exprType instanceof ASTTList)) {
                throw new TypeCheckError("Match expression must evaluate to a list, but got: " + exprType.toStr());
            }

            ASTType elementType = resolveType(((ASTTList) exprType).getElementType(), env);

            Environment<ASTType> nilEnv = env.beginScope();
            nilEnv.assoc(nilCaseName, exprType);
            ASTType nilCaseType = resolveType(nilCaseExpr.typeCheck(nilEnv), env);

            Environment<ASTType> consEnv = env.beginScope();
            consEnv.assoc(headName, elementType);
            consEnv.assoc(tailName, exprType);
            ASTType consCaseType = resolveType(consCaseExpr.typeCheck(consEnv), env);

            if (!nilCaseType.equals(consCaseType)) {
                throw new TypeCheckError("Type mismatch between nil case and cons case: " +
                                         nilCaseType.toStr() + " vs " + consCaseType.toStr());
            }

            return nilCaseType;
        } catch (InterpreterError e) {
            throw new TypeCheckError("Error while resolving types in match expression: " + e.getMessage());
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
        return "match " + expr.toStr() + " with nil => " + nilCaseExpr.toStr() +
               " | " + headName + " :: " + tailName + " => " + consCaseExpr.toStr();
    }
}


public class ASTCons implements ASTNode {
    private final ASTNode head;
    private final ASTNode tail;

    public ASTCons(ASTNode head, ASTNode tail) {
        this.head = head;
        this.tail = tail;
    }

    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterError {
        IValue hVal = head.eval(env);
        IValue tVal = tail.eval(env);
        
        if (tVal instanceof VLazyList) {
            tVal = ((VLazyList) tVal).force();
            System.err.println("Cons: tail was a lazy list, forced to " + tVal);
        }

        if (!(tVal instanceof VList)) {
            System.err.println("Cons error: tail evaluated to " + tail.getClass().getSimpleName() + " with value " + tail);

            throw new RuntimeException("Tail of cons must be a list");
        }
        return new VList(hVal, (VList) tVal);
    }

    @Override
    public ASTType typeCheck(Environment<ASTType> env) throws TypeCheckError {
        ASTType headType = resolveType(head.typeCheck(env), env);
        ASTType tailType = resolveType(tail.typeCheck(env), env);

        if (!(tailType instanceof ASTTList)) {
            throw new TypeCheckError("Tail of cons must be a list, but got: " + tailType.toString());
        }

        ASTType elementType = resolveType(((ASTTList) tailType).getElementType(), env);
        if (!headType.equals(elementType)) {
            throw new TypeCheckError("Head and tail types of '::' must match, found: " + headType.toStr() + " and " + elementType.toStr());
        }

        return new ASTTList(headType);
    }

    // Helper to resolve type aliases
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
        return "(" + (head != null ? head.toStr() : "null") + " :: " + (tail != null ? tail.toStr() : "null") + ")";
    }
}

public class ASTLazyCons implements ASTNode {
    private final ASTNode head;
    private final ASTNode tail;

    public ASTLazyCons(ASTNode head, ASTNode tail) {
        this.head = head;
        this.tail = tail;
    }

    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterError {
        return new VLazyList(() -> {
            try {
                IValue hVal = head.eval(env);
                if (hVal == null) {
                    throw new RuntimeException("Head evaluation returned null in lazy cons");
                }
                return hVal;
            } catch (InterpreterError e) {
                throw new RuntimeException("Error evaluating head in lazy cons", e);
            }
        }, () -> {
            try {
                IValue tVal = tail.eval(env);
                if (tVal instanceof VList) {
                    return (VList) tVal;
                }
                if (tVal == null) {
                    throw new RuntimeException("Tail evaluation returned null in lazy cons");
                }
                return VLazyList.empty();
            } catch (InterpreterError e) {
                throw new RuntimeException("Error evaluating tail in lazy cons", e);
            }
        });
    }

    @Override
    public ASTType typeCheck(Environment<ASTType> env) throws TypeCheckError {
        ASTType headType = resolveType(head.typeCheck(env), env);
        ASTType tailType = resolveType(tail.typeCheck(env), env);

        if (!(tailType instanceof ASTTList)) {
            throw new TypeCheckError("Tail of lazy cons must be of type 'list', found: " + tailType.toStr());
        }

        ASTType elementType = resolveType(((ASTTList) tailType).getElementType(), env);
        if (!headType.equals(elementType)) {
            throw new TypeCheckError("Head type (" + headType.toStr() + ") does not match tail element type (" + elementType.toStr() + ")");
        }

        return new ASTTList(headType);
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
        return "(" + (head != null ? head.toStr() : "null") + " :? " + (tail != null ? tail.toStr() : "null") + ")";
    }
}

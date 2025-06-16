public class ASTNeq implements ASTNode {
    private final ASTNode lhs, rhs;

    public ASTNeq(ASTNode lhs, ASTNode rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterError {
        IValue v1 = lhs.eval(env);
        IValue v2 = rhs.eval(env);
        if (v1 instanceof VInt && v2 instanceof VInt) {
            return new VBool(((VInt) v1).getval() != ((VInt) v2).getval());
        }
        if (v1 instanceof VBool && v2 instanceof VBool) {
            return new VBool(((VBool) v1).getValue() != ((VBool) v2).getValue());
        }
        throw new InterpreterError("Invalid operands for !=");
    }

    @Override
    public ASTType typeCheck(Environment<ASTType> env) throws TypeCheckError {
        ASTType leftType = resolveType(lhs.typeCheck(env), env);
        ASTType rightType = resolveType(rhs.typeCheck(env), env);

        if (!leftType.equals(rightType)) {
            throw new TypeCheckError("Operands of '!=' must have the same type, found: " 
                + leftType.toStr() + " and " + rightType.toStr());
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
        return "(" + lhs.toStr() + " != " + rhs.toStr() + ")";
    }
}


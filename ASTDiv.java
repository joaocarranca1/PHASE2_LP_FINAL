public class ASTDiv implements ASTNode {

    private final ASTNode lhs, rhs; 

    public ASTDiv(ASTNode lhs, ASTNode rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue v1 = lhs.eval(e);
        IValue v2 = rhs.eval(e);
        if (v1 instanceof VInt && v2 instanceof VInt) {
            int i1 = ((VInt) v1).getval();
            int i2 = ((VInt) v2).getval();
            if (i2 == 0) {
                throw new InterpreterError("Division by zero");
            }
            return new VInt(i1 / i2);
        } else {
            throw new InterpreterError("Illegal types to / operator");
        }
    }

    @Override
    public ASTType typeCheck(Environment<ASTType> env) throws TypeCheckError {
        ASTType leftType = resolveType(lhs.typeCheck(env), env);
        ASTType rightType = resolveType(rhs.typeCheck(env), env);

        if (!(leftType instanceof ASTTInt) || !(rightType instanceof ASTTInt)) {
            throw new TypeCheckError("Operands of '/' must be of type 'int', found: " 
                + leftType.toStr() + " and " + rightType.toStr());
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
        return "(" + lhs.toStr() + " / " + rhs.toStr() + ")";
    }
}

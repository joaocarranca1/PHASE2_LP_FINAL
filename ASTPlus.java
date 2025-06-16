public class ASTPlus implements ASTNode {

    ASTNode lhs, rhs;

    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue v1 = lhs.eval(e);
        IValue v2 = rhs.eval(e);

        
        if (v1 instanceof VString || v2 instanceof VString) {
            String s1 = v1.toStr();
            String s2 = v2.toStr();
            return new VString(s1 + s2);
        }

        
        if (v1 instanceof VInt && v2 instanceof VInt) {
            int i1 = ((VInt) v1).getval();
            int i2 = ((VInt) v2).getval();
            return new VInt(i1 + i2); 
        }

        throw new InterpreterError("illegal types to + operator: " + v1.getClass().getSimpleName() + " and " + v2.getClass().getSimpleName());
    }

    public ASTPlus(ASTNode l, ASTNode r) {
        lhs = l;
        rhs = r;
    }

    @Override
    public ASTType typeCheck(Environment<ASTType> env) throws TypeCheckError {
        ASTType leftType = resolveType(lhs.typeCheck(env), env);
        ASTType rightType = resolveType(rhs.typeCheck(env), env);


        if (leftType instanceof ASTTArrow) {
            throw new TypeCheckError("Left operand of '+' is a function that needs to be applied first");
        }
        if (rightType instanceof ASTTArrow) {
            throw new TypeCheckError("Right operand of '+' is a function that needs to be applied first");
        }

        
        if (leftType instanceof ASTTString || rightType instanceof ASTTString) {
            return new ASTTString();
        }

        
        if (leftType instanceof ASTTInt && rightType instanceof ASTTInt) {
            return new ASTTInt();
        }

        throw new TypeCheckError("Operands of '+' must be of type 'int' or 'string', found: " + leftType.toStr() + " and " + rightType.toStr());
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
        return "(" + lhs.toStr() + " + " + rhs.toStr() + ")";
    }
}

public class ASTOr implements ASTNode {
    ASTNode left, right;

    public ASTOr(ASTNode left, ASTNode right) {
        this.left = left;
        this.right = right;
    }

    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue lv = left.eval(e);
        if (!(lv instanceof VBool)) throw new InterpreterError("Left side not boolean");
        if (((VBool)lv).getValue()) return new VBool(true);
        return right.eval(e);
    }

    @Override
    public ASTType typeCheck(Environment<ASTType> env) throws TypeCheckError {
       
        ASTType leftType = resolveType(left.typeCheck(env), env);
        if (!(leftType instanceof ASTTBool)) {
            throw new TypeCheckError("Left operand of '||' must be of type 'bool', found: " + leftType.toStr());
        }

        
        ASTType rightType = resolveType(right.typeCheck(env), env);
        if (!(rightType instanceof ASTTBool)) {
            throw new TypeCheckError("Right operand of '||' must be of type 'bool', found: " + rightType.toStr());
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
        return "(" + left.toStr() + " || " + right.toStr() + ")";
    }
}

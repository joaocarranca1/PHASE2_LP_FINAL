public class ASTFieldAccess implements ASTNode {
    private final ASTNode structExpr;
    private final String field;

    public ASTFieldAccess(ASTNode structExpr, String field) {
        this.structExpr = structExpr;
        this.field = field;
    }

    public ASTNode getStructExpr() {
        return structExpr;
    }
    
    public String getField() {
        return field;
    }

    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterError {
        IValue v = structExpr.eval(env);
        if (!(v instanceof VStruct)) {
            throw new InterpreterError("Field access on non-struct value");
        }
        VStruct vs = (VStruct) v;
        if (!vs.getFields().containsKey(field)) {
            throw new InterpreterError("Field #" + field + " not found in struct");
        }
        return vs.getFields().get(field);
    }

    @Override
    public ASTType typeCheck(Environment<ASTType> env) throws TypeCheckError {
        ASTType t = structExpr.typeCheck(env);
        if (!(t instanceof ASTTStruct)) {
            throw new TypeCheckError("Field access on non-struct type");
        }
        ASTTStruct ts = (ASTTStruct) t;
        if (!ts.getFields().hasField(field)) {
            throw new TypeCheckError("Field #" + field + " not found in struct type");
        }
        return ts.getFields().getFieldType(field);
    }

    @Override
    public String toStr() {
        return structExpr.toStr() + ".#" + field;
    }
}

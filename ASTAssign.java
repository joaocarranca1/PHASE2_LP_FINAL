public class ASTAssign implements ASTNode {
    private final ASTNode lhs;
    private final ASTNode rhs;

    public ASTAssign(ASTNode lhs, ASTNode rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterError {
        if (lhs instanceof ASTId) {
            String name = ((ASTId) lhs).getId();
            IValue ref = env.find(name);
            if (!(ref instanceof VBox)) {
                throw new InterpreterError("Assigned variable is not a box");
            }
            IValue val = rhs.eval(env);
            ((VBox) ref).set(val);
            return val;
        } else if (lhs instanceof ASTFieldAccess) {
            ASTFieldAccess fieldAccess = (ASTFieldAccess) lhs;
            IValue structVal = fieldAccess.getStructExpr().eval(env);
            if (!(structVal instanceof VStruct)) {
                throw new InterpreterError("Field access on non-struct value in assignment");
            }
            VStruct struct = (VStruct) structVal;
            String fieldName = fieldAccess.getField();
            
            if (!struct.getFields().containsKey(fieldName)) {
                throw new InterpreterError("Field #" + fieldName + " not found in struct");
            }
            
            IValue fieldRef = struct.getFields().get(fieldName);
            if (!(fieldRef instanceof VBox)) {
                throw new InterpreterError("Assigned field is not a box");
            }
            
            IValue val = rhs.eval(env);
            ((VBox) fieldRef).set(val);
            return val;
        } else {
            throw new InterpreterError("Left-hand side of := must be an identifier or field access");
        }
    }

    @Override
    public ASTType typeCheck(Environment<ASTType> env) throws TypeCheckError {
        if (lhs instanceof ASTId) {
            String name = ((ASTId) lhs).getId();
            try {
                ASTType lhsType = resolveType(env.find(name), env);
                if (!(lhsType instanceof ASTTRef)) {
                    throw new TypeCheckError("Assigned variable must be a reference type, found: " + lhsType.toStr());
                }
                ASTType rhsType = resolveType(rhs.typeCheck(env), env);
                ASTType refType = resolveType(((ASTTRef) lhsType).getType(), env);
                if (!rhsType.equals(refType)) {
                    throw new TypeCheckError("Type mismatch in assignment: expected " + refType.toStr() + ", found " + rhsType.toStr());
                }
                return new ASTTUnit();
            } catch (InterpreterError e) {
                throw new TypeCheckError("Error while resolving type for identifier: " + name + " - " + e.getMessage());
            }
        } else if (lhs instanceof ASTFieldAccess) {
            ASTFieldAccess fieldAccess = (ASTFieldAccess) lhs;
            ASTType structType = fieldAccess.getStructExpr().typeCheck(env);
            if (!(structType instanceof ASTTStruct)) {
                throw new TypeCheckError("Field access on non-struct type in assignment");
            }
            
            ASTTStruct structTType = (ASTTStruct) structType;
            String fieldName = fieldAccess.getField();
            
            if (!structTType.getFields().hasField(fieldName)) {
                throw new TypeCheckError("Field #" + fieldName + " not found in struct type");
            }
            
            ASTType fieldType = structTType.getFields().getFieldType(fieldName);
            if (!(fieldType instanceof ASTTRef)) {
                throw new TypeCheckError("Assigned field must be a reference type, found: " + fieldType.toStr());
            }
            
            ASTType rhsType = resolveType(rhs.typeCheck(env), env);
            ASTType refType = resolveType(((ASTTRef) fieldType).getType(), env);
            if (!rhsType.equals(refType)) {
                throw new TypeCheckError("Type mismatch in field assignment: expected " + refType.toStr() + ", found " + rhsType.toStr());
            }
            return new ASTTUnit();
        } else {
            throw new TypeCheckError("Left-hand side of := must be an identifier or field access");
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
        } else {
            return t;
        }
    }

    @Override
    public String toStr() {
        return "(" + lhs.toStr() + " := " + rhs.toStr() + ")";
    }
}


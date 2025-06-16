import java.util.List;
import java.util.ArrayList;

public class ASTTArrow implements ASTType {
    private final ASTType paramType; 
    private final ASTType returnType; 

    
    public ASTTArrow(ASTType paramType, ASTType returnType) {
        if (paramType == null || returnType == null) {
            throw new IllegalArgumentException("Parameter and return types cannot be null");
        }
        this.paramType = paramType;
        this.returnType = returnType;
    }

    
    public static ASTTArrow createUncurried(List<ASTType> paramTypes, ASTType returnType) {
        if (paramTypes == null || paramTypes.isEmpty()) {
            throw new IllegalArgumentException("Parameter types list cannot be null or empty");
        }
        if (returnType == null) {
            throw new IllegalArgumentException("Return type cannot be null");
        }

        
        ASTType current = returnType;
        for (int i = paramTypes.size() - 1; i >= 0; i--) {
            if (paramTypes.get(i) == null) {
                throw new IllegalArgumentException("Parameter type at position " + i + " cannot be null");
            }
            current = new ASTTArrow(paramTypes.get(i), current);
        }
        return (ASTTArrow) current;
    }

   
    public ASTType getParamType() {
        return paramType;
    }

   
    public ASTType getReturnType() {
        return returnType;
    }

    
    public List<ASTType> getParamTypes() {
        List<ASTType> types = new ArrayList<>();
        ASTType current = this;
        while (current instanceof ASTTArrow) {
            ASTTArrow arrow = (ASTTArrow) current;
            types.add(arrow.paramType);
            current = arrow.returnType;
        }
        return types;
    }

    @Override
    public ASTType typeCheck(Environment<ASTType> env) throws TypeCheckError {
        try {
            
            paramType.typeCheck(env);
           
            returnType.typeCheck(env);
            return this;
        } catch (Exception e) {
            throw new TypeCheckError("Error type checking arrow type: " + e.getMessage());
        }
    }

    @Override
    public String toStr() {
        if (returnType instanceof ASTTArrow) {
            
            return "(" + paramType.toStr() + " -> " + returnType.toStr() + ")";
        } else {
           
            return paramType.toStr() + " -> " + returnType.toStr();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ASTTArrow)) {
            return false;
        }
        ASTTArrow other = (ASTTArrow) obj;
        return paramType.equals(other.paramType) && returnType.equals(other.returnType);
    }

    @Override
    public int hashCode() {
        return 31 * paramType.hashCode() + returnType.hashCode();
    }
}


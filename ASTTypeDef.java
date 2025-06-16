import java.util.*;

public class ASTTypeDef implements ASTNode {
    private HashMap<String, ASTType> ltd; // Local type definitions
    private ASTNode body; // Body of the type definition scope

    public ASTTypeDef(HashMap<String, ASTType> ltdp, ASTNode b) {
        ltd = ltdp;
        body = b;
    }

    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterError {
        // Type definitions don't affect runtime evaluation directly
        // They are handled during type checking phase
        return body.eval(env);
    }

    @Override
    public ASTType typeCheck(Environment<ASTType> env) throws TypeCheckError {
      
        HashMap<String, ASTType> checkedTypes = new HashMap<>();
        
        for (Map.Entry<String, ASTType> entry : ltd.entrySet()) {
            try {
                
                ASTType checkedType = entry.getValue().typeCheck(env);
                checkedTypes.put(entry.getKey(), checkedType);
                
                
                env.assoc(entry.getKey(), checkedType);
            } catch (InterpreterError e) {
                throw new TypeCheckError("Error adding type definition " + entry.getKey() + ": " + e.getMessage());
            }
        }
        
        return body.typeCheck(env);
    }

    public String toStr() {
        StringBuilder sb = new StringBuilder("type { ");
        for (String typeName : ltd.keySet()) {
            sb.append(typeName).append(" = ").append(ltd.get(typeName).toStr()).append("; ");
        }
        sb.append("} in ").append(body.toStr());
        return sb.toString();
    }
}

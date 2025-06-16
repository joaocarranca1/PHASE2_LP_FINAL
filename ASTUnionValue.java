import java.util.Map;
import java.util.HashMap;
public class ASTUnionValue implements ASTNode {
    private final String label;
    private final ASTNode value;

    public ASTUnionValue(String label, ASTNode value) {
        if (label == null || value == null) {
            throw new IllegalArgumentException("Label and value cannot be null");
        }
        this.label = label;
        this.value = value;
    }

    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterError {
        IValue evaluatedValue = value.eval(env);
        return new VUnion(label, evaluatedValue);
    }

    @Override
    public ASTType typeCheck(Environment<ASTType> env) throws TypeCheckError {
       
        ASTType valueType = resolveType(value.typeCheck(env), env);
        
       
        
        HashMap<String, ASTType> variants = new HashMap<>();
        variants.put(label, valueType);
        return new ASTTUnion(variants);
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
        }
       
        return t;
    }

    @Override
    public String toStr() {
        return "#" + label + "(" + value.toStr() + ")";
    }
}

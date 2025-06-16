import java.util.Map;
import java.util.HashMap;

public class ASTStruct implements ASTNode {
    private final Map<String, ASTNode> fields;

    public ASTStruct(Map<String, ASTNode> fields) {
        this.fields = new HashMap<>(fields);
    }

    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterError {
        Map<String, IValue> evalFields = new HashMap<>();
        for (Map.Entry<String, ASTNode> entry : fields.entrySet()) {
            evalFields.put(entry.getKey(), entry.getValue().eval(env));
        }
        return new VStruct(evalFields);
    }

    @Override
    public ASTType typeCheck(Environment<ASTType> env) throws TypeCheckError {
        TypeBindList fieldTypes = new TypeBindList();
        for (Map.Entry<String, ASTNode> entry : fields.entrySet()) {
            fieldTypes.addField(entry.getKey(), entry.getValue().typeCheck(env));
        }
        return new ASTTStruct(fieldTypes);
    }

    @Override
    public String toStr() {
        StringBuilder sb = new StringBuilder("{ ");
        boolean first = true;
        for (Map.Entry<String, ASTNode> entry : fields.entrySet()) {
            if (!first) sb.append(", ");
            sb.append("#").append(entry.getKey()).append("=").append(entry.getValue().toStr());
            first = false;
        }
        sb.append(" }");
        return sb.toString();
    }
}

import java.util.Map;
import java.util.HashMap;

public class VStruct implements IValue {
    private final Map<String, IValue> fields;

    public VStruct(Map<String, IValue> fields) {
        this.fields = new HashMap<>(fields);
    }

    public Map<String, IValue> getFields() {
        return fields;
    }

    @Override
    public String toStr() {
        StringBuilder sb = new StringBuilder("{ ");
        boolean first = true;
        for (Map.Entry<String, IValue> entry : fields.entrySet()) {
            if (!first) sb.append(", ");
            sb.append("#").append(entry.getKey()).append("=").append(entry.getValue().toStr());
            first = false;
        }
        sb.append(" }");
        return sb.toString();
    }

    @Override
    public String toString() {
        return toStr();
    }
}

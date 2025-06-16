import java.util.*;

public class TypeBindList {

    private HashMap<String, ASTType> lbl;

    public TypeBindList(HashMap<String, ASTType> ll) {
        lbl = ll;
    }

   
    public TypeBindList() {
        lbl = new HashMap<>();
    }

    
    public HashMap<String, ASTType> getFields() {
        return lbl;
    }

    
    public void addField(String name, ASTType type) {
        lbl.put(name, type);
    }

   
    public ASTType getFieldType(String name) {
        return lbl.get(name);
    }

   
    public boolean hasField(String name) {
        return lbl.containsKey(name);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{ ");
        for (String field : lbl.keySet()) {
            sb.append(field).append(": ").append(lbl.get(field).toStr()).append(", ");
        }
        if (!lbl.isEmpty()) {
            sb.setLength(sb.length() - 2); 
        }
        sb.append(" }");
        return sb.toString();
    }
}
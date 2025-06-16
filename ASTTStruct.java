
import java.util.Map;
public class ASTTStruct implements ASTType {

    private TypeBindList ll;

    public ASTTStruct(TypeBindList llp) {
        ll = llp;
    }

    @Override
    public ASTType typeCheck(Environment<ASTType> env) throws TypeCheckError {
        TypeBindList checkedFields = new TypeBindList();
        for (String field : ll.getFields().keySet()) {
            ASTType fieldType = ll.getFields().get(field).typeCheck(env);
            checkedFields.addField(field, fieldType);
        }
        return new ASTTStruct(checkedFields);
    }

    public String toStr() {
        StringBuilder sb = new StringBuilder("struct { ");
        for (String field : ll.getFields().keySet()) {
            sb.append(field).append(": ").append(ll.getFields().get(field).toStr()).append(", ");
        }
        if (!ll.getFields().isEmpty()) {
            sb.setLength(sb.length() - 2);
        }
        sb.append(" }");
        return sb.toString();
    }

    
    public Map<String, ASTType> getFieldMap() {
        return ll.getFields();
    }

    public TypeBindList getFields() {
        return ll;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ASTTStruct other = (ASTTStruct) obj;
        return ll.getFields().equals(other.ll.getFields());
    }

    @Override
    public int hashCode() {
        return ll.getFields().hashCode();
    }

    public boolean isSubtypeOf(ASTType other) {
        if (!(other instanceof ASTTStruct)) {
            return false;
        }
        
        ASTTStruct otherStruct = (ASTTStruct) other;
        Map<String, ASTType> myFields = this.getFieldMap();
        Map<String, ASTType> otherFields = otherStruct.getFieldMap();
        
       
        for (String fieldName : otherFields.keySet()) {
            if (!myFields.containsKey(fieldName)) {
                return false; 
            }
            
            if (!myFields.get(fieldName).equals(otherFields.get(fieldName))) {
                return false; 
            }
        }
        return true; 
    }
}
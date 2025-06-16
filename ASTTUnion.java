import java.util.HashMap;

public class ASTTUnion implements ASTType {
    private HashMap<String, ASTType> variants;

    public ASTTUnion(HashMap<String, ASTType> variants) {
        this.variants = variants;
    }
    
    public ASTTUnion(TypeBindList typeBindList) {
        this.variants = typeBindList.getFields();
    }
    
    public HashMap<String, ASTType> getVariants() {
        return variants;
    }

    @Override
    public ASTType typeCheck(Environment<ASTType> env) throws TypeCheckError {
        HashMap<String, ASTType> checkedVariants = new HashMap<>();
        for (String variant : variants.keySet()) {
            checkedVariants.put(variant, variants.get(variant).typeCheck(env));
        }
        return new ASTTUnion(checkedVariants);
    }
    
    public String toStr() {
        StringBuilder sb = new StringBuilder("union { ");
        for (String variant : variants.keySet()) {
            sb.append("#").append(variant).append(": ").append(variants.get(variant).toStr()).append(", ");
        }
        if (!variants.isEmpty()) sb.setLength(sb.length() - 2);
        sb.append(" }");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ASTTUnion other = (ASTTUnion) obj;
        return variants.equals(other.variants);
    }

    @Override
    public int hashCode() {
        return variants.hashCode();
    }

    public boolean isSubtypeOf(ASTType other) {
        if (!(other instanceof ASTTUnion)) {
            return false;
        }
        
        ASTTUnion otherUnion = (ASTTUnion) other;
        HashMap<String, ASTType> myVariants = this.getVariants();
        HashMap<String, ASTType> otherVariants = otherUnion.getVariants();
        
       
        for (String variantName : myVariants.keySet()) {
            if (!otherVariants.containsKey(variantName)) {
                return false; 
            }
           
            ASTType myVariantType = myVariants.get(variantName);
            ASTType otherVariantType = otherVariants.get(variantName);
            
          
            if (!myVariantType.equals(otherVariantType)) {
                return false; 
            }
        }
        return true; 
    }
}

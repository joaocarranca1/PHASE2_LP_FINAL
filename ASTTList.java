import java.util.Objects;

public class ASTTList implements ASTType {
    private ASTType elt;

    public ASTTList(ASTType eltt) {
        elt = eltt;
    }

    
    public ASTType getElementType() {
        return elt;
    }

    @Override
    public ASTType typeCheck(Environment<ASTType> env) throws TypeCheckError {
       
        ASTType checkedElt = elt.typeCheck(env);
        return new ASTTList(checkedElt); 
    }

    @Override
    public String toStr() {
        return "list<" + elt.toStr() + ">";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ASTTList other = (ASTTList) obj;
        return Objects.equals(elt, other.elt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(elt);
    }
}

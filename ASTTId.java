import java.util.Objects;

public class ASTTId implements ASTType {

    private String id;

    public ASTTId(String id) {
        this.id = id;
    }

    // Add this method
    public String getId() {
        return id;
    }

    @Override
    public ASTType typeCheck(Environment<ASTType> env) throws TypeCheckError {
       
        return this;
    }

    @Override
    public String toStr() {
        return id; 
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ASTTId other = (ASTTId) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

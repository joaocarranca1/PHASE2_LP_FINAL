import java.util.Objects;

public class ASTTRef implements ASTType {

    private ASTType type;

    public ASTTRef(ASTType type) {
        this.type = type;
    }
    
    public ASTType getType() {
        return type;
    }


    public ASTType getInnerType() {
        return type;
    }

    @Override
    public ASTType typeCheck(Environment<ASTType> env) throws TypeCheckError {
        ASTType checkedType = type.typeCheck(env);
        return new ASTTRef(checkedType);
    }
    
    public String toStr() {
        return "ref<" + type.toStr() + ">";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ASTTRef other = (ASTTRef) obj;
        return Objects.equals(type, other.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}
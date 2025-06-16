public class VString implements IValue {
    private String value;
    
    public VString(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    @Override
    public String toStr() {
        return value;
    }
}

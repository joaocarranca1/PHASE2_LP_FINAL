public class VBool implements IValue {
    private final boolean value;

    public VBool(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public String toStr() {
        return Boolean.toString(value);
    }

    @Override
    public String toString() {
        return toStr();
    }
}


public class VUnion implements IValue {
    private final String label;
    private final IValue value;

    public VUnion(String label, IValue value) {
        if (label == null || value == null) {
            throw new IllegalArgumentException("Label and value cannot be null");
        }
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public IValue getValue() {
        return value;
    }

    @Override
    public String toStr() {
        return "#" + label + "(" + value.toStr() + ")";
    }

    @Override
    public String toString() {
        return toStr();
    }
}

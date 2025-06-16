public class VBox implements IValue {
    private IValue value;

    public VBox(IValue value) {
        this.value = value;
    }

    public IValue get() {
        return value;
    }

    public void set(IValue value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "box(" + value + ")";
    }

    @Override
    public String toStr() {
        return value.toStr();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof VBox)) return false;
        return value.equals(((VBox) obj).get());
    }
}


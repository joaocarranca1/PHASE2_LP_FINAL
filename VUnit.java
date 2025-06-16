public class VUnit implements IValue {
    public static final VUnit INSTANCE = new VUnit();

    private VUnit() {}

    @Override
    public String toString() {
        return "()";
    }

    @Override
    public String toStr() {
        return "()";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof VUnit;
    }
}

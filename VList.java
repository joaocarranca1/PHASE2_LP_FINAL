public class VList implements IValue {
    private final IValue head;
    private final VList tail;
    private final boolean isEmpty;

   
    public VList() {
        this.head = null;
        this.tail = null;
        this.isEmpty = true;
    }

    public VList(IValue head, VList tail) {
        this.head = head;
        this.tail = tail;
        this.isEmpty = false;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public IValue getHead() {
        if (isEmpty) throw new RuntimeException("Cannot get head of empty list");
        return head;
    }

    public VList getTail() {
        if (isEmpty) throw new RuntimeException("Cannot get tail of empty list");
        return tail;
    }

    @Override
    public String toStr() {
        if (isEmpty) return "nil";
        return head.toStr() + " :: " + tail.toStr();
    }

    @Override
    public String toString() {
        return toStr();
    }
}

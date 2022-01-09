import java.util.Objects;

public class MinPQ<Key extends Comparable<Key>> {
    private class   Node {
        Key     key;
        Node    next;
        Node(Key key) { this.key = key; next = null; }
        Node(Key key, Node next) { this.key = key; this.next = next; }
    }
    private Node    root;
    private int     size;
    //Create an empty priority queue
    MinPQ() { root = null; size = 0; }

    @Override
    public boolean  equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MinPQ<?> minPQ = (MinPQ<?>) o;
        return Objects.equals(root, minPQ.root);
    }

    @Override
    public int      hashCode() {
        return Objects.hash(root);
    }

    private boolean less(Key a, Key b) { return a.compareTo(b) < 0; }

    //insert a key into a priority queue
    public void     enqueue(Key key) {
        if (root == null) { root = new Node(key); ++size; return; }
        Node roll = root;
        Node prev = null;
        while (roll != null) {
            if (less(key, (Key) roll.key)) break ;
            prev = roll;
            roll = roll.next;
        }
        Node v = new Node(key, (roll == null) ? null : roll);
        if (prev != null) prev.next = v;
        else root = v;
        ++size;
    }

    //return and remove the lesser key
    public Key      dequeue() {
        Key rValue = root.key;
        Node roll = root;
        root = root.next;
        roll = null;
        --size;
        return rValue;
    }

    //return a lesser key
    public Key      min() {
        return root.key;
    }

    //is the priority queue empty?
    public boolean  isEmpty() { return size == 0; }

    public int      size() { return size; }
}

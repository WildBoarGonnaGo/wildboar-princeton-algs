public class SepChainHashST<Key extends Comparable<Key>, Value> {
    private final int         M = 97;
    private final Node[]      arrHash = new Node[M];
    private static class Node {
        Object  key;
        Object  value;
        Node    next;
        public  Node(Object key, Object value) {
            this.key = key;
            this.value = value;
            next = null;
        }
    }

    private int hash(Key key) {
        return key.hashCode() & 0x7fffffff % M;
    }

    public Value    insert(Key key, Value value) {
        int i = hash(key);
        Node x = arrHash[i];
        for ( ; x.next != null; x = x.next)
            if (x.key.equals(key)) { x.value = value; return (Value)x.value; }
        x.next = new Node(key, value);
        return null;
    }

    public Value    get(Key key) {
        int i = hash(key);
        for (Node x = arrHash[i]; x != null; x = x.next)
            if (x.key.equals(key)) { return (Value)x.value; }
        return null;
    }
}

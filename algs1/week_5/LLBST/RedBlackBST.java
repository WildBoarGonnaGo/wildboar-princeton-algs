public class RedBlackBST<Key extends Comparable<Key>, Value> {
    private static final boolean    RED = true;
    private static final boolean    BLACK = false;
    private Node                    root;

    private class   Node {
        public Node(Key key, Value value) {
            this.key = key;
            this.value = value;
            this.color = RED;
            this.left = null;
            this.right = null;
        }
        private Key             key;
        private Value           value;
        public Node             left;
        public Node             right;
        public boolean          color;
    };

    public RedBlackBST() { root = null; }

    private boolean isRed(Node node) {
        if (node == null) return false;
        else return node.color == RED;
    }

    private Node    rotateLeft(Node lNode) {
        Node rNode = lNode.right;
        if (!isRed(rNode)) return lNode;
        lNode.right = rNode.left;
        rNode.left = lNode;
        rNode.color = lNode.color;
        lNode.color = RED;
        return rNode;
    }

    private Node    rotateRight(Node rNode) {
        Node lNode = rNode.left;
        if (!isRed(lNode)) return rNode;
        rNode.left = lNode.right;
        lNode.right = rNode;
        lNode.color = rNode.color;
        rNode.color = RED;
        return lNode;
    }

    private void            flipColors(Node midNode) {
        if (!isRed(midNode.left) && !isRed(midNode.right) && isRed(midNode)) return ;
        midNode.color = RED;
        midNode.left.color = BLACK;
        midNode.right.color = BLACK;
    }

    private Node    insert(Node roll, Key key, Value value) {
        if (roll == null) return new Node(key, value);
        int cmp = key.compareTo(roll.key);
        if (cmp == 0) roll.value = value;
        else if (cmp < 0) roll.left = insert(roll.left, key, value);
        else roll.right = insert(roll.right, key, value);
        if (isRed(roll.right) && !isRed(roll.left)) roll = rotateLeft(roll);
        else if (isRed(roll.left) && isRed(roll.left.left)) roll = rotateRight(roll);
        else if (!isRed(roll) && isRed(roll.left) && isRed(roll.right)) flipColors(roll);
        return roll;
    }

    public Value    get(Key key) {
        Node roll = root;
        while (roll != null) {
            int cmp = key.compareTo(roll.key);
            if (cmp < 0) roll = roll.left;
            else if (cmp > 0) roll = roll.right;
            else if (cmp == 0) return roll.value;
        }
        return null;
    }
}

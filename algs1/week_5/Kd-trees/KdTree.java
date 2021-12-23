import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Queue;

public class KdTree {
    private class Node {
        public Node    left;
        public Node    right;
        private Point2D key;
        public Node(Point2D key) { this.key = key; }
    }
    Node        root;

    public KdTree() { root = null; }

    private Node                insert(Node x, Point2D p, int h) {
        if (x == null) { x = new Node(p); return x; }
        double cmp = (h++ % 2 == 0) ? p.x() - x.key.x() :
                p.y() - x.key.y();
        if (cmp < 0) return insert(x.left, p, h);
        else if (cmp > 0) return insert(x.right, p, h);
        else return x;
    }

    public void     insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        insert(root, p, 0);
    }

    private Node                search(Node x, Point2D p, int h) {
        if (x == null) return null;
        double cmp = (h++ % 2 == 0) ? p.x() - x.key.x() :
                p.y() - x.key.y();
        if (cmp < 0) return insert(x.left, p, h);
        else if (cmp > 0) return insert(x.right, p, h);
        else return x;
    }

    public Node     search(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return search(root, p, 0);
    }

    private double              searchMinX() {
        Node    roll = root;
        double  result = root.key.x();
        if (roll.left == null) return result;
        else roll = roll.left;
        while (roll.left.left != null) {
            if (result > roll.key.x()) result = roll.key.x();
            roll = roll.left.left;
        }
        return result;
    }

    private double              searchMaxX() {
        Node    roll = root;
        double  result = root.key.x();
        if (roll.right == null) return result;
        else roll = roll.right;
        while (roll.right.right != null) {
            if (result < roll.key.x()) result = roll.key.x();
            roll = roll.right.right;
        }
        return result;
    }

    private double              searchMinY() {
        Node    roll = root;
        double  result = root.key.y();
        while (roll.left.left != null) {
            if (result > roll.key.y()) result = roll.key.y();
            roll = roll.left.left;
        }
        return result;
    }

    private double              searchMaxY() {
        Node    roll = root;
        double  result = root.key.y();
        while (roll.right.right != null) {
            if (result < roll.key.y()) result = roll.key.y();
            roll = roll.right.right;
        }
        return result;
    }

    private void                exch(double a, double b) {
        double buffer = a;
        a = b;
        b = buffer;
    }

    private void                draw(Node x, double xMin, double yMin,
                                     double xMax, double yMax, int h) {
        if (x == null) return ;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.008);
        StdDraw.point(x.key.x(), x.key.y());
        if (h++ % 2 == 0) {
            double tmp = x.key.x();
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius(0.004);
            StdDraw.line(tmp, yMin, tmp, yMax);
            draw(x.left, xMin, yMin, tmp, yMax, h);
            draw(x.right, tmp, yMin, xMax, yMax, h);
        } else {
            double tmp = x.key.y();
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius(0.004);
            StdDraw.line(xMin, tmp, xMax, tmp);
            draw(x.left, xMin, yMin, xMax, tmp, h);
            draw(x.right, xMin, tmp, xMax, yMax, h);
        }
    }

    public void                 draw() {
        if (root == null) return;
        double[] rect = new double[4];
        double xMin = searchMinX(), yMin = searchMinY(),
        xMax = searchMaxX(), yMax = searchMaxY();
        StdDraw.setPenRadius(0.008);
        draw(root, xMin, yMin, xMin, xMax, 0);
    }

    private void                range(Queue<Point2D> res, RectHV rect, Node x, int h) {
        if (rect.contains(x.key)) res.enqueue(x.key);
    }

    public Iterable<Point2D>    range(RectHV rect) {
        Queue<Point2D>  result = new Queue<>();
        range(result, rect, root, 0);
        return result;
    }
}

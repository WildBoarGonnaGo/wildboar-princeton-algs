import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;

public class KdTree {
    private class Node {
        public  Node    left;
        public  Node    right;
        private Point2D key;
        private RectHV  rect;
        public Node(Point2D key, RectHV rect) {
            this.key = key;
            this.rect = rect;
            left = null;
            right = null;
        }
    }
    private int         size;
    private Node        root;

    public KdTree() {
        root = null;
        size = 0;
    }

    public boolean              isEmpty() { return size == 0; }

    public int                  size() { return size; }

    private int                 compare(Point2D lNode, Point2D rNode, int h) {
        int result = 0;

        if (h % 2 == 0) {
            result = Double.compare(lNode.x(), rNode.x());
            if (result == 0) result = Double.compare(lNode.y(), rNode.y());
        } else {
            result = Double.compare(lNode.y(), rNode.y());
            if (result == 0) result = Double.compare(lNode.x(), rNode.x());
        }
        return result;
    }

    private Node                insert(Node x, Point2D p, double xmin, double ymin,
                                       double xmax, double ymax, int h) {
        if (x == null) { x = new Node(p, new RectHV(xmin, ymin, xmax, ymax)); ++size; return x; }
        int cmp = compare(p, x.key, h);
        if (cmp < 0) {
            if (h % 2 == 0)
                x.left = insert(x.left, p, xmin, ymin, x.key.x(), ymax, h + 1);
            else
                x.left = insert(x.left, p, xmin, ymin, xmax, x.key.y(), h + 1);
        } else if (cmp > 0) {
            if (h % 2 == 0)
                x.right = insert(x.right, p, x.key.x(), ymin, xmax, ymax, h + 1);
            else
                x.right = insert(x.right, p, xmin, x.key.y(), xmax, ymax, h + 1);
        }
        return x;
    }

    public void                 insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        root = insert(root, p, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY,
            Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 0);
    }

    public boolean              contains(Point2D p) {
        return search(root, p, 0) != null;
    }

    private Node                search(Node x, Point2D p, int h) {
        if (x == null) return null;
        Node result = null;
        int cmp = compare(p, x.key, h);
        if (cmp < 0)
                result = search(x.left, p, h + 1);
        else if (cmp > 0)
                result = search(x.right, p, h + 1);
        return result;
    }

    private void                boundaries(Node x, double[] rect) {
        if (x == null) return ;
        if (rect[0] > x.key.x()) rect[0] = x.key.x();
        if (rect[1] > x.key.y()) rect[1] = x.key.y();
        if (rect[2] < x.key.x()) rect[2] = x.key.x();
        if (rect[3] < x.key.y()) rect[3] = x.key.y();
        boundaries(x.left, rect);
        boundaries(x.right, rect);
    }

    private void                draw(Node x, double xMin, double yMin,
                                     double xMax, double yMax, int h) {
        if (x == null) return ;
        if (h++ % 2 == 0) {
            double tmp = x.key.x();
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius(0.002);
            StdDraw.line(tmp, yMin, tmp, yMax);
            draw(x.left, xMin, yMin, tmp, yMax, h);
            draw(x.right, tmp, yMin, xMax, yMax, h);
        } else {
            double tmp = x.key.y();
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius(0.002);
            StdDraw.line(xMin, tmp, xMax, tmp);
            draw(x.left, xMin, yMin, xMax, tmp, h);
            draw(x.right, xMin, tmp, xMax, yMax, h);
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(x.key.x(), x.key.y());
    }

    public void                 draw() {
        if (root == null) return;
        double[] rect = new double[4];
        StdDraw.clear();
        rect[0] = Double.POSITIVE_INFINITY;
        rect[1] = Double.POSITIVE_INFINITY;
        rect[2] = Double.NEGATIVE_INFINITY;
        rect[3] = Double.NEGATIVE_INFINITY;
        boundaries(root, rect);
        double xMin = rect[0], yMin = rect[1], xMax = rect[2], yMax = rect[3],
        distX = Math.abs(xMax - xMin), distY = Math.abs(yMax - yMin),
                diff = 0.4 * ((distX < distY) ? distX : distY);
        rect = null;
        xMin -= diff; yMin -= diff; xMax += diff; yMax += diff;
        if (distX == 0) { xMin = xMin - 0.4; xMax = xMax + 0.4; }
        if (distY == 0) { yMin = yMin - 0.4; yMax = yMax + 0.4; }
        StdDraw.setXscale(xMin, xMax);
        StdDraw.setYscale(yMin, yMax);
        draw(root, xMin, yMin, xMax, yMax, 0);
    }

    private void                range(Queue<Point2D> res, RectHV rect, Node x) {
        if (x != null && rect.intersects(x.rect)) {
            if (rect.contains(x.key)) res.enqueue(x.key);
            range(res, rect, x.left);
            range(res, rect, x.right);
        }
    }

    public Iterable<Point2D>    range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        Queue<Point2D>  result = new Queue<>();
        range(result, rect, root);
        return result;
    }

    private Point2D             nearest(Node x, Point2D p, Point2D min) {
        if (x != null && min.distanceSquaredTo(p) >= x.rect.distanceSquaredTo(p)) {
            if (min.distanceSquaredTo(p) > x.key.distanceSquaredTo(p)) min = x.key;
            if (x.left != null && x.left.rect.contains(p)) {
                min = nearest(x.left, p, min);
                min = nearest(x.right, p, min);
            } else {
                min = nearest(x.right, p, min);
                min = nearest(x.left, p, min);
            }
        }
        return min;
    }

    public Point2D              nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;
        Point2D min = root.key;
        Point2D res = nearest(root, p, min);
        return (res);
    }

    public static void main(String[] args) {
        String filename = args[0];
        In instream = new In(filename);
        KdTree test = new KdTree();
        while (!instream.isEmpty()) {
            double x = instream.readDouble();
            double y = instream.readDouble();
            test.insert(new Point2D(x, y));
            test.draw();
            StdDraw.pause(1000);
        }
        StdOut.println("Nearest point to ZERO(0, 0): " + test.nearest(new Point2D(0, 0)));
        StdOut.println("Nearest point to (0.9, 0.9): " + test.nearest(new Point2D(0.9, 0.9)));
        StdOut.println("Nearest point to (0.5, 0.4): " + test.nearest(new Point2D(0.5, 0.4)));
        StdOut.println("Nearest point to (-0.4, -0.6): " + test.nearest(new Point2D(-0.4, -0.6)));
        Queue<Point2D> testQueue = (Queue<Point2D>) test.range(new RectHV(0.2, 0.2, 0.5, 0.5));
        StdOut.print("Elements of 'testQueue': " + testQueue.toString());
    }
}

import edu.princeton.cs.algs4.*;

public class KdTree {
    private class Node {
        public  Node    left;
        public  Node    right;
        private Point2D key;
        public Node(Point2D key) { this.key = key; }
    }
    Node        root;

    public KdTree() { root = null; }

    private Node                insert(Node x, Point2D p, int h) {
        if (x == null) { x = new Node(p); return x; }
        double xDiff = p.x() - x.key.x(), yDiff = p.y() - x.key.y(),
        cmp = (h++ % 2 == 0) ? xDiff : yDiff;
        double cmp2 = (h % 2 == 0) ? xDiff : yDiff;
        if (cmp < 0) x.left = insert(x.left, p, h);
        else if (cmp2 != 0) x.right = insert(x.right, p, h);
        return x;
    }

    public void     insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        root = insert(root, p, 0);
    }

    private Node                search(Node x, Point2D p, int h) {
        if (x == null) return null;
        double xDiff = p.x() - x.key.x(), yDiff = p.y() - x.key.y(),
                cmp = (h++ % 2 == 0) ? xDiff : yDiff;
        double cmp2 = (h % 2 == 0) ? xDiff : yDiff;
        if (cmp < 0) return search(x.left, p, h);
        else if (cmp2 != 0) return search(x.right, p, h);
        else return x;
    }

    public Node     search(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return search(root, p, 0);
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
        rect[0] = Double.MAX_VALUE; rect[1] = Double.MAX_VALUE;
        rect[2] = Double.MIN_VALUE; rect[3] = Double.MIN_VALUE;
        boundaries(root, rect);
        double xMin = rect[0], yMin = rect[1], xMax = rect[2], yMax = rect[3],
        distX = Math.abs(xMax - xMin), distY = Math.abs(yMax - yMin),
                diff = 0.2 * ((distX < distY) ? distX : distY);
        rect = null;
        xMin -= diff; yMin -= diff; xMax += diff; yMax += diff;
        if (distX == 0) { xMin = xMin - 0.2; xMax = xMax + 0.2; }
        if (distY == 0) { yMin = yMin - 0.2; yMax = yMax + 0.2; }
        StdDraw.setXscale(xMin, xMax);
        StdDraw.setYscale(yMin, yMax);
        draw(root, xMin, yMin, xMax, yMax, 0);
    }

    private void                range(Queue<Point2D> res, RectHV rect, Node x, int h) {
        if (x == null) return ;
        if (rect.contains(x.key)) res.enqueue(x.key);
        if (h++ % 2 == 0) {
            if (x.key.x() > rect.xmax()) range(res, rect, x.left, h);
            else if (x.key.x() < rect.xmin()) range(res, rect, x.right, h);
            else {
                range(res, rect, x.left, h);
                range(res, rect, x.right, h);
            }
        } else {
            if (x.key.y() > rect.ymax()) range(res, rect, x.left, h);
            else if (x.key.y() < rect.ymin()) range(res, rect, x.right, h);
            else {
                range(res, rect, x.left, h);
                range(res, rect, x.right, h);
            }
        }
    }

    public Iterable<Point2D>    range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        Queue<Point2D>  result = new Queue<>();
        range(result, rect, root, 0);
        return result;
    }

    private Point2D             nearest(Node x, Point2D p, double min, int h) {
        if (x == null) return null;
        Point2D res = null;
        double  dist = x.key.distanceTo(p);
        if (min > dist) { min = dist; res = x.key; }
        if (h++ % 2 == 0) {
            Point2D prev = res;
            if (p.x() <= x.key.x()) res = nearest(x.left, p, min, h);
            if (res == null) res = prev;
            prev = res;
            if (p.x() >= x.key.x() && p.y() != x.key.y())
                res = nearest(x.right, p, min, h);
            if (res == null) res = prev;
        } else {
            Point2D prev = res;
            if (p.y() <= x.key.y()) res = nearest(x.left, p, min, h);
            if (res == null) res = prev;
            prev = res;
            if (p.y() >= x.key.y() && p.x() != x.key.x())
                res = nearest(x.right, p, min, h);
            if (res == null) res = prev;
        }
        return res;
    }

    public Point2D              nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        double  min = Double.MAX_VALUE;
        Point2D res = nearest(root, p, min, 0);
        return (res);
    }

    public static void main(String[] args) {
        KdTree test = new KdTree();
        test.insert(new Point2D(-0.35, 0.25));
        test.draw();
        StdDraw.pause(1000);
        test.insert(new Point2D(0.6, 0.725));
        test.draw();
        StdDraw.pause(1000);
        test.insert(new Point2D(0.25, 0.3));
        test.draw();
        StdDraw.pause(1000);
        test.insert(new Point2D(1.3, -0.2));
        test.draw();
        StdDraw.pause(1000);
        test.insert(new Point2D(-0.9, -0.4));
        test.draw();
        StdDraw.pause(1000);
        test.insert(new Point2D(-0.225, -0.4));
        test.draw();
        StdDraw.pause(1000);
        test.insert(new Point2D(-0.35, -0.55));
        test.draw();
        StdOut.println("Nearest point to ZERO(0, 0): " + test.nearest(new Point2D(0, 0)));
        StdOut.println("Nearest point to (0.9, 0.9): " + test.nearest(new Point2D(0.9, 0.9)));
        StdOut.println("Nearest point to (0.5, 0.4): " + test.nearest(new Point2D(0.5, 0.4)));
        Queue<Point2D> testQueue = (Queue<Point2D>) test.range(new RectHV(0.2, 0.2, 0.5, 0.5));
        StdOut.print("Elements of 'testQueue': " + testQueue.toString());
    }
}

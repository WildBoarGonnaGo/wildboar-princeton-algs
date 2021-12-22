import edu.princeton.cs.algs4.*;

import java.util.Iterator;
import java.util.TreeSet;

public class PointSET {
    TreeSet<Point2D>    pointSet;

    //construct an empty set of points
    public          PointSET() {
        StdDraw.setPenRadius(0.005);
        pointSet = new TreeSet<>();
    }

    //is the set empty
    public boolean  isEmpty() { return pointSet.isEmpty(); }

    //number of points in the set
    public int      size() { return pointSet.size(); }

    //add the point to the set (if it is not already in the set)
    public void     insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (!contains(p)) pointSet.add(p);
    }

    //does the set contain point p
    public boolean  contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return pointSet.contains(p);
    }

    //draw all points in standard draw
    public void     draw() {
        StdDraw.clear();
        Iterator<Point2D>   it = pointSet.iterator();
        while(it.hasNext()) {
            Point2D tmp = it.next();
            StdDraw.point(tmp.x(), tmp.y());
        }
        StdDraw.show();
    }

    //all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D>    range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        Queue<Point2D> result = new Queue<>();
        Iterator<Point2D>   it = pointSet.iterator();
        while (it.hasNext()) {
            Point2D tmp = it.next();
            if (rect.contains(tmp)) result.enqueue(tmp);
        }
        return result;
    }

    //a nearest neighbor in the set to point p; null if the set it empty
    public Point2D              nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        double  r = Double.MAX_VALUE;
        Point2D result = null;
        Iterator<Point2D>   it = pointSet.iterator();
        while (it.hasNext()) {
            Point2D tmp = it.next();
            if (r > tmp.distanceTo(p)) { r = tmp.distanceTo(p); result = tmp; }
        }
        return result;
    }

    public static void main(String[] args) {
        int         numPoints = StdRandom.uniform(10, 20);
        PointSET    test = new PointSET();
        StdDraw.setXscale(-100, 100);
        StdDraw.setYscale(-100, 100);
        for (int i = 0; i < numPoints; ++i) {
            double x = StdRandom.uniform(-95.0, 95.0);
            double y = StdRandom.uniform(-95.0, 95.0);
            Point2D candidate = new Point2D(x, y);
            test.insert(candidate);
        }
        RectHV area = new RectHV(-50.0, -50.0, 50.0, 50.0);
        Queue<Point2D> testQueue = (Queue<Point2D>) test.range(area);
        StdOut.println("Points in area " + area.toString());
        while (!testQueue.isEmpty()) StdOut.print(testQueue.dequeue().toString() +
                ((!testQueue.isEmpty()) ? " " : "\n"));
        StdOut.println("Neares point to zero(0.0, 0.0): " + test.nearest(new Point2D(0.0, 0.0)).toString());
        StdOut.println("Number of points amd Given size: " + test.size() + ", " + numPoints);
        test.draw();
    }
}
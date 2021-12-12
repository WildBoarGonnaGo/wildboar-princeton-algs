import java.lang.Comparable;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Comparator;

public class Point implements Comparable<Point> {
	private final int x, y;

	public Point(int x, int y) { this.x = x; this.y = y; }

	public void draw() { StdDraw.point(x, y); }

	public void drawTo(Point that) { StdDraw.line(this.x, this.y, that.x, that.y); }

	public double slopeTo(Point that) {
		if (this.x == that.x && this.y == that.y) return Double.NEGATIVE_INFINITY;
		else if (this.x == that.x) return Double.POSITIVE_INFINITY;
		else if (this.y == that.y) return +0.0;
		else return (double)(that.y - this.y) / (double)(that.x - this.x);
	}

	public Comparator<Point>	slopeOrder() {
		return new Comparator<Point>() {
			@Override
			public int compare(Point o1, Point o2) {
				double diff = slopeTo(o1) - slopeTo(o2);
				if (diff < 0.0) return -1;
				else if (diff == 0.0) return 0;
				else return 1;
			}
		};
	}

	@Override
	public int		compareTo(Point that) {
		int res = 0;

		if (this.y < that.y) return --res;
		else if (this.y > that.y) return ++res;
		else {
			if (this.x < that.x) --res;
			else if (this.x > that.y) ++res;
		}
		return res;
	}

	@Override
	public String	toString() {  return "(" + x + ", " + y + ")"; }

	public static void main(String[] args) {

		Point a = new Point(0, 0);
		Point b = new Point(0, 1);
		Point c = new Point(1, 0);
		Point d = new Point(1, 1);
		StdOut.println("Point a" + a.toString());
		StdOut.println("Point b" + b.toString());
		StdOut.println("Point c" + c.toString());
		StdOut.println("Point d" + d.toString());
		StdOut.println("\na.slopeTo(a) = " + a.slopeTo(a));
		StdOut.println("a.slopeTo(b) = " + a.slopeTo(b));
		StdOut.println("a.slopeTo(c) = " + a.slopeTo(c));
		StdOut.println("a.slopeTo(d) = " + a.slopeTo(d));
		Comparator<Point> compSmpl= b.slopeOrder();
		StdOut.println("compSmpl.compare(b ,c) = " + compSmpl.compare(a, c));
		StdOut.println("compSmpl.compare(c ,d) = " + compSmpl.compare(c, d));
		StdOut.println("compSmpl.compare(b ,d) = " + compSmpl.compare(a, d));
		StdDraw.setXscale(-2, 2);
		StdDraw.setYscale(-2, 2);
		StdDraw.setPenRadius(0.02);
		a.draw(); d.draw();
		StdDraw.setPenRadius(0.01);
		a.drawTo(d);
	}
}

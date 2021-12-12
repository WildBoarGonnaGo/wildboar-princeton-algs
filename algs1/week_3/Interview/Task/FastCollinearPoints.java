import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;
import java.util.LinkedList;

public class FastCollinearPoints {
	private LineSegment[]			segments;
	private int						numberOfSegments;

	private	void	checkNullWithin(Point[] points) {
		int len = points.length;
		for (int i = 0; i < len; ++i) {
			if (points[i] == null)
				throw new IllegalArgumentException();
		}
	}

	private void	checkDuplicates(Point[] points) {
		int len = points.length;
		for (int i = 0; i < len - 1; ++i) {
			if (points[i].compareTo(points[i + 1]) == 0)
				throw new IllegalArgumentException();
		}
	}

	public FastCollinearPoints(Point[] points) {
		if (points == null) throw new IllegalArgumentException();
		checkNullWithin(points);
		Point[]	firstSort = points.clone();
		LinkedList<LineSegment>	toolSegmList = new LinkedList<>();
		Arrays.sort(firstSort);
		int len = points.length;

		numberOfSegments = 0;
		for (int i = 0; i < len; ++i) {
			Point[]	slopeSort = firstSort.clone();
			Point	p = firstSort[i];
			Arrays.sort(slopeSort, p.slopeOrder());

			int j = 1;
			while (j < len) {
				int arrow = 0;
				LinkedList<Point>	tmpPoints = new LinkedList<>();
				double	initSlope = p.slopeTo(slopeSort[j]);
				while (j < len && p.slopeTo(slopeSort[j]) == initSlope) {
					tmpPoints.add(slopeSort[j++]);
					++arrow;
				}
				if (arrow >= 3 && p.compareTo(tmpPoints.getFirst()) < 0) {
					++numberOfSegments;
					toolSegmList.add(new LineSegment(p, tmpPoints.getLast()));
				}
			}
		}
		segments = toolSegmList.toArray(new LineSegment[0]);
	}

	public int 				numberOfSegments() { return this.numberOfSegments; }

	public LineSegment[]	segments() { return this.segments; }

	public static void 		main(String[] args) {

		// read the n points from a file
		In in = new In(args[0]);
		int n = in.readInt();
		Point[] points = new Point[n];
		for (int i = 0; i < n; i++) {
			int x = in.readInt();
			int y = in.readInt();
			points[i] = new Point(x, y);
		}

		// draw the points
		//StdDraw.enableDoubleBuffering();
		StdDraw.setPenRadius(0.006);
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		for (Point p : points) {
			p.draw();
		}
		StdDraw.show();

		// print and draw the line segments
		StdDraw.setPenRadius(0.003);
		FastCollinearPoints collinear = new FastCollinearPoints(points);
		for (LineSegment segment : collinear.segments()) {
			StdOut.println(segment);
			segment.draw();
		}
		StdDraw.show();
	}
}

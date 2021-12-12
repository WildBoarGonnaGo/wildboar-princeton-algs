import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.LinkedList;

import java.util.Arrays;

public class BruteCollinearPoints {
	private LineSegment[]	segments;
	private int				size;

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

	// find all line segments containing 4 points
	public BruteCollinearPoints(Point[] points) {
		if (points == null)
			throw new IllegalArgumentException();
		checkNullWithin(points);
		Point[] aux = points.clone();
		Arrays.sort(aux);
		checkDuplicates(aux);
		LinkedList<LineSegment>	result = new LinkedList<>();
		int len = points.length;
		for (int i = 0; i < len - 3; ++i) {
			Point	first = aux[i];
			for (int j = i + 1; j < len - 2; ++j) {
				Point second = aux[j];
				double firstSlope = first.slopeTo(second);
				for (int k = j + 1; k < len - 1; ++k) {
					Point third = aux[k];
					double secondSlope = first.slopeTo(third);
					if (firstSlope == secondSlope) {
						for (int z = k + 1; z < len; ++z) {
							Point forth = aux[z];
							double thirdSlope = first.slopeTo(forth);
							if (firstSlope == thirdSlope) {
								result.add(new LineSegment(first, forth));
							}
						}
					}
				}
			}
		}
		segments = result.toArray(new LineSegment[0]);
	}

	//the number of line segments
	public int				numberOfSegments() { return segments.length; }

	//the line segments
	public LineSegment[]	segments() { return segments; }

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
		BruteCollinearPoints collinear = new BruteCollinearPoints(points);
		for (LineSegment segment : collinear.segments()) {
			StdOut.println(segment);
			segment.draw();
		}
		StdDraw.show();
	}
}

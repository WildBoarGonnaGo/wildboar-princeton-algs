import java.util.ArrayDeque;
import java.lang.Math;
import edu.princeton.cs.algs4.StdOut;

public class Board {
	private int[][]				tiles;
	private final int			n;
	//Create a board from n-by-b array of tiles,
	//where tiles[row][col] = tile at(row, col)
	public Board(int[][] tiles) {
		n = tiles.length;
		this.tiles = new int[n][n];
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; ++j)
				this.tiles[i][j] = tiles[i][j];
		}
	}

	//string representation of this board
	@Override
	public String	toString() {
		String result = n + "\n";
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; ++j) {
				result += " " + tiles[i][j];
			}
			result += "\n";
		}
		return result;
	}

	//board dimension n
	public int		dimension() { return n; }

	//number of tiles out of place
	public int		hamming() {
		int hamming = 0;

		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; ++j) {
				int val = tiles[i][j];
				if (i == n - 1 && j == n - 1) break ;
				if (val != i * n + j + 1) ++hamming;
			}
		}
		return (hamming);
	}

	//sum of Manhattan distances between tiles and goal
	public int		manhattan() {
		int 	manhattan = 0;
		int[]	tool;

		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; ++j) {
				tool = manhattan(i * n + j + 1, 0, n * n - 1);
				if (tool != null) manhattan += tool[0] + tool[1];
			}
		}
		return (manhattan);
	}

	private static void exch(int[][] arr, int irow, int icol, int jrow, int jcol) {
		int buffer = arr[irow][icol];
		arr[irow][icol] = arr[jrow][jcol];
		arr[jrow][jcol] = buffer;
	}

	private int[]	manhattan(int value, int lo, int hi) {
		int[] result = null;
		int size = hi - lo;
		if (hi < lo) return null;
		int mid = lo + (hi - lo) / 2;
		int seek = (value == 0) ? n * n - 1 : value - 1,
			row = mid / n, col = mid % n,
			dstRow = seek / n, dstCol = seek % n;
		if (tiles[row][col] == value) {
			result = new int[2];
			result[0] = Math.abs(dstRow - row);
			result[1] = Math.abs(dstCol - col);
		}
		if (result == null) result = manhattan(value, lo, mid - 1);
		if (result == null) result = manhattan(value,mid + 1, hi);
		return (result);
	}

	public boolean			isGoal() { return hamming() == 0 && manhattan() == 0; }

	private boolean			tilesEq(int[][]	rval) {
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; ++j) {
				if (tiles[i][j] != rval[i][j]) return false;
			}
		}
		return true;
	}

	@Override
	public boolean	equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Board)) return false;
		Board Board = (Board) o;
		return n == Board.n && tilesEq(Board.tiles);
	}

	//a neighboring boards
	public Iterable<Board> neighbors() {
		ArrayDeque<Board>	neighList = new ArrayDeque<>();
		int[]	src = manhattan(0, 0, n * n - 1);
		src[0] = n - 1 - src[0];
		src[1] = n - 1 - src[1];
		if (src[1] > 0) {
			Board neighbor = new Board(this.tiles);
			exch(neighbor.tiles, src[0], src[1], src[0], src[1] - 1);
			neighList.add(neighbor);
		}
		if (src[0] < n - 1) {
			Board neighbor = new Board(this.tiles);
			exch(neighbor.tiles, src[0], src[1], src[0] + 1, src[1]);
			neighList.add(neighbor);
		}
		if (src[1] < n - 1) {
			Board neighbor = new Board(this.tiles);
			exch(neighbor.tiles, src[0], src[1], src[0], src[1] + 1);
			neighList.add(neighbor);
		}
		if (src[0] > 0) {
			Board neighbor = new Board(this.tiles);
			exch(neighbor.tiles, src[0], src[1], src[0] - 1, src[1]);
			neighList.add(neighbor);
		}
		return neighList;
	}

	public Board twin() {
		int[][]	copy = new int[n][n];
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; ++j) copy[i][j] = tiles[i][j];
		}
		int num = 0, irow = 0, icol = 0;
		if (copy[num / n][num % n] == 0) ++num;
		irow = num / n;
		icol = num++ % n;
		if (copy[num / n][num % n] == 0) ++num;
		int jrow = num / n, jcol = num % n;
		exch(copy, irow, icol, jrow, jcol);
		return new Board(copy);
	}

	public static void	main(String[] args) {
		int count = 0;
		int[][]	sample = { {1, 0, 3}, {4, 2, 5}, {7, 8, 6} };
		Board sampleBoard = new Board(sample);
		StdOut.println(sampleBoard.toString());
		int[][]	sample2 = { {8, 1, 3}, {4, 0, 2}, {7, 6, 5} };
		Board sampleBoard2 = new Board(sample2);
		StdOut.println("sampleBoard2.hamming() = " + sampleBoard2.hamming());
		StdOut.println("sampleBoard2.manhattan() = " + sampleBoard2.manhattan());
		ArrayDeque<Board>	sampleNeighbors = (ArrayDeque<Board>) sampleBoard.neighbors();
		StdOut.println("original of 'sampleBoard': ");
		StdOut.println(sampleBoard.toString());
		StdOut.println("hamming: " + sampleBoard.hamming());
		StdOut.println("manhattan: " + sampleBoard.manhattan());
		for (Board zombie : sampleNeighbors) {
			StdOut.println("\nneighbor " + ++count);
			StdOut.println(zombie.toString());
			StdOut.println("hamming: " + zombie.hamming());
			StdOut.println("manhattan: " + zombie.manhattan());
		}
		StdOut.println("original of 'sampleBoard2': ");
		StdOut.println(sampleBoard2.toString());
		StdOut.println("hamming: " + sampleBoard2.hamming());
		StdOut.println("manhattan: " + sampleBoard2.manhattan());
		sampleNeighbors = (ArrayDeque<Board>) sampleBoard2.neighbors();
		count = 0;
		for (Board zombie : sampleNeighbors) {
			StdOut.println("\nneighbor " + ++count);
			StdOut.println(zombie.toString());
			StdOut.println("hamming: " + zombie.hamming());
			StdOut.println("manhattan: " + zombie.manhattan());
		}
		StdOut.println("'SampleBoard2' original:");
		StdOut.println(sampleBoard2.toString());
		StdOut.println("'SampleBoard2' twin:");
		StdOut.println(sampleBoard2.twin().toString());
		StdOut.println("'SampleBoard' original:");
		StdOut.println(sampleBoard.toString());
		StdOut.println("'SampleBoard' twin:");
		StdOut.println(sampleBoard.twin().toString());
	}
}

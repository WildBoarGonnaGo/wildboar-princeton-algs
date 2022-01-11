import java.lang.Math;

public class Euclidian extends BoardField {
	Euclidian(int[][] tiles, int n) { super(tiles, n); }
	Euclidian(int[][] tiles, int n, int move) { super(tiles, n, move); }
	Euclidian(BoardField that) { super(that); }
	Euclidian(String fileName) { super(fileName); }

	public double	euclidian() {
		double manh = 0;
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; ++j) {
				if (i == n - 1 && j == n - 1) break ;
				else {
					int[] tmp = binSearch(i * n + j + 1, 0, n * n - 1);
					manh += Math.sqrt(tmp[0] * tmp[0] + tmp[1] * tmp[1]);
				}
			}
		}
		return manh;
	}

	@Override
	public int				compareTo(BoardField op) {
		Euclidian tmp = new Euclidian(op);
		double diff = this.euclidian() + this.move - tmp.euclidian() - tmp.move;
		if (diff < 0) return -1;
		else if (diff > 0) return 1;
		else return 0;
	}

	@Override
	MinPQ<BoardField>   	neighbors(int move) {
		MinPQ<BoardField>   result = new MinPQ<BoardField>();
		int[] manh = binSearch(0, 0, n * n - 1);
		int row = n - 1 - manh[0], col = n - 1 - manh[1];
		if (row > 0) {
			exch(tiles, row, col, row - 1, col);
			BoardField enroll = new Euclidian(tiles, n, move);
			exch(tiles, row, col, row - 1, col);
			result.enqueue(enroll);
		}
		if (row < n - 1) {
			exch(tiles, row, col, row + 1, col);
			BoardField enroll = new Euclidian(tiles, n, move);
			exch(tiles, row, col, row + 1, col);
			result.enqueue(enroll);
		}
		if (col > 0) {
			exch(tiles, row, col, row, col - 1);
			BoardField enroll = new Euclidian(tiles, n, move);
			exch(tiles, row, col, row, col - 1);
			result.enqueue(enroll);
		}
		if (col < n - 1) {
			exch(tiles, row, col, row, col + 1);
			BoardField enroll = new Euclidian(tiles, n, move);
			exch(tiles, row, col, row, col + 1);
			result.enqueue(enroll);
		}
		return result;
	}


	public BoardField		twin() {
		int[][] copy = new int[n][n];
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; ++j) copy[i][j] = tiles[i][j];
		}
		int pos = 0;
		int prevPos = pos;
		while (copy[prevPos / n][++prevPos % n] == 0) ;
		pos = prevPos;
		while (copy[pos / n][++pos % n] == 0) ;
		exch(copy, pos / n, pos % n,
			prevPos / n, prevPos % n);
		BoardField      result = new Euclidian(copy, n);
		return result;
	}

	public static void  	main(String[] args) {
		int size = 0;
		if (args.length == 0) {
			System.out.println("Usage of BoardField program: java BoardField <filename>");
			return ;
		}
		Euclidian test = new Euclidian(args[0]);
		System.out.println("hamming of original one: " + test.euclidian());
		System.out.println(test.toString());
		MinPQ<BoardField> testPQ = test.neighbors(0);
		int number = 0;
		while (!testPQ.isEmpty()) {
			Euclidian	tmp = (Euclidian) testPQ.min();
			System.out.println("euclidian(" + number++ + "): " + tmp.euclidian());
			System.out.println(testPQ.dequeue().toString());
		}
		System.out.println("Twin of original:");
		System.out.println(test.twin().toString());
		System.out.print("Is Board solvable: ");
		System.out.println(test.isSolvable());
		System.out.print("Is twin board solvable: ");
		System.out.println(test.twin().isSolvable());
	}
}

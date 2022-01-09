import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Manhattan extends BoardField {
	public Manhattan(String fileName) { super(fileName); }
	public Manhattan(int[][] tiles, int n) { super(tiles, n); }
	public Manhattan(int[][] tiles, int n, int move) { super(tiles, n, move); }
	public Manhattan(BoardField that) { super(that); }

	@Override
	public int	compareTo(BoardField op) {
		Manhattan tmp = new Manhattan(op);
		int result = this.manhattan() + this.move - tmp.manhattan() - op.move;
		tmp = null;
		return result;
	}

	@Override
	MinPQ<BoardField>   	neighbors(int move) {
		MinPQ<BoardField>   result = new MinPQ<BoardField>();
		int[] manh = quickSearch(0, 0, n * n - 1);
		int row = n - 1 - manh[0], col = n - 1 - manh[1];
		if (row > 0) {
			exch(tiles, row, col, row - 1, col);
			BoardField enroll = new Manhattan(tiles, n, move);
			exch(tiles, row, col, row - 1, col);
			result.enqueue(enroll);
		}
		if (row < n - 1) {
			exch(tiles, row, col, row + 1, col);
			BoardField enroll = new Manhattan(tiles, n, move);
			exch(tiles, row, col, row + 1, col);
			result.enqueue(enroll);
		}
		if (col > 0) {
			exch(tiles, row, col, row, col - 1);
			BoardField enroll = new Manhattan(tiles, n, move);
			exch(tiles, row, col, row, col - 1);
			result.enqueue(enroll);
		}
		if (col < n - 1) {
			exch(tiles, row, col, row, col + 1);
			BoardField enroll = new Manhattan(tiles, n, move);
			exch(tiles, row, col, row, col + 1);
			result.enqueue(enroll);
		}
		return result;
	}

	public int          manhattan() {
		int manh = 0;
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; ++j) {
				if (i == n - 1 && j == n - 1) break ;
				else {
					int[] tmp = quickSearch(i * n + j + 1, 0, n * n - 1);
					manh += tmp[0] + tmp[1];
				}
			}
		}
		return manh;
	}

	public BoardField		twin() {
		BoardField      result = new Manhattan(tiles, n, move);
		int pos = 0;
		int prevPos = pos;
		while (tiles[pos / n][++pos % n] == 0) ;
		exch(result.tiles, pos / n, pos % n,
			prevPos / n, prevPos % n);
		return result;
	}

	public static void  main(String[] args) {
		int size = 0;
		if (args.length == 0) {
			System.out.println("Usage of BoardField program: java BoardField <filename>");
			return ;
		}
		Manhattan  test = new Manhattan(args[0]);
		System.out.println("manhattan of original one: " + test.manhattan());
		System.out.println(test.toString());
		MinPQ<BoardField> testPQ = test.neighbors(0);
		int number = 0;
		while (!testPQ.isEmpty()) {
			Manhattan tmp = (Manhattan) testPQ.min();
			System.out.println("manhattan(" + ++number + "): " + tmp.manhattan());
			System.out.println(testPQ.dequeue().toString());
		}
		System.out.println("Twin of original:");
		System.out.println(test.twin().toString());
	}
}

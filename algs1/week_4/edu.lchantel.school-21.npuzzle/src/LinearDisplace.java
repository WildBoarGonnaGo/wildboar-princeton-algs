import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LinearDisplace extends BoardField {
	public LinearDisplace(String fileName) { super(fileName); }
	public LinearDisplace(int[][] tiles, int n) { super(tiles, n); }
	public LinearDisplace(int[][] tiles, int n, int move) { super(tiles, n, move); }
	public LinearDisplace(BoardField that) { super(that); }

	public int          hamming() {
		int hamm = 0;
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; ++j) {
				if (i == n - 1 && j == n - 1) continue;
				if (tiles[i][j] != i * n + j + 1) ++hamm;
			}
		}
		return hamm;
	}

	@Override
	public int	compareTo(BoardField op) {
		LinearDisplace tmp = new LinearDisplace(op);
		int result = this.hamming() + this.move - tmp.hamming() - op.move;
		tmp = null;
		return result;
	}

	@Override
	MinPQ<BoardField>   	neighbors(int move) {
		MinPQ<BoardField>   result = new MinPQ<BoardField>();
		int[] manh = binSearch(0, 0, n * n - 1);
		int row = n - 1 - manh[0], col = n - 1 - manh[1];
		if (row > 0) {
			exch(tiles, row, col, row - 1, col);
			BoardField enroll = new LinearDisplace(tiles, n, move);
			exch(tiles, row, col, row - 1, col);
			result.enqueue(enroll);
		}
		if (row < n - 1) {
			exch(tiles, row, col, row + 1, col);
			BoardField enroll = new LinearDisplace(tiles, n, move);
			exch(tiles, row, col, row + 1, col);
			result.enqueue(enroll);
		}
		if (col > 0) {
			exch(tiles, row, col, row, col - 1);
			BoardField enroll = new LinearDisplace(tiles, n, move);
			exch(tiles, row, col, row, col - 1);
			result.enqueue(enroll);
		}
		if (col < n - 1) {
			exch(tiles, row, col, row, col + 1);
			BoardField enroll = new LinearDisplace(tiles, n, move);
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
		BoardField      result = new LinearDisplace(copy, n);
		return result;
	}

	public static void  main(String[] args) {
		int size = 0;
		if (args.length == 0) {
			System.out.println("Usage of BoardField program: java BoardField <filename>");
			return ;
		}
		LinearDisplace test = new LinearDisplace(args[0]);
		System.out.println("hamming of original one: " + test.hamming());
		System.out.println(test.toString());
		MinPQ<BoardField> testPQ = test.neighbors(0);
		int number = 0;
		while (!testPQ.isEmpty()) {
			LinearDisplace	tmp = (LinearDisplace) testPQ.min();
			System.out.println("hamming(" + number++ + "): " + tmp.hamming());
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

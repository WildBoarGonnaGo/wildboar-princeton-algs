import edu.princeton.cs.algs4.Out;

public class OutRowOutColumn extends BoardField{
	OutRowOutColumn(int[][] tiles, int n) { super(tiles, n); }
	OutRowOutColumn(int[][] tiles, int n, int move) { super(tiles, n, move); }
	OutRowOutColumn(BoardField that) { super(that); }
	OutRowOutColumn(String fileName) { super(fileName); }

	public int	outRC() {
		int outrc = 0;
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; ++j) {
				if (tiles[i][j] == 0) continue;
				if ((tiles[i][j] - 1) / n != i) ++outrc;
				if ((tiles[i][j] - 1) % n != j) ++outrc;
			}
		}
		return outrc;
	}

	@Override
	public int	compareTo(BoardField op) {
		OutRowOutColumn tmp = new OutRowOutColumn(op);
		int result = this.outRC() + this.move - tmp.outRC() - op.move;
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
			BoardField enroll = new OutRowOutColumn(tiles, n, move);
			exch(tiles, row, col, row - 1, col);
			result.enqueue(enroll);
		}
		if (row < n - 1) {
			exch(tiles, row, col, row + 1, col);
			BoardField enroll = new OutRowOutColumn(tiles, n, move);
			exch(tiles, row, col, row + 1, col);
			result.enqueue(enroll);
		}
		if (col > 0) {
			exch(tiles, row, col, row, col - 1);
			BoardField enroll = new OutRowOutColumn(tiles, n, move);
			exch(tiles, row, col, row, col - 1);
			result.enqueue(enroll);
		}
		if (col < n - 1) {
			exch(tiles, row, col, row, col + 1);
			BoardField enroll = new OutRowOutColumn(tiles, n, move);
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
		BoardField      result = new OutRowOutColumn(tiles, n, move);
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
		OutRowOutColumn  test = new OutRowOutColumn(args[0]);
		System.out.println("manhattan of original one: " + test.outRC());
		System.out.println(test.toString());
		MinPQ<BoardField> testPQ = test.neighbors(0);
		int number = 0;
		while (!testPQ.isEmpty()) {
			OutRowOutColumn tmp = (OutRowOutColumn) testPQ.min();
			System.out.println("out of row and out of column(" + ++number + "): " + tmp.outRC());
			System.out.println(testPQ.dequeue().toString());
		}
		System.out.println("Twin of original:");
		System.out.println(test.twin().toString());
	}
}

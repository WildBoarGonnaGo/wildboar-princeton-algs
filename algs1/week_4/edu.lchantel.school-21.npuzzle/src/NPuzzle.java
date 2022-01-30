import java.util.LinkedList;

public class NPuzzle {
	private	boolean					solvable;
	private int						moves;
	private LinkedList<BoardField>	path;
	private int						cSize;
	private int						cTime;
	private Node					rival;

	private class Node implements Comparable<Node> {
		public	Node				prev;
		private final BoardField	data;
		private final int			move;

		public				Node(BoardField data) {
			this.data = data;
			move = 0;
			prev = null;
		}
		public				Node(BoardField data, int move, Node prev) {
			this.data = data;
			this.move = move;
			this.prev = prev;
		}

		@Override
		public int	compareTo(Node that) {
			return this.data.compareTo(that.data);
		}
	}

	private	BoardField	getInstance(int var, int[][] board, int n) {
		if (var == 1) return new Manhattan(board, n);
		else if (var == 2) return new LinearDisplace(board, n);
		else if (var == 3) return new Euclidian(board, n);
		else return new OutRowOutColumn(board, n);
	}

	private BoardField	getInstance(int var, BoardField sample) {
		if (var == 1) return new Manhattan(sample);
		else if (var == 2) return new LinearDisplace(sample);
		else if (var == 3) return new Euclidian(sample);
		else return new OutRowOutColumn(sample);
	}

	NPuzzle(BoardField smpl, int var) {
		if (smpl == null) throw new NullPointerException();
		if (var < 1 || var > 4) throw new IllegalArgumentException();
		solvable = false;
		path = null;
		cSize = 0;
		moves = -1;
		int n = smpl.size();
		rival = new Node(getInstance(var, smpl));
		if (!smpl.isSolvable()) {
			solvable = false;
			return ;
		}
		int[][] destM = new int[n][n];
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; ++j) {
				if (i == n - 1 && j == n - 1) destM[i][j] = 0;
				else destM[i][j] = i * n + j + 1;
			}
		}
		BoardField				destBoard = getInstance(var, destM, n);
		BinaryHeapMinPQ<Node>	rivalQueue = new BinaryHeapMinPQ<>();
		Node					last = null;
		rivalQueue.enqueue(rival);
		path = new LinkedList<>();
		while (!rivalQueue.isEmpty()) {
			Node	jedy = rivalQueue.dequeue();
			if (jedy.data.equals(destBoard)) {
				solvable = true;
				while (last != null) {
					path.addFirst(last.data);
					++moves;
					last = last.prev;
				}
				return ;
			}
			MinPQ<BoardField> jedyQueue = jedy.data.neighbors(jedy.move + 1);
			cSize += jedyQueue.size();
			while (!jedyQueue.isEmpty()) {
				if (jedy.prev == null || !jedy.prev.data.equals(jedyQueue.min())) {
					last = new Node(jedyQueue.dequeue(), jedy.move + 1, jedy);
					rivalQueue.enqueue(last);
					++cTime;
				}
				else jedyQueue.dequeue();
			}
			last = rivalQueue.min();
		}
	}

	public boolean					isSolvable() { return solvable; }
	public int						moves() { return moves; }
	public LinkedList<BoardField>	solveList() { return path; }
	public int						timeComplex() { return cTime; }
	public int						sizeComplex() { return cSize; }
	public BoardField				getInitBoard() { return rival.data; }
}

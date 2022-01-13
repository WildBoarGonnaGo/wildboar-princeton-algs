import java.util.LinkedList;
import java.io.File;
import java.util.ArrayList;

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
		public				Node(BoardField data, int move) {
			this.data = data;
			this.move = move;
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
		ArrayList<BoardField>	visited = new ArrayList(n * n);
		BoardField				destBoard = getInstance(var, destM, n);
		MinPQ<Node>				rivalQueue = new MinPQ<>();
		Node					last = null;
		rivalQueue.enqueue(rival);
		path = new LinkedList<>();
		while (!rivalQueue.isEmpty()) {
			Node	jedy = rivalQueue.dequeue();
			visited.add(jedy.data);
			if (jedy.data.equals(destBoard)) {
				solvable = true;
				while (last != null) {
					path.addFirst(last.data);
					++moves;
					last = last.prev;
				}
				return ;
			}
			MinPQ<BoardField>	jedyQueue = jedy.data.neighbors(jedy.move + 1);
			cSize += jedyQueue.size();
			while (!jedyQueue.isEmpty()) {
				if (visited.isEmpty() || !visited.contains(jedyQueue.min())) {
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

	public static void				main(String[] args) {
		if (args.length != 2) {
			System.err.println("NPuzzle: Error: wrong number of arguments");
			System.exit(1) ;
		}
		File inputFile = new File(args[0]);
		if (!inputFile.exists()) {
			System.err.println("NPuzzle: Error: No such file");
			System.exit(1) ;
		}
		NPuzzle	solve = null;
		if (args[1].equals("-MAN")) solve = new NPuzzle(new Manhattan(args[0]), 1);
		else if (args[1].equals("-LIN")) solve = new NPuzzle(new LinearDisplace(args[0]), 2);
		else if (args[1].equals("-EUC")) solve = new NPuzzle(new Euclidian(args[0]), 3);
		else if (args[1].equals("-OUT")) solve = new NPuzzle(new OutRowOutColumn(args[0]), 4);
		else {
			System.err.println("Usage: NPuzzle file -[MAN|LIN|EUC|OUT]");
			System.exit(1);
		}
		if (!solve.isSolvable()) {
			System.out.println("Puzzle:");
			System.out.print(solve.getInitBoard());
			System.out.println("... is unsolvable");
		} else {
			String	delimeter = "-------------";
			System.out.println("Represented sequence of moves: ");
			for (BoardField state : solve.solveList()) {
				System.out.print(state.toString());
				System.out.println(delimeter);
			}
			System.out.println("Total number of states ever selected (complexity in time): " + solve.timeComplex());
			System.out.println("Maximum number of states ever represented in memory at the same time");
			System.out.println("during the search (complexity in size): " + solve.sizeComplex());
			System.out.println("Number of moves required for transition from initial to goal state: " + solve.moves());
		}
	}
}

import java.util.LinkedList;
import java.io.File;

public class NPuzzle {
	private	boolean					solvable;
	private int						moves;
	private LinkedList<BoardField>	path;
	private int						cSize;
	private int						cTime;
	private BoardField				rival;

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
		moves = 0;
		int n = smpl.size();
		rival = getInstance(var, smpl);
		if (!rival.isSolvable()) { solvable = false; return ; }
		int[][] destM = new int[n][n];
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; ++j) {
				if (i == n - 1 && j == n - 1) destM[i][j] = 0;
				else destM[i][j] = i * n + j + 1;
			}
		}
		BoardField			destBoard = getInstance(var, destM, n);
		MinPQ<BoardField>	rivalQueue = new MinPQ<>();
		rivalQueue.enqueue(rival);
		path = new LinkedList<>();
		while (!rivalQueue.isEmpty()) {
			BoardField	jedy = rivalQueue.dequeue();
			path.addLast(jedy);
			if (jedy.equals(destBoard)) { solvable = true; return; }
			++moves;
			MinPQ<BoardField>	jedyQueue = jedy.neighbors(moves);
			cSize += jedyQueue.size();
			while (!jedyQueue.isEmpty()) {
				if (path.isEmpty() || !path.contains(jedyQueue.min()))
					{ rivalQueue.enqueue(jedyQueue.dequeue()); ++cTime; }
				else jedyQueue.dequeue();
			}
		}
	}

	public boolean					isSolvable() { return solvable; }
	public int						moves() { return moves; }
	public LinkedList<BoardField>	solveList() { return path; }
	public int						timeComplex() { return cTime; }
	public int						sizeComplex() { return cSize; }
	public BoardField				getRival() { return rival; }

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
			System.out.print(solve.getRival());
			System.out.println("... is unsolvable");
		} else {
			String	delimeter = "-------------";
			System.out.println("Represented sequence of moves: ");
			for (BoardField state : solve.solveList()) {
				System.out.println(state.toString());
				System.out.println(delimeter);
			}
			System.out.println("Total number of states ever selected (complexity in time): " + solve.timeComplex());
			System.out.println("Maximum number of states ever represented in memory at the same time");
			System.out.println("during the search (complexity in size): " + solve.sizeComplex());
			System.out.println("Number of moves required for transition from initial to goal state: " + solve.moves());
		}
	}
}

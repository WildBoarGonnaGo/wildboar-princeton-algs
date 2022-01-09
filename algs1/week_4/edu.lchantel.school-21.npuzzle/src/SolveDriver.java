import java.util.LinkedList;

public class SolveDriver {
	private	boolean					solvable;
	private int						moves;
	private LinkedList<BoardField>	path;
	private int						cTime;
	private int						cSize;

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

	SolveDriver(BoardField smpl, int var) {
		if (smpl == null) throw new NullPointerException();
		if (var < 1 || var > 4) throw new IllegalArgumentException();
		solvable = false;
		path = null;
		cTime = 0;
		cSize = 0;
		moves = 0;
		int n = smpl.size();
		int[][] destM = new int[n][n];
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; ++j) {
				if (i == n - 1 && j == n - 1) destM[i][j] = 0;
				destM[i][j] = i * n + j + 1;
			}
		}
		BoardField			destBoard = getInstance(var, destM, n);
		BoardField			rival = getInstance(var, smpl);
		BoardField			rivalTwin = getInstance(var, smpl.twin());
		MinPQ<BoardField>	rivalQueue = new MinPQ<>();
		MinPQ<BoardField>	twinQueue = new MinPQ<>();
		rivalQueue.enqueue(rival);
		twinQueue.enqueue(rivalTwin);
		path = new LinkedList<>();
		LinkedList<BoardField>	sythPlan = new LinkedList<>();
		while (!rivalQueue.isEmpty() && !twinQueue.isEmpty()) {
			BoardField	jedy = rivalQueue.dequeue();
			BoardField	syth = twinQueue.dequeue();
			path.addLast(jedy);
			sythPlan.addLast(syth);
			if (jedy.equals(destBoard)) { solvable = true; return; }
			else if (syth.equals(destBoard)) { path = null; return ; }
			++moves;
			MinPQ<BoardField>	jedyQueue = jedy.neighbors(moves);
			MinPQ<BoardField>	sythQueue = syth.neighbors(moves);
			while (!jedyQueue.isEmpty()) {
				if (path.isEmpty() || !path.getLast().equals(jedyQueue.min()))
					rivalQueue.enqueue(jedyQueue.dequeue());
				else jedyQueue.dequeue();
			}
			while(!sythQueue.isEmpty()) {
				if (sythPlan.isEmpty() || !sythPlan.getLast().equals(sythQueue.min()))
					twinQueue.enqueue(sythQueue.dequeue());
				else sythQueue.dequeue();
			}
		}
	}

	public boolean					isSolvable() { return solvable; }

	public int						moves() { return moves; }

	public LinkedList<BoardField>	solveList() { return path; }
}

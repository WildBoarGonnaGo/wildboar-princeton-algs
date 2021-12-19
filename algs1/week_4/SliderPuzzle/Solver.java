import	edu.princeton.cs.algs4.MinPQ;
import	edu.princeton.cs.algs4.In;
import	edu.princeton.cs.algs4.StdOut;
import	java.util.Comparator;
import	java.util.ArrayDeque;

public class Solver {
	private class SNode implements Comparable<SNode>
	{
		public Board board;
		public int	 moves;
		public SNode previous;

		public SNode(Board initial)
		{
			board = initial;
			moves = 0;
			previous = null;
		}

		public SNode(Board initial, int mov, SNode prev)
		{
			board = initial;
			moves = mov;
			previous = prev;
		}

		public int compareTo(SNode that)
		{
			int thisMove = this.board.manhattan() + this.moves;
			int thatMove = that.board.manhattan() + that.moves;
			if (thisMove < thatMove)	return -1;
			if (thisMove > thatMove)	return 1;
			return 0;
		}
	};
	private boolean					solvable;
	private ArrayDeque<Board>		solList;
	private	int						move;

	//find the solution to the initial board
	public Solver(Board initial) {
		solvable = false;
		solList = null;
		move = -1;
		MinPQ<SNode> nq = new MinPQ<SNode>();
		MinPQ<SNode> tq = new MinPQ<SNode>();

		nq.insert(new SNode(initial));
		tq.insert(new SNode(initial.twin()));

		while (!nq.isEmpty() && !tq.isEmpty())
		{
			SNode n = nq.delMin();
			SNode t = tq.delMin();

			// test if we have reached the goal
			if (t.board.manhattan() == 0)	break;

			if (n.board.manhattan() == 0)
			{
				// rebuild the solutions
				solList = new ArrayDeque<>();
				solList.push(n.board);
				solvable = true;
				move = n.moves;

				SNode prev = n.previous;
				while (prev != null)
				{
					solList.push(prev.board);
					move += prev.moves;
					prev = prev.previous;
				}
				break;
			}

			// putting neighbors into queues
			for (Board nbr : n.board.neighbors())
				if (n.previous == null || !n.previous.board.equals(nbr))
					nq.insert(new SNode(nbr, n.moves + 1, n));

			for (Board tbr : t.board.neighbors())
				if (t.previous == null || !t.previous.board.equals(tbr))
					tq.insert(new SNode(tbr, t.moves + 1, t));

		}
	}

	private class hammPriority implements Comparator<Board> {
		@Override
		public int	compare(Board o1, Board o2) {
			int priority_1 = move + o1.hamming();
			int priority_2 = move + o2.hamming();
			return (priority_1 - priority_2);
		}
	}


	// is the initial board solvable?
	public boolean isSolvable() { return solvable; }

	//min number of moves to solve initial board; -1 if unsolvable
	public int				moves() { return move; }

	// sequence of boards in a shortest solution; null if unsolvable
	public Iterable<Board>	solution() { return solList; }

	public static void		main(String[] args) {
		// create initial board from file
		In in = new In(args[0]);
		int n = in.readInt();
		int[][] tiles = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				tiles[i][j] = in.readInt();
		Board initial = new Board(tiles);

		// solve the puzzle
		Solver solver = new Solver(initial);

		// print solution to standard output
		if (!solver.isSolvable())
			StdOut.println("No solution possible");
		else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}
	}
}

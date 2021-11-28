import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private final int						size;
	private final int						totalSize;
	private final WeightedQuickUnionUF		monteCarloModel;
	private boolean[]						grid;
	private int								openSiteSize;

	//creates n-by-n grid, with all sites initially blocked
	public			Percolation(int n) {
		if (n <= 0) throw new IllegalArgumentException();
		size = n;
		totalSize = size * size;
		grid = new boolean[totalSize];
		monteCarloModel = new WeightedQuickUnionUF(totalSize + 2);
		openSiteSize = 0;
	}
	//opens the site (row, col) if it is not open already
	public void			open(int row, int column) {
		int	trueRow = row - 1;
		int trueColumn = column - 1;
		int	p = trueRow * size + trueColumn;

		if (trueColumn < 0 || trueColumn >= size || trueRow < 0 || trueRow >= size)
			throw new IllegalArgumentException();
		if (!isOpen(row, column)) {
			grid[p] = true;
			int connect = p + 1;

			if (trueRow == 0)
				monteCarloModel.union(trueRow * size + column, 0);
			else if (trueRow == size - 1)
				monteCarloModel.union(trueRow * size + column, totalSize + 1);
			if (row > 1 && isOpen(row - 1, column))
				monteCarloModel.union((trueRow - 1) * size + column, connect);
			if (row < size && isOpen(row + 1, column))
				monteCarloModel.union((trueRow + 1) * size + column, connect);
			if (column < size && isOpen(row, column + 1))
				monteCarloModel.union(trueRow * size + column + 1, connect);
			if (column > 1 && isOpen(row, column - 1))
				monteCarloModel.union(trueRow * size + column - 1, connect);
			++openSiteSize;
		}
	}
	//is the site (row, col) open?
	public boolean		isOpen(int row, int column) {
		int	trueRow = row - 1;
		int trueColumn = column - 1;
		int	p = trueRow * size + trueColumn;

		if (trueColumn < 0 || trueColumn >= size || trueRow < 0 || trueRow >= size)
			throw new IllegalArgumentException();
		return (grid[p]);
	}
	//is the site (row, col) full?
	public boolean		isFull(int row, int column) {
		int	trueRow = row - 1;
		int trueColumn = column - 1;
		int	p = trueRow * size + column;

		if (trueColumn < 0 || trueColumn >= size || trueRow < 0 || trueRow >= size
			|| p < 1 || p > totalSize + 1)
			throw new IllegalArgumentException();

		return (monteCarloModel.find(trueRow * size + column) == monteCarloModel.find(0));
	}
	// returns the number of open sites
	public int			numberOfOpenSites() {
		return (openSiteSize);
	}
	//does the system percolate?
	public boolean		percolates() {
		return (monteCarloModel.find(0) == monteCarloModel.find(totalSize + 1));
	}

	public static void	main(String[] args) {
		int size = 0;

		if (args.length != 1) {
			System.out.println("Size of program wasn't set. default size for percolation simple test is 7");
			return;
		}
		size = Integer.decode(args[0]).intValue();
		Percolation	someObj = new Percolation(size);

		System.out.println("Monte Carlo's square before percolation");
		for (int i = 0; i < size; ++i) {
			for (int j = 0; j < size; ++j)
				System.out.print(someObj.isOpen(i + 1, j + 1) ? '.' : 'f');
			System.out.print('\n');
		}

		while (!someObj.percolates()) {
			int	row = StdRandom.uniform(1, size + 1);
			int column = StdRandom.uniform(1, size + 1);
			someObj.open(row, column);
		}
		System.out.println("Monte Carlo's square after percolation");
		for (int i = 0; i < size; ++i) {
			for (int j = 0; j < size; ++j)
				System.out.print(someObj.isOpen(i + 1, j + 1) ? '.' : 'f');
			System.out.print('\n');
		}
		System.out.println("Opened sites: " + someObj.numberOfOpenSites());
	}
}

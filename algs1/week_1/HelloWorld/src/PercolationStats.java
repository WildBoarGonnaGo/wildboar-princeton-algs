import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import java.lang.Math;

public class PercolationStats {
	private final int		trials;
	private final double[]	fractions;

	//perform independent trials on an n-by-n grid
	public PercolationStats(int n, int trials) {
		if (n <= 0 || trials <= 0)
			throw new IllegalArgumentException();
		Percolation	arrTrial = null;

		this.trials = trials;
		fractions = new double[trials];
		for (int i = 0; i < trials; ++i) {
			arrTrial = new Percolation(n);
			while (!arrTrial.percolates()) {
				int	p = StdRandom.uniform(1, n + 1);
				int q = StdRandom.uniform(1, n + 1);
				arrTrial.open(p, q);
			}
			fractions[i] = arrTrial.numberOfOpenSites() / (double)(n * n);
		}
	}

	//sample mean of percolation threshold
	public double	mean() {
		return StdStats.mean(fractions);
	}

	//sample standard deviation of percolation threshold
	public double	stddev() {
		return StdStats.stddev(fractions);
	}

	//low endpoint of 95% confidence interval
	public double	confidenceLo() {
		return (mean() - 1.96 * stddev() / Math.sqrt((double)this.trials));
	}

	//high endpoint of 95% confidence interval
	public double	confidenceHi() {
		return (mean() + 1.96 * stddev() / Math.sqrt((double)this.trials));
	}

	public static void main(String[] args) {
		if (args.length != 2) {
			return;
		}
		PercolationStats	test = new PercolationStats(Integer.decode(args[0]).intValue(),
			Integer.decode(args[1]).intValue());
		System.out.println(String.format("%-23s = %f", "mean", test.mean()));
		System.out.println(String.format("%-23s = %f", "stddev", test.stddev()));
		System.out.println(String.format("%-23s = [%f, %f]", "95% confidence interval",
			test.confidenceLo(), test.confidenceHi()));
	}
}

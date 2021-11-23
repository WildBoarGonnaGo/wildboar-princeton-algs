import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Scanner;

public class RandomWord {
	public static void main(String[] args) {
		String	champion = "";
		double	i = 1.0;

		while (!StdIn.isEmpty()) {
			String rival = StdIn.readString();
			champion = (StdRandom.bernoulli(1.0 / (i++))) ? rival : champion;
		}
		StdOut.println(champion);
	}
}

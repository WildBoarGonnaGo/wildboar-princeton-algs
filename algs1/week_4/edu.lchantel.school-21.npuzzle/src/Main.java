import java.io.File;

public class Main {
	public static void main(String args[]) {
		if (args.length != 2) {
			System.err.println("NPuzzle: Error: wrong number of arguments");
			System.err.println("Usage: Main file -[MAN|LIN|EUC|OUT]");
			System.exit(1) ;
		}
		File inputFile = new File(args[0]);
		if (!inputFile.exists()) {
			System.err.println("NPuzzle: Error: No such file");
			System.err.println("Usage: Main file -[MAN|LIN|EUC|OUT]");
			System.exit(1) ;
		}
		NPuzzle	solve = null;
		if (args[1].equals("-MAN")) solve = new NPuzzle(new Manhattan(args[0]), 1);
		else if (args[1].equals("-LIN")) solve = new NPuzzle(new LinearDisplace(args[0]), 2);
		else if (args[1].equals("-EUC")) solve = new NPuzzle(new Euclidian(args[0]), 3);
		else if (args[1].equals("-OUT")) solve = new NPuzzle(new OutRowOutColumn(args[0]), 4);
		else {
			System.err.println("Usage: Main file -[MAN|LIN|EUC|OUT]");
			System.exit(1);
		}
		XtermPrinter print = new XtermPrinter(solve);
		print.visualize();
	}
}

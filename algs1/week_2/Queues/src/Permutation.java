import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class Permutation {
	public static void main(String[] args) {
		if (args.length != 1) {
			return ;
		}
		RandomizedQueue<String>	queue = new RandomizedQueue<>();
		Integer					dequeueNumber = Integer.parseInt(args[0]);
		while (!StdIn.isEmpty())
			queue.enqueue(StdIn.readString());
		while (dequeueNumber-- != 0)
			StdOut.println(queue.dequeue());
	}
}

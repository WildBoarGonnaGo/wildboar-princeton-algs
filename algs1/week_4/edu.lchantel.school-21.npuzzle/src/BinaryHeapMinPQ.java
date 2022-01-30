import java.util.Scanner;
import java.util.Random;

public class BinaryHeapMinPQ <Key extends Comparable<Key> >{
	private Key[]	arr;
	private int		capacity;
	private int		size;

	BinaryHeapMinPQ(int capacity) {
		this.capacity = capacity;
		size = 0;
		arr = (Key[]) new Comparable[this.capacity + 1];
	}

	BinaryHeapMinPQ() { this(1); }

	public boolean	isEmpty() { return size == 0; }

	private boolean	less(int i, int j) {
		return arr[i].compareTo(arr[j]) < 0;
	}

	private	void	exch(int i, int j) {
		Key	buffer = arr[i];
		arr[i] = arr[j];
		arr[j] = buffer;
	}

	/**
	 * Proposition is - the smallest key in a[1], so we
	 * swim smaller values up to the root of tree
	 * */
	private void	orderUp(int k) {
		while(k > 1 && less(k, k / 2)) {
			exch(k, k/2);
			k = k / 2;
		}
	}

	private void	orderDown(int k) {
		//Check children of k'th node (start from 2k)
		while (2 * k <= size) {
			int j = 2 * k;
			if (j < size && less(j + 1, j)) ++j;
			if (less(k, j)) break ;
			exch(k, j);
			k = j;
		}
	}

	private void	resize(int size) {
		int		tmpSize = (this.size > size) ? size : this.size;
		Key[]	tmp = arr;
		arr = (Key []) new Comparable[size];
		for (int i = 0; i < tmpSize; ++i) arr[i] = tmp[i];
	}

	public void		enqueue(Key value) {
		if (++size == capacity) { capacity *= 2; resize(capacity); }
		arr[size] = value;
		orderUp(size);
	}

	public Key		dequeue() {
		Key res = arr[1];
		exch(1, size--);
		orderDown(1);
		arr[size + 1] = null;
		return res;
	}

	public Key		min() { return arr[1]; }

	public static void main(String[] args) {
		Random rand = new Random();
		int num = rand.nextInt(25);
		BinaryHeapMinPQ<Integer> test = new BinaryHeapMinPQ<>();
		for (int i = 0; i < num; ++i) test.enqueue(rand.nextInt(50));
		System.out.print("Test contains in ascending order: ");
		while (!test.isEmpty()) {
			System.out.print(test.dequeue().toString());
			System.out.print((test.isEmpty()) ? '\n' : ' ');
		}
	}
}

public class BinaryHeapMinPQ <Key extends Comparable<Key> >{
	private Key[]	arr;
	private int		capacity;
	private int		size;

	BinaryHeapMinPQ(int capacity) {
		this.capacity = capacity;
		size = 0;
		arr = (Key[]) new Comparable[this.capacity];
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
	 * Proposition is - the smallest key in a[1]
	 * */
	private void	swim(int k) {
		while(k > 1 && less(k, k / 2)) {
			exch(k, k/2);
			k = k / 2;
		}
	}
}

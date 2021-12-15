import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class QuickSort {
	private static int		partition(Comparable[] arr, int lo, int hi) {
		int i = lo, j = hi + 1;
		while(true) {
			while(less(arr[++i], arr[lo])) { if (i == hi) break; }
			while(less(arr[lo], arr[--j])) { if (j == lo) break; }
			if (i >= j) break;
			exch(arr, i, j);
		}
		exch(arr, lo, j);
		return j;
	}

	private static void		sort(Comparable[] arr, int lo, int hi) {
		if (lo >= hi) return;
		int	mid = partition(arr, lo, hi);
		sort(arr, lo, mid - 1);
		sort(arr, mid + 1, hi);
	}

	public static void		quickSort(Comparable[] arr) {
		StdRandom.shuffle(arr);
		sort(arr, 0, arr.length - 1);
	}

	public static void		nutsAndBolts(Comparable[] nuts, Comparable[] bolts) {
		StdRandom.shuffle(nuts);
		StdRandom.shuffle(bolts);
		quickSortNutsAndBolts(nuts, bolts, 0, nuts.length - 1);
	}

	public static void		quickSortNutsAndBolts(Comparable[] nuts, Comparable[] bolts, int lo, int hi) {
		if (lo >= hi) return ;
		int mid = nutsAndBoltsPartition(nuts, lo, hi, bolts[lo]);
		nutsAndBoltsPartition(bolts, lo, hi, nuts[mid]);
		quickSortNutsAndBolts(nuts, bolts, lo, mid - 1);
		quickSortNutsAndBolts(nuts, bolts, mid + 1, hi);
	}

	public static int		nutsAndBoltsPartition(Comparable[] arr, int lo, int hi, Comparable rval) {
		int i = hi;
		for (int j = hi; j > lo; --j) {
			if (less(rval, arr[j])) exch(arr, j, i--);
			if (rval.compareTo(arr[j]) == 0) exch(arr, j++, lo);
		}
		exch(arr, lo, i);
		return i;
	}

	private static boolean	less(Comparable a, Comparable b) { return a.compareTo(b) < 0; }

	private static void		exch(Comparable[] arr, int i, int j) {
		Comparable buffer = arr[i];
		arr[i] = arr[j];
		arr[j] = buffer;
	}

	public static void main(String[] args) {
		int	size = StdRandom.uniform(10);
		Comparable[]	arr = new Comparable[size];
		StdOut.print("Array before sort: ");
		for (int i = 0; i < size; ++i) {
			arr[i] = Integer.valueOf(StdRandom.uniform(20));
			StdOut.print(arr[i].toString() + ((i == size - 1) ? '\n' : ' '));
		}
		quickSort(arr);
		StdOut.print("Array after sort: ");
		for (int i = 0; i < size; ++i)
			StdOut.print(arr[i].toString() + ((i == size - 1) ? '\n' : ' '));
		StdOut.println("BOTLS AND NUTS IMPLEMENTATION:");
		size = StdRandom.uniform(2, 10);
		Comparable[]	nuts = new Comparable[size];
		for (int i = 0; i < size; ++i) nuts[i] = StdRandom.uniform(size * 4);
		Comparable[]	bolts = nuts.clone();
		StdRandom.shuffle(bolts);
		StdOut.print("Nuts array before sort: ");
		for (int i = 0; i < size; ++i)
			StdOut.print(nuts[i].toString() + ((i == size - 1) ? '\n' : ' '));
		StdOut.print("Bolts array before sort: ");
		for (int i = 0; i < size; ++i)
			StdOut.print(bolts[i].toString() + ((i == size - 1) ? '\n' : ' '));
		nutsAndBolts(nuts, bolts);
		StdOut.print("Nuts array after sort: ");
		for (int i = 0; i < size; ++i)
			StdOut.print(nuts[i].toString() + ((i == size - 1) ? '\n' : ' '));
		StdOut.print("Bolts after before sort: ");
		for (int i = 0; i < size; ++i)
			StdOut.print(bolts[i].toString() + ((i == size - 1) ? '\n' : ' '));

	}
}

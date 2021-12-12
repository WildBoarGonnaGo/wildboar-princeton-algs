import java.util.Random;

public class Inversions {
	private static int merge(Comparable[] arr, int lo, int hi) {
		int mid = hi - lo / 2;
		int inversions = 0;
		int indxMid = mid;
		Comparable[]	aux = new Comparable[mid + 1];
		for (int i = lo; i < mid; ++i)  aux[i] = arr[i];
		for (int i = lo; i < hi; ++i) {
			if (lo > mid) arr[i] = arr[indxMid++];
			else if (indxMid > hi) arr[i] = aux[lo++];
			else if (aux[lo].compareTo(arr[indxMid]) < 0) arr[i] = aux[lo++];
			else { arr[i] = arr[indxMid++]; ++inversions; }
		}
		return inversions;
	}

	private static boolean		less(Comparable a, Comparable b) { return (a.compareTo(b) < 0); }

	/*private static int	count(Comparable[] arr, int lo, int hi) {
		int mid = (hi - lo) / 2;
		if (mid == 0) return 0;
		int inv = 0;
		inv += count(arr, lo, mid);
		inv += count(arr, mid + 1, hi);
		for (int i = 0; i < len; ++i) aux[i] = arr[i];
		count= merge(aux, 0, len);
		return count;
	}*/



	private static int	sort(Comparable[] arr) {
		int len = arr.length;
		int inv = 0;
		for (int i = 0; i < len; ++i) {
			for (int j = i; j > 0; --j)
				if (less(arr[j], arr[j - 1])) { exch(arr, j, j - 1); ++inv; }
		}
		return (inv);
	}

	public static void	exch(Comparable[] arr, int a, int b) {
		Comparable buffer = a;

		buffer = arr[a];
		arr[a] = arr[b];
		arr[b] = buffer;
	}

	public static void 	main(String[] args) {
		Random			random = new Random();
		int				size = random.nextInt(10);
		Comparable[]	arr = new Comparable[size];
		System.out.print("Array before mergesort: ");
		for (int i = 0; i < size; ++i) {
			arr[i] = Integer.valueOf(random.nextInt(20));
			System.out.print(arr[i].toString() + ((i == size - 1) ? '\n' : ' '));
		}
		int auxSize = size / 2;
		Comparable[]	copy = new Comparable[auxSize];
		for (int i = 0; i < auxSize; ++i) copy[i] = arr[i];
		//mergesort(copy, 0, size - 1);
		System.out.print("Array after mergesort: ");
		for (int i = 0; i < size; ++i) System.out.print(copy[i].toString()
			+ ((i == size - 1) ? '\n' : ' '));
	}
}

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Inversion {
    private static final int    CUTOFF = 3;

    private static int      sort(Comparable arr[], int lo, int hi) {
        int count = 0;
        for (int i = lo; i <= hi; ++i) {
            for (int j = i; j > lo; --j) {
                if (less(arr[j], arr[j - 1])) {
                    exch(arr, j, j - 1);
                    ++count;
                }
            }
        }
        return count;
    }

    private static void     exch(Comparable[] arr, int i, int j) {
        Comparable  buffer = arr[i];
        arr[i] = arr[j];
        arr[j] = buffer;
    }

    private static boolean  less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    private static int      count(Comparable arr[], Comparable aux[], int lo, int hi) {
        int result = 0;
        if (hi - lo <= CUTOFF) {
            result = insertionSort(aux, lo, hi);
            return (result);
        }
        int mid = lo + (hi - lo) / 2;
        if (lo >= hi) return 0;
        result += count(arr, aux, lo, mid);
        result += count(arr, aux, mid + 1, hi);
        result += merge(arr, aux, mid, lo, hi);
        return (result);
    }

    private static int insertionSort(Comparable[] arr, int lo, int hi) {
        int inv = 0;
        for (int i = lo; i <= hi; ++i) {
            for (int j = i; j > lo; --j) {
                if (less(arr[j], arr[j - 1])) {
                    exch(arr, j, j - 1);
                    ++inv;
                }
            }
        }
        return inv;
    }

    private static int merge(Comparable arr[], Comparable[] aux, int mid, int lo, int hi) {
        int inv = 0;

        for (int i = lo; i <= hi; ++i) aux[i] = arr[i];
        int x = lo, y = mid + 1;
        for (int i = lo; i <= hi; ++i) {
            if (x > mid) arr[i] = aux[y++];
            else if (y > hi) arr[i] = aux[x++];
            else if (less(aux[y], aux[x])) { arr[i] = aux[y++]; inv += mid - x + 1; }
            else arr[i] = aux[x++];
        }
        return (inv);
    }

    private static int count(Comparable arr[]) {
        Comparable[]    aux = arr.clone();
        Comparable[]    aux2 = aux.clone();
        int result = count(aux, aux2, 0, arr.length - 1);
        return (result);
    }

    public static void main(String[] args) {
        int size = StdRandom.uniform(10);
        Comparable[]    arr = new Comparable[size];
        for (int i = 0; i < size; ++i) arr[i] = Integer.valueOf(StdRandom.uniform(size * 2));
        StdOut.print("Array before sort: ");
        for (int i = 0; i < size; ++i) StdOut.print(arr[i].toString()
                + ((i == size - 1) ? '\n' : ' '));
        int inv = count(arr);
        MergeSort.mergesort(arr, 0, arr.length - 1);
        StdOut.print("Array after sort: ");
        for (int i = 0; i < size; ++i) StdOut.print(arr[i].toString()
                + ((i == size - 1) ? '\n' : ' '));
        StdOut.println("Number of inversions: " + inv);
    }
}

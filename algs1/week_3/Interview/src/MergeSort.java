import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.LinkedList;

public class MergeSort {
    private static void     sort(Comparable arr[], int lo, int hi) {
        for (int i = lo; i <= hi; ++i) {
            for (int j = i; j > lo; --j) {
                if (less(arr[j], arr[j - 1]))
                    exch(arr, j, j - 1);
            }
        }
    }

    private static void     exch(Comparable[] arr, int i, int j) {
        Comparable  buffer = arr[i];
        arr[i] = arr[j];
        arr[j] = buffer;
    }

    private static boolean  less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    public static void     mergesort(Comparable arr[], int lo, int hi) {
        int mid = lo + (hi - lo) / 2;
        if (lo >= hi) return ;
        mergesort(arr, lo, mid);
        mergesort(arr, mid + 1, hi);
        merge(arr, lo, hi);
    }

    private static void     merge(Comparable arr[], int lo, int hi) {
        int midLen = (hi - lo) / 2;
        Comparable[]    aux = new Comparable[midLen + 1];
        for (int i = 0; i <= midLen; ++i)
            aux[i] = arr[lo + i];
        midLen = lo + midLen;
        int oldLo = lo;
        int indxMid = midLen + 1;
        for (int i = lo; i <= hi; ++i) {
            if (lo > midLen) arr[i] = arr[indxMid++];
            else if (indxMid > hi) arr[i] = aux[lo++ - oldLo];
            else if (less(arr[indxMid], aux[lo - oldLo])) arr[i] = arr[indxMid++];
            else arr[i] = aux[lo++ - oldLo];
        }
    }

    public static void main(String[] args) {
        int size = StdRandom.uniform(10);
        Comparable[]    arr = new Comparable[size];
        for (int i = 0; i < size; ++i) arr[i] = Integer.valueOf(StdRandom.uniform(size * 2));
        StdOut.print("Array before sort: ");
        for (int i = 0; i < size; ++i) StdOut.print(arr[i].toString()
                + ((i == size - 1) ? '\n' : ' '));
        mergesort(arr, 0, arr.length - 1);
        StdOut.print("Array after sort: ");
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < size; ++i) {
            StdOut.print(arr[i].toString()
                + ((i == size - 1) ? '\n' : ' '));
            list.push(Integer.valueOf(arr[i].toString()));
        }
        Shuffle.shuffle(list);
        arr = list.toArray(new Integer[0]);
        StdOut.print("Array after shuffle: ");
        for (int i = 0; i < size; ++i) StdOut.print(arr[i].toString()
                    + ((i == size - 1) ? '\n' : ' '));
    }
}

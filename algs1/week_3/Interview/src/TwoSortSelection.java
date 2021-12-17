import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class TwoSortSelection {
    private static void         merge(Comparable[] a, Comparable[]b, Comparable[] cat, int lo, int hi) {
        int i = lo, mid = lo + a.length - 1, j = mid + 1;
        for (int k = lo; k <= hi; ++k) {
            if (i > mid) a[k] = a[j++];
            else if (j > hi) a[k] = b[i++];
            else if (less(a[j], b[i])) a[k] = a[j++];
            else a[k] = b[i++];
        }
    }

    private static Comparable   min(Comparable a, Comparable b) { return ((less(a, b)) ? a : b); }

    private static Comparable   kthOfTwo(int k, Comparable[] a, int aLo, int aHi,
                                         Comparable[] b, int bLo, int bHi) {
        int aLen = aHi - aLo, bLen = bHi - bLo;
        if (k > (aLen + bLen) || k < 1) return -1;
        if (aLen > bLen) return kthOfTwo(k, b, bLo, bHi, a, aLo, aHi);
        if (aLen == 0) return b[k - 1];
        if (k == 1) return min(a[aLo], b[bLo]);
        int aDiff = (int)min(k / 2, aLen);
        int bDiff = k - aDiff;
        if (less(b[bLo + bDiff - 1], a[aLo + aDiff - 1])) return kthOfTwo(k - bDiff, a, aLo,
                aHi, b, bLo + bDiff, bHi);
        else return kthOfTwo(k - aDiff, a, aLo + aDiff,
                aHi, b, bLo, bHi);
    }

    private static boolean      less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    private static void         exch(Comparable[] arr, int i, int j) {
        Comparable buffer = arr[i];
        arr[i] = arr[j];
        arr[j] = buffer;
    }

    public static void main(String[] args) {
        int size = StdRandom.uniform(1,10);
        Comparable[]    a = new Comparable[size];
        int aLen = a.length;
        for (int i = 0; i < aLen;  ++i) a[i] = Integer.valueOf(StdRandom.uniform(20));
        StdOut.print("Array 'a' before sort: ");
        for (int i = 0; i < aLen; ++i) StdOut.print(a[i].toString() + ((i == size - 1) ? '\n' : ' '));
        QuickSort.quickSort(a);
        StdOut.print("Array 'a' after sort: ");
        for (int i = 0; i < aLen; ++i) StdOut.print(a[i].toString() + ((i == size - 1) ? '\n' : ' '));
        size = StdRandom.uniform(1, 10);
        Comparable[]    b = new Comparable[size];
        int bLen = b.length;
        for (int i = 0; i < bLen;  ++i) b[i] = Integer.valueOf(StdRandom.uniform(20));
        StdOut.print("Array 'b' before sort: ");
        for (int i = 0; i < bLen; ++i) StdOut.print(b[i].toString() + ((i == size - 1) ? '\n' : ' '));
        QuickSort.quickSort(b);
        StdOut.print("Array 'b' after sort: ");
        for (int i = 0; i < bLen; ++i) StdOut.print(b[i].toString() + ((i == size - 1) ? '\n' : ' '));
        Comparable[]    stacked = new Comparable[aLen + bLen];
        for (int i = 0; i < aLen; ++i) stacked[i] = a[i];
        for (int i = aLen; i < aLen + bLen; ++i) stacked[i] = b[i - aLen];
        int k = StdRandom.uniform(0, aLen + bLen - 1);
        StdOut.println("rank of array 'k': " + (k + 1));
        StdOut.print("Array 'stacked' before sort: ");
        for (int i = 0; i < stacked.length; ++i) StdOut.print(stacked[i].toString() + ((i == stacked.length - 1) ? '\n' : ' '));
        Comparable rankey = kthOfTwo(k + 1, a, 0, a.length, b, 0, b.length);
        QuickSort.quickSort(stacked);
        StdOut.print("Array 'stacked' after sort: ");
        for (int i = 0; i < stacked.length; ++i) StdOut.print(stacked[i].toString() + ((i == stacked.length - 1) ? '\n' : ' '));
        StdOut.println("rankey = " + rankey);
        StdOut.println("List of all decimal dominants (keys with n / 10 or greater frequency) in 'stacked' array: "
                + QuickSelect.listOfDecDominants(stacked).toString());
    }
}

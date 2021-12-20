import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.LinkedList;


public class QuickSelect {

    private static void    quickSort3way(Comparable[] arr,LinkedList<Comparable> result,
                                         int lo, int hi) {
        int freq = arr.length / 10;
        if (hi == lo && freq <= 1) { result.push(arr[lo]); return ; }
        if (hi <= lo) return ;
        int i = lo, gt = hi, lt = lo;
        while (i <= gt) {
            int cmp = arr[i].compareTo(arr[lo]);
            if (cmp < 0) exch(arr, lt++, i++);
            else if (cmp > 0) exch(arr, i, gt--);
            else i++;
        }
        if (gt - lt + 1 > freq) result.addLast(arr[lt]);
        quickSort3way(arr, result, lo,lt - 1);
        quickSort3way(arr, result, gt + 1, hi);
    }

    private static void     exch(Comparable[] arr, int i, int j) {
        Comparable buffer = arr[i];
        arr[i] = arr[j];
        arr[j] = buffer;
    }

    public static       LinkedList<Comparable> listOfDecDominants(Comparable[] a) {
        StdRandom.shuffle(a);
        LinkedList<Comparable>  result = new LinkedList<>();
        quickSort3way(a, result, 0, a.length - 1);
        return result;
    }
}

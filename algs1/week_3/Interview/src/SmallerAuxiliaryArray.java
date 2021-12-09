/*
Merging with smaller auxiliary array. Suppose that the subarray a[0] to a [n−1] is sorted and the subarray
a[n] to a[2*n−1] is sorted. How can you merge the two subarrays so that  a[0] to
a[2*n−1] is sorted using an auxiliary array of length  n (instead of  2n)
* */

public class SmallerAuxiliaryArray {
	private static void merge(Comparable[] a) {
        int arrMid = a.length / 2;
        Comparable[]    aux = new Comparable[arrMid];
        for (int i = 0; i < arrMid; ++i)
            aux[i] = a[i];
        int hi = a.length;
        int lo = 0;
        int mid = arrMid;
        for (int i = 0; i < hi; ++i) {
                if (mid < hi && lo < arrMid) {
                    int diff = aux[lo].compareTo(a[mid]);
                    if (lo < arrMid && diff <= 0) a[i] = aux[lo++];
                    else if (mid < hi && diff > 0) a[i] = a[mid++];
                } else {
                    if (lo < arrMid) a[i] = aux[lo++];
                    else if (mid < hi) a[i] = a[mid++];
                }
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello world");
        Comparable  ex[] = new Comparable[10];
        ex[0] = 1; ex[1] = 3; ex[2] = 5; ex[3] = 7; ex[4] = 9;
        ex[5] = 2; ex[6] = 4; ex[7] = 6; ex[8] = 8; ex[9] = 10;
        System.out.println("Array before merge:");
        for (int i = 0; i < 10; ++i)
            System.out.print(ex[i].toString() + ((i == 9) ? '\n' : ' '));
        merge(ex);
        System.out.println("Array after merge:");
        for (int i = 0; i < 10; ++i)
            System.out.print(ex[i].toString() + ((i == 9) ? '\n' : ' '));
    }
}

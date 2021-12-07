/*
Merging with smaller auxiliary array. Suppose that the subarray a[0] to a [n−1] is sorted and the subarray
a[n] to a[2*n−1] is sorted. How can you merge the two subarrays so that  a[0] to
a[2*n−1] is sorted using an auxiliary array of length  n (instead of  2n)
* */


public class SmallerAuxiliaryArray {
	public static void merge(Comparable[] target) {
		int				auxLength = target.length / 2;
		int				arrlen = target.length;
		Comparable[]	aux = new Comparable[auxLength];
		for (int i = 0; i < auxLength; ++i)
			aux[i] = target[i];
		int start , mid = auxLength;
		for (int i = 0; i < arrlen; ++i) {
			if (start > )
		}
	}
}

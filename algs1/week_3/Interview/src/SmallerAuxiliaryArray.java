/*
Merging with smaller auxiliary array. Suppose that the subarray a[0] to a [n−1] is sorted and the subarray
a[n] to a[2*n−1] is sorted. How can you merge the two subarrays so that  a[0] to
a[2*n−1] is sorted using an auxiliary array of length  n (instead of  2n)
* */

public class SmallerAuxiliaryArray {
	private static void	merge(Comparable[] target) {
		int				auxLength = target.length / 2;
		int				arrlen = target.length;
		Comparable[]	aux = new Comparable[auxLength];
		for (int i = 0; i < auxLength; ++i)
			aux[i] = target[i];
		int start = 0, mid = auxLength;
		for (int i = 0; i < auxLength; ++i) {
				if (aux[start].compareTo(target[mid]) <= 0) target[i] = aux[start++];
				else {
					Comparable buffer = aux[start];
					aux[(start == 0) ? start : --start] = target[mid];
					target[mid++] = buffer;
				}
			}
		}
	private class IntegerCompare implements Comparable<Integer> {
		Integer	lval;

		public IntegerCompare() { lval = 0; }
		public IntegerCompare(Integer lval) { this.lval = lval; }

		@Override
		public int	compareTo(Integer rval) {
			return lval - rval;
		}

		int		getLVal() { return lval; }
		void	setLVal(Integer lval) { this.lval = lval; }
	}

	public static void main(String[] args) {
		Comparable[]	arr = new Comparable[10];
		for (int i = 5; i < 10; ++i) arr[i] = Integer.valueOf(i - 5);
		for (int i = 0; i < 5; ++i) arr[i] = Integer.valueOf(i + 5);
		System.out.println("Array before mergesort:");
		for (int i = 0; i < 10; ++i) System.out.print(arr[i].toString() + ((i == 9) ? '\n' : ' '));
		merge(arr);
		System.out.println("Array after mergesort:");
		for (int i = 0; i < 10; ++i) System.out.print(arr[i].toString() + ((i == 9) ? '\n' : ' '));
	}
}

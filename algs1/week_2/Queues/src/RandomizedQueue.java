import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

	private Item[]	arr;
	private int		size;
	//construct an empty randomized queue
	public RandomizedQueue() { size = 0; arr = (Item[]) new Object[1]; }

	//is the randomized queue empty?
	public boolean	isEmpty() { return size == 0; }

	//return the number of items on the randomized queue
	public int		size() { return size; }

	//add the item
	public void		enqueue(Item item) {
		if (item == null)
			throw new IllegalArgumentException();
		if (size == arr.length) resize(arr.length * 2);
		arr[size++] = item;
	}

	//user defined array resize function
	private void	resize(int amorSize) {
		if (size == arr.length) {
			int oldSize = (arr.length > amorSize) ? amorSize : arr.length;
			Item[] newArr = (Item[]) new Object[amorSize];
			for (int i = 0; i < oldSize; ++i)
				newArr[i] = arr[i];
			arr = newArr;
		}
	}

	// remove and return a random item
	public Item		dequeue() {
		if (isEmpty())
			throw new NoSuchElementException();
		if (size == arr.length / 4) resize (arr.length / 2);
		int		indx = StdRandom.uniform(size);
		Item	result = arr[indx];
		arr[indx] = arr[size - 1];
		arr[size-- - 1] = null;
		return result;
	}

	//return a random item (but do not remove it)
	public Item		sample() {
		if (isEmpty())
			throw new NoSuchElementException();
		int		indx = StdRandom.uniform(size);
		Item	result = arr[indx];
		return (result);
	}

	//return an independent iterator over items in random order
	public Iterator<Item> iterator() { return new RandomizedQueueIterator(); }

	private class RandomizedQueueIterator implements Iterator<Item> {
		private Item[]	arrCopy;
		int				iterSize;

		RandomizedQueueIterator() {
			iterSize = size();

			arrCopy = (Item[]) new Object[iterSize];
			for (int i = 0; i < iterSize; ++i)
				arrCopy[i] = arr[i];
		}

		@Override
		public boolean	hasNext() { return  iterSize != 0; }

		@Override
		public void		remove() { throw new UnsupportedOperationException(); }

		@Override
		public Item		next() {
			if (!hasNext())
				throw new NoSuchElementException();
			int		indx = StdRandom.uniform(iterSize);
			Item	result = arrCopy[indx];
			arrCopy[indx] = arrCopy[iterSize - 1];
			arrCopy[iterSize-- - 1] = null;
			return result;
		}
	}

	public static void main(String[] args) {
		RandomizedQueue<Integer>	test = new RandomizedQueue<>();
		test.enqueue(1);
		test.enqueue(2);
		test.enqueue(3);
		test.enqueue(4);
		test.enqueue(5);

		Iterator<Integer>	it = test.iterator();
		while (it.hasNext()) {
			StdOut.print(it.next());
			StdOut.print((it.hasNext()) ? ' ' : '\n');
		}
		StdOut.print("Does it.next() throws an exception? ");
		try { it.next(); System.out.println("NO"); }
		catch (NoSuchElementException exc) {
			String	errMsg = "YES (" + exc.getClass().getName() + ')';
			StdOut.println(errMsg);
		}
		StdOut.print("Does it.remove() throws an exception? ");
		try { it.remove(); System.out.println("NO"); }
		catch (UnsupportedOperationException exc) {
			String	errMsg = "YES (" + exc.getClass().getName() + ')';
			StdOut.println(errMsg);
		}
		StdOut.print("sample() function test (7 times): ");
		for (int i = 0; i < 7; ++i) {
			StdOut.print(test.sample());
			StdOut.print((i == 6) ? '\n' : ' ');
		}
		StdOut.println("dequeue() tests with size(). Size of Queue before dequeue() is: " + test.size());
		while (!test.isEmpty()) {
			StdOut.print(test.dequeue());
			StdOut.print("(size = ");
			StdOut.print(test.size());
			StdOut.print(')');
			StdOut.print((!test.isEmpty()) ? ' ' : '\n');
		}
		StdOut.print("Does test.dequeue() throws an exception? ");
		try { test.dequeue(); System.out.println("NO"); }
		catch (NoSuchElementException exc) {
			String	errMsg = "YES (" + exc.getClass().getName() + ')';
			StdOut.println(errMsg);
		}
		StdOut.print("Does test.sample() throws an exception? ");
		try { test.sample(); System.out.println("NO"); }
		catch (NoSuchElementException exc) {
			String	errMsg = "YES (" + exc.getClass().getName() + ')';
			StdOut.println(errMsg);
		}
		StdOut.print("Does test.enqueue(null) throws an exception? ");
		try { test.enqueue(null); System.out.println("NO"); }
		catch (IllegalArgumentException exc) {
			String	errMsg = "YES (" + exc.getClass().getName() + ')';
			StdOut.println(errMsg);
		}
	}
}

import java.util.NoSuchElementException;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
	private class Node {
		Item item;
		Node next;
		Node prev;
	}
	private 	Node front, back;
	private int	size;

	//construct an empty deque
	public Deque() { size = 0; }

	//is the deque empty
	public boolean isEmpty() {
		return (front == null);
	}

	//return the number of item in Deque
	public int		size() { return size; }
	//add the item to the front
	public void	addFirst(Item item) {
		if (item == null)
			throw new IllegalArgumentException();
		if (isEmpty()) {
			front = new Node();
			front.item = item;
			back = front;
		} else {
			Node oldFront = front;
			front = new Node();
			front.item = item;
			front.next = oldFront;
		}
		++size;
	}

	//add the item to the back
	public void addLast(Item item) {
		if (item == null)
			throw new IllegalArgumentException();
		if (isEmpty()) {
			front = new Node();
			front.item = item;
			back = front;
		} else {
			Node oldLast = back;
			back = new Node();
			back.item = item;
			oldLast.next = back;
			back.prev = oldLast;
		}
		++size;
	}

	//remove and return the item from the front
	public Item	removeFirst() {
		if (isEmpty())
			throw new NoSuchElementException();
		Node	remFirst = front;
		Item	result = front.item;
		front = front.next;
		remFirst = null;
		if (front == null || front.next == null)
			back = front;
		--size;
		return (result);
	}

	//remove and return the item from the back
	public Item	removeLast() {
		Item	item;
		if (isEmpty())
			throw new NoSuchElementException();
		if (back == front) {
			item = front.item;
			back = (front = null);
			return (item);
		}
		Node	oldLast = back;
		item = back.item;
		back = back.prev;
		if (back != null)
			back.next = null;
		oldLast = null;
		--size;
		return (item);
	}

	//return an independent iterator over items in random order
	public Iterator<Item>	iterator() { return new DequeIterator(); }

	private class DequeIterator implements Iterator<Item> {
		private Node	start;

		DequeIterator() { start = front; }

		@Override
		public boolean	hasNext() { return start != null; }

		@Override
		public void		remove() { throw new UnsupportedOperationException(); }

		@Override
		public Item		next() {
			if (!hasNext())
				throw new NoSuchElementException();
			Item	item = start.item;
			start = start.next;
			return (item);
		}
	}

	public static void main(String[] args) {
		Deque<Integer>	test = new Deque<>();

		test.addFirst(5);
		test.addFirst(4);
		test.addFirst(3);
		test.addLast(6);
		test.addLast(7);


		Iterator<Integer>	roll = test.iterator();
		StdOut.println("Initialized base Deque.");
		StdOut.println("test.size() = " + test.size());
		while (roll.hasNext()) {
			StdOut.print(roll.next());
			StdOut.print((roll.hasNext()) ? ' ' : '\n');
		}
		StdOut.print("Was an exception thrown: 'roll.next()'? ");
		try { roll.next(); StdOut.println("NO");}
		catch (NoSuchElementException exc) {
			String	msg = "YES (" + exc.getClass().toString() + ')';
			StdOut.println(msg); }
		StdOut.print("Was an exception thrown: 'test.addFirst(null)'? ");
		try { test.addFirst(null); StdOut.println("NO");}
		catch (IllegalArgumentException exc) {
			String	msg = "YES (" + exc.getClass().toString() + ')';
			StdOut.println(msg); }

		StdOut.print("Was an exception thrown: 'test.addLast(null)'? ");
		try { test.addLast(null); StdOut.println("NO");}
		catch (IllegalArgumentException exc) {
			String	msg = "YES (" + exc.getClass().toString() + ')';
			StdOut.println(msg);
		}

		test.removeFirst();
		test.removeLast();
		roll = test.iterator();
		StdOut.println("Same Deque with first and last elements been removed.");
		StdOut.println("test.size() = " + test.size());
		while (roll.hasNext()) {
			StdOut.print(roll.next());
			StdOut.print((roll.hasNext()) ? ' ' : '\n');
		}

		StdOut.print("Was an exception thrown: 'roll.remove'? ");
		try { roll.remove(); StdOut.println("NO");}
		catch (UnsupportedOperationException exc) {
			String	msg = "YES (" + exc.getClass().toString() + ')';
			StdOut.println(msg);
		}

		test.removeFirst();
		test.removeLast();
		roll = test.iterator();
		StdOut.println("Same Deque with first and last elements been removed.");
		StdOut.println("test.size() = " + test.size());
		while (roll.hasNext()) {
			StdOut.print(roll.next());
			StdOut.print((roll.hasNext()) ? ' ' : '\n');
		}
		test.removeLast();
		StdOut.print("Was an exception thrown: 'test.removeLast()'? ");
		try { test.removeLast(); StdOut.println("NO");}
		catch (NoSuchElementException exc) {
			String	msg = "YES (" + exc.getClass().toString() + ')';
			StdOut.println(msg);
		}
		StdOut.print("Was an exception thrown: 'test.removeFirst()'? ");
		try { test.removeFirst(); StdOut.println("NO");}
		catch (NoSuchElementException exc) {
			String	msg = "YES (" + exc.getClass().toString() + ')';
			StdOut.println(msg);
		}
	}
}
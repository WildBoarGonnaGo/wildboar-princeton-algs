public class QueueTwoAmorStack<Item> {
	private class Stack<Item> {
		private int		size;
		private	Item	arr[];

		public Stack() { arr = (Item[])new Object[1]; size = 0;}

		public boolean		isEmpty() {
			return (size == 0);
		}

		public void			push(Item item) {
			int	oldSize = arr.length;

			if (size == oldSize) resize((oldSize == 0) ? 1 : oldSize * 2);
			arr[size++] = item;
		}

		private	void		resize(int newSize) {
			int		iterSize = (arr.length > newSize) ? newSize : arr.length;
			Item[]	result = (Item[]) new Object[newSize];
			for (int i = 0; i < iterSize; ++i)
				result[i] = arr[i];
			arr = result;
		}

		public Item			pop() {
			Item	result = null;

			if (!isEmpty()) {
				int	arrSize = arr.length;
				result = arr[--size];
				arr[size] = null;
				if (size == arrSize / 4) resize(arrSize / 2);
			}
			return (result);
		}
	}
	private Stack<Item>	target, box;

	public QueueTwoAmorStack() {
		target = new Stack<>();
		box = new Stack<>();
	}

	public boolean	isEmpty() {
		return target.isEmpty();
	}

	void	enqueue(Item item) {
		while (!target.isEmpty())
			box.push(target.pop());
		target.push(item);
		while (!box.isEmpty())
			target.push(box.pop());
	}

	Item	dequeue() {
		if (target.isEmpty()) return (null);
		return (target.pop());
	}
}

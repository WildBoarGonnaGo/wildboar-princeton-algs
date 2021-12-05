public class StackWithMax<Item> {
	Double	max;
	Node	first;
	private class Node {
		Item	item;
		Node	next;
	}

	public StackWithMax() { max = 0.0; }

	public boolean	isEmpty() {
		return first == null;
	}

	public void		push(Item item) {
		Node oldFirst = first;
		first = new Node();
		first.item = item;
		if (max < Double.parseDouble(item.toString()))
			max = Double.parseDouble(item.toString());
		first.next = oldFirst;
	}

	public Item		pop() {
		if (isEmpty()) {
			max = 0.0;
			return null;
		}
		Item result = first.item;
		if (max == Double.parseDouble(first.item.toString())) {
			Node tmp = first.next;
			max =  0.0;
			while (tmp != null) {
				if (max < Double.parseDouble(tmp.item.toString()))
					max = Double.parseDouble(tmp.item.toString());
				tmp = tmp.next;
			}
		}
		first = first.next;
		return (result);
	}

	public Double	getMax() { return max; }
}

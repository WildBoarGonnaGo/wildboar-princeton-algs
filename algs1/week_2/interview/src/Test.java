public class Test {
	public static void main(String[] args) {
		QueueTwoAmorStack<Integer>	queueTest = new QueueTwoAmorStack<>();
		queueTest.enqueue(1);
		queueTest.enqueue(2);
		queueTest.enqueue(3);
		queueTest.enqueue(4);

		for (int i = 0; i < 4; ++i)
			System.out.println("Output of queueTest.dequeue() = " + queueTest.dequeue());

		StackWithMax<Integer>	stackTest = new StackWithMax<>();
		stackTest.push(1);
		stackTest.push(2);
		stackTest.push(3);
		stackTest.push(4);
		stackTest.push(3);
		stackTest.push(2);
		stackTest.push(1);
		System.out.println("stackTest.getMax() = " + stackTest.getMax());

		for (int i = 0; i < 7; ++i) {
			System.out.print("stackTest.pop() = " + stackTest.pop());
			System.out.println(", stackTest.getMax = " + stackTest.getMax());
		}
	}
}

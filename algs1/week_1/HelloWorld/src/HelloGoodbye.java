public class HelloGoodbye {
	public static void main(String[] args) {
		System.out.println((args.length == 2) ? "Hello " + args[0]
			+ " and " + args[1] + ".\nGoodbye " + args[1]
			+ " and " + args[0] + '.' : "");
	}
}

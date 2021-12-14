import java.util.Iterator;
import java.util.LinkedList;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class Shuffle<Item> {
    LinkedList<Item>    shuffleList;

    private Shuffle() { ; }

    public Shuffle(LinkedList<Item> shuffleList) {
        this.shuffleList = shuffleList;
    }

    public static void shuffle(LinkedList list) {
        if (list == null)
            throw new IllegalArgumentException("null argument is inacceptable");
        if (list.size() == 1) return ;
        LinkedList list1 = new LinkedList();
        LinkedList list2 = new LinkedList();;
        while (!list.isEmpty()) {
            list1.push(list.poll());
            if (!list.isEmpty()) list2.push(list.poll());
        }
        shuffle(list1);
        shuffle(list2);
        while (!list1.isEmpty() || !list2.isEmpty()) {
            int coinToss = StdRandom.uniform(1);
            if (coinToss == 0 && !list1.isEmpty()) list.push(list1.poll());
            else list.push(list2.poll());
        }
    }
}

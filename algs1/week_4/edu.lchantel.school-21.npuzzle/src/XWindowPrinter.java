import javax.swing.JFrame;
import javax.swing.ImageIcon;
import java.awt.*;
import java.util.LinkedList;
import java.lang.IllegalArgumentException;
import java.util.Iterator;

public class XWindowPrinter extends JFrame {
    private class Coord {
        public int x;
        public int y;
    };
    private LinkedList<Coord>    list;

    public XWindowPrinter(NPuzzle puzzle) {
        if (puzzle == null) throw new IllegalArgumentException();
        list = new LinkedList<>();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Iterator it = list.iterator();
        while (it.hasNext()) {

        }
    }

    public static void main(String[] args)
    {

    }
}

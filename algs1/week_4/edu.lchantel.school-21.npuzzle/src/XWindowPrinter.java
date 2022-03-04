import javax.swing.JFrame;
import javax.swing.ImageIcon;
import java.awt.*;
import java.util.LinkedList;
import java.lang.IllegalArgumentException;
import java.util.Iterator;
import java.util.Arrays;

public class XWindowPrinter extends JFrame {
    private class Coord {
        private int x;
        private int y;

        public Coord(int x, int y) { this.x = x; this.y = y; }
        public int getX() { return x; }
        public int getY() { return y; }
    };
    private LinkedList<Coord>       list;
    private LinkedList<BoardField>  boardList;

    public XWindowPrinter(NPuzzle puzzle) {
        if (puzzle == null) throw new IllegalArgumentException();
        list = new LinkedList<>();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        boardList = puzzle.solveList();
        for (int i = 0; i < boardList.size(); ++i) {
            int[][] tmp = boardList.get(i).getTiles();
            int     tmpSize = boardList.get(i).size();
            for (int j = 0; j < tmpSize; ++j) {
                int found = Arrays.binarySearch(tmp[j], 0);
                if (found >= 0) {
                    list.addLast(new Coord(j, found));
                    break ;
                }
            }
        }

    }

    public static void main(String[] args) {

    }
}

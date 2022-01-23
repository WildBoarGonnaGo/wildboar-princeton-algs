import java.lang.Thread;
import java.lang.InterruptedException;

public class XtermPrinter implements BoardPrinter {
    NPuzzle                     solver;
    private final int           sp;
    private final int           size;
    private static final String ANSI_ESC = "\u001b";
    private static final String ANSI_RESET = "[0m";

    private int     countSp() {
        int res = 0;
        int tmp = size * size - 1;
        while (tmp > 0) { tmp /= 10; ++res; }
        return res + 1;
    }

    XtermPrinter(NPuzzle solver) {
        if (solver == null) throw new NullPointerException();
        this.solver = solver;
        size = solver.getInitBoard().size();
        sp = countSp();
    }

    private String  ANSI_Colored(boolean bold, int color, String text) {
        return ANSI_ESC + ((bold == true) ? "[1;38;5;" : "[38;5;") + color + 'm'
            + text + ANSI_ESC + ANSI_RESET;
    }

    private String  ANSI_erase() { return ANSI_ESC + "[0K"; }

    private String  ANSI_savePos() { return ANSI_ESC + "7";}

    private String  ANSI_recoverPos() { return ANSI_ESC + "8"; }

    @Override
    public void     print(BoardField o) {
        StringBuilder	build = new StringBuilder();
        int[][] board = o.getTiles();
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if (board[i][j] == 0 || (i == size - 1 && j == -1))
                    build.append(padRight(" "));
                else if (board[i][j] != i * size + j + 1)
                    build.append(padRight(board[i][j], true, 196));
                else build.append(padRight(board[i][j], true, 82));
            }
            if (i != size - 1) build.append('\n');
        }
        System.out.print(ANSI_recoverPos() + build.toString());
    }

    private String  padRight(int number, boolean bold, int color) {
        String  numStr = Integer.toString(number);
        int     len = sp - numStr.length();
        while (len-- > 0) numStr += ' ';
        return ANSI_Colored(bold, color, numStr);
    }

    private String  padRight(String str) {
        int     len = sp - str.length();
        while (len-- > 0) str += ' ';
        return str;
    }

    @Override
    public void     clear() { System.out.print(ANSI_recoverPos() + ANSI_erase()); }

    @Override
    public void     misc() {
        System.out.println(String.format("%-55s%25s ", "Time complexity (total number of states visited):",
            ANSI_Colored(true, 202, Integer.toString(solver.timeComplex()))));
        System.out.println(String.format("%-55s%25s", "Size complexity (total number of states overviewed):",
            ANSI_Colored(true, 202, Integer.toString(solver.sizeComplex()))));
        System.out.println(String.format("%-55s%25s", "Number of moves to goal",
            ANSI_Colored(true, 202, Integer.toString(solver.moves()))));
    }

    @Override
    public void     visualize() {
        int i = 0;
        Thread  timer = new Thread();
        if (solver.isSolvable()) {
            for (BoardField roll : solver.solveList()) {
                if (i == 0) {
                    System.out.println("Size of the board: "
                        + ANSI_Colored(true, 228, Integer.toString(roll.size())));
                    System.out.print(ANSI_savePos());
                    print(roll);
                } else { clear(); print(roll); }
                try { timer.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
                ++i;
            }
            System.out.print("\n");
            misc();
        } else {
            System.out.println("Puzzle:");
            System.out.print(ANSI_savePos());
            print(solver.getInitBoard());
            System.out.println("\n... is " + ANSI_Colored(true, 196, "unsolvable"));
        }
    }
}

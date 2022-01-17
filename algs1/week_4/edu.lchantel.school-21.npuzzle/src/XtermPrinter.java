public class XtermPrinter implements BoardPrinter {
    NPuzzle solver;
    private static final String ANSI_ESC = "\\u001b[";

    XtermPrinter(NPuzzle solver) {
        if (solver == null) throw new NullPointerException();
        this.solver = solver;
    }

    private String  ANSI_CursorMove(int row, int col) {
        return ANSI_ESC + '{' + row + "};{" + col + "}H";
    }

    private String  ANSI_Colored(boolean bold, byte color, String text) {
        return ANSI_ESC + ((bold == true) ? "1;38;5;" : "38;5;") + color + 'm'
            + text + ANSI_ESC + "0m";
    }

    private String  ANSI_eraseRow() {
        return ANSI_ESC + ()
    }
}

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
}

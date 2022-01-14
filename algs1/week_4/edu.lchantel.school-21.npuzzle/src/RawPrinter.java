public class RawPrinter implements BoardPrinter {
    NPuzzle solution;
    String  delimeter;

    RawPrinter(NPuzzle solution) { this.solution = solution; delimeter = "-----------"; }

    @Override
    public void     print(BoardField o) {
        if (o == null) throw new NullPointerException();
        System.out.print(o.toString());
        System.out.println(delimeter);
    }

    @Override
    public void     misc() {
        System.out.println("Time complexity (total number of states visited): " + solution.timeComplex());
        System.out.println("Size complexity (total number of states overviewed): " + solution.sizeComplex());
        System.out.println("Number of moves to goal state: " + solution.moves());
    }

    @Override
    public void     clear() { return; }

    @Override
    public  void    visualize() {
        for (BoardField smpl : solution.solveList()) print(smpl);
        misc();
    }
}

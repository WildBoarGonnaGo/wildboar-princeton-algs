import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public abstract class BoardField implements Comparable<BoardField> {
    protected int[][]           tiles;
    protected int               n;
    protected int               move;
    protected boolean           solvable;
    protected int               zeroRow;

    public                      BoardField(int[][] tiles, int n) {
        if (n == 0 || tiles == null) throw new IllegalArgumentException();
        this.n = n;
        this.tiles = new int[n][n];
        move = 0;
        zeroRow = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                this.tiles[i][j] = tiles[i][j];
                if (tiles[i][j] == 0) zeroRow = n - i;
            }
        }
        solvable = checkSolve();
    }

    public                      BoardField(int[][] tiles, int n, int move) {
        if (n == 0 || tiles == null) throw new IllegalArgumentException();
        this.n = n;
        this.tiles = new int[n][n];
        this.move = move;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                this.tiles[i][j] = tiles[i][j];
                if (move == 0 && tiles[i][j] == 0) zeroRow = n - i;
            }
        }
        if (move == 0) solvable = checkSolve();
    }

    public                      BoardField(String fileName) {
        Scanner scan = null;
        try {
            scan = new Scanner(new File(fileName));
            scan.useDelimiter("\\u000A");
            while (scan.hasNext("(#.*)?")) scan.nextLine();
            if (scan.hasNext("(\\d)+(#.*)?")) n = scan.nextInt();
            else throw new IllegalArgumentException("Error: wrong board file syntax: wrong size input");
            tiles = new int[n][n];
            String tmp = Integer.toString(n - 1);
            for (int i = 0; i < n; ++i) {
                if (scan.hasNext("\\s*(\\d+\\s+){" + tmp + "}?\\d+(\\s+#.*)?")) {
                    scan.useDelimiter("\\s+");
                    for (int j = 0; j < n; ++j) {
                        tiles[i][j] = scan.nextInt();
                        if (tiles[i][j] == 0) zeroRow = n - i;
                    }
                } else throw new IllegalArgumentException("Error: wrong board file syntax: wrong row input");
                if (scan.hasNext("(#.*)+"))
                    scan.nextLine();
                scan.useDelimiter("\\u000A");
            }
            solvable = checkSolve();
        } catch (FileNotFoundException exc) { exc.printStackTrace(); }
    }

    public                      BoardField(BoardField that) {
        if (this.equals(that)) return;
        n = that.n;
        move = that.move;
        tiles = new int[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) tiles[i][j] = that.tiles[i][j];
        }
        solvable = checkSolve();
    }

    public boolean              compFields(int[][] that) {
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j)
                if (tiles[i][j] != that[i][j]) return false;
        }
        return true;
    }

    @Override
    public boolean              equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardField boardField = (BoardField) o;
        return n == boardField.n && compFields(boardField.tiles);
    }

    @Override
    public int                  hashCode() {
        int result = Objects.hash(n);
        result = 31 * result + Arrays.hashCode(tiles);
        return result;
    }

    protected int[]              binSearch(int v, int lo, int hi) {
        if (lo > hi) return null;
        int[]   manh = null;
        int res, mid = lo + (hi - lo) / 2, exRow = (v == 0) ? n - 1 : (v - 1) / n,
        exCol = (v == 0) ? n - 1 : (v - 1) % n, row = mid / n, col = mid % n;
        if (tiles[row][col] == v) {
            manh = new int[2];
            manh[0] = Math.abs(row - exRow);
            manh[1] = Math.abs(col - exCol);
            return manh;
        }
        if (manh == null) manh = binSearch(v, lo, mid - 1);
        if (manh == null) manh = binSearch(v, mid + 1, hi);
        return manh;
    }

    public int                  move() { return this.move; }

    protected void              exch(int[][] arr, int irow, int icol,
                             int jrow, int jcol) {
        int buffer = arr[irow][icol];
        arr[irow][icol] = arr[jrow][jcol];
        arr[jrow][jcol] = buffer;
    }

    private void                exch1d(int[] arr, int i, int j) {
        int buffer = arr[i];
        arr[i] = arr[j];
        arr[j] = buffer;
    }

    abstract MinPQ<BoardField>  neighbors(int move);

    abstract BoardField         twin();

    public int                  size() { return n; }

    private int                 merge(int[] arr, int[] aux, int mid, int lo, int hi) {
        int res = 0;
        for (int i = lo; i <= hi; ++i) aux[i] = arr[i];
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; ++k) {
            if (i > mid) arr[k] = aux[j++];
            else if (j > hi) arr[k] = aux[i++];
            else if (aux[j] < aux[i]) { arr[k] = aux[j++]; res += mid - i + 1; }
            else arr[k] = aux[i++];
        }
        return res;
    }

    private int                 mergeSort(int[] arr, int[] aux, int lo, int hi) {
        int inv = 0;
        if (lo >= hi) return inv;
        int mid = lo + (hi - lo) / 2;
        inv += mergeSort(arr, aux, lo, mid);
        inv += mergeSort(arr, aux, mid + 1, hi);
        inv += merge(arr, aux, mid, lo, hi);
        return inv;
    }

    /*
    N - width of grid
    1. if N is odd, the puzzle instance is solvable if number of inversions is even in input state
    2. if N is even, puzzle instance is solvable if:
        - the blank is on the even row counting from bottom and number of inversions is odd
        - the blang is on odd row counting from the bottom, and number of inversions is even
    3. For all other cases, the puzzle instance is not solvable
    * */
    private boolean             checkSolve() {
        int res = 0, indx = 0, len = n * n - 1;
        int[] inspect = new int[len];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j)
                if (tiles[i][j] != 0) inspect[indx++] = tiles[i][j];
        }
        int[] aux = Arrays.copyOf(inspect, len);

        res = mergeSort(inspect, aux,0, indx - 1) + ((n % 2 == 0) ? zeroRow : 0);
        aux = null;
        inspect = null;
        return ((n % 2 == 0) ? res % 2 != 0 : res % 2 == 0);
    }

    @Override
    public String               toString() {
        StringBuilder   buildStr = new StringBuilder();
        buildStr.append(n);
        buildStr.append('\n');
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                String  form = String.format("%3d", tiles[i][j]);
                buildStr.append(form);
            }
            buildStr.append('\n');
        }
        return buildStr.toString();
    }

    public boolean      isSolvable() { return solvable; }

    public int[][]      getTiles() { return tiles; }
}

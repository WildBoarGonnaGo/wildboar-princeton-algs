import java.util.Arrays;
import java.util.Objects;

public class Board {
    private int[][] tiles;
    int             n;

    Board(int[][] tiles, int n) {
        if (n == 0 || tiles == null) throw new IllegalArgumentException();
        this.n = n;
        tiles = new int[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) this.tiles[i][j] = tiles[i][j];
        }
    }

    public boolean  compFields(int[][] that) {
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j)
                if (tiles[i][j] != that[i][j]) return false;
        }
        return true;
    }

    @Override
    public boolean  equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return n == board.n && compFields(board.tiles);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(n);
        result = 31 * result + Arrays.hashCode(tiles);
        return result;
    }
}


public abstract class Model_PlayerAbstract extends Model_Abstract implements Model_Player
{
    protected String name;
    protected int m, n;         // Number of lines and number of columns for the grid the player will play in.
    protected short[][] grid;   // The Tetris grid colors.

    protected Model_Tetromino currentTetromino;
    protected int currentTetrominoPositionLine, currentTetrominoPositionColumn;
    protected long tetrominoLastFallingTime = 0;
    protected long timeLastCheckForTetrominoShouldBeChanged = 0;

    /**
     * Create player.
     */
    public Model_PlayerAbstract(String name, int m, int n) {
        this.name = name;
        this.m = m;
        this.n = n;
        this.grid = new short[m][n];
        for (int i = 0; i < this.grid.length; i++) {
            for (int j = 0; j < this.grid[0].length; j++) {
                grid[i][j] = Model_Color.NONE;
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        String s = "";
        if (this.getName() != null) {
            s += this.getName() + "\n";
        }
        s += "Grid:\n";
        for (int i = 0; i < this.grid.length; i++) {
            for (int j = 0; j < this.grid[0].length; j++) {
                if (grid[i][j] == Model_Color.NONE) {
                    s += ".";
                } else {
                    s += grid[i][j];
                }
            }
            s += "\n";
        }
        return s;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public short[][] getGrid() {
        return grid;
    }

    public void setGrid(short[][] grid) {
        this.grid = grid;
    }

    public Model_Tetromino getCurrentTetromino() {
        return currentTetromino;
    }

    public void setCurrentTetromino(Model_Tetromino currentTetromino) {
        this.currentTetromino = currentTetromino;
    }

    public int getCurrentTetrominoPositionLine() {
        return currentTetrominoPositionLine;
    }

    public void setCurrentTetrominoPositionLine(int currentTetrimonoPositionLine) {
        this.currentTetrominoPositionLine = currentTetrimonoPositionLine;
    }

    public int getCurrentTetrominoPositionColumn() {
        return currentTetrominoPositionColumn;
    }

    public void setCurrentTetrominoPositionColumn(int currentTetrominoPositionColumn) {
        this.currentTetrominoPositionColumn = currentTetrominoPositionColumn;
    }

    public long getTetrominoLastFallingTime() {
        return tetrominoLastFallingTime;
    }

    public void setTetrominoLastFallingTime(long tetrominoLastFallingTime) {
        this.tetrominoLastFallingTime = tetrominoLastFallingTime;
    }

    public long getTimeLastCheckForTetrominoShouldBeChanged() {
        return timeLastCheckForTetrominoShouldBeChanged;
    }

    public void setTimeLastCheckForTetrominoShouldBeChanged(long timeLastCheckForTetrominoShouldBeChanged) {
        this.timeLastCheckForTetrominoShouldBeChanged = timeLastCheckForTetrominoShouldBeChanged;
    }
}

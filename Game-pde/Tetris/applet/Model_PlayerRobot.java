
public abstract class Model_PlayerRobot extends Model_PlayerAbstract
{
    protected int nextTetrominoPositionLine, nextTetrominoPositionColumn;

    /**
     * Create robot player.
     */
    public Model_PlayerRobot(String name, int m, int n) {
        super(name, m, n);
    }

    public int getNextTetrominoPositionLine() {
        return nextTetrominoPositionLine;
    }

    public void setNextTetrominoPositionLine(int nextTetrominoPositionLine) {
        this.nextTetrominoPositionLine = nextTetrominoPositionLine;
    }

    public int getNextTetrominoPositionColumn() {
        return nextTetrominoPositionColumn;
    }

    public void setNextTetrominoPositionColumn(int nextTetrominoPositionColumn) {
        this.nextTetrominoPositionColumn = nextTetrominoPositionColumn;
    }
}

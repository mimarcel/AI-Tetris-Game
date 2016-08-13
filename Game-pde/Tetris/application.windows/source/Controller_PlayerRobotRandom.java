

public class Controller_PlayerRobotRandom extends Controller_PlayerRobot
{
    public static int counter = 0;
    protected int countedAs;

    /**
     * Create controller.
     */
    public Controller_PlayerRobotRandom(Controller_Tetris controller_tetris, Model_PlayerRobotRandom model) throws Model_Exception {
        super(controller_tetris, model);
        counter++;
        this.countedAs = counter;
    }

    public Model_PlayerRobotRandom getModel() {
        return (Model_PlayerRobotRandom) super.getModel();
    }

    public View_PlayerRobotRandom getView() {
        return (View_PlayerRobotRandom) super.getView();
    }

    public String getPlayerName() throws Model_Exception {
        String playerName = super.getPlayerName();
        if (playerName == null) {
            playerName = Controller_Tetris.translate("Robot Random") + (countedAs != 1 ? " " + countedAs : "");
        }
        return playerName;
    }

    public void findNextMove() throws Model_Exception {
        int line = this.getModel().getCurrentTetrominoPositionLine();
        int column = this.getModel().getCurrentTetrominoPositionColumn();
        int newLine, newColumn;
        int tries = 0;
        do {
            tries++;
            int maxRandomLine = Controller_Tetris.NUMBER_OF_LINES_TETRIS_GRID - line - 1;
            newLine = line + (maxRandomLine <= 0 ? 1 : Controller_Tetris.randomInt(maxRandomLine) + 1);
            newColumn = Controller_Tetris.randomInt(Controller_Tetris.NUMBER_OF_COLUMNS_TETRIS_GRID);
        }
        while (!this.getGridController().tetrominoCanFindAWayToPosition(
                this.getCurrentTetromino(),
                this.getModel().getCurrentTetrominoPositionLine(),
                this.getModel().getCurrentTetrominoPositionColumn(),
                newLine, newColumn, this.getModel().getGrid())
                && tries < 20);
        if (tries < 20) {
            this.getModel().setNextTetrominoPositionColumn(newColumn);
            this.getModel().setNextTetrominoPositionLine(newLine);
        } else {
            if (this.getGridController().tetrominoCanBePlacedOnPosition(
                    this.getCurrentTetromino(),
                    line + 1,
                    column,
                    this.getModel().getGrid()
            )) {
                this.getModel().setNextTetrominoPositionColumn(column);
                this.getModel().setNextTetrominoPositionLine(line + 1);
            } else {
                this.getModel().setNextTetrominoPositionColumn(column);
                this.getModel().setNextTetrominoPositionLine(line);
            }
        }
    }
}

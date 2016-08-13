

public abstract class Controller_PlayerRobot extends Controller_PlayerAbstract
{
    public long MIN_TIME_TO_MOVE = 200;
    public long MAX_TIME_TO_MOVE = 4000;
    protected long lastTimeMoved = 0;
    protected int lastSavedNumberOfMovesFromHumanPlayers = 0;

    /**
     * Create controller.
     */
    public Controller_PlayerRobot(Controller_Tetris controller_tetris, Model_PlayerRobot model) throws Model_Exception {
        super(controller_tetris, model);
        this.getGridController().disableFalling();
    }

    public Model_PlayerRobot getModel() {
        return (Model_PlayerRobot) super.getModel();
    }

    public View_PlayerRobot getView() {
        return (View_PlayerRobot) super.getView();
    }

    /**
     * Ready to make a move?
     */
    public boolean isReady() {
        boolean timeToMove = false;
        int time = this.getParentController().getView().getApp().millis();

        long diff = this.getParentController().getNumberOfMovesFromHumanPlayers() - this.lastSavedNumberOfMovesFromHumanPlayers;
        timeToMove =
                // If all human players made a move then robot should make a move as well.
                ((diff >= Controller_PlayerHuman.counter) && (time - this.lastTimeMoved >= MIN_TIME_TO_MOVE))
                        // OR if max time to move...
                        || (time - this.lastTimeMoved >= MAX_TIME_TO_MOVE);
        if (timeToMove) {
            this.lastSavedNumberOfMovesFromHumanPlayers += Controller_PlayerHuman.counter;
        }
        return timeToMove;
    }

    /**
     * Find next move. Set nextTetrominoPositionLine and nextTetrominoPositionColumn.
     * @throws Model_Exception
     */
    public abstract void findNextMove() throws Model_Exception;

    /**
     * Make next move.
     */
    public void makeNextMove() throws Model_Exception {
        this.lastTimeMoved = this.getParentController().getView().getApp().millis();
        this.getModel().setCurrentTetrominoPositionLine(
                this.getModel().getNextTetrominoPositionLine()
        );
        this.getModel().setCurrentTetrominoPositionColumn(
                this.getModel().getNextTetrominoPositionColumn()
        );
    }
}

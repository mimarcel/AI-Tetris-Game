

public abstract class View_PlayerRobot extends View_Player
{
    protected boolean nextTimeSearchMove = false;
    public boolean SEARCH_MOVE_MESSAGE_DISPLAYED = true;

    /**
     * Create view.
     */
    public View_PlayerRobot(Controller_PlayerRobot controller_player, View_Tetris view) {
        super(controller_player, view);
    }

    public Controller_PlayerRobot getController() {
        return (Controller_PlayerRobot) super.getController();
    }

    public void draw() throws Model_Exception {
        super.draw();
        if (this.nextTimeSearchMove) {
            this.getController().findNextMove();
            this.getController().makeNextMove();
            this.nextTimeSearchMove = false;
        }
        if (this.getController().getParentController().isGameStarted()
                && !this.getController().isGameOver()
                && this.getController().isReady()
                ) {
            if (SEARCH_MOVE_MESSAGE_DISPLAYED) {
                this.getController().getGridController().getView().messageOnGrid(Controller_Tetris.translate("Searching move..."));
            }
            this.nextTimeSearchMove = true;
        }
    }

    public void mousePressed() throws Model_Exception {
        super.mousePressed();
    }
}

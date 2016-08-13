package Tetris.View;

import Tetris.Controller.Controller_PlayerHuman;
import Tetris.Controller.Controller_PlayerRobot;
import Tetris.Controller.Controller_Tetris;
import Tetris.Controller.Player.Controller_Player_Grid;
import Tetris.Model.Model_Exception;
import Tetris.View.Player.View_Player_Grid;

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

    @Override
    public Controller_PlayerRobot getController() {
        return (Controller_PlayerRobot) super.getController();
    }

    @Override
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
}

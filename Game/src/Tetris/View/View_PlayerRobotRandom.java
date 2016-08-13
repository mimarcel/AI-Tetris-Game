package Tetris.View;

import Tetris.Controller.Controller_PlayerRobotRandom;
import Tetris.Model.Model_Exception;

public class View_PlayerRobotRandom extends View_PlayerRobot
{
    /**
     * Create view.
     */
    public View_PlayerRobotRandom(Controller_PlayerRobotRandom controller_player, View_Tetris view) {
        super(controller_player, view);
        SEARCH_MOVE_MESSAGE_DISPLAYED = false;
    }

    @Override
    public Controller_PlayerRobotRandom getController() {
        return (Controller_PlayerRobotRandom) super.getController();
    }

    @Override
    public void setup() throws Model_Exception {
        super.setup();
        this.getController().getGridController().enableFalling();
    }
}

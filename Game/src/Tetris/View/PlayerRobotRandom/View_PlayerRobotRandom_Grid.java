package Tetris.View.PlayerRobotRandom;

import Tetris.Controller.PlayerRobotRandom.Controller_PlayerRobotRandom_Grid;
import Tetris.View.Player.View_Player_Grid;
import Tetris.View.View_PlayerRobotRandom;

public class View_PlayerRobotRandom_Grid extends View_Player_Grid
{
    /**
     * Create controller.
     */
    public View_PlayerRobotRandom_Grid(Controller_PlayerRobotRandom_Grid controller_player_grid, View_PlayerRobotRandom view) {
        super(controller_player_grid, view);
    }

    @Override
    public View_PlayerRobotRandom getParentView() {
        return (View_PlayerRobotRandom) super.getParentView();
    }
}

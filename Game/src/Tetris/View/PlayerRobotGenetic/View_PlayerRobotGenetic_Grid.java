package Tetris.View.PlayerRobotGenetic;

import Tetris.Controller.PlayerRobotGenetic.Controller_PlayerRobotGenetic_Grid;
import Tetris.Model.Model_Exception;
import Tetris.View.Player.View_Player_Grid;
import Tetris.View.View_PlayerRobotGenetic;

public class View_PlayerRobotGenetic_Grid extends View_Player_Grid
{
    /**
     * Create controller.
     */
    public View_PlayerRobotGenetic_Grid(Controller_PlayerRobotGenetic_Grid controller_player_grid, View_PlayerRobotGenetic view) {
        super(controller_player_grid, view);
    }

    @Override
    public View_PlayerRobotGenetic getParentView() {
        return (View_PlayerRobotGenetic) super.getParentView();
    }

    @Override
    public void draw() throws Model_Exception {
        super.draw();
    }
}

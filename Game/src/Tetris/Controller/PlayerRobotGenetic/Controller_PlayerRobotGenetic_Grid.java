package Tetris.Controller.PlayerRobotGenetic;

import Tetris.Controller.Controller_PlayerRobotGenetic;
import Tetris.Controller.Player.Controller_Player_Grid;
import Tetris.Model.Model_PlayerRobotGenetic;
import Tetris.View.PlayerRobotGenetic.View_PlayerRobotGenetic_Grid;

public class Controller_PlayerRobotGenetic_Grid extends Controller_Player_Grid
{
    /**
     * Create controller.
     */
    public Controller_PlayerRobotGenetic_Grid(Controller_PlayerRobotGenetic controller_player) {
        super(controller_player);
        CHECK_TETROMINO_SHOULD_BE_CHANGED = 0;
        this.view = new View_PlayerRobotGenetic_Grid(this, this.getParentController().getView());
    }

    @Override
    public View_PlayerRobotGenetic_Grid getView() {
        return (View_PlayerRobotGenetic_Grid) super.getView();
    }

    @Override
    public Model_PlayerRobotGenetic getModel() {
        return (Model_PlayerRobotGenetic) super.getModel();
    }

    @Override
    public Controller_PlayerRobotGenetic getParentController() {
        return (Controller_PlayerRobotGenetic) super.getParentController();
    }
}

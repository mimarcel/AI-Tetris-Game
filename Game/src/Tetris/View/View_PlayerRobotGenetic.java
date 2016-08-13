package Tetris.View;

import Tetris.Controller.Controller_PlayerHuman;
import Tetris.Controller.Controller_PlayerRobotGenetic;
import Tetris.Controller.Controller_Tetris;
import Tetris.Model.Model_Exception;

public class View_PlayerRobotGenetic extends View_PlayerRobot
{
    /**
     * Create view.
     */
    public View_PlayerRobotGenetic(Controller_PlayerRobotGenetic controller_player, View_Tetris view) throws Model_Exception {
        super(controller_player, view);
        SEARCH_MOVE_MESSAGE_DISPLAYED = Controller_PlayerHuman.counter > 0
                && Controller_Tetris.NUMBER_OF_COLUMNS_TETRIS_GRID*Controller_Tetris.NUMBER_OF_LINES_TETRIS_GRID > 20*10;
    }

    @Override
    public Controller_PlayerRobotGenetic getController() {
        return (Controller_PlayerRobotGenetic) super.getController();
    }

    @Override
    public void setup() throws Model_Exception {
        super.setup();
        if (this.getController().getModel().getM()*this.getController().getModel().getN() > 30*30) {
            this.getParentView().addError(
                    new Model_Exception(
                            Controller_Tetris.translate("Robot Genetic cannot play on a grid larger than 30x30.")
                    )
            );
            this.getController().playerGameOver();
        }
    }

}

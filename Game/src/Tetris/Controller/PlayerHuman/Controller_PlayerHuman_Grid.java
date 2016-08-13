package Tetris.Controller.PlayerHuman;

import Tetris.Controller.Controller_PlayerHuman;
import Tetris.Controller.Player.Controller_Player_Grid;
import Tetris.Model.Model_Exception;
import Tetris.Model.Model_PlayerHuman;
import Tetris.View.PlayerHuman.View_PlayerHuman_Grid;

public class Controller_PlayerHuman_Grid extends Controller_Player_Grid
{
    /**
     * Create controller.
     */
    public Controller_PlayerHuman_Grid(Controller_PlayerHuman controller_player) {
        super(controller_player);
        this.view = new View_PlayerHuman_Grid(this, this.getParentController().getView());
    }

    @Override
    public View_PlayerHuman_Grid getView() {
        return (View_PlayerHuman_Grid) super.getView();
    }

    @Override
    public Model_PlayerHuman getModel() {
        return this.getParentController().getModel();
    }

    @Override
    public Controller_PlayerHuman getParentController() {
        return (Controller_PlayerHuman) super.getParentController();
    }

    @Override
    public void newTetrominoOnGrid() throws Model_Exception {
        super.newTetrominoOnGrid();
        this.getParentController().getParentController().incrementNumberOfMovesFromHumanPlayers();
    }
}

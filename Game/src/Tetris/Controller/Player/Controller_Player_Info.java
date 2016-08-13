package Tetris.Controller.Player;

import Tetris.Controller.Controller_Abstract;
import Tetris.Controller.Controller_PlayerAbstract;
import Tetris.Model.Model_Exception;
import Tetris.View.Player.View_Player_Info;

public class Controller_Player_Info extends Controller_Abstract
{

    /**
     * Create controller.
     */
    public Controller_Player_Info(Controller_PlayerAbstract controller_player) {
        super(controller_player, null, null);
        this.view = new View_Player_Info(this, this.getParentController().getView());
    }

    @Override
    public View_Player_Info getView() {
        return (View_Player_Info) super.getView();
    }

    @Override
    public Controller_PlayerAbstract getParentController() {
        return (Controller_PlayerAbstract) super.getParentController();
    }

    public String getInfo() throws Model_Exception {
        return this.getParentController().getPlayerName();
    }
}

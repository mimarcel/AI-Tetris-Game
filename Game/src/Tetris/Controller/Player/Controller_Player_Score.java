package Tetris.Controller.Player;

import Tetris.Controller.Controller_Abstract;
import Tetris.Controller.Controller_PlayerAbstract;
import Tetris.View.Player.View_Player_Score;

public class Controller_Player_Score extends Controller_Abstract
{
    /**
     * Create controller.
     */
    public Controller_Player_Score(Controller_PlayerAbstract controller_player) {
        super(controller_player, null, null);
        this.view = new View_Player_Score(this, this.getParentController().getView());
    }

    @Override
    public View_Player_Score getView() {
        return (View_Player_Score) super.getView();
    }

    @Override
    public Controller_PlayerAbstract getParentController() {
        return (Controller_PlayerAbstract) super.getParentController();
    }

    /**
     * Get score as string.
     */
    public String getScore() {
        return "" + this.getParentController().getScore();
    }
}

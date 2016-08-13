package Tetris.View;

import Tetris.Controller.Controller_PlayerHuman;
import Tetris.Model.Model_Exception;

public class View_PlayerHuman extends View_Player
{
    /**
     * Create view.
     */
    public View_PlayerHuman(Controller_PlayerHuman controller_player, View_Tetris view) {
        super(controller_player, view);
    }

    @Override
    public Controller_PlayerHuman getController() {
        return (Controller_PlayerHuman) super.getController();
    }

    @Override
    protected void addViewChildrenFromControllers() {
        super.addViewChildrenFromControllers();
        this.addViewChild(this.getController().getLegendController().getView());
    }

    @Override
    public void mousePressed() throws Model_Exception {
        super.mousePressed();
    }
}

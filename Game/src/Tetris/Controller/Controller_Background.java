package Tetris.Controller;

import Tetris.View.View_Background;

public class Controller_Background extends Controller_Abstract
{
    /**
     * Create controller.
     */
    public Controller_Background(Controller_Tetris controller_tetris) {
        super(controller_tetris, null, null);
        this.view = new View_Background(this, controller_tetris.getView());
    }

    @Override
    public View_Background getView() {
        return (View_Background) super.getView();
    }

    @Override
    public Controller_Tetris getParentController() {
        return (Controller_Tetris)super.getParentController();
    }
}

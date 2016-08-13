

public class Controller_Background extends Controller_Abstract
{
    /**
     * Create controller.
     */
    public Controller_Background(Controller_Tetris controller_tetris) {
        super(controller_tetris, null, null);
        this.view = new View_Background(this, controller_tetris.getView());
    }

    public View_Background getView() {
        return (View_Background) super.getView();
    }

    public Controller_Tetris getParentController() {
        return (Controller_Tetris)super.getParentController();
    }
}

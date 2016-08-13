

public class Controller_Restart extends Controller_Abstract
{

    /**
     * Create controller.
     */
    public Controller_Restart(Controller_Tetris controller_tetris) {
        super(controller_tetris, null, null);
        this.view = new View_Restart(this, controller_tetris.getView());
    }

    public Controller_Tetris getParentController() {
        return (Controller_Tetris) super.getParentController();
    }

    public View_Restart getView() {
        return (View_Restart) super.getView();
    }

    /**
     * Get Restart game message.
     */
    public String getRestartMessage() throws Model_Exception {
        return Controller_Tetris.translate("Restart");
    }
}

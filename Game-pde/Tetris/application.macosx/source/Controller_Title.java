

public class Controller_Title extends Controller_Abstract
{

    /**
     * Create controller.
     */
    public Controller_Title(Controller_Tetris controller_tetris) {
        super(controller_tetris, null, null);
        this.view = new View_Title(this, controller_tetris.getView());
    }

    public Controller_Tetris getParentController() {
        return (Controller_Tetris) super.getParentController();
    }

    public View_Title getView() {
        return (View_Title) super.getView();
    }

    /**
     * Get title as string.
     */
    public String getTitle() throws Model_Exception {
        return this.getParentController().translate("Tetris");
    }

    /**
     * Get title logo url.
     */
    public String getLogoUrl() {
        return this.getParentController().getMediaBaseUrl() + "/Media/Logo/Ai-Tetris-Logo.png";
    }
}

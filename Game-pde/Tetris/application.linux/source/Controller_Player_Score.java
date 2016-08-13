

public class Controller_Player_Score extends Controller_Abstract
{
    /**
     * Create controller.
     */
    public Controller_Player_Score(Controller_PlayerAbstract controller_player) {
        super(controller_player, null, null);
        this.view = new View_Player_Score(this, this.getParentController().getView());
    }

    public View_Player_Score getView() {
        return (View_Player_Score) super.getView();
    }

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



public class Controller_PlayerRobotRandom_Grid extends Controller_Player_Grid
{
    /**
     * Create controller.
     */
    public Controller_PlayerRobotRandom_Grid(Controller_PlayerRobotRandom controller_player) {
        super(controller_player);
        this.view = new View_PlayerRobotRandom_Grid(this, this.getParentController().getView());
    }

    public View_PlayerRobotRandom_Grid getView() {
        return (View_PlayerRobotRandom_Grid) super.getView();
    }

    public Model_PlayerRobotRandom getModel() {
        return (Model_PlayerRobotRandom) super.getModel();
    }

    public Controller_PlayerRobotRandom getParentController() {
        return (Controller_PlayerRobotRandom) super.getParentController();
    }
}

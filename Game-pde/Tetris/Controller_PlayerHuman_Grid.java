

public class Controller_PlayerHuman_Grid extends Controller_Player_Grid
{
    /**
     * Create controller.
     */
    public Controller_PlayerHuman_Grid(Controller_PlayerHuman controller_player) {
        super(controller_player);
        this.view = new View_PlayerHuman_Grid(this, this.getParentController().getView());
    }

    public View_PlayerHuman_Grid getView() {
        return (View_PlayerHuman_Grid) super.getView();
    }

    public Model_PlayerHuman getModel() {
        return this.getParentController().getModel();
    }

    public Controller_PlayerHuman getParentController() {
        return (Controller_PlayerHuman) super.getParentController();
    }

    public void newTetrominoOnGrid() throws Model_Exception {
        super.newTetrominoOnGrid();
        this.getParentController().getParentController().incrementNumberOfMovesFromHumanPlayers();
    }
}

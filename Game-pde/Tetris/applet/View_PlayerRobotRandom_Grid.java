

public class View_PlayerRobotRandom_Grid extends View_Player_Grid
{
    /**
     * Create controller.
     */
    public View_PlayerRobotRandom_Grid(Controller_PlayerRobotRandom_Grid controller_player_grid, View_PlayerRobotRandom view) {
        super(controller_player_grid, view);
    }

    public View_PlayerRobotRandom getParentView() {
        return (View_PlayerRobotRandom) super.getParentView();
    }
}

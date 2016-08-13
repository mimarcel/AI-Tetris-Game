

public class View_PlayerRobotGenetic_Grid extends View_Player_Grid
{
    /**
     * Create controller.
     */
    public View_PlayerRobotGenetic_Grid(Controller_PlayerRobotGenetic_Grid controller_player_grid, View_PlayerRobotGenetic view) {
        super(controller_player_grid, view);
    }

    public View_PlayerRobotGenetic getParentView() {
        return (View_PlayerRobotGenetic) super.getParentView();
    }

    public void draw() throws Model_Exception {
        super.draw();
    }
}

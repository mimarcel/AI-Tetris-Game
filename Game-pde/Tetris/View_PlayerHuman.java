

public class View_PlayerHuman extends View_Player
{
    /**
     * Create view.
     */
    public View_PlayerHuman(Controller_PlayerHuman controller_player, View_Tetris view) {
        super(controller_player, view);
    }

    public Controller_PlayerHuman getController() {
        return (Controller_PlayerHuman) super.getController();
    }

    protected void addViewChildrenFromControllers() {
        super.addViewChildrenFromControllers();
        this.addViewChild(this.getController().getLegendController().getView());
    }

    public void mousePressed() throws Model_Exception {
        super.mousePressed();
    }
}

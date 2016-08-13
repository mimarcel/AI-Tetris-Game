

public interface Controller
{
    /**
     * Get parent controller.
     */
    public Controller getParentController();

    /**
     * Get view associated with this controller.
     */
    public View getView();

    /**
     * Get model associated with this controller.
     */
    public Model getModel();
}

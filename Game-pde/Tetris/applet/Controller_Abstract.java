

public abstract class Controller_Abstract implements Controller
{
    protected Controller parentController;
    protected Model model;
    protected View view;

    /**
     * Create controller.
     */
    public Controller_Abstract() {

    }

    /**
     * Create controller with view and model.
     */
    public Controller_Abstract(Controller parentController, View view, Model model) {
        this.view = view;
        this.model = model;
        this.parentController = parentController;
    }

    public View getView() {
        return this.view;
    }

    public Model getModel() {
        return this.model;
    }

    public Controller getParentController() {
        return this.parentController;
    }

}

package Tetris.Controller;

import Tetris.Model.Model;
import Tetris.View.View;

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

    @Override
    public View getView() {
        return this.view;
    }

    @Override
    public Model getModel() {
        return this.model;
    }

    @Override
    public Controller getParentController() {
        return this.parentController;
    }

}

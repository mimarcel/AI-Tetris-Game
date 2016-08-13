

public abstract class View_Abstract implements View
{
    protected Controller controller;
    protected View parentView;

    // Actions.
    public static final int ACTION_SETUP = 1;
    public static final int ACTION_DRAW = 2;
    public static final int ACTION_MOUSE_PRESSED = 3;
    public static final int ACTION_MOUSE_MOVED = 4;
    public static final int ACTION_KEY_PRESSED = 5;
    public static final int ACTION_MOUSE_DRAGGED = 6;

    public static int[] getAllActionConstants() {
        return new int[]{ACTION_SETUP, ACTION_DRAW, ACTION_MOUSE_PRESSED, ACTION_MOUSE_MOVED, ACTION_KEY_PRESSED};
    }

    /**
     * Create view.
     */
    public View_Abstract(Controller controller, View parentView) {
        this.controller = controller;
        this.parentView = parentView;
    }

    public Controller getController() {
        return controller;
    }

    public View getParentView() {
        return parentView;
    }

    /**
     * Do action.
     */
    public Object doAction(int action) throws Model_Exception {
        switch (action) {
            case ACTION_SETUP:
                this.setup();
                break;
            case ACTION_DRAW:
                this.draw();
                break;
            case ACTION_MOUSE_PRESSED:
                this.mousePressed();
                break;
            case ACTION_MOUSE_MOVED:
                this.mouseMoved();
                break;
            case ACTION_KEY_PRESSED:
                this.keyPressed();
                break;
            case ACTION_MOUSE_DRAGGED:
                this.mouseDragged();
        }
        return null;
    }

    public void setup() throws Model_Exception {
    }

    public void draw() throws Model_Exception {
    }

    public void mousePressed() throws Model_Exception {
    }

    public void mouseMoved() throws Model_Exception {
    }

    public void keyPressed() throws Model_Exception {
    }

    public void mouseDragged() throws Model_Exception {
    }
}

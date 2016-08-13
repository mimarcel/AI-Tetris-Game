

public abstract class View_AbstractWithChildren extends View_Abstract
{
    protected View_Abstract[] views;
    protected boolean[] activeViews;
    protected int n;

    /**
     * Create view.
     */
    public View_AbstractWithChildren(Controller controller, View parentView, int maxNumberOfChildren) {
        super(controller, parentView);
        this.views = new View_Abstract[maxNumberOfChildren];
        this.activeViews = new boolean[maxNumberOfChildren];
        this.n = 0;
    }

    /**
     * Add new child view.
     */
    public void addViewChild(View_Abstract v) {
        this.views[n] = v;
        this.activeViews[n++] = true;
    }

    /**
     * Do action for all children.
     */
    public void doActionForAllChildren(int action) throws Model_Exception {
        for (int i = 0; i < n; i++) {
            if (activeViews[i]) {
                views[i].doAction(action);
            }
        }
    }

    public void setup() throws Model_Exception {
        super.setup();
        this.doActionForAllChildren(View_Abstract.ACTION_SETUP);
    }

    public void draw() throws Model_Exception {
        super.draw();
        this.doActionForAllChildren(View_Abstract.ACTION_DRAW);
    }

    public void mousePressed() throws Model_Exception {
        super.mousePressed();
        this.doActionForAllChildren(View_Abstract.ACTION_MOUSE_PRESSED);
    }

    public void mouseMoved() throws Model_Exception {
        super.mouseMoved();
        this.doActionForAllChildren(View_Abstract.ACTION_MOUSE_MOVED);
    }

    public void keyPressed() throws Model_Exception {
        super.keyPressed();
        this.doActionForAllChildren(View_Abstract.ACTION_KEY_PRESSED);
    }

    public void mouseDragged() throws Model_Exception {
        super.mouseDragged();
        this.doActionForAllChildren(View_Abstract.ACTION_MOUSE_DRAGGED);
    }
}



public class View_Background extends View_Abstract
{
    public float BACKGROUND_RED = 230;
    public float BACKGROUND_GREEN = 230;
    public float BACKGROUND_BLUE = 230;
    public float BACKGROUND_RECTANGLE_STROKE_RED = 0;
    public float BACKGROUND_RECTANGLE_STROKE_GREEN = 0;
    public float BACKGROUND_RECTANGLE_STROKE_BLUE = 0;
    public float BACKGROUND_PADDING = 5;

    /**
     * Create view.
     */
    public View_Background(Controller_Background controller_background, View_Tetris view) {
        super(controller_background, view);
    }

    public View_Tetris getParentView() {
        return (View_Tetris) super.getParentView();
    }

    public void draw() {
        // Background.
        this.getParentView().getApp().background(BACKGROUND_RED, BACKGROUND_GREEN, BACKGROUND_BLUE);

        // Rectangle.
        this.getParentView().getApp().noFill();
        this.getParentView().getApp().stroke(BACKGROUND_RECTANGLE_STROKE_RED,
                BACKGROUND_RECTANGLE_STROKE_GREEN,
                BACKGROUND_RECTANGLE_STROKE_BLUE
        );
        this.getParentView().getApp().rect(BACKGROUND_PADDING, BACKGROUND_PADDING,
                this.getParentView().WIDTH - BACKGROUND_PADDING * 2, this.getParentView().HEIGHT - BACKGROUND_PADDING * 2);
    }
}

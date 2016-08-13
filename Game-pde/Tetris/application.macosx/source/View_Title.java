
import processing.core.PApplet;
import processing.core.PImage;

public class View_Title extends View_Abstract
{
    public boolean IS_ACTIVE = true;

    protected static PImage titleImage;
    public float TITLE_HEIGHT = 50;
    public float TITLE_X = -1;
    public float TITLE_Y = -1;

    public float TITLE_IMAGE_WIDTH = 0;
    public float TITLE_IMAGE_HEIGHT = 0;
    public float TITLE_IMAGE_X1 = -1;
    public float TITLE_IMAGE_Y1 = -1;
    public float TITLE_IMAGE_X2 = -1;
    public float TITLE_IMAGE_Y2 = -1;

    protected boolean inside = false;
    public float TITLE_IMAGE_FILL_INSIDE_RED = 0;
    public float TITLE_IMAGE_FILL_INSIDE_GREEN = 0;
    public float TITLE_IMAGE_FILL_INSIDE_BLUE = 50;
    public float TITLE_IMAGE_FILL_OUTSIDE_RED = 0;
    public float TITLE_IMAGE_FILL_OUTSIDE_GREEN = 0;
    public float TITLE_IMAGE_FILL_OUTSIDE_BLUE = 0;

    /**
     * Create title view.
     */
    public View_Title(Controller_Title controller, View_Tetris view_tetris) {
        super(controller, view_tetris);
        TITLE_X = view_tetris.WIDTH / 2;
    }

    public Controller_Title getController() {
        return (Controller_Title) super.getController();
    }

    public View_Tetris getParentView() {
        return (View_Tetris) super.getParentView();
    }

    public void setup() throws Model_Exception {
        TITLE_Y = TITLE_HEIGHT / 2 + this.getParentView().getBackgroundPadding() + 2;

        if (titleImage == null || titleImage.width == 0 || titleImage.height == 0) {
            titleImage = this.getParentView().getApp().loadImage(this.getController().getLogoUrl());
            if (titleImage == null || titleImage.width == 0 || titleImage.height == 0) {
                this.getParentView().addError(
                        new Exception(
                                Controller_Tetris.translate("Title image not found.")
                        )
                );
            }
        }

        TITLE_IMAGE_WIDTH = this.getParentView().WIDTH - this.getParentView().getBackgroundPadding() * 2 - 2;
        TITLE_IMAGE_HEIGHT = TITLE_HEIGHT;
        TITLE_IMAGE_X1 = TITLE_X - TITLE_IMAGE_WIDTH / 2;
        TITLE_IMAGE_X2 = TITLE_X + TITLE_IMAGE_WIDTH / 2;
        TITLE_IMAGE_Y1 = TITLE_Y - TITLE_IMAGE_HEIGHT / 2;
        TITLE_IMAGE_Y2 = TITLE_Y + TITLE_IMAGE_HEIGHT / 2;

        //TITLE_IMAGE_FILL_OUTSIDE_RED = this.getParentView().getBackgroundColorRed();
        //TITLE_IMAGE_FILL_OUTSIDE_GREEN = this.getParentView().getBackgroundColorGreen();
        //TITLE_IMAGE_FILL_OUTSIDE_BLUE = this.getParentView().getBackgroundColorBlue();

    }

    public void draw() throws Model_Exception {
        if (!IS_ACTIVE) {
            return;
        }

        // Rectangle.
        this.fillRectangle();

        // Image.
        this.getParentView().getApp().imageMode(PApplet.CENTER);
        if (titleImage != null) {
            this.getParentView().getApp().image(titleImage, TITLE_X, TITLE_Y);
        }
    }

    public void mousePressed() throws Model_Exception {
        if (!IS_ACTIVE || titleImage == null || titleImage.width == 0) {
            return;
        }

        int mx = this.getParentView().getApp().mouseX;
        int my = this.getParentView().getApp().mouseY;

        if (titleImage != null
                && mx >= TITLE_X - titleImage.width / 2
                && mx <= TITLE_X + titleImage.width / 2
                && my >= TITLE_Y - titleImage.height / 2
                && my <= TITLE_Y + titleImage.height / 2
                ) {
            this.getParentView().getApp().link(this.getController().getParentController().getAppUrl(true));
        }
    }

    public void mouseMoved() throws Model_Exception {
        if (!IS_ACTIVE) {
            return;
        }

        int mx = this.getParentView().getApp().mouseX;
        int my = this.getParentView().getApp().mouseY;

        // Change fill rectangle.
        if (mx >= TITLE_IMAGE_X1
                && mx <= TITLE_IMAGE_X2
                && my >= TITLE_IMAGE_Y1
                && my <= TITLE_IMAGE_Y2
                ) {
            if (!this.inside) {
                this.inside = true;
                this.getParentView().getApp().redraw();
            }
        } else {
            if (this.inside) {
                this.inside = false;
                this.getParentView().getApp().redraw();
            }
        }

        if (titleImage != null
                && mx >= TITLE_X - titleImage.width / 2
                && mx <= TITLE_X + titleImage.width / 2
                && my >= TITLE_Y - titleImage.height / 2
                && my <= TITLE_Y + titleImage.height / 2
                ) {
            this.getParentView().askCursorToBe(PApplet.HAND);
        }
    }

    /**
     * Fill rectangle depending on the boolean inside.
     */
    protected void fillRectangle() {
        this.getParentView().getApp().noStroke();
        if (this.inside) {
            this.getParentView().getApp().fill(TITLE_IMAGE_FILL_INSIDE_RED, TITLE_IMAGE_FILL_INSIDE_GREEN, TITLE_IMAGE_FILL_INSIDE_BLUE);
        } else {
            this.getParentView().getApp().fill(TITLE_IMAGE_FILL_OUTSIDE_RED, TITLE_IMAGE_FILL_OUTSIDE_GREEN, TITLE_IMAGE_FILL_OUTSIDE_BLUE);
        }
        this.getParentView().getApp().rect(TITLE_IMAGE_X1, TITLE_IMAGE_Y1, TITLE_IMAGE_WIDTH, TITLE_IMAGE_HEIGHT);
    }
}

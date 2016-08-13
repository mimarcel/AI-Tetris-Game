
import processing.core.PApplet;

public class View_Player_Info extends View_Abstract
{
    public float INFO_START_X = -1;
    public float INFO_START_Y = -1;
    public float INFO_WIDTH = -1;
    public float INFO_HEIGHT = -1;

    public static final float INFO_PADDING_LEFT = 0;
    public static final float INFO_PADDING_RIGHT = 0;
    public static final float INFO_PADDING_TOP = 10;

    public float FILL_INFO_RED = -1;
    public float FILL_INFO_GREEN = -1;
    public float FILL_INFO_BLUE = -1;
    public float COLOR_INFO_RED = 0;
    public float COLOR_INFO_GREEN = 0;
    public float COLOR_INFO_BLUE = 0;
    public float INFO_TEXT_SIZE = 13;

    /**
     * Create controller.
     */
    public View_Player_Info(Controller_Player_Info controller_player_info, View_Player view) {
        super(controller_player_info, view);
    }

    public Controller_Player_Info getController() {
        return (Controller_Player_Info) super.getController();
    }

    public View_Player getParentView() {
        return (View_Player) super.getParentView();
    }

    public void setup() throws Model_Exception {
        INFO_START_X = this.getController().getParentController().getGridController().getView().GRID_START_X
                + this.getController().getParentController().getGridController().getView().GRID_WIDTH
                + View_Player_Grid.GRID_PADDING_RIGHT
                + INFO_PADDING_LEFT;
        INFO_HEIGHT = 50;
        INFO_START_Y = INFO_PADDING_TOP
                + this.getController().getParentController().getView().PLAYER_START_Y
                + this.getController().getParentController().getView().PLAYER_HEIGHT
                - this.getController().getParentController().getView().PLAYER_PADDING_DOWN
                - INFO_HEIGHT;
        INFO_WIDTH = Math.max(this.getParentView().SIZE_MATRIX_ELEMENT_X * 4, View_Player_Grid.MIN_WIDTH_TO_LEAVE_BLANK);


        FILL_INFO_RED = this.getController().getParentController().getGridController().getView().FILL_GRID_RED;
        FILL_INFO_GREEN = this.getController().getParentController().getGridController().getView().FILL_GRID_GREEN;
        FILL_INFO_BLUE = this.getController().getParentController().getGridController().getView().FILL_GRID_BLUE;
    }

    public void draw() throws Model_Exception {
        // Fill rectangle.
        this.getParentView().getParentView().useDefaultStroke();
        this.getParentView().getParentView().getApp().fill(FILL_INFO_RED, FILL_INFO_GREEN, FILL_INFO_BLUE);
        this.getParentView().getParentView().getApp().rect(INFO_START_X, INFO_START_Y, INFO_WIDTH, INFO_HEIGHT);

        // Info.
        this.getParentView().getParentView().getApp().textSize(INFO_TEXT_SIZE);
        this.getParentView().getParentView().getApp().textAlign(PApplet.CENTER, PApplet.CENTER);
        this.getParentView().getParentView().getApp().fill(COLOR_INFO_RED, COLOR_INFO_GREEN, COLOR_INFO_BLUE);
        this.getController().getParentController().getParentController();
        this.getParentView().getParentView().getApp().text(
                this.getController().getInfo(),
                INFO_START_X, INFO_START_Y, INFO_WIDTH, INFO_HEIGHT
        );
    }
}

package Tetris.View.Player;

import Tetris.Controller.Player.Controller_Player_Score;
import Tetris.Model.Model_Exception;
import Tetris.View.View_Abstract;
import Tetris.View.View_Player;
import processing.core.PApplet;

public class View_Player_Score extends View_Abstract
{
    public float SCORE_START_X = -1;
    public float SCORE_START_Y = -1;
    public float SCORE_WIDTH = -1;
    public float SCORE_HEIGHT = -1;

    public static final float SCORE_PADDING_LEFT = 0;
    public static final float SCORE_PADDING_RIGHT = 0;
    public static final float SCORE_PADDING_TOP = 10;

    public float FILL_SCORE_RED = -1;
    public float FILL_SCORE_GREEN = -1;
    public float FILL_SCORE_BLUE = -1;
    public float COLOR_SCORE_RED = 0;
    public float COLOR_SCORE_GREEN = 0;
    public float COLOR_SCORE_BLUE = 0;
    public float SCORE_TEXT_SIZE = 15;

    /**
     * Create controller.
     */
    public View_Player_Score(Controller_Player_Score controller_player_Score, View_Player view) {
        super(controller_player_Score, view);
    }

    @Override
    public Controller_Player_Score getController() {
        return (Controller_Player_Score) super.getController();
    }

    @Override
    public View_Player getParentView() {
        return (View_Player) super.getParentView();
    }

    @Override
    public void setup() throws Model_Exception {
        SCORE_START_X = this.getController().getParentController().getGridController().getView().GRID_START_X
                + this.getController().getParentController().getGridController().getView().GRID_WIDTH
                + View_Player_Grid.GRID_PADDING_RIGHT
                + SCORE_PADDING_LEFT;
        SCORE_START_Y = this.getParentView().PLAYER_START_Y + SCORE_PADDING_TOP
                + this.getController().getParentController().getNextController().getView().NEXT_HEIGHT
                + View_Player_Next.NEXT_PADDING_TOP;
        SCORE_WIDTH = Math.max(this.getParentView().SIZE_MATRIX_ELEMENT_X * 4, View_Player_Grid.MIN_WIDTH_TO_LEAVE_BLANK);
        SCORE_HEIGHT = 50;

        FILL_SCORE_RED = this.getController().getParentController().getGridController().getView().FILL_GRID_RED;
        FILL_SCORE_GREEN = this.getController().getParentController().getGridController().getView().FILL_GRID_GREEN;
        FILL_SCORE_BLUE = this.getController().getParentController().getGridController().getView().FILL_GRID_BLUE;
    }

    @Override
    public void draw() throws Model_Exception {
        // Fill rectangle.
        this.getParentView().getParentView().useDefaultStroke();
        this.getParentView().getParentView().getApp().fill(FILL_SCORE_RED, FILL_SCORE_GREEN, FILL_SCORE_BLUE);
        this.getParentView().getParentView().getApp().rect(SCORE_START_X, SCORE_START_Y, SCORE_WIDTH, SCORE_HEIGHT);

        // Score label.
        this.getParentView().getParentView().getApp().textSize(SCORE_TEXT_SIZE);
        this.getParentView().getParentView().getApp().textAlign(PApplet.CENTER, PApplet.BOTTOM);
        this.getParentView().getParentView().getApp().fill(COLOR_SCORE_RED, COLOR_SCORE_GREEN, COLOR_SCORE_BLUE);
        this.getParentView().getParentView().getApp().text(
                this.getController().getParentController().getParentController().translate("Score"),
                SCORE_START_X + 0.5f * SCORE_WIDTH,
                SCORE_START_Y + 0.5f * SCORE_HEIGHT);
        // Score.
        this.getParentView().getParentView().getApp().text(
                this.getController().getScore(),
                SCORE_START_X + 0.5f * SCORE_WIDTH,
                SCORE_START_Y + 0.5f * SCORE_HEIGHT + SCORE_TEXT_SIZE + 2);
    }
}

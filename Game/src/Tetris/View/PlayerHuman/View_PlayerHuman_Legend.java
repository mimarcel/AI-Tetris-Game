package Tetris.View.PlayerHuman;

import Tetris.Controller.Controller_Tetris;
import Tetris.Controller.PlayerHuman.Controller_PlayerHuman_Legend;
import Tetris.Model.Model_Exception;
import Tetris.View.Player.View_Player_Grid;
import Tetris.View.View_Abstract;
import Tetris.View.View_Player;
import processing.core.PApplet;

public class View_PlayerHuman_Legend extends View_Abstract
{
    public float LEGEND_START_X = -1;
    public float LEGEND_START_Y = -1;
    public float LEGEND_WIDTH = -1;
    public float LEGEND_HEIGHT = -1;

    public static final float LEGEND_PADDING_LEFT = 0;
    public static final float LEGEND_PADDING_RIGHT = 0;
    public static final float LEGEND_PADDING_TOP = 10;

    public float FILL_LEGEND_RED = -1;
    public float FILL_LEGEND_GREEN = -1;
    public float FILL_LEGEND_BLUE = -1;
    public float COLOR_LEGEND_RED = 0;
    public float COLOR_LEGEND_GREEN = 0;
    public float COLOR_LEGEND_BLUE = 0;
    public float LEGEND_LABEL_TEXT_SIZE = 15;
    public float LEGEND_TEXT_SIZE = 10;

    /**
     * Create controller.
     */
    public View_PlayerHuman_Legend(Controller_PlayerHuman_Legend controller_player_Score, View_Player view) {
        super(controller_player_Score, view);
    }

    @Override
    public Controller_PlayerHuman_Legend getController() {
        return (Controller_PlayerHuman_Legend) super.getController();
    }

    @Override
    public View_Player getParentView() {
        return (View_Player) super.getParentView();
    }

    @Override
    public void setup() throws Model_Exception {
        LEGEND_START_X = this.getController().getParentController().getGridController().getView().GRID_START_X
                + this.getController().getParentController().getGridController().getView().GRID_WIDTH
                + View_Player_Grid.GRID_PADDING_RIGHT
                + LEGEND_PADDING_LEFT;
        LEGEND_START_Y = LEGEND_PADDING_TOP
                + this.getController().getParentController().getScoreController().getView().SCORE_START_Y
                + this.getController().getParentController().getScoreController().getView().SCORE_HEIGHT;
        LEGEND_WIDTH = Math.max(this.getParentView().SIZE_MATRIX_ELEMENT_X * 4, View_Player_Grid.MIN_WIDTH_TO_LEAVE_BLANK);
        LEGEND_HEIGHT = 119;

        FILL_LEGEND_RED = this.getController().getParentController().getGridController().getView().FILL_GRID_RED;
        FILL_LEGEND_GREEN = this.getController().getParentController().getGridController().getView().FILL_GRID_GREEN;
        FILL_LEGEND_BLUE = this.getController().getParentController().getGridController().getView().FILL_GRID_BLUE;
    }

    @Override
    public void draw() throws Model_Exception {
        // Fill rectangle.
        this.getParentView().getParentView().useDefaultStroke();
        this.getParentView().getParentView().getApp().fill(FILL_LEGEND_RED, FILL_LEGEND_GREEN, FILL_LEGEND_BLUE);
        this.getParentView().getParentView().getApp().rect(LEGEND_START_X, LEGEND_START_Y, LEGEND_WIDTH, LEGEND_HEIGHT);

        // Legend label.
        this.getParentView().getParentView().getApp().textSize(LEGEND_LABEL_TEXT_SIZE);
        this.getParentView().getParentView().getApp().textAlign(PApplet.CENTER, PApplet.TOP);
        this.getParentView().getParentView().getApp().fill(COLOR_LEGEND_RED, COLOR_LEGEND_GREEN, COLOR_LEGEND_BLUE);
        this.getController().getParentController().getParentController();
        this.getParentView().getParentView().getApp().text(
                Controller_Tetris.translate("Legend"),
                LEGEND_START_X + 0.5f * LEGEND_WIDTH,
                LEGEND_START_Y + 0.0f * LEGEND_HEIGHT);
        // Score.
        this.getParentView().getParentView().getApp().textSize(LEGEND_TEXT_SIZE);
        this.getParentView().getParentView().getApp().textAlign(PApplet.LEFT, PApplet.TOP);
        this.getParentView().getParentView().getApp().text(
                this.getController().getLegend(),
                LEGEND_START_X + 5,
                LEGEND_START_Y + 0.0f * LEGEND_HEIGHT + LEGEND_LABEL_TEXT_SIZE + 5,
                LEGEND_WIDTH - 5,
                LEGEND_HEIGHT - (LEGEND_LABEL_TEXT_SIZE + 5));
    }
}

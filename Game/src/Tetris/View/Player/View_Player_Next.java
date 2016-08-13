package Tetris.View.Player;

import Tetris.Controller.Controller_Tetris;
import Tetris.Controller.Player.Controller_Player_Next;
import Tetris.Model.Model_Exception;
import Tetris.View.View_Abstract;
import Tetris.View.View_Player;
import processing.core.PApplet;

public class View_Player_Next extends View_Abstract
{
    public float NEXT_START_X = -1;
    public float NEXT_START_Y = -1;
    public float NEXT_WIDTH = -1;
    public float NEXT_HEIGHT = -1;

    public static final float NEXT_PADDING_LEFT = 0;
    public static final float NEXT_PADDING_RIGHT = 0;
    public static final float NEXT_PADDING_TOP = 10;

    public float FILL_NEXT_RED = -1;
    public float FILL_NEXT_GREEN = -1;
    public float FILL_NEXT_BLUE = -1;

    public float COLOR_NEXT_RED = 0;
    public float COLOR_NEXT_GREEN = 0;
    public float COLOR_NEXT_BLUE = 0;

    public float NEXT_TEXT_SIZE = 15;

    /**
     * Create controller.
     */
    public View_Player_Next(Controller_Player_Next controller_player_Next, View_Player view) {
        super(controller_player_Next, view);
    }

    @Override
    public View_Player getParentView() {
        return (View_Player) super.getParentView();
    }

    @Override
    public Controller_Player_Next getController() {
        return (Controller_Player_Next) super.getController();
    }

    @Override
    public void setup() throws Model_Exception {
        NEXT_START_X = this.getController().getParentController().getGridController().getView().GRID_START_X
                + this.getController().getParentController().getGridController().getView().GRID_WIDTH
                + View_Player_Grid.GRID_PADDING_RIGHT
                + NEXT_PADDING_LEFT;
        NEXT_START_Y = this.getParentView().PLAYER_START_Y + NEXT_PADDING_TOP;
        NEXT_WIDTH = Math.max(this.getParentView().SIZE_MATRIX_ELEMENT_X * 4, View_Player_Grid.MIN_WIDTH_TO_LEAVE_BLANK);
        NEXT_HEIGHT = this.getParentView().SIZE_MATRIX_ELEMENT_Y * 4
                + NEXT_TEXT_SIZE + 10;

        FILL_NEXT_RED = this.getController().getParentController().getGridController().getView().FILL_GRID_RED;
        FILL_NEXT_GREEN = this.getController().getParentController().getGridController().getView().FILL_GRID_GREEN;
        FILL_NEXT_BLUE = this.getController().getParentController().getGridController().getView().FILL_GRID_BLUE;
    }

    @Override
    public void draw() throws Model_Exception {
        // Fill rectangle.
        this.getParentView().getParentView().useDefaultStroke();
        this.getParentView().getParentView().getApp().fill(FILL_NEXT_RED, FILL_NEXT_GREEN, FILL_NEXT_BLUE);
        this.getParentView().getParentView().getApp().rect(NEXT_START_X, NEXT_START_Y, NEXT_WIDTH, NEXT_HEIGHT);

        // Next tetromino label.
        this.getParentView().getParentView().getApp().textSize(NEXT_TEXT_SIZE);
        this.getParentView().getParentView().getApp().textAlign(PApplet.CENTER, PApplet.TOP);
        this.getParentView().getParentView().getApp().fill(COLOR_NEXT_RED, COLOR_NEXT_GREEN, COLOR_NEXT_BLUE);
        this.getParentView().getParentView().getApp().text(
                Controller_Tetris.translate("Next tetromino:"),
                NEXT_START_X + 0.5f * NEXT_WIDTH,
                NEXT_START_Y + 0.0f * NEXT_HEIGHT);

        // Next tetromino.
        if (this.getController().getNextTetromino() != null) {
            this.getParentView().tetrominoMode(View_Player.TETROMINO_MODE_CENTER, View_Player.TETROMINO_MODE_MIDDLE);
            this.getParentView().tetromino(this.getController().getNextTetromino(),
                    NEXT_START_X + 0.5f * NEXT_WIDTH,
                    NEXT_START_Y + 0.5f * (NEXT_HEIGHT - NEXT_TEXT_SIZE) + NEXT_TEXT_SIZE);
        }
    }
}

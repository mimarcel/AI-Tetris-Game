package Tetris.View.Player;

import Tetris.Controller.Controller_PlayerHuman;
import Tetris.Controller.Controller_Tetris;
import Tetris.Controller.Player.Controller_Player_Grid;
import Tetris.Model.Model_Color;
import Tetris.Model.Model_Exception;
import Tetris.View.View_Abstract;
import Tetris.View.View_Player;
import processing.core.PApplet;

public abstract class View_Player_Grid extends View_Abstract
{
    public float GRID_START_X = -1;
    public float GRID_START_Y = -1;
    public float GRID_WIDTH = -1;
    public static float MIN_WIDTH_TO_LEAVE_BLANK = 100;
    public float GRID_HEIGHT = -1;

    public static final float GRID_PADDING_LEFT = 10;
    public static final float GRID_PADDING_RIGHT = 10;
    public static final float GRID_PADDING_TOP = 10;
    public static final float GRID_PADDING_DOWN = 10;
    public float FILL_GRID_RED = 200;
    public float FILL_GRID_GREEN = 200;
    public float FILL_GRID_BLUE = 200;

    public float MESSAGE_TEXT_X = -1;
    public float MESSAGE_TEXT_Y = -1;
    public float MESSAGE_TEXT_SIZE = 20;
    public float MESSAGE_TEXT_RED = 0;
    public float MESSAGE_TEXT_GREEN = 0;
    public float MESSAGE_TEXT_BLUE = 0;
    public float GAME_OVER_FILL_ALPHA = 128;

    public int FILL_RED_PRESS_ANY_KEY_TEXT = 0;
    public int FILL_GREEN_PRESS_ANY_KEY_TEXT = 0;
    public int FILL_BLUE_PRESS_ANY_KEY_TEXT = 0;
    public int TEXT_SIZE_PRESS_ANY_KEY_TEXT = 20;

    public static boolean DRAW_YOUR_OWN_BLOCKS = false;

    /**
     * Create controller.
     */
    public View_Player_Grid(Controller_Player_Grid controller_player_grid, View_Player view) {
        super(controller_player_grid, view);
    }

    @Override
    public Controller_Player_Grid getController() {
        return (Controller_Player_Grid) super.getController();
    }

    @Override
    public View_Player getParentView() {
        return (View_Player) super.getParentView();
    }

    @Override
    public void setup() throws Model_Exception {
        GRID_START_X = this.getParentView().PLAYER_START_X + GRID_PADDING_LEFT;
        GRID_START_Y = this.getParentView().PLAYER_START_Y + GRID_PADDING_TOP;
        GRID_WIDTH = this.getParentView().SIZE_MATRIX_ELEMENT_X * Controller_Tetris.NUMBER_OF_COLUMNS_TETRIS_GRID;
        GRID_HEIGHT = this.getParentView().SIZE_MATRIX_ELEMENT_Y * Controller_Tetris.NUMBER_OF_LINES_TETRIS_GRID;

        MESSAGE_TEXT_X = GRID_START_X + GRID_WIDTH / 2;
        MESSAGE_TEXT_Y = GRID_START_Y + GRID_HEIGHT / 2;

        /*@removeLineFromPjs
        int dyob = Controller_Tetris.getParameterByName("dyob");
        if (dyob) {
            DRAW_YOUR_OWN_BLOCKS = true;//Controller_PlayerHuman.counter == 0;
        }
        @removeLineFromPjs*/
    }

    @Override
    public void draw() throws Model_Exception {
        // Rectangle.
        this.getParentView().getParentView().useDefaultStroke();
        this.getParentView().getParentView().getApp().fill(FILL_GRID_RED, FILL_GRID_GREEN, FILL_GRID_BLUE);
        this.getParentView().getParentView().getApp().rect(
                GRID_START_X - this.getParentView().TETROMINO_STROKE_WEIGHT,
                GRID_START_Y - this.getParentView().TETROMINO_STROKE_WEIGHT,
                GRID_WIDTH + this.getParentView().TETROMINO_STROKE_WEIGHT,
                GRID_HEIGHT + this.getParentView().TETROMINO_STROKE_WEIGHT);

        if (this.getController().getParentController().getParentController().isGameStarted()) {
            // Check what happens.
            if (!this.getController().getParentController().isGameOver()) {
                this.getController().checkCurrentTetromino();
            }

            if (!this.getController().getParentController().isGameOver()) {
                // Update grid.
                int clearedLines = this.getController().updateCurrentGrid();

                // Update score.
                this.getController().getParentController().updateScore(clearedLines);
                this.getController().getParentController().playerUpdatedGrid(clearedLines);
            }
        }

        // Grid.
        this.getParentView().tetrominoMode(View_Player.TETROMINO_MODE_RIGHT, View_Player.TETROMINO_MODE_TOP);
        this.getController().addShadowInTheGridForCurrentTetromino();
        this.getController().addCurrentTetrominoToCurentGrid();
        this.getParentView().grid(this.getController().getModel().getGrid(), GRID_START_X, GRID_START_Y, null);
        this.getController().removeCurrentTetrominoFromGrid();
        this.getController().removeShadowFromTheGrid();

        if (!this.getController().getParentController().getParentController().isGameStarted()) {
            // Wait to start message.
            this.getParentView().getParentView().getApp().textAlign(PApplet.CENTER, PApplet.CENTER);
            this.getParentView().getParentView().getApp().fill(FILL_RED_PRESS_ANY_KEY_TEXT, FILL_GREEN_PRESS_ANY_KEY_TEXT, FILL_BLUE_PRESS_ANY_KEY_TEXT);
            this.getParentView().getParentView().getApp().textSize(TEXT_SIZE_PRESS_ANY_KEY_TEXT);
            this.getParentView().getParentView().getApp().text(
                    Controller_Tetris.translate("Press any key to start"),
                    GRID_START_X + GRID_WIDTH / 2,
                    GRID_START_Y + GRID_HEIGHT / 2);
        }

        if (this.getController().getParentController().isGameOver()
                && this.getController().getParentController().getParentController().isGameStarted()) {
            // Prepare game over message.
            this.getParentView().getParentView().getApp().textSize(MESSAGE_TEXT_SIZE);
            String message = this.getController().getParentController().getFinalPlace() == 1
                    ? "You won"
                    : "You lost";
            message = Controller_Tetris.translate("Game over")
                    + (this.getController().getParentController().getParentController().getNumberOfPlayers() > 1
                    ? "\n" + Controller_Tetris.translate(message)
                    : "");
            this.messageOnGrid(message);
        }
    }

    @Override
    public Object doAction(int action) throws Model_Exception {
        if (!this.getController().getParentController().isGameOver()) {
            return super.doAction(action);
        } else {
            // Only allow drawing.
            return action == ACTION_DRAW ? super.doAction(action) : null;
        }
    }

    public void messageOnGrid(String message) {
        // Message rectangle.
        this.getParentView().getParentView().getApp().noStroke();
        this.getParentView().getParentView().getApp().fill(FILL_GRID_RED, FILL_GRID_GREEN, FILL_GRID_BLUE, GAME_OVER_FILL_ALPHA);
        this.getParentView().getParentView().getApp().rect(
                MESSAGE_TEXT_X - this.getParentView().getParentView().getApp().textWidth(message) / 2,
                MESSAGE_TEXT_Y - MESSAGE_TEXT_SIZE,
                this.getParentView().getParentView().getApp().textWidth(message),
                MESSAGE_TEXT_SIZE * 3);

        // Message.
        this.getParentView().getParentView().getApp().textAlign(PApplet.CENTER, PApplet.CENTER);
        this.getParentView().getParentView().getApp().fill(MESSAGE_TEXT_RED, MESSAGE_TEXT_GREEN, MESSAGE_TEXT_BLUE);
        this.getParentView().getParentView().getApp().text(
                message,
                MESSAGE_TEXT_X,
                MESSAGE_TEXT_Y
        );
    }

    @Override
    public void mouseDragged() throws Model_Exception {
        this.fillCells();
        super.mouseDragged();
    }

    @Override
    public void mousePressed() throws Model_Exception {
        this.fillCells();
        super.mousePressed();
    }

    protected void fillCells() {
        if (DRAW_YOUR_OWN_BLOCKS) {
            int mX = this.getParentView().getParentView().getApp().mouseX;
            int mY = this.getParentView().getParentView().getApp().mouseY;
            if (mX >= GRID_START_X
                    && mX <= GRID_START_X + GRID_WIDTH
                    && mY >= GRID_START_Y
                    && mY <= GRID_START_Y + GRID_HEIGHT
                    ) {
                int c = (int) Math.floor((mX - GRID_START_X) / this.getParentView().SIZE_MATRIX_ELEMENT_X);
                int l = (int) Math.floor((mY - GRID_START_Y) / this.getParentView().SIZE_MATRIX_ELEMENT_Y);
                short[][] grid = this.getController().getModel().getGrid();
                if (l >= 0 && l < grid.length && c >= 0 && c < grid[l].length) {
                    this.getController().getModel().getGrid()[l][c] = Model_Color.PURPLE;
                }
            }
        }
    }
}

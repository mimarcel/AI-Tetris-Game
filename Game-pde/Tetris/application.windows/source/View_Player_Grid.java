
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

    /**
     * Create controller.
     */
    public View_Player_Grid(Controller_Player_Grid controller_player_grid, View_Player view) {
        super(controller_player_grid, view);
    }

    public Controller_Player_Grid getController() {
        return (Controller_Player_Grid) super.getController();
    }

    public View_Player getParentView() {
        return (View_Player) super.getParentView();
    }

    public void setup() throws Model_Exception {
        GRID_START_X = this.getParentView().PLAYER_START_X + GRID_PADDING_LEFT;
        GRID_START_Y = this.getParentView().PLAYER_START_Y + GRID_PADDING_TOP;
        GRID_WIDTH = this.getParentView().SIZE_MATRIX_ELEMENT_X * Controller_Tetris.NUMBER_OF_COLUMNS_TETRIS_GRID;
        GRID_HEIGHT = this.getParentView().SIZE_MATRIX_ELEMENT_Y * Controller_Tetris.NUMBER_OF_LINES_TETRIS_GRID;

        MESSAGE_TEXT_X = GRID_START_X + GRID_WIDTH / 2;
        MESSAGE_TEXT_Y = GRID_START_Y + GRID_HEIGHT / 2;
    }

    public void draw() throws Model_Exception {
        // Rectangle.
        this.getParentView().getParentView().useDefaultStroke();
        this.getParentView().getParentView().getApp().fill(FILL_GRID_RED, FILL_GRID_GREEN, FILL_GRID_BLUE);
        this.getParentView().getParentView().getApp().rect(
                GRID_START_X - this.getParentView().TETROMINO_STROKE_WEIGHT,
                GRID_START_Y - this.getParentView().TETROMINO_STROKE_WEIGHT,
                GRID_WIDTH + this.getParentView().TETROMINO_STROKE_WEIGHT,
                GRID_HEIGHT + this.getParentView().TETROMINO_STROKE_WEIGHT);

        if (!this.getController().getParentController().getParentController().isGameStarted()) {
            // Wait to start message.
            this.getParentView().getParentView().getApp().textAlign(PApplet.CENTER, PApplet.CENTER);
            this.getParentView().getParentView().getApp().fill(FILL_RED_PRESS_ANY_KEY_TEXT, FILL_GREEN_PRESS_ANY_KEY_TEXT, FILL_BLUE_PRESS_ANY_KEY_TEXT);
            this.getParentView().getParentView().getApp().textSize(TEXT_SIZE_PRESS_ANY_KEY_TEXT);
            this.getParentView().getParentView().getApp().text(
                    Controller_Tetris.translate("Press any key to start"),
                    GRID_START_X + GRID_WIDTH / 2,
                    GRID_START_Y + GRID_HEIGHT / 2);
        } else {
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
}

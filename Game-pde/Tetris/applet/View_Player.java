

public abstract class View_Player extends View_AbstractWithChildren
{
    public static int COUNTER_PLAYER = 0;
    protected static float COUNTER_PLAYER_START_X = -1;

    public float PLAYER_START_X = -1;
    public float PLAYER_START_Y = -1;
    public float PLAYER_WIDTH = -1;
    public float PLAYER_HEIGHT = -1;
    public float PLAYER_PADDING_DOWN = 10;
    public float PLAYER_PADDING_UP = 10;
    public float PLAYER_PADDING_LEFT = 10;
    public float PLAYER_PADDING_RIGHT = 10;

    public float PLAYER_RECTANGLE_FILL_RED = 250;
    public float PLAYER_RECTANGLE_FILL_GREEN = 250;
    public float PLAYER_RECTANGLE_FILL_BLUE = 250;

    public float SIZE_MATRIX_ELEMENT_X = -1;
    public float SIZE_MATRIX_ELEMENT_Y = -1;

    public float TETROMINO_STROKE_WEIGHT = -1;
    public float TETROMINO_STROKE_RED = 133;
    public float TETROMINO_STROKE_GREEN = 133;
    public float TETROMINO_STROKE_BLUE = 133;

    public static final int TETROMINO_MODE_TOP = 0;
    public static final int TETROMINO_MODE_CENTER = 1;
    public static final int TETROMINO_MODE_BOTTOM = 2;
    public static final int TETROMINO_MODE_LEFT = 3;
    public static final int TETROMINO_MODE_MIDDLE = 4;
    public static final int TETROMINO_MODE_RIGHT = 5;
    protected int tetrominoModeH = TETROMINO_MODE_TOP;
    protected int tetrominoModeV = TETROMINO_MODE_RIGHT;

    /**
     * Create view.
     */
    public View_Player(Controller_PlayerAbstract controller_player, View_Tetris view) {
        super(controller_player, view, 10);
    }

    public Controller_PlayerAbstract getController() {
        return (Controller_PlayerAbstract) super.getController();
    }

    public View_Tetris getParentView() {
        return (View_Tetris) super.getParentView();
    }

    public void setup() throws Model_Exception {
        // Set params.
        int n = this.getController().getParentController().getNumberOfPlayers();
        COUNTER_PLAYER++;
        switch (n) {
            case 1:
                PLAYER_PADDING_LEFT = 200;
                PLAYER_PADDING_RIGHT = 200;
            default:
                switch (COUNTER_PLAYER) {
                    case 1:
                        COUNTER_PLAYER_START_X = this.getParentView().getBackgroundPadding() + PLAYER_PADDING_LEFT;
                        break;
                    default:
//                        COUNTER_PLAYER_START_X += PLAYER_PADDING_LEFT;
                        break;
                }
                PLAYER_START_X = COUNTER_PLAYER_START_X;
                PLAYER_START_Y = this.getParentView().getController().getTitleController().getView().TITLE_Y
                        + this.getParentView().getController().getTitleController().getView().TITLE_HEIGHT / 2
                        + PLAYER_PADDING_UP;
                PLAYER_WIDTH = (this.getParentView().WIDTH - this.getParentView().getBackgroundPadding() * 2
                        - PLAYER_PADDING_LEFT - n * PLAYER_PADDING_RIGHT
                ) / n;
                PLAYER_HEIGHT = this.getParentView().HEIGHT
                        - PLAYER_START_Y
                        - PLAYER_PADDING_DOWN
                        - this.getController().getParentController().getBackgroundController().getView().BACKGROUND_PADDING;
                COUNTER_PLAYER_START_X += PLAYER_WIDTH + PLAYER_PADDING_RIGHT;
                break;
        }

        // Tetromino = 4x4 matrix of squares. (each element is a square)
        // The grid view should have NUMBER_OF_COLUMNS_TETRIS_GRID squares on one line => NUMBER_OF_COLUMNS_TETRIS_GRID squares width.
        // The grid view should have NUMBER_OF_LINES_TETRIS_GRID squares on one column => NUMBER_OF_LINES_TETRIS_GRID squares height.
        // The next-tetromino view should be big enough for a 4x4-matrix tetromino => 4 squares width and 4 squares height.

        // player_width = grid_width + next_width + padding stuff = (NUMBER_OF_COLUMNS_TETRIS_GRID + 4) squares + padding stuff
        // player_height = grid_height + padding stuff = NUMBER_OF_LINES_TETRIS_GRID squares + padding stuff

        float width = (PLAYER_WIDTH
                - View_Player_Grid.GRID_PADDING_LEFT - View_Player_Grid.GRID_PADDING_RIGHT
                - Math.max(View_Player_Next.NEXT_PADDING_LEFT, View_Player_Score.SCORE_PADDING_LEFT)
                - Math.max(View_Player_Next.NEXT_PADDING_RIGHT, View_Player_Score.SCORE_PADDING_RIGHT)
        );
        SIZE_MATRIX_ELEMENT_X = width / (Controller_Tetris.NUMBER_OF_COLUMNS_TETRIS_GRID + 4);
        if (SIZE_MATRIX_ELEMENT_X * 4 < View_Player_Grid.MIN_WIDTH_TO_LEAVE_BLANK) {
            SIZE_MATRIX_ELEMENT_X = (width - View_Player_Grid.MIN_WIDTH_TO_LEAVE_BLANK) / Controller_Tetris.NUMBER_OF_COLUMNS_TETRIS_GRID;
        }
        SIZE_MATRIX_ELEMENT_Y = (PLAYER_HEIGHT - PLAYER_PADDING_DOWN)
                / Controller_Tetris.NUMBER_OF_LINES_TETRIS_GRID;
        TETROMINO_STROKE_WEIGHT = 0.07f * Math.min(SIZE_MATRIX_ELEMENT_X, SIZE_MATRIX_ELEMENT_Y);

        // Add view children.
        this.addViewChildrenFromControllers();

        super.setup();
    }

    /**
     * Add views created by controllers to current view.
     */
    protected void addViewChildrenFromControllers() {
        this.addViewChild(this.getController().getGridController().getView());
        this.addViewChild(this.getController().getNextController().getView());
        this.addViewChild(this.getController().getScoreController().getView());
        this.addViewChild(this.getController().getInfoController().getView());
    }

    public void draw() throws Model_Exception {
        // Rectangle.
        this.getParentView().getApp().noStroke();
        this.getParentView().getApp().fill(PLAYER_RECTANGLE_FILL_RED, PLAYER_RECTANGLE_FILL_GREEN, PLAYER_RECTANGLE_FILL_BLUE);
        this.getParentView().getApp().rect(PLAYER_START_X, PLAYER_START_Y, PLAYER_WIDTH, PLAYER_HEIGHT);

        super.draw();
    }

    /**
     * Set tetromino mode for drawing.
     */
    public void tetrominoMode(int tetrominoModeH, int tetrominoModeV) {
        this.tetrominoModeH = tetrominoModeH;
        this.tetrominoModeV = tetrominoModeV;
    }

    /**
     * Draw grid from matrix.
     * The color of each grid element is oneColor.
     * If oneColor is null then the color of each grid element is given by the value of the element.
     */
    public void grid(short[][] matrix, float x, float y, Short oneColor) {
        float startX = 0;
        float startY = 0;
        switch (this.tetrominoModeH) {
            case TETROMINO_MODE_LEFT:
                startX = x - (float) matrix[0].length * SIZE_MATRIX_ELEMENT_X;
                break;
            case TETROMINO_MODE_CENTER:
                startX = x - (float) matrix[0].length / 2 * SIZE_MATRIX_ELEMENT_X;
                break;
            case TETROMINO_MODE_RIGHT:
                startX = x;
                break;
        }
        switch (this.tetrominoModeV) {
            case TETROMINO_MODE_TOP:
                startY = y;
                break;
            case TETROMINO_MODE_MIDDLE:
                startY = y - (float) matrix.length / 2 * SIZE_MATRIX_ELEMENT_Y;
                break;
            case TETROMINO_MODE_BOTTOM:
                startY = y - (float) matrix.length * SIZE_MATRIX_ELEMENT_Y;
                break;
        }

        // Prepare drawing.
        this.getParentView().getApp().stroke(TETROMINO_STROKE_RED, TETROMINO_STROKE_GREEN, TETROMINO_STROKE_BLUE);
        this.getParentView().getApp().strokeWeight(TETROMINO_STROKE_WEIGHT);
        if (oneColor != null) {
            this.prepareFillForTetromino(oneColor);
        }

        // Draw it.
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] != 0) {
                    if (oneColor == null) {
                        this.prepareFillForTetromino(matrix[i][j]);
                    }
                    this.getParentView().getApp().rect(
                            startX + j * SIZE_MATRIX_ELEMENT_X,
                            startY + i * SIZE_MATRIX_ELEMENT_Y,
                            SIZE_MATRIX_ELEMENT_X,
                            SIZE_MATRIX_ELEMENT_Y);
                }
            }
        }
    }

    /**
     * Draw tetromino on position x , y.
     */
    public void tetromino(Model_Tetromino t, float x, float y) {
        grid(t.getCurrentRotation(), x, y, t.getColor());
    }

    /**
     * Prepare fill color depending on constant color.
     */
    protected void prepareFillForTetromino(int color) {
        float red = 0, green = 0, blue = 0, transparency = 255;
        switch (color) {
            case Model_Color.BLUE:
                red = 0;
                green = 0;
                blue = 255;
                break;
            case Model_Color.GREEN:
                red = 0;
                green = 255;
                blue = 0;
                break;
            case Model_Color.LIGHT_BLUE:
                red = 0;
                green = 255;
                blue = 255;
                break;
            case Model_Color.ORANGE:
                red = 255;
                green = 128;
                blue = 0;
                break;
            case Model_Color.PINK:
                red = 200;
                green = 0;
                blue = 200;
                break;
            case Model_Color.RED:
                red = 200;
                green = 33;
                blue = 33;
                break;
            case Model_Color.YELLOW:
                red = 255;
                green = 255;
                blue = 0;
                break;
            case Model_Color.WHITE:
                red = 255;
                green = 255;
                blue = 255;
                transparency = 128;
                break;
            case Model_Color.BLACK:
                red = 0;
                green = 0;
                blue = 0;
                break;
        }
        this.getParentView().getApp().fill(red, green, blue, transparency);
    }

    public void pleaseWaitMessage() {

    }
}

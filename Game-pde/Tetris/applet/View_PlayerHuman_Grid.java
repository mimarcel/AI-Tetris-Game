
import processing.core.PApplet;

public class View_PlayerHuman_Grid extends View_Player_Grid
{
    public static int COUNTER_HUMAN_PLAYERS = 0;

    public int MOVE_TETROMINO_KEY_LEFT = -1;
    public int MOVE_TETROMINO_KEY_RIGHT = -1;
    public int MOVE_TETROMINO_KEY_DOWN = -1;
    public int MOVE_TETROMINO_KEY_FALL = -1;
    public int MOVE_TETROMINO_KEY_ROTATE_LEFT = -1;
    public int MOVE_TETROMINO_KEY_ROTATE_RIGHT = -1;

    /**
     * Create controller.
     */
    public View_PlayerHuman_Grid(Controller_PlayerHuman_Grid controller_player_grid, View_PlayerHuman view) {
        super(controller_player_grid, view);
    }

    public View_PlayerHuman getParentView() {
        return (View_PlayerHuman) super.getParentView();
    }

    public void setup() throws Model_Exception {
        COUNTER_HUMAN_PLAYERS++;

        super.setup();

        int n = this.getController().getParentController().getParentController().getNumberOfPlayers(Model_Player.PLAYER_TYPE_HUMAN);
        switch (n) {
            case 1:
                MOVE_TETROMINO_KEY_LEFT = PApplet.LEFT;
                MOVE_TETROMINO_KEY_RIGHT = PApplet.RIGHT;
                MOVE_TETROMINO_KEY_DOWN = PApplet.DOWN;
                MOVE_TETROMINO_KEY_FALL = 32;
                MOVE_TETROMINO_KEY_ROTATE_LEFT = PApplet.UP;
                MOVE_TETROMINO_KEY_ROTATE_RIGHT = -1;
                break;
            case 2:
                switch (COUNTER_HUMAN_PLAYERS) {
                    case 2:
                        MOVE_TETROMINO_KEY_LEFT = PApplet.LEFT;
                        MOVE_TETROMINO_KEY_RIGHT = PApplet.RIGHT;
                        MOVE_TETROMINO_KEY_DOWN = PApplet.DOWN;
                        MOVE_TETROMINO_KEY_FALL = (int) 'L';
                        MOVE_TETROMINO_KEY_ROTATE_LEFT = PApplet.UP;
                        MOVE_TETROMINO_KEY_ROTATE_RIGHT = -1;
                        break;
                    case 1:
                        MOVE_TETROMINO_KEY_LEFT = (int) 'A';
                        MOVE_TETROMINO_KEY_RIGHT = (int) 'D';
                        MOVE_TETROMINO_KEY_DOWN = (int) 'S';
                        MOVE_TETROMINO_KEY_FALL = (int) '1';
                        MOVE_TETROMINO_KEY_ROTATE_LEFT = (int) 'W';
                        MOVE_TETROMINO_KEY_ROTATE_RIGHT = -1;
                        break;
                }
                break;
        }
    }

    public void keyPressed() throws Model_Exception {
        int key = this.getParentView().getParentView().getApp().keyCode;
        if (key == MOVE_TETROMINO_KEY_RIGHT) {
            this.getController().moveCurrentTetrominoToRight();
        }
        if (key == MOVE_TETROMINO_KEY_LEFT) {
            this.getController().moveCurrentTetrominoToLeft();
        }
        if (key == MOVE_TETROMINO_KEY_DOWN) {
            this.getController().moveCurrentTetrominoDownOneLevel();
        }
        if (key == MOVE_TETROMINO_KEY_FALL) {
            this.getController().fallCurrentTetromino();
        }
        if (key == MOVE_TETROMINO_KEY_ROTATE_RIGHT) {
            this.getController().rotateTetrominoToRight();
        }
        if (key == MOVE_TETROMINO_KEY_ROTATE_LEFT) {
            this.getController().rotateTetrominoToLeft();
        }
    }
}

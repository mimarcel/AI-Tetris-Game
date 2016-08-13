package Tetris.Model.Tetromino;

import Tetris.Model.Model_Abstract;
import Tetris.Model.Model_Color;
import Tetris.Model.Model_Exception;
import Tetris.Model.Model_Tetromino;

public class Model_Tetromino_Factory extends Model_Abstract
{
    public static final int TETROMINO_TYPE_I = 0;
    public static final int TETROMINO_TYPE_O = 1;
    public static final int TETROMINO_TYPE_T = 2;
    public static final int TETROMINO_TYPE_J = 3;
    public static final int TETROMINO_TYPE_L = 4;
    public static final int TETROMINO_TYPE_S = 5;
    public static final int TETROMINO_TYPE_Z = 6;

    protected static Model_Tetromino_Factory instance;

    /**
     * Constructor is restricted.
     */
    protected Model_Tetromino_Factory() {

    }

    /**
     * Get singleton player factory object.
     * @return Model_Player_Factory
     */
    public static Model_Tetromino_Factory getInstance() {
        if (Model_Tetromino_Factory.instance == null) {
            Model_Tetromino_Factory.instance = new Model_Tetromino_Factory();
        }
        return Model_Tetromino_Factory.instance;
    }

    /**
     * Create new tetromino of type given.
     */
    public Model_Tetromino createTetromino(int type) throws Model_Exception {
        Model_Tetromino t = null;
        switch (type) {
            case TETROMINO_TYPE_I:
                t = new Model_Tetromino(
                        "I",
                        Model_Color.LIGHT_BLUE,
                        new short[][]
                                {
                                        {0, 0, 1, 0},
                                        {0, 0, 1, 0},
                                        {0, 0, 1, 0},
                                        {0, 0, 1, 0},
                                }
                );
                break;
            case TETROMINO_TYPE_O:
                t = new Model_Tetromino(
                        "O",
                        Model_Color.YELLOW,
                        new short[][]
                                {
                                        {1, 1},
                                        {1, 1},
                                }
                );
                break;
            case TETROMINO_TYPE_T:
                t = new Model_Tetromino(
                        "T",
                        Model_Color.PINK,
                        new short[][]
                                {
                                        {0, 0, 0},
                                        {0, 1, 0},
                                        {1, 1, 1},
                                }
                );
                break;
            case TETROMINO_TYPE_J:
                t = new Model_Tetromino(
                        "J",
                        Model_Color.BLUE,
                        new short[][]
                                {
                                        {0, 1, 0},
                                        {0, 1, 0},
                                        {1, 1, 0},
                                }
                );
                break;
            case TETROMINO_TYPE_L:
                t = new Model_Tetromino(
                        "L",
                        Model_Color.ORANGE,
                        new short[][]
                                {
                                        {0, 1, 0},
                                        {0, 1, 0},
                                        {0, 1, 1},
                                }
                );
                break;
            case TETROMINO_TYPE_S:
                t = new Model_Tetromino(
                        "S",
                        Model_Color.GREEN,
                        new short[][]
                                {
                                        {1, 0, 0},
                                        {1, 1, 0},
                                        {0, 1, 0},
                                }
                );
                break;
            case TETROMINO_TYPE_Z:
                t = new Model_Tetromino(
                        "Z",
                        Model_Color.RED,
                        new short[][]
                                {
                                        {0, 0, 1},
                                        {0, 1, 1},
                                        {0, 1, 0},
                                }
                );
                break;
        }
        return t;
    }

    /**
     * Create all types of tetrominoes and return them into an array.
     */
    public Model_Tetromino[] getAllTetrominoes() throws Model_Exception {
        Model_Tetromino[] tetrominoes = new Model_Tetromino[7];
        int nrt = -1;
        for (int type : new int[]{TETROMINO_TYPE_I, TETROMINO_TYPE_O, TETROMINO_TYPE_T,
                TETROMINO_TYPE_J, TETROMINO_TYPE_L, TETROMINO_TYPE_S, TETROMINO_TYPE_Z}) {
            tetrominoes[++nrt] = Model_Tetromino_Factory.getInstance().createTetromino(type);
        }
        return tetrominoes;
    }

    /**
     * Get number of tetrominoes types.
     */
    public int getNumberOfTetrominoes() {
        return 7;
    }
}

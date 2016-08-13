package Tetris.Controller.PlayerHuman;

import Tetris.Controller.Controller_Abstract;
import Tetris.Controller.Controller_PlayerHuman;
import Tetris.Model.Model_Exception;
import Tetris.View.PlayerHuman.View_PlayerHuman_Legend;
import processing.core.PApplet;

public class Controller_PlayerHuman_Legend extends Controller_Abstract
{
    /**
     * Create controller.
     */
    public Controller_PlayerHuman_Legend(Controller_PlayerHuman controller_player) {
        super(controller_player, null, null);
        this.view = new View_PlayerHuman_Legend(this, this.getParentController().getView());
    }

    @Override
    public View_PlayerHuman_Legend getView() {
        return (View_PlayerHuman_Legend) super.getView();
    }

    @Override
    public Controller_PlayerHuman getParentController() {
        return (Controller_PlayerHuman) super.getParentController();
    }

    /**
     * Get legend.
     */
    public String getLegend() throws Model_Exception {
        return this.keyCodeToString(this.getParentController().getGridController().getView().MOVE_TETROMINO_KEY_RIGHT) + ":"
                + " " + this.getParentController().getParentController().translate("move to right") + "\n"

                + this.keyCodeToString(this.getParentController().getGridController().getView().MOVE_TETROMINO_KEY_LEFT) + ":"
                + " " + this.getParentController().getParentController().translate("move to left") + "\n"

                + this.keyCodeToString(this.getParentController().getGridController().getView().MOVE_TETROMINO_KEY_ROTATE_LEFT) + ":"
                + " " + this.getParentController().getParentController().translate("rotate to left") + "\n"

                + this.keyCodeToString(this.getParentController().getGridController().getView().MOVE_TETROMINO_KEY_DOWN) + ":"
                + " " + this.getParentController().getParentController().translate("fall down one level") + "\n"

                + this.keyCodeToString(this.getParentController().getGridController().getView().MOVE_TETROMINO_KEY_FALL) + ":"
                + " " + this.getParentController().getParentController().translate("fall") + "\n";
    }

    /**
     * Return key code string equivalent.
     */
    protected String keyCodeToString(int keyCode) throws Model_Exception {
        switch (keyCode) {
            case PApplet.UP:
                return this.getParentController().getParentController().translate("UP-arrow");
            case PApplet.DOWN:
                return this.getParentController().getParentController().translate("DOWN-arrow");
            case PApplet.LEFT:
                return this.getParentController().getParentController().translate("LEFT-arrow");
            case PApplet.RIGHT:
                return this.getParentController().getParentController().translate("RIGHT-arrow");
            case 32:
                return this.getParentController().getParentController().translate("SPACE");
            default:
                return "" + (char) keyCode;//@removeLineFromPjs
            /*@removeLineFromPjs
            return String.fromCharCode(keyCode)
            @removeLineFromPjs*/
        }
    }
}

package Tetris.View;

import Tetris.Controller.Controller_PlayerAbstract;
import Tetris.Controller.Controller_PlayerHuman;
import Tetris.Controller.Controller_Restart;
import Tetris.Model.Model_Exception;
import processing.core.PApplet;

public class View_Restart extends View_Abstract
{
    public float RESTART_X = -1;
    public float RESTART_Y = -1;
    public float RESTART_WIDTH = -1;
    public float RESTART_HEIGHT = -1;
    public float RESTART_TEXT_SIZE = 20;

    public float RESTART_COLOR_RED = 255;
    public float RESTART_COLOR_GREEN = 255;
    public float RESTART_COLOR_BLUE = 255;

    /**
     * Create view.
     */
    public View_Restart(Controller_Restart controller, View_Tetris view_tetris) {
        super(controller, view_tetris);
    }

    @Override
    public Controller_Restart getController() {
        return (Controller_Restart) super.getController();
    }

    @Override
    public View_Tetris getParentView() {
        return (View_Tetris) super.getParentView();
    }

    @Override
    public void setup() throws Model_Exception {
        RESTART_X = 10;
        RESTART_Y = this.getController().getParentController().getTitleController().getView().TITLE_HEIGHT / 2 + this.getParentView().getBackgroundPadding() + 2;
        this.getParentView().getApp().textSize(RESTART_TEXT_SIZE);
        RESTART_WIDTH = this.getParentView().getApp().textWidth(this.getController().getRestartMessage());
        RESTART_HEIGHT = RESTART_TEXT_SIZE;

        if (!this.getController().getParentController().getTitleController().getView().IS_ACTIVE) {
            RESTART_COLOR_RED = 0;
            RESTART_COLOR_GREEN = 0;
            RESTART_COLOR_BLUE = 0;
        }
    }

    @Override
    public void draw() throws Model_Exception {
        if (!this.isActive()) {
            return;
        }
        // Image.
        this.getParentView().getApp().textAlign(PApplet.LEFT, PApplet.CENTER);
        this.getParentView().getApp().fill(RESTART_COLOR_RED, RESTART_COLOR_GREEN, RESTART_COLOR_BLUE);
        this.getParentView().getApp().textSize(RESTART_TEXT_SIZE);
        this.getParentView().getApp().text(this.getController().getRestartMessage(), RESTART_X, RESTART_Y);

        /*@removeLineFromPjs
        if (this.getController().getParentController().isGameOver() == true && this.getParentView().resultsPopup == 1){
            var players = [];for (Controller_PlayerAbstract cp : this.getController().getParentController().getPlayerControllers()) {players.push({n: cp.getPlayerName(), s: cp.getScore(), m : cp.getNumberOfMoves(), ti: new Date().getTime(), ty: cp.getModel().getType(), l: Controller_Tetris.NUMBER_OF_LINES_TETRIS_GRID, c: Controller_Tetris.NUMBER_OF_COLUMNS_TETRIS_GRID, d: View_Player_Grid.DRAW_YOUR_OWN_BLOCKS ? 1 :0});}this.getParentView().resultsPopup++;players.sort(function(a,b){if (a.score < b.score) { return -1; } return a.score == b.score ? 0 : 1;});
            gameOver(players);
        }
        @removeLineFromPjs*/
    }

    /**
     * Should display Restart?
     */
    protected boolean isActive() {
        return this.getController().getParentController().isGameStarted() == true
                && (this.getController().getParentController().isGameOver() == true || Controller_PlayerHuman.counter == 0);
    }

    @Override
    public void mousePressed() {
        if (!this.isActive()) {
            return;
        }

        int mx = this.getParentView().getApp().mouseX;
        int my = this.getParentView().getApp().mouseY;

        // Restart game.
        if (mx >= RESTART_X
                && mx <= RESTART_X + RESTART_WIDTH
                && my >= RESTART_Y - RESTART_HEIGHT / 2
                && my <= RESTART_Y + RESTART_HEIGHT / 2
                ) {
            this.getParentView().setRestart(true);
        }
    }

    @Override
    public void mouseMoved() throws Model_Exception {
        if (!this.isActive()) {
            return;
        }

        int mx = this.getParentView().getApp().mouseX;
        int my = this.getParentView().getApp().mouseY;

        if (mx >= RESTART_X
                && mx <= RESTART_X + RESTART_WIDTH
                && my >= RESTART_Y - RESTART_HEIGHT / 2
                && my <= RESTART_Y + RESTART_HEIGHT / 2
                ) {
            this.getParentView().askCursorToBe(PApplet.HAND);
        }
    }
}

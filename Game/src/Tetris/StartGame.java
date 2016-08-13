package Tetris;

import Tetris.Controller.*;
import Tetris.Model.Model_Exception;
import Tetris.View.PlayerHuman.View_PlayerHuman_Grid;
import Tetris.View.View_Abstract;
import Tetris.View.View_Player;
import Tetris.View.View_Tetris;
import processing.core.PApplet;

import javax.swing.*;

/*
@removeLineFromAll*/
public class StartGame extends PApplet
{
    /*@removeLineFromAll
    */
    protected View_Tetris view;
    protected int unexpectedErrors;
    public final int MAX_UNEXPECTED_ERRORS_ALLOWED = 2;

    /**
     * Do action.
     */
    public Object doAction(int action) {
        try {
            switch (action) {
                case View_Abstract.ACTION_SETUP:
                    view = new View_Tetris(this);
                    view.setRestart(false);
                    view.GAME_STATE = View_Tetris.GAME_STATE_IN_GAME;
                    view.doAction(action);
                    break;
                case View_Abstract.ACTION_DRAW:
                case View_Abstract.ACTION_MOUSE_PRESSED:
                case View_Abstract.ACTION_MOUSE_MOVED:
                case View_Abstract.ACTION_KEY_PRESSED:
                case View_Abstract.ACTION_MOUSE_DRAGGED:
                    view.doAction(action);
                    break;
            }
        } catch (Model_Exception ex) {//@removeLineFromPjs
            handleEasyError(ex, action);//@removeLineFromPjs
        } catch (Exception ex) {//@removeLineFromPjs
            handleUnexpectedError(ex, action);//@removeLineFromPjs
        }//@removeLineFromPjs

        /*@removeLineFromPjs
        } catch (ex) {
            if (ex instanceof Model_Exception) {
                handleEasyError(ex);
            } else {
                handleUnexpectedError(ex);
            }
        }
         @removeLineFromPjs*/

        // If there were any errors added in the tetris view (not thrown) then handle them now.
        for (int i = 0; i < view.getNoOfErrors(); i++) {
            Exception e = view.getErrors()[i];
            if (e instanceof Model_Exception) {
                handleEasyError((Model_Exception) e, action);
            } else {
                handleUnexpectedError(e, action);
            }
        }
        view.resetErrors();
        return null;
    }

    @Override
    public void setup() {
        /*@removeLineFromPjs
        int l = int(Controller_Tetris.getParameterByName("l"));
        if (l) {
            Controller_Tetris.NUMBER_OF_LINES_TETRIS_GRID = l;
        }
        int c = int(Controller_Tetris.getParameterByName("c"));
        if (c) {
            Controller_Tetris.NUMBER_OF_COLUMNS_TETRIS_GRID = c;
        }
        @removeLineFromPjs*/
        doAction(View_Abstract.ACTION_SETUP);
    }

    @Override
    public void draw() {
        if (view.isRestart()) {
            Controller_PlayerRobot.counterAllPlayers = 0;
            View_Player.COUNTER_PLAYER = 0;
            View_PlayerHuman_Grid.COUNTER_HUMAN_PLAYERS = 0;
            Controller_PlayerHuman.counter = 0;
            Controller_PlayerRobotRandom.counter = 0;
            Controller_PlayerRobotGenetic.counter = 0;
            doAction(View_Abstract.ACTION_SETUP);
        } else {
            doAction(View_Abstract.ACTION_DRAW);
        }
    }

    @Override
    public void mousePressed() {
        doAction(View_Abstract.ACTION_MOUSE_PRESSED);
    }

    @Override
    public void mouseMoved() {
        doAction(View_Abstract.ACTION_MOUSE_MOVED);
    }

    public void mouseDragged() {
        doAction(View_Abstract.ACTION_MOUSE_DRAGGED);
    }

    @Override
    public void keyPressed() {
        doAction(View_Abstract.ACTION_KEY_PRESSED);
    }

    /**
     * Handler error.
     */
    protected void handleEasyError(Model_Exception ex, int action) {
        String message = ex == null ? "" : ex.getMessage();//@removeLineFromPjs
        //JOptionPane.showMessageDialog(this, message);//@removeLineFromPjs
        /*@removeLineFromPjs
        alert(ex.message);
        @removeLineFromPjs*/
    }

    /**
     * Handler error.
     */
    protected void handleUnexpectedError(Exception ex, int action) {
        unexpectedErrors++;

        String message;
        try {
            message = Controller_Tetris.translate("An unexpected error has occurred.") + "\n";
        } catch (Exception exc) {
            message = "An unexpected error has occurred.\n";
        }

        try {
            if (unexpectedErrors <= MAX_UNEXPECTED_ERRORS_ALLOWED) {
                message += Controller_Tetris.translate("The game will continue, but its behaviour may not be fully stable.");
            } else {
                unexpectedErrors = MAX_UNEXPECTED_ERRORS_ALLOWED;
                message = null;
            }
        } catch (Exception exc) {
            //
        }

        if (message != null) {
            //JOptionPane.showMessageDialog(this, message);//@removeLineFromAll
            ex.printStackTrace();//@removeLineFromPjs
            /*@removeLineFromPjs
            alert(message);
            console.error(ex.message);
            @removeLineFromPjs*/
        }
    }

    /*
    @removeLineFromAll*/
}
/*@removeLineFromAll
    */

/*@removeLineFromPjs

public class Exception {
    protected String message;

    public Exception(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
};

@removeLineFromPjs */

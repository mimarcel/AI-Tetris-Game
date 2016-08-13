
import processing.core.PApplet;

import javax.swing.*;

/*
public class StartGame 
{
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
                    view.doAction(action);
                    break;
            }
        } catch (Model_Exception ex) {//
            handleEasyError(ex, action);//
        } catch (Exception ex) {//
            handleUnexpectedError(ex, action);//
        }//

        /*
        } catch (ex) {
            if (ex instanceof Model_Exception) {
                handleEasyError(ex);
            } else {
                handleUnexpectedError(ex);
            }
        }
         */

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

    public void setup() {
        /*
        int l = int(Controller_Tetris.getParameterByName("l"));
        if (l) {
            Controller_Tetris.NUMBER_OF_LINES_TETRIS_GRID = l;
        }
        int c = int(Controller_Tetris.getParameterByName("c"));
        if (c) {
            Controller_Tetris.NUMBER_OF_COLUMNS_TETRIS_GRID = c;
        }
        */
        doAction(View_Abstract.ACTION_SETUP);
    }

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

    public void mousePressed() {
        doAction(View_Abstract.ACTION_MOUSE_PRESSED);
    }

    public void mouseMoved() {
        doAction(View_Abstract.ACTION_MOUSE_MOVED);
    }

    public void keyPressed() {
        doAction(View_Abstract.ACTION_KEY_PRESSED);
    }

    /**
     * Handler error.
     */
    protected void handleEasyError(Model_Exception ex, int action) {
        String message = ex == null ? "" : ex.getMessage();//
        JOptionPane.showMessageDialog(this, message);//
        /*
        alert(ex.message);
        */
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
            ex.printStackTrace();//
            /*
            alert(message);
            console.error(ex.message);
            */
        }
    }

    /*
}
    */

/*

public class Exception {
    protected String message;

    public Exception(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
};

 */

package Tetris.View;

import Tetris.Controller.Controller;
import Tetris.Model.Model_Exception;

public interface View
{
    /**
     * Get parent view.
     */
    public View getParentView();

    /**
     * Get controller associated with this view.
     */
    public Controller getController();

    /**
     * Prepare view.
     */
    public void setup() throws Model_Exception;

    /**
     * Draw view.
     */
    public void draw() throws Model_Exception;

    /*
     * On mouse pressed.
     */
    public void mousePressed() throws Model_Exception;

    /*
    * On mouse moved.
    */
    public void mouseMoved() throws Model_Exception;

    /*
    * On key pressed.
    */
    public void keyPressed() throws Model_Exception;


    /*
    * On mouse dragged.
    */
    public void mouseDragged() throws Model_Exception;
}

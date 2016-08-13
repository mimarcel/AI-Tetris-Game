/* @pjs preload="/Media/Logo/Ai-Tetris-Logo.png,/Media/Language/en/icon.png,/Media/Language/ro/icon.png"; */

import processing.core.PApplet;

public class View_Tetris extends View_AbstractWithChildren
{
    public static final int GAME_STATE_IN_GAME = 1;
    public static final int GAME_STATE_IN_GENETIC_TRAINING = 2;
    public int GAME_STATE;

    // Params.
    public int WIDTH = 900;
    public int HEIGHT = 600;
    public float STROKE_WEIGHT = 1;
    public float STROKE_RED = 0;
    public float STROKE_GREEN = 0;
    public float STROKE_BLUE = 0;
    public static float FRAME_RATE = 10;
    protected boolean restart;

    // Controller.
    protected PApplet app;//
    protected int currentCursorType = -1;
    protected Exception[] errors;
    protected int noOfErrors = 0;
    protected int resultsPopup = 0;

    /**
     * Create Tetris view.
     */
    public View_Tetris(
            PApplet //
                    start
    ) throws Model_Exception {
        /*
        int w = int(Controller_Tetris.getParameterByName("w"));
        if (w) {
            this.WIDTH = w;
        }
        int h = int(Controller_Tetris.getParameterByName("h"));
        if (h) {
            this.HEIGHT = h;
        }
        */
        super(null, null, 10);
        this.app = start;//
    }

    public void setup() throws Model_Exception {
        this.initGame();
        /*
        // Set language.
        var activeLanguage = $('.language-div li.active');
        var idActiveLanguage = activeLanguage ? activeLanguage.attr('id') : null;
        var codeLanguage = idActiveLanguage ? idActiveLanguage.substr(9) : null;
        if (codeLanguage) {
            view.getController().getLanguageController().setCurrentLanguage(codeLanguage);
        }
        */
        this.app.size(WIDTH, HEIGHT);
        super.setup();
        this.app.frameRate(FRAME_RATE);
        this.app.cursor(PApplet.ARROW);
        //this.app.noLoop();
    }

    /**
     * Init and start game.
     */
    private void initGame() throws Model_Exception {
        // Create tetris controller. It will create the entire MVC environment.
        this.controller = new Controller_Tetris(this);


        this.getController().setBaseMediaUrl("http://tetris");//All

        // Add background view.
        this.addViewChild(this.getController().getBackgroundController().getView());

        // Add title view.
        this.addViewChild(this.getController().getTitleController().getView());

        // Add language view created.
        this.addViewChild(this.getController().getLanguageController().getView());

        // Add restart game view created.
        this.addViewChild(this.getController().getRestartController().getView());

        // Errors.
        this.errors = new Exception[10];

        this.getController().setGameStarted(false);
        this.resultsPopup = 1;

        this.startGame();
    }

    /**
     * Start game.
     */
    public void startGame() throws Model_Exception {
        if (Controller_Tetris.NUMBER_OF_LINES_TETRIS_GRID < 10
                || Controller_Tetris.NUMBER_OF_LINES_TETRIS_GRID > WIDTH
                || Controller_Tetris.NUMBER_OF_COLUMNS_TETRIS_GRID < 5
                || Controller_Tetris.NUMBER_OF_COLUMNS_TETRIS_GRID > HEIGHT) {
            if (this.getNoOfErrors() == 0) {
                this.addError(
                        new Model_Exception(
                                Controller_Tetris.translate("Grid cannot be smaller than 10x5 or higher than ")
                                        + WIDTH + "x" + HEIGHT + "."
                        )
                );
            }
            return;
        }
        // Set player types.
        /*
        String firstPlayer = Controller_Tetris.getParameterByName("p1");
        String secondPlayer = Controller_Tetris.getParameterByName("p2");
        int[] playerTypes;
         if (firstPlayer && secondPlayer) {
            playerTypes = new int[]
                {
                        (firstPlayer ? (int)firstPlayer : Model_Player.PLAYER_TYPE_HUMAN),
                        (secondPlayer ? (int)secondPlayer : Model_Player.PLAYER_TYPE_ROBOT_GENETIC),
                };
          } else {
            playerTypes = new int[]
                {
                        (firstPlayer ? (int)firstPlayer : Model_Player.PLAYER_TYPE_HUMAN)
                };
          }
         */
        int[] playerTypes = new int[]//
                {//
                        Model_Player.PLAYER_TYPE_ROBOT_GENETIC,//
                        Model_Player.PLAYER_TYPE_ROBOT_GENETIC,//
                };//

        this.getController().startGame(playerTypes);

        // Add player views created.
        for (Controller_PlayerAbstract playerController : this.getController().getPlayerControllers()) {
            this.addViewChild(playerController.getView());
        }
    }

    public void mouseMoved() throws Model_Exception {
        int oldCursorType = this.currentCursorType;
        this.askCursorToBe(PApplet.ARROW);
        super.mouseMoved();
        if (this.currentCursorType != oldCursorType) {
            this.app.cursor(this.currentCursorType);
        }
    }

    public void keyPressed() throws Model_Exception {
        if (!this.getController().isGameStarted()) {
            this.getController().setGameStarted(true);
        } else {
            super.keyPressed();
        }
    }

    public void draw() throws Model_Exception {
        super.draw();
        /*
        if (this.getController().isGameOver() == true && this.resultsPopup == 1){
            var players = [];for (Controller_PlayerAbstract cp : this.getController().getParentController().getPlayerControllers()) {players.push({n: cp.getPlayerName(), s: cp.getScore(), m : cp.getNumberOfMoves(), ti: new Date().getTime(), ty: cp.getModel().getType(), l: Controller_Tetris.NUMBER_OF_LINES_TETRIS_GRID, c: Controller_Tetris.NUMBER_OF_COLUMNS_TETRIS_GRID, d: View_Player_Grid.DRAW_YOUR_OWN_BLOCKS ? 1 :0});}this.resultsPopup++;players.sort(function(a,b){if (a.score < b.score) { return -1; } return a.score == b.score ? 0 : 1;});
            gameOver(players);
        }
        */
    }

    /**
     * Get controller.
     * @return Controller_Tetris
     */
    public Controller_Tetris getController() {
        return (Controller_Tetris) this.controller;
    }

    /**
     * Disable language view.
     */
    public void disableLanguageView() {
        this.getController().getLanguageController().getView().IS_ACTIVE = false;
        // If title view is disabled as well then set title_y and title_height to 0.
        if (!this.getController().getTitleController().getView().IS_ACTIVE) {
            this.getController().getTitleController().getView().TITLE_Y = 0;
            this.getController().getTitleController().getView().TITLE_HEIGHT = 0;
        }
    }

    /**
     * Disable title view.
     */
    public void disableTitleView() {
        this.getController().getTitleController().getView().IS_ACTIVE = false;
        // If language view is disabled as well then set title_y and title_height to 0.
        if (!this.getController().getLanguageController().getView().IS_ACTIVE) {
            this.getController().getTitleController().getView().TITLE_Y = 0;
            this.getController().getTitleController().getView().TITLE_HEIGHT = 0;
        }
    }

    /**
     * Set current cursor as cursorType.
     */
    public void askCursorToBe(int cursorType) {
        this.currentCursorType = cursorType;
    }

    /**
     * Get background padding value.
     */
    public float getBackgroundPadding() {
        return this.getController().getBackgroundController().getView().BACKGROUND_PADDING;
    }

    /**
     * Get background color - red.
     */
    public float getBackgroundColorRed() {
        return this.getController().getBackgroundController().getView().BACKGROUND_RED;
    }

    /**
     * Get background color - green.
     */
    public float getBackgroundColorGreen() {
        return this.getController().getBackgroundController().getView().BACKGROUND_GREEN;
    }

    /**
     * Get background color - blue.
     */
    public float getBackgroundColorBlue() {
        return this.getController().getBackgroundController().getView().BACKGROUND_BLUE;
    }

    public PApplet getApp() {//
        return app;//
    }//

    /**
     * Restart game.
     */
    public void setRestart(boolean b) {
        restart = b;
    }

    public boolean isRestart() {
        return restart;
    }

    /**
     * Prepare default stroke color and weight.
     */
    public void useDefaultStroke() {
        this.app.stroke(STROKE_RED, STROKE_GREEN, STROKE_BLUE);
        this.app.strokeWeight(STROKE_WEIGHT);
    }

    /**
     * Add error to be displayed at the end of the current action.
     */
    public void addError(Exception e) {
        this.errors[this.noOfErrors] = e;
        if (this.noOfErrors < this.errors.length) {
            this.noOfErrors++;
        }
    }

    /**
     * Get number of errors.
     */
    public int getNoOfErrors() {
        return noOfErrors;
    }

    /**
     * Get errors added in the view (not thrown).
     */
    public Exception[] getErrors() {
        return this.errors;
    }

    /**
     * Reset errors.
     */
    public void resetErrors() {
        this.noOfErrors = 0;
    }
}


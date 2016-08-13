package Tetris.Controller;

import Tetris.Model.*;
import Tetris.View.View_Tetris;

import java.util.Random;

public class Controller_Tetris extends Controller_Abstract
{
    public static int NUMBER_OF_LINES_TETRIS_GRID = 20;
    public static int NUMBER_OF_COLUMNS_TETRIS_GRID = 10;
    public boolean STOP_GAME_ON_FIRST_PLAYER_GAME_OVER = true;

    protected Controller_Background backgroundController;
    protected Controller_Title titleController;
    protected static Controller_Language languageController;
    protected static Controller_Translator translatorController;
    protected Controller_Restart restartController;
    protected Controller_PlayerAbstract[] playerControllers;

    public static int MAX_TETROMINOES_TO_SAVE = 10;
    // The tetrominoes generated will be saved here.
    protected Model_Tetromino[] tetrominoes;
    protected int tPos = -1;
    // Each player is currently having one of the tetrominoes here.
    protected int[] playersTetrominoesPos;

    // Mark gave-over status for players.
    protected int playersGameOver;
    // Count when human players move. It will be used by robots to move when humans move.
    protected long numberOfMovesFromHumanPlayers = 0;

    protected String baseMediaUrl = "";
    protected static Random random = new Random();//@removeLineFromPjs
    protected boolean gameOver = false;
    protected boolean gameStarted = false;
    protected Model_Logger logger = null; // used only during training (for logging) @removeLineFromAll
    protected boolean logEachGame;//@removeLineFromAll
    protected boolean logEachGameInAdvanceDetails;//@removeLineFromAll

    public void setLogger(Model_Logger logger) {//@removeLineFromAll
        this.logger = logger;//@removeLineFromAll
    }//@removeLineFromAll

    public Model_Logger getLogger() {//@removeLineFromAll
        return logger;//@removeLineFromAll
    }//@removeLineFromAll

    public Controller_Tetris(View_Tetris view) {
        super(null, view, null);

        if (view.GAME_STATE == View_Tetris.GAME_STATE_IN_GAME) {
            // Create translator.
            translatorController = new Controller_Translator(this);

            // Create language controller.
            languageController = new Controller_Language(this);

            // Create background controller.
            this.backgroundController = new Controller_Background(this);

            // Create title controller.
            this.titleController = new Controller_Title(this);

            // Create restart game controller.
            this.restartController = new Controller_Restart(this);
        }
    }

    @Override
    public View_Tetris getView() {
        return (View_Tetris) super.getView();
    }

    @Override
    public Model_Tetris getModel() {
        return (Model_Tetris) super.getModel();
    }

    /**
     * Get random double number.
     */
    public static double randomDouble() {
        return random.nextDouble();//@removeLineFromPjs
        /*@removeLineFromPjs
        return Math.random();
        @removeLineFromPjs*/
    }

    /**
     * Get random boolean value.
     */
    public static boolean randomBoolean() {
        return random.nextBoolean();//@removeLineFromPjs
        /*@removeLineFromPjs
        return Math.random() < 0.5;
        @removeLineFromPjs*/
    }

    /**
     * Get random int number.
     */
    public static int randomInt(int max) {
        return random.nextInt(max);//@removeLineFromPjs
        /*@removeLineFromPjs
        return Math.floor(Math.random()*max) % max;
        @removeLineFromPjs*/
    }

    /**
     * Start game.
     */
    public void startGame(int[] playersTypes) throws Model_Exception {
        // Create model.
        this.model = new Model_Tetris(playersTypes, NUMBER_OF_LINES_TETRIS_GRID, NUMBER_OF_COLUMNS_TETRIS_GRID);

        // Create player controllers.
        this.createPlayerControllers(this.getModel().getPlayers());

        // Game just started.
        this.playersGameOver = 0;

        // Make first move.
        this.tetrominoes = new Model_Tetromino[MAX_TETROMINOES_TO_SAVE];
        this.playersTetrominoesPos = new int[this.getModel().getNumberOfPlayers()];
        this.generateNewTetromino();
        for (int i = 0; i < this.getModel().getNumberOfPlayers(); i++) {
            this.playerControllers[i].setCurrentTetromino(new Model_Tetromino(this.tetrominoes[tPos]));
        }

        // Prepare next move.
        this.generateNewTetromino();
        for (int i = 0; i < this.playerControllers.length; i++) {
            this.playersTetrominoesPos[i] = this.tPos;
            this.playerControllers[i].setNextTetromino(new Model_Tetromino(this.tetrominoes[tPos]));
        }
    }

    /**
     * Create new random tetromino and add it at tPos.
     */
    public Model_Tetromino generateNewTetromino() throws Model_Exception {
        // Get random tetromino.
        int maxRandomType = this.getModel().getNumberOfTetrominos();
        int randomType = random.nextInt(maxRandomType);//@removeLineFromPjs
        /*@removeLineFromPjs
        var randomType = Math.floor(Math.random()*maxRandomType) % maxRandomType;
        @removeLineFromPjs*/
        Model_Tetromino t = this.getModel().getTetrominosFactory().createTetromino(randomType);

        // Save it.
        tPos++;
        tPos = tPos % MAX_TETROMINOES_TO_SAVE;
        this.tetrominoes[tPos] = t;
        return t;
    }

    /**
     * Create player controllers from player models.
     */
    protected void createPlayerControllers(Model_Player[] players) throws Model_Exception {
        this.playerControllers = new Controller_PlayerAbstract[players.length];
        for (int i = 0; i < players.length; i++) {
            switch (players[i].getType()) {
                case Model_Player.PLAYER_TYPE_HUMAN:
                    this.playerControllers[i] = new Controller_PlayerHuman(this, (Model_PlayerHuman) players[i]);
                    break;
                case Model_Player.PLAYER_TYPE_ROBOT_RANDOM:
                    this.playerControllers[i] = new Controller_PlayerRobotRandom(this, (Model_PlayerRobotRandom) players[i]);
                    break;
                case Model_Player.PLAYER_TYPE_ROBOT_GENETIC:
                    this.playerControllers[i] = new Controller_PlayerRobotGenetic(this, (Model_PlayerRobotGenetic) players[i]);
            }
        }
    }

    @Override
    public String toString() {
        return this.getModel().toString();
    }

    /**
     * Get title controller.
     */
    public Controller_Title getTitleController() {
        return titleController;
    }

    /**
     * Get background controller.
     */
    public Controller_Background getBackgroundController() {
        return backgroundController;
    }

    /**
     * Get language controller.
     */
    public Controller_Language getLanguageController() {
        return languageController;
    }

    /**
     * Get translator controller.
     */
    public Controller_Translator getTranslatorController() {
        return translatorController;
    }

    /**
     * Get restart game controller.
     */
    public Controller_Restart getRestartController() {
        return restartController;
    }

    /**
     * Get players controllers.
     */
    public Controller_PlayerAbstract[] getPlayerControllers() {
        return playerControllers;
    }

    /**
     * Get app title.
     */
    public String getTitle() throws Model_Exception {
        return this.titleController.getTitle();
    }

    /**
     * Translate a string in current language.
     */
    public static String translate(String s) throws Model_Exception {
        return translatorController != null
                ? translatorController.translate(s, languageController.getCurrentLanguage().getCode())
                : s;
    }

    /**
     * Get where media folder's base.
     */
    public String getMediaBaseUrl() {
        return baseMediaUrl;
    }

    /**
     * Set media folder's base.
     */
    public void setBaseMediaUrl(String s) {
        this.baseMediaUrl = s;
    }

    /**
     * Get number of players.
     */
    public int getNumberOfPlayers() {
        return this.playerControllers.length;
    }

    /**
     * Get number of players of given type.
     */
    public int getNumberOfPlayers(int type) {
        int n = 0;
        for (Controller_PlayerAbstract p : this.playerControllers) {
            if (p.getModel().getType() == type) {
                n++;
            }
        }
        return n;
    }

    public String getAppUrl(boolean includeCurrentLanguageCode) throws Model_Exception {
        String appUrl = this.getView().getApp().getDocumentBase().toString();//@removeLineFromPjs
        /*@removeLineFromPjs
        var appUrl = document.location.origin;
        if (!appUrl) {
            appUrl = document.location.protocol + "//" + document.location.host;
        }
        @removeLineFromPjs*/
        if (includeCurrentLanguageCode) {
            Model_Language currentLanguage = languageController.getCurrentLanguage();
            if (!currentLanguage.isDefault()) {
                appUrl += "/" + currentLanguage.getCode();
            }
        }
        return appUrl + "/";
    }

    /**
     * Get player pos by player name.
     */
    public int getPlayerPosByPlayerName(String playerName) throws Model_Exception {
        for (int i = 0; i < this.playerControllers.length; i++) {
            if (this.playerControllers[i].getPlayerName().equals(playerName)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * All players will get the same tetrominoes. Each of them has a current tetromino. Return next tetromino for given player.
     */
    public Model_Tetromino getNextTetromino(String playerName) throws Model_Exception {
        int playerPos = this.getPlayerPosByPlayerName(playerName);
        if (this.playersTetrominoesPos[playerPos] == tPos) {
            this.generateNewTetromino();
        }
        this.playersTetrominoesPos[playerPos]++;
        this.playersTetrominoesPos[playerPos] %= MAX_TETROMINOES_TO_SAVE;
        return new Model_Tetromino(this.tetrominoes[this.playersTetrominoesPos[playerPos]]);
    }

    /**
     * Player playerName lost the game
     */
    public void gameOver(String playerName) throws Model_Exception {
        int playerPos = this.getPlayerPosByPlayerName(playerName);

        if (this.STOP_GAME_ON_FIRST_PLAYER_GAME_OVER) {
            for (int i = 0; i < this.playerControllers.length; i++) {
                this.playerControllers[i].setGameOver(true);
            }
            this.playersGameOver = this.playerControllers.length;

            // Update final places.
            if (playerControllers.length == 1) {
                this.playerControllers[0].setFinalPlace(1);
            } else for (int i = 0; i < this.playerControllers.length; i++) {
                if (i != playerPos) {
                    // @todo If more than 2 players, need to change setting the final place.
                    this.playerControllers[i].setFinalPlace(1);
                } else {
                    this.playerControllers[i].setFinalPlace(2);
                }
            }
        } else {
            this.playerControllers[playerPos].setGameOver(true);
            this.playerControllers[playerPos].setFinalPlace(this.playerControllers.length - this.playersGameOver);
            this.playersGameOver++;
        }
        this.gameOver = this.playersGameOver >= this.playerControllers.length;
    }

    /**
     * Is game over?
     */
    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public int getPlayersGameOver() {
        return playersGameOver;
    }

    /**
     * Player updated grid. Add lines to other players.
     */
    public void playerUpdatedGrid(String playerName, int removedLines) throws Model_Exception {
        if (removedLines != 0) {
            for (int i = 0; i < this.playerControllers.length; i++) {
                if (!this.playerControllers[i].getPlayerName().equals(playerName)
                        && !this.playerControllers[i].isGameOver()) {
                    this.playerControllers[i].addBadLines(removedLines);
                }
            }
        }
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setLogEachGame(boolean logEachGame) {//@removeLineFromAll
        this.logEachGame = logEachGame;//@removeLineFromAll
    }//@removeLineFromAll

    //@removeLineFromAll
    public void setLogEachGameInAdvanceDetails(boolean logEachGameInAdvanceDetails) {//@removeLineFromAll
        this.logEachGameInAdvanceDetails = logEachGameInAdvanceDetails;//@removeLineFromAll
    }//@removeLineFromAll

    //@removeLineFromAll
    public boolean isLogEachGame() {//@removeLineFromAll
        return logEachGame;//@removeLineFromAll
    }//@removeLineFromAll

    //@removeLineFromAll
    public boolean isLogEachGameInAdvanceDetails() {//@removeLineFromAll
        return logEachGameInAdvanceDetails;//@removeLineFromAll
    }//@removeLineFromAll

    public void incrementNumberOfMovesFromHumanPlayers() {
        this.numberOfMovesFromHumanPlayers++;
    }

    public long getNumberOfMovesFromHumanPlayers() {
        return numberOfMovesFromHumanPlayers;
    }

    public static String getParameterByName(String name) {
        /*@removeLineFromPjs
        name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
        var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
                results = regex.exec(location.search);
        return results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
        @removeLineFromPjs*/
        return "";//@removeLineFromPjs
    }
}
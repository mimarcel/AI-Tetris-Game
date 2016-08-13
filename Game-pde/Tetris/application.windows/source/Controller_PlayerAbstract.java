

public abstract class Controller_PlayerAbstract implements Controller
{
    public static double PROBABILITY_TO_ADD_BAD_LINES = 0.25;
    public static long counterAllPlayers = 0;
    protected Controller_Tetris parentController;
    protected Model_PlayerAbstract model;
    protected View_Player view;
    protected Controller_Player_Info infoController;
    protected Controller_Player_Grid gridController;
    protected Controller_Player_Score scoreController;
    protected Controller_Player_Next nextController;

    protected int score = 0;
    protected Model_Tetromino nextTetromino;
    protected int finalPlace = -1;
    protected boolean gameOver = false;
    protected int numberOfMoves = 0;

    /**
     * Create controller.
     */
    public Controller_PlayerAbstract(Controller_Tetris controller_tetris, Model_PlayerAbstract model) throws Model_Exception {
        this.parentController = controller_tetris;
        this.model = model;
        Controller_PlayerAbstract.counterAllPlayers++;
        /*
        var name = Controller_Tetris.getParameterByName("p" + Controller_PlayerAbstract.counterAllPlayers + "_name");
        if (name) {
            this.getModel().setName(name);
        }

        var ptabl = Controller_Tetris.getParameterByName("ptabl");
        if (ptabl) {
            this.PROBABILITY_TO_ADD_BAD_LINES = ptabl;
        }
        */

        switch (model.getType()) {
            case Model_Player.PLAYER_TYPE_HUMAN:
                this.view = new View_PlayerHuman((Controller_PlayerHuman) this, this.parentController.getView());
                this.gridController = new Controller_PlayerHuman_Grid((Controller_PlayerHuman) this);
                break;
            case Model_Player.PLAYER_TYPE_ROBOT_RANDOM:
                this.view = new View_PlayerRobotRandom((Controller_PlayerRobotRandom) this, this.parentController.getView());
                this.gridController = new Controller_PlayerRobotRandom_Grid((Controller_PlayerRobotRandom) this);
                break;
            case Model_Player.PLAYER_TYPE_ROBOT_GENETIC:
                this.view = new View_PlayerRobotGenetic((Controller_PlayerRobotGenetic) this, this.parentController.getView());
                this.gridController = new Controller_PlayerRobotGenetic_Grid((Controller_PlayerRobotGenetic) this);
                break;
        }

        this.infoController = new Controller_Player_Info(this);
        this.nextController = new Controller_Player_Next(this);
        this.scoreController = new Controller_Player_Score(this);
    }

    /**
     * Get player model.
     */
    public Model_PlayerAbstract getModel() {
        return model;
    }

    /**
     * Get view player.
     */
    public View_Player getView() {
        return view;
    }

    /**
     * Get parent controller.
     */
    public Controller_Tetris getParentController() {
        return parentController;
    }

    /**
     * Get info controller.
     */
    public Controller_Player_Info getInfoController() {
        return infoController;
    }

    /**
     * Get grid controller.
     */
    public Controller_Player_Grid getGridController() {
        return gridController;
    }

    /**
     * Get score controller.
     */
    public Controller_Player_Score getScoreController() {
        return scoreController;
    }

    /**
     * Get next tetromino controller.
     */
    public Controller_Player_Next getNextController() {
        return nextController;
    }

    /**
     * Get store.
     */
    public int getScore() {
        return score;
    }

    /**
     * Get next tetromino.
     */
    public Model_Tetromino getNextTetromino() {
        return nextTetromino;
    }

    /**
     * Get player name associated with this controller.
     */
    public String getPlayerName() throws Model_Exception {
        return this.model.getName();
    }

    /**
     * Set current tetromino.
     */
    public void setCurrentTetromino(Model_Tetromino currentTetromino) {
        this.getGridController().setCurrentTetromino(currentTetromino);
    }

    /**
     * Set next tetromino.
     */
    public void setNextTetromino(Model_Tetromino nextTetromino) {
        this.nextTetromino = nextTetromino;
    }

    /**
     * Get current tetromino.
     */
    public Model_Tetromino getCurrentTetromino() throws Model_Exception {
        return this.getGridController().getCurrentTetromino();
    }

    /**
     * Get next tetromino for this player. If it's not already generated then generate a new one.
     */
    public void setNextTetrominoForPlayer() throws Model_Exception {
        this.numberOfMoves++;
        Model_Tetromino t = this.getParentController().getNextTetromino(this.getPlayerName());
        this.setNextTetromino(t);
    }

    /**
     * Update score.
     */
    public void updateScore(int plus) {
        this.score += plus;
    }

    /**
     * Player got game over.
     */
    public void playerGameOver() throws Model_Exception {
        this.gameOver = true;
        this.getParentController().gameOver(this.getPlayerName());
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    /**
     * Get final place.
     */
    public int getFinalPlace() {
        return finalPlace;
    }

    /**
     * Set final place.
     */
    public void setFinalPlace(int finalPlace) {
        this.finalPlace = finalPlace;
    }

    /**
     * Player updated grid.
     */
    public void playerUpdatedGrid(int removedLines) throws Model_Exception {
        this.getParentController().playerUpdatedGrid(this.getPlayerName(), removedLines);
    }

    /**
     * Called when other player removed lines from grid.
     * With a probability, add 'bad' lines to rival players.
     */
    public void addBadLines(int removedLines) {
        for (int k = 0; k < removedLines; k++) {
            double random = Controller_Tetris.randomDouble();
            if (random < PROBABILITY_TO_ADD_BAD_LINES) {
                short[][] grid = this.getGridController().getModel().getGrid();

                // Move up all the lines.
                for (int i = 0; i < grid.length - 1; i++) {
                    for (int j = 0; j < grid[i].length; j++) {
                        grid[i][j] = grid[i + 1][j];
                    }
                }

                // Add one bottom line.
                for (int j = 0; j < grid[grid.length - 1].length; j++) {
                    grid[grid.length - 1][j] = Model_Color.BLACK;
                }
                int randomPos = Controller_Tetris.randomInt(grid[grid.length - 1].length);
                grid[grid.length - 1][randomPos] = Model_Color.NONE;
                this.getGridController().getModel().setGrid(grid);
            }
        }
    }

    public String toString() {
        this.getGridController().addCurrentTetrominoToCurentGrid();
        String s = "";
        if (this.getModel().getName() == null) {
            try {
                s += this.getPlayerName() + "\n";
            } catch (Model_Exception ignored) {
            }
        }
        s += this.getModel().toString();
        this.getGridController().removeCurrentTetrominoFromGrid();
        return s;
    }

    public int getNumberOfMoves() {
        return numberOfMoves;
    }
}

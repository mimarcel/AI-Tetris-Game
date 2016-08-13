

public class Controller_PlayerRobotGenetic extends Controller_PlayerRobot
{
    public static long counter = 0;
    public static double EVALUATION_CURRENT_GRID_IMPORTANCE = 0.5;
    public static double EVALUATION_NEXT_TETROMINO_IMPORTANCE = 0.25;
    public static double EVALUATION_ALL_TETROMINOES_IMPORTANCE = 0.25;
    public static double DEFAULT_ACCURACY_HOW_MANY_POSITIONS_TO_CHECK_IN_ADVANCE_PERCENTAGE = 1;
    public double ACCURACY_HOW_MANY_POSITIONS_TO_CHECK_IN_ADVANCE_PERCENTAGE = DEFAULT_ACCURACY_HOW_MANY_POSITIONS_TO_CHECK_IN_ADVANCE_PERCENTAGE;
    protected final int NO_F_SET = -100000;

    protected long countedAs;
    protected Model_Tetromino[] tetrominoes = null;

    /**
     * Create controller.
     */
    public Controller_PlayerRobotGenetic(Controller_Tetris controller_tetris, Model_PlayerRobotGenetic model) throws Model_Exception {
        super(controller_tetris, model);
        counter++;
        this.countedAs = counter;
        /*
        var ahmptciap = Controller_Tetris.getParameterByName("p" + Controller_PlayerAbstract.counterAllPlayers + "_ahmptciap");
        if (ahmptciap) {
            this.ACCURACY_HOW_MANY_POSITIONS_TO_CHECK_IN_ADVANCE_PERCENTAGE = ahmptciap;
        }
        double[] weightsFromParams;
        for (var i=0; i < 6; i++) {
            var w = Controller_Tetris.getParameterByName("p" + Controller_PlayerAbstract.counterAllPlayers + "_w" + i);
            if (w) {
                if (weightsFromParams == null) {
                    weightsFromParams = new double[6];
                }
                weightsFromParams[i] = w;
            }
        }
        if (weightsFromParams) {
            this.getModel().setWeights(weightsFromParams);
        }
        */
    }

    public Model_PlayerRobotGenetic getModel() {
        return (Model_PlayerRobotGenetic) super.getModel();
    }

    public View_PlayerRobotGenetic getView() {
        return (View_PlayerRobotGenetic) super.getView();
    }

    public String getPlayerName() throws Model_Exception {
        String playerName = super.getPlayerName();
        if (playerName == null) {
            playerName = Controller_Tetris.translate("Robot Genetic") + (countedAs != 1 ? " " + countedAs : "");
        }
        return playerName;
    }

    protected boolean evaluateInAdvanceCurrentVal = true;





    public void findNextMove() throws Model_Exception {
        double[] result = this.findBestPositionForCurrentTetrominoInCurrentGrid();
        int besti = (int) result[0];
        int bestj = (int) result[1];
        int bestk = (int) result[2];
        if (besti != -1 && bestj != -1 && bestk != -1) {
            this.getModel().setNextTetrominoPositionLine(besti);
            this.getModel().setNextTetrominoPositionColumn(bestj);
            for (int l = 1; l <= bestk; l++) {
                this.getCurrentTetromino().rotateToLeft();
            }
        }
    }

    public double[] findBestPositionForCurrentTetrominoInCurrentGrid() throws Model_Exception {
        return this.findBestPositionInAdvance(
                true,
                this.getCurrentTetromino(),
                this.getModel().getCurrentTetrominoPositionLine(),
                this.getModel().getCurrentTetrominoPositionColumn(),
                this.getModel().getGrid()
        );
    }

    public double[] findBestPositionInAdvance(boolean evaluateInAdvance, Model_Tetromino t, int currentPosLine, int currentPosColumn, short[][] grid) throws Model_Exception {
        this.evaluateInAdvanceCurrentVal = evaluateInAdvance;
        int besti = -1, bestj = -1, bestk = -1;
        double bestf = NO_F_SET;

//        //Test tetrominoCanFindAWayToPosition function. why are you seeing this anyway?
//        t= Model_Tetromino_Factory.getInstance().createTetromino(Model_Tetromino_Factory.TETROMINO_TYPE_I);
//        //t.rotateToLeft();
//        short [][] gridc = this.getGridCopy(this.getModel().getGrid());
//        int TOL= 9;
//        int TOC = 0;
//        short[][] ADD = new short[][] {
//                {1, 0, 1, 0, 1},
//                {1, 0, 1, 0, 0},
//                {1, 0, 1, 0, 1},
//                {1, 0, 1, 0, 0},
//                {1, 1},
//        };
//
//        currentPosLine = 9;
//        currentPosColumn = 3;
//        int toLine= 9;
//        int toColumn = 1;
//
//        this.getParentController().getLogger().log("Tetromino:\n" + t.toStringOnlyCurrentPosition() +
//        " from (" + currentPosLine + " " + currentPosColumn + ") to (" + toLine + " " + toColumn + ")\n");
//        this.getGridController().addMatrixToGrid(ADD,(short)5, TOL, TOC, gridc) ;
//        this.getParentController().getLogger().log("Grid After:\n" + this.gridToString(gridc));
//        this.getParentController().getLogger().log( "" +
//                this.getGridController().tetrominoCanFindAWayToPosition(t, currentPosLine, currentPosColumn, toLine, toColumn, gridc)
//                );
//        System.exit(0);

        if (false) {
        }

        // Find the best position (besti, bestj) for current tetromino rotated bestk times by 90 degrees to the left.
        for (int k = 0; k <= 3; k++) {
            if (!t.currentRotationIsDuplicated()) {
                if (false) {
                }
                for (int i = currentPosLine; i < grid.length; i++) {
                    for (int j = 0; j < grid[i].length; j++) {
                        // Check this position always when evaluateInAdvance is true.
                        // If evaluateInAdvance is false then don't check this position unless accuracy is good.
                        if ((evaluateInAdvance || Controller_Tetris.randomDouble() < ACCURACY_HOW_MANY_POSITIONS_TO_CHECK_IN_ADVANCE_PERCENTAGE)
                                // And is it a final position?
                                && this.getGridController().tetrominoCanBePlacedOnPosition(t, i, j, grid)
                                && !this.getGridController().tetrominoCanBePlacedOnPosition(t, i + 1, j, grid)) {
                            // Can current tetromino can be moved to this position?
                            if (this.getGridController().tetrominoCanFindAWayToPosition(t, currentPosLine, currentPosColumn, i, j, grid)) {
                                if (false) {
                                }
                                // Evaluate this position.
                                double f = this.evaluatePosition(t, i, j, grid, evaluateInAdvance);
                                if (evaluateInAdvance) {
                                    if (false) {
                                    }
                                } else {
                                    if (false) {
                                    }
                                }
                                if (f > bestf) {
                                    besti = i;
                                    bestj = j;
                                    bestk = k;
                                    bestf = f;
                                    if (false) {
                                    }
                                } else {
                                    if (false) {
                                    }
                                }
                            } else {
                                if (false) {
                                }
                            }
                        }
                    }
                }
                if (false) {
                }
            }
            t.rotateToLeft();
        }
        if (evaluateInAdvance) {
            if (false) {
            }
            if (false) {
            }
            if (false) {
            }
        }
        if (false) {
        }
        return new double[]{besti, bestj, bestk, bestf};
    }

    short[][] gridCopyInAdvanceTrue = new short[Controller_Tetris.NUMBER_OF_LINES_TETRIS_GRID][Controller_Tetris.NUMBER_OF_COLUMNS_TETRIS_GRID];
    short[][] gridCopyInAdvanceFalse = new short[Controller_Tetris.NUMBER_OF_LINES_TETRIS_GRID][Controller_Tetris.NUMBER_OF_COLUMNS_TETRIS_GRID];

    protected short[][] getGridCopy(short[][] grid) {
        short[][] gridCopy = this.evaluateInAdvanceCurrentVal ? gridCopyInAdvanceTrue : gridCopyInAdvanceFalse;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                gridCopy[i][j] = grid[i][j];
            }
        }
        return gridCopy;
    }

    /**
     * Evaluate how good this position is.
     */
    public double evaluatePosition(Model_Tetromino t, int line, int column, short[][] grid, boolean evaluateInAdvance) throws Model_Exception {
        if (evaluateInAdvance) {
            if (false) {
            }
        }
        short[][] gridCopy = this.getGridCopy(grid);
        double f = this.evaluateGrid(grid, gridCopy, t, line, column);
        if (evaluateInAdvance) {
            if (false) {
            }
            // Final evaluation =  EVALUATION_CURRENT_GRID_IMPORTANCE * f(currentGrid)
            // + EVALUATION_ALL_TETROMINOES_IMPORTANCE * f(move in advance once - all tetrominoes)
            // + EVALUATION_NEXT_TETROMINO_IMPORTANCE * f(move in advance once - next tetromino)
            f = EVALUATION_CURRENT_GRID_IMPORTANCE * f;
            double missedToCheckPercentage = 0;
            for (Model_Tetromino tetromino : this.getAllTetrominoes()) {
                double[] result = this.findBestPositionInAdvance(false, tetromino, 0,
                        /*
                        int(Controller_Tetris.NUMBER_OF_COLUMNS_TETRIS_GRID / 2)
                        */
                        Controller_Tetris.NUMBER_OF_COLUMNS_TETRIS_GRID / 2//
                        , gridCopy);
                this.evaluateInAdvanceCurrentVal = true;
                double bestf = result[3];
                if (bestf != NO_F_SET) {
                    f += EVALUATION_ALL_TETROMINOES_IMPORTANCE / this.getAllTetrominoes().length * bestf;
                } else {
                    missedToCheckPercentage += EVALUATION_ALL_TETROMINOES_IMPORTANCE / this.getAllTetrominoes().length;
                }

                if (tetromino.getCode().equals(this.getNextTetromino().getCode())) {
                    if (bestf != NO_F_SET) {
                        f += EVALUATION_NEXT_TETROMINO_IMPORTANCE * bestf;
                    } else {
                        missedToCheckPercentage += EVALUATION_NEXT_TETROMINO_IMPORTANCE;
                    }
                }
            }

            // If evaluation in advance for some tetrominoes did result in NO_F_SET then it was ignored before.
            // Now rescale f.
            f = f / (1 - missedToCheckPercentage);
        }

        return f;
    }

    protected Model_Tetromino[] getAllTetrominoes() throws Model_Exception {
        if (this.tetrominoes == null) {
            this.tetrominoes = Model_Tetromino_Factory.getInstance().getAllTetrominoes();
        }
        return this.tetrominoes;
    }

    /**
     * Evaluate grid depending on the weights and the values of the parameters for current grid.
     */
    public double evaluateGrid(short[][] gridBefore, short[][] gridAfter, Model_Tetromino t, int line, int column) throws Model_Exception {
        // Before
            if (false) {
            }
        int[] paramsBefore = !this.evaluateInAdvanceCurrentVal
                ? this.gridBeforeParams
                : this.evaluateGridParams(gridBefore);
        int maxHeightBefore = paramsBefore[0];
        int noOfHolesBefore = paramsBefore[1];
        int noOfBlockadesBefore = paramsBefore[2];
        int freeSpaceBefore = paramsBefore[3];
        int noOfNeighboursBefore = paramsBefore[4];

        // Go ahead with the game.
        this.getGridController().addTetrominoToGrid(t, line, column, gridAfter);
        int noOfClearedLines = this.getGridController().updateGrid(gridAfter);

        // After
        if (false) {
        }
        int[] paramsAfter = this.evaluateGridParams(gridAfter);
        if (this.evaluateInAdvanceCurrentVal) {
            if (false) {
            }
            this.saveGridBeforeParams(paramsAfter);// current gridAfter will be gridBefore for next move.
        }
        int maxHeightAfter = paramsAfter[0];
        int noOfHolesAfter = paramsAfter[1];
        int noOfBlockadesAfter = paramsAfter[2];
        int freeSpaceAfter = paramsAfter[3];
        int noOfNeighboursAfter = paramsAfter[4];
        if (false) {
        }

        return this.getModel().getEvaluationFunction().evaluate(
                this.getModel().getWeights(),
                gridAfter,
                maxHeightBefore, maxHeightAfter,
                noOfHolesBefore, noOfHolesAfter,
                noOfBlockadesBefore, noOfBlockadesAfter,
                freeSpaceBefore, freeSpaceAfter,
                noOfNeighboursBefore, noOfNeighboursAfter,
                noOfClearedLines
        );
    }

    private int[] gridBeforeParams;

    protected void saveGridBeforeParams(int[] gridParams) {
        this.gridBeforeParams = gridParams;
    }

    protected int[] noOfFilledGridCellsFoundOnColumn = new int[Controller_Tetris.NUMBER_OF_COLUMNS_TETRIS_GRID];
    protected int[] noOfBlockadesForColumn = new int[Controller_Tetris.NUMBER_OF_COLUMNS_TETRIS_GRID];

    protected int[] evaluateGridParams(short[][] grid) {
        int maxHeight = 0;
        int noOfHoles = 0;
        int noOfBlockades = 0;
        int freeSpace = 0;
        int noOfNeighbours = 0;

        // Init data.
        for (int i = 0; i < noOfFilledGridCellsFoundOnColumn.length; i++) {
            noOfFilledGridCellsFoundOnColumn[i] = 0;
        }
        for (int i = 0; i < noOfBlockadesForColumn.length; i++) {
            noOfBlockadesForColumn[i] = 0;
        }

        // From highest row to lowest row
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 0) {
                    // Empty cell!

                    if (noOfFilledGridCellsFoundOnColumn[j] > 0) {
                        // Number of holes increased.
                        noOfHoles++;
                    } else {
                        // Free space increased.
                        freeSpace++;
                    }

                    // Set number of blockades for column j.
                    noOfBlockadesForColumn[j] = noOfFilledGridCellsFoundOnColumn[j];
                } else {
                    // Filled cell!
                    noOfFilledGridCellsFoundOnColumn[j]++;

                    if (grid.length - i > maxHeight) {
                        // Max height updated.
                        maxHeight = grid.length - i;
                    }

                    // Number of neighbours increased.
                    noOfNeighbours += this.numberOfNeighbours(grid, i, j);
                }
            }
        }

        // Finalise data.
        for (int i = 0; i < noOfBlockadesForColumn.length; i++) {
            noOfBlockades += noOfBlockadesForColumn[i];
        }

        if (false) {
        }
        return new int[]{maxHeight, noOfHoles, noOfBlockades, freeSpace, noOfNeighbours};
    }

    protected int numberOfNeighbours(short[][] grid, int i, int j) {
        return this.numberOfNeighboursValueOf(grid, i, j - 1)
                + this.numberOfNeighboursValueOf(grid, i - 1, j - 1)
                + this.numberOfNeighboursValueOf(grid, i - 1, j)
                + this.numberOfNeighboursValueOf(grid, i - 1, j + 1)
                + this.numberOfNeighboursValueOf(grid, i, j + 1)
                + this.numberOfNeighboursValueOf(grid, i + 1, j + 1)
                + this.numberOfNeighboursValueOf(grid, i + 1, j)
                + this.numberOfNeighboursValueOf(grid, i + 1, j - 1);
    }

    protected int numberOfNeighboursValueOf(short[][] grid, int i, int j) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[i].length) {
            return 2;// out-of-grid is considered as very good, i.e. it counts as 2 neighbours
        }
        return grid[i][j] == 0 ? 0 : 1;
    }

    public String getWeightsAsString() {
        String s = "";
        for (int i = 0; i < this.getModel().getWeights().length; i++) {
            s += this.getModel().getWeight(i) + " ";
        }
        return s;
    }

    public String toString() {
        return super.toString()
                .replace("Grid", String.format("Chromosome: %s\nGrid", this.getWeightsAsString()))
                ;
    }
}

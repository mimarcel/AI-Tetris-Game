package Tetris.Controller.Player;

import Tetris.Controller.Controller_Abstract;
import Tetris.Controller.Controller_PlayerAbstract;
import Tetris.Controller.Controller_Tetris;
import Tetris.Controller.PlayerHuman.Controller_PlayerHuman_Grid;
import Tetris.Controller.PlayerRobotGenetic.Controller_PlayerRobotGenetic_Grid;
import Tetris.Controller.PlayerRobotRandom.Controller_PlayerRobotRandom_Grid;
import Tetris.Model.*;
import Tetris.View.Player.View_Player_Grid;
import Tetris.View.PlayerHuman.View_PlayerHuman_Grid;
import Tetris.View.PlayerRobotGenetic.View_PlayerRobotGenetic_Grid;
import Tetris.View.PlayerRobotRandom.View_PlayerRobotRandom_Grid;
import Tetris.View.View_PlayerHuman;
import Tetris.View.View_PlayerRobotGenetic;
import Tetris.View.View_PlayerRobotRandom;

public abstract class Controller_Player_Grid extends Controller_Abstract
{
    public long TIME_TETROMINO_FALLS_DOWN_ONE_LEVEL = 500;
    public long CHECK_TETROMINO_SHOULD_BE_CHANGED = 500;
    protected boolean disabledFalling = false;

    /**
     * Create controller.
     */
    public Controller_Player_Grid(Controller_PlayerAbstract controller_player) {
        super(controller_player, null, null);

        switch (controller_player.getModel().getType()) {
            case Model_Player.PLAYER_TYPE_HUMAN:
                this.view = new View_PlayerHuman_Grid(
                        (Controller_PlayerHuman_Grid) this,
                        (View_PlayerHuman) this.getParentController().getView()
                );
                break;
            case Model_Player.PLAYER_TYPE_ROBOT_RANDOM:
                this.view = new View_PlayerRobotRandom_Grid(
                        (Controller_PlayerRobotRandom_Grid) this,
                        (View_PlayerRobotRandom) this.getParentController().getView()
                );
                break;
            case Model_Player.PLAYER_TYPE_ROBOT_GENETIC:
                this.view = new View_PlayerRobotGenetic_Grid(
                        (Controller_PlayerRobotGenetic_Grid) this,
                        (View_PlayerRobotGenetic) this.getParentController().getView()
                );
                break;
        }

        this.model = controller_player.getModel();
    }

    @Override
    public View_Player_Grid getView() {
        return (View_Player_Grid) super.getView();
    }

    @Override
    public Model_PlayerAbstract getModel() {
        return (Model_PlayerAbstract) this.getParentController().getModel();
    }

    @Override
    public Controller_PlayerAbstract getParentController() {
        return (Controller_PlayerAbstract) super.getParentController();
    }

    /**
     * Get where tetromino should be placed on X.
     */
    public float getTetrominoPositionX() {
        return this.getView().GRID_START_X
                + this.getParentController().getView().SIZE_MATRIX_ELEMENT_X
                * this.getModel().getCurrentTetrominoPositionColumn();
    }

    /**
     * Get where tetromino should be placed on Y.
     */
    public float getTetrominoPositionY() {
        return this.getView().GRID_START_Y
                + this.getParentController().getView().SIZE_MATRIX_ELEMENT_Y
                * this.getModel().getCurrentTetrominoPositionLine();
    }

    /**
     * Set current tetromino.
     */
    public void setCurrentTetromino(Model_Tetromino currentTetromino) {
        this.setCurrentTetrominoPositionLine(0);
        this.setCurrentTetrominoPositionColumn(
                /*@removeLineFromPjs
                        int(Controller_Tetris.NUMBER_OF_COLUMNS_TETRIS_GRID / 2)
                        @removeLineFromPjs*/
                Controller_Tetris.NUMBER_OF_COLUMNS_TETRIS_GRID / 2//@removeLineFromPjs
        );
        this.getModel().setTetrominoLastFallingTime(this.getView().getParentView().getParentView().getApp().millis());
        this.getModel().setCurrentTetromino(currentTetromino);
    }

    /**
     * Set the column position of the current tetromino. Update grid.
     */
    public void setCurrentTetrominoPositionColumn(int column) {
        this.getModel().setCurrentTetrominoPositionColumn(column);
    }

    /**
     * Set the line position of the current tetromino. Update grid.
     */
    public void setCurrentTetrominoPositionLine(int line) {
        this.getModel().setCurrentTetrominoPositionLine(line);
    }

    /**
     * Remove current tetromino from grid.
     */
    public void removeCurrentTetrominoFromGrid() {
        int line = this.getModel().getCurrentTetrominoPositionLine();
        int column = this.getModel().getCurrentTetrominoPositionColumn();
        this.removeTetrominoFromGrid(this.getCurrentTetromino(), line, column);
    }

    /**
     * Remove tetromino from grid.
     */
    protected void removeTetrominoFromGrid(Model_Tetromino tetromino, int line, int column) {
        if (tetromino == null) {
            return;
        }
        this.removeMatrixFromGrid(tetromino.getCurrentRotation(), line, column);
    }

    /**
     * Remove matrix from grid.
     */
    protected void removeMatrixFromGrid(short[][] pos, int line, int column) {
        short[][] grid = this.getModel().getGrid();
        for (int i = 0; i < pos.length; i++) {
            for (int j = 0; j < pos[i].length; j++) {
                if (pos[i][j] != 0
                        && line + i >= 0 && line + i < grid.length//@removeLineFromAll
                        && column + j >= 0 && column + j < grid[i].length//@removeLineFromAll
                        ) {
                    grid[line + i][column + j] = Model_Color.NONE;
                }
            }
        }
        this.getModel().setGrid(grid);
    }

    /**
     * Add current tetromino from grid.
     */
    public void addCurrentTetrominoToCurentGrid() {
        int line = this.getModel().getCurrentTetrominoPositionLine();
        int column = this.getModel().getCurrentTetrominoPositionColumn();
        this.addTetrominoToGrid(this.getCurrentTetromino(), line, column, this.getModel().getGrid());
    }

    /**
     * Add tetromino to grid.
     */
    public void addTetrominoToGrid(Model_Tetromino tetromino, int line, int column, short[][] grid) {
        if (tetromino == null) {
            return;
        }
        this.addMatrixToGrid(tetromino.getCurrentRotation(), tetromino.getColor(), line, column, grid);
    }

    /**
     * Add matrix to grid using color given.
     */
    public void addMatrixToGrid(short[][] pos, short color, int line, int column, short[][] grid) {
        for (int i = 0; i < pos.length; i++) {
            for (int j = 0; j < pos[i].length; j++) {
                if (pos[i][j] != 0
                        && line + i >= 0 && line + i < grid.length//@removeLineFromAll
                        && column + j >= 0 && column + j < grid[i].length//@removeLineFromAll
                        ) {
                    grid[line + i][column + j] = color;
                }
            }
        }
    }

    /**
     * Get current tetromino.
     */
    public Model_Tetromino getCurrentTetromino() {
        return this.getModel().getCurrentTetromino();
    }

    /**
     * Return true if current tetromino can be placed on give position in the grid.
     */
    public boolean tetrominoCanBePlacedOnPosition(Model_Tetromino tetromino, int line, int column, short[][] grid) {
        short[][] position = tetromino.getCurrentRotation();
        for (int i = 0; i < position.length; i++) {
            for (int j = 0; j < position[i].length; j++) {
                // Out of grid?
                if (position[i][j] != 0 &&
                        (line + i < 0 || line + i >= grid.length
                                || column + j < 0 || column + j >= grid[line + i].length)
                        ) {
                    return false;
                }
                // Is over grid element?
                if (position[i][j] != 0 && grid[line + i][column + j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Disable falling. (for robots)
     */
    public void disableFalling() {
        this.disabledFalling = true;
    }

    /**
     * Enable falling.
     */
    public void enableFalling() {
        this.disabledFalling = false;
    }

    /**
     * Check current tetromino.
     */
    public boolean checkCurrentTetromino() throws Model_Exception {
        long time = this.getParentController().getView().getParentView().getApp().millis();
        if (!this.disabledFalling &&
                time - this.getModel().getTetrominoLastFallingTime() >= TIME_TETROMINO_FALLS_DOWN_ONE_LEVEL) {
            this.getModel().setTetrominoLastFallingTime(time);
            // Down one level.
            if (this.tetrominoCanBePlacedOnPosition(
                    this.getCurrentTetromino(),
                    this.getModel().getCurrentTetrominoPositionLine() + 1,
                    this.getModel().getCurrentTetrominoPositionColumn(),
                    this.getModel().getGrid())
                    ) {
                this.setCurrentTetrominoPositionLine(this.getModel().getCurrentTetrominoPositionLine() + 1);
                return true;
            }
        }
        if (time - this.getModel().getTimeLastCheckForTetrominoShouldBeChanged() >= CHECK_TETROMINO_SHOULD_BE_CHANGED) {
            this.getModel().setTimeLastCheckForTetrominoShouldBeChanged(time);
            if (!this.tetrominoCanBePlacedOnPosition(
                    this.getCurrentTetromino(),
                    this.getModel().getCurrentTetrominoPositionLine() + 1,
                    this.getModel().getCurrentTetrominoPositionColumn(),
                    this.getModel().getGrid())
                    ) {
                this.newTetrominoOnGrid();
                return true;
            }
        }

        return false;
    }

    /**
     * Change current tetromino to be next tetromino and generate a new next tetromino.
     */
    public synchronized void newTetrominoOnGrid() throws Model_Exception {
        // Next tetromino
        this.addCurrentTetrominoToCurentGrid();
        this.setCurrentTetromino(this.getParentController().getNextTetromino());

        // Game over?
        if (!this.tetrominoCanBePlacedOnPosition(
                this.getCurrentTetromino(),
                this.getModel().getCurrentTetrominoPositionLine(),
                this.getModel().getCurrentTetrominoPositionColumn(),
                this.getModel().getGrid())
                ) {
            this.getParentController().playerGameOver();
        }

        this.getParentController().setNextTetrominoForPlayer();
    }

    /**
     * Move right.
     */
    public void moveCurrentTetrominoToRight() {
        if (this.tetrominoCanBePlacedOnPosition(
                this.getCurrentTetromino(),
                this.getModel().getCurrentTetrominoPositionLine(),
                this.getModel().getCurrentTetrominoPositionColumn() + 1,
                this.getModel().getGrid())
                ) {
            this.setCurrentTetrominoPositionColumn(this.getModel().getCurrentTetrominoPositionColumn() + 1);
        }
    }

    /**
     * Move left.
     */
    public void moveCurrentTetrominoToLeft() {
        if (this.tetrominoCanBePlacedOnPosition(
                this.getCurrentTetromino(),
                this.getModel().getCurrentTetrominoPositionLine(),
                this.getModel().getCurrentTetrominoPositionColumn() - 1,
                this.getModel().getGrid())
                ) {
            this.setCurrentTetrominoPositionColumn(this.getModel().getCurrentTetrominoPositionColumn() - 1);
        }
    }

    /**
     * Move down.
     */
    public void moveCurrentTetrominoDownOneLevel() {
        if (this.tetrominoCanBePlacedOnPosition(
                this.getCurrentTetromino(),
                this.getModel().getCurrentTetrominoPositionLine() + 1,
                this.getModel().getCurrentTetrominoPositionColumn(),
                this.getModel().getGrid())
                ) {
            this.setCurrentTetrominoPositionLine(this.getModel().getCurrentTetrominoPositionLine() + 1);
        }
    }

    /**
     * Fall current tetromino.
     */
    public void fallCurrentTetromino() {
        while (this.tetrominoCanBePlacedOnPosition(
                this.getCurrentTetromino(),
                this.getModel().getCurrentTetrominoPositionLine() + 1,
                this.getModel().getCurrentTetrominoPositionColumn(),
                this.getModel().getGrid())) {
            this.setCurrentTetrominoPositionLine(this.getModel().getCurrentTetrominoPositionLine() + 1);
        }
    }

    /**
     * Rotate tetromino to right if possible.
     */
    public void rotateTetrominoToRight() {
        this.getModel().getCurrentTetromino().rotateToRight();
        int i;
        for (i = 0; i < 4; i++) {
            if (this.tetrominoCanBePlacedOnPosition(
                    this.getCurrentTetromino(),
                    this.getModel().getCurrentTetrominoPositionLine(),
                    this.getModel().getCurrentTetrominoPositionColumn() - i,
                    this.getModel().getGrid())) {
                break;
            }
        }
        if (i == 4) {
            this.getModel().getCurrentTetromino().rotateToLeft();
        } else {
            this.getModel().setCurrentTetrominoPositionColumn(
                    this.getModel().getCurrentTetrominoPositionColumn() - i
            );
        }
    }

    /**
     * Rotate tetromino to left if possible.
     */
    public void rotateTetrominoToLeft() {
        this.getModel().getCurrentTetromino().rotateToLeft();
        int i;
        for (i = 0; i < 4; i++) {
            if (this.tetrominoCanBePlacedOnPosition(
                    this.getCurrentTetromino(),
                    this.getModel().getCurrentTetrominoPositionLine(),
                    this.getModel().getCurrentTetrominoPositionColumn() - i,
                    this.getModel().getGrid())) {
                break;
            }
        }
        if (i == 4) {
            this.getModel().getCurrentTetromino().rotateToRight();
        } else {
            this.getModel().setCurrentTetrominoPositionColumn(
                    this.getModel().getCurrentTetrominoPositionColumn() - i
            );
        }
    }

    protected int shadowLine;

    /**
     * Add shadow tetromino.
     */
    public void addShadowInTheGridForCurrentTetromino() {
        shadowLine = this.getModel().getCurrentTetrominoPositionLine();
        while (this.tetrominoCanBePlacedOnPosition(
                this.getCurrentTetromino(),
                shadowLine,
                this.getModel().getCurrentTetrominoPositionColumn(),
                this.getModel().getGrid())) {
            shadowLine++;
        }
        if (shadowLine >= this.getModel().getCurrentTetrominoPositionLine() + 4) {
            this.addMatrixToGrid(this.getCurrentTetromino().getCurrentRotation(),
                    Model_Color.WHITE,
                    shadowLine - 1, this.getModel().getCurrentTetrominoPositionColumn(),
                    this.getModel().getGrid());
        }
    }

    /**
     * Remove tetromino shadow.
     */
    public void removeShadowFromTheGrid() {
        if (shadowLine >= this.getModel().getCurrentTetrominoPositionLine() + 4) {
            this.removeMatrixFromGrid(this.getCurrentTetromino().getCurrentRotation(),
                    shadowLine - 1, this.getModel().getCurrentTetrominoPositionColumn());
        }
    }

    /**
     * Update grid: clear lines.
     */
    public int updateCurrentGrid() {
        return this.updateGrid(this.getModel().getGrid());
    }

    public int updateGrid(short[][] grid) {
        int goodLines = 0;
        for (int i = 0; i < grid.length; i++) {
            boolean goodLine = true;
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 0) {
                    goodLine = false;
                    break;
                }
            }

            if (goodLine) {
                goodLines++;
                // Move all lines above i one level down.
                for (int ii = i; ii > 0; ii--) {
                    for (int j = 0; j < grid[ii].length; j++) {
                        grid[ii][j] = grid[ii - 1][j];
                    }
                }

                // Put 0 on last line.
                for (int j = 0; j < grid[0].length; j++) {
                    grid[0][j] = 0;
                }
            }
        }
        return goodLines;
    }

    /**
     * Return true if it's possible for the current tetromino to be moved from current position to new position.
     */
    public boolean tetrominoCanFindAWayToPosition(Model_Tetromino t, int fromLine, int fromColumn, int newLine, int newColumn, short[][] grid) {
        // It should be able to placed at final position.
        if (!this.tetrominoCanBePlacedOnPosition(t, newLine, newColumn, grid)) {
            return false;
        }
        // Does it have a path to (newLine, newColumn)?
        return this.findPath(t, fromLine, fromColumn, newLine, newColumn, grid);
    }

    /**
     * Argh. I'm just fed up making this game!
     */
    protected boolean findPath(Model_Tetromino t, int line, int column, int newLine, int newColumn, short[][] grid) {
        short initialRotation = t.getCurrentRotationNumber();
        boolean ok = this.findPathTryLeft(t, line, column, newLine, newColumn, grid);
        if (!ok) {
            t.setCurrentRotationNumber(initialRotation);
            ok =this.findPathTryRight(t, line, column, newLine, newColumn, grid);
        }
        t.setCurrentRotationNumber(initialRotation);
        return ok;
    }

    /**
     * Return true if tetromino t can be correctly placed on (toLine, toColumn) from neighbour cell (fromLine, fromColumn) in the grid.
     * Also tries to rotate it. If possible, then returns true and leaves the tetromino in the rotated position.
     */
    protected boolean tetrominoCanBePlacedOnPositionIfRotated(Model_Tetromino t, int fromLine, int fromColumn, int toLine, int toColumn, short[][] grid) {
        if (this.tetrominoCanBePlacedOnPosition(t, toLine, toColumn, grid)) {
            return true;
        }
        for (int i = 1; i <= 3; i++) {
            t.rotateToLeft();
            if (this.tetrominoCanBePlacedOnPosition(t, fromLine, fromColumn, grid)
                    && this.tetrominoCanBePlacedOnPosition(t, toLine, toColumn, grid)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Argh. I'm just fed up making this game!
     */
    protected boolean findPathTryLeft(Model_Tetromino t, int fromLine, int fromColumn, int toLine, int toColumn, short[][] grid) {
        int line, column;

        // Move to the left from fromColumn to toColumn.
        line = fromLine;
        column = fromColumn;
        while (column > toColumn) {
            if (!this.tetrominoCanBePlacedOnPositionIfRotated(t, line, column, line, column - 1, grid)) {
                return false;
            }
            column--;
        }

        boolean skipColumn;
        while (column >= 0) {
            skipColumn = false;
            // Go down from fromLine to toLine.
            line = fromLine;
            while (line < toLine) {
                if (!this.tetrominoCanBePlacedOnPositionIfRotated(t, line, column, line + 1, column, grid)) {
                    skipColumn = true;
                    break;
                }
                line++;
            }
            if (skipColumn) {
                // Try again, if moving a bit more to the left.
                column--;
                continue;
            }

            // / Go right from column to toColumn.
            int goBackColumn = column;
            while (goBackColumn < toColumn) {
                if (!this.tetrominoCanBePlacedOnPositionIfRotated(t, line, goBackColumn, line, goBackColumn + 1, grid)) {
                    skipColumn = true;
                    break;
                }
                goBackColumn++;
            }

            // Found a path!
            if (!skipColumn) {
                return true;
            } else {
                column--;
            }
        }
        return false;
    }

    /**
     * Argh. I'm just fed up making this game!
     */
    protected boolean findPathTryRight(Model_Tetromino t, int fromLine, int fromColumn, int toLine, int toColumn, short[][] grid) {
        int line, column;

        // Move to the right from fromColumn to toColumn.
        line = fromLine;
        column = fromColumn;
        while (column < toColumn) {
            if (!this.tetrominoCanBePlacedOnPositionIfRotated(t, line, column, line, column + 1, grid)) {
                return false;
            }
            column++;
        }

        boolean skipColumn;
        while (column <= Controller_Tetris.NUMBER_OF_COLUMNS_TETRIS_GRID) {
            skipColumn = false;
            // Go down from fromLine to toLine.
            line = fromLine;
            while (line < toLine) {
                if (!this.tetrominoCanBePlacedOnPositionIfRotated(t, line, column, line + 1, column, grid)) {
                    skipColumn = true;
                    break;
                }
                line++;
            }
            if (skipColumn) {
                // Try again, if moving a bit more to the right.
                column++;
                continue;
            }

            // / Go left from column to toColumn.
            int goBackColumn = column;
            while (goBackColumn > toColumn) {
                if (!this.tetrominoCanBePlacedOnPositionIfRotated(t, line, goBackColumn, line, goBackColumn - 1, grid)) {
                    skipColumn = true;
                    break;
                }
                goBackColumn--;
            }

            // Found a path!
            if (!skipColumn) {
                return true;
            } else {
                column++;
            }
        }
        return false;
    }
}

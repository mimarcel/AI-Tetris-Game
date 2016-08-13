package Genetic.Genetic_GATetris;

import Genetic.GATetris;
import Tetris.Controller.Controller_PlayerRobotGenetic;

public class GaTetris_ThreadGameManager extends Thread
{
    protected GATetris gaTetris;
    protected int currentPlayerToManage = -1;

    public GaTetris_ThreadGameManager(GATetris gaTetris) {
        this.gaTetris = gaTetris;
    }

    @Override
    public void run() {
        while (!gaTetris.shouldStop()) {
            this.findMoveForAvailablePlayer();
            try {
                Thread.sleep(gaTetris.TIME_TO_SLEEP_FOR_GAME_MANAGER_AFTER_ONE_MOVE);
            } catch (InterruptedException e) {
                gaTetris.handleEasyError(e);
            }
        }
    }

    protected void findMoveForAvailablePlayer() {
        try {
            // Get player to make a move.
            Controller_PlayerRobotGenetic cp = null;
            synchronized (this.gaTetris.getGameManageLocker()) {
                if (this.gaTetris.controllerTetris == null || !this.gaTetris.controllerTetris.isGameStarted()) {
                    return;
                }

                if (this.gaTetris.currentPlayerOnMove + 1 < this.gaTetris.controllerTetris.getNumberOfPlayers()) {
                    this.gaTetris.currentPlayerOnMove++;
                    this.currentPlayerToManage = this.gaTetris.currentPlayerOnMove;
                    cp = (Controller_PlayerRobotGenetic) this.gaTetris.controllerTetris.getPlayerControllers()[this.gaTetris.currentPlayerOnMove];
                    this.log("Move #" + this.gaTetris.numberOfMovesForCurrentGame + " " + this.getName() + " will handle " + String.format("%40s", cp.getPlayerName()) + ". Player moved " + cp.getNumberOfMoves() +" times.\n");
                }
            }
            if (cp == null) {
                return;
            }

            while (this.gaTetris.controllerTetris.isGameStarted()
                    && !cp.isGameOver()
                    && cp.getNumberOfMoves() < this.gaTetris.numberOfMovesForCurrentGame) {
                // Player's action.
                if (!cp.isGameOver() && cp.isReady()) {
                    this.gaTetris.log(this.gaTetris.logIntro() + "\n");
                    cp.findNextMove();
                    cp.makeNextMove();
                }

                // Check what happens.
                if (!cp.isGameOver()) {
                    cp.getGridController().checkCurrentTetromino();

                    if (!cp.isGameOver()) {
                        // Update grid.
                        int clearedLines = cp.getGridController().updateCurrentGrid();

                        // Update score.
                        cp.updateScore(clearedLines);
                        //cp.playerUpdatedGrid(clearedLines);
                        this.gaTetris.log(String.format("%sScore: %d\n\n", cp.toString(), cp.getScore()));
                    } else {
                        this.gaTetris.log(String.format("%sGame Over %s.\n\n", cp.toString(), cp.getPlayerName()));
                    }
                }
            }

            synchronized (this.gaTetris.getGameManageLocker()) {
                this.gaTetris.numberOfPlayersWhichDidTheirMove++;
                this.gaTetris.lastPlayerWhichMoved = cp;
                this.log("Move #" + this.gaTetris.numberOfMovesForCurrentGame + " " + this.getName() + " did  handle " + String.format("%40s", cp.getPlayerName()) + ". Player moved " + cp.getNumberOfMoves() + " times.\n");
            }
        } catch (Exception ex) {
            this.gaTetris.handleEasyError(ex);
        }
        this.currentPlayerToManage = -1;
    }

    protected void log(String message) {
        this.gaTetris.forceLog(message);
    }

    public int getCurrentPlayerToManage() {
        return currentPlayerToManage;
    }
}

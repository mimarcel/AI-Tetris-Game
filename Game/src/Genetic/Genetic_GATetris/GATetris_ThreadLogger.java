package Genetic.Genetic_GATetris;

import Genetic.GATetris;

public class GATetris_ThreadLogger extends Thread
{
    protected GATetris gaTetris;

    public GATetris_ThreadLogger(GATetris gaTetris) {
        this.gaTetris = gaTetris;
    }

    @Override
    public void run() {
        while (!gaTetris.shouldStop()) {
            this.log(
                    gaTetris.logIntro()
                            //+ " - " + this.gameManagersStats()
                            + "\n"
            );
            try {
                Thread.sleep(gaTetris.TIME_TO_LOG_PROGRESS);
            } catch (InterruptedException e) {
                gaTetris.handleEasyError(e);
            }
        }
    }

    private String gameManagersStats() {
        return this.gaTetris.numberOfPlayersWhichDidTheirMove + " did their move";
//        synchronized (this.gaTetris.getGameManageLocker()) {
//            String s = "";
//            if (this.gaTetris.getGameManagers() == null) {
//                return s;
//            }
//            for (int i = 0; i < this.gaTetris.getGameManagers().length; i++) {
//                s += " " + this.gaTetris.getGameManagers()[i].getName()
//                        + ": " + this.gaTetris.getGameManagers()[i].getCurrentPlayerToManage();
//            }
//            return s;
//        }
    }

    protected void log(String message) {
        if (gaTetris.getCurrentPlayerOnMove() == -1) {
            return;
        }
        gaTetris.forceLog(message);
    }
}

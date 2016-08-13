package Genetic;

import Tetris.Model.Model_Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public abstract class GALog extends GA implements Model_Logger
{
    public static String logFile = "D:\\Personal\\Dropbox\\mywork\\Tetris\\Game\\log-5.txt";
    protected BufferedWriter bwLogFile;

    /**
     * Handle fatal error.
     */
    public void handleFatalError(Exception ex) {
        ex.printStackTrace();
        System.exit(1);
    }

    /**
     * Handle easy error.
     */
    public void handleEasyError(Exception ex) {
        ex.printStackTrace();
    }

    /**
     * Log message in log file.
     */
    public synchronized boolean log(String message) {
        try {
            bwLogFile.write(message);
            bwLogFile.flush();
            return true;
        } catch (IOException ex) {
            this.handleEasyError(ex);
            return false;
        }
    }

    /**
     * Open log file.
     */
    public void openLogFile() {
        try {
            FileWriter fw = new FileWriter(new File(logFile));
            bwLogFile = new BufferedWriter(fw);
        } catch (Exception ex) {
            this.handleEasyError(ex);
        }
    }

    /**
     * Close log file.
     */
    public void closeLogFile() {
        try {
            bwLogFile.close();
        } catch (IOException ex) {
            this.handleEasyError(ex);
        }
    }

    /**
     * Override to init this.population and do any other action needed before the algorithm starts.
     */
    public void init() {
        this.openLogFile();
        this.log("START Training\n\n");
    }

    /**
     * Things to do after the result is found.
     */
    public void done() {
        this.log("\n\nDONE Training\n");
        this.closeLogFile();
    }
}

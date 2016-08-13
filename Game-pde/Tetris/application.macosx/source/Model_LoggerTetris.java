
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Model_LoggerTetris implements Model_Logger
{
    public static String logFile = "D:\\Personal\\Dropbox\\mywork\\Tetris\\Game\\log-in-game.txt";
    protected BufferedWriter bwLogFile;

    /**
     * Handle fatal error.
     */
    protected void handleFatalError(Exception ex) {
        ex.printStackTrace();
        System.exit(1);
    }

    /**
     * Handle easy error.
     */
    protected void handleEasyError(Exception ex) {
        ex.printStackTrace();
    }

    /**
     * Log message in log file.
     */
    public boolean log(String message) {
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
}

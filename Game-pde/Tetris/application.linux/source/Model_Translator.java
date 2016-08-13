
import java.util.HashMap;

public class Model_Translator extends Model_Abstract
{
    protected HashMap<String, HashMap> translations;

    /**
     * Get translations.
     */
    public HashMap<String, String> getTranslations(String code) {
        if (this.translations == null) {
            this.translations = new HashMap<String, HashMap>();
            HashMap<String, String> roHashMap = new HashMap<String, String>();
            HashMap<String, String> enHashMap = new HashMap<String, String>();

            enHashMap.put("Tetris", "Tetris Game");
            enHashMap.put("Language was selected.", "English language was selected.");
            enHashMap.put("Next tetromino:", "Next:");
            enHashMap.put("Legend", "Keys to use");
            enHashMap.put("UP-arrow", "UP");
            enHashMap.put("DOWN-arrow", "DOWN");
            enHashMap.put("LEFT-arrow", "LEFT");
            enHashMap.put("RIGHT-arrow", "RIGHT");

            roHashMap.put("Tetris", "Tetris");
            roHashMap.put("Go back", "Înapoi");
            roHashMap.put("No current language set.", "Eroare la selectarea limbii.");
            roHashMap.put("An unexpected error has occurred.", "Eroare.");
            roHashMap.put("The game will continue, but its behaviour may not be fully stable.", "Aplicatia va continua, dar s-ar putea sa fie instabila.");
            roHashMap.put("Title image not found.", "Logo-ul Tetris nu a fost gasit.");
            roHashMap.put("Language image not found.", "Eroare la incarcarea imaginilor in aplicatie.");
            roHashMap.put("Language was selected.", "Limba română a fost selectată.");
            roHashMap.put("Score", "Scor");
            roHashMap.put("Legend", "Legenda");
            roHashMap.put("Next tetromino:", "Urmează:");
            roHashMap.put("UP-arrow", "UP");
            roHashMap.put("DOWN-arrow", "DOWN");
            roHashMap.put("LEFT-arrow", "LEFT");
            roHashMap.put("RIGHT-arrow", "RIGHT");
            roHashMap.put("SPACE", "SPACE");
            roHashMap.put("move to right", "mută la dreapta");
            roHashMap.put("move to left", "mută la stânga");
            roHashMap.put("rotate to left", "rotește la stânga");
            roHashMap.put("fall down one level", "în jos un nivel");
            roHashMap.put("fall", "în jos");
            roHashMap.put("Game over", "Sfârșit joc");
            roHashMap.put("You won", "Ai câștigat");
            roHashMap.put("You lost", "Ai pierdut");
            roHashMap.put("Human player", "Jucător");
            roHashMap.put("Robot Random", "Robot Random");
            roHashMap.put("Press any key to start", "Apasă orice tastă");
            roHashMap.put("Searching move...", "Caută mutare...");
            roHashMap.put("Robot Genetic cannot play on a grid larger than 30x30.", "Un robot genetic nu poate sa joace un joc de Tetris > 30x30.");
            roHashMap.put("Grid cannot be smaller than 10x5 or higher than ", "Nu se poate juca AI Tetris < 10 x 5 sau > ");

            this.translations.put("ro", roHashMap);
            this.translations.put("en", enHashMap);
        }
        return this.translations.get(code);
    }
}

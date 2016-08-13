
import processing.core.PApplet;
import processing.core.PImage;

public class View_Language extends View_Abstract
{
    public boolean IS_ACTIVE = true;

    protected static PImage[] languageImages;
    protected static String[] languageCodes;
    public float LANGUAGE_START_X = -1;
    public float LANGUAGE_WIDTH = 48;
    public int LANGUAGE_DIRECTION = -1;
    public float LANGUAGE_PADDING = 5;
    public float LANGUAGE_START_Y;
    public float LANGUAGE_HEIGHT = 48;

    protected int mouseOverLanguage = -1;
    protected float[] languageImagesX1;
    protected float[] languageImagesX2;
    protected float[] languageImagesY1;
    protected float[] languageImagesY2;

    /**
     * Create language view.
     */
    public View_Language(Controller_Language controller, View_Tetris view_tetris) {
        super(controller, view_tetris);
    }

    public Controller_Language getController() {
        return (Controller_Language) super.getController();
    }

    public View_Tetris getParentView() {
        return (View_Tetris) super.getParentView();
    }

    public void setup() throws Model_Exception {
        if (!IS_ACTIVE) {
            return;
        }

        LANGUAGE_START_X = this.getParentView().WIDTH - LANGUAGE_WIDTH - this.getParentView().getBackgroundPadding() - 2;
        LANGUAGE_START_Y = this.getParentView().getBackgroundPadding() + 3;

        // Load icon images and save language codes.
        Model_Language[] languages = this.getController().getAvailableLanguages();
        if (languageCodes == null) {
            languageImages = new PImage[languages.length];
            languageCodes = new String[languages.length];
            for (int i = 0; i < languages.length; i++) {
                languageImages[languages.length - i - 1]
                        = this.getParentView().getApp().loadImage(this.getController().getIconLink(languages[i]));
                if (languageImages[languages.length - i - 1] == null
                        || languageImages[languages.length - i - 1].width == 0
                        || languageImages[languages.length - i - 1].height == 0) {
                    this.getParentView().addError(
                            new Exception(
                                    Controller_Tetris.translate("Language image not found.")
                            )
                    );
                }
                languageCodes[languages.length - i - 1] = languages[i].getCode();
            }
        }

        // Init arrays.
        languageImagesX1 = new float[languages.length];
        languageImagesX2 = new float[languages.length];
        languageImagesY1 = new float[languages.length];
        languageImagesY2 = new float[languages.length];
        this.computeImagesLimits();
    }

    public void draw() throws Model_Exception {
        if (!IS_ACTIVE) {
            return;
        }

        // Draw icon images.
        this.getParentView().getApp().imageMode(PApplet.CORNER);
        for (int i = 0; i < languageImages.length; i++) {
            if (languageImages[i] != null) {
                float transparency = i != this.mouseOverLanguage ? 128 : 255;
                this.getParentView().getApp().tint(255, transparency);
                this.getParentView().getApp().image(languageImages[i],
                        languageImagesX1[i],
                        languageImagesY1[i]);
            }
        }
        this.getParentView().getApp().tint(255, 255);

        /*
        if (this.getController().getParentController().isGameOver() == true && this.getParentView().resultsPopup == 1){
            var players = [];for (Controller_PlayerAbstract cp : this.getController().getParentController().getPlayerControllers()) {players.push({n: cp.getPlayerName(), s: cp.getScore(), m : cp.getNumberOfMoves(), ti: new Date().getTime(), ty: cp.getModel().getType(), l: Controller_Tetris.NUMBER_OF_LINES_TETRIS_GRID, c: Controller_Tetris.NUMBER_OF_COLUMNS_TETRIS_GRID, d: View_Player_Grid.DRAW_YOUR_OWN_BLOCKS ? 1 :0});}this.getParentView().resultsPopup++;players.sort(function(a,b){if (a.score < b.score) { return -1; } return a.score == b.score ? 0 : 1;});
            gameOver(players);
        }
        */
    }

    public void mousePressed() throws Model_Exception {
        if (!IS_ACTIVE) {
            return;
        }

        int mx = this.getParentView().getApp().mouseX;
        int my = this.getParentView().getApp().mouseY;

        // On click on an language icon image, the language will be set.
        for (int i = 0; i < languageImages.length; i++) {
            if (mx >= languageImagesX1[i] && mx <= languageImagesX2[i]
                    && my >= languageImagesY1[i] && my <= languageImagesY2[i]) {
                //this.getController().getParentController().getAppUrl(true);
                /*
                String oldCurrentLanguage = this.getController().getCurrentLanguage().getCode();
                String newCurrentLanguage = languageCodes[i];
                $('#language-' + oldCurrentLanguage).removeClass('active');
                $('#language-' + newCurrentLanguage).addClass('active');
                */
                this.getController().setCurrentLanguage(languageCodes[i]);
                /*
                String translatedTitle = Controller_Tetris.translate("Go back");
                String appUrl = this.getController().getParentController().getAppUrl(true);
                String translatedTopPlayers = Controller_Tetris.translate("Show Top Players");
                $('#game .home a').attr('href', appUrl + "play/players");
                $('#game .home a img').attr('title', translatedTitle);
                $('.top-players-link').html(translatedTopPlayers);
                */
            }
        }
    }

    public void mouseMoved() throws Model_Exception {
        if (!IS_ACTIVE) {
            return;
        }

        int mx = this.getParentView().getApp().mouseX;
        int my = this.getParentView().getApp().mouseY;

        int oldMouseOverLanguage = this.mouseOverLanguage;
        this.mouseOverLanguage = -1;

        // Check if mouse is over one of the icon images.
        for (int i = 0; i < languageImages.length; i++) {
            if (mx >= languageImagesX1[i] && mx <= languageImagesX2[i]
                    && my >= languageImagesY1[i] && my <= languageImagesY2[i]) {
                this.mouseOverLanguage = i;
                this.getParentView().askCursorToBe(PApplet.HAND);
                break;
            }
        }
        if (oldMouseOverLanguage != this.mouseOverLanguage) {
            this.getParentView().getApp().redraw();
        }

    }

    /**
     * Compute x1, x2, y1 and y2 points for each icon image.
     */
    protected void computeImagesLimits() {
        for (int i = 0; i < languageImages.length; i++) {
            languageImagesX1[i] = LANGUAGE_START_X + i * LANGUAGE_DIRECTION * LANGUAGE_WIDTH + i * LANGUAGE_DIRECTION * LANGUAGE_PADDING;
            languageImagesX2[i] = languageImagesX1[i] + LANGUAGE_WIDTH;
            languageImagesY1[i] = LANGUAGE_START_Y;
            languageImagesY2[i] = LANGUAGE_START_Y + LANGUAGE_HEIGHT;
        }
    }
}

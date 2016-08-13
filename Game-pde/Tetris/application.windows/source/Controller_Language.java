

public class Controller_Language extends Controller_Abstract
{
    protected static String currentLanguage;

    /**
     * Create controller.
     */
    public Controller_Language(Controller_Tetris controller_tetris) {
        super(controller_tetris, null, null);
        this.model = new Model_Language_Collection();
        this.view = new View_Language(this, controller_tetris.getView());
    }

    public Controller_Tetris getParentController() {
        return (Controller_Tetris) super.getParentController();
    }

    public View_Language getView() {
        return (View_Language) super.getView();
    }

    public Model_Language_Collection getModel() {
        return (Model_Language_Collection) super.getModel();
    }

    /**
     * Return available languages.
     */
    public Model_Language[] getAvailableLanguages() {
        return this.getModel().getAllItems();
    }

    /**
     * Get language.
     */
    public Model_Language getLanguage(String code) {
        return this.getModel().getItem(code);
    }

    /**
     * Set current language.
     */
    public void setCurrentLanguage(String currentL) {
        currentLanguage = currentL;
    }

    /**
     * Get current language.
     */
    public Model_Language getCurrentLanguage() throws Model_Exception {
        Model_Language language = this.getLanguage(currentLanguage);
        if (language == null) {
            return this.getDefaultLanguage();
        }
        return language;
    }

    /**
     * Get default language.
     */
    public Model_Language getDefaultLanguage() throws Model_Exception {
        Model_Language language = this.getLanguage("en");
        if (language == null) {
            throw new Model_Exception(Controller_Tetris.translate("No current language set."));
        }
        return language;
    }

    /**
     * Get icon url for given language model.
     */
    public String getIconLink(Model_Language language) {
        return this.getParentController().getMediaBaseUrl() + language.getIconLink();
    }
}

package Tetris.Controller;

import Tetris.Model.Model_Translator;

public class Controller_Translator extends Controller_Abstract
{
    /**
     * Create controller.
     */
    public Controller_Translator(Controller_Tetris controller_tetris) {
        super(controller_tetris, null, null);
        this.model = new Model_Translator();
    }

    @Override
    public Model_Translator getModel() {
        return (Model_Translator) super.getModel();
    }

    /**
     * Translate a string.
     */
    public String translate(String s, String languageCode) {
        String translation = this.getModel().getTranslations(languageCode).get(s);
        return translation == null ? s : translation;
    }
}

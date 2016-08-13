

public class Model_Language_Collection extends Model_Collection_Abstract
{
    protected Model_Language[] _languages;

    /**
     * Return available languages.
     */
    public Model_Language[] getAllItems() {
        if (this._languages == null) {
            Model_Language en = new Model_Language("en", "English");
            en.setIsDefault(true).setIconLink("/Media/Language/en/icon.png");

            Model_Language ro = new Model_Language("ro", "Română");
            ro.setIsDefault(false).setIconLink("/Media/Language/ro/icon.png");
            this._languages = new Model_Language[]{en, ro};
        }
        return this._languages;
    }

    /**
     * Get language.
     */
    public Model_Language getItem(String code) {
        if (code == null) {
            return null;
        }
        Model_Language[] languages = this.getAllItems();
        for (Model_Language l : languages) {
            if (l.getCode().equals(code)) {
                return l;
            }
        }
        return null;
    }
}

package Tetris.Model;

public class Model_Language extends Model_Abstract
{
    protected String code;
    protected String name;
    protected boolean isDefault_;
    protected String iconLink;

    public Model_Language(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public Model_Language setCode(String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public Model_Language setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isDefault() {
        return isDefault_;
    }

    public Model_Language setIsDefault(boolean isDefault) {
        this.isDefault_ = isDefault;
        return this;
    }

    public String getIconLink() {
        return iconLink;
    }

    public Model_Language setIconLink(String iconLink) {
        this.iconLink = iconLink;
        return this;
    }
}

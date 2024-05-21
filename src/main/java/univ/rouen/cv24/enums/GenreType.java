package univ.rouen.cv24.enums;
import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;

@XmlEnum(String.class)
public enum GenreType {
    @XmlEnumValue("M.") M("M."),
    @XmlEnumValue("Mme") MME("Mme");

    private final String value;

    GenreType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
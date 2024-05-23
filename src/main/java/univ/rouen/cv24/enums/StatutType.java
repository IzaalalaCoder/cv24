package univ.rouen.cv24.enums;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;

@XmlEnum()
public enum StatutType {

    @XmlEnumValue("stage")
    STAGE("stage"),

    @XmlEnumValue("emploi")
    EMPLOI("emploi");

    private final String value;

    StatutType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
package univ.rouen.cv24.enums;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;

@XmlEnum(String.class)
public enum CertType {
    @XmlEnumValue("MAT") MAT("MAT"),
    @XmlEnumValue("CLES") CLES("CLES"),
    @XmlEnumValue("TOEIC") TOEIC("TOEIC");

    private final String value;

    CertType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
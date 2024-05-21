package univ.rouen.cv24.enums;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;

@XmlEnum(String.class)
public enum NivsType {
    @XmlEnumValue("A1") A1("A1"),
    @XmlEnumValue("A2") A2("A2"),
    @XmlEnumValue("B1") B1("B1"),
    @XmlEnumValue("B2") B2("B2"),
    @XmlEnumValue("C1") C1("C1"),
    @XmlEnumValue("C2") C2("C2");

    private final String value;

    NivsType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
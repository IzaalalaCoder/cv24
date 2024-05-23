package univ.rouen.cv24.entity;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;
import univ.rouen.cv24.enums.CertType;
import univ.rouen.cv24.enums.NivsType;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Lv {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    private Integer id;

    @XmlAttribute
    private CertType cert;

    @XmlAttribute
    private String lang;

    @XmlAttribute
    private int nivi;

    @XmlAttribute
    private NivsType nivs;

    public Lv() {}

    public CertType getCert() {
        return cert;
    }

    public Integer getNivi() {
        return nivi;
    }

    public Integer getId() {
        return id;
    }

    public NivsType getNivs() {
        return nivs;
    }

    public String getLang() {
        return lang;
    }
}
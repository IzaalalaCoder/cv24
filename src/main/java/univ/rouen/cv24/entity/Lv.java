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

    @XmlAttribute(namespace = "http://univ.fr/cv24")
    private String lang;

    @XmlAttribute(namespace = "http://univ.fr/cv24")
    private int nivi;

    @XmlAttribute
    private NivsType nivs;

    // Getters and Setters
}
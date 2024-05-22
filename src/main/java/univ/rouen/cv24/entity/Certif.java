package univ.rouen.cv24.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import univ.rouen.cv24.util.LocalDateTimeAdapter;

import java.time.LocalDateTime;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Certif {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    private Integer id;

    @XmlElement(namespace = "http://univ.fr/cv24")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime datedeb;

    @XmlElement(namespace = "http://univ.fr/cv24")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime datefin;

    @XmlElement(namespace = "http://univ.fr/cv24")
    private String titreCert;

    // Getters and Setters

    public Certif() {}

    public LocalDateTime getDatedeb() {
        return datedeb;
    }

    public String getTitreCert() {
        return titreCert;
    }

    public LocalDateTime getDatefin() {
        return datefin;
    }
}
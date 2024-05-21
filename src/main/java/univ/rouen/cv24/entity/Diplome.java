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
public class Diplome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    private Integer id;

    @XmlElement(namespace = "http://univ.fr/cv24")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime date;

    @XmlElement(namespace = "http://univ.fr/cv24")
    private String institut;

    @XmlElement(namespace = "http://univ.fr/cv24")
    private String titre;

    @XmlAttribute
    private Integer niveau;

    public Diplome() {

    }

    // Getters and Setters


    public Integer getNiveau() {
        return niveau;
    }

    public String getTitre() {
        return titre;
    }
}
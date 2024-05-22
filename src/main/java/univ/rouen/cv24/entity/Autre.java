package univ.rouen.cv24.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.xml.bind.annotation.*;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Autre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    private Integer id;

    @XmlAttribute
    private String comment;

    @XmlAttribute
    private String titre;

    // Getters and Setters

    public Autre() {}

    public String getTitre() {
        return titre;
    }

    public String getComment() {
        return comment;
    }
}
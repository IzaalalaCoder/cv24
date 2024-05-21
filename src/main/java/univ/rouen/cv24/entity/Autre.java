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

    @XmlAttribute(namespace = "http://univ.fr/cv24")
    private String comment;

    @XmlAttribute(namespace = "http://univ.fr/cv24")
    private String titre;

    // Getters and Setters
}
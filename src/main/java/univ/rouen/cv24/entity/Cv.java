package univ.rouen.cv24.entity;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;

import java.util.List;


@Entity
@XmlRootElement(name = "cv24", namespace = "http://univ.fr/cv24")
@XmlAccessorType(XmlAccessType.FIELD)
public class Cv {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @XmlElement(namespace = "http://univ.fr/cv24")
    private Identite identite;

    @OneToOne(cascade = CascadeType.ALL)
    @XmlElement(namespace = "http://univ.fr/cv24")
    private Objectif objectif;

    @OneToMany(cascade = CascadeType.ALL)
    @XmlElement(namespace = "http://univ.fr/cv24")
    private List<Prof> prof;

    @OneToOne(cascade = CascadeType.ALL)
    @XmlElement(namespace = "http://univ.fr/cv24")
    private Competences competences;

    @OneToOne(cascade = CascadeType.ALL)
    @XmlElement(namespace = "http://univ.fr/cv24")
    private Divers divers;

    // Getters and Setters


    public Identite getIdentite() {
        return identite;
    }

    public Objectif getObjectif() {
        return objectif;
    }

    public Competences getCompetences() {
        return competences;
    }

    public Integer getId() {
        return id;
    }
}

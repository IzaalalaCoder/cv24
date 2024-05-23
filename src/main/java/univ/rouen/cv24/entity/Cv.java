package univ.rouen.cv24.entity;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;

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

    @OneToOne(cascade = CascadeType.ALL)
    @XmlElement(namespace = "http://univ.fr/cv24")
    private Prof prof;

    @OneToOne(cascade = CascadeType.ALL)
    @XmlElement(namespace = "http://univ.fr/cv24")
    private Competences competences;

    @OneToOne(cascade = CascadeType.ALL)
    @XmlElement(namespace = "http://univ.fr/cv24")
    private Divers divers;

    public Cv() {}

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

    public Divers getDivers() {
        return divers;
    }

    public Prof getProf() {
        return prof;
    }
}
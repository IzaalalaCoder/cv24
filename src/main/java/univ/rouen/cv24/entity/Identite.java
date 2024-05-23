package univ.rouen.cv24.entity;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;
import univ.rouen.cv24.enums.GenreType;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Identite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    private Integer id;

    @XmlElement(namespace = "http://univ.fr/cv24")
    private GenreType genre;

    @XmlElement(namespace = "http://univ.fr/cv24")
    private String nom;

    @XmlElement(namespace = "http://univ.fr/cv24")
    private String prenom;

    @XmlElement(namespace = "http://univ.fr/cv24")
    private String tel;

    @XmlElement(namespace = "http://univ.fr/cv24")
    private String mel;

    public Identite() {}

    public Integer getId() {
        return id;
    }

    public GenreType getGenre() {
        return genre;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTel() {
        return tel;
    }

    public String getMel() {
        return mel;
    }
}
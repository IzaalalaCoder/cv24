package univ.rouen.cv24.entity;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;
import java.util.List;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Competences {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    private Integer id;

    @OneToMany(cascade = CascadeType.ALL)
    @XmlElement(namespace = "http://univ.fr/cv24")
    private List<Diplome> diplome;

    @OneToMany(cascade = CascadeType.ALL)
    @XmlElement(namespace = "http://univ.fr/cv24")
    private List<Certif> certif;

    public Competences() {}

    public List<Diplome> getDiplome() {
        return diplome;
    }

    public List<Certif> getCertif() {
        return certif;
    }
}
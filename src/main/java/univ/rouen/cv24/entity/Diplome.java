package univ.rouen.cv24.entity;

import jakarta.persistence.*;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import univ.rouen.cv24.util.DateAdapter;
import java.util.Date;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Diplome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    @XmlElement(namespace = "http://univ.fr/cv24")
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date date;

    @XmlElement(namespace = "http://univ.fr/cv24")
    private String institut;

    @XmlElement(namespace = "http://univ.fr/cv24")
    private String titre;

    @XmlAttribute
    private Integer niveau;

    public Diplome() {

    }

    public Integer getNiveau() {
        return niveau;
    }

    public String getTitre() {
        return titre;
    }

    public Date getDate() {
        return date;
    }

    public String getInstitut() {
        return institut;
    }
}
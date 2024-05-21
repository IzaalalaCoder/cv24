package univ.rouen.cv24.entity;

import jakarta.persistence.*;

import jakarta.xml.bind.annotation.*;
import java.util.List;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Divers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    private Integer id;

    @OneToMany(cascade = CascadeType.ALL)
    @XmlElement(namespace = "http://univ.fr/cv24")
    private List<Lv> lv;

    @OneToMany(cascade = CascadeType.ALL)
    @XmlElement(namespace = "http://univ.fr/cv24")
    private List<Autre> autre;

    // Getters and Setters
}
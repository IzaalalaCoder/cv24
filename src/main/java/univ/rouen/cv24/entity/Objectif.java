package univ.rouen.cv24.entity;

import jakarta.persistence.*;
import univ.rouen.cv24.enums.StatutType;
import jakarta.xml.bind.annotation.*;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Objectif {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    private Integer id;

    @XmlValue
    private String description;

    @XmlAttribute
    private StatutType statut;

    public Objectif() {}

    public String getDescription() {
        return description;
    }

    public StatutType getStatut() {
        return statut;
    }

    public Integer getId() {
        return id;
    }
}
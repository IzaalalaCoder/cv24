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
public class Detail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    private Integer id;

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime datedeb;

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime datefin;

    @XmlElement(namespace = "http://univ.fr/cv24")
    private String titreDetail;

    // Getters and Setters
}
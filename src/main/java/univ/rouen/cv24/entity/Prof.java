package univ.rouen.cv24.entity;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;
import java.util.List;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Prof {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    private Integer id;

    @OneToMany(cascade = CascadeType.ALL)
    @XmlElement(namespace = "http://univ.fr/cv24")
    private List<Detail> detail;

    public Prof() {}

    public List<Detail> getDetail() {
        return detail;
    }

    public Integer getId() {
        return id;
    }
}
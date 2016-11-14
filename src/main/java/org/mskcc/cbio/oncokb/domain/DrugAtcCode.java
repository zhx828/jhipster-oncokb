package org.mskcc.cbio.oncokb.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DrugAtcCode.
 */
@Entity
@Table(name = "drug_atc_code")
public class DrugAtcCode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private Drug drug;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public DrugAtcCode name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drug getDrug() {
        return drug;
    }

    public DrugAtcCode drug(Drug drug) {
        this.drug = drug;
        return this;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DrugAtcCode drugAtcCode = (DrugAtcCode) o;
        if(drugAtcCode.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, drugAtcCode.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DrugAtcCode{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}

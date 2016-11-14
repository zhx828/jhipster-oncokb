package org.mskcc.cbio.oncokb.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DrugSynonym.
 */
@Entity
@Table(name = "drug_synonym")
public class DrugSynonym implements Serializable {

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

    public DrugSynonym name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drug getDrug() {
        return drug;
    }

    public DrugSynonym drug(Drug drug) {
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
        DrugSynonym drugSynonym = (DrugSynonym) o;
        if(drugSynonym.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, drugSynonym.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DrugSynonym{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}

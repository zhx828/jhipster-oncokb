package org.mskcc.cbio.oncokb.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A GeneAlias.
 */
@Entity
@Table(name = "gene_alias")
public class GeneAlias implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private Gene gene;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public GeneAlias name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gene getGene() {
        return gene;
    }

    public GeneAlias gene(Gene gene) {
        this.gene = gene;
        return this;
    }

    public void setGene(Gene gene) {
        this.gene = gene;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GeneAlias geneAlias = (GeneAlias) o;
        if(geneAlias.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, geneAlias.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GeneAlias{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}

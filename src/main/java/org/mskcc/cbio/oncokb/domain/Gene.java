package org.mskcc.cbio.oncokb.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Gene.
 */
@Entity
@Table(name = "gene")
public class Gene implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "entrez_gene_id")
    private Integer entrezGeneId;

    @Column(name = "hugo_symbol")
    private String hugoSymbol;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "gene")
    @JsonIgnore
    private Set<GeneAlias> geneAliases = new HashSet<>();

    @OneToMany(mappedBy = "gene")
    @JsonIgnore
    private Set<GeneLabel> geneLabels = new HashSet<>();

    @OneToMany(mappedBy = "gene")
    @JsonIgnore
    private Set<Status> statuses = new HashSet<>();

    @OneToMany(mappedBy = "gene")
    @JsonIgnore
    private Set<Comment> comments = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEntrezGeneId() {
        return entrezGeneId;
    }

    public Gene entrezGeneId(Integer entrezGeneId) {
        this.entrezGeneId = entrezGeneId;
        return this;
    }

    public void setEntrezGeneId(Integer entrezGeneId) {
        this.entrezGeneId = entrezGeneId;
    }

    public String getHugoSymbol() {
        return hugoSymbol;
    }

    public Gene hugoSymbol(String hugoSymbol) {
        this.hugoSymbol = hugoSymbol;
        return this;
    }

    public void setHugoSymbol(String hugoSymbol) {
        this.hugoSymbol = hugoSymbol;
    }

    public String getName() {
        return name;
    }

    public Gene name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<GeneAlias> getGeneAliases() {
        return geneAliases;
    }

    public Gene geneAliases(Set<GeneAlias> geneAliases) {
        this.geneAliases = geneAliases;
        return this;
    }

    public Gene addGeneAliases(GeneAlias geneAlias) {
        geneAliases.add(geneAlias);
        geneAlias.setGene(this);
        return this;
    }

    public Gene removeGeneAliases(GeneAlias geneAlias) {
        geneAliases.remove(geneAlias);
        geneAlias.setGene(null);
        return this;
    }

    public void setGeneAliases(Set<GeneAlias> geneAliases) {
        this.geneAliases = geneAliases;
    }

    public Set<GeneLabel> getGeneLabels() {
        return geneLabels;
    }

    public Gene geneLabels(Set<GeneLabel> geneLabels) {
        this.geneLabels = geneLabels;
        return this;
    }

    public Gene addGeneLabels(GeneLabel geneLabel) {
        geneLabels.add(geneLabel);
        geneLabel.setGene(this);
        return this;
    }

    public Gene removeGeneLabels(GeneLabel geneLabel) {
        geneLabels.remove(geneLabel);
        geneLabel.setGene(null);
        return this;
    }

    public void setGeneLabels(Set<GeneLabel> geneLabels) {
        this.geneLabels = geneLabels;
    }

    public Set<Status> getStatuses() {
        return statuses;
    }

    public Gene statuses(Set<Status> statuses) {
        this.statuses = statuses;
        return this;
    }

    public Gene addStatuses(Status status) {
        statuses.add(status);
        status.setGene(this);
        return this;
    }

    public Gene removeStatuses(Status status) {
        statuses.remove(status);
        status.setGene(null);
        return this;
    }

    public void setStatuses(Set<Status> statuses) {
        this.statuses = statuses;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Gene comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public Gene addComments(Comment comment) {
        comments.add(comment);
        comment.setGene(this);
        return this;
    }

    public Gene removeComments(Comment comment) {
        comments.remove(comment);
        comment.setGene(null);
        return this;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Gene gene = (Gene) o;
        if(gene.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, gene.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Gene{" +
            "id=" + id +
            ", entrezGeneId='" + entrezGeneId + "'" +
            ", hugoSymbol='" + hugoSymbol + "'" +
            ", name='" + name + "'" +
            '}';
    }
}

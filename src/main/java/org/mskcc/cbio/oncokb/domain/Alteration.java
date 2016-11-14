package org.mskcc.cbio.oncokb.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import org.mskcc.cbio.oncokb.domain.enumeration.AlterationType;

/**
 * A Alteration.
 */
@Entity
@Table(name = "alteration")
public class Alteration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "alteration")
    private String alteration;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "alteration_type")
    private AlterationType alterationType;

    @Column(name = "ref_residues")
    private String refResidues;

    @Column(name = "protein_start")
    private Integer proteinStart;

    @Column(name = "potein_end")
    private Integer poteinEnd;

    @Column(name = "variant_residues")
    private String variantResidues;

    @OneToMany(mappedBy = "alteration")
    @JsonIgnore
    private Set<Status> statuses = new HashSet<>();

    @OneToMany(mappedBy = "alteration")
    @JsonIgnore
    private Set<Comment> comments = new HashSet<>();

    @ManyToOne
    private Gene gene;

    @ManyToOne
    private VariantConsequence consequence;

    @ManyToMany
    @JoinTable(name = "alteration_portal_alterations",
               joinColumns = @JoinColumn(name="alterations_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="portal_alterations_id", referencedColumnName="ID"))
    private Set<PortalAlteration> portalAlterations = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlteration() {
        return alteration;
    }

    public Alteration alteration(String alteration) {
        this.alteration = alteration;
        return this;
    }

    public void setAlteration(String alteration) {
        this.alteration = alteration;
    }

    public String getName() {
        return name;
    }

    public Alteration name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AlterationType getAlterationType() {
        return alterationType;
    }

    public Alteration alterationType(AlterationType alterationType) {
        this.alterationType = alterationType;
        return this;
    }

    public void setAlterationType(AlterationType alterationType) {
        this.alterationType = alterationType;
    }

    public String getRefResidues() {
        return refResidues;
    }

    public Alteration refResidues(String refResidues) {
        this.refResidues = refResidues;
        return this;
    }

    public void setRefResidues(String refResidues) {
        this.refResidues = refResidues;
    }

    public Integer getProteinStart() {
        return proteinStart;
    }

    public Alteration proteinStart(Integer proteinStart) {
        this.proteinStart = proteinStart;
        return this;
    }

    public void setProteinStart(Integer proteinStart) {
        this.proteinStart = proteinStart;
    }

    public Integer getPoteinEnd() {
        return poteinEnd;
    }

    public Alteration poteinEnd(Integer poteinEnd) {
        this.poteinEnd = poteinEnd;
        return this;
    }

    public void setPoteinEnd(Integer poteinEnd) {
        this.poteinEnd = poteinEnd;
    }

    public String getVariantResidues() {
        return variantResidues;
    }

    public Alteration variantResidues(String variantResidues) {
        this.variantResidues = variantResidues;
        return this;
    }

    public void setVariantResidues(String variantResidues) {
        this.variantResidues = variantResidues;
    }

    public Set<Status> getStatuses() {
        return statuses;
    }

    public Alteration statuses(Set<Status> statuses) {
        this.statuses = statuses;
        return this;
    }

    public Alteration addStatuses(Status status) {
        statuses.add(status);
        status.setAlteration(this);
        return this;
    }

    public Alteration removeStatuses(Status status) {
        statuses.remove(status);
        status.setAlteration(null);
        return this;
    }

    public void setStatuses(Set<Status> statuses) {
        this.statuses = statuses;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Alteration comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public Alteration addComments(Comment comment) {
        comments.add(comment);
        comment.setAlteration(this);
        return this;
    }

    public Alteration removeComments(Comment comment) {
        comments.remove(comment);
        comment.setAlteration(null);
        return this;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Gene getGene() {
        return gene;
    }

    public Alteration gene(Gene gene) {
        this.gene = gene;
        return this;
    }

    public void setGene(Gene gene) {
        this.gene = gene;
    }

    public VariantConsequence getConsequence() {
        return consequence;
    }

    public Alteration consequence(VariantConsequence variantConsequence) {
        this.consequence = variantConsequence;
        return this;
    }

    public void setConsequence(VariantConsequence variantConsequence) {
        this.consequence = variantConsequence;
    }

    public Set<PortalAlteration> getPortalAlterations() {
        return portalAlterations;
    }

    public Alteration portalAlterations(Set<PortalAlteration> portalAlterations) {
        this.portalAlterations = portalAlterations;
        return this;
    }

    public Alteration addPortalAlterations(PortalAlteration portalAlteration) {
        portalAlterations.add(portalAlteration);
        portalAlteration.getAlterations().add(this);
        return this;
    }

    public Alteration removePortalAlterations(PortalAlteration portalAlteration) {
        portalAlterations.remove(portalAlteration);
        portalAlteration.getAlterations().remove(this);
        return this;
    }

    public void setPortalAlterations(Set<PortalAlteration> portalAlterations) {
        this.portalAlterations = portalAlterations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Alteration alteration = (Alteration) o;
        if(alteration.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, alteration.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Alteration{" +
            "id=" + id +
            ", alteration='" + alteration + "'" +
            ", name='" + name + "'" +
            ", alterationType='" + alterationType + "'" +
            ", refResidues='" + refResidues + "'" +
            ", proteinStart='" + proteinStart + "'" +
            ", poteinEnd='" + poteinEnd + "'" +
            ", variantResidues='" + variantResidues + "'" +
            '}';
    }
}

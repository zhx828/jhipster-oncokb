package org.mskcc.cbio.oncokb.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A PortalAlteration.
 */
@Entity
@Table(name = "portal_alteration")
public class PortalAlteration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "cancer_type")
    private String cancerType;

    @Column(name = "cancer_study")
    private String cancerStudy;

    @Column(name = "sample_id")
    private String sampleId;

    @Column(name = "protein_change")
    private String proteinChange;

    @Column(name = "protein_start_position")
    private Integer proteinStartPosition;

    @Column(name = "protein_end_position")
    private Integer proteinEndPosition;

    @Column(name = "alteration_type")
    private String alterationType;

    @ManyToOne
    private Gene gene;

    @ManyToMany(mappedBy = "portalAlterations")
    @JsonIgnore
    private Set<Alteration> alterations = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCancerType() {
        return cancerType;
    }

    public PortalAlteration cancerType(String cancerType) {
        this.cancerType = cancerType;
        return this;
    }

    public void setCancerType(String cancerType) {
        this.cancerType = cancerType;
    }

    public String getCancerStudy() {
        return cancerStudy;
    }

    public PortalAlteration cancerStudy(String cancerStudy) {
        this.cancerStudy = cancerStudy;
        return this;
    }

    public void setCancerStudy(String cancerStudy) {
        this.cancerStudy = cancerStudy;
    }

    public String getSampleId() {
        return sampleId;
    }

    public PortalAlteration sampleId(String sampleId) {
        this.sampleId = sampleId;
        return this;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public String getProteinChange() {
        return proteinChange;
    }

    public PortalAlteration proteinChange(String proteinChange) {
        this.proteinChange = proteinChange;
        return this;
    }

    public void setProteinChange(String proteinChange) {
        this.proteinChange = proteinChange;
    }

    public Integer getProteinStartPosition() {
        return proteinStartPosition;
    }

    public PortalAlteration proteinStartPosition(Integer proteinStartPosition) {
        this.proteinStartPosition = proteinStartPosition;
        return this;
    }

    public void setProteinStartPosition(Integer proteinStartPosition) {
        this.proteinStartPosition = proteinStartPosition;
    }

    public Integer getProteinEndPosition() {
        return proteinEndPosition;
    }

    public PortalAlteration proteinEndPosition(Integer proteinEndPosition) {
        this.proteinEndPosition = proteinEndPosition;
        return this;
    }

    public void setProteinEndPosition(Integer proteinEndPosition) {
        this.proteinEndPosition = proteinEndPosition;
    }

    public String getAlterationType() {
        return alterationType;
    }

    public PortalAlteration alterationType(String alterationType) {
        this.alterationType = alterationType;
        return this;
    }

    public void setAlterationType(String alterationType) {
        this.alterationType = alterationType;
    }

    public Gene getGene() {
        return gene;
    }

    public PortalAlteration gene(Gene gene) {
        this.gene = gene;
        return this;
    }

    public void setGene(Gene gene) {
        this.gene = gene;
    }

    public Set<Alteration> getAlterations() {
        return alterations;
    }

    public PortalAlteration alterations(Set<Alteration> alterations) {
        this.alterations = alterations;
        return this;
    }

    public PortalAlteration addAlterations(Alteration alteration) {
        alterations.add(alteration);
        alteration.getPortalAlterations().add(this);
        return this;
    }

    public PortalAlteration removeAlterations(Alteration alteration) {
        alterations.remove(alteration);
        alteration.getPortalAlterations().remove(this);
        return this;
    }

    public void setAlterations(Set<Alteration> alterations) {
        this.alterations = alterations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PortalAlteration portalAlteration = (PortalAlteration) o;
        if(portalAlteration.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, portalAlteration.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PortalAlteration{" +
            "id=" + id +
            ", cancerType='" + cancerType + "'" +
            ", cancerStudy='" + cancerStudy + "'" +
            ", sampleId='" + sampleId + "'" +
            ", proteinChange='" + proteinChange + "'" +
            ", proteinStartPosition='" + proteinStartPosition + "'" +
            ", proteinEndPosition='" + proteinEndPosition + "'" +
            ", alterationType='" + alterationType + "'" +
            '}';
    }
}

package org.mskcc.cbio.oncokb.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Drug.
 */
@Entity
@Table(name = "drug")
public class Drug implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "drug_name")
    private String drugName;

    @Column(name = "fda_approved")
    private Boolean fdaApproved;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "drug")
    @JsonIgnore
    private Set<DrugSynonym> synonyms = new HashSet<>();

    @OneToMany(mappedBy = "drug")
    @JsonIgnore
    private Set<DrugAtcCode> atcCodes = new HashSet<>();

    @OneToMany(mappedBy = "drug")
    @JsonIgnore
    private Set<Status> statuses = new HashSet<>();

    @OneToMany(mappedBy = "drug")
    @JsonIgnore
    private Set<Comment> comments = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDrugName() {
        return drugName;
    }

    public Drug drugName(String drugName) {
        this.drugName = drugName;
        return this;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public Boolean isFdaApproved() {
        return fdaApproved;
    }

    public Drug fdaApproved(Boolean fdaApproved) {
        this.fdaApproved = fdaApproved;
        return this;
    }

    public void setFdaApproved(Boolean fdaApproved) {
        this.fdaApproved = fdaApproved;
    }

    public String getDescription() {
        return description;
    }

    public Drug description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<DrugSynonym> getSynonyms() {
        return synonyms;
    }

    public Drug synonyms(Set<DrugSynonym> drugSynonyms) {
        this.synonyms = drugSynonyms;
        return this;
    }

    public Drug addSynonyms(DrugSynonym drugSynonym) {
        synonyms.add(drugSynonym);
        drugSynonym.setDrug(this);
        return this;
    }

    public Drug removeSynonyms(DrugSynonym drugSynonym) {
        synonyms.remove(drugSynonym);
        drugSynonym.setDrug(null);
        return this;
    }

    public void setSynonyms(Set<DrugSynonym> drugSynonyms) {
        this.synonyms = drugSynonyms;
    }

    public Set<DrugAtcCode> getAtcCodes() {
        return atcCodes;
    }

    public Drug atcCodes(Set<DrugAtcCode> drugAtcCodes) {
        this.atcCodes = drugAtcCodes;
        return this;
    }

    public Drug addAtcCodes(DrugAtcCode drugAtcCode) {
        atcCodes.add(drugAtcCode);
        drugAtcCode.setDrug(this);
        return this;
    }

    public Drug removeAtcCodes(DrugAtcCode drugAtcCode) {
        atcCodes.remove(drugAtcCode);
        drugAtcCode.setDrug(null);
        return this;
    }

    public void setAtcCodes(Set<DrugAtcCode> drugAtcCodes) {
        this.atcCodes = drugAtcCodes;
    }

    public Set<Status> getStatuses() {
        return statuses;
    }

    public Drug statuses(Set<Status> statuses) {
        this.statuses = statuses;
        return this;
    }

    public Drug addStatuses(Status status) {
        statuses.add(status);
        status.setDrug(this);
        return this;
    }

    public Drug removeStatuses(Status status) {
        statuses.remove(status);
        status.setDrug(null);
        return this;
    }

    public void setStatuses(Set<Status> statuses) {
        this.statuses = statuses;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Drug comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public Drug addComments(Comment comment) {
        comments.add(comment);
        comment.setDrug(this);
        return this;
    }

    public Drug removeComments(Comment comment) {
        comments.remove(comment);
        comment.setDrug(null);
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
        Drug drug = (Drug) o;
        if(drug.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, drug.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Drug{" +
            "id=" + id +
            ", drugName='" + drugName + "'" +
            ", fdaApproved='" + fdaApproved + "'" +
            ", description='" + description + "'" +
            '}';
    }
}

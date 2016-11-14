package org.mskcc.cbio.oncokb.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ClinicalTrial.
 */
@Entity
@Table(name = "clinical_trial")
public class ClinicalTrial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nct_id")
    private String nctId;

    @Column(name = "cdr_id")
    private String cdrId;

    @Column(name = "title")
    private String title;

    @Column(name = "purpose")
    private String purpose;

    @Column(name = "recruiting_status")
    private String recruitingStatus;

    @Column(name = "eligibility_criteria")
    private String eligibilityCriteria;

    @Column(name = "phase")
    private String phase;

    @Column(name = "disease_condition")
    private String diseaseCondition;

    @Column(name = "last_changed_date")
    private String lastChangedDate;

    @OneToMany(mappedBy = "clinicalTrial")
    @JsonIgnore
    private Set<Status> statuses = new HashSet<>();

    @OneToMany(mappedBy = "clinicalTrial")
    @JsonIgnore
    private Set<Comment> comments = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "clinical_trial_drugs",
               joinColumns = @JoinColumn(name="clinical_trials_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="drugs_id", referencedColumnName="ID"))
    private Set<Drug> drugs = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "clinical_trial_countries",
               joinColumns = @JoinColumn(name="clinical_trials_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="countries_id", referencedColumnName="ID"))
    private Set<Country> countries = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNctId() {
        return nctId;
    }

    public ClinicalTrial nctId(String nctId) {
        this.nctId = nctId;
        return this;
    }

    public void setNctId(String nctId) {
        this.nctId = nctId;
    }

    public String getCdrId() {
        return cdrId;
    }

    public ClinicalTrial cdrId(String cdrId) {
        this.cdrId = cdrId;
        return this;
    }

    public void setCdrId(String cdrId) {
        this.cdrId = cdrId;
    }

    public String getTitle() {
        return title;
    }

    public ClinicalTrial title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPurpose() {
        return purpose;
    }

    public ClinicalTrial purpose(String purpose) {
        this.purpose = purpose;
        return this;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getRecruitingStatus() {
        return recruitingStatus;
    }

    public ClinicalTrial recruitingStatus(String recruitingStatus) {
        this.recruitingStatus = recruitingStatus;
        return this;
    }

    public void setRecruitingStatus(String recruitingStatus) {
        this.recruitingStatus = recruitingStatus;
    }

    public String getEligibilityCriteria() {
        return eligibilityCriteria;
    }

    public ClinicalTrial eligibilityCriteria(String eligibilityCriteria) {
        this.eligibilityCriteria = eligibilityCriteria;
        return this;
    }

    public void setEligibilityCriteria(String eligibilityCriteria) {
        this.eligibilityCriteria = eligibilityCriteria;
    }

    public String getPhase() {
        return phase;
    }

    public ClinicalTrial phase(String phase) {
        this.phase = phase;
        return this;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getDiseaseCondition() {
        return diseaseCondition;
    }

    public ClinicalTrial diseaseCondition(String diseaseCondition) {
        this.diseaseCondition = diseaseCondition;
        return this;
    }

    public void setDiseaseCondition(String diseaseCondition) {
        this.diseaseCondition = diseaseCondition;
    }

    public String getLastChangedDate() {
        return lastChangedDate;
    }

    public ClinicalTrial lastChangedDate(String lastChangedDate) {
        this.lastChangedDate = lastChangedDate;
        return this;
    }

    public void setLastChangedDate(String lastChangedDate) {
        this.lastChangedDate = lastChangedDate;
    }

    public Set<Status> getStatuses() {
        return statuses;
    }

    public ClinicalTrial statuses(Set<Status> statuses) {
        this.statuses = statuses;
        return this;
    }

    public ClinicalTrial addStatuses(Status status) {
        statuses.add(status);
        status.setClinicalTrial(this);
        return this;
    }

    public ClinicalTrial removeStatuses(Status status) {
        statuses.remove(status);
        status.setClinicalTrial(null);
        return this;
    }

    public void setStatuses(Set<Status> statuses) {
        this.statuses = statuses;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public ClinicalTrial comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public ClinicalTrial addComments(Comment comment) {
        comments.add(comment);
        comment.setClinicalTrial(this);
        return this;
    }

    public ClinicalTrial removeComments(Comment comment) {
        comments.remove(comment);
        comment.setClinicalTrial(null);
        return this;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Drug> getDrugs() {
        return drugs;
    }

    public ClinicalTrial drugs(Set<Drug> drugs) {
        this.drugs = drugs;
        return this;
    }

    public ClinicalTrial addDrugs(Drug drug) {
        drugs.add(drug);
        return this;
    }

    public ClinicalTrial removeDrugs(Drug drug) {
        drugs.remove(drug);
        return this;
    }

    public void setDrugs(Set<Drug> drugs) {
        this.drugs = drugs;
    }

    public Set<Country> getCountries() {
        return countries;
    }

    public ClinicalTrial countries(Set<Country> countries) {
        this.countries = countries;
        return this;
    }

    public ClinicalTrial addCountries(Country country) {
        countries.add(country);
        return this;
    }

    public ClinicalTrial removeCountries(Country country) {
        countries.remove(country);
        return this;
    }

    public void setCountries(Set<Country> countries) {
        this.countries = countries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ClinicalTrial clinicalTrial = (ClinicalTrial) o;
        if(clinicalTrial.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, clinicalTrial.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ClinicalTrial{" +
            "id=" + id +
            ", nctId='" + nctId + "'" +
            ", cdrId='" + cdrId + "'" +
            ", title='" + title + "'" +
            ", purpose='" + purpose + "'" +
            ", recruitingStatus='" + recruitingStatus + "'" +
            ", eligibilityCriteria='" + eligibilityCriteria + "'" +
            ", phase='" + phase + "'" +
            ", diseaseCondition='" + diseaseCondition + "'" +
            ", lastChangedDate='" + lastChangedDate + "'" +
            '}';
    }
}

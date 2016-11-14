package org.mskcc.cbio.oncokb.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import org.mskcc.cbio.oncokb.domain.enumeration.EvidenceType;

import org.mskcc.cbio.oncokb.domain.enumeration.LevelOfEvidence;

import org.mskcc.cbio.oncokb.domain.enumeration.KnownEffect;

/**
 * A Evidence.
 */
@Entity
@Table(name = "evidence")
public class Evidence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "evidence_type")
    private EvidenceType evidenceType;

    @Enumerated(EnumType.STRING)
    @Column(name = "level_of_evidence")
    private LevelOfEvidence levelOfEvidence;

    @Enumerated(EnumType.STRING)
    @Column(name = "known_effect")
    private KnownEffect knownEffect;

    @Column(name = "cancer_type")
    private String cancerType;

    @Column(name = "subtype")
    private String subtype;

    @Column(name = "description")
    private String description;

    @Column(name = "additional_info")
    private String additionalInfo;

    @Column(name = "last_edit")
    private ZonedDateTime lastEdit;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "evidence")
    @JsonIgnore
    private Set<Status> statuses = new HashSet<>();

    @OneToMany(mappedBy = "evidence")
    @JsonIgnore
    private Set<Comment> comments = new HashSet<>();

    @ManyToOne
    private Gene gene;

    @ManyToMany
    @JoinTable(name = "evidence_alterations",
               joinColumns = @JoinColumn(name="evidences_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="alterations_id", referencedColumnName="ID"))
    private Set<Alteration> alterations = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "evidence_treatments",
               joinColumns = @JoinColumn(name="evidences_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="treatments_id", referencedColumnName="ID"))
    private Set<Treatment> treatments = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "evidence_articles",
               joinColumns = @JoinColumn(name="evidences_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="articles_id", referencedColumnName="ID"))
    private Set<Article> articles = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "evidence_clinical_trials",
               joinColumns = @JoinColumn(name="evidences_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="clinical_trials_id", referencedColumnName="ID"))
    private Set<ClinicalTrial> clinicalTrials = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "evidence_nccn_guidelines",
               joinColumns = @JoinColumn(name="evidences_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="nccn_guidelines_id", referencedColumnName="ID"))
    private Set<NccnGuideline> nccnGuidelines = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EvidenceType getEvidenceType() {
        return evidenceType;
    }

    public Evidence evidenceType(EvidenceType evidenceType) {
        this.evidenceType = evidenceType;
        return this;
    }

    public void setEvidenceType(EvidenceType evidenceType) {
        this.evidenceType = evidenceType;
    }

    public LevelOfEvidence getLevelOfEvidence() {
        return levelOfEvidence;
    }

    public Evidence levelOfEvidence(LevelOfEvidence levelOfEvidence) {
        this.levelOfEvidence = levelOfEvidence;
        return this;
    }

    public void setLevelOfEvidence(LevelOfEvidence levelOfEvidence) {
        this.levelOfEvidence = levelOfEvidence;
    }

    public KnownEffect getKnownEffect() {
        return knownEffect;
    }

    public Evidence knownEffect(KnownEffect knownEffect) {
        this.knownEffect = knownEffect;
        return this;
    }

    public void setKnownEffect(KnownEffect knownEffect) {
        this.knownEffect = knownEffect;
    }

    public String getCancerType() {
        return cancerType;
    }

    public Evidence cancerType(String cancerType) {
        this.cancerType = cancerType;
        return this;
    }

    public void setCancerType(String cancerType) {
        this.cancerType = cancerType;
    }

    public String getSubtype() {
        return subtype;
    }

    public Evidence subtype(String subtype) {
        this.subtype = subtype;
        return this;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getDescription() {
        return description;
    }

    public Evidence description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public Evidence additionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
        return this;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public ZonedDateTime getLastEdit() {
        return lastEdit;
    }

    public Evidence lastEdit(ZonedDateTime lastEdit) {
        this.lastEdit = lastEdit;
        return this;
    }

    public void setLastEdit(ZonedDateTime lastEdit) {
        this.lastEdit = lastEdit;
    }

    public String getStatus() {
        return status;
    }

    public Evidence status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Status> getStatuses() {
        return statuses;
    }

    public Evidence statuses(Set<Status> statuses) {
        this.statuses = statuses;
        return this;
    }

    public Evidence addStatuses(Status status) {
        statuses.add(status);
        status.setEvidence(this);
        return this;
    }

    public Evidence removeStatuses(Status status) {
        statuses.remove(status);
        status.setEvidence(null);
        return this;
    }

    public void setStatuses(Set<Status> statuses) {
        this.statuses = statuses;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Evidence comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public Evidence addComments(Comment comment) {
        comments.add(comment);
        comment.setEvidence(this);
        return this;
    }

    public Evidence removeComments(Comment comment) {
        comments.remove(comment);
        comment.setEvidence(null);
        return this;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Gene getGene() {
        return gene;
    }

    public Evidence gene(Gene gene) {
        this.gene = gene;
        return this;
    }

    public void setGene(Gene gene) {
        this.gene = gene;
    }

    public Set<Alteration> getAlterations() {
        return alterations;
    }

    public Evidence alterations(Set<Alteration> alterations) {
        this.alterations = alterations;
        return this;
    }

    public Evidence addAlterations(Alteration alteration) {
        alterations.add(alteration);
        return this;
    }

    public Evidence removeAlterations(Alteration alteration) {
        alterations.remove(alteration);
        return this;
    }

    public void setAlterations(Set<Alteration> alterations) {
        this.alterations = alterations;
    }

    public Set<Treatment> getTreatments() {
        return treatments;
    }

    public Evidence treatments(Set<Treatment> treatments) {
        this.treatments = treatments;
        return this;
    }

    public Evidence addTreatments(Treatment treatment) {
        treatments.add(treatment);
        return this;
    }

    public Evidence removeTreatments(Treatment treatment) {
        treatments.remove(treatment);
        return this;
    }

    public void setTreatments(Set<Treatment> treatments) {
        this.treatments = treatments;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public Evidence articles(Set<Article> articles) {
        this.articles = articles;
        return this;
    }

    public Evidence addArticles(Article article) {
        articles.add(article);
        return this;
    }

    public Evidence removeArticles(Article article) {
        articles.remove(article);
        return this;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    public Set<ClinicalTrial> getClinicalTrials() {
        return clinicalTrials;
    }

    public Evidence clinicalTrials(Set<ClinicalTrial> clinicalTrials) {
        this.clinicalTrials = clinicalTrials;
        return this;
    }

    public Evidence addClinicalTrials(ClinicalTrial clinicalTrial) {
        clinicalTrials.add(clinicalTrial);
        return this;
    }

    public Evidence removeClinicalTrials(ClinicalTrial clinicalTrial) {
        clinicalTrials.remove(clinicalTrial);
        return this;
    }

    public void setClinicalTrials(Set<ClinicalTrial> clinicalTrials) {
        this.clinicalTrials = clinicalTrials;
    }

    public Set<NccnGuideline> getNccnGuidelines() {
        return nccnGuidelines;
    }

    public Evidence nccnGuidelines(Set<NccnGuideline> nccnGuidelines) {
        this.nccnGuidelines = nccnGuidelines;
        return this;
    }

    public Evidence addNccnGuidelines(NccnGuideline nccnGuideline) {
        nccnGuidelines.add(nccnGuideline);
        return this;
    }

    public Evidence removeNccnGuidelines(NccnGuideline nccnGuideline) {
        nccnGuidelines.remove(nccnGuideline);
        return this;
    }

    public void setNccnGuidelines(Set<NccnGuideline> nccnGuidelines) {
        this.nccnGuidelines = nccnGuidelines;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Evidence evidence = (Evidence) o;
        if(evidence.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, evidence.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Evidence{" +
            "id=" + id +
            ", evidenceType='" + evidenceType + "'" +
            ", levelOfEvidence='" + levelOfEvidence + "'" +
            ", knownEffect='" + knownEffect + "'" +
            ", cancerType='" + cancerType + "'" +
            ", subtype='" + subtype + "'" +
            ", description='" + description + "'" +
            ", additionalInfo='" + additionalInfo + "'" +
            ", lastEdit='" + lastEdit + "'" +
            ", status='" + status + "'" +
            '}';
    }
}

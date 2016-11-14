package org.mskcc.cbio.oncokb.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import org.mskcc.cbio.oncokb.domain.enumeration.StatusType;

/**
 * A Status.
 */
@Entity
@Table(name = "status")
public class Status implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_type")
    private StatusType statusType;

    @Column(name = "status")
    private String status;

    @ManyToOne
    private Alteration alteration;

    @ManyToOne
    private Gene gene;

    @ManyToOne
    private ClinicalTrial clinicalTrial;

    @ManyToOne
    private Article article;

    @ManyToOne
    private Drug drug;

    @ManyToOne
    private Evidence evidence;

    @ManyToOne
    private Treatment treatment;

    @OneToOne
    @JoinColumn(unique = true)
    private TimeStamp timeStamp;

    @ManyToOne
    private Comment comment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusType getStatusType() {
        return statusType;
    }

    public Status statusType(StatusType statusType) {
        this.statusType = statusType;
        return this;
    }

    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }

    public String getStatus() {
        return status;
    }

    public Status status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Alteration getAlteration() {
        return alteration;
    }

    public Status alteration(Alteration alteration) {
        this.alteration = alteration;
        return this;
    }

    public void setAlteration(Alteration alteration) {
        this.alteration = alteration;
    }

    public Gene getGene() {
        return gene;
    }

    public Status gene(Gene gene) {
        this.gene = gene;
        return this;
    }

    public void setGene(Gene gene) {
        this.gene = gene;
    }

    public ClinicalTrial getClinicalTrial() {
        return clinicalTrial;
    }

    public Status clinicalTrial(ClinicalTrial clinicalTrial) {
        this.clinicalTrial = clinicalTrial;
        return this;
    }

    public void setClinicalTrial(ClinicalTrial clinicalTrial) {
        this.clinicalTrial = clinicalTrial;
    }

    public Article getArticle() {
        return article;
    }

    public Status article(Article article) {
        this.article = article;
        return this;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Drug getDrug() {
        return drug;
    }

    public Status drug(Drug drug) {
        this.drug = drug;
        return this;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    public Evidence getEvidence() {
        return evidence;
    }

    public Status evidence(Evidence evidence) {
        this.evidence = evidence;
        return this;
    }

    public void setEvidence(Evidence evidence) {
        this.evidence = evidence;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public Status treatment(Treatment treatment) {
        this.treatment = treatment;
        return this;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    public TimeStamp getTimeStamp() {
        return timeStamp;
    }

    public Status timeStamp(TimeStamp timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }

    public void setTimeStamp(TimeStamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Comment getComment() {
        return comment;
    }

    public Status comment(Comment comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Status status = (Status) o;
        if(status.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, status.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Status{" +
            "id=" + id +
            ", statusType='" + statusType + "'" +
            ", status='" + status + "'" +
            '}';
    }
}

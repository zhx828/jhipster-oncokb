package org.mskcc.cbio.oncokb.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Comment.
 */
@Entity
@Table(name = "comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "content")
    private String content;

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

    @OneToMany(mappedBy = "comment")
    @JsonIgnore
    private Set<Status> statuses = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public Comment content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Alteration getAlteration() {
        return alteration;
    }

    public Comment alteration(Alteration alteration) {
        this.alteration = alteration;
        return this;
    }

    public void setAlteration(Alteration alteration) {
        this.alteration = alteration;
    }

    public Gene getGene() {
        return gene;
    }

    public Comment gene(Gene gene) {
        this.gene = gene;
        return this;
    }

    public void setGene(Gene gene) {
        this.gene = gene;
    }

    public ClinicalTrial getClinicalTrial() {
        return clinicalTrial;
    }

    public Comment clinicalTrial(ClinicalTrial clinicalTrial) {
        this.clinicalTrial = clinicalTrial;
        return this;
    }

    public void setClinicalTrial(ClinicalTrial clinicalTrial) {
        this.clinicalTrial = clinicalTrial;
    }

    public Article getArticle() {
        return article;
    }

    public Comment article(Article article) {
        this.article = article;
        return this;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Drug getDrug() {
        return drug;
    }

    public Comment drug(Drug drug) {
        this.drug = drug;
        return this;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    public Evidence getEvidence() {
        return evidence;
    }

    public Comment evidence(Evidence evidence) {
        this.evidence = evidence;
        return this;
    }

    public void setEvidence(Evidence evidence) {
        this.evidence = evidence;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public Comment treatment(Treatment treatment) {
        this.treatment = treatment;
        return this;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    public TimeStamp getTimeStamp() {
        return timeStamp;
    }

    public Comment timeStamp(TimeStamp timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }

    public void setTimeStamp(TimeStamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Set<Status> getStatuses() {
        return statuses;
    }

    public Comment statuses(Set<Status> statuses) {
        this.statuses = statuses;
        return this;
    }

    public Comment addStatuses(Status status) {
        statuses.add(status);
        status.setComment(this);
        return this;
    }

    public Comment removeStatuses(Status status) {
        statuses.remove(status);
        status.setComment(null);
        return this;
    }

    public void setStatuses(Set<Status> statuses) {
        this.statuses = statuses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Comment comment = (Comment) o;
        if(comment.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, comment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Comment{" +
            "id=" + id +
            ", content='" + content + "'" +
            '}';
    }
}

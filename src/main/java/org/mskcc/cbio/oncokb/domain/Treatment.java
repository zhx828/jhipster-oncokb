package org.mskcc.cbio.oncokb.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Treatment.
 */
@Entity
@Table(name = "treatment")
public class Treatment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "treatment")
    @JsonIgnore
    private Set<Status> statuses = new HashSet<>();

    @OneToMany(mappedBy = "treatment")
    @JsonIgnore
    private Set<Comment> comments = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "treatment_drugs",
               joinColumns = @JoinColumn(name="treatments_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="drugs_id", referencedColumnName="ID"))
    private Set<Drug> drugs = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "treatment_approved_indications",
               joinColumns = @JoinColumn(name="treatments_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="approved_indications_id", referencedColumnName="ID"))
    private Set<ApprovedIndication> approvedIndications = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Status> getStatuses() {
        return statuses;
    }

    public Treatment statuses(Set<Status> statuses) {
        this.statuses = statuses;
        return this;
    }

    public Treatment addStatuses(Status status) {
        statuses.add(status);
        status.setTreatment(this);
        return this;
    }

    public Treatment removeStatuses(Status status) {
        statuses.remove(status);
        status.setTreatment(null);
        return this;
    }

    public void setStatuses(Set<Status> statuses) {
        this.statuses = statuses;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Treatment comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public Treatment addComments(Comment comment) {
        comments.add(comment);
        comment.setTreatment(this);
        return this;
    }

    public Treatment removeComments(Comment comment) {
        comments.remove(comment);
        comment.setTreatment(null);
        return this;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Drug> getDrugs() {
        return drugs;
    }

    public Treatment drugs(Set<Drug> drugs) {
        this.drugs = drugs;
        return this;
    }

    public Treatment addDrugs(Drug drug) {
        drugs.add(drug);
        return this;
    }

    public Treatment removeDrugs(Drug drug) {
        drugs.remove(drug);
        return this;
    }

    public void setDrugs(Set<Drug> drugs) {
        this.drugs = drugs;
    }

    public Set<ApprovedIndication> getApprovedIndications() {
        return approvedIndications;
    }

    public Treatment approvedIndications(Set<ApprovedIndication> approvedIndications) {
        this.approvedIndications = approvedIndications;
        return this;
    }

    public Treatment addApprovedIndications(ApprovedIndication approvedIndication) {
        approvedIndications.add(approvedIndication);
        return this;
    }

    public Treatment removeApprovedIndications(ApprovedIndication approvedIndication) {
        approvedIndications.remove(approvedIndication);
        return this;
    }

    public void setApprovedIndications(Set<ApprovedIndication> approvedIndications) {
        this.approvedIndications = approvedIndications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Treatment treatment = (Treatment) o;
        if(treatment.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, treatment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Treatment{" +
            "id=" + id +
            '}';
    }
}

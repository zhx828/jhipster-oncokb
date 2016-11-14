package org.mskcc.cbio.oncokb.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ApprovedIndication.
 */
@Entity
@Table(name = "approved_indication")
public class ApprovedIndication implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "approved_indications")
    private String approvedIndications;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApprovedIndications() {
        return approvedIndications;
    }

    public ApprovedIndication approvedIndications(String approvedIndications) {
        this.approvedIndications = approvedIndications;
        return this;
    }

    public void setApprovedIndications(String approvedIndications) {
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
        ApprovedIndication approvedIndication = (ApprovedIndication) o;
        if(approvedIndication.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, approvedIndication.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ApprovedIndication{" +
            "id=" + id +
            ", approvedIndications='" + approvedIndications + "'" +
            '}';
    }
}

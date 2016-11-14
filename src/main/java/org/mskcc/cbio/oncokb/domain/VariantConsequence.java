package org.mskcc.cbio.oncokb.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A VariantConsequence.
 */
@Entity
@Table(name = "variant_consequence")
public class VariantConsequence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "term")
    private String term;

    @Column(name = "is_generally_truncating")
    private Boolean isGenerallyTruncating;

    @Column(name = "description")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTerm() {
        return term;
    }

    public VariantConsequence term(String term) {
        this.term = term;
        return this;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Boolean isIsGenerallyTruncating() {
        return isGenerallyTruncating;
    }

    public VariantConsequence isGenerallyTruncating(Boolean isGenerallyTruncating) {
        this.isGenerallyTruncating = isGenerallyTruncating;
        return this;
    }

    public void setIsGenerallyTruncating(Boolean isGenerallyTruncating) {
        this.isGenerallyTruncating = isGenerallyTruncating;
    }

    public String getDescription() {
        return description;
    }

    public VariantConsequence description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VariantConsequence variantConsequence = (VariantConsequence) o;
        if(variantConsequence.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, variantConsequence.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "VariantConsequence{" +
            "id=" + id +
            ", term='" + term + "'" +
            ", isGenerallyTruncating='" + isGenerallyTruncating + "'" +
            ", description='" + description + "'" +
            '}';
    }
}

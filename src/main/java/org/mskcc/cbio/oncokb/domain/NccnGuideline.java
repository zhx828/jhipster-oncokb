package org.mskcc.cbio.oncokb.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A NccnGuideline.
 */
@Entity
@Table(name = "nccn_guideline")
public class NccnGuideline implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "disease")
    private String disease;

    @Column(name = "version")
    private String version;

    @Column(name = "pages")
    private String pages;

    @Column(name = "category")
    private String category;

    @Column(name = "description")
    private String description;

    @Column(name = "additional_info")
    private String additionalInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisease() {
        return disease;
    }

    public NccnGuideline disease(String disease) {
        this.disease = disease;
        return this;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getVersion() {
        return version;
    }

    public NccnGuideline version(String version) {
        this.version = version;
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPages() {
        return pages;
    }

    public NccnGuideline pages(String pages) {
        this.pages = pages;
        return this;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getCategory() {
        return category;
    }

    public NccnGuideline category(String category) {
        this.category = category;
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public NccnGuideline description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public NccnGuideline additionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
        return this;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NccnGuideline nccnGuideline = (NccnGuideline) o;
        if(nccnGuideline.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, nccnGuideline.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "NccnGuideline{" +
            "id=" + id +
            ", disease='" + disease + "'" +
            ", version='" + version + "'" +
            ", pages='" + pages + "'" +
            ", category='" + category + "'" +
            ", description='" + description + "'" +
            ", additionalInfo='" + additionalInfo + "'" +
            '}';
    }
}

package com.neutronbinary.infectolabs.microservice.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A NBMapAttributes.
 */
@Entity
@Table(name = "nb_map_attributes")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class NBMapAttributes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nb_idfk")
    private String nbIDFK;

    @Column(name = "nb_title")
    private String nbTitle;

    @Column(name = "nb_title_location")
    private String nbTitleLocation;

    @Column(name = "nb_palette_idfk")
    private String nbPaletteIDFK;

    @Column(name = "nb_chart_idfk")
    private String nbChartIDFK;

    @Column(name = "nb_chart_type")
    private String nbChartType;

    @Column(name = "nb_last_updated")
    private String nbLastUpdated;

    @Column(name = "nb_last_updated_by")
    private String nbLastUpdatedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public NBMapAttributes id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNbIDFK() {
        return this.nbIDFK;
    }

    public NBMapAttributes nbIDFK(String nbIDFK) {
        this.setNbIDFK(nbIDFK);
        return this;
    }

    public void setNbIDFK(String nbIDFK) {
        this.nbIDFK = nbIDFK;
    }

    public String getNbTitle() {
        return this.nbTitle;
    }

    public NBMapAttributes nbTitle(String nbTitle) {
        this.setNbTitle(nbTitle);
        return this;
    }

    public void setNbTitle(String nbTitle) {
        this.nbTitle = nbTitle;
    }

    public String getNbTitleLocation() {
        return this.nbTitleLocation;
    }

    public NBMapAttributes nbTitleLocation(String nbTitleLocation) {
        this.setNbTitleLocation(nbTitleLocation);
        return this;
    }

    public void setNbTitleLocation(String nbTitleLocation) {
        this.nbTitleLocation = nbTitleLocation;
    }

    public String getNbPaletteIDFK() {
        return this.nbPaletteIDFK;
    }

    public NBMapAttributes nbPaletteIDFK(String nbPaletteIDFK) {
        this.setNbPaletteIDFK(nbPaletteIDFK);
        return this;
    }

    public void setNbPaletteIDFK(String nbPaletteIDFK) {
        this.nbPaletteIDFK = nbPaletteIDFK;
    }

    public String getNbChartIDFK() {
        return this.nbChartIDFK;
    }

    public NBMapAttributes nbChartIDFK(String nbChartIDFK) {
        this.setNbChartIDFK(nbChartIDFK);
        return this;
    }

    public void setNbChartIDFK(String nbChartIDFK) {
        this.nbChartIDFK = nbChartIDFK;
    }

    public String getNbChartType() {
        return this.nbChartType;
    }

    public NBMapAttributes nbChartType(String nbChartType) {
        this.setNbChartType(nbChartType);
        return this;
    }

    public void setNbChartType(String nbChartType) {
        this.nbChartType = nbChartType;
    }

    public String getNbLastUpdated() {
        return this.nbLastUpdated;
    }

    public NBMapAttributes nbLastUpdated(String nbLastUpdated) {
        this.setNbLastUpdated(nbLastUpdated);
        return this;
    }

    public void setNbLastUpdated(String nbLastUpdated) {
        this.nbLastUpdated = nbLastUpdated;
    }

    public String getNbLastUpdatedBy() {
        return this.nbLastUpdatedBy;
    }

    public NBMapAttributes nbLastUpdatedBy(String nbLastUpdatedBy) {
        this.setNbLastUpdatedBy(nbLastUpdatedBy);
        return this;
    }

    public void setNbLastUpdatedBy(String nbLastUpdatedBy) {
        this.nbLastUpdatedBy = nbLastUpdatedBy;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NBMapAttributes)) {
            return false;
        }
        return id != null && id.equals(((NBMapAttributes) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NBMapAttributes{" +
            "id=" + getId() +
            ", nbIDFK='" + getNbIDFK() + "'" +
            ", nbTitle='" + getNbTitle() + "'" +
            ", nbTitleLocation='" + getNbTitleLocation() + "'" +
            ", nbPaletteIDFK='" + getNbPaletteIDFK() + "'" +
            ", nbChartIDFK='" + getNbChartIDFK() + "'" +
            ", nbChartType='" + getNbChartType() + "'" +
            ", nbLastUpdated='" + getNbLastUpdated() + "'" +
            ", nbLastUpdatedBy='" + getNbLastUpdatedBy() + "'" +
            "}";
    }
}

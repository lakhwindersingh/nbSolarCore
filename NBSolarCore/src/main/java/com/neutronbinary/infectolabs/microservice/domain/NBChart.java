package com.neutronbinary.infectolabs.microservice.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A NBChart.
 */
@Entity
@Table(name = "nb_chart")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class NBChart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nb_chart_id")
    private String nbChartID;

    @Column(name = "nb_chart_title")
    private String nbChartTitle;

    @Column(name = "nb_chart_type")
    private String nbChartType;

    @Column(name = "nb_chart_params")
    private String nbChartParams;

    @Column(name = "nb_last_updated")
    private String nbLastUpdated;

    @Column(name = "nb_last_updated_by")
    private String nbLastUpdatedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public NBChart id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNbChartID() {
        return this.nbChartID;
    }

    public NBChart nbChartID(String nbChartID) {
        this.setNbChartID(nbChartID);
        return this;
    }

    public void setNbChartID(String nbChartID) {
        this.nbChartID = nbChartID;
    }

    public String getNbChartTitle() {
        return this.nbChartTitle;
    }

    public NBChart nbChartTitle(String nbChartTitle) {
        this.setNbChartTitle(nbChartTitle);
        return this;
    }

    public void setNbChartTitle(String nbChartTitle) {
        this.nbChartTitle = nbChartTitle;
    }

    public String getNbChartType() {
        return this.nbChartType;
    }

    public NBChart nbChartType(String nbChartType) {
        this.setNbChartType(nbChartType);
        return this;
    }

    public void setNbChartType(String nbChartType) {
        this.nbChartType = nbChartType;
    }

    public String getNbChartParams() {
        return this.nbChartParams;
    }

    public NBChart nbChartParams(String nbChartParams) {
        this.setNbChartParams(nbChartParams);
        return this;
    }

    public void setNbChartParams(String nbChartParams) {
        this.nbChartParams = nbChartParams;
    }

    public String getNbLastUpdated() {
        return this.nbLastUpdated;
    }

    public NBChart nbLastUpdated(String nbLastUpdated) {
        this.setNbLastUpdated(nbLastUpdated);
        return this;
    }

    public void setNbLastUpdated(String nbLastUpdated) {
        this.nbLastUpdated = nbLastUpdated;
    }

    public String getNbLastUpdatedBy() {
        return this.nbLastUpdatedBy;
    }

    public NBChart nbLastUpdatedBy(String nbLastUpdatedBy) {
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
        if (!(o instanceof NBChart)) {
            return false;
        }
        return id != null && id.equals(((NBChart) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NBChart{" +
            "id=" + getId() +
            ", nbChartID='" + getNbChartID() + "'" +
            ", nbChartTitle='" + getNbChartTitle() + "'" +
            ", nbChartType='" + getNbChartType() + "'" +
            ", nbChartParams='" + getNbChartParams() + "'" +
            ", nbLastUpdated='" + getNbLastUpdated() + "'" +
            ", nbLastUpdatedBy='" + getNbLastUpdatedBy() + "'" +
            "}";
    }
}

package com.neutronbinary.infectolabs.microservice.domain;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * not an ignored comment
 */
@ApiModel(description = "not an ignored comment")
@Entity
@Table(name = "nb_map_components")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class NBMapComponents implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nb_idfk")
    private String nbIDFK;

    @Column(name = "nb_component_id")
    private String nbComponentID;

    @Column(name = "nb_component_type")
    private String nbComponentType;

    @Column(name = "nb_component_value")
    private String nbComponentValue;

    @Column(name = "nb_default")
    private String nbDefault;

    @Column(name = "nb_last_updated")
    private String nbLastUpdated;

    @Column(name = "nb_last_updated_by")
    private String nbLastUpdatedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public NBMapComponents id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNbIDFK() {
        return this.nbIDFK;
    }

    public NBMapComponents nbIDFK(String nbIDFK) {
        this.setNbIDFK(nbIDFK);
        return this;
    }

    public void setNbIDFK(String nbIDFK) {
        this.nbIDFK = nbIDFK;
    }

    public String getNbComponentID() {
        return this.nbComponentID;
    }

    public NBMapComponents nbComponentID(String nbComponentID) {
        this.setNbComponentID(nbComponentID);
        return this;
    }

    public void setNbComponentID(String nbComponentID) {
        this.nbComponentID = nbComponentID;
    }

    public String getNbComponentType() {
        return this.nbComponentType;
    }

    public NBMapComponents nbComponentType(String nbComponentType) {
        this.setNbComponentType(nbComponentType);
        return this;
    }

    public void setNbComponentType(String nbComponentType) {
        this.nbComponentType = nbComponentType;
    }

    public String getNbComponentValue() {
        return this.nbComponentValue;
    }

    public NBMapComponents nbComponentValue(String nbComponentValue) {
        this.setNbComponentValue(nbComponentValue);
        return this;
    }

    public void setNbComponentValue(String nbComponentValue) {
        this.nbComponentValue = nbComponentValue;
    }

    public String getNbDefault() {
        return this.nbDefault;
    }

    public NBMapComponents nbDefault(String nbDefault) {
        this.setNbDefault(nbDefault);
        return this;
    }

    public void setNbDefault(String nbDefault) {
        this.nbDefault = nbDefault;
    }

    public String getNbLastUpdated() {
        return this.nbLastUpdated;
    }

    public NBMapComponents nbLastUpdated(String nbLastUpdated) {
        this.setNbLastUpdated(nbLastUpdated);
        return this;
    }

    public void setNbLastUpdated(String nbLastUpdated) {
        this.nbLastUpdated = nbLastUpdated;
    }

    public String getNbLastUpdatedBy() {
        return this.nbLastUpdatedBy;
    }

    public NBMapComponents nbLastUpdatedBy(String nbLastUpdatedBy) {
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
        if (!(o instanceof NBMapComponents)) {
            return false;
        }
        return id != null && id.equals(((NBMapComponents) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NBMapComponents{" +
            "id=" + getId() +
            ", nbIDFK='" + getNbIDFK() + "'" +
            ", nbComponentID='" + getNbComponentID() + "'" +
            ", nbComponentType='" + getNbComponentType() + "'" +
            ", nbComponentValue='" + getNbComponentValue() + "'" +
            ", nbDefault='" + getNbDefault() + "'" +
            ", nbLastUpdated='" + getNbLastUpdated() + "'" +
            ", nbLastUpdatedBy='" + getNbLastUpdatedBy() + "'" +
            "}";
    }
}

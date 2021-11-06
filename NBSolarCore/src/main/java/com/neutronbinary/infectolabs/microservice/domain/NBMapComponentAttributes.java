package com.neutronbinary.infectolabs.microservice.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A NBMapComponentAttributes.
 */
@Entity
@Table(name = "nb_map_component_attributes")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class NBMapComponentAttributes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nb_component_idfk")
    private String nbComponentIDFK;

    @Column(name = "nb_component_name")
    private String nbComponentName;

    @Column(name = "nb_last_updated")
    private String nbLastUpdated;

    @Column(name = "nb_last_updated_by")
    private String nbLastUpdatedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public NBMapComponentAttributes id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNbComponentIDFK() {
        return this.nbComponentIDFK;
    }

    public NBMapComponentAttributes nbComponentIDFK(String nbComponentIDFK) {
        this.setNbComponentIDFK(nbComponentIDFK);
        return this;
    }

    public void setNbComponentIDFK(String nbComponentIDFK) {
        this.nbComponentIDFK = nbComponentIDFK;
    }

    public String getNbComponentName() {
        return this.nbComponentName;
    }

    public NBMapComponentAttributes nbComponentName(String nbComponentName) {
        this.setNbComponentName(nbComponentName);
        return this;
    }

    public void setNbComponentName(String nbComponentName) {
        this.nbComponentName = nbComponentName;
    }

    public String getNbLastUpdated() {
        return this.nbLastUpdated;
    }

    public NBMapComponentAttributes nbLastUpdated(String nbLastUpdated) {
        this.setNbLastUpdated(nbLastUpdated);
        return this;
    }

    public void setNbLastUpdated(String nbLastUpdated) {
        this.nbLastUpdated = nbLastUpdated;
    }

    public String getNbLastUpdatedBy() {
        return this.nbLastUpdatedBy;
    }

    public NBMapComponentAttributes nbLastUpdatedBy(String nbLastUpdatedBy) {
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
        if (!(o instanceof NBMapComponentAttributes)) {
            return false;
        }
        return id != null && id.equals(((NBMapComponentAttributes) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NBMapComponentAttributes{" +
            "id=" + getId() +
            ", nbComponentIDFK='" + getNbComponentIDFK() + "'" +
            ", nbComponentName='" + getNbComponentName() + "'" +
            ", nbLastUpdated='" + getNbLastUpdated() + "'" +
            ", nbLastUpdatedBy='" + getNbLastUpdatedBy() + "'" +
            "}";
    }
}

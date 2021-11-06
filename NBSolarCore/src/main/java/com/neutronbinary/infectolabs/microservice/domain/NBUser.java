package com.neutronbinary.infectolabs.microservice.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A NBUser.
 */
@Entity
@Table(name = "nb_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class NBUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nb_user_id")
    private String nbUserID;

    @Column(name = "nb_auth_type")
    private String nbAuthType;

    @Column(name = "nb_password_hash")
    private String nbPasswordHash;

    @Column(name = "nb_first_name")
    private String nbFirstName;

    @Column(name = "nb_last_name")
    private String nbLastName;

    @Column(name = "nb_address")
    private String nbAddress;

    @Column(name = "nb_email_id")
    private String nbEmailId;

    @Column(name = "nb_phone")
    private String nbPhone;

    @Column(name = "nb_is_active")
    private String nbIsActive;

    @Column(name = "nb_is_suspended")
    private String nbIsSuspended;

    @Column(name = "nb_is_banished")
    private String nbIsBanished;

    @Column(name = "nb_last_updated")
    private String nbLastUpdated;

    @Column(name = "nb_last_updated_by")
    private String nbLastUpdatedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public NBUser id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNbUserID() {
        return this.nbUserID;
    }

    public NBUser nbUserID(String nbUserID) {
        this.setNbUserID(nbUserID);
        return this;
    }

    public void setNbUserID(String nbUserID) {
        this.nbUserID = nbUserID;
    }

    public String getNbAuthType() {
        return this.nbAuthType;
    }

    public NBUser nbAuthType(String nbAuthType) {
        this.setNbAuthType(nbAuthType);
        return this;
    }

    public void setNbAuthType(String nbAuthType) {
        this.nbAuthType = nbAuthType;
    }

    public String getNbPasswordHash() {
        return this.nbPasswordHash;
    }

    public NBUser nbPasswordHash(String nbPasswordHash) {
        this.setNbPasswordHash(nbPasswordHash);
        return this;
    }

    public void setNbPasswordHash(String nbPasswordHash) {
        this.nbPasswordHash = nbPasswordHash;
    }

    public String getNbFirstName() {
        return this.nbFirstName;
    }

    public NBUser nbFirstName(String nbFirstName) {
        this.setNbFirstName(nbFirstName);
        return this;
    }

    public void setNbFirstName(String nbFirstName) {
        this.nbFirstName = nbFirstName;
    }

    public String getNbLastName() {
        return this.nbLastName;
    }

    public NBUser nbLastName(String nbLastName) {
        this.setNbLastName(nbLastName);
        return this;
    }

    public void setNbLastName(String nbLastName) {
        this.nbLastName = nbLastName;
    }

    public String getNbAddress() {
        return this.nbAddress;
    }

    public NBUser nbAddress(String nbAddress) {
        this.setNbAddress(nbAddress);
        return this;
    }

    public void setNbAddress(String nbAddress) {
        this.nbAddress = nbAddress;
    }

    public String getNbEmailId() {
        return this.nbEmailId;
    }

    public NBUser nbEmailId(String nbEmailId) {
        this.setNbEmailId(nbEmailId);
        return this;
    }

    public void setNbEmailId(String nbEmailId) {
        this.nbEmailId = nbEmailId;
    }

    public String getNbPhone() {
        return this.nbPhone;
    }

    public NBUser nbPhone(String nbPhone) {
        this.setNbPhone(nbPhone);
        return this;
    }

    public void setNbPhone(String nbPhone) {
        this.nbPhone = nbPhone;
    }

    public String getNbIsActive() {
        return this.nbIsActive;
    }

    public NBUser nbIsActive(String nbIsActive) {
        this.setNbIsActive(nbIsActive);
        return this;
    }

    public void setNbIsActive(String nbIsActive) {
        this.nbIsActive = nbIsActive;
    }

    public String getNbIsSuspended() {
        return this.nbIsSuspended;
    }

    public NBUser nbIsSuspended(String nbIsSuspended) {
        this.setNbIsSuspended(nbIsSuspended);
        return this;
    }

    public void setNbIsSuspended(String nbIsSuspended) {
        this.nbIsSuspended = nbIsSuspended;
    }

    public String getNbIsBanished() {
        return this.nbIsBanished;
    }

    public NBUser nbIsBanished(String nbIsBanished) {
        this.setNbIsBanished(nbIsBanished);
        return this;
    }

    public void setNbIsBanished(String nbIsBanished) {
        this.nbIsBanished = nbIsBanished;
    }

    public String getNbLastUpdated() {
        return this.nbLastUpdated;
    }

    public NBUser nbLastUpdated(String nbLastUpdated) {
        this.setNbLastUpdated(nbLastUpdated);
        return this;
    }

    public void setNbLastUpdated(String nbLastUpdated) {
        this.nbLastUpdated = nbLastUpdated;
    }

    public String getNbLastUpdatedBy() {
        return this.nbLastUpdatedBy;
    }

    public NBUser nbLastUpdatedBy(String nbLastUpdatedBy) {
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
        if (!(o instanceof NBUser)) {
            return false;
        }
        return id != null && id.equals(((NBUser) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NBUser{" +
            "id=" + getId() +
            ", nbUserID='" + getNbUserID() + "'" +
            ", nbAuthType='" + getNbAuthType() + "'" +
            ", nbPasswordHash='" + getNbPasswordHash() + "'" +
            ", nbFirstName='" + getNbFirstName() + "'" +
            ", nbLastName='" + getNbLastName() + "'" +
            ", nbAddress='" + getNbAddress() + "'" +
            ", nbEmailId='" + getNbEmailId() + "'" +
            ", nbPhone='" + getNbPhone() + "'" +
            ", nbIsActive='" + getNbIsActive() + "'" +
            ", nbIsSuspended='" + getNbIsSuspended() + "'" +
            ", nbIsBanished='" + getNbIsBanished() + "'" +
            ", nbLastUpdated='" + getNbLastUpdated() + "'" +
            ", nbLastUpdatedBy='" + getNbLastUpdatedBy() + "'" +
            "}";
    }
}

package com.neutronbinary.infectolabs.gateway.domain;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A NBMap.
 */
@Table("nb_map")
public class NBMap implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Column("nb_id")
    private String nbID;

    @Column("nb_name")
    private String nbName;

    @Column("nb_owner")
    private String nbOwner;

    @Column("nb_owner_private_key")
    private String nbOwnerPrivateKey;

    @Column("nb_owner_public_key")
    private String nbOwnerPublicKey;

    @Column("nb_map_publish_method")
    private String nbMapPublishMethod;

    @Column("nb_subscription_date")
    private String nbSubscriptionDate;

    @Column("nb_subscription_last_date")
    private String nbSubscriptionLastDate;

    @Column("nb_last_updated")
    private String nbLastUpdated;

    @Column("nb_last_updated_by")
    private String nbLastUpdatedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public NBMap id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNbID() {
        return this.nbID;
    }

    public NBMap nbID(String nbID) {
        this.setNbID(nbID);
        return this;
    }

    public void setNbID(String nbID) {
        this.nbID = nbID;
    }

    public String getNbName() {
        return this.nbName;
    }

    public NBMap nbName(String nbName) {
        this.setNbName(nbName);
        return this;
    }

    public void setNbName(String nbName) {
        this.nbName = nbName;
    }

    public String getNbOwner() {
        return this.nbOwner;
    }

    public NBMap nbOwner(String nbOwner) {
        this.setNbOwner(nbOwner);
        return this;
    }

    public void setNbOwner(String nbOwner) {
        this.nbOwner = nbOwner;
    }

    public String getNbOwnerPrivateKey() {
        return this.nbOwnerPrivateKey;
    }

    public NBMap nbOwnerPrivateKey(String nbOwnerPrivateKey) {
        this.setNbOwnerPrivateKey(nbOwnerPrivateKey);
        return this;
    }

    public void setNbOwnerPrivateKey(String nbOwnerPrivateKey) {
        this.nbOwnerPrivateKey = nbOwnerPrivateKey;
    }

    public String getNbOwnerPublicKey() {
        return this.nbOwnerPublicKey;
    }

    public NBMap nbOwnerPublicKey(String nbOwnerPublicKey) {
        this.setNbOwnerPublicKey(nbOwnerPublicKey);
        return this;
    }

    public void setNbOwnerPublicKey(String nbOwnerPublicKey) {
        this.nbOwnerPublicKey = nbOwnerPublicKey;
    }

    public String getNbMapPublishMethod() {
        return this.nbMapPublishMethod;
    }

    public NBMap nbMapPublishMethod(String nbMapPublishMethod) {
        this.setNbMapPublishMethod(nbMapPublishMethod);
        return this;
    }

    public void setNbMapPublishMethod(String nbMapPublishMethod) {
        this.nbMapPublishMethod = nbMapPublishMethod;
    }

    public String getNbSubscriptionDate() {
        return this.nbSubscriptionDate;
    }

    public NBMap nbSubscriptionDate(String nbSubscriptionDate) {
        this.setNbSubscriptionDate(nbSubscriptionDate);
        return this;
    }

    public void setNbSubscriptionDate(String nbSubscriptionDate) {
        this.nbSubscriptionDate = nbSubscriptionDate;
    }

    public String getNbSubscriptionLastDate() {
        return this.nbSubscriptionLastDate;
    }

    public NBMap nbSubscriptionLastDate(String nbSubscriptionLastDate) {
        this.setNbSubscriptionLastDate(nbSubscriptionLastDate);
        return this;
    }

    public void setNbSubscriptionLastDate(String nbSubscriptionLastDate) {
        this.nbSubscriptionLastDate = nbSubscriptionLastDate;
    }

    public String getNbLastUpdated() {
        return this.nbLastUpdated;
    }

    public NBMap nbLastUpdated(String nbLastUpdated) {
        this.setNbLastUpdated(nbLastUpdated);
        return this;
    }

    public void setNbLastUpdated(String nbLastUpdated) {
        this.nbLastUpdated = nbLastUpdated;
    }

    public String getNbLastUpdatedBy() {
        return this.nbLastUpdatedBy;
    }

    public NBMap nbLastUpdatedBy(String nbLastUpdatedBy) {
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
        if (!(o instanceof NBMap)) {
            return false;
        }
        return id != null && id.equals(((NBMap) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NBMap{" +
            "id=" + getId() +
            ", nbID='" + getNbID() + "'" +
            ", nbName='" + getNbName() + "'" +
            ", nbOwner='" + getNbOwner() + "'" +
            ", nbOwnerPrivateKey='" + getNbOwnerPrivateKey() + "'" +
            ", nbOwnerPublicKey='" + getNbOwnerPublicKey() + "'" +
            ", nbMapPublishMethod='" + getNbMapPublishMethod() + "'" +
            ", nbSubscriptionDate='" + getNbSubscriptionDate() + "'" +
            ", nbSubscriptionLastDate='" + getNbSubscriptionLastDate() + "'" +
            ", nbLastUpdated='" + getNbLastUpdated() + "'" +
            ", nbLastUpdatedBy='" + getNbLastUpdatedBy() + "'" +
            "}";
    }
}

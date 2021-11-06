package com.neutronbinary.infectolabs.microservice.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A NBPalette.
 */
@Entity
@Table(name = "nb_palette")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class NBPalette implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nb_palette_id")
    private String nbPaletteID;

    @Column(name = "nb_palette_title")
    private String nbPaletteTitle;

    @Column(name = "nb_palette_type")
    private String nbPaletteType;

    @Column(name = "nb_palette_colors")
    private String nbPaletteColors;

    @Column(name = "nb_last_updated")
    private String nbLastUpdated;

    @Column(name = "nb_last_updated_by")
    private String nbLastUpdatedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public NBPalette id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNbPaletteID() {
        return this.nbPaletteID;
    }

    public NBPalette nbPaletteID(String nbPaletteID) {
        this.setNbPaletteID(nbPaletteID);
        return this;
    }

    public void setNbPaletteID(String nbPaletteID) {
        this.nbPaletteID = nbPaletteID;
    }

    public String getNbPaletteTitle() {
        return this.nbPaletteTitle;
    }

    public NBPalette nbPaletteTitle(String nbPaletteTitle) {
        this.setNbPaletteTitle(nbPaletteTitle);
        return this;
    }

    public void setNbPaletteTitle(String nbPaletteTitle) {
        this.nbPaletteTitle = nbPaletteTitle;
    }

    public String getNbPaletteType() {
        return this.nbPaletteType;
    }

    public NBPalette nbPaletteType(String nbPaletteType) {
        this.setNbPaletteType(nbPaletteType);
        return this;
    }

    public void setNbPaletteType(String nbPaletteType) {
        this.nbPaletteType = nbPaletteType;
    }

    public String getNbPaletteColors() {
        return this.nbPaletteColors;
    }

    public NBPalette nbPaletteColors(String nbPaletteColors) {
        this.setNbPaletteColors(nbPaletteColors);
        return this;
    }

    public void setNbPaletteColors(String nbPaletteColors) {
        this.nbPaletteColors = nbPaletteColors;
    }

    public String getNbLastUpdated() {
        return this.nbLastUpdated;
    }

    public NBPalette nbLastUpdated(String nbLastUpdated) {
        this.setNbLastUpdated(nbLastUpdated);
        return this;
    }

    public void setNbLastUpdated(String nbLastUpdated) {
        this.nbLastUpdated = nbLastUpdated;
    }

    public String getNbLastUpdatedBy() {
        return this.nbLastUpdatedBy;
    }

    public NBPalette nbLastUpdatedBy(String nbLastUpdatedBy) {
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
        if (!(o instanceof NBPalette)) {
            return false;
        }
        return id != null && id.equals(((NBPalette) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NBPalette{" +
            "id=" + getId() +
            ", nbPaletteID='" + getNbPaletteID() + "'" +
            ", nbPaletteTitle='" + getNbPaletteTitle() + "'" +
            ", nbPaletteType='" + getNbPaletteType() + "'" +
            ", nbPaletteColors='" + getNbPaletteColors() + "'" +
            ", nbLastUpdated='" + getNbLastUpdated() + "'" +
            ", nbLastUpdatedBy='" + getNbLastUpdatedBy() + "'" +
            "}";
    }
}

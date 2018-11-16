package com.lakon.rto.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A UnitType.
 */
@Entity
@Table(name = "unit_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UnitType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "jhi_type", nullable = false)
    private String type;

    @ManyToOne
    @JsonIgnoreProperties("unitTypes")
    private BaseUnit baseUnit;

    @OneToMany(mappedBy = "unitType")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UnitTypeItem> unitTypeItems = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public UnitType type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BaseUnit getBaseUnit() {
        return baseUnit;
    }

    public UnitType baseUnit(BaseUnit baseUnit) {
        this.baseUnit = baseUnit;
        return this;
    }

    public void setBaseUnit(BaseUnit baseUnit) {
        this.baseUnit = baseUnit;
    }

    public Set<UnitTypeItem> getUnitTypeItems() {
        return unitTypeItems;
    }

    public UnitType unitTypeItems(Set<UnitTypeItem> unitTypeItems) {
        this.unitTypeItems = unitTypeItems;
        return this;
    }

    public UnitType addUnitTypeItem(UnitTypeItem unitTypeItem) {
        this.unitTypeItems.add(unitTypeItem);
        unitTypeItem.setUnitType(this);
        return this;
    }

    public UnitType removeUnitTypeItem(UnitTypeItem unitTypeItem) {
        this.unitTypeItems.remove(unitTypeItem);
        unitTypeItem.setUnitType(null);
        return this;
    }

    public void setUnitTypeItems(Set<UnitTypeItem> unitTypeItems) {
        this.unitTypeItems = unitTypeItems;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UnitType unitType = (UnitType) o;
        if (unitType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), unitType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UnitType{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            "}";
    }
}

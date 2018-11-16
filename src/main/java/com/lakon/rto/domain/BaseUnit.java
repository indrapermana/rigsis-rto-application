package com.lakon.rto.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A BaseUnit.
 */
@Entity
@Table(name = "base_unit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BaseUnit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "description")
    private String description;

    @Column(name = "origin")
    private String origin;

    @OneToMany(mappedBy = "baseUnit")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UnitType> unitTypes = new HashSet<>();
    @OneToMany(mappedBy = "baseUnit")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DerivedUnit> derivedUnits = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public BaseUnit name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public BaseUnit symbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getDescription() {
        return description;
    }

    public BaseUnit description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrigin() {
        return origin;
    }

    public BaseUnit origin(String origin) {
        this.origin = origin;
        return this;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Set<UnitType> getUnitTypes() {
        return unitTypes;
    }

    public BaseUnit unitTypes(Set<UnitType> unitTypes) {
        this.unitTypes = unitTypes;
        return this;
    }

    public BaseUnit addUnitType(UnitType unitType) {
        this.unitTypes.add(unitType);
        unitType.setBaseUnit(this);
        return this;
    }

    public BaseUnit removeUnitType(UnitType unitType) {
        this.unitTypes.remove(unitType);
        unitType.setBaseUnit(null);
        return this;
    }

    public void setUnitTypes(Set<UnitType> unitTypes) {
        this.unitTypes = unitTypes;
    }

    public Set<DerivedUnit> getDerivedUnits() {
        return derivedUnits;
    }

    public BaseUnit derivedUnits(Set<DerivedUnit> derivedUnits) {
        this.derivedUnits = derivedUnits;
        return this;
    }

    public BaseUnit addDerivedUnit(DerivedUnit derivedUnit) {
        this.derivedUnits.add(derivedUnit);
        derivedUnit.setBaseUnit(this);
        return this;
    }

    public BaseUnit removeDerivedUnit(DerivedUnit derivedUnit) {
        this.derivedUnits.remove(derivedUnit);
        derivedUnit.setBaseUnit(null);
        return this;
    }

    public void setDerivedUnits(Set<DerivedUnit> derivedUnits) {
        this.derivedUnits = derivedUnits;
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
        BaseUnit baseUnit = (BaseUnit) o;
        if (baseUnit.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), baseUnit.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BaseUnit{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", symbol='" + getSymbol() + "'" +
            ", description='" + getDescription() + "'" +
            ", origin='" + getOrigin() + "'" +
            "}";
    }
}

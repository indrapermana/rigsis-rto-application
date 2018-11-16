package com.lakon.rto.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DerivedUnit.
 */
@Entity
@Table(name = "derived_unit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DerivedUnit implements Serializable {

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

    @Column(name = "conversion_type")
    private String conversionType;

    @Column(name = "factor")
    private Double factor;

    @Column(name = "origin")
    private String origin;

    @ManyToOne
    @JsonIgnoreProperties("derivedUnits")
    private BaseUnit baseUnit;

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

    public DerivedUnit name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public DerivedUnit symbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getConversionType() {
        return conversionType;
    }

    public DerivedUnit conversionType(String conversionType) {
        this.conversionType = conversionType;
        return this;
    }

    public void setConversionType(String conversionType) {
        this.conversionType = conversionType;
    }

    public Double getFactor() {
        return factor;
    }

    public DerivedUnit factor(Double factor) {
        this.factor = factor;
        return this;
    }

    public void setFactor(Double factor) {
        this.factor = factor;
    }

    public String getOrigin() {
        return origin;
    }

    public DerivedUnit origin(String origin) {
        this.origin = origin;
        return this;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public BaseUnit getBaseUnit() {
        return baseUnit;
    }

    public DerivedUnit baseUnit(BaseUnit baseUnit) {
        this.baseUnit = baseUnit;
        return this;
    }

    public void setBaseUnit(BaseUnit baseUnit) {
        this.baseUnit = baseUnit;
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
        DerivedUnit derivedUnit = (DerivedUnit) o;
        if (derivedUnit.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), derivedUnit.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DerivedUnit{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", symbol='" + getSymbol() + "'" +
            ", conversionType='" + getConversionType() + "'" +
            ", factor=" + getFactor() +
            ", origin='" + getOrigin() + "'" +
            "}";
    }
}

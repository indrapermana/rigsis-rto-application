package com.lakon.rto.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A UnitTypeItem.
 */
@Entity
@Table(name = "unit_type_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UnitTypeItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "conversion_type")
    private String conversionType;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "factor")
    private Double factor;

    @ManyToOne
    @JsonIgnoreProperties("unitTypeItems")
    private UnitType unitType;

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

    public UnitTypeItem name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConversionType() {
        return conversionType;
    }

    public UnitTypeItem conversionType(String conversionType) {
        this.conversionType = conversionType;
        return this;
    }

    public void setConversionType(String conversionType) {
        this.conversionType = conversionType;
    }

    public String getSymbol() {
        return symbol;
    }

    public UnitTypeItem symbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getFactor() {
        return factor;
    }

    public UnitTypeItem factor(Double factor) {
        this.factor = factor;
        return this;
    }

    public void setFactor(Double factor) {
        this.factor = factor;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public UnitTypeItem unitType(UnitType unitType) {
        this.unitType = unitType;
        return this;
    }

    public void setUnitType(UnitType unitType) {
        this.unitType = unitType;
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
        UnitTypeItem unitTypeItem = (UnitTypeItem) o;
        if (unitTypeItem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), unitTypeItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UnitTypeItem{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", conversionType='" + getConversionType() + "'" +
            ", symbol='" + getSymbol() + "'" +
            ", factor=" + getFactor() +
            "}";
    }
}

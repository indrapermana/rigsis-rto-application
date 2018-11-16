package com.lakon.rto.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A RecordItem.
 */
@Entity
@Table(name = "record_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RecordItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_number")
    private Integer number;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "mnemonic", nullable = false)
    private String mnemonic;

    @Column(name = "special_case")
    private String specialCase;

    @NotNull
    @Column(name = "unit_type", nullable = false)
    private String unitType;

    @NotNull
    @Column(name = "unit", nullable = false)
    private String unit;

    @NotNull
    @Column(name = "data_type", nullable = false)
    private String dataType;

    @Column(name = "null_value")
    private Integer nullValue;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JsonIgnoreProperties("recordItems")
    private RecordType recordType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public RecordItem number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public RecordItem name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMnemonic() {
        return mnemonic;
    }

    public RecordItem mnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
        return this;
    }

    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }

    public String getSpecialCase() {
        return specialCase;
    }

    public RecordItem specialCase(String specialCase) {
        this.specialCase = specialCase;
        return this;
    }

    public void setSpecialCase(String specialCase) {
        this.specialCase = specialCase;
    }

    public String getUnitType() {
        return unitType;
    }

    public RecordItem unitType(String unitType) {
        this.unitType = unitType;
        return this;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getUnit() {
        return unit;
    }

    public RecordItem unit(String unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDataType() {
        return dataType;
    }

    public RecordItem dataType(String dataType) {
        this.dataType = dataType;
        return this;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Integer getNullValue() {
        return nullValue;
    }

    public RecordItem nullValue(Integer nullValue) {
        this.nullValue = nullValue;
        return this;
    }

    public void setNullValue(Integer nullValue) {
        this.nullValue = nullValue;
    }

    public String getDescription() {
        return description;
    }

    public RecordItem description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RecordType getRecordType() {
        return recordType;
    }

    public RecordItem recordType(RecordType recordType) {
        this.recordType = recordType;
        return this;
    }

    public void setRecordType(RecordType recordType) {
        this.recordType = recordType;
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
        RecordItem recordItem = (RecordItem) o;
        if (recordItem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), recordItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RecordItem{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", name='" + getName() + "'" +
            ", mnemonic='" + getMnemonic() + "'" +
            ", specialCase='" + getSpecialCase() + "'" +
            ", unitType='" + getUnitType() + "'" +
            ", unit='" + getUnit() + "'" +
            ", dataType='" + getDataType() + "'" +
            ", nullValue=" + getNullValue() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}

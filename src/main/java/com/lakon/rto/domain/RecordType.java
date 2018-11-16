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
 * A RecordType.
 */
@Entity
@Table(name = "record_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RecordType implements Serializable {

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

    @Column(name = "jhi_trigger")
    private String trigger;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "recordType")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RecordItem> recordItems = new HashSet<>();
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

    public RecordType number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public RecordType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrigger() {
        return trigger;
    }

    public RecordType trigger(String trigger) {
        this.trigger = trigger;
        return this;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    public String getDescription() {
        return description;
    }

    public RecordType description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<RecordItem> getRecordItems() {
        return recordItems;
    }

    public RecordType recordItems(Set<RecordItem> recordItems) {
        this.recordItems = recordItems;
        return this;
    }

    public RecordType addRecordItem(RecordItem recordItem) {
        this.recordItems.add(recordItem);
        recordItem.setRecordType(this);
        return this;
    }

    public RecordType removeRecordItem(RecordItem recordItem) {
        this.recordItems.remove(recordItem);
        recordItem.setRecordType(null);
        return this;
    }

    public void setRecordItems(Set<RecordItem> recordItems) {
        this.recordItems = recordItems;
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
        RecordType recordType = (RecordType) o;
        if (recordType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), recordType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RecordType{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", name='" + getName() + "'" +
            ", trigger='" + getTrigger() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}

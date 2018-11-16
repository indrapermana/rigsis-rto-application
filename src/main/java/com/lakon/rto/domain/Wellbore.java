package com.lakon.rto.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.lakon.rto.domain.enumeration.Status;

import com.lakon.rto.domain.enumeration.BooleanType;

/**
 * A Wellbore.
 */
@Entity
@Table(name = "wellbore")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Wellbore implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "well_name")
    private String wellName;

    @Column(name = "parent_wellbore_name")
    private String parentWellboreName;

    @Column(name = "goverment_number")
    private String govermentNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "purpose")
    private String purpose;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "shape")
    private String shape;

    @Column(name = "day_target")
    private Integer dayTarget;

    @Column(name = "kick_off_date")
    private ZonedDateTime kickOffDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "achieved_td")
    private BooleanType achievedTD;

    @Column(name = "md_current")
    private Double mdCurrent;

    @Column(name = "tvd_current")
    private Double tvdCurrent;

    @Column(name = "md_bit_current")
    private Double mdBitCurrent;

    @Column(name = "tvd_bit_current")
    private Double tvdBitCurrent;

    @Column(name = "md_kick_off")
    private Double mdKickOff;

    @Column(name = "tvd_kick_off")
    private Double tvdKickOff;

    @Column(name = "md_planned")
    private Double mdPlanned;

    @Column(name = "tvd_planned")
    private Double tvdPlanned;

    @Column(name = "md_sub_sea")
    private Double mdSubSea;

    @Column(name = "tvd_sub_sea")
    private Double tvdSubSea;

    @ManyToOne
    @JsonIgnoreProperties("wellbores")
    private Well well;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Wellbore parent;

    @OneToMany(mappedBy = "wellbore")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Rig> rigs = new HashSet<>();
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

    public Wellbore name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWellName() {
        return wellName;
    }

    public Wellbore wellName(String wellName) {
        this.wellName = wellName;
        return this;
    }

    public void setWellName(String wellName) {
        this.wellName = wellName;
    }

    public String getParentWellboreName() {
        return parentWellboreName;
    }

    public Wellbore parentWellboreName(String parentWellboreName) {
        this.parentWellboreName = parentWellboreName;
        return this;
    }

    public void setParentWellboreName(String parentWellboreName) {
        this.parentWellboreName = parentWellboreName;
    }

    public String getGovermentNumber() {
        return govermentNumber;
    }

    public Wellbore govermentNumber(String govermentNumber) {
        this.govermentNumber = govermentNumber;
        return this;
    }

    public void setGovermentNumber(String govermentNumber) {
        this.govermentNumber = govermentNumber;
    }

    public Status getStatus() {
        return status;
    }

    public Wellbore status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getPurpose() {
        return purpose;
    }

    public Wellbore purpose(String purpose) {
        this.purpose = purpose;
        return this;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getType() {
        return type;
    }

    public Wellbore type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getShape() {
        return shape;
    }

    public Wellbore shape(String shape) {
        this.shape = shape;
        return this;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public Integer getDayTarget() {
        return dayTarget;
    }

    public Wellbore dayTarget(Integer dayTarget) {
        this.dayTarget = dayTarget;
        return this;
    }

    public void setDayTarget(Integer dayTarget) {
        this.dayTarget = dayTarget;
    }

    public ZonedDateTime getKickOffDate() {
        return kickOffDate;
    }

    public Wellbore kickOffDate(ZonedDateTime kickOffDate) {
        this.kickOffDate = kickOffDate;
        return this;
    }

    public void setKickOffDate(ZonedDateTime kickOffDate) {
        this.kickOffDate = kickOffDate;
    }

    public BooleanType getAchievedTD() {
        return achievedTD;
    }

    public Wellbore achievedTD(BooleanType achievedTD) {
        this.achievedTD = achievedTD;
        return this;
    }

    public void setAchievedTD(BooleanType achievedTD) {
        this.achievedTD = achievedTD;
    }

    public Double getMdCurrent() {
        return mdCurrent;
    }

    public Wellbore mdCurrent(Double mdCurrent) {
        this.mdCurrent = mdCurrent;
        return this;
    }

    public void setMdCurrent(Double mdCurrent) {
        this.mdCurrent = mdCurrent;
    }

    public Double getTvdCurrent() {
        return tvdCurrent;
    }

    public Wellbore tvdCurrent(Double tvdCurrent) {
        this.tvdCurrent = tvdCurrent;
        return this;
    }

    public void setTvdCurrent(Double tvdCurrent) {
        this.tvdCurrent = tvdCurrent;
    }

    public Double getMdBitCurrent() {
        return mdBitCurrent;
    }

    public Wellbore mdBitCurrent(Double mdBitCurrent) {
        this.mdBitCurrent = mdBitCurrent;
        return this;
    }

    public void setMdBitCurrent(Double mdBitCurrent) {
        this.mdBitCurrent = mdBitCurrent;
    }

    public Double getTvdBitCurrent() {
        return tvdBitCurrent;
    }

    public Wellbore tvdBitCurrent(Double tvdBitCurrent) {
        this.tvdBitCurrent = tvdBitCurrent;
        return this;
    }

    public void setTvdBitCurrent(Double tvdBitCurrent) {
        this.tvdBitCurrent = tvdBitCurrent;
    }

    public Double getMdKickOff() {
        return mdKickOff;
    }

    public Wellbore mdKickOff(Double mdKickOff) {
        this.mdKickOff = mdKickOff;
        return this;
    }

    public void setMdKickOff(Double mdKickOff) {
        this.mdKickOff = mdKickOff;
    }

    public Double getTvdKickOff() {
        return tvdKickOff;
    }

    public Wellbore tvdKickOff(Double tvdKickOff) {
        this.tvdKickOff = tvdKickOff;
        return this;
    }

    public void setTvdKickOff(Double tvdKickOff) {
        this.tvdKickOff = tvdKickOff;
    }

    public Double getMdPlanned() {
        return mdPlanned;
    }

    public Wellbore mdPlanned(Double mdPlanned) {
        this.mdPlanned = mdPlanned;
        return this;
    }

    public void setMdPlanned(Double mdPlanned) {
        this.mdPlanned = mdPlanned;
    }

    public Double getTvdPlanned() {
        return tvdPlanned;
    }

    public Wellbore tvdPlanned(Double tvdPlanned) {
        this.tvdPlanned = tvdPlanned;
        return this;
    }

    public void setTvdPlanned(Double tvdPlanned) {
        this.tvdPlanned = tvdPlanned;
    }

    public Double getMdSubSea() {
        return mdSubSea;
    }

    public Wellbore mdSubSea(Double mdSubSea) {
        this.mdSubSea = mdSubSea;
        return this;
    }

    public void setMdSubSea(Double mdSubSea) {
        this.mdSubSea = mdSubSea;
    }

    public Double getTvdSubSea() {
        return tvdSubSea;
    }

    public Wellbore tvdSubSea(Double tvdSubSea) {
        this.tvdSubSea = tvdSubSea;
        return this;
    }

    public void setTvdSubSea(Double tvdSubSea) {
        this.tvdSubSea = tvdSubSea;
    }

    public Well getWell() {
        return well;
    }

    public Wellbore well(Well well) {
        this.well = well;
        return this;
    }

    public void setWell(Well well) {
        this.well = well;
    }

    public Wellbore getParent() {
        return parent;
    }

    public Wellbore parent(Wellbore wellbore) {
        this.parent = wellbore;
        return this;
    }

    public void setParent(Wellbore wellbore) {
        this.parent = wellbore;
    }

    public Set<Rig> getRigs() {
        return rigs;
    }

    public Wellbore rigs(Set<Rig> rigs) {
        this.rigs = rigs;
        return this;
    }

    public Wellbore addRig(Rig rig) {
        this.rigs.add(rig);
        rig.setWellbore(this);
        return this;
    }

    public Wellbore removeRig(Rig rig) {
        this.rigs.remove(rig);
        rig.setWellbore(null);
        return this;
    }

    public void setRigs(Set<Rig> rigs) {
        this.rigs = rigs;
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
        Wellbore wellbore = (Wellbore) o;
        if (wellbore.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), wellbore.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Wellbore{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", wellName='" + getWellName() + "'" +
            ", parentWellboreName='" + getParentWellboreName() + "'" +
            ", govermentNumber='" + getGovermentNumber() + "'" +
            ", status='" + getStatus() + "'" +
            ", purpose='" + getPurpose() + "'" +
            ", type='" + getType() + "'" +
            ", shape='" + getShape() + "'" +
            ", dayTarget=" + getDayTarget() +
            ", kickOffDate='" + getKickOffDate() + "'" +
            ", achievedTD='" + getAchievedTD() + "'" +
            ", mdCurrent=" + getMdCurrent() +
            ", tvdCurrent=" + getTvdCurrent() +
            ", mdBitCurrent=" + getMdBitCurrent() +
            ", tvdBitCurrent=" + getTvdBitCurrent() +
            ", mdKickOff=" + getMdKickOff() +
            ", tvdKickOff=" + getTvdKickOff() +
            ", mdPlanned=" + getMdPlanned() +
            ", tvdPlanned=" + getTvdPlanned() +
            ", mdSubSea=" + getMdSubSea() +
            ", tvdSubSea=" + getTvdSubSea() +
            "}";
    }
}

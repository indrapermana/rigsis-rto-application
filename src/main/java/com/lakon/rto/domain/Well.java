package com.lakon.rto.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

/**
 * A Well.
 */
@Entity
@Table(name = "well")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Well implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "legal_name")
    private String legalName;

    @Column(name = "license_date")
    private ZonedDateTime licenseDate;

    @Column(name = "license_number")
    private String licenseNumber;

    @Column(name = "goverment_number")
    private String govermentNumber;

    @Column(name = "api_number")
    private String apiNumber;

    @Column(name = "country")
    private String country;

    @Column(name = "jhi_block")
    private String block;

    @Column(name = "field")
    private String field;

    @Column(name = "district")
    private String district;

    @Column(name = "county")
    private String county;

    @Column(name = "state")
    private String state;

    @Column(name = "region")
    private String region;

    @Column(name = "operator")
    private String operator;

    @Column(name = "operator_interest")
    private Double operatorInterest;

    @Column(name = "operator_division")
    private String operatorDivision;

    @Column(name = "time_zone")
    private String timeZone;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "purpose")
    private String purpose;

    @Column(name = "direction")
    private String direction;

    @Column(name = "fluid")
    private String fluid;

    @Column(name = "date_time_spud")
    private ZonedDateTime dateTimeSpud;

    @Column(name = "date_time_pa")
    private ZonedDateTime dateTimePA;

    @Column(name = "head_elevation")
    private Double headElevation;

    @Column(name = "ground_elevation")
    private Double groundElevation;

    @Column(name = "water_depth")
    private Double waterDepth;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @OneToMany(mappedBy = "well")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Wellbore> wellbores = new HashSet<>();
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

    public Well name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLegalName() {
        return legalName;
    }

    public Well legalName(String legalName) {
        this.legalName = legalName;
        return this;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public ZonedDateTime getLicenseDate() {
        return licenseDate;
    }

    public Well licenseDate(ZonedDateTime licenseDate) {
        this.licenseDate = licenseDate;
        return this;
    }

    public void setLicenseDate(ZonedDateTime licenseDate) {
        this.licenseDate = licenseDate;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public Well licenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
        return this;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getGovermentNumber() {
        return govermentNumber;
    }

    public Well govermentNumber(String govermentNumber) {
        this.govermentNumber = govermentNumber;
        return this;
    }

    public void setGovermentNumber(String govermentNumber) {
        this.govermentNumber = govermentNumber;
    }

    public String getApiNumber() {
        return apiNumber;
    }

    public Well apiNumber(String apiNumber) {
        this.apiNumber = apiNumber;
        return this;
    }

    public void setApiNumber(String apiNumber) {
        this.apiNumber = apiNumber;
    }

    public String getCountry() {
        return country;
    }

    public Well country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBlock() {
        return block;
    }

    public Well block(String block) {
        this.block = block;
        return this;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getField() {
        return field;
    }

    public Well field(String field) {
        this.field = field;
        return this;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDistrict() {
        return district;
    }

    public Well district(String district) {
        this.district = district;
        return this;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCounty() {
        return county;
    }

    public Well county(String county) {
        this.county = county;
        return this;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getState() {
        return state;
    }

    public Well state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRegion() {
        return region;
    }

    public Well region(String region) {
        this.region = region;
        return this;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getOperator() {
        return operator;
    }

    public Well operator(String operator) {
        this.operator = operator;
        return this;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Double getOperatorInterest() {
        return operatorInterest;
    }

    public Well operatorInterest(Double operatorInterest) {
        this.operatorInterest = operatorInterest;
        return this;
    }

    public void setOperatorInterest(Double operatorInterest) {
        this.operatorInterest = operatorInterest;
    }

    public String getOperatorDivision() {
        return operatorDivision;
    }

    public Well operatorDivision(String operatorDivision) {
        this.operatorDivision = operatorDivision;
        return this;
    }

    public void setOperatorDivision(String operatorDivision) {
        this.operatorDivision = operatorDivision;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public Well timeZone(String timeZone) {
        this.timeZone = timeZone;
        return this;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public Status getStatus() {
        return status;
    }

    public Well status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getPurpose() {
        return purpose;
    }

    public Well purpose(String purpose) {
        this.purpose = purpose;
        return this;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getDirection() {
        return direction;
    }

    public Well direction(String direction) {
        this.direction = direction;
        return this;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getFluid() {
        return fluid;
    }

    public Well fluid(String fluid) {
        this.fluid = fluid;
        return this;
    }

    public void setFluid(String fluid) {
        this.fluid = fluid;
    }

    public ZonedDateTime getDateTimeSpud() {
        return dateTimeSpud;
    }

    public Well dateTimeSpud(ZonedDateTime dateTimeSpud) {
        this.dateTimeSpud = dateTimeSpud;
        return this;
    }

    public void setDateTimeSpud(ZonedDateTime dateTimeSpud) {
        this.dateTimeSpud = dateTimeSpud;
    }

    public ZonedDateTime getDateTimePA() {
        return dateTimePA;
    }

    public Well dateTimePA(ZonedDateTime dateTimePA) {
        this.dateTimePA = dateTimePA;
        return this;
    }

    public void setDateTimePA(ZonedDateTime dateTimePA) {
        this.dateTimePA = dateTimePA;
    }

    public Double getHeadElevation() {
        return headElevation;
    }

    public Well headElevation(Double headElevation) {
        this.headElevation = headElevation;
        return this;
    }

    public void setHeadElevation(Double headElevation) {
        this.headElevation = headElevation;
    }

    public Double getGroundElevation() {
        return groundElevation;
    }

    public Well groundElevation(Double groundElevation) {
        this.groundElevation = groundElevation;
        return this;
    }

    public void setGroundElevation(Double groundElevation) {
        this.groundElevation = groundElevation;
    }

    public Double getWaterDepth() {
        return waterDepth;
    }

    public Well waterDepth(Double waterDepth) {
        this.waterDepth = waterDepth;
        return this;
    }

    public void setWaterDepth(Double waterDepth) {
        this.waterDepth = waterDepth;
    }

    public String getLatitude() {
        return latitude;
    }

    public Well latitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public Well longitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Set<Wellbore> getWellbores() {
        return wellbores;
    }

    public Well wellbores(Set<Wellbore> wellbores) {
        this.wellbores = wellbores;
        return this;
    }

    public Well addWellbore(Wellbore wellbore) {
        this.wellbores.add(wellbore);
        wellbore.setWell(this);
        return this;
    }

    public Well removeWellbore(Wellbore wellbore) {
        this.wellbores.remove(wellbore);
        wellbore.setWell(null);
        return this;
    }

    public void setWellbores(Set<Wellbore> wellbores) {
        this.wellbores = wellbores;
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
        Well well = (Well) o;
        if (well.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), well.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Well{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", legalName='" + getLegalName() + "'" +
            ", licenseDate='" + getLicenseDate() + "'" +
            ", licenseNumber='" + getLicenseNumber() + "'" +
            ", govermentNumber='" + getGovermentNumber() + "'" +
            ", apiNumber='" + getApiNumber() + "'" +
            ", country='" + getCountry() + "'" +
            ", block='" + getBlock() + "'" +
            ", field='" + getField() + "'" +
            ", district='" + getDistrict() + "'" +
            ", county='" + getCounty() + "'" +
            ", state='" + getState() + "'" +
            ", region='" + getRegion() + "'" +
            ", operator='" + getOperator() + "'" +
            ", operatorInterest=" + getOperatorInterest() +
            ", operatorDivision='" + getOperatorDivision() + "'" +
            ", timeZone='" + getTimeZone() + "'" +
            ", status='" + getStatus() + "'" +
            ", purpose='" + getPurpose() + "'" +
            ", direction='" + getDirection() + "'" +
            ", fluid='" + getFluid() + "'" +
            ", dateTimeSpud='" + getDateTimeSpud() + "'" +
            ", dateTimePA='" + getDateTimePA() + "'" +
            ", headElevation=" + getHeadElevation() +
            ", groundElevation=" + getGroundElevation() +
            ", waterDepth=" + getWaterDepth() +
            ", latitude='" + getLatitude() + "'" +
            ", longitude='" + getLongitude() + "'" +
            "}";
    }
}

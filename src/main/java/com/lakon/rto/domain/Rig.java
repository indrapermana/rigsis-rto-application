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

import com.lakon.rto.domain.enumeration.BooleanType;

/**
 * A Rig.
 */
@Entity
@Table(name = "rig")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Rig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "offshore")
    private BooleanType offshore;

    @Column(name = "owner")
    private String owner;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "rig_class")
    private String rigClass;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "year_service")
    private Integer yearService;

    @Column(name = "approvals")
    private String approvals;

    @Column(name = "registration")
    private String registration;

    @Column(name = "contact")
    private String contact;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "fax")
    private String fax;

    @Column(name = "drill_depth")
    private Double drillDepth;

    @Column(name = "water_depth")
    private Double waterDepth;

    @Column(name = "air_gap")
    private Double airGap;

    @Column(name = "start_date")
    private ZonedDateTime startDate;

    @Column(name = "end_date")
    private ZonedDateTime endDate;

    @ManyToOne
    @JsonIgnoreProperties("rigs")
    private Wellbore wellbore;

    @ManyToMany(mappedBy = "rigs")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Job> jobs = new HashSet<>();

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

    public Rig name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BooleanType getOffshore() {
        return offshore;
    }

    public Rig offshore(BooleanType offshore) {
        this.offshore = offshore;
        return this;
    }

    public void setOffshore(BooleanType offshore) {
        this.offshore = offshore;
    }

    public String getOwner() {
        return owner;
    }

    public Rig owner(String owner) {
        this.owner = owner;
        return this;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getType() {
        return type;
    }

    public Rig type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRigClass() {
        return rigClass;
    }

    public Rig rigClass(String rigClass) {
        this.rigClass = rigClass;
        return this;
    }

    public void setRigClass(String rigClass) {
        this.rigClass = rigClass;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public Rig manufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Integer getYearService() {
        return yearService;
    }

    public Rig yearService(Integer yearService) {
        this.yearService = yearService;
        return this;
    }

    public void setYearService(Integer yearService) {
        this.yearService = yearService;
    }

    public String getApprovals() {
        return approvals;
    }

    public Rig approvals(String approvals) {
        this.approvals = approvals;
        return this;
    }

    public void setApprovals(String approvals) {
        this.approvals = approvals;
    }

    public String getRegistration() {
        return registration;
    }

    public Rig registration(String registration) {
        this.registration = registration;
        return this;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getContact() {
        return contact;
    }

    public Rig contact(String contact) {
        this.contact = contact;
        return this;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public Rig email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public Rig phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public Rig fax(String fax) {
        this.fax = fax;
        return this;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Double getDrillDepth() {
        return drillDepth;
    }

    public Rig drillDepth(Double drillDepth) {
        this.drillDepth = drillDepth;
        return this;
    }

    public void setDrillDepth(Double drillDepth) {
        this.drillDepth = drillDepth;
    }

    public Double getWaterDepth() {
        return waterDepth;
    }

    public Rig waterDepth(Double waterDepth) {
        this.waterDepth = waterDepth;
        return this;
    }

    public void setWaterDepth(Double waterDepth) {
        this.waterDepth = waterDepth;
    }

    public Double getAirGap() {
        return airGap;
    }

    public Rig airGap(Double airGap) {
        this.airGap = airGap;
        return this;
    }

    public void setAirGap(Double airGap) {
        this.airGap = airGap;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public Rig startDate(ZonedDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public Rig endDate(ZonedDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public Wellbore getWellbore() {
        return wellbore;
    }

    public Rig wellbore(Wellbore wellbore) {
        this.wellbore = wellbore;
        return this;
    }

    public void setWellbore(Wellbore wellbore) {
        this.wellbore = wellbore;
    }

    public Set<Job> getJobs() {
        return jobs;
    }

    public Rig jobs(Set<Job> jobs) {
        this.jobs = jobs;
        return this;
    }

    public Rig addJob(Job job) {
        this.jobs.add(job);
        job.getRigs().add(this);
        return this;
    }

    public Rig removeJob(Job job) {
        this.jobs.remove(job);
        job.getRigs().remove(this);
        return this;
    }

    public void setJobs(Set<Job> jobs) {
        this.jobs = jobs;
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
        Rig rig = (Rig) o;
        if (rig.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rig.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Rig{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", offshore='" + getOffshore() + "'" +
            ", owner='" + getOwner() + "'" +
            ", type='" + getType() + "'" +
            ", rigClass='" + getRigClass() + "'" +
            ", manufacturer='" + getManufacturer() + "'" +
            ", yearService=" + getYearService() +
            ", approvals='" + getApprovals() + "'" +
            ", registration='" + getRegistration() + "'" +
            ", contact='" + getContact() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", fax='" + getFax() + "'" +
            ", drillDepth=" + getDrillDepth() +
            ", waterDepth=" + getWaterDepth() +
            ", airGap=" + getAirGap() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            "}";
    }
}

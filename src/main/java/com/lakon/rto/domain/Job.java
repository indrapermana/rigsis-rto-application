package com.lakon.rto.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Job.
 */
@Entity
@Table(name = "job")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Job implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_type")
    private String type;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "job_rig",
               joinColumns = @JoinColumn(name = "jobs_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "rigs_id", referencedColumnName = "id"))
    private Set<Rig> rigs = new HashSet<>();

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

    public Job type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Rig> getRigs() {
        return rigs;
    }

    public Job rigs(Set<Rig> rigs) {
        this.rigs = rigs;
        return this;
    }

    public Job addRig(Rig rig) {
        this.rigs.add(rig);
        rig.getJobs().add(this);
        return this;
    }

    public Job removeRig(Rig rig) {
        this.rigs.remove(rig);
        rig.getJobs().remove(this);
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
        Job job = (Job) o;
        if (job.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), job.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Job{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            "}";
    }
}

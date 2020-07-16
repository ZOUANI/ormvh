package com.ormvah.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

import com.ormvah.domain.enumeration.Status;

/**
 * A Task.
 */
@Entity
@Table(name = "task")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "assigned_at")
    private Instant assignedAt;

    @Column(name = "processed_at")
    private Instant processedAt;

    @Column(name = "observation")
    private String observation;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "delai")
    private Double delai;

    @Column(name = "relance")
    private Double relance;

    @Column(name = "accuse")
    private Boolean accuse;

    @Column(name = "reponse")
    private Boolean reponse;

    @Column(name = "date_accuse")
    private LocalDate dateAccuse;

    @Column(name = "date_reponse")
    private LocalDate dateReponse;

    @ManyToOne
    @JsonIgnoreProperties(value = "tasks", allowSetters = true)
    private User assigne;

    @ManyToOne
    @JsonIgnoreProperties(value = "tasks", allowSetters = true)
    private Courrier courrier;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Task title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Task description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Task createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Task updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getAssignedAt() {
        return assignedAt;
    }

    public Task assignedAt(Instant assignedAt) {
        this.assignedAt = assignedAt;
        return this;
    }

    public void setAssignedAt(Instant assignedAt) {
        this.assignedAt = assignedAt;
    }

    public Instant getProcessedAt() {
        return processedAt;
    }

    public Task processedAt(Instant processedAt) {
        this.processedAt = processedAt;
        return this;
    }

    public void setProcessedAt(Instant processedAt) {
        this.processedAt = processedAt;
    }

    public String getObservation() {
        return observation;
    }

    public Task observation(String observation) {
        this.observation = observation;
        return this;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Status getStatus() {
        return status;
    }

    public Task status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Task createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public Task updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Double getDelai() {
        return delai;
    }

    public Task delai(Double delai) {
        this.delai = delai;
        return this;
    }

    public void setDelai(Double delai) {
        this.delai = delai;
    }

    public Double getRelance() {
        return relance;
    }

    public Task relance(Double relance) {
        this.relance = relance;
        return this;
    }

    public void setRelance(Double relance) {
        this.relance = relance;
    }

    public Boolean isAccuse() {
        return accuse;
    }

    public Task accuse(Boolean accuse) {
        this.accuse = accuse;
        return this;
    }

    public void setAccuse(Boolean accuse) {
        this.accuse = accuse;
    }

    public Boolean isReponse() {
        return reponse;
    }

    public Task reponse(Boolean reponse) {
        this.reponse = reponse;
        return this;
    }

    public void setReponse(Boolean reponse) {
        this.reponse = reponse;
    }

    public LocalDate getDateAccuse() {
        return dateAccuse;
    }

    public Task dateAccuse(LocalDate dateAccuse) {
        this.dateAccuse = dateAccuse;
        return this;
    }

    public void setDateAccuse(LocalDate dateAccuse) {
        this.dateAccuse = dateAccuse;
    }

    public LocalDate getDateReponse() {
        return dateReponse;
    }

    public Task dateReponse(LocalDate dateReponse) {
        this.dateReponse = dateReponse;
        return this;
    }

    public void setDateReponse(LocalDate dateReponse) {
        this.dateReponse = dateReponse;
    }

    public User getAssigne() {
        return assigne;
    }

    public Task assigne(User user) {
        this.assigne = user;
        return this;
    }

    public void setAssigne(User user) {
        this.assigne = user;
    }

    public Courrier getCourrier() {
        return courrier;
    }

    public Task courrier(Courrier courrier) {
        this.courrier = courrier;
        return this;
    }

    public void setCourrier(Courrier courrier) {
        this.courrier = courrier;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Task)) {
            return false;
        }
        return id != null && id.equals(((Task) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Task{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", assignedAt='" + getAssignedAt() + "'" +
            ", processedAt='" + getProcessedAt() + "'" +
            ", observation='" + getObservation() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", delai=" + getDelai() +
            ", relance=" + getRelance() +
            ", accuse='" + isAccuse() + "'" +
            ", reponse='" + isReponse() + "'" +
            ", dateAccuse='" + getDateAccuse() + "'" +
            ", dateReponse='" + getDateReponse() + "'" +
            "}";
    }
}

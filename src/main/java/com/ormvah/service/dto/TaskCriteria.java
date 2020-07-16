package com.ormvah.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.ormvah.domain.enumeration.Status;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.ormvah.domain.Task} entity. This class is used
 * in {@link com.ormvah.web.rest.TaskResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tasks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TaskCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Status
     */
    public static class StatusFilter extends Filter<Status> {

        public StatusFilter() {
        }

        public StatusFilter(StatusFilter filter) {
            super(filter);
        }

        @Override
        public StatusFilter copy() {
            return new StatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private StringFilter description;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private InstantFilter assignedAt;

    private InstantFilter processedAt;

    private StringFilter observation;

    private StatusFilter status;

    private StringFilter createdBy;

    private StringFilter updatedBy;

    private DoubleFilter delai;

    private DoubleFilter relance;

    private BooleanFilter accuse;

    private BooleanFilter reponse;

    private LocalDateFilter dateAccuse;

    private LocalDateFilter dateReponse;

    private LongFilter assigneId;

    private LongFilter courrierId;

    public TaskCriteria() {
    }

    public TaskCriteria(TaskCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.assignedAt = other.assignedAt == null ? null : other.assignedAt.copy();
        this.processedAt = other.processedAt == null ? null : other.processedAt.copy();
        this.observation = other.observation == null ? null : other.observation.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.updatedBy = other.updatedBy == null ? null : other.updatedBy.copy();
        this.delai = other.delai == null ? null : other.delai.copy();
        this.relance = other.relance == null ? null : other.relance.copy();
        this.accuse = other.accuse == null ? null : other.accuse.copy();
        this.reponse = other.reponse == null ? null : other.reponse.copy();
        this.dateAccuse = other.dateAccuse == null ? null : other.dateAccuse.copy();
        this.dateReponse = other.dateReponse == null ? null : other.dateReponse.copy();
        this.assigneId = other.assigneId == null ? null : other.assigneId.copy();
        this.courrierId = other.courrierId == null ? null : other.courrierId.copy();
    }

    @Override
    public TaskCriteria copy() {
        return new TaskCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitle() {
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public InstantFilter getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(InstantFilter createdAt) {
        this.createdAt = createdAt;
    }

    public InstantFilter getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(InstantFilter updatedAt) {
        this.updatedAt = updatedAt;
    }

    public InstantFilter getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(InstantFilter assignedAt) {
        this.assignedAt = assignedAt;
    }

    public InstantFilter getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(InstantFilter processedAt) {
        this.processedAt = processedAt;
    }

    public StringFilter getObservation() {
        return observation;
    }

    public void setObservation(StringFilter observation) {
        this.observation = observation;
    }

    public StatusFilter getStatus() {
        return status;
    }

    public void setStatus(StatusFilter status) {
        this.status = status;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public StringFilter getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(StringFilter updatedBy) {
        this.updatedBy = updatedBy;
    }

    public DoubleFilter getDelai() {
        return delai;
    }

    public void setDelai(DoubleFilter delai) {
        this.delai = delai;
    }

    public DoubleFilter getRelance() {
        return relance;
    }

    public void setRelance(DoubleFilter relance) {
        this.relance = relance;
    }

    public BooleanFilter getAccuse() {
        return accuse;
    }

    public void setAccuse(BooleanFilter accuse) {
        this.accuse = accuse;
    }

    public BooleanFilter getReponse() {
        return reponse;
    }

    public void setReponse(BooleanFilter reponse) {
        this.reponse = reponse;
    }

    public LocalDateFilter getDateAccuse() {
        return dateAccuse;
    }

    public void setDateAccuse(LocalDateFilter dateAccuse) {
        this.dateAccuse = dateAccuse;
    }

    public LocalDateFilter getDateReponse() {
        return dateReponse;
    }

    public void setDateReponse(LocalDateFilter dateReponse) {
        this.dateReponse = dateReponse;
    }

    public LongFilter getAssigneId() {
        return assigneId;
    }

    public void setAssigneId(LongFilter assigneId) {
        this.assigneId = assigneId;
    }

    public LongFilter getCourrierId() {
        return courrierId;
    }

    public void setCourrierId(LongFilter courrierId) {
        this.courrierId = courrierId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TaskCriteria that = (TaskCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(description, that.description) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(assignedAt, that.assignedAt) &&
            Objects.equals(processedAt, that.processedAt) &&
            Objects.equals(observation, that.observation) &&
            Objects.equals(status, that.status) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(delai, that.delai) &&
            Objects.equals(relance, that.relance) &&
            Objects.equals(accuse, that.accuse) &&
            Objects.equals(reponse, that.reponse) &&
            Objects.equals(dateAccuse, that.dateAccuse) &&
            Objects.equals(dateReponse, that.dateReponse) &&
            Objects.equals(assigneId, that.assigneId) &&
            Objects.equals(courrierId, that.courrierId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        title,
        description,
        createdAt,
        updatedAt,
        assignedAt,
        processedAt,
        observation,
        status,
        createdBy,
        updatedBy,
        delai,
        relance,
        accuse,
        reponse,
        dateAccuse,
        dateReponse,
        assigneId,
        courrierId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
                (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
                (assignedAt != null ? "assignedAt=" + assignedAt + ", " : "") +
                (processedAt != null ? "processedAt=" + processedAt + ", " : "") +
                (observation != null ? "observation=" + observation + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (updatedBy != null ? "updatedBy=" + updatedBy + ", " : "") +
                (delai != null ? "delai=" + delai + ", " : "") +
                (relance != null ? "relance=" + relance + ", " : "") +
                (accuse != null ? "accuse=" + accuse + ", " : "") +
                (reponse != null ? "reponse=" + reponse + ", " : "") +
                (dateAccuse != null ? "dateAccuse=" + dateAccuse + ", " : "") +
                (dateReponse != null ? "dateReponse=" + dateReponse + ", " : "") +
                (assigneId != null ? "assigneId=" + assigneId + ", " : "") +
                (courrierId != null ? "courrierId=" + courrierId + ", " : "") +
            "}";
    }

}

package com.ormvah.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.ormvah.domain.enumeration.TypeCourrier;
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
 * Criteria class for the {@link com.ormvah.domain.Courrier} entity. This class is used
 * in {@link com.ormvah.web.rest.CourrierResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /courriers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CourrierCriteria implements Serializable, Criteria {
    /**
     * Class for filtering TypeCourrier
     */
    public static class TypeCourrierFilter extends Filter<TypeCourrier> {

        public TypeCourrierFilter() {
        }

        public TypeCourrierFilter(TypeCourrierFilter filter) {
            super(filter);
        }

        @Override
        public TypeCourrierFilter copy() {
            return new TypeCourrierFilter(this);
        }

    }
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

    private StringFilter idCourrier;

    private StringFilter subject;

    private StringFilter description;

    private TypeCourrierFilter typeCourrier;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private StringFilter createdBy;

    private StringFilter updatedBy;

    private DoubleFilter delai;

    private DoubleFilter relance;

    private BooleanFilter accuse;

    private BooleanFilter reponse;

    private LocalDateFilter dateAccuse;

    private LocalDateFilter dateReponse;

    private StatusFilter status;

    private InstantFilter receivedAt;

    private StringFilter instruction;

    private StringFilter expediteurDesc;

    private InstantFilter sentAt;

    private StringFilter destinataireDesc;

    private StringFilter destinataireVille;

    private LongFilter voieId;

    private LongFilter natureCourrierId;

    private LongFilter linkedToId;

    private LongFilter taskId;

    private LongFilter expeditorId;

    private LongFilter destinatorId;

    private LongFilter coordinatorId;

    private LongFilter emetteurId;

    private LongFilter evaluationId;

    private LongFilter courrierObjectId;

    private LongFilter expeditorTypeId;

    private LongFilter subdivisionId;

    private LongFilter servicesId;

    private LongFilter bordereauId;

    public CourrierCriteria() {
    }

    public CourrierCriteria(CourrierCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idCourrier = other.idCourrier == null ? null : other.idCourrier.copy();
        this.subject = other.subject == null ? null : other.subject.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.typeCourrier = other.typeCourrier == null ? null : other.typeCourrier.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.updatedBy = other.updatedBy == null ? null : other.updatedBy.copy();
        this.delai = other.delai == null ? null : other.delai.copy();
        this.relance = other.relance == null ? null : other.relance.copy();
        this.accuse = other.accuse == null ? null : other.accuse.copy();
        this.reponse = other.reponse == null ? null : other.reponse.copy();
        this.dateAccuse = other.dateAccuse == null ? null : other.dateAccuse.copy();
        this.dateReponse = other.dateReponse == null ? null : other.dateReponse.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.receivedAt = other.receivedAt == null ? null : other.receivedAt.copy();
        this.instruction = other.instruction == null ? null : other.instruction.copy();
        this.expediteurDesc = other.expediteurDesc == null ? null : other.expediteurDesc.copy();
        this.sentAt = other.sentAt == null ? null : other.sentAt.copy();
        this.destinataireDesc = other.destinataireDesc == null ? null : other.destinataireDesc.copy();
        this.destinataireVille = other.destinataireVille == null ? null : other.destinataireVille.copy();
        this.voieId = other.voieId == null ? null : other.voieId.copy();
        this.natureCourrierId = other.natureCourrierId == null ? null : other.natureCourrierId.copy();
        this.linkedToId = other.linkedToId == null ? null : other.linkedToId.copy();
        this.taskId = other.taskId == null ? null : other.taskId.copy();
        this.expeditorId = other.expeditorId == null ? null : other.expeditorId.copy();
        this.destinatorId = other.destinatorId == null ? null : other.destinatorId.copy();
        this.coordinatorId = other.coordinatorId == null ? null : other.coordinatorId.copy();
        this.emetteurId = other.emetteurId == null ? null : other.emetteurId.copy();
        this.evaluationId = other.evaluationId == null ? null : other.evaluationId.copy();
        this.courrierObjectId = other.courrierObjectId == null ? null : other.courrierObjectId.copy();
        this.expeditorTypeId = other.expeditorTypeId == null ? null : other.expeditorTypeId.copy();
        this.subdivisionId = other.subdivisionId == null ? null : other.subdivisionId.copy();
        this.servicesId = other.servicesId == null ? null : other.servicesId.copy();
        this.bordereauId = other.bordereauId == null ? null : other.bordereauId.copy();
    }

    @Override
    public CourrierCriteria copy() {
        return new CourrierCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getIdCourrier() {
        return idCourrier;
    }

    public void setIdCourrier(StringFilter idCourrier) {
        this.idCourrier = idCourrier;
    }

    public StringFilter getSubject() {
        return subject;
    }

    public void setSubject(StringFilter subject) {
        this.subject = subject;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public TypeCourrierFilter getTypeCourrier() {
        return typeCourrier;
    }

    public void setTypeCourrier(TypeCourrierFilter typeCourrier) {
        this.typeCourrier = typeCourrier;
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

    public StatusFilter getStatus() {
        return status;
    }

    public void setStatus(StatusFilter status) {
        this.status = status;
    }

    public InstantFilter getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(InstantFilter receivedAt) {
        this.receivedAt = receivedAt;
    }

    public StringFilter getInstruction() {
        return instruction;
    }

    public void setInstruction(StringFilter instruction) {
        this.instruction = instruction;
    }

    public StringFilter getExpediteurDesc() {
        return expediteurDesc;
    }

    public void setExpediteurDesc(StringFilter expediteurDesc) {
        this.expediteurDesc = expediteurDesc;
    }

    public InstantFilter getSentAt() {
        return sentAt;
    }

    public void setSentAt(InstantFilter sentAt) {
        this.sentAt = sentAt;
    }

    public StringFilter getDestinataireDesc() {
        return destinataireDesc;
    }

    public void setDestinataireDesc(StringFilter destinataireDesc) {
        this.destinataireDesc = destinataireDesc;
    }

    public StringFilter getDestinataireVille() {
        return destinataireVille;
    }

    public void setDestinataireVille(StringFilter destinataireVille) {
        this.destinataireVille = destinataireVille;
    }

    public LongFilter getVoieId() {
        return voieId;
    }

    public void setVoieId(LongFilter voieId) {
        this.voieId = voieId;
    }

    public LongFilter getNatureCourrierId() {
        return natureCourrierId;
    }

    public void setNatureCourrierId(LongFilter natureCourrierId) {
        this.natureCourrierId = natureCourrierId;
    }

    public LongFilter getLinkedToId() {
        return linkedToId;
    }

    public void setLinkedToId(LongFilter linkedToId) {
        this.linkedToId = linkedToId;
    }

    public LongFilter getTaskId() {
        return taskId;
    }

    public void setTaskId(LongFilter taskId) {
        this.taskId = taskId;
    }

    public LongFilter getExpeditorId() {
        return expeditorId;
    }

    public void setExpeditorId(LongFilter expeditorId) {
        this.expeditorId = expeditorId;
    }

    public LongFilter getDestinatorId() {
        return destinatorId;
    }

    public void setDestinatorId(LongFilter destinatorId) {
        this.destinatorId = destinatorId;
    }

    public LongFilter getCoordinatorId() {
        return coordinatorId;
    }

    public void setCoordinatorId(LongFilter coordinatorId) {
        this.coordinatorId = coordinatorId;
    }

    public LongFilter getEmetteurId() {
        return emetteurId;
    }

    public void setEmetteurId(LongFilter emetteurId) {
        this.emetteurId = emetteurId;
    }

    public LongFilter getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(LongFilter evaluationId) {
        this.evaluationId = evaluationId;
    }

    public LongFilter getCourrierObjectId() {
        return courrierObjectId;
    }

    public void setCourrierObjectId(LongFilter courrierObjectId) {
        this.courrierObjectId = courrierObjectId;
    }

    public LongFilter getExpeditorTypeId() {
        return expeditorTypeId;
    }

    public void setExpeditorTypeId(LongFilter expeditorTypeId) {
        this.expeditorTypeId = expeditorTypeId;
    }

    public LongFilter getSubdivisionId() {
        return subdivisionId;
    }

    public void setSubdivisionId(LongFilter subdivisionId) {
        this.subdivisionId = subdivisionId;
    }

    public LongFilter getServicesId() {
        return servicesId;
    }

    public void setServicesId(LongFilter servicesId) {
        this.servicesId = servicesId;
    }

    public LongFilter getBordereauId() {
        return bordereauId;
    }

    public void setBordereauId(LongFilter bordereauId) {
        this.bordereauId = bordereauId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CourrierCriteria that = (CourrierCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(idCourrier, that.idCourrier) &&
            Objects.equals(subject, that.subject) &&
            Objects.equals(description, that.description) &&
            Objects.equals(typeCourrier, that.typeCourrier) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(delai, that.delai) &&
            Objects.equals(relance, that.relance) &&
            Objects.equals(accuse, that.accuse) &&
            Objects.equals(reponse, that.reponse) &&
            Objects.equals(dateAccuse, that.dateAccuse) &&
            Objects.equals(dateReponse, that.dateReponse) &&
            Objects.equals(status, that.status) &&
            Objects.equals(receivedAt, that.receivedAt) &&
            Objects.equals(instruction, that.instruction) &&
            Objects.equals(expediteurDesc, that.expediteurDesc) &&
            Objects.equals(sentAt, that.sentAt) &&
            Objects.equals(destinataireDesc, that.destinataireDesc) &&
            Objects.equals(destinataireVille, that.destinataireVille) &&
            Objects.equals(voieId, that.voieId) &&
            Objects.equals(natureCourrierId, that.natureCourrierId) &&
            Objects.equals(linkedToId, that.linkedToId) &&
            Objects.equals(taskId, that.taskId) &&
            Objects.equals(expeditorId, that.expeditorId) &&
            Objects.equals(destinatorId, that.destinatorId) &&
            Objects.equals(coordinatorId, that.coordinatorId) &&
            Objects.equals(emetteurId, that.emetteurId) &&
            Objects.equals(evaluationId, that.evaluationId) &&
            Objects.equals(courrierObjectId, that.courrierObjectId) &&
            Objects.equals(expeditorTypeId, that.expeditorTypeId) &&
            Objects.equals(subdivisionId, that.subdivisionId) &&
            Objects.equals(servicesId, that.servicesId) &&
            Objects.equals(bordereauId, that.bordereauId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        idCourrier,
        subject,
        description,
        typeCourrier,
        createdAt,
        updatedAt,
        createdBy,
        updatedBy,
        delai,
        relance,
        accuse,
        reponse,
        dateAccuse,
        dateReponse,
        status,
        receivedAt,
        instruction,
        expediteurDesc,
        sentAt,
        destinataireDesc,
        destinataireVille,
        voieId,
        natureCourrierId,
        linkedToId,
        taskId,
        expeditorId,
        destinatorId,
        coordinatorId,
        emetteurId,
        evaluationId,
        courrierObjectId,
        expeditorTypeId,
        subdivisionId,
        servicesId,
        bordereauId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourrierCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (idCourrier != null ? "idCourrier=" + idCourrier + ", " : "") +
                (subject != null ? "subject=" + subject + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (typeCourrier != null ? "typeCourrier=" + typeCourrier + ", " : "") +
                (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
                (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (updatedBy != null ? "updatedBy=" + updatedBy + ", " : "") +
                (delai != null ? "delai=" + delai + ", " : "") +
                (relance != null ? "relance=" + relance + ", " : "") +
                (accuse != null ? "accuse=" + accuse + ", " : "") +
                (reponse != null ? "reponse=" + reponse + ", " : "") +
                (dateAccuse != null ? "dateAccuse=" + dateAccuse + ", " : "") +
                (dateReponse != null ? "dateReponse=" + dateReponse + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (receivedAt != null ? "receivedAt=" + receivedAt + ", " : "") +
                (instruction != null ? "instruction=" + instruction + ", " : "") +
                (expediteurDesc != null ? "expediteurDesc=" + expediteurDesc + ", " : "") +
                (sentAt != null ? "sentAt=" + sentAt + ", " : "") +
                (destinataireDesc != null ? "destinataireDesc=" + destinataireDesc + ", " : "") +
                (destinataireVille != null ? "destinataireVille=" + destinataireVille + ", " : "") +
                (voieId != null ? "voieId=" + voieId + ", " : "") +
                (natureCourrierId != null ? "natureCourrierId=" + natureCourrierId + ", " : "") +
                (linkedToId != null ? "linkedToId=" + linkedToId + ", " : "") +
                (taskId != null ? "taskId=" + taskId + ", " : "") +
                (expeditorId != null ? "expeditorId=" + expeditorId + ", " : "") +
                (destinatorId != null ? "destinatorId=" + destinatorId + ", " : "") +
                (coordinatorId != null ? "coordinatorId=" + coordinatorId + ", " : "") +
                (emetteurId != null ? "emetteurId=" + emetteurId + ", " : "") +
                (evaluationId != null ? "evaluationId=" + evaluationId + ", " : "") +
                (courrierObjectId != null ? "courrierObjectId=" + courrierObjectId + ", " : "") +
                (expeditorTypeId != null ? "expeditorTypeId=" + expeditorTypeId + ", " : "") +
                (subdivisionId != null ? "subdivisionId=" + subdivisionId + ", " : "") +
                (servicesId != null ? "servicesId=" + servicesId + ", " : "") +
                (bordereauId != null ? "bordereauId=" + bordereauId + ", " : "") +
            "}";
    }

}

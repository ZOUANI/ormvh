package com.ormvah.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.ormvah.domain.enumeration.TypeCourrier;

import com.ormvah.domain.enumeration.Status;

/**
 * A Courrier.
 */
@Entity
@Table(name = "courrier")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Courrier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Identifiant du courrier
     */
    @ApiModelProperty(value = "Identifiant du courrier")
    @Column(name = "id_courrier")
    private String idCourrier;

    /**
     * Objet
     */
    @ApiModelProperty(value = "Objet")
    @Column(name = "subject")
    private String subject;

    @Column(name = "description")
    private String description;

    /**
     * Arrivée / Sortie
     */
    @ApiModelProperty(value = "Arrivée / Sortie")
    @Enumerated(EnumType.STRING)
    @Column(name = "type_courrier")
    private TypeCourrier typeCourrier;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

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

    /**
     * status
     */
    @ApiModelProperty(value = "status")
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Lob
    @Column(name = "data")
    private byte[] data;

    @Column(name = "data_content_type")
    private String dataContentType;

    /**
     * Date d'arrivée
     */
    @ApiModelProperty(value = "Date d'arrivée")
    @Column(name = "received_at")
    private Instant receivedAt;

    @Column(name = "instruction")
    private String instruction;

    /**
     * Expediteur description
     */
    @ApiModelProperty(value = "Expediteur description")
    @Column(name = "expediteur_desc")
    private String expediteurDesc;

    /**
     * Date d'envoie
     */
    @ApiModelProperty(value = "Date d'envoie")
    @Column(name = "sent_at")
    private Instant sentAt;

    /**
     * destinataire description
     */
    @ApiModelProperty(value = "destinataire description")
    @Column(name = "destinataire_desc")
    private String destinataireDesc;

    /**
     * destinataire ville
     */
    @ApiModelProperty(value = "destinataire ville")
    @Column(name = "destinataire_ville")
    private String destinataireVille;

    @OneToOne
    @JoinColumn(unique = true)
    private Voie voie;

    @OneToOne
    @JoinColumn(unique = true)
    private NatureCourrier natureCourrier;

    @OneToOne
    @JoinColumn(unique = true)
    private Courrier linkedTo;

    @OneToMany(mappedBy = "courrier")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Task> tasks = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "courriers", allowSetters = true)
    private Expeditor expeditor;

    @ManyToOne
    @JsonIgnoreProperties(value = "courriers", allowSetters = true)
    private Expeditor destinator;

    @ManyToOne
    @JsonIgnoreProperties(value = "courriers", allowSetters = true)
    private LeService coordinator;

    @ManyToOne
    @JsonIgnoreProperties(value = "courriers", allowSetters = true)
    private LeService emetteur;

    @ManyToOne
    @JsonIgnoreProperties(value = "courriers", allowSetters = true)
    private Evaluation evaluation;

    @ManyToOne
    @JsonIgnoreProperties(value = "courriers", allowSetters = true)
    private CourrierObject courrierObject;

    @ManyToOne
    @JsonIgnoreProperties(value = "courriers", allowSetters = true)
    private ExpeditorType expeditorType;

    @ManyToOne
    @JsonIgnoreProperties(value = "courriers", allowSetters = true)
    private Subdivision subdivision;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "courrier_services",
               joinColumns = @JoinColumn(name = "courrier_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "services_id", referencedColumnName = "id"))
    private Set<LeService> services = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "courriers", allowSetters = true)
    private Bordereau bordereau;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdCourrier() {
        return idCourrier;
    }

    public Courrier idCourrier(String idCourrier) {
        this.idCourrier = idCourrier;
        return this;
    }

    public void setIdCourrier(String idCourrier) {
        this.idCourrier = idCourrier;
    }

    public String getSubject() {
        return subject;
    }

    public Courrier subject(String subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public Courrier description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TypeCourrier getTypeCourrier() {
        return typeCourrier;
    }

    public Courrier typeCourrier(TypeCourrier typeCourrier) {
        this.typeCourrier = typeCourrier;
        return this;
    }

    public void setTypeCourrier(TypeCourrier typeCourrier) {
        this.typeCourrier = typeCourrier;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Courrier createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Courrier updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Courrier createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public Courrier updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Double getDelai() {
        return delai;
    }

    public Courrier delai(Double delai) {
        this.delai = delai;
        return this;
    }

    public void setDelai(Double delai) {
        this.delai = delai;
    }

    public Double getRelance() {
        return relance;
    }

    public Courrier relance(Double relance) {
        this.relance = relance;
        return this;
    }

    public void setRelance(Double relance) {
        this.relance = relance;
    }

    public Boolean isAccuse() {
        return accuse;
    }

    public Courrier accuse(Boolean accuse) {
        this.accuse = accuse;
        return this;
    }

    public void setAccuse(Boolean accuse) {
        this.accuse = accuse;
    }

    public Boolean isReponse() {
        return reponse;
    }

    public Courrier reponse(Boolean reponse) {
        this.reponse = reponse;
        return this;
    }

    public void setReponse(Boolean reponse) {
        this.reponse = reponse;
    }

    public LocalDate getDateAccuse() {
        return dateAccuse;
    }

    public Courrier dateAccuse(LocalDate dateAccuse) {
        this.dateAccuse = dateAccuse;
        return this;
    }

    public void setDateAccuse(LocalDate dateAccuse) {
        this.dateAccuse = dateAccuse;
    }

    public LocalDate getDateReponse() {
        return dateReponse;
    }

    public Courrier dateReponse(LocalDate dateReponse) {
        this.dateReponse = dateReponse;
        return this;
    }

    public void setDateReponse(LocalDate dateReponse) {
        this.dateReponse = dateReponse;
    }

    public Status getStatus() {
        return status;
    }

    public Courrier status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public byte[] getData() {
        return data;
    }

    public Courrier data(byte[] data) {
        this.data = data;
        return this;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getDataContentType() {
        return dataContentType;
    }

    public Courrier dataContentType(String dataContentType) {
        this.dataContentType = dataContentType;
        return this;
    }

    public void setDataContentType(String dataContentType) {
        this.dataContentType = dataContentType;
    }

    public Instant getReceivedAt() {
        return receivedAt;
    }

    public Courrier receivedAt(Instant receivedAt) {
        this.receivedAt = receivedAt;
        return this;
    }

    public void setReceivedAt(Instant receivedAt) {
        this.receivedAt = receivedAt;
    }

    public String getInstruction() {
        return instruction;
    }

    public Courrier instruction(String instruction) {
        this.instruction = instruction;
        return this;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getExpediteurDesc() {
        return expediteurDesc;
    }

    public Courrier expediteurDesc(String expediteurDesc) {
        this.expediteurDesc = expediteurDesc;
        return this;
    }

    public void setExpediteurDesc(String expediteurDesc) {
        this.expediteurDesc = expediteurDesc;
    }

    public Instant getSentAt() {
        return sentAt;
    }

    public Courrier sentAt(Instant sentAt) {
        this.sentAt = sentAt;
        return this;
    }

    public void setSentAt(Instant sentAt) {
        this.sentAt = sentAt;
    }

    public String getDestinataireDesc() {
        return destinataireDesc;
    }

    public Courrier destinataireDesc(String destinataireDesc) {
        this.destinataireDesc = destinataireDesc;
        return this;
    }

    public void setDestinataireDesc(String destinataireDesc) {
        this.destinataireDesc = destinataireDesc;
    }

    public String getDestinataireVille() {
        return destinataireVille;
    }

    public Courrier destinataireVille(String destinataireVille) {
        this.destinataireVille = destinataireVille;
        return this;
    }

    public void setDestinataireVille(String destinataireVille) {
        this.destinataireVille = destinataireVille;
    }

    public Voie getVoie() {
        return voie;
    }

    public Courrier voie(Voie voie) {
        this.voie = voie;
        return this;
    }

    public void setVoie(Voie voie) {
        this.voie = voie;
    }

    public NatureCourrier getNatureCourrier() {
        return natureCourrier;
    }

    public Courrier natureCourrier(NatureCourrier natureCourrier) {
        this.natureCourrier = natureCourrier;
        return this;
    }

    public void setNatureCourrier(NatureCourrier natureCourrier) {
        this.natureCourrier = natureCourrier;
    }

    public Courrier getLinkedTo() {
        return linkedTo;
    }

    public Courrier linkedTo(Courrier courrier) {
        this.linkedTo = courrier;
        return this;
    }

    public void setLinkedTo(Courrier courrier) {
        this.linkedTo = courrier;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public Courrier tasks(Set<Task> tasks) {
        this.tasks = tasks;
        return this;
    }

    public Courrier addTask(Task task) {
        this.tasks.add(task);
        task.setCourrier(this);
        return this;
    }

    public Courrier removeTask(Task task) {
        this.tasks.remove(task);
        task.setCourrier(null);
        return this;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Expeditor getExpeditor() {
        return expeditor;
    }

    public Courrier expeditor(Expeditor expeditor) {
        this.expeditor = expeditor;
        return this;
    }

    public void setExpeditor(Expeditor expeditor) {
        this.expeditor = expeditor;
    }

    public Expeditor getDestinator() {
        return destinator;
    }

    public Courrier destinator(Expeditor expeditor) {
        this.destinator = expeditor;
        return this;
    }

    public void setDestinator(Expeditor expeditor) {
        this.destinator = expeditor;
    }

    public LeService getCoordinator() {
        return coordinator;
    }

    public Courrier coordinator(LeService leService) {
        this.coordinator = leService;
        return this;
    }

    public void setCoordinator(LeService leService) {
        this.coordinator = leService;
    }

    public LeService getEmetteur() {
        return emetteur;
    }

    public Courrier emetteur(LeService leService) {
        this.emetteur = leService;
        return this;
    }

    public void setEmetteur(LeService leService) {
        this.emetteur = leService;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public Courrier evaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
        return this;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    public CourrierObject getCourrierObject() {
        return courrierObject;
    }

    public Courrier courrierObject(CourrierObject courrierObject) {
        this.courrierObject = courrierObject;
        return this;
    }

    public void setCourrierObject(CourrierObject courrierObject) {
        this.courrierObject = courrierObject;
    }

    public ExpeditorType getExpeditorType() {
        return expeditorType;
    }

    public Courrier expeditorType(ExpeditorType expeditorType) {
        this.expeditorType = expeditorType;
        return this;
    }

    public void setExpeditorType(ExpeditorType expeditorType) {
        this.expeditorType = expeditorType;
    }

    public Subdivision getSubdivision() {
        return subdivision;
    }

    public Courrier subdivision(Subdivision subdivision) {
        this.subdivision = subdivision;
        return this;
    }

    public void setSubdivision(Subdivision subdivision) {
        this.subdivision = subdivision;
    }

    public Set<LeService> getServices() {
        return services;
    }

    public Courrier services(Set<LeService> leServices) {
        this.services = leServices;
        return this;
    }

    public Courrier addServices(LeService leService) {
        this.services.add(leService);
        leService.getCourriers().add(this);
        return this;
    }

    public Courrier removeServices(LeService leService) {
        this.services.remove(leService);
        leService.getCourriers().remove(this);
        return this;
    }

    public void setServices(Set<LeService> leServices) {
        this.services = leServices;
    }

    public Bordereau getBordereau() {
        return bordereau;
    }

    public Courrier bordereau(Bordereau bordereau) {
        this.bordereau = bordereau;
        return this;
    }

    public void setBordereau(Bordereau bordereau) {
        this.bordereau = bordereau;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Courrier)) {
            return false;
        }
        return id != null && id.equals(((Courrier) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Courrier{" +
            "id=" + getId() +
            ", idCourrier='" + getIdCourrier() + "'" +
            ", subject='" + getSubject() + "'" +
            ", description='" + getDescription() + "'" +
            ", typeCourrier='" + getTypeCourrier() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", delai=" + getDelai() +
            ", relance=" + getRelance() +
            ", accuse='" + isAccuse() + "'" +
            ", reponse='" + isReponse() + "'" +
            ", dateAccuse='" + getDateAccuse() + "'" +
            ", dateReponse='" + getDateReponse() + "'" +
            ", status='" + getStatus() + "'" +
            ", data='" + getData() + "'" +
            ", dataContentType='" + getDataContentType() + "'" +
            ", receivedAt='" + getReceivedAt() + "'" +
            ", instruction='" + getInstruction() + "'" +
            ", expediteurDesc='" + getExpediteurDesc() + "'" +
            ", sentAt='" + getSentAt() + "'" +
            ", destinataireDesc='" + getDestinataireDesc() + "'" +
            ", destinataireVille='" + getDestinataireVille() + "'" +
            "}";
    }
}

package com.ormvah.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Bordereau.
 */
@Entity
@Table(name = "bordereau")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Bordereau implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @Column(name = "description")
    private String description;

    @Column(name = "nombre_piece_jointe")
    private Integer nombrePieceJointe;

    @Column(name = "date_bordereaux")
    private LocalDate dateBordereaux;

    @OneToMany(mappedBy = "bordereau")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Courrier> courriers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public Bordereau libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public Bordereau description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNombrePieceJointe() {
        return nombrePieceJointe;
    }

    public Bordereau nombrePieceJointe(Integer nombrePieceJointe) {
        this.nombrePieceJointe = nombrePieceJointe;
        return this;
    }

    public void setNombrePieceJointe(Integer nombrePieceJointe) {
        this.nombrePieceJointe = nombrePieceJointe;
    }

    public LocalDate getDateBordereaux() {
        return dateBordereaux;
    }

    public Bordereau dateBordereaux(LocalDate dateBordereaux) {
        this.dateBordereaux = dateBordereaux;
        return this;
    }

    public void setDateBordereaux(LocalDate dateBordereaux) {
        this.dateBordereaux = dateBordereaux;
    }

    public Set<Courrier> getCourriers() {
        return courriers;
    }

    public Bordereau courriers(Set<Courrier> courriers) {
        this.courriers = courriers;
        return this;
    }

    public Bordereau addCourrier(Courrier courrier) {
        this.courriers.add(courrier);
        courrier.setBordereau(this);
        return this;
    }

    public Bordereau removeCourrier(Courrier courrier) {
        this.courriers.remove(courrier);
        courrier.setBordereau(null);
        return this;
    }

    public void setCourriers(Set<Courrier> courriers) {
        this.courriers = courriers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bordereau)) {
            return false;
        }
        return id != null && id.equals(((Bordereau) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bordereau{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", description='" + getDescription() + "'" +
            ", nombrePieceJointe=" + getNombrePieceJointe() +
            ", dateBordereaux='" + getDateBordereaux() + "'" +
            "}";
    }
}

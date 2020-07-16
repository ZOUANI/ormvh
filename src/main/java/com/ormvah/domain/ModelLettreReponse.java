package com.ormvah.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A ModelLettreReponse.
 */
@Entity
@Table(name = "model_lettre_reponse")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ModelLettreReponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "chemin", nullable = false)
    private String chemin;

    @OneToOne
    @JoinColumn(unique = true)
    private CategorieModelLettreReponse categorieModelLettreReponse;

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

    public ModelLettreReponse libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getCode() {
        return code;
    }

    public ModelLettreReponse code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getChemin() {
        return chemin;
    }

    public ModelLettreReponse chemin(String chemin) {
        this.chemin = chemin;
        return this;
    }

    public void setChemin(String chemin) {
        this.chemin = chemin;
    }

    public CategorieModelLettreReponse getCategorieModelLettreReponse() {
        return categorieModelLettreReponse;
    }

    public ModelLettreReponse categorieModelLettreReponse(CategorieModelLettreReponse categorieModelLettreReponse) {
        this.categorieModelLettreReponse = categorieModelLettreReponse;
        return this;
    }

    public void setCategorieModelLettreReponse(CategorieModelLettreReponse categorieModelLettreReponse) {
        this.categorieModelLettreReponse = categorieModelLettreReponse;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ModelLettreReponse)) {
            return false;
        }
        return id != null && id.equals(((ModelLettreReponse) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ModelLettreReponse{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", code='" + getCode() + "'" +
            ", chemin='" + getChemin() + "'" +
            "}";
    }
}

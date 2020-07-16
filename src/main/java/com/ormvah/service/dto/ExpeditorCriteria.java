package com.ormvah.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.ormvah.domain.enumeration.Sexe;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.ormvah.domain.Expeditor} entity. This class is used
 * in {@link com.ormvah.web.rest.ExpeditorResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /expeditors?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ExpeditorCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Sexe
     */
    public static class SexeFilter extends Filter<Sexe> {

        public SexeFilter() {
        }

        public SexeFilter(SexeFilter filter) {
            super(filter);
        }

        @Override
        public SexeFilter copy() {
            return new SexeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private StringFilter description;

    private StringFilter nature;

    private SexeFilter sexe;

    private IntegerFilter age;

    private StringFilter nationality;

    private StringFilter adress;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private StringFilter createdBy;

    private StringFilter updatedBy;

    public ExpeditorCriteria() {
    }

    public ExpeditorCriteria(ExpeditorCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.nature = other.nature == null ? null : other.nature.copy();
        this.sexe = other.sexe == null ? null : other.sexe.copy();
        this.age = other.age == null ? null : other.age.copy();
        this.nationality = other.nationality == null ? null : other.nationality.copy();
        this.adress = other.adress == null ? null : other.adress.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.updatedBy = other.updatedBy == null ? null : other.updatedBy.copy();
    }

    @Override
    public ExpeditorCriteria copy() {
        return new ExpeditorCriteria(this);
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

    public StringFilter getNature() {
        return nature;
    }

    public void setNature(StringFilter nature) {
        this.nature = nature;
    }

    public SexeFilter getSexe() {
        return sexe;
    }

    public void setSexe(SexeFilter sexe) {
        this.sexe = sexe;
    }

    public IntegerFilter getAge() {
        return age;
    }

    public void setAge(IntegerFilter age) {
        this.age = age;
    }

    public StringFilter getNationality() {
        return nationality;
    }

    public void setNationality(StringFilter nationality) {
        this.nationality = nationality;
    }

    public StringFilter getAdress() {
        return adress;
    }

    public void setAdress(StringFilter adress) {
        this.adress = adress;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ExpeditorCriteria that = (ExpeditorCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(description, that.description) &&
            Objects.equals(nature, that.nature) &&
            Objects.equals(sexe, that.sexe) &&
            Objects.equals(age, that.age) &&
            Objects.equals(nationality, that.nationality) &&
            Objects.equals(adress, that.adress) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(updatedBy, that.updatedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        title,
        description,
        nature,
        sexe,
        age,
        nationality,
        adress,
        createdAt,
        updatedAt,
        createdBy,
        updatedBy
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExpeditorCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (nature != null ? "nature=" + nature + ", " : "") +
                (sexe != null ? "sexe=" + sexe + ", " : "") +
                (age != null ? "age=" + age + ", " : "") +
                (nationality != null ? "nationality=" + nationality + ", " : "") +
                (adress != null ? "adress=" + adress + ", " : "") +
                (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
                (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (updatedBy != null ? "updatedBy=" + updatedBy + ", " : "") +
            "}";
    }

}

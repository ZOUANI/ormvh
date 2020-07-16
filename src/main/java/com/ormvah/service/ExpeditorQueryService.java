package com.ormvah.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.ormvah.domain.Expeditor;
import com.ormvah.domain.*; // for static metamodels
import com.ormvah.repository.ExpeditorRepository;
import com.ormvah.service.dto.ExpeditorCriteria;

/**
 * Service for executing complex queries for {@link Expeditor} entities in the database.
 * The main input is a {@link ExpeditorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Expeditor} or a {@link Page} of {@link Expeditor} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ExpeditorQueryService extends QueryService<Expeditor> {

    private final Logger log = LoggerFactory.getLogger(ExpeditorQueryService.class);

    private final ExpeditorRepository expeditorRepository;

    public ExpeditorQueryService(ExpeditorRepository expeditorRepository) {
        this.expeditorRepository = expeditorRepository;
    }

    /**
     * Return a {@link List} of {@link Expeditor} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Expeditor> findByCriteria(ExpeditorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Expeditor> specification = createSpecification(criteria);
        return expeditorRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Expeditor} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Expeditor> findByCriteria(ExpeditorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Expeditor> specification = createSpecification(criteria);
        return expeditorRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ExpeditorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Expeditor> specification = createSpecification(criteria);
        return expeditorRepository.count(specification);
    }

    /**
     * Function to convert {@link ExpeditorCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Expeditor> createSpecification(ExpeditorCriteria criteria) {
        Specification<Expeditor> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Expeditor_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Expeditor_.title));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Expeditor_.description));
            }
            if (criteria.getNature() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNature(), Expeditor_.nature));
            }
            if (criteria.getSexe() != null) {
                specification = specification.and(buildSpecification(criteria.getSexe(), Expeditor_.sexe));
            }
            if (criteria.getAge() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAge(), Expeditor_.age));
            }
            if (criteria.getNationality() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNationality(), Expeditor_.nationality));
            }
            if (criteria.getAdress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdress(), Expeditor_.adress));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), Expeditor_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), Expeditor_.updatedAt));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Expeditor_.createdBy));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), Expeditor_.updatedBy));
            }
        }
        return specification;
    }
}

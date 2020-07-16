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

import com.ormvah.domain.LeService;
import com.ormvah.domain.*; // for static metamodels
import com.ormvah.repository.LeServiceRepository;
import com.ormvah.service.dto.LeServiceCriteria;

/**
 * Service for executing complex queries for {@link LeService} entities in the database.
 * The main input is a {@link LeServiceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LeService} or a {@link Page} of {@link LeService} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LeServiceQueryService extends QueryService<LeService> {

    private final Logger log = LoggerFactory.getLogger(LeServiceQueryService.class);

    private final LeServiceRepository leServiceRepository;

    public LeServiceQueryService(LeServiceRepository leServiceRepository) {
        this.leServiceRepository = leServiceRepository;
    }

    /**
     * Return a {@link List} of {@link LeService} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LeService> findByCriteria(LeServiceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LeService> specification = createSpecification(criteria);
        return leServiceRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link LeService} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LeService> findByCriteria(LeServiceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LeService> specification = createSpecification(criteria);
        return leServiceRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LeServiceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LeService> specification = createSpecification(criteria);
        return leServiceRepository.count(specification);
    }

    /**
     * Function to convert {@link LeServiceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LeService> createSpecification(LeServiceCriteria criteria) {
        Specification<LeService> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LeService_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), LeService_.title));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), LeService_.description));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), LeService_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), LeService_.updatedAt));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), LeService_.createdBy));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), LeService_.updatedBy));
            }
            if (criteria.getCourrierId() != null) {
                specification = specification.and(buildSpecification(criteria.getCourrierId(),
                    root -> root.join(LeService_.courriers, JoinType.LEFT).get(Courrier_.id)));
            }
        }
        return specification;
    }
}

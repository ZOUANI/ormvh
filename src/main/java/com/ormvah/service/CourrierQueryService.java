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

import com.ormvah.domain.Courrier;
import com.ormvah.domain.*; // for static metamodels
import com.ormvah.repository.CourrierRepository;
import com.ormvah.service.dto.CourrierCriteria;

/**
 * Service for executing complex queries for {@link Courrier} entities in the database.
 * The main input is a {@link CourrierCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Courrier} or a {@link Page} of {@link Courrier} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CourrierQueryService extends QueryService<Courrier> {

    private final Logger log = LoggerFactory.getLogger(CourrierQueryService.class);

    private final CourrierRepository courrierRepository;

    public CourrierQueryService(CourrierRepository courrierRepository) {
        this.courrierRepository = courrierRepository;
    }

    /**
     * Return a {@link List} of {@link Courrier} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Courrier> findByCriteria(CourrierCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Courrier> specification = createSpecification(criteria);
        return courrierRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Courrier} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Courrier> findByCriteria(CourrierCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Courrier> specification = createSpecification(criteria);
        return courrierRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CourrierCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Courrier> specification = createSpecification(criteria);
        return courrierRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    private Specification<Courrier> createSpecification(CourrierCriteria criteria) {
        Specification<Courrier> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Courrier_.id));
            }
            if (criteria.getIdCourrier() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdCourrier(), Courrier_.idCourrier));
            }
            if (criteria.getSubject() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubject(), Courrier_.subject));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Courrier_.description));
            }
            if (criteria.getTypeCourrier() != null) {
                specification = specification.and(buildSpecification(criteria.getTypeCourrier(), Courrier_.typeCourrier));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), Courrier_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), Courrier_.updatedAt));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Courrier_.createdBy));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), Courrier_.updatedBy));
            }
            if (criteria.getDelai() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDelai(), Courrier_.delai));
            }
            if (criteria.getRelance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRelance(), Courrier_.relance));
            }
            if (criteria.getAccuse() != null) {
                specification = specification.and(buildSpecification(criteria.getAccuse(), Courrier_.accuse));
            }
            if (criteria.getReponse() != null) {
                specification = specification.and(buildSpecification(criteria.getReponse(), Courrier_.reponse));
            }
            if (criteria.getDateAccuse() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateAccuse(), Courrier_.dateAccuse));
            }
            if (criteria.getDateReponse() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateReponse(), Courrier_.dateReponse));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Courrier_.status));
            }
            if (criteria.getReceivedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReceivedAt(), Courrier_.receivedAt));
            }
            if (criteria.getInstruction() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInstruction(), Courrier_.instruction));
            }
            if (criteria.getExpediteurDesc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExpediteurDesc(), Courrier_.expediteurDesc));
            }
            if (criteria.getSentAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSentAt(), Courrier_.sentAt));
            }
            if (criteria.getDestinataireDesc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDestinataireDesc(), Courrier_.destinataireDesc));
            }
            if (criteria.getDestinataireVille() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDestinataireVille(), Courrier_.destinataireVille));
            }
            if (criteria.getVoieId() != null) {
                specification = specification.and(buildSpecification(criteria.getVoieId(),
                    root -> root.join(Courrier_.voie, JoinType.LEFT).get(Voie_.id)));
            }
            if (criteria.getNatureCourrierId() != null) {
                specification = specification.and(buildSpecification(criteria.getNatureCourrierId(),
                    root -> root.join(Courrier_.natureCourrier, JoinType.LEFT).get(NatureCourrier_.id)));
            }
            if (criteria.getLinkedToId() != null) {
                specification = specification.and(buildSpecification(criteria.getLinkedToId(),
                    root -> root.join(Courrier_.linkedTo, JoinType.LEFT).get(Courrier_.id)));
            }
            if (criteria.getTaskId() != null) {
                specification = specification.and(buildSpecification(criteria.getTaskId(),
                    root -> root.join(Courrier_.tasks, JoinType.LEFT).get(Task_.id)));
            }
            if (criteria.getExpeditorId() != null) {
                specification = specification.and(buildSpecification(criteria.getExpeditorId(),
                    root -> root.join(Courrier_.expeditor, JoinType.LEFT).get(Expeditor_.id)));
            }
            if (criteria.getDestinatorId() != null) {
                specification = specification.and(buildSpecification(criteria.getDestinatorId(),
                    root -> root.join(Courrier_.destinator, JoinType.LEFT).get(Expeditor_.id)));
            }
            if (criteria.getCoordinatorId() != null) {
                specification = specification.and(buildSpecification(criteria.getCoordinatorId(),
                    root -> root.join(Courrier_.coordinator, JoinType.LEFT).get(LeService_.id)));
            }
            if (criteria.getEmetteurId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmetteurId(),
                    root -> root.join(Courrier_.emetteur, JoinType.LEFT).get(LeService_.id)));
            }
            if (criteria.getEvaluationId() != null) {
                specification = specification.and(buildSpecification(criteria.getEvaluationId(),
                    root -> root.join(Courrier_.evaluation, JoinType.LEFT).get(Evaluation_.id)));
            }
            if (criteria.getCourrierObjectId() != null) {
                specification = specification.and(buildSpecification(criteria.getCourrierObjectId(),
                    root -> root.join(Courrier_.courrierObject, JoinType.LEFT).get(CourrierObject_.id)));
            }
            if (criteria.getExpeditorTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getExpeditorTypeId(),
                    root -> root.join(Courrier_.expeditorType, JoinType.LEFT).get(ExpeditorType_.id)));
            }
            if (criteria.getSubdivisionId() != null) {
                specification = specification.and(buildSpecification(criteria.getSubdivisionId(),
                    root -> root.join(Courrier_.subdivision, JoinType.LEFT).get(Subdivision_.id)));
            }
            if (criteria.getServicesId() != null) {
                specification = specification.and(buildSpecification(criteria.getServicesId(),
                    root -> root.join(Courrier_.services, JoinType.LEFT).get(LeService_.id)));
            }
            if (criteria.getBordereauId() != null) {
                specification = specification.and(buildSpecification(criteria.getBordereauId(),
                    root -> root.join(Courrier_.bordereau, JoinType.LEFT).get(Bordereau_.id)));
            }
        }
        return specification;
    }
}

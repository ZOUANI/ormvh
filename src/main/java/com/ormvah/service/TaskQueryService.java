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

import com.ormvah.domain.Task;
import com.ormvah.domain.*; // for static metamodels
import com.ormvah.repository.TaskRepository;
import com.ormvah.service.dto.TaskCriteria;

/**
 * Service for executing complex queries for {@link Task} entities in the database.
 * The main input is a {@link TaskCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Task} or a {@link Page} of {@link Task} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TaskQueryService extends QueryService<Task> {

    private final Logger log = LoggerFactory.getLogger(TaskQueryService.class);

    private final TaskRepository taskRepository;

    public TaskQueryService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Return a {@link List} of {@link Task} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Task> findByCriteria(TaskCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Task> specification = createSpecification(criteria);
        return taskRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Task} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Task> findByCriteria(TaskCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Task> specification = createSpecification(criteria);
        return taskRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TaskCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Task> specification = createSpecification(criteria);
        return taskRepository.count(specification);
    }

    /**
     * Function to convert {@link TaskCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Task> createSpecification(TaskCriteria criteria) {
        Specification<Task> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Task_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Task_.title));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Task_.description));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), Task_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), Task_.updatedAt));
            }
            if (criteria.getAssignedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAssignedAt(), Task_.assignedAt));
            }
            if (criteria.getProcessedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProcessedAt(), Task_.processedAt));
            }
            if (criteria.getObservation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getObservation(), Task_.observation));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Task_.status));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Task_.createdBy));
            }
            if (criteria.getUpdatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUpdatedBy(), Task_.updatedBy));
            }
            if (criteria.getDelai() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDelai(), Task_.delai));
            }
            if (criteria.getRelance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRelance(), Task_.relance));
            }
            if (criteria.getAccuse() != null) {
                specification = specification.and(buildSpecification(criteria.getAccuse(), Task_.accuse));
            }
            if (criteria.getReponse() != null) {
                specification = specification.and(buildSpecification(criteria.getReponse(), Task_.reponse));
            }
            if (criteria.getDateAccuse() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateAccuse(), Task_.dateAccuse));
            }
            if (criteria.getDateReponse() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateReponse(), Task_.dateReponse));
            }
            if (criteria.getAssigneId() != null) {
                specification = specification.and(buildSpecification(criteria.getAssigneId(),
                    root -> root.join(Task_.assigne, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getCourrierId() != null) {
                specification = specification.and(buildSpecification(criteria.getCourrierId(),
                    root -> root.join(Task_.courrier, JoinType.LEFT).get(Courrier_.id)));
            }
        }
        return specification;
    }
}

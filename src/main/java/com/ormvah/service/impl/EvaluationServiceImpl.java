package com.ormvah.service.impl;

import com.ormvah.service.EvaluationService;
import com.ormvah.domain.Evaluation;
import com.ormvah.repository.EvaluationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Evaluation}.
 */
@Service
@Transactional
public class EvaluationServiceImpl implements EvaluationService {

    private final Logger log = LoggerFactory.getLogger(EvaluationServiceImpl.class);

    private final EvaluationRepository evaluationRepository;

    public EvaluationServiceImpl(EvaluationRepository evaluationRepository) {
        this.evaluationRepository = evaluationRepository;
    }

    /**
     * Save a evaluation.
     *
     * @param evaluation the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Evaluation save(Evaluation evaluation) {
        log.debug("Request to save Evaluation : {}", evaluation);
        return evaluationRepository.save(evaluation);
    }

    /**
     * Get all the evaluations.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Evaluation> findAll() {
        log.debug("Request to get all Evaluations");
        return evaluationRepository.findAll();
    }


    /**
     * Get one evaluation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Evaluation> findOne(Long id) {
        log.debug("Request to get Evaluation : {}", id);
        return evaluationRepository.findById(id);
    }

    /**
     * Delete the evaluation by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Evaluation : {}", id);
        evaluationRepository.deleteById(id);
    }
}

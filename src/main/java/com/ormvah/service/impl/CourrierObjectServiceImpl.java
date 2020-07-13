package com.ormvah.service.impl;

import com.ormvah.service.CourrierObjectService;
import com.ormvah.domain.CourrierObject;
import com.ormvah.repository.CourrierObjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link CourrierObject}.
 */
@Service
@Transactional
public class CourrierObjectServiceImpl implements CourrierObjectService {

    private final Logger log = LoggerFactory.getLogger(CourrierObjectServiceImpl.class);

    private final CourrierObjectRepository courrierObjectRepository;

    public CourrierObjectServiceImpl(CourrierObjectRepository courrierObjectRepository) {
        this.courrierObjectRepository = courrierObjectRepository;
    }

    /**
     * Save a courrierObject.
     *
     * @param courrierObject the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CourrierObject save(CourrierObject courrierObject) {
        log.debug("Request to save CourrierObject : {}", courrierObject);
        return courrierObjectRepository.save(courrierObject);
    }

    /**
     * Get all the courrierObjects.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CourrierObject> findAll() {
        log.debug("Request to get all CourrierObjects");
        return courrierObjectRepository.findAll();
    }


    /**
     * Get one courrierObject by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CourrierObject> findOne(Long id) {
        log.debug("Request to get CourrierObject : {}", id);
        return courrierObjectRepository.findById(id);
    }

    /**
     * Delete the courrierObject by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CourrierObject : {}", id);
        courrierObjectRepository.deleteById(id);
    }
}

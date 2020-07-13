package com.ormvah.service.impl;

import com.ormvah.service.SubdivisionService;
import com.ormvah.domain.Subdivision;
import com.ormvah.repository.SubdivisionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Subdivision}.
 */
@Service
@Transactional
public class SubdivisionServiceImpl implements SubdivisionService {

    private final Logger log = LoggerFactory.getLogger(SubdivisionServiceImpl.class);

    private final SubdivisionRepository subdivisionRepository;

    public SubdivisionServiceImpl(SubdivisionRepository subdivisionRepository) {
        this.subdivisionRepository = subdivisionRepository;
    }

    /**
     * Save a subdivision.
     *
     * @param subdivision the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Subdivision save(Subdivision subdivision) {
        log.debug("Request to save Subdivision : {}", subdivision);
        return subdivisionRepository.save(subdivision);
    }

    /**
     * Get all the subdivisions.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Subdivision> findAll() {
        log.debug("Request to get all Subdivisions");
        return subdivisionRepository.findAll();
    }


    /**
     * Get one subdivision by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Subdivision> findOne(Long id) {
        log.debug("Request to get Subdivision : {}", id);
        return subdivisionRepository.findById(id);
    }

    /**
     * Delete the subdivision by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Subdivision : {}", id);
        subdivisionRepository.deleteById(id);
    }
}

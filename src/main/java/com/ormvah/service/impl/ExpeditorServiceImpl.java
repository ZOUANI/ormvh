package com.ormvah.service.impl;

import com.ormvah.service.ExpeditorService;
import com.ormvah.domain.Expeditor;
import com.ormvah.repository.ExpeditorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Expeditor}.
 */
@Service
@Transactional
public class ExpeditorServiceImpl implements ExpeditorService {

    private final Logger log = LoggerFactory.getLogger(ExpeditorServiceImpl.class);

    private final ExpeditorRepository expeditorRepository;

    public ExpeditorServiceImpl(ExpeditorRepository expeditorRepository) {
        this.expeditorRepository = expeditorRepository;
    }

    /**
     * Save a expeditor.
     *
     * @param expeditor the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Expeditor save(Expeditor expeditor) {
        log.debug("Request to save Expeditor : {}", expeditor);
        return expeditorRepository.save(expeditor);
    }

    /**
     * Get all the expeditors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Expeditor> findAll(Pageable pageable) {
        log.debug("Request to get all Expeditors");
        return expeditorRepository.findAll(pageable);
    }


    /**
     * Get one expeditor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Expeditor> findOne(Long id) {
        log.debug("Request to get Expeditor : {}", id);
        return expeditorRepository.findById(id);
    }

    /**
     * Delete the expeditor by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Expeditor : {}", id);
        expeditorRepository.deleteById(id);
    }
}

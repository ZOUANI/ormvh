package com.ormvah.service.impl;

import com.ormvah.service.BordereauService;
import com.ormvah.domain.Bordereau;
import com.ormvah.repository.BordereauRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Bordereau}.
 */
@Service
@Transactional
public class BordereauServiceImpl implements BordereauService {

    private final Logger log = LoggerFactory.getLogger(BordereauServiceImpl.class);

    private final BordereauRepository bordereauRepository;

    public BordereauServiceImpl(BordereauRepository bordereauRepository) {
        this.bordereauRepository = bordereauRepository;
    }

    /**
     * Save a bordereau.
     *
     * @param bordereau the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Bordereau save(Bordereau bordereau) {
        log.debug("Request to save Bordereau : {}", bordereau);
        return bordereauRepository.save(bordereau);
    }

    /**
     * Get all the bordereaus.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Bordereau> findAll() {
        log.debug("Request to get all Bordereaus");
        return bordereauRepository.findAll();
    }


    /**
     * Get one bordereau by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Bordereau> findOne(Long id) {
        log.debug("Request to get Bordereau : {}", id);
        return bordereauRepository.findById(id);
    }

    /**
     * Delete the bordereau by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Bordereau : {}", id);
        bordereauRepository.deleteById(id);
    }
}

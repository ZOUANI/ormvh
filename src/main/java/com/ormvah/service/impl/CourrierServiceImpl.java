package com.ormvah.service.impl;

import com.ormvah.service.CourrierService;
import com.ormvah.domain.Courrier;
import com.ormvah.repository.CourrierRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Courrier}.
 */
@Service
@Transactional
public class CourrierServiceImpl implements CourrierService {

    private final Logger log = LoggerFactory.getLogger(CourrierServiceImpl.class);

    private final CourrierRepository courrierRepository;

    public CourrierServiceImpl(CourrierRepository courrierRepository) {
        this.courrierRepository = courrierRepository;
    }

    @Override
    public Courrier save(Courrier courrier) {
        log.debug("Request to save Courrier : {}", courrier);
        return courrierRepository.save(courrier);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Courrier> findAll(Pageable pageable) {
        log.debug("Request to get all Courriers");
        return courrierRepository.findAll(pageable);
    }


    public Page<Courrier> findAllWithEagerRelationships(Pageable pageable) {
        return courrierRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Courrier> findOne(Long id) {
        log.debug("Request to get Courrier : {}", id);
        return courrierRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Courrier : {}", id);
        courrierRepository.deleteById(id);
    }
}

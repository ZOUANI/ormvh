package com.ormvah.service.impl;

import com.ormvah.service.LeServiceService;
import com.ormvah.domain.LeService;
import com.ormvah.repository.LeServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link LeService}.
 */
@Service
@Transactional
public class LeServiceServiceImpl implements LeServiceService {

    private final Logger log = LoggerFactory.getLogger(LeServiceServiceImpl.class);

    private final LeServiceRepository leServiceRepository;

    public LeServiceServiceImpl(LeServiceRepository leServiceRepository) {
        this.leServiceRepository = leServiceRepository;
    }

    @Override
    public LeService save(LeService leService) {
        log.debug("Request to save LeService : {}", leService);
        return leServiceRepository.save(leService);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LeService> findAll(Pageable pageable) {
        log.debug("Request to get all LeServices");
        return leServiceRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<LeService> findOne(Long id) {
        log.debug("Request to get LeService : {}", id);
        return leServiceRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LeService : {}", id);
        leServiceRepository.deleteById(id);
    }
}

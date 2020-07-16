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

    @Override
    public CourrierObject save(CourrierObject courrierObject) {
        log.debug("Request to save CourrierObject : {}", courrierObject);
        return courrierObjectRepository.save(courrierObject);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourrierObject> findAll() {
        log.debug("Request to get all CourrierObjects");
        return courrierObjectRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CourrierObject> findOne(Long id) {
        log.debug("Request to get CourrierObject : {}", id);
        return courrierObjectRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CourrierObject : {}", id);
        courrierObjectRepository.deleteById(id);
    }
}

package com.ormvah.service.impl;

import com.ormvah.service.NatureCourrierService;
import com.ormvah.domain.NatureCourrier;
import com.ormvah.repository.NatureCourrierRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link NatureCourrier}.
 */
@Service
@Transactional
public class NatureCourrierServiceImpl implements NatureCourrierService {

    private final Logger log = LoggerFactory.getLogger(NatureCourrierServiceImpl.class);

    private final NatureCourrierRepository natureCourrierRepository;

    public NatureCourrierServiceImpl(NatureCourrierRepository natureCourrierRepository) {
        this.natureCourrierRepository = natureCourrierRepository;
    }

    @Override
    public NatureCourrier save(NatureCourrier natureCourrier) {
        log.debug("Request to save NatureCourrier : {}", natureCourrier);
        return natureCourrierRepository.save(natureCourrier);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NatureCourrier> findAll() {
        log.debug("Request to get all NatureCourriers");
        return natureCourrierRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<NatureCourrier> findOne(Long id) {
        log.debug("Request to get NatureCourrier : {}", id);
        return natureCourrierRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete NatureCourrier : {}", id);
        natureCourrierRepository.deleteById(id);
    }
}

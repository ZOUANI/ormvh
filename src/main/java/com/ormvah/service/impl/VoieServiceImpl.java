package com.ormvah.service.impl;

import com.ormvah.service.VoieService;
import com.ormvah.domain.Voie;
import com.ormvah.repository.VoieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Voie}.
 */
@Service
@Transactional
public class VoieServiceImpl implements VoieService {

    private final Logger log = LoggerFactory.getLogger(VoieServiceImpl.class);

    private final VoieRepository voieRepository;

    public VoieServiceImpl(VoieRepository voieRepository) {
        this.voieRepository = voieRepository;
    }

    /**
     * Save a voie.
     *
     * @param voie the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Voie save(Voie voie) {
        log.debug("Request to save Voie : {}", voie);
        return voieRepository.save(voie);
    }

    /**
     * Get all the voies.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Voie> findAll() {
        log.debug("Request to get all Voies");
        return voieRepository.findAll();
    }


    /**
     * Get one voie by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Voie> findOne(Long id) {
        log.debug("Request to get Voie : {}", id);
        return voieRepository.findById(id);
    }

    /**
     * Delete the voie by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Voie : {}", id);
        voieRepository.deleteById(id);
    }
}

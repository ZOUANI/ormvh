package com.ormvah.service;

import com.ormvah.domain.LeService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link LeService}.
 */
public interface LeServiceService {

    /**
     * Save a leService.
     *
     * @param leService the entity to save.
     * @return the persisted entity.
     */
    LeService save(LeService leService);

    /**
     * Get all the leServices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LeService> findAll(Pageable pageable);


    /**
     * Get the "id" leService.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LeService> findOne(Long id);

    /**
     * Delete the "id" leService.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

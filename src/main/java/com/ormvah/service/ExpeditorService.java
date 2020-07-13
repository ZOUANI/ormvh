package com.ormvah.service;

import com.ormvah.domain.Expeditor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Expeditor}.
 */
public interface ExpeditorService {

    /**
     * Save a expeditor.
     *
     * @param expeditor the entity to save.
     * @return the persisted entity.
     */
    Expeditor save(Expeditor expeditor);

    /**
     * Get all the expeditors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Expeditor> findAll(Pageable pageable);


    /**
     * Get the "id" expeditor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Expeditor> findOne(Long id);

    /**
     * Delete the "id" expeditor.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

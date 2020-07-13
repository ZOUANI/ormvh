package com.ormvah.service;

import com.ormvah.domain.ExpeditorType;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ExpeditorType}.
 */
public interface ExpeditorTypeService {

    /**
     * Save a expeditorType.
     *
     * @param expeditorType the entity to save.
     * @return the persisted entity.
     */
    ExpeditorType save(ExpeditorType expeditorType);

    /**
     * Get all the expeditorTypes.
     *
     * @return the list of entities.
     */
    List<ExpeditorType> findAll();


    /**
     * Get the "id" expeditorType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ExpeditorType> findOne(Long id);

    /**
     * Delete the "id" expeditorType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

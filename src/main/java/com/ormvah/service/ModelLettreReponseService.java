package com.ormvah.service;

import com.ormvah.domain.ModelLettreReponse;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ModelLettreReponse}.
 */
public interface ModelLettreReponseService {

    /**
     * Save a modelLettreReponse.
     *
     * @param modelLettreReponse the entity to save.
     * @return the persisted entity.
     */
    ModelLettreReponse save(ModelLettreReponse modelLettreReponse);

    /**
     * Get all the modelLettreReponses.
     *
     * @return the list of entities.
     */
    List<ModelLettreReponse> findAll();


    /**
     * Get the "id" modelLettreReponse.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ModelLettreReponse> findOne(Long id);

    /**
     * Delete the "id" modelLettreReponse.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

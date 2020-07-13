package com.ormvah.service;

import com.ormvah.domain.CategorieModelLettreReponse;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CategorieModelLettreReponse}.
 */
public interface CategorieModelLettreReponseService {

    /**
     * Save a categorieModelLettreReponse.
     *
     * @param categorieModelLettreReponse the entity to save.
     * @return the persisted entity.
     */
    CategorieModelLettreReponse save(CategorieModelLettreReponse categorieModelLettreReponse);

    /**
     * Get all the categorieModelLettreReponses.
     *
     * @return the list of entities.
     */
    List<CategorieModelLettreReponse> findAll();


    /**
     * Get the "id" categorieModelLettreReponse.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CategorieModelLettreReponse> findOne(Long id);

    /**
     * Delete the "id" categorieModelLettreReponse.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

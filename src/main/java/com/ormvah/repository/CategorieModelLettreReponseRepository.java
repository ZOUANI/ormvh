package com.ormvah.repository;

import com.ormvah.domain.CategorieModelLettreReponse;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CategorieModelLettreReponse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategorieModelLettreReponseRepository extends JpaRepository<CategorieModelLettreReponse, Long> {
}

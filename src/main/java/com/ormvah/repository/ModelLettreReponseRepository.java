package com.ormvah.repository;

import com.ormvah.domain.ModelLettreReponse;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ModelLettreReponse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModelLettreReponseRepository extends JpaRepository<ModelLettreReponse, Long> {
}

package com.ormvah.repository;

import com.ormvah.domain.Voie;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Voie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoieRepository extends JpaRepository<Voie, Long> {
}

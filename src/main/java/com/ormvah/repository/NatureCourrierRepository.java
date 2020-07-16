package com.ormvah.repository;

import com.ormvah.domain.NatureCourrier;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the NatureCourrier entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NatureCourrierRepository extends JpaRepository<NatureCourrier, Long> {
}

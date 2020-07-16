package com.ormvah.repository;

import com.ormvah.domain.Subdivision;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Subdivision entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubdivisionRepository extends JpaRepository<Subdivision, Long> {
}

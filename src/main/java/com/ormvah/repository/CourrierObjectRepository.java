package com.ormvah.repository;

import com.ormvah.domain.CourrierObject;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CourrierObject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourrierObjectRepository extends JpaRepository<CourrierObject, Long> {
}

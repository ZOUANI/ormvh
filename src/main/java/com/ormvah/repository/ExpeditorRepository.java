package com.ormvah.repository;

import com.ormvah.domain.Expeditor;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Expeditor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExpeditorRepository extends JpaRepository<Expeditor, Long>, JpaSpecificationExecutor<Expeditor> {
}

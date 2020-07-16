package com.ormvah.repository;

import com.ormvah.domain.ExpeditorType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ExpeditorType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExpeditorTypeRepository extends JpaRepository<ExpeditorType, Long> {
}

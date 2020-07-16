package com.ormvah.repository;

import com.ormvah.domain.LeService;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the LeService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeServiceRepository extends JpaRepository<LeService, Long>, JpaSpecificationExecutor<LeService> {
}

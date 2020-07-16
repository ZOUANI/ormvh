package com.ormvah.repository;

import com.ormvah.domain.Bordereau;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Bordereau entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BordereauRepository extends JpaRepository<Bordereau, Long> {
}

package com.ormvah.repository;

import com.ormvah.domain.Courrier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Courrier entity.
 */
@Repository
public interface CourrierRepository extends JpaRepository<Courrier, Long>, JpaSpecificationExecutor<Courrier> {

    @Query(value = "select distinct courrier from Courrier courrier left join fetch courrier.services",
        countQuery = "select count(distinct courrier) from Courrier courrier")
    Page<Courrier> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct courrier from Courrier courrier left join fetch courrier.services")
    List<Courrier> findAllWithEagerRelationships();

    @Query("select courrier from Courrier courrier left join fetch courrier.services where courrier.id =:id")
    Optional<Courrier> findOneWithEagerRelationships(@Param("id") Long id);

}

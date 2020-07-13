package com.ormvah.repository;

import com.ormvah.domain.Task;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Task entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {

    @Query("select task from Task task where task.assigne.login = ?#{principal.username}")
    List<Task> findByAssigneIsCurrentUser();

}

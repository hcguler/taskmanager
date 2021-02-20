package com.hcg.simpletaskmanager.repository;

import com.hcg.simpletaskmanager.entity.TaskEntity;
import com.hcg.simpletaskmanager.entity.TaskLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * JPA repository for Task Domain Object
 */
@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Integer> {

    /**
     * Get parent tasks if state equals open
     * @param pageable
     * @return Page<TaskEntity>
     */
    @Query(value = "SELECT * FROM TASK u WHERE u.state = 1 and u.task_id is null order by task_level asc, created_date desc",
            nativeQuery = true)
    Page<TaskEntity> findAllOpenTasks(Pageable pageable);
    /**
     * Get parent tasks if state equals close
     * @param pageable
     * @return Page<TaskEntity>
     */
    @Query(value = "SELECT * FROM TASK u WHERE u.state = 0 and u.task_id is null order by task_level asc, created_date desc ",
            nativeQuery = true)
    Page<TaskEntity> findAllCloseTasks(Pageable pageable);

    Page<TaskEntity> findAllByTaskLevel(Pageable pageable,TaskLevel taskLevel);


    @Query(value = "SELECT * FROM TASK u WHERE u.task_name LIKE %:taskName% and u.task_id is null order by task_level asc, created_date desc",
            nativeQuery = true)
    Page<TaskEntity> findAllByParentTaskByName(Pageable pageable,String taskName);
}

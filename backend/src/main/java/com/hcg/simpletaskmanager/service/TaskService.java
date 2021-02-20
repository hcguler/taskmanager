package com.hcg.simpletaskmanager.service;

import com.hcg.simpletaskmanager.exception.TaskException;
import com.hcg.simpletaskmanager.model.PageTaskModelList;
import com.hcg.simpletaskmanager.model.TaskModel;
import com.hcg.simpletaskmanager.entity.TaskLevel;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * This interface have all task function with business logic
 */
public interface TaskService {
    /**
     * Save task
     * @param task
     * @return TaskModel
     * @throws TaskException Business logic exception
     */
    TaskModel saveTask(@Valid TaskModel task) throws TaskException;

    /**
     * Update task
     * @param task
     * @return TaskModel
     * @throws TaskException Business logic exception
     */
    TaskModel updateTask(@Valid TaskModel task) throws TaskException;

    /**
     * Get task using id field
     * @param id
     * @return TaskModel
     * @throws TaskException Business logic exception
     */
    TaskModel getTaskById(@Positive Integer id) throws TaskException;

    /**
     * Get all open task with pagination and default sorted
     * @param pageable
     * @return PageTaskModelList
     */
    PageTaskModelList getAllOpenTasks(Pageable pageable);

    /**
     * Get all close task with pagination and default sorted
     * @param pageable
     * @return PageTaskModelList
     */
    PageTaskModelList getAllCloseTasks(Pageable pageable);

    /**
     * Get tasks query with TaskLevel with pagination and default sorted
     * @param pageable
     * @return List<TaskModel>
     */
    List<TaskModel> getTasksByLevel(Pageable pageable, TaskLevel taskLevel);

    /**
     * Delete task using id field
     * @param id
     * @throws TaskException Business logic exception
     */
    void deleteTask(@Positive Integer id) throws TaskException;

    /**
     * Get task page list by taskname
     * @param paging
     * @param taskName
     * @return List<TaskModel>
     */
    List<TaskModel> getTaskPageByTaskName(Pageable paging, String taskName);
}

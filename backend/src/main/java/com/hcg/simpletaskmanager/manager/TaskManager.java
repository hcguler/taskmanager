package com.hcg.simpletaskmanager.manager;

import com.hcg.simpletaskmanager.model.PageTaskModelList;
import com.hcg.simpletaskmanager.model.TaskModel;
import com.hcg.simpletaskmanager.entity.TaskLevel;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * This interface include task domain object provided functions for manage conversion.
 * The main purpose is to separate the business logic from the conversion process.
 */
public interface TaskManager {
    TaskModel saveTask(@Valid TaskModel task);
    TaskModel getTaskById(@Positive Integer id);
    PageTaskModelList getAllOpenTasks(Pageable pageable);
    PageTaskModelList getAllCloseTasks(Pageable pageable);
    List<TaskModel> getTasksByLevel(Pageable pageable, TaskLevel taskLevel);
    void deleteTask(@Positive Integer id);
    List<TaskModel> getTaskPageByTaskName(Pageable paging, String taskName);
}

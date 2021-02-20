package com.hcg.simpletaskmanager.contoller;

import com.hcg.simpletaskmanager.model.PageTaskModelList;
import com.hcg.simpletaskmanager.exception.TaskException;
import com.hcg.simpletaskmanager.model.TaskModel;
import com.hcg.simpletaskmanager.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class implement all provided tasks http method
 */
@RestController
@RequestMapping(path = "/task")
public class TaskControllerImpl implements TaskController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskControllerImpl.class);
    private final TaskService taskService;

    public TaskControllerImpl(TaskService taskService) {
        this.taskService = taskService;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public TaskModel getTaskById(Integer id) throws TaskException {
        LOGGER.info("Called get task by id= " + id);
        return taskService.getTaskById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageTaskModelList getAllOpenTasks(Integer pageNo, Integer pageSize, String sortBy) {
        LOGGER.info("Called get all open tasks");
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        return taskService.getAllOpenTasks(paging);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageTaskModelList getAllCloseTasks(Integer pageNo, Integer pageSize, String sortBy) {
        LOGGER.info("Called get all open tasks");
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        return taskService.getAllCloseTasks(paging);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TaskModel> getTaskPageByTaskName(Integer pageNo, Integer pageSize, String sortBy, String taskName) {
        LOGGER.info("Called get task list by taskName");
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return taskService.getTaskPageByTaskName(paging,taskName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<TaskModel> saveTask(@Valid TaskModel taskModel) throws TaskException {
        TaskModel savedModel = taskService.saveTask(taskModel);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedModel)
                .toUri();
        LOGGER.debug("Add new task.");
        return ResponseEntity.created(location).body(savedModel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<TaskModel> updateTask(@Valid TaskModel taskModel) throws TaskException {
        TaskModel savedModel = taskService.updateTask(taskModel);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedModel)
                .toUri();
        LOGGER.debug("Update task called.");
        return ResponseEntity.created(location).body(savedModel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Boolean> deleteTask(@Positive Integer id) throws TaskException {
        taskService.deleteTask(id);
        Map<String, Boolean> response = new HashMap<>(1);
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}

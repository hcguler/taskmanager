package com.hcg.simpletaskmanager.contoller;

import com.hcg.simpletaskmanager.exception.TaskException;
import com.hcg.simpletaskmanager.model.PageTaskModelList;
import com.hcg.simpletaskmanager.model.TaskModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Map;

/**
 * This class include provided http methods for Task object
 */
@Validated
public interface TaskController {

    /**
     * Get task using id field
     * @param id Task unique identifier
     * @return TaskModel
     */
    @CrossOrigin(origins = "*")
    @GetMapping(value = "/getTask/{id}", produces = MediaType.APPLICATION_JSON_VALUE, name = "getTask")
    TaskModel getTaskById(@PathVariable Integer id) throws TaskException;

    /**
     * Get all open tasks using pagination
     * @param pageNo number of page value
     * @param pageSize number of size for all page data
     * @param sortBy sort field info
     * @return PageTaskModelList
     */
    @CrossOrigin(origins = "*")
    @GetMapping(value = "/getAllOpenTasks", produces = MediaType.APPLICATION_JSON_VALUE, name = "getAllOpenTasks")
    PageTaskModelList getAllOpenTasks(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "task_level") String sortBy);
    /**
     * Get all closed tasks using pagination
     * @param pageNo number of page value
     * @param pageSize number of size for all page data
     * @param sortBy sort field info
     * @return PageTaskModelList
     */
    @CrossOrigin(origins = "*")
    @GetMapping(value = "/getAllCloseTasks", produces = MediaType.APPLICATION_JSON_VALUE, name = "getAllCloseTasks")
    PageTaskModelList getAllCloseTasks(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy);

    /**
     * Get task page list by taskName
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @param taskName
     * @return List<TaskModel>
     */
    @CrossOrigin(origins = "*")
    @GetMapping(value = "/getTaskPageByTaskName", produces = MediaType.APPLICATION_JSON_VALUE, name = "getTaskPageByTaskName")
    List<TaskModel> getTaskPageByTaskName(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "") String taskName);

    /**
     * This method using save task
     * @param taskModel
     * @return ResponseEntity<TaskModel>
     */
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/save/task", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, name = "saveTask")
    ResponseEntity<TaskModel> saveTask(@Valid @RequestBody TaskModel taskModel) throws TaskException;
    /**
     * This method using update task
     * @param taskModel
     * @return ResponseEntity<TaskModel>
     */
    @CrossOrigin(origins = "*")
    @PutMapping(value = "/update/task", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, name = "updateTask")
    ResponseEntity<TaskModel> updateTask(@Valid @RequestBody TaskModel taskModel) throws TaskException;

    /**
     * This method using delete task by id
     * @param id Task identifier
     * @return Map<String, Boolean>
     */
    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "/delete/task/{id}")
    Map<String, Boolean> deleteTask(@Positive @PathVariable Integer id) throws TaskException;
}

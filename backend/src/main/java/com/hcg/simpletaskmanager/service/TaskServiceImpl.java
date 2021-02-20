package com.hcg.simpletaskmanager.service;

import com.hcg.simpletaskmanager.model.PageTaskModelList;
import com.hcg.simpletaskmanager.entity.TaskLevel;
import com.hcg.simpletaskmanager.entity.TaskState;
import com.hcg.simpletaskmanager.exception.TaskException;
import com.hcg.simpletaskmanager.manager.TaskManager;
import com.hcg.simpletaskmanager.model.TaskModel;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;


@Service
public class TaskServiceImpl implements TaskService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskServiceImpl.class);

    public static final int MAX_NUMBER_OF_SUBTASK = 4;

    private final TaskManager taskManager;

    public TaskServiceImpl(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(rollbackFor = {TaskException.class, Exception.class})
    @Override
    public TaskModel saveTask(@Valid TaskModel task) throws TaskException {
        if (ObjectUtils.isEmpty(task.getId()) && ObjectUtils.isEmpty(task.getState())) {
            task.setState(TaskState.OPEN);
        }
        if (!CollectionUtils.isEmpty(task.getSubTaskList()) && task.getSubTaskList().size() > MAX_NUMBER_OF_SUBTASK) {
            throw new TaskException(TaskException.TASK_CAN_NOT_HAVE_MORE_THAN_4_SUBTASK);
        }
        try {
            return taskManager.saveTask(task);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error("Task already exist", e);
            throw new TaskException(TaskException.TASK_NAME_MUST_UNIQUE);
        }catch (Exception e) {
            LOGGER.error("Task does not save", e);
            throw new TaskException(TaskException.UNKOWN_EXCEPTION);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(rollbackFor = {TaskException.class, Exception.class})
    @Override
    public TaskModel updateTask(@Valid TaskModel task) throws TaskException {
        TaskModel oldTaskModel = getTaskById(task.getId());
        if (!oldTaskModel.getTaskName().equals(task.getTaskName())){
            LOGGER.error("Task name can not update");
            throw new TaskException(TaskException.CAN_NOT_CHANGE_TASK_NAME);
        }
        if (!CollectionUtils.isEmpty(task.getSubTaskList()) && task.getSubTaskList().size() > MAX_NUMBER_OF_SUBTASK) {
            throw new TaskException(TaskException.TASK_CAN_NOT_HAVE_MORE_THAN_4_SUBTASK);
        }
        if (TaskState.OPEN.equals(oldTaskModel.getState()) && TaskState.CLOSE.equals(task.getState())) {
            if (!CollectionUtils.isEmpty(task.getSubTaskList())) {
                for (TaskModel subTask : task.getSubTaskList()) {
                    if (TaskState.OPEN.equals(subTask.getState())) {
                        LOGGER.error("Task have open subtask, so can not update close state");
                        throw new TaskException(TaskException.CAN_NOT_CLOSE_PARENT_TASK);
                    }
                }
            }
        } else {
            //Parent task closed but sub task update open state
            if (!CollectionUtils.isEmpty(task.getSubTaskList())) {
                for (TaskModel subTask : task.getSubTaskList()) {
                    if (TaskState.OPEN.equals(subTask.getState())) {
                        task.setState(TaskState.OPEN);
                    }
                }
            }
        }
        try {
            return taskManager.saveTask(task);
        } catch (Exception e) {
            LOGGER.error("Task does not update", e);
            throw new TaskException(TaskException.UNKOWN_EXCEPTION);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public TaskModel getTaskById(@Positive Integer id) throws TaskException {
        try {
            return taskManager.getTaskById(id);
        } catch (Exception e) {
            LOGGER.error("Task does not get", e);
            throw new TaskException(TaskException.NOT_FOUND + " id=" + id);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public PageTaskModelList getAllOpenTasks(Pageable pageable) {
        return taskManager.getAllOpenTasks(pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public PageTaskModelList getAllCloseTasks(Pageable pageable) {
        return taskManager.getAllCloseTasks(pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TaskModel> getTasksByLevel(Pageable pageable, TaskLevel taskLevel) {
        return taskManager.getTasksByLevel(pageable,taskLevel);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void deleteTask(@Positive Integer id) throws TaskException {
        if (ObjectUtils.isNotEmpty(taskManager.getTaskById(id))) {
            taskManager.deleteTask(id);
        } else {
            LOGGER.error("Task not found for delete operation");
            throw new TaskException(TaskException.NOT_FOUND + " id=" + id);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TaskModel> getTaskPageByTaskName(Pageable paging, String taskName) {
        return taskManager.getTaskPageByTaskName(paging,taskName);
    }
}

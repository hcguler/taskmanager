package com.hcg.simpletaskmanager.manager;

import com.hcg.simpletaskmanager.model.PageTaskModelList;
import com.hcg.simpletaskmanager.converter.TaskMapper;
import com.hcg.simpletaskmanager.entity.TaskEntity;
import com.hcg.simpletaskmanager.entity.TaskLevel;
import com.hcg.simpletaskmanager.model.TaskModel;
import com.hcg.simpletaskmanager.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * Include spring context using @Component annotation
 * This class responsible for convert and call repository functions
 */
@Component
public class TaskManagerImpl implements TaskManager {
    private final TaskRepository taskRepository;

    public TaskManagerImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public TaskModel saveTask(@Valid TaskModel task) {
        return TaskMapper.INSTANCE.taskEntityToTaskModel(taskRepository.saveAndFlush(TaskMapper.INSTANCE.taskModelToTaskEntity(task)));
    }

    @Override
    public TaskModel getTaskById(@Positive Integer id) {
        return TaskMapper.INSTANCE.taskEntityToTaskModel(taskRepository.findById(id).get());
    }

    @Override
    public PageTaskModelList getAllOpenTasks(Pageable pageable) {
        Page<TaskEntity> taskEntityPage = taskRepository.findAllOpenTasks(pageable);
        PageTaskModelList pageTaskModelList =  new PageTaskModelList();
        pageTaskModelList.setTotal(taskEntityPage.getTotalElements());
        List<TaskModel> taskModels = TaskMapper.INSTANCE.taskEntityListToTaskModelList(taskEntityPage.getContent());
        pageTaskModelList.setTaskModelList(taskModels);
        return pageTaskModelList;
    }

    @Override
    public PageTaskModelList getAllCloseTasks(Pageable pageable) {
        Page<TaskEntity> taskEntityPage = taskRepository.findAllCloseTasks(pageable);
        PageTaskModelList pageTaskModelList =  new PageTaskModelList();
        pageTaskModelList.setTotal(taskEntityPage.getTotalElements());
        List<TaskModel> taskModels = TaskMapper.INSTANCE.taskEntityListToTaskModelList(taskEntityPage.getContent());
        pageTaskModelList.setTaskModelList(taskModels);
        return pageTaskModelList;
    }

    @Override
    public List<TaskModel> getTasksByLevel(Pageable pageable, TaskLevel taskLevel) {
        return TaskMapper.INSTANCE.taskEntityListToTaskModelList(taskRepository.findAllByTaskLevel(pageable,taskLevel).getContent());
    }

    @Override
    public void deleteTask(@Positive Integer id) {
        taskRepository.deleteById(id);
    }

    @Override
    public List<TaskModel> getTaskPageByTaskName(Pageable paging, String taskName) {
        return TaskMapper.INSTANCE.taskEntityListToTaskModelList(taskRepository.findAllByParentTaskByName(paging,taskName).getContent());
    }
}

package com.hcg.simpletaskmanager.entity;

import com.hcg.simpletaskmanager.base.entity.BaseEntity;
import com.hcg.simpletaskmanager.converter.TaskLevelConverter;
import com.hcg.simpletaskmanager.converter.TaskStateConverter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * This class mapping task domain object to database table
 *
 */
@EntityListeners(TaskEntityListener.class)
@Entity
@Table(name = "TASK")
public class TaskEntity extends BaseEntity {

    @Column(name = "TASK_NAME", unique = true, updatable = false)
    @NotBlank
    @Size(max = 25)
    private String taskName;

    @Column(name = "TASK_DESCRIPTION")
    @Size(max = 100)
    private String taskDescription;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "TASK_ID")
    private List<TaskEntity> subTaskList;

    @Column(name = "STATE")
    @Convert(converter = TaskStateConverter.class)
    @NotNull
    private TaskState state;

    @Column(name = "TASK_LEVEL")
    @Convert(converter = TaskLevelConverter.class)
    @NotNull
    private TaskLevel taskLevel;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public List<TaskEntity> getSubTaskList() {
        return subTaskList;
    }

    public void setSubTaskList(List<TaskEntity> subTaskEntityList) {
        this.subTaskList = subTaskEntityList;
    }

    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    public TaskLevel getTaskLevel() {
        return taskLevel;
    }

    public void setTaskLevel(TaskLevel taskLevel) {
        this.taskLevel = taskLevel;
    }
}

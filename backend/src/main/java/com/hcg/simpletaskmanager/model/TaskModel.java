package com.hcg.simpletaskmanager.model;

import com.hcg.simpletaskmanager.base.model.BaseModel;
import com.hcg.simpletaskmanager.entity.TaskLevel;
import com.hcg.simpletaskmanager.entity.TaskState;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Network transfer object.
 * This class include javax validation for block invalid data persistent execution
 * It is created so that the database structure is not shown in external systems.
 * The projection can be applied even if it is the same in this scenario.
 */
public class TaskModel extends BaseModel {
    @Size(max = 25)
    @NotBlank
    private String taskName;

    @Size(max = 100)
    private String taskDescription;

    private List<TaskModel> subTaskList;

    @NotNull
    private TaskState state = TaskState.OPEN;

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

    public List<TaskModel> getSubTaskList() {
        return subTaskList;
    }

    public void setSubTaskList(List<TaskModel> subTaskList) {
        this.subTaskList = subTaskList;
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

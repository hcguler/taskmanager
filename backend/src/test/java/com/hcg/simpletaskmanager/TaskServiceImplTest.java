package com.hcg.simpletaskmanager;

import com.hcg.simpletaskmanager.configuration.SimpleTaskManagerBootTest;
import com.hcg.simpletaskmanager.entity.TaskLevel;
import com.hcg.simpletaskmanager.entity.TaskState;
import com.hcg.simpletaskmanager.exception.TaskException;
import com.hcg.simpletaskmanager.model.PageTaskModelList;
import com.hcg.simpletaskmanager.model.TaskModel;
import com.hcg.simpletaskmanager.service.TaskService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class TaskServiceImplTest extends SimpleTaskManagerBootTest {

    @Autowired
    private TaskService taskService;

    @Test
    public void simpleTaskSave() {
        try {
            TaskModel savedTask = taskService.saveTask(getTaskModel("simpleTaskSave"));
            Assert.assertTrue(!ObjectUtils.isEmpty(savedTask));
        } catch (TaskException e) {
            Assert.fail();
        }
    }

    @Test
    public void taskNameEmptySaveTest() {
        Assertions.assertThrows(Exception.class, () -> {
            taskService.saveTask(getTaskModel(null));
        });
    }

    @Test
    public void saveTaskDefaultOpen() {
        TaskModel sample = getTaskModel("saveTaskDefaultOpen");
        sample.setState(null);
        try {
            TaskModel taskModel = taskService.saveTask(sample);
            Assert.assertTrue(TaskState.OPEN.equals(taskModel.getState()));
        } catch (TaskException e) {
            Assert.fail();
        }
    }

    @Test
    public void moreThan4SubTaskSaveTest() {
        TaskModel parentTask = getTaskModel("parentTask_moreThan4");
        List<TaskModel> subTaskList = new ArrayList<>();
        subTaskList.add(getTaskModel("subTask1_moreThan4"));
        subTaskList.add(getTaskModel("subTask2_moreThan4"));
        subTaskList.add(getTaskModel("subTask3_moreThan4"));
        subTaskList.add(getTaskModel("subTask4_moreThan4"));
        subTaskList.add(getTaskModel("subTask5_moreThan4"));
        parentTask.setSubTaskList(subTaskList);
        TaskException taskException = Assertions.assertThrows(TaskException.class, () -> {
            taskService.saveTask(parentTask);
        });
        Assert.assertTrue(TaskException.TASK_CAN_NOT_HAVE_MORE_THAN_4_SUBTASK.equals(taskException.getMessage()));
    }


    @Test
    public void updateTaskTest() {
        TaskModel sample = getTaskModel("updateTaskTest");
        try {
            TaskModel savedTask = taskService.saveTask(sample);
            savedTask.setState(TaskState.CLOSE);
            TaskModel updatedTask = taskService.updateTask(savedTask);
            Assert.assertTrue(savedTask.getId() == updatedTask.getId() && TaskState.CLOSE.equals(updatedTask.getState()));
        } catch (TaskException e) {
            Assert.fail();
        }

    }

    @Test
    public void updateTaskNameTest() {
        TaskModel sample = getTaskModel("updateTaskNameTest");
        try {
            TaskModel savedTask = taskService.saveTask(sample);
            savedTask.setTaskName("updateTaskNameTestsample");
            TaskException taskException = Assertions.assertThrows(TaskException.class, () -> {
                taskService.updateTask(savedTask);
            });
            Assert.assertTrue(TaskException.CAN_NOT_CHANGE_TASK_NAME.equals(taskException.getMessage()));
        } catch (TaskException e) {
            Assert.fail();
        }
    }

    @Test
    public void subTaskOpenParentCloseTest() {
        TaskModel parentTask = getTaskModel("parentTask_CloseTest");
        List<TaskModel> subTaskList = new ArrayList<>();
        subTaskList.add(getTaskModel("subtask1_CloseTest"));
        subTaskList.add(getTaskModel("subtask2_CloseTest"));
        subTaskList.add(getTaskModel("subtask3_CloseTest"));
        parentTask.setSubTaskList(subTaskList);
        try {
            TaskModel savedTask = taskService.saveTask(parentTask);
            savedTask.setState(TaskState.CLOSE);
            TaskException taskException = Assertions.assertThrows(TaskException.class, () -> {
                taskService.updateTask(savedTask);
            });
            Assert.assertTrue(TaskException.CAN_NOT_CLOSE_PARENT_TASK.equals(taskException.getMessage()));
        } catch (TaskException e) {
            Assert.fail();
        }

    }

    @Test
    public void openParentTaskAutoTest() {
        TaskModel parentTask = getTaskModel("parentTask_autoOpen");
        parentTask.setState(TaskState.CLOSE);
        List<TaskModel> subTaskList = new ArrayList<>();
        TaskModel subtask1 = getTaskModel("subtask1_autoOpen");
        subtask1.setState(TaskState.CLOSE);
        subTaskList.add(subtask1);
        parentTask.setSubTaskList(subTaskList);
        try {
            TaskModel savedTask = taskService.saveTask(parentTask);
            savedTask.getSubTaskList().get(0).setState(TaskState.OPEN);
            TaskModel updateTask = taskService.updateTask(savedTask);
            Assert.assertTrue(TaskState.OPEN.equals(updateTask.getState()));
        } catch (TaskException e) {
            Assert.fail();
        }

    }

    @Test
    public void cascadeDeleteSubTaskTest() {
        TaskModel parentTask = getTaskModel("parentTask_cascade");
        List<TaskModel> subTaskList = new ArrayList<>();
        subTaskList.add(getTaskModel("subtask1_cascade"));
        subTaskList.add(getTaskModel("subtask2_cascade"));
        subTaskList.add(getTaskModel("subtask3_cascade"));
        subTaskList.add(getTaskModel("subtask4_cascade"));
        parentTask.setSubTaskList(subTaskList);
        try {
            TaskModel savedTask = taskService.saveTask(parentTask);

            int numberOfTask = getNumberOfOpenTask();

            taskService.deleteTask(savedTask.getId());

            int numberOfTaskAfterDelete = getNumberOfOpenTask();

            Assert.assertTrue(numberOfTask - numberOfTaskAfterDelete == 5);
        } catch (TaskException e) {
            Assert.fail();
        }

    }

    @Test
    public void getAllOpenTaskTest() {
        Pageable paging = PageRequest.of(0, 100, Sort.by("id"));
        PageTaskModelList allOpenTasks = taskService.getAllOpenTasks(paging);
        for (TaskModel taskModel : allOpenTasks.getTaskModelList()) {
            Assert.assertTrue(TaskState.OPEN.equals(taskModel.getState()));
        }
    }

    @Test
    public void getAllCloseTaskTest() {
        Pageable paging = PageRequest.of(0, 100, Sort.by("id"));
        PageTaskModelList allCloseTasks = taskService.getAllCloseTasks(paging);
        for (TaskModel taskModel : allCloseTasks.getTaskModelList()) {
            Assert.assertTrue(TaskState.CLOSE.equals(taskModel.getState()));
        }
    }

    @Test
    public void orderTest() {
        TaskModel first_order_task = getTaskModel("first_order_task");
        first_order_task.setTaskLevel(TaskLevel.BLOCKER);
        try {
            TaskModel saved_first_order_task = taskService.saveTask(first_order_task);
            Pageable paging = PageRequest.of(0, 100, Sort.by("id"));
            PageTaskModelList allOpenTasks = taskService.getAllOpenTasks(paging);
            //initialize blocker open task index=0 and latest save blocker index 1 "created_date asc"
            Assert.assertTrue(allOpenTasks.getTaskModelList().get(1).getId().equals(saved_first_order_task.getId()));
        } catch (TaskException e) {
            Assert.fail();
        }
    }

    @Test
    public void getAllCriticalTaskTest() {
        Pageable paging = PageRequest.of(0, 100, Sort.by("id"));
        List<TaskModel> allCriticalTasks = taskService.getTasksByLevel(paging,TaskLevel.CRITICAL);
        for (TaskModel taskModel : allCriticalTasks) {
            Assert.assertTrue(TaskLevel.CRITICAL.equals(taskModel.getTaskLevel()));
        }
    }

    @Test
    public void getTaskByTaskName() {
        Pageable paging = PageRequest.of(0, 100, Sort.by("id"));
        String queryTaskName = "4";
        List<TaskModel> containst4TaskList = taskService.getTaskPageByTaskName(paging, queryTaskName);
        for (TaskModel taskModel : containst4TaskList) {
            Assert.assertTrue(taskModel.getTaskName().contains(queryTaskName));
        }
    }

    private int getNumberOfOpenTask() {
        Pageable paging = PageRequest.of(0, 100, Sort.by("id"));
        PageTaskModelList allOpenTasks = taskService.getAllOpenTasks(paging);
        int numberOfTask = allOpenTasks.getTaskModelList().size();
        for (TaskModel taskModel : allOpenTasks.getTaskModelList()) {
            if (!CollectionUtils.isEmpty(taskModel.getSubTaskList())) {
                numberOfTask += taskModel.getSubTaskList().size();
            }
        }
        return numberOfTask;
    }

    private TaskModel getTaskModel(String taskName) {
        TaskModel taskModel = new TaskModel();
        taskModel.setTaskDescription("Description of task");
        taskModel.setTaskName(taskName);
        taskModel.setState(TaskState.OPEN);
        taskModel.setTaskLevel(TaskLevel.CRITICAL);
        return taskModel;
    }
}

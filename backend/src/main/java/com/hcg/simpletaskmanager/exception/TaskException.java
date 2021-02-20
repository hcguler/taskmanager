package com.hcg.simpletaskmanager.exception;

public class TaskException extends RuntimeException {
    public static final String UNKOWN_EXCEPTION = "Unknow exception";

    public static final String NOT_FOUND = "Task not found";

    public static final String TASK_NAME_MUST_UNIQUE = "Task name must be unique";

    public static final String CAN_NOT_CHANGE_TASK_NAME = "Task name can not updatable";

    public static final String TASK_CAN_NOT_HAVE_MORE_THAN_4_SUBTASK = "Task can not have more then 4 subtask";

    public static final String CAN_NOT_CLOSE_PARENT_TASK = "Cannot close parent task while there is any open subtask";

    public TaskException(String message) {
        super(message);
    }


}

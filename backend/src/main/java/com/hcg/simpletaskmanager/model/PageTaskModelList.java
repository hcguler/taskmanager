package com.hcg.simpletaskmanager.model;

import java.io.Serializable;
import java.util.List;

public class PageTaskModelList implements Serializable {
    private long total;
    private List<TaskModel> taskModelList;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<TaskModel> getTaskModelList() {
        return taskModelList;
    }

    public void setTaskModelList(List<TaskModel> taskModelList) {
        this.taskModelList = taskModelList;
    }
}

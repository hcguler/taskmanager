package com.hcg.simpletaskmanager.entity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;
import java.util.Objects;

public class TaskEntityListener {
    @PrePersist
    @PreUpdate
    private void beforeAnyUpdate(TaskEntity taskEntity) {
        if (Objects.nonNull(taskEntity.getId())) {
            taskEntity.setUpdatedDate(new Date());
            taskEntity.setVersion(taskEntity.getVersion()+1);
        }else{
            taskEntity.setVersion(Long.valueOf(0));
            taskEntity.setCreatedDate(new Date());
        }
    }
}

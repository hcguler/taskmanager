package com.hcg.simpletaskmanager.converter;

import com.hcg.simpletaskmanager.entity.TaskEntity;
import com.hcg.simpletaskmanager.model.TaskModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * This class using converting operation for task domain object
 * Mapstruct does not use reflection, so more giving more performans another converter technics
 * Generate TaskMapperImp in build time build/generated/sources/annotationProcessor/java/main/com/hcg/simpletaskmanager/converter/TaskMapperImpl.java
 */
@Mapper
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper( TaskMapper.class );

    /**
     * Convert single taskModel to taskEntity
     * @param taskModel
     * @return TaskEntity
     */
    TaskEntity taskModelToTaskEntity(TaskModel taskModel);

    /**
     * Convert single taskEntity to taskModel
     * @param taskEntity
     * @return TaskModel
     */
    TaskModel taskEntityToTaskModel(TaskEntity taskEntity);

    /**
     * Convert multiple taskModels to taskEntities
     * @param taskModels
     * @return List<TaskEntity>
     */
    List<TaskEntity> taskModelListToTaskEntityList(List<TaskModel> taskModels);

    /**
     * Convert multiple taskEntities to taskModels
     * @param taskEntityEntities
     * @return List<TaskModel>
     */
    List<TaskModel> taskEntityListToTaskModelList(List<TaskEntity> taskEntityEntities);
}

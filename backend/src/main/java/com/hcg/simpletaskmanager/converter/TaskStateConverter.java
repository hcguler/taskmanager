package com.hcg.simpletaskmanager.converter;

import com.hcg.simpletaskmanager.entity.TaskState;

import javax.persistence.AttributeConverter;

/**
 * We need this converter trace and manage order of enum values
 */
public class TaskStateConverter implements AttributeConverter<TaskState,Integer> {
    @Override
    public Integer convertToDatabaseColumn(TaskState attribute) {
        if (attribute == null)
            return null;

        switch (attribute) {
            case CLOSE:
                return 0;

            case OPEN:
                return 1;

            default:
                throw new IllegalArgumentException(attribute + " not supported.");
        }
    }

    @Override
    public TaskState convertToEntityAttribute(Integer dbData) {
        if (dbData == null)
            return null;

        switch (dbData) {
            case 0:
                return TaskState.CLOSE;

            case 1:
                return TaskState.OPEN;

            default:
                throw new IllegalArgumentException(dbData + " not supported.");
        }
    }
}

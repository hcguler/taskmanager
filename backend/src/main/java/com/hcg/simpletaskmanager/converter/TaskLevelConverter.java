package com.hcg.simpletaskmanager.converter;

import com.hcg.simpletaskmanager.entity.TaskLevel;

import javax.persistence.AttributeConverter;

/**
 * We need this converter trace and manage order of enum values
 */
public class TaskLevelConverter implements AttributeConverter<TaskLevel,Integer> {
    @Override
    public Integer convertToDatabaseColumn(TaskLevel attribute) {
        if (attribute == null)
            return null;

        switch (attribute) {
            case BLOCKER:
                return 1;

            case CRITICAL:
                return 2;

            case HIGH:
                return 3;

            case LOW:
                return 4;

            default:
                throw new IllegalArgumentException(attribute + " not supported.");
        }
    }

    @Override
    public TaskLevel convertToEntityAttribute(Integer dbData) {
        if (dbData == null)
            return null;

        switch (dbData) {
            case 1:
                return TaskLevel.BLOCKER;

            case 2:
                return TaskLevel.CRITICAL;

            case 3:
                return TaskLevel.HIGH;

            case 4:
                return TaskLevel.LOW;


            default:
                throw new IllegalArgumentException(dbData + " not supported.");
        }
    }
}

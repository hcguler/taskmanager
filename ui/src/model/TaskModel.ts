export interface TaskModel {
    "createdDate"?: string;
    "id"?: number;
    "state": string;
    "subTaskList"?: Array<TaskModel>;
    "taskDescription"?: string;
    "taskLevel": string;
    "taskName": string;
    "updatedDate"?: string;
    "version"?: number;
}

import {TaskModel} from "@/model/TaskModel";

export class PageTaskModelList {
    total: number = 0;
    taskModelList: Array<TaskModel> = new Array<TaskModel>();

    constructor(total: number, taskModelList: Array<TaskModel>) {
        this.total = total;
        this.taskModelList = taskModelList;
    }
}

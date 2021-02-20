export abstract class ServiceUrlConstants {
    static readonly BASEURL: string = "http://localhost:8081/task/";
    static readonly GETALLOPENTASK_URL: string = ServiceUrlConstants.BASEURL + "getAllOpenTasks";
    static readonly GETALLCLOSETASK_URL: string = ServiceUrlConstants.BASEURL + "getAllCloseTasks";
    static readonly GETTASKPAGEBYTASKNAME_URL: string = ServiceUrlConstants.BASEURL + "getTaskPageByTaskName";
    static readonly CREATE_TASK_URL: string = ServiceUrlConstants.BASEURL + "save/task";
    static readonly UPDATE_TASK_URL: string = ServiceUrlConstants.BASEURL + "update/task";
    static readonly DELETE_TASK_URL: string = ServiceUrlConstants.BASEURL + "delete/task/";
    static readonly PAGE_SIZE: string = "10";
}

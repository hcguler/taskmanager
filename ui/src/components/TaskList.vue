<template>
  <div class="container-fluid">
    <div class="row ">
      <div class="col-sm-6">
        <div class="card ">
          <div class="row">
            <h5 class="col-4 card-title text-left">Open Tasks</h5>
            <div class="col-8 text-right">
              <button type="button" class="btn btn-outline-primary" style="margin-right: 10px;"
                      @click="onCreateSubTask">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-plus" viewBox="0 0 16 16">
                  <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"/>
                </svg>
                Create Subtask
              </button>
              <button type="button" class="btn btn-primary" @click="onCreateParentTask">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-plus" viewBox="0 0 16 16">
                  <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"/>
                </svg>
                Create Task
              </button>
              <TaskOperationDialog :parent-task-for-sub-task-update="parentTaskForSubTaskUpdate"
                                   :sub-task-mode="isSubTaskMode"
                                   :update-task-model="selectedTask"
                                   :dialog-title="dialogTitle"
                                   dialog-id="taskOperationDialog">
              </TaskOperationDialog>
            </div>
          </div>
          <div class="row">
            <div class="col-12" v-for="taskItem in openedTaskList" :key="taskItem.id">
              <div id="parentTaskDiv" v-on:click="updateParentTask(taskItem)">
                <task :task-item="taskItem">
                  <div class="col-6" v-if="taskItem.subTaskList!=undefined" v-for="subTaskItem of taskItem.subTaskList">
                    <div @click="updateSubtask(subTaskItem)" @click.stop="'parentTaskDiv'">
                      <task :task-item="subTaskItem"></task>
                    </div>
                  </div>
                </task>
              </div>
            </div>
          </div>
          <div class="row" style="align-self: center">
            <div class="col-12">
              <b-pagination
                  v-model="openTaskPage"
                  :total-rows="openedTaskCount"
                  :per-page="pageSize"
                  @input="loadLists"
              ></b-pagination>
            </div>
          </div>
        </div>
      </div>
      <div class="col-sm-6">
        <div class="card ">
          <div class="row">
            <h5 class="col-4 card-title text-left">Closed Tasks</h5>
          </div>
          <div class="row">
            <div class="col-12" v-for="taskItem in closedTaskList" :key="taskItem.id">
              <div id="parentTaskDivClosed" v-on:click="updateParentTask(taskItem)">
                <task :task-item="taskItem">
                  <div class="col-6" v-if="taskItem.subTaskList!=undefined" v-for="subTaskItem of taskItem.subTaskList">
                    <div @click="updateSubtask(subTaskItem)" @click.stop="'parentTaskDivClosed'">
                      <task :task-item="subTaskItem"></task>
                    </div>
                  </div>
                </task>
              </div>
            </div>
          </div>
          <div class="row" style="align-self: center">
            <div class="col-12">
              <b-pagination
                  v-model="closeTaskPage"
                  :total-rows="closedTaskCount"
                  :per-page="pageSize"
                  @input="loadLists"
              ></b-pagination>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

</template>

<script lang="ts">
import {Component, Vue} from 'vue-property-decorator';
import Task from "@/components/Task.vue";
import {TaskModel} from "@/model/TaskModel";
import TaskOperationDialog from '@/components/TaskOperationDialog.vue';
import {ServiceUrlConstants} from "@/model/ServiceUrlConstants";


@Component({
  components: {TaskOperationDialog, Task}
})
export default class TaskList extends Vue {
  private selectedTask: TaskModel;
  private parentTaskForSubTaskUpdate: TaskModel;
  private isSubTaskMode = false;
  private dialogTitle: string='';
  private openTaskPage: number=1;
  private closeTaskPage: number=1;
  private pageSize: number = ServiceUrlConstants.PAGE_SIZE;

  mounted() {
    this.loadLists();
  }

  loadLists() {
    this.$store.dispatch('updateOpenedListAction', this.openTaskPage>0?this.openTaskPage-1:0)
        .then(() => {
        })
        .catch(error => {
          console.error(error)
        })
    this.$store.dispatch('updateClosedListAction', this.closeTaskPage>0?this.closeTaskPage-1:0)
        .then(() => {
        })
        .catch(error => {
          console.error(error)
        })
  }

  get openedTaskCount(): number {
    let openedTaskCount = this.$store.state.openedTaskCount;
    return openedTaskCount.toString();
  }
  get closedTaskCount(): number {
    let closedTaskCount = this.$store.state.closedTaskCount;
    return closedTaskCount.toString();
  }
  get openedTaskList(): Array<TaskModel> {
    let openedTaskList = this.$store.state.openedTaskList;
    return openedTaskList;
  }

  get closedTaskList(): Array<TaskModel> {
    let closedTaskList = this.$store.state.closedTaskList;
    return closedTaskList;
  }

  updateParentTask(item: TaskModel) {
    this.selectedTask = item;
    this.isSubTaskMode = false;
    this.dialogTitle = item.taskName;
    this.$bvModal.show('taskOperationDialog');
  }

  updateSubtask(item: TaskModel) {
    this.openedTaskList.forEach((parent) => {
      if (parent.subTaskList?.includes(item)) {
        this.parentTaskForSubTaskUpdate = parent;
      }
    })
    this.closedTaskList.forEach((parent) => {
      if (parent.subTaskList?.includes(item)) {
        this.parentTaskForSubTaskUpdate = parent;
      }
    })
    this.isSubTaskMode = true;
    this.selectedTask = item;
    this.dialogTitle = this.parentTaskForSubTaskUpdate.taskName + '->' + item.taskName
    this.$bvModal.show('taskOperationDialog');
  }

  onCreateParentTask() {
    this.dialogTitle = "Create New Task"
    this.isSubTaskMode = false;
    this.selectedTask = {
      "taskName": "",
      "taskDescription": "",
    } as TaskModel;
    this.$bvModal.show('taskOperationDialog');
  }

  onCreateSubTask() {
    this.dialogTitle = "Create New Sub Task"
    this.isSubTaskMode = true;
    this.selectedTask = {
      "taskName": "",
      "taskDescription": "",
    } as TaskModel;
    this.$bvModal.show('taskOperationDialog');
  }
}

</script>

<style scoped lang="sass">
@import '../css/app'
</style>

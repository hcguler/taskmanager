<template>
  <b-modal
      :id="dialogId"
      ref="modal"
      :title="dialogTitle"
      hide-footer
      @ok="saveTask"
  >
    <b-alert
        :show="showAlertFlag"
        @dismissed="showAlertFlag=false"
        dismissible
        variant="danger"
    >
      {{ alertMessage }}
    </b-alert>
    <span v-if="subTaskMode">Parent Task</span>
    <b-form-input v-if="subTaskMode && taskModel.id" v-model="parentTaskForSubTaskUpdate.taskName"
                  class="marginBottomSpace"
                  :readonly="taskModel.id>0"
    ></b-form-input>
    <div v-if="subTaskMode && !taskModel.id" class="marginBottomSpace">
      <b-form-input list="my-list-id"
                    placeholder="Select Parent Task"
                    v-model="searchText"
                    autocomplete="off"
                    @input="onChangeDropDownText"></b-form-input>
      <datalist id="my-list-id" v-model="selectedParent">
        <option v-for="task in allParentTaskList">{{ task.taskName }}</option>
      </datalist>
    </div>
    <span>Task Name</span>
    <b-form-input v-model="taskModel.taskName"
                  :readonly="taskModel.id>0"
                  class="marginBottomSpace"
                  placeholder="Please enter new task name"
    ></b-form-input>
    <span v-if="taskModel.id>0">State</span>
    <div v-if="taskModel.id>0" class="marginBottomSpace">
      <b-button :class="selectedState==='OPEN'?'active':''"
                class="rightSpaceForButton"
                @click="onSelectState('OPEN')" variant="outline-primary">Open
      </b-button>
      <b-button :class="selectedState==='CLOSE'?'active':''"
                @click="onSelectState('CLOSE')" variant="outline-primary">Close
      </b-button>
    </div>
    <span>Level</span>
    <div class="marginBottomSpace">
      <b-button :class="selectedTaskLevel==='BLOCKER'?'active':''"
                class="rightSpaceForButton"
                @click="onSelectLevel('BLOCKER')" variant="outline-danger">Blocker
      </b-button>
      <b-button :class="selectedTaskLevel==='CRITICAL'?'active':''"
                class="rightSpaceForButton"
                @click="onSelectLevel('CRITICAL')" variant="outline-warning">Critical
      </b-button>
      <b-button :class="selectedTaskLevel==='HIGH'?'active':''"
                class="rightSpaceForButton"
                @click="onSelectLevel('HIGH')" variant="outline-info">High
      </b-button>
      <b-button :class="selectedTaskLevel==='LOW'?'active':''"
                class="rightSpaceForButton"
                @click="onSelectLevel('LOW')" variant="outline-success">Low
      </b-button>
    </div>
    <div class="row">
      <div class="col-6">
        <span class="text-left">Task Description</span>
      </div>
      <div class="col-6 text-right">
        <span>{{ getCount(taskModel.taskDescription) }}/100</span>
      </div>
    </div>
    <b-form-textarea
        class="marginBottomSpace"
        id="textarea"
        v-model="taskModel.taskDescription"
        placeholder="Please enter new task description"
        rows="3"
        max="100"
        max-rows="6"
    ></b-form-textarea>
    <b-button v-if="!taskModel.id" :disabled="isFormNotValid"
              class="mt-3 btn btn-success" block @click="saveTask">Create Task
    </b-button>
    <div class="row">
      <div class="col-6">
        <b-button v-if="taskModel.id>0" :disabled="isFormNotValid"
                  class="btn btn-success" block @click="updateTask">Update Task
        </b-button>
      </div>
      <div class="col-6">
        <b-button v-if="taskModel.id>0" :disabled="isFormNotValid"
                  class="btn btn-danger" block @click="deleteTask">Delete Task
        </b-button>
      </div>
    </div>
  </b-modal>
</template>

<script lang="ts">
import {Component, Prop, Vue, Watch} from 'vue-property-decorator';
import {TaskModel} from "@/model/TaskModel";
import axios from "axios";
import {ServiceUrlConstants} from "@/model/ServiceUrlConstants";

@Component({
  components: {TaskOperationDialog}
})
export default class TaskOperationDialog extends Vue {
  @Prop() private dialogId: string;
  @Prop() private dialogTitle: string;
  @Prop() private subTaskMode: boolean = false;
  @Prop() private updateTaskModel: TaskModel;
  @Prop() private parentTaskForSubTaskUpdate: TaskModel;
  private taskModel: TaskModel = {
    "taskName": "",
    "taskDescription": "",
  } as TaskModel;
  private searchText: string = ""
  private selectedParent: TaskModel = {
    "taskName": "",
    "taskDescription": "",
  } as TaskModel;
  private selectedTaskLevel: string = ""
  private selectedState: string = ""
  private allParentTaskList = new Array<TaskModel>()
  private showAlertFlag: boolean = false
  private alertMessage: string = ""

  get isFormNotValid(): boolean {
    if (!this.taskModel.taskName)
      return true
    if (!this.taskModel.taskDescription)
      return true
    if (!this.taskModel.taskLevel)
      return true
    if (this.taskModel.taskName.trim().length === 0)
      return true
    if (this.taskModel.taskDescription.trim().length === 0)
      return true
    if (this.taskModel.taskLevel.trim().length === 0)
      return true
    if ('Create New Sub Task' === this.dialogTitle && !this.selectedParent && !this.selectedParent.id)
      return true
    return false

  }

  @Watch('updateTaskModel')
  nameChanged(newVal: TaskModel) {
    this.initializeData();
  }

  onChangeDropDownText() {
    /**
     * minimum 3 character execute search event
     */
    if (this.searchText.length > 2) {
      axios.get(ServiceUrlConstants.GETTASKPAGEBYTASKNAME_URL + '?pageNo=0&pageSize=' + ServiceUrlConstants.PAGE_SIZE + '&sortBy=id&' +
          'taskName=' + this.searchText)
          .then((response) => {
            this.allParentTaskList = new Array<TaskModel>();
            response.data.forEach(item => {
              this.allParentTaskList.push(item);
            })
          });
    }
  }

  initializeData() {
    this.showAlertFlag = false;
    this.searchText = '';
    this.selectedParent = {
      "taskName": "",
      "taskDescription": "",
    } as TaskModel;
    this.allParentTaskList =  new Array<TaskModel>()
    if (this.updateTaskModel) {
      if (this.subTaskMode) {
        this.taskModel = this.updateTaskModel;
        this.selectedTaskLevel = this.updateTaskModel.taskLevel;
        this.selectedState = this.updateTaskModel.state;
      } else {
        this.taskModel = this.updateTaskModel;
        this.selectedTaskLevel = this.updateTaskModel.taskLevel;
        this.selectedState = this.updateTaskModel.state;
      }
    }
  }

  updateAllList() {
    this.$store.dispatch('updateOpenedListAction', 0)
        .then(() => {
        })
        .catch(error => {
          console.error(error)
        })
    this.$store.dispatch('updateClosedListAction', 0)
        .then(() => {
        })
        .catch(error => {
          console.error(error)
        })
  }

  saveTask(bvModalEvt) {
    bvModalEvt.preventDefault()

    if ('Create New Sub Task' === this.dialogTitle) {

      this.allParentTaskList.forEach(item=>{
        if (item.taskName.includes(this.searchText)){
          this.selectedParent = item;
        }
      })
      if(!this.selectedParent || !this.selectedParent.id){
        this.alertMessage = "Parent selection must";
        this.showAlertFlag = true;
      }else{
        if (!this.selectedParent.subTaskList) {
          this.selectedParent.subTaskList = new Array<TaskModel>();
        }
        this.selectedParent.subTaskList.push(this.taskModel);
        this.updateTaskUsingApi(this.selectedParent);
      }
    }else{
      this.saveTaskUsingApi(this.taskModel);
    }
  }

  private saveTaskUsingApi(task:TaskModel) {
    axios.post(ServiceUrlConstants.CREATE_TASK_URL, task)
        .then((response) => {
          this.updateAllList();
          this.$nextTick(() => {
            this.$bvModal.hide(this.dialogId)
          })
        }, (error) => {
          this.extracted(error);
        });
  }
  private updateTaskUsingApi(task:TaskModel) {
    axios.put(ServiceUrlConstants.UPDATE_TASK_URL, task)
        .then((response) => {
          this.updateAllList();
          this.$nextTick(() => {
            this.$bvModal.hide(this.dialogId)
          })
        }, (error) => {
          this.extracted(error);
        });
  }

  private extracted(error) {
    if (error.response) {
      this.alertMessage = error.response.data.errorMessage;
      this.showAlertFlag = true;
    }
  }

  updateTask() {
    if (this.subTaskMode){
      this.updateTaskUsingApi(this.parentTaskForSubTaskUpdate)
    }else{
      this.updateTaskUsingApi(this.taskModel)
    }
  }

  deleteTask() {
    axios.delete(ServiceUrlConstants.DELETE_TASK_URL + this.taskModel.id)
        .then((response) => {
          this.updateAllList();
          this.$nextTick(() => {
            this.$bvModal.hide(this.dialogId)
          })
        }, (error) => {
          this.extracted(error);
        });
  }

  getCount(description: string) {
    if (description) {
      return 100 - description.length;
    }
    if (this.updateTaskModel) {
      return 100 - this.updateTaskModel.taskDescription?.length;
    }
    return 100;
  }

  onSelectLevel(level: string) {
    this.selectedTaskLevel = level
    this.taskModel.taskLevel = level
  }

  onSelectState(state: string) {
    this.selectedState = state
    this.taskModel.state = state
  }
}
</script>

<style scoped lang="sass">
@import '../css/app'
</style>

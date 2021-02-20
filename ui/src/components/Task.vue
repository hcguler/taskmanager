<template>
  <div class="card" :class="taskItem.state === 'OPEN'?taskItem.taskLevel:taskItem.state">
    <div class="card-body">
      <div class="container">
        <div class="row">
          <div class="col-8">
            <p class="card-title">{{ taskItem.taskName }}</p>
            <p class="card-subtitle mb-2 text-muted">{{ taskItem.taskDescription }}</p>
          </div>
          <div class="col-4 text-right">
            <div v-if="taskItem.state==='CLOSE'" class="btn btn-closed rightSpaceForButton">{{ taskItem.state }}</div>
            <div v-if="taskItem.taskLevel==='BLOCKER'" class="btn btn-blocker">{{ taskItem.taskLevel }}</div>
            <div v-if="taskItem.taskLevel==='CRITICAL'" class="btn btn-critical">{{ taskItem.taskLevel }}</div>
            <div v-if="taskItem.taskLevel==='HIGH'" class="btn btn-high">{{ taskItem.taskLevel }}</div>
            <div v-if="taskItem.taskLevel==='LOW'" class="btn btn-low">{{ taskItem.taskLevel }}</div>
          </div>
        </div>
      </div>
    </div>
    <div class="container">
      <div class="row">
        <slot></slot>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import {Component, Prop, Vue} from 'vue-property-decorator';
import {TaskModel} from "../model/TaskModel";

@Component({
})
export default class Task extends Vue {
  @Prop() private taskItem: TaskModel;
}
</script>

<style scoped lang="sass">
@import '../css/app'
</style>

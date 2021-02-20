<template>
  <div id="app" class="w-100">
    <header class="headerMainPage">
      <div>
        My Task Manager
      </div>
    </header>
    <TaskList/>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import TaskList from './components/TaskList.vue';

@Component({
  components: {
    TaskList,
  },
})
export default class App extends Vue {}
</script>
<style lang="sass">
@import './css/app'
</style>

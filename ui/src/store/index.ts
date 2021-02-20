import Vue from 'vue'
import Vuex from 'vuex'
import {TaskModel} from "@/model/TaskModel";
import axios from "axios";
import {ServiceUrlConstants} from "@/model/ServiceUrlConstants";
import {PageTaskModelList} from "@/model/PageTaskModelList";

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        openedTaskList: Array<TaskModel>(),
        closedTaskList: Array<TaskModel>(),
        openedTaskCount: Object,
        closedTaskCount: Object,
    },
    mutations: {
        updateOpenedList(state, pageTask: PageTaskModelList) {
            state.openedTaskList = pageTask.taskModelList as Array<TaskModel>
            state.openedTaskCount = pageTask.total
        },
        updateClosedList(state, pageTask: PageTaskModelList) {
            state.closedTaskList = pageTask.taskModelList as Array<TaskModel>
            state.closedTaskCount =  pageTask.total
        },
    },
    actions: {
         updateOpenedListAction(context, pageNo) {
             axios.get(ServiceUrlConstants.GETALLOPENTASK_URL + '?pageNo=' + pageNo + '&pageSize='+ServiceUrlConstants.PAGE_SIZE+'&sortBy=id')
                .then((response) => {
                    context.commit('updateOpenedList', response.data as PageTaskModelList);
                });
        },
        async updateClosedListAction({commit}, pageNo) {
            await axios.get(ServiceUrlConstants.GETALLCLOSETASK_URL + '?pageNo=' + pageNo + '&pageSize='+ServiceUrlConstants.PAGE_SIZE+'&sortBy=id')
                .then((response) => {
                    commit('updateClosedList', response.data as PageTaskModelList);
                });
        },
    },
    modules: {}
})

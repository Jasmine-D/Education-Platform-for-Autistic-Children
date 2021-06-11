<template>
  <div class="bg_img">
    <Header :tab="headerName"/>
    <div style="margin-left:18%;">
      <div style="float:left;width:20%;">
        <Menu :link="link"/>
      </div>
      <div style="float:left;width:80%;">  
          <div class="q-my-xl" style="width:80%; height:fit-content; margin-left:10%; text-align:center">
            <q-table
              :data="reportList"
              :columns="columns"
              row-key="scene_name"
              no-data-label="暂时还没有相关学习报告呢~~赶快去学习吧~"
              :loading="loading"
            >
               <template v-slot:loading>
                <q-inner-loading showing color="primary" />
              </template>
              <div slot="top" class="row flex-center fit">
                <p class="text-h5 text-purple" style="margin-bottom:0px">学习报告</p>
              </div>
              <!-- slot name syntax: body-cell-<column_name> -->
              <q-td slot="body-cell-operation" slot-scope="props" :props="props">
                <q-btn flat dense size="md" color="purple" @click="viewReport(props.rowIndex)">{{ props.value }}</q-btn>
              </q-td>
              <q-td slot="body-cell-score" slot-scope="props" :props="props">
                <q-rating
                    v-model="props.value"
                    size="1.5em"
                    color="grey"
                    :color-selected="ratingColors"
                    :max="5"
                    icon="pets"
                    readonly
                />
              </q-td>
            </q-table>
          </div>
      </div>
      <div style="clear:both;"></div> 
    </div>
    <Report :popUp="popUp"
            :close="closeReport"
            :sceneName="scene_name"
            :words="words"
            :report="reportDetail"
    ></Report>   
  </div>
</template>

<style scoped>
  .bg_img {
    width: 100%;
    height: 100%;
    min-height: 750px;
    position:relative;
  }

  .bg_img:before {
    background-image: url(../assets/fruites.png);
    background-size: cover;
    background-color: #fff;   
    width: 100%;
    height: 100%;
    content: "";  /* 这个不要漏了 */
    position: absolute;
    top: 0;
    left: 0;
    z-index: -1; /*-1 可以当背景*/
    opacity: 0.5
  }
</style>

<script>
import Header from '@/components/Header.vue'
import Menu from '@/components/Menu.vue'
import Report from '@/components/Report.vue'
import {getAllReport} from '../api/report'
export default {
  components: { 
    Header,
    Menu,
    Report
  },
  data () {
    return {
      link:'Report',
      headerName:'Account',
      user:'',
      loading:true,
      //showScene:false,
      //showLoad:true,
      total:0,
      popUp:false,
      words:'',
      reportDetail:'',
      scene_name:'',
      columns: [
        {
          name: 'scene_name',
          required: true,
          label: '学习场景',
          align: 'left',
          field: 'scene_name',
          headerClasses: 'bg-purple-5 text-white'
        },
        { 
          name: 'creator', 
          align: 'left', 
          label: '场景发布者', 
          field: 'creator', 
          headerClasses: 'bg-purple text-white'
        },
        { 
          name: 'time', 
          align: 'left', 
          label: '学习日期', 
          field: 'time', 
          headerClasses: 'bg-purple-5 text-white',
          sortable: true 
        },
        { 
          name: 'teacher', 
          align: 'left', 
          label: '辅导老师', 
          field: 'teacher',
          headerClasses: 'bg-purple text-white'
        },
        { 
          name: 'score', 
          align: 'left', 
          label: '老师评分', 
          field: 'score',
          headerClasses: 'bg-purple-5 text-white', 
          sortable: true 
        },
        { 
          name: 'operation', 
          align: 'left', 
          label: '操作', 
          field: 'operation',
          headerClasses: 'bg-purple text-white' 
        }
      ],
      reportList: [],
      ratingColors:['pink-3','pink-5','orange-8','deep-orange-13','red-14']
    }
  },
  methods:{
    async getUserReport(){
      //this.showScene=false,
      //this.showLoad=true,
      this.loading=true;
      getAllReport({user_id:this.user.user_id}).then((response)=>{
        console.log(response,"getAllReport");
        this.total=response.data.total;
        if(this.total==0){
          this.reportList=[];
        }else{
          this.reportList=response.data.reports;
        }
        this.loading=false;
        //this.showScene=true;
        //this.showLoad=false;
      });
    },
    viewReport(index){
      this.words=this.reportList[index].tags;
      this.reportDetail=this.reportList[index];
      this.scene_name=this.reportList[index].scene_name;
      this.popUp=true;
    },
    closeReport(f){
        this.popUp=f;
    },
  },
  created(){
    this.user=JSON.parse(sessionStorage.getItem('user'));
    this.getUserReport();
  }
}
</script>
<template>
    <div class="scene_list">
        <Header :tab="headerName"/>
        <transition
          appear
          enter-active-class="animated fadeIn"
          leave-active-class="animated fadeOut"
        >
        <div class="cards" v-show="showScene">
            <div v-if="flag==0" style="font-size:18px;margin-left:2%">一共{{total}}个场景，请选择感兴趣的场景进行学习吧~~</div>
            <div v-else-if="totalPages>0" style="font-size:18px;margin-left:2%">场景搜索结果如下(共{{total}}条)：</div>
            <div class="row">
                <div class="col-md-4 col-xs-12" v-for="(scene,index) in sceneList" :key="index">
                    <SceneCard  :scene="scene" :index="index"  :setPrompt="openValidate" :cardType="cardType" @setIndex="targetScene"></SceneCard>
                </div>
            </div>
            <div v-if="totalPages==0" style="margin:0% 5%;font-size:18px;">抱歉，什么也没有呢~~</div>
            <div class="flex flex-center pag">
                <q-pagination v-if="totalPages>0"
                v-model="currentPage"
                :max="totalPages"
                :max-pages="5"
                color="primary"
                :boundary-numbers="true"
                round push
                size="20px"
                ></q-pagination>
            </div>
        </div>
        </transition>
        <q-inner-loading :showing="showLoad">
           <q-spinner-gears size="50px" color="primary" />
        </q-inner-loading>
        <PasswordValidate :prompt="prompt"  :scene="sceneList[index]" :closeDialog="close" :setCollect="setCollect"></PasswordValidate>
    </div>
</template>

<script>
import Header from '@/components/Header.vue';
import SceneCard from '@/components/SceneCard.vue';
import PasswordValidate from '@/components/PasswordValidate.vue'
import { getAllScene ,searchScene} from '../api/scene'
export default {
    components: { 
        Header,
        SceneCard,
        PasswordValidate
    },
    data(){
        return{
            headerName:'SceneList',
            showScene:false,
            showLoad:true,
            prompt:false,
            index:'',
            currentPage:1,
            cardType:1,
            totalPages:0,
            pageSize:3,
            total:0,
            user:{},
            flag:0,
            key:'',
            //makeCollect:false,
            sceneList:[]
        }
    },
    methods:{
        openValidate(t){
            this.prompt=t;
            console.log(this.prompt);
        },
        targetScene(i){
            this.index=i;
            console.log("target:"+this.index);
        },
        close(f){
            this.prompt=f;
        },
        setCollect(t){
            this.sceneList[this.index].is_collect=t;
            //this.makeCollect=t;
        },
        async getSceneList(){
            if(this.flag==1){
                this.currentPage=1;
            }
            this.flag=0;
            this.showScene=false;
            this.showLoad=true;
            getAllScene({
                currentPage:this.currentPage,
                pageSize:this.pageSize,
                user_id:this.user.user_id
            }).then((response)=>{
                console.log(response,"getSceneList");
                if(response.data.total==0){
                    this.totalPages=0;
                    this.total=0;
                    this.sceneList=[];
                }else{
                    this.sceneList=response.data.scenes;
                    this.totalPages=Math.ceil(response.data.total/this.pageSize);
                    this.total=response.data.total;
                }
               this.showLoad=false;
               this.showScene=true;
            });
        },
        async searchSceneByName(text){
            if(this.flag==0){
                this.currentPage=1;
            }
            this.flag=1;
            this.key=text;
            this.showScene=false;
            this.showLoad=true;
            searchScene({
                currentPage:this.currentPage,
                pageSize:this.pageSize,
                user_id:this.user.user_id,
                key:text
            }).then((response)=>{
                console.log(response,"searchScene");
                if(response.data.total==0){
                    this.totalPages=0;
                    this.sceneList=[];
                    this.total=0;
                }else{
                    this.sceneList=response.data.scenes;
                    this.totalPages=Math.ceil(response.data.total/this.pageSize);
                    this.total=response.data.total;
                }
                this.showLoad=false;
                this.showScene=true;
                sessionStorage.removeItem('searchScene');
            })
        }
    },
    watch:{
        currentPage(){
            if(this.flag==0){
                this.getSceneList();
            }else{
                this.searchSceneByName(this.key);
            }
           
        },
        key(){
            this.currentPage=1;
        }
    },
    computed:{

    },
    created(){
        this.user=JSON.parse(sessionStorage.getItem('user'));
        console.log(this.user);
        this.showScene=false;
        this.showLoad=true;
        this.key=sessionStorage.getItem('searchScene');
        if(this.key!==''&&this.key!=null){
            this.searchSceneByName(this.key);
        }else{
            this.getSceneList();
        }
       //console.log(this.$route);
    }
}
</script>

<style>
.scene_list .cards{
    background-image: url(../assets/fruites1.png);
    background-size: cover;
    padding:2% 0% 2% 0%;
    min-width: 1000px;
    min-height: 600px;
    background-color: #fff;
    width: 100%;
    height: 100%;
}

.scene_list .pag{
    margin-left: 8%;
}

</style>
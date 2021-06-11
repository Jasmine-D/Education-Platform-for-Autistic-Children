<template>
    <div class="myCollection">
        <Header :tab="headerName"></Header>
        <div style="margin-left:18%;">
            <div style="float:left;width:20%;">
                <Menu :link="link"/>
            </div>
            
            <div style="float:left;width:80%;">
                <div style="margin-top:2%;">
                    <div style="text-align:center;width:100%">
                        <q-btn-toggle
                            v-model="select"
                            color="purple-3"
                            text-color="grey-3"
                            toggle-color="purple-5"
                            toggle-text-color="orange-7"
                            rounded
                            :options="[
                            {slot:'one', value: 0},
                            {slot: 'two', value: 1},
                            ]" size="lg">
                            <template v-slot:one>
                                <div class="row items-center no-wrap">
                                    <div class="text-center">
                                    收藏的单词集
                                    </div>
                                    <q-icon left name="style" />
                                </div>
                            </template>

                            <template v-slot:two>
                                <div class="row items-center no-wrap">
                                    <div class="text-center">
                                    收藏的场景
                                    </div>
                                    <q-icon left name="emoji_nature" />
                                </div>
                            </template>

                        </q-btn-toggle>
                    </div>
                    <transition
                        appear
                        enter-active-class="animated fadeIn"
                        leave-active-class="animated fadeOut"
                    >
                        <div  v-show="showScene">
                            <div style="margin-top:2%;" class="row" v-if="select==1">
                                <div v-if="totalPages==0" style="margin:0% 20%;font-size:18px;">收藏夹什么也没有哦~~赶快去收藏感兴趣的内容吧！</div>
                                <div class="col-md-6 col-xs-12" v-for="(scene,index) in collectSceneList" :key="index">
                                    <SceneCard  
                                      :scene="scene" 
                                      :index="index" 
                                      :setPrompt="openValidate" 
                                      :cardType="cardType" 
                                      @setIndex="targetScene" 
                                      :refresh="refreshCard">
                                    </SceneCard>
                                </div>
                                <div style="clear:both;"></div>
                            </div>
                            <div style="margin-top:2%;" v-if="select==0">
                              <div v-if="totalPages==0" style="margin:0% 20%;font-size:18px;">收藏夹什么也没有哦~~赶快去收藏感兴趣的内容吧！</div>
                                <div class="q-pb-lg row" style="display:flex;justify-content:center" v-for="(setInfo,index) in setListInfo" :key="index">
                                  <WordSet
                                    :isVisible="setInfo.is_visible"
                                    :setName="setInfo.set_name"
                                    :setCreator="setInfo.set_creator"
                                    :cardNum="setInfo.card_num"
                                    :setContent="setInfo.set_intro"
                                    :setId="setInfo.set_id"
                                    :isStar="setInfo.is_star"
                                    :type=1>
                                  </WordSet>
                                </div>
                                <div style="clear:both;"></div>
                            </div>
                        </div>
                    </transition>
                    <q-inner-loading :showing="showLoad">
                        <q-spinner-gears size="50px" color="primary" />
                    </q-inner-loading>
                </div>
            </div>
            <div style="clear:both;"></div>
            
        </div>
        <PasswordValidate :prompt="prompt" :scene="collectSceneList[index]" :closeDialog="close" :setCollect="setCollect"></PasswordValidate>
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
</template>

<script>
import Header from '@/components/Header.vue'
import Menu from '@/components/Menu.vue'
import SceneCard from '@/components/SceneCard.vue'
import PasswordValidate from '@/components/PasswordValidate.vue'
import WordSet from '@/components/WordSet.vue'
import {getCollectScene} from '../api/scene'
import {myStarSet} from '../api/wordset'
export default {
    components: { 
        Header,
        Menu,
        SceneCard,
        PasswordValidate,
        WordSet
    },
    data(){
        return{
            headerName:'Account',
            link:'MyCollection',
            showScene:false,
            showLoad:true,
            currentPage:1,
            pageSize:2,
            total:0,
            totalPages:0,
            prompt:false,
            user:{},
            index:'',
            select:1,
            cardType:2,
            //makeCollect:false,
            collectSceneList:[],

            pageSizeforSet:5,
            setListInfo:[],

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
        refreshCard(){
            this.currentPage=1;
            this.total=0;
            this.getCollect();
        },
        async getCollect(){
            this.showScene=false;
            this.showLoad=true;
            getCollectScene({
                currentPage:this.currentPage,
                pageSize:this.pageSize,
                user_id:this.user.user_id
            }).then((response)=>{
                console.log(response,"getCollectScene");
                if(response.data.total==0){
                    this.totalPages=0;
                    this.total=0;
                    this.collectSceneList=[];
                }else{
                    this.collectSceneList=response.data.scenes;
                    this.totalPages=Math.ceil(response.data.total/this.pageSize);
                    this.total=response.data.total;
                }
                this.showLoad=false;
                this.showScene=true;
            });
        },

        async getSet() {
            myStarSet(this.currentPage,this.pageSizeforSet,this.user.user_id)
              .then((response)=>{
                this.setListInfo=response.data.myStarWordSet;
                this.total=response.data.total;
                this.totalPages=Math.ceil(this.total/this.pageSizeforSet)
              }).catch((error)=>{
                console.log(error)
            })
        }
        
        
    },
    watch:{
        currentPage(){
            if(this.select==1){
                this.getCollect();
            }
           
        },
        select(){
            this.currentPage=1;
            this.total=0;
            if(this.select==1){
                this.getCollect();
            } else {
                this.getSet();
            }
        }
    },
    created(){
        this.user=JSON.parse(sessionStorage.getItem('user'));
        this.showScene=false;
        this.showLoad=true;
        if(this.select==1){
            this.getCollect();
        }
    }
}
</script>

<style>
.myCollection .pag{
    margin-top: 2%;
    margin-bottom: 3%;
    margin-left:35%;
}
.myCollection{
    width: 100%;
    height: 100%;
    min-height: 800px;
    position:relative;
}
.myCollection:before{
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
.my-card {
    width: 70%;
    text-align: center;
}
</style>
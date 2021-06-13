<template>
    <div class="scene">
        <Header :tab="headerName"/>
        <transition
          appear
          enter-active-class="animated fadeIn"
          leave-active-class="animated fadeOut"
        >
        <div v-bind:class="part1?'details1':'details2'" v-show="showScene">
            <div class="title text-h5 q-mb-lg">
                情景主题:{{scene_name}}
                <q-btn flat round :color="is_collect?'red':'grey'" icon="favorite" @click="collectNot()" size="lg"/>
            </div>
            <div class="q-ma-md step"> <!--步进器-->
                <q-stepper
                    v-model="step"
                    animated
                    flat
                    class="stepper">
                    <q-step
                        :name="1"
                        title="学习环节"
                        icon="menu_book"
                        active-icon="menu_book"
                        done-icon="menu_book"
                        :done="step>0"></q-step>
                        
                    <q-step
                        :name="2"
                        title="测试环节"
                        icon="quize"
                        active-icon="quize"
                        done-icon="quize"
                        :done="step>1"></q-step>

                    <q-step
                        :name="3"
                        title="生成报告"
                        icon="description"
                        active-icon="description"
                        done-icon="description"
                        ></q-step>
                </q-stepper>
            </div>
            <div class="body row" v-if="part1&&scenePic[0]"> <!--学习环节-->
                <div class="col-md-1 leftButton">
                    <q-btn round flat :color="currentPage==1?'grey':'purple-7'" icon="arrow_back" class="arrow" :disable="currentPage==1" @click="back()"/>
                </div>
                <div class="col-md-10 content">
                    <div class="pic left"> 
                        <img :src="scenePic[currentPage-1].pic_url" class="scenePic" />
                        <div class="btn">
                            <q-btn rounded color="blue" icon="mic" label="开始录音" v-if="!is_record" class="button" @click="startRecord()"/>
                            <q-btn rounded color="blue" icon="mic" label="结束录音" v-if="is_record" class="button" @click="stopRecord()"/>
                            <q-btn rounded color="blue" icon="library_music" label="播放" class="button" @click="playRecord()"/>
                            <div class="duration" v-if="is_record">{{count.toFixed(1)}}s后自动停止</div>
                            <div class="duration" v-if="recorder[currentPage-1]">
                                录音时长：{{recorder[currentPage-1].duration.toFixed(2)}}s
                            </div>
                        </div>
                    </div>
                    <div class="detail left">
                        <div class="bubble1 left">
                            <div>
                                <div class="bubble2 left">
                                    <img :src="getRandomPic1()" class="bubbleBg"/>
                                </div>
                                <div class="keyword left">{{scenePic[currentPage-1].keyword}}</div>
                                <div class="clear"></div>
                            </div>
                            
                            <div>
                                <div class="dialog left">
                                    <img src="../assets/dialog.png"/>
                                </div>
                                <div class="desc left">{{scenePic[currentPage-1].description}}</div>
                                <div class="clear"></div>
                            </div>
                        </div>
                        <div class="left cartoon">
                            <img :src="getRandomPic2()"/>
                        </div>
                        <div class="clear"></div>
                    </div>
                    <div class="clear"></div>
                </div>
                <div class="col-md-1 rightButton">  
                    <q-btn round flat :color="currentPage==totalPages?'grey':'purple-7'" icon="arrow_forward" class="arrow" :disable="currentPage==totalPages" @click="forward()"/>
                </div>
                
            </div>
            <div class="row" v-if="part2&&questions[0]"><!--测试环节-->
                <div class="col-md-1 leftButton" v-if="is_video">
                    <q-btn round flat :color="currentVedio?'grey':'purple-7'" icon="arrow_back" class="arrow" :disable="currentVedio" @click="back()"/>
                </div>
                <div class="col-md-1 leftButton" v-else>
                    <q-btn round flat :color="currentQuestion==1?'grey':'purple-7'" icon="arrow_back" class="arrow" :disable="currentQuestion==1" @click="back()"/>
                </div>
                <div class="col-md-10 content2">
                    <div v-if="currentVedio">
                        <div class="video1 left">
                            <div class="video2 left">
                                <img src="../assets/video.png"/>
                            </div>
                            <div class="videoContent left" v-if="playerOptions['sources'][0]['src']!==''">
                                <video-player class="vjs-custom-skin" :options="playerOptions"></video-player>
                            </div>
                            <div class="clear"></div>
                        </div>
                        
                        <div class="cartoon2 left">
                            <img src="../assets/cartoon3.png"/>
                            <div class="watch">
                                <div class="watchBg float">
                                    <img src="../assets/bubble1.png"/>
                                </div>
                                <div class="watchTitle float">观看视频</div>
                                <div class="clear"></div>
                            </div>
                        </div>
                            
                        
                        <div class="clear"></div>
                    </div>
                    <div v-else>
                        <div class="pic2 left">
                            <img :src="questions[currentQuestion-1].pic_url" class="scenePic" />
                        </div>
                        <div class="question left">
                            <div class="questionBg left">
                                <img src="../assets/qpic1.png"/>
                            </div>
                            <div class="questionContent left">{{questions[currentQuestion-1].content}}</div>
                            <div class="clear"></div>
                        </div>
                        <div class="clear"></div>
                    </div>
                </div> 
                <div class="col-md-1 rightButton" v-if="currentVedio">
                    <q-btn round flat :color="totalQuestions<1?'grey':'purple-7'" icon="arrow_forward" class="arrow" :disable="totalQuestions<1" @click="forward()"/>
                </div>
                <div class="col-md-1 rightButton" v-else>
                    <q-btn round flat :color="currentQuestion>=totalQuestions?'grey':'purple-7'" icon="arrow_forward" class="arrow" :disable="currentQuestion==totalQuestions" @click="forward()"/>
                </div>
            </div>
            <div v-if="part3"><!--生成报告-->
                <div class="reportBg left">
                    <img src="../assets/bg6.png"/>  
                </div>
                <div class="reportForm left">
                    
                    <q-form class="q-gutter-lg">
                        <div class="reportTitle">
                            <span>填写学习报告</span>
                        </div>
                        <q-input outlined rounded v-model="report.teacher" label="辅导老师：" clearable class="teacherName">
                            <template v-slot:prepend>
                                <q-icon name="assignment_ind"></q-icon>
                            </template>
                        </q-input>
                        <div>
                            <span  class="label">老师评分<q-icon name="rate_review"></q-icon>：</span>
                            <q-rating
                                v-model="report.score"
                                size="3.5em"
                                color="grey"
                                :color-selected="ratingColors"
                                :max="5"
                                icon="local_florist"
                            />
                        </div>
                        <q-input
                            outlined rounded counter maxlength="100"
                            v-model="report.comment"
                            type="textarea"
                            :max-height="100"
                            rows="6"
                            label="老师评语："
                            class="teacherComment"
                            >
                            <template v-slot:prepend>
                                <q-icon name="speaker_notes"></q-icon>
                            </template>
                        </q-input>
                        <div class="submitButton">
                            <q-btn push rounded size="lg" color="amber-14" @click="submit()">生成报告</q-btn>
                        </div>
                    </q-form>
                </div>
                <div class="clear"></div>
            </div>
            <div class="back" v-if="is_up()">
                <q-btn push color="purple-6" label="返回上一环节" class="next_btn" icon="reply" @click="up()"/>
            </div>
            <div class="next" v-if="is_down()">
                <q-btn push color="purple-6" label="进入下一环节" class="next_btn" icon="forward" @click="down()"/>
            </div>
            <div class="viewReport" v-if="part3">
                <div v-if="existReport" style="float:left;margin-right:2%;">
                   <q-btn push color="purple-6" label="查看已生成报告" class="next_btn" icon="visibility" @click="viewReport()"/>
                </div>
                <div v-if="createReport" style="float:left">
                   <q-btn push color="purple-6" label="完成学习" class="next_btn" icon="forward" @click="jump()"/>
                </div>
                <div style="clear:both"></div>
            </div>
        </div>
        </transition>
        <q-inner-loading :showing="showLoad">
           <q-spinner-gears size="50px" color="primary" />
        </q-inner-loading>
        <Report :popUp="popUp"
                :close="closeReport"
                :sceneName="scene_name"
                :words="words"
                :report="reportDetail"
        ></Report>
    </div>
</template>

<script>
import Header from '@/components/Header.vue'
import Report from '@/components/Report.vue'
import Recorder from 'js-audio-recorder'
import {getScenePic,getSceneInformation,getSceneQuestion,collectOrCancel} from '../api/scene'
import {getSomeReport,modifyReport,createReport} from '../api/report'
export default {
    components: { 
        Header,
        Report
    },
    data(){
        return{
            headerName:'SceneList',
            showScene:false,
            showLoad:true,
            scene_name:'',
            scene_id:0,
            user:'',
            is_collect:false,
            words:'',
            //date:'',
            is_record:false,
            is_video:false,
            video_url:'',
            step:'1',
            part1: true,
            part2: false,
            part3: false,
            popUp:false,
            createReport:false,
            existReport:false,
            report_id:0,
            totalPages:0,
            currentPage:1,
            totalQuestions:2,
            currentQuestion:1,
            currentVedio:false,
            bubbleIndex:1,
            recorder:[],
            timer:'',
            duration:[],
            count:15,
            ratingColors:['pink-3','pink-5','orange-8','deep-orange-13','red-14'],
            report:{
                teacher:'',
                score:0,
                comment:''
            },
            reportDetail:{},
            reportDetail1:{},
            scenePic:[],
            questions:[],
            playerOptions:{
                playbackRates: [1.0, 1.5, 2.0], //播放速度
                autoplay: false,
                controls: true, //控制条
                preload: 'auto', //视频预加载
                muted: false, //默认情况下将会消除任何音频。
                loop: false, //导致视频一结束就重新开始。
                language: 'zh-CN',
                aspectRatio: '16:9', // 将播放器置于流畅模式，并在计算播放器的动态大小时使用该值。值应该代表一个比例 - 用冒号分隔的两个数字（例如"16:9"或"4:3"）
                fluid: true, // 当true时，Video.js player将拥有流体大小。换句话说，它将按比例缩放以适应其容器。
                sources: [{
                    type:"video/mpeg",
                    type:"video/webm",
                    type:"video/mp4",
                    src: ""
                }],
                poster: require("../assets/poster1.png"), //你的封面地址（覆盖在视频上面的图片）
                width: document.documentElement.clientWidth,
                notSupportedMessage: '此视频暂无法播放，请稍后再试' //允许覆盖Video.js无法播放媒体源时显示的默认信息。

            }


        }
    },
    methods:{
        getRandomPic1(){
            var pic_index=(this.currentPage-1)%3+1;
            return require('../assets/bubble'+pic_index+'.png');
        },
        getRandomPic2(){
            var pic_index=(this.currentPage-1)%5+1;
            return require('../assets/cartoon'+pic_index+'.png');
        },
        back(){//向前翻页
            if(this.part1){//学习环节翻页
                this.currentPage-=1;
            }else if(this.part2){//测试环节翻页
                if(this.is_video&&this.currentQuestion==1){
                    this.currentVedio=true;
                }else{
                    this.currentQuestion-=1;
                }
            }
            
        },
        forward(){//向后翻页
            if(this.part1){//学习环节翻页
                this.currentPage+=1;
            }else if(this.part2){//测试环节翻页
                if(this.is_video&&this.currentVedio){
                    this.currentVedio=false;
                }else{
                    this.currentQuestion+=1;
                }
            }
            
        },
        down(){//转到下一环节
            if(this.part1){
                this.step=2;
                this.part1=false;
                this.part2=true;
                this.part3=false;
            }else if(this.part2){
                this.step=3;
                this.part1=false;
                this.part2=false;
                this.part3=true;
            }
            
        },
        up(){//回到上一环节
            if(this.part2){
                this.step=1;
                this.part1=true;
                this.part2=false;
                this.part3=false;
            }else if(this.part3){
                this.step=2;
                this.part1=false;
                this.part2=true;
                this.part3=false;
            }
            
        },
        is_down(){//是否能转到下一环节
            if(this.part1&&this.currentPage==this.totalPages){
                return true;
            }else if(this.part2&&this.currentQuestion==this.totalQuestions&&!this.currentVedio){
                return true;
            }
            return false;
        },
        is_up(){//是否能返回上一环节
            if(this.part2|this.part3){
                return true;
            }
            return false;
        },
        startRecord(){
            this.is_record=true;
            this.recorder[this.currentPage-1] = new Recorder();
            Recorder.getPermission().then(() => {
                console.log('开始录音');
                this.recorder[this.currentPage-1].start(); // 开始录音
                this.timer = setInterval(() => {
                    if (this.count <=0) {
                        this.timer = clearInterval(this.timer);
                        this.count = 15;
                        this.is_record=false;
                    } else {
                        this.count -= 0.2;
                    }
                }, 200);
            }, (error) => {
                this.$Message.info('请先允许该网页使用麦克风');
                console.log(`${error.name} : ${error.message}`);
            });

        },
        stopRecord(){
            this.is_record=false;
            console.log('停止录音');
            this.recorder[this.currentPage-1].stop(); // 停止录音
            this.timer = clearInterval(this.timer);
            this.count=15;
        },
        playRecord(){
            this.recorder[this.currentPage-1].play() // 播放录音

        },
        convertTime(t){
            var time=new Date(t);
            var year=time.getFullYear();
            var month=time.getMonth()+1;
            var day=time.getDate();
            return year+'-'+(month>=10?month:('0'+month))+'-'+(day>=10?day:('0'+day));
        },
        closeReport(f){
            this.popUp=f;
        },
        jump(){
            this.$router.push("/SceneList");
        },
        viewReport(){
            if(!this.createReport){
                this.reportDetail=this.reportDetail1;
            }
            this.popUp=true;
        },
        submit(){//生成报告
            if(this.report.teacher==''){
                this.$q.notify({
                    type: 'negative',
                    message: `请输入辅导老师名字！`,
                    position: 'top'
                });
                return;
            }
            else if(this.report.score==0){
                this.$q.notify({
                    type: 'negative',
                    message: `请进行打分！`,
                    position: 'top'
                });
                return;
            }
            else if(this.report.comment==''){
                this.$q.notify({
                    type: 'negative',
                    message: `请输入评语！`,
                    position: 'top'
                });
                return;
            }
            if(this.existReport){
                this.modifySceneReport();
            }else{
                this.createSceneReport();
            }
            this.createReport=true;
            this.popUp=true;
            //this.date=this.convertTime(new Date());
        },
        async getScenePicList(){
            getScenePic({scene_id:this.scene_id}).then((response)=>{
                console.log(response,"getScenePic");
                if(response.data.total==0){
                    this.totalPages=0;
                    this.scenePic=[];
                    return;
                }else{
                    this.totalPages=response.data.total;
                    this.scenePic=response.data.scenePic;
                }
                this.showLoad=false;
                this.showScene=true;
            });
        },
        async getSceneInfo(){
            //this.$q.loading.show();
            this.showLoad=true;
            this.showScene=false;
            getSceneInformation(
                {
                    scene_id:this.scene_id,
                    user_id:this.user.user_id
                }
            ).then((response)=>{
                console.log(response,"getSceneInfo");
                this.scene_name=response.data.scene_name;
                this.is_collect=response.data.is_collect;
                this.words=response.data.tags;
                this.is_video=response.data.is_video;
                this.playerOptions['sources'][0]['src'] =response.data.video_url;
                if(this.is_video){
                    this.currentVedio=true;
                }else{
                    this.currentVedio=false;
                }
            });
            //this.$q.loading.hide();
        },
        async getSceneQuestionList(){
            this.showLoad=true;
            this.showScene=false;
            getSceneQuestion({scene_id:this.scene_id}).then((response)=>{
                console.log(response,"getSceneQuestion");
                if(response.data.total==0){
                    this.totalQuestions=0;
                    this.questions=[];
                    return;
                }else{
                    this.totalQuestions=response.data.total;
                    this.questions=response.data.question;
                }
                this.showLoad=false;
                this.showScene=true;
            });
        },
        async collectNot(){
            collectOrCancel({
                user_id:this.user.user_id,
                scene_id:this.scene_id
                }).then((response)=>{
                    console.log(response);
                    if(response.data.is_collect==0){
                        this.is_collect=false;
                        this.$q.notify({
                            type: 'positive',
                            message: `取消收藏成功！`,
                            position: 'top'
                        });
                    }else{
                        this.is_collect=true;
                        this.$q.notify({
                            type: 'positive',
                            message: `收藏成功！`,
                            position: 'top'
                        });
                    }
                });
        },
        async getReport(){
            this.showLoad=true;
            this.showScene=false;
            getSomeReport({
                scene_id:this.scene_id,
                user_id:this.user.user_id
            }).then((response)=>{
                console.log(response,"getReport");
                this.existReport=response.data.exist;
                this.reportDetail1=response.data.report;
                if(response.data.exist){
                    this.report_id=response.data.report.report_id;
                }
                this.showLoad=false;
                this.showScene=true;
            });
        },
        async modifySceneReport(){
            let fd = new FormData();
            fd.append('report_id',this.report_id);
            fd.append('score',this.report.score);
            fd.append('comment',this.report.comment);
            fd.append('teacher',this.report.teacher);
            modifyReport(fd).then((response)=>{
                console.log(response,"modifyReport");
                this.reportDetail={};
                this.$set(this.reportDetail, 'score', this.report.score);
                this.$set(this.reportDetail, 'teacher', this.report.teacher);
                this.$set(this.reportDetail, 'comment', this.report.comment);
                this.$set(this.reportDetail,'time',this.convertTime(new Date()));
            });
        },
        async createSceneReport(){
            let fd = new FormData();
            fd.append('scene_id',this.scene_id);
            fd.append('user_id',this.user.user_id);
            fd.append('score',this.report.score);
            fd.append('comment',this.report.comment);
            fd.append('teacher',this.report.teacher);
            createReport(fd).then((response)=>{
                console.log(response,"createReport");
                this.reportDetail={};
                this.$set(this.reportDetail, 'score', this.report.score);
                this.$set(this.reportDetail, 'teacher', this.report.teacher);
                this.$set(this.reportDetail, 'comment', this.report.comment);
                this.$set(this.reportDetail,'time',this.convertTime(new Date()));
                this.existReport=true;
            })
        }
    },
    watch:{
        part2(){
            if(this.part2){
                this.getSceneQuestionList();
            }
        },
        part3(){
            if(this.part3){
                this.getReport();
            }
        }
    },
    computed:{
        
    },
    created(){
        this.user=JSON.parse(sessionStorage.getItem('user'));
        this.scene_id=parseInt(sessionStorage.getItem('scene_id'));
        this.getSceneInfo();
        this.getScenePicList();
        this.recorder=new Array(this.totalPages);
    },
    mounted(){
        
    }
}
</script>

<style>


.scene .details1{
    background-image: url(../assets/bg3.png);
    background-size: cover;
    padding:2%;
    min-width: 1000px;
    background-color: #fff;
    width: 100%;
    height: 100%;
}
.scene .details2{
    background-image: url(../assets/bg5.png);
    background-size: cover;
    padding:2%;
    min-width: 1000px;
    background-color: #fff;
    width: 100%;
    height: 100%;
}

.scene .title{
    font-family: '楷体';
    letter-spacing: 1px;
    text-align: center;
}

.scene .step{
    margin: 0% 20% 2% 20%;
    
}

.scene .stepper{
    background-color:rgba(251,241,242,0);
}


.scene .q-stepper__dot{
    font-size: 25px;
    min-width: 35px;
    width: 35px;
    height: 35px;
}

.scene .q-stepper__title{
    font-size: 20px;
    font-family: '楷体';
}

.scene .q-stepper__tab--active{
    color:blueviolet;
}

.scene .q-stepper__tab--done{
    color:blueviolet;
}

.scene .q-stepper__content{
    display: none;
}

.scene .arrow{
    font-size: 30px;
    position:fixed;
}

.scene .content{
    padding:0% 0% 5% 2%;
}
.scene .content2{
    padding:0% 5% 5% 2%;
}
.scene .pic{
    width:40%;
    text-align: center;
}
.scene .pic2{
    width:60%;
    text-align: center;
}
.scene .scenePic{
    width:100%;
    height: auto;
}

.scene .button{
    margin: 1% 3%;
    font-size: 17px;
    font-family: '楷体';
}

.scene .detail{
    margin: 0% 0% 0% 5%;
    width:50%;
}

.scene .bubble1{
    width: 25%;

}

.scene .bubbleBg{
    width: 100%;
    height: auto;
}

.scene .keyword{
    font-size: 24px;
    font-family: '楷体';
    font-weight: bolder;
    margin-top: -65%;
    margin-left: 5%;
    text-align: center;
    width:90%;
}
.scene .cartoon{
    width:70%;
    padding-left: 40%;
}
.scene .cartoon2{
    width:25%;
    margin-left:-15%;
}
.scene .cartoon2 img{
    width: 100%;
}
.scene .cartoon img{
    width: 100%;
}

.scene .dialog{
    margin-top:2%;
    width:320%;
}
.scene .dialog img{
    width:100%;
}
.scene .desc{
    font-size: 20px;
    font-family: '楷体';
    width: 230%;
    color:#ab00bf;
    font-weight: bolder;
    margin-top:-120%;
    margin-left: 75%;
}
.scene .clear{
    clear: both;
}
.scene .left{
    float: left;
}

.scene .next{
    top:90%;
    left:85%;
    position:fixed;
    width: 100%;
}

.scene .viewReport{
    top:90%;
    left:72%;
    position: fixed;
    width: 100%;
}

.scene .back{
    top:90%;
    left:3%;
    position:fixed;
    width: 100%;
}

.scene .next_btn{
    font-size: 18px;
    font-family: '楷体';
    
}

.scene .duration{
    font-size: 18px;
    font-family: '楷体';
    margin-right: 25%;
}

.scene .video1{
    width: 80%;
}
.scene .video2 img{
    width:100%;
    height: auto;
}
.scene .videoContent{
    width: 65%;
    margin-left:17%;
    margin-top:-55%;
}
.scene .watch{
    margin-left:30%;
}
.scene .watchBg{
    width:120%;
}

.scene .watchBg img{
    width: 100%;
    height: auto;
}


.scene .watchTitle{
    width:80%;
    font-size: 30px;
    font-family: '楷体';
    margin-left:18%;
    margin-top:-80%;
    text-align: center;
}
.scene .question{
    margin-left: -10%;
    width:45%;
}
.scene .questionBg{
    width:100%;
}
.scene .questionBg img{
    width: 100%;
    height: auto;
}

.scene .questionContent{
    font-size: 20px;
    font-family: '楷体';
    color: dodgerblue;
    width:65%;
    margin-top:-80%;
    margin-left: 13%;
}

.scene .reportBg{
    width: 80%;
    margin:0% 10%;
}

.scene .reportBg img{
    width:100%;
    height: auto;
}

.scene .reportForm{
    margin-left:32%;
    margin-top:-53%;
    width: 35%;
}

.scene .teacherName{
    width:60%;
    font-family: '楷体';
    font-size: 20px;
}
.scene .teacherComment{
    font-family: '楷体';
    font-size: 18px;
}

.scene .reportTitle{
    font-size: 23px;
    font-family: '楷体';
    text-align: center;
}

.scene .label{
    font-size: 20px;
    font-family: 楷体;
}

.scene .submitButton{
    text-align: center;
    font-family: '楷体';
    letter-spacing: 1px;
}


.scene .q-img .q-img__content{
    background-color:rgba(0,0,0,0);
}
</style>
<template>
    <div class="myScene">
        <Header :tab="headerName"></Header>
        <div style="margin-left:18%;">
            <div style="float:left;width:20%;">
                <Menu :link="link"/>
            </div>
            <transition
                appear
                enter-active-class="animated fadeIn"
                leave-active-class="animated fadeOut"
            >
            <div style="float:left;width:80%;" v-show="showScene">
                <div style="margin-top:2%;">
                    <div style="text-align:center;width:100%">
                        <q-btn push rounded color="purple-5" icon="backup" class="btn" @click="toCreate=true">创建场景</q-btn>
                    </div>
                    <div style="margin-top:2%;" class="row">
                        <div v-if="totalPages==0" style="margin:0% 5%;font-size:18px;">你还没有创建过场景哦~~赶快去创建吧！</div>
                        <div class="col-md-6 col-xs-12" v-for="(scene,index) in mySceneList" :key="index">
                            <SceneCard  :scene="scene" :index="index" :setPrompt="openValidate" :cardType="cardType" @setIndex="targetScene" 
                             :openPassword="openPassword" :refresh="refreshCard"></SceneCard>
                        </div>
                        <div style="clear:both;"></div>
                    </div>
                </div>
            </div>
            </transition>
            <q-inner-loading :showing="showLoad">
                <q-spinner-gears size="50px" color="primary" />
            </q-inner-loading>
            <div style="clear:both;"></div>
            
        </div>
        <PasswordValidate :prompt="prompt" :scene="mySceneList[index]" :closeDialog="close"></PasswordValidate>
        <setScenePassword :prompt="prompt_pwd" :scene="mySceneList[index]" :closeDialog="close1" :refresh="refreshCard"></setScenePassword>
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
        <q-dialog v-model="toCreate" full-height persistent>
            
            <q-card style="width:70vw;max-width:80vw;" class="bg-orange-1" >
                <transition
                    appear
                    enter-active-class="animated fadeIn"
                    leave-active-class="animated fadeOut"
                >
                <div class="q-ma-md" v-show="showScene1">
                    <div style="font-size:28px;padding:0% 10% 0% 42%;float:left;font-family:'微雅软黑';width:80%;" >
                        创建场景学习
                    </div>
                    <div style="float:left;width:20%;padding:0% 0% 0% 12%;">
                        <q-btn push round icon="close" color="orange-4" @click="closeCreate()">
                            <q-tooltip anchor="bottom middle" self="top middle" :offset="[10, 10]">
                                <span style="font-weight:600;color:#ff264f;">退出</span>将
                                <span style="font-weight:600;color:#ff264f;">不保存</span>编辑内容
                            </q-tooltip>
                        </q-btn>
                    </div>
                    <div style="clear:both"></div>
                    <div style="font-family:'微雅软黑';margin-top:2%;" class="q-gutter-lg">
                        <div style="width:35%;">
                            <q-input rounded outlined style="font-size:20px;" v-model="sceneName" label="情景主题：">
                                <template v-slot:prepend>
                                    <q-icon name="emoji_nature"></q-icon>
                                </template>
                            </q-input>
                        </div>
                        <div>
                            <div style="float:left;font-size:25px;width:20%;text-align:center">学习环节设计</div>
                            <div style="float:left;width:80%;padding-top:1.7%;">
                                <q-separator color="orange-3" size="2px"/>
                            </div>
                            <div style="clear:both;"></div>
                            <div style="font-size:16px;width:20%;text-align:center">(场景总数不超过12个)</div>
                        </div>
                        <div v-for="(item,index) in studyPart" :key="index">
                            <div style="float:left;width:5%;font-size:15px;">No.{{index+1}}</div>
                            <div style="float:left;width:35%;">
                                <q-uploader
                                    label="添加场景图片(图片大小不超过1MB)："
                                    hide-upload-btn
                                    accept=".jpg, .png"
                                    :filter="checkFile"
                                    color="orange-5"
                                    text-color="dark"
                                    @removed="deleteFile1(...arguments,index)"
                                    @added="addFile1(...arguments,index)"
                                    @rejected="onPicRejected"
                                    style="width:90%;"
                                />
                            </div>
                            <div style="float:left;width:23%;margin-right:2%;">
                                <q-input rounded outlined counter maxlength="4" style="font-size:20px;" 
                                v-model="item.keyword" label="关键词汇：" hint="词汇长度不超过四个字">
                                    <template v-slot:prepend>
                                        <q-icon name="style"></q-icon>
                                    </template>
                                </q-input>
                                <div style="margin-top:5%;margin-left:5%;">
                                    <q-btn style="margin-right:8%;" color="orange-7" rounded push label="添加" v-if="index+1==studyPart.length&&index<12" @click="addItem1()"></q-btn>
                                    <q-btn rounded push label="删除" color="deep-orange-7" v-if="index!==0" @click="deleteItem1(index)"></q-btn>
                                </div>
                            </div>
                            <div style="float:left;width:35%;">
                                <q-input
                                    outlined rounded counter maxlength="60"
                                    v-model="item.description"
                                    type="textarea"
                                    :max-height="150"
                                    rows="6"
                                    label="描述语言："
                                    style="font-size:17px;"
                                    hint="为图片配一句描述性句子，不超过60字"
                                    >
                                    <template v-slot:prepend>
                                        <q-icon name="description"></q-icon>
                                    </template>
                                </q-input>
                            </div>
                            <div style="clear:both;"></div>
                        </div>
                        <div>
                            <div style="float:left;font-size:25px;width:20%;text-align:center">测试环节设计</div>
                            <div style="float:left;width:80%;padding-top:1.7%;">
                                <q-separator color="orange-3" size="2px"/>
                            </div>
                            <div style="clear:both;"></div>
                            <div style="font-size:16px;width:20%;text-align:center">(问题总数不超过10个)</div>
                        </div>
                        <div>
                            <div style="float:left;width:28%;">
                                <q-toggle
                                    v-model="is_video"
                                    checked-icon="videocam"
                                    color="orange-9"
                                    unchecked-icon="videocam_off"
                                    size="xl"
                                    style="font-size:17px;"
                                    :label="is_video?'是否添加视频？(是)':'是否添加视频？(否)'"
                                />
                            </div>
                            <div style="float:left;width:35%;" v-if="is_video">
                                <q-uploader
                                    label="添加学习视频(视频大小不超过60MB)："
                                    hide-upload-btn
                                    color="deep-orange-5"
                                    text-color="dark"
                                    :filter="checkVideo"
                                    accept=".mp4, .webm"
                                    @removed="deleteVideo"
                                    @added="addVideo"
                                    @rejected="onVideoRejected"
                                    style="width:90%;"
                                />
                            </div>
                            <div style="clear:both;"></div>
                        </div>
                        <div class="q-gutter-y-lg">
                            <div v-for="(question,i) in testPart" :key="i">
                                <div style="float:left;width:5%;font-size:15px;">No.{{i+1}}</div>
                                <div style="float:left;width:35%;">
                                    <q-uploader
                                        label="添加题目图片(图片大小不超过1MB)："
                                        hide-upload-btn
                                        :filter="checkFile"
                                        accept=".jpg, .png"
                                        color="amber-3"
                                        text-color="dark"
                                        @removed="deleteFile2(...arguments,i)"
                                        @added="addFile2(...arguments,i)"
                                        @rejected="onPicRejected"
                                        style="width:90%;"
                                    />
                                </div>
                                <div style="float:left;width:35%;">
                                    <q-input
                                        outlined rounded counter maxlength="60"
                                        v-model="question.content"
                                        type="textarea"
                                        :max-height="150"
                                        rows="5"
                                        label="问题内容："
                                        style="font-size:17px;"
                                        hint="根据图片输入具体问题，不超过60字"
                                        >
                                        <template v-slot:prepend>
                                            <q-icon name="create"></q-icon>
                                        </template>
                                    </q-input>
                                </div>
                                <div style="float:left;width:20%;margin-left:2%;margin-top:5%;">
                                    <q-btn style="margin-right:5%;" color="orange-7" rounded push label="添加" v-if="i+1==testPart.length&&i<10" @click="addItem2()"></q-btn>
                                    <q-btn rounded push label="删除" color="deep-orange-7" v-if="i!==0" @click="deleteItem2(i)"></q-btn>
                                </div>
                                <div style="clear:both;"></div>
                            </div>
                        </div>
                        <div>
                            <div style="float:left;font-size:25px;width:20%;text-align:center">提交场景设计</div>
                            <div style="float:left;width:80%;padding-top:1.7%;">
                                <q-separator color="orange-3" size="2px"/>
                            </div>
                            <div style="clear:both;"></div>
                        </div>
                        <div>
                            <div style="float:left;width:40%;">
                                <q-toggle
                                    v-model="is_visible"
                                    checked-icon="lock_open"
                                    color="orange-9"
                                    unchecked-icon="lock"
                                    size="xl"
                                    style="font-size:17px;"
                                    :label="is_visible?'是否公开创建的场景？(是)':'是否公开创建的场景？(否)'"
                                />
                            </div>
                            <div style="float:left;width:30%;" v-if="!is_visible">
                                <q-input
                                rounded
                                outlined
                                v-model="password"
                                :type="pwd_seen ? 'text':'password'"
                                label="设置密码：" clearable
                                :rules="[(val) => (val.length==6) || '密码长度为6位']">
                                    <template v-slot:prepend>
                                        <q-icon name="lock"></q-icon>
                                    </template>
                                    <template v-slot:append>
                                        <q-icon
                                        :name="pwd_seen ? 'visibility':'visibility_off'"
                                        class="cursor-pointer"
                                        @click="pwd_seen = !pwd_seen"
                                        />
                                    </template>
                                </q-input>
                            </div>
                            <div style="clear:both;"></div>
                        </div>
                        <div style="text-align:center">
                            <q-btn push rounded color="amber-6" size="lg" style="width:15%;" @click="submitForm()" :disable="!is_visible&&password.length!==6">提 交</q-btn>
                        </div>
                    </div>
                </div>
                </transition>
                <q-inner-loading :showing="showLoad1">
                    <q-spinner-cube
                        color="orange"
                        size="5.5em"
                    />
                    <div>正在处理数据，请耐心等待~~</div>
                </q-inner-loading>
            </q-card>
            
        </q-dialog>
    </div>
</template>

<script>
import Header from '@/components/Header.vue'
import Menu from '@/components/Menu.vue'
import SceneCard from '@/components/SceneCard.vue'
import PasswordValidate from '@/components/PasswordValidate.vue'
import setScenePassword from '@/components/setScenePassword.vue'
import {getCreatedScene,createScene} from '../api/scene'
export default {
    components: { 
        Header,
        Menu,
        SceneCard,
        PasswordValidate,
        setScenePassword
    },
    data(){
        return{
            headerName:'Account',
            link:'MyScene',
            toCreate:false,
            showScene:false,
            showLoad:true,
            showScene1:true,
            showLoad1:false,
            currentPage:1,
            pageSize:2,
            total:0,
            totalPages:0,
            user:{},
            prompt:false,
            prompt_pwd:false,
            //makeCollect:false,
            pwd_seen:false,
            cardType:0,
            index:'',
            sceneName:'',
            is_video:false,
            is_visible:true,
            password:'',
            video:'',
            studyPart:[
                {
                    file:'',
                    keyword:'',
                    description:''
                }
            ],
            testPart:[
                {
                    file:'',
                    content:''
                }
            ],
            mySceneList:[]
        }
    },
    methods:{
        openValidate(t){
            this.prompt=t;
            console.log(this.prompt);
        },
        openPassword(t){
            this.prompt_pwd=t;
        },
        targetScene(i){
            this.index=i;
            console.log("target:"+this.index);
        },
        close(f){
            this.prompt=f;
        },
        close1(f){
            this.prompt_pwd=f;
        },
        refreshCard(){
            this.currentPage=1;
            this.total=0;
            this.getUserCreateScene();
        },
        async getUserCreateScene(){
            this.showScene=false;
            this.showLoad=true;
            getCreatedScene({
                currentPage:this.currentPage,
                pageSize:this.pageSize,
                user_id:this.user.user_id,
                creator:this.user.username
            }).then((response)=>{
                console.log(response,"getCreateScene");
                if(response.data.total==0){
                    this.totalPages=0;
                    this.total=0;
                    this.mySceneList=[];
                }else{
                    this.mySceneList=response.data.scenes;
                    this.totalPages=Math.ceil(response.data.total/this.pageSize);
                    this.total=response.data.total;
                }
                this.showLoad=false;
                this.showScene=true;
            });
        },
        checkFile(files){
            return files.filter(file=>file.size/1024/1024<1);
           
        },
        onPicRejected (rejectedEntries) {
            this.$q.notify({
                type: 'negative',
                message: `请上传1MB以内的jpg或png格式的图片！`,
                position: 'top'
            })
        },
        checkVideo(files){
            return files.filter(file=>file.size/1024/1024<60);
        },
        onVideoRejected(rejectedEntries){
            this.$q.notify({
                type: 'negative',
                message: `请上传60MB以内的mp4或webm格式的视频！`,
                position: 'top'
            })
        },
        addItem1(){
            this.studyPart.push({file:'',keyword:'',description:''});
        },
        deleteItem1(index){
            this.studyPart.splice(index,1);
            console.log(this.studyPart,"删除后");
        },
        deleteFile1(files,index){
            console.log(files,"picture study removed");
            console.log(index,"index study removed");
            this.studyPart[index].file='';
            console.log(this.studyPart,"studyPart,removed");
        },
        addFile1(files,index){
            console.log(files,"picture study added");
            console.log(index,"index study added");
            this.studyPart[index].file=files[0];
            console.log(this.studyPart,"studyPart,added");
        },
        deleteVideo(files){
            console.log(files,"video removed");
            this.video='';
        },
        addVideo(files){
            console.log(files,"video added");
            this.video=files[0];
        },
        deleteFile2(files,index){
            console.log(files,"picture test removed");
            console.log(index,"index test removed");
            this.testPart[index].file='';
            console.log(this.testPart,"testPart,removed");
        },
        addFile2(files,index){
            console.log(files,"picture test added");
            console.log(index,"index test added");
            this.testPart[index].file=files[0];
            console.log(this.testPart,"testPart,added");
        },
        addItem2(){
            this.testPart.push({file:'',content:''});
        },
        deleteItem2(index){
            this.testPart.splice(index,1);
            console.log(this.testPart,"删除后");
        },
        closeCreate(){
            this.toCreate=false;
            this.setEmpty();
        },
        setEmpty(){
            var size1=this.studyPart.length;
            var size2=this.testPart.length;
            for(var i=0;i<size1-1;i++){
                this.studyPart.pop();
            }
            this.studyPart[0].file='';
            this.studyPart[0].keyword='';
            this.studyPart[0].description='';
            console.log(this.studyPart,"studyPart empty");
            for(var i=0;i<size2-1;i++){
                this.testPart.pop();
            }
            this.testPart[0].file='';
            this.testPart[0].content='';
            console.log(this.testPart,"testPart empty");
            this.pwd_seen=false;
            this.password='';
            this.video='';
            this.is_video=false;
            this.is_visible=true;
            this.sceneName='';
        },
        testForm(){//测试提交结果是否含有空值
            for(var i=0;i<this.studyPart.length;i++){
                if(this.studyPart[i].file===''||this.studyPart[i].keyword===''||this.studyPart[i].description===''){
                    return false;
                }
            }
            for(var i=0;i<this.testPart.length;i++){
                if(this.testPart[i].file===''||this.testPart[i].content===''){
                    return false;
                }
            }
            if(this.sceneName===''||(this.is_video&&this.video==='')||(!this.is_visible&&this.password==='')){
                return false;
            }
            return true;
        },
        submitForm(){
            if(this.testForm()){
                this.showScene1=false;
                this.showLoad1=true;
                this.createUserScene();
            }else{
                this.$q.notify({
                    type: 'negative',
                    message: `请填写空白区域或上传相应图片(视频)，否则请删除空白项！`,
                    position: 'top'
                });
            }
        },
        getData(){
            let formData=new FormData();
            let keywords=[];
            let descriptions=[];
            let questionContents=[];
            for(var i=0;i<this.studyPart.length;i++){
                formData.append('files1',this.studyPart[i].file);
                keywords.push(this.studyPart[i].keyword);
                descriptions.push(this.studyPart[i].description);
            }
            for(var i=0;i<this.testPart.length;i++){
                formData.append('files2',this.testPart[i].file);
                questionContents.push(this.testPart[i].content);
            }
            let keywordString=keywords.join(" ");
            let descriptionString=descriptions.join("|");
            let questionContentString=questionContents.join("|");
            formData.append('keyword',keywordString);
            formData.append('description',descriptionString);
            formData.append('content',questionContentString);
            formData.append('scene_name',this.sceneName);
            formData.append('creator',this.user.username);
            if(this.is_visible){
                formData.append('is_visible',1);
            }else{
                formData.append('is_visible',0);
            }
            formData.append('pwd',this.password);
            if(this.is_video){
                formData.append('is_video',1);
                formData.append('video',this.video);
            }else{
                formData.append('is_video',0);
                formData.append('video',this.studyPart[0].file);
            }
            return formData;
        },

        async createUserScene(){
            let fd=this.getData();
            console.log(fd.get('files1'));
            createScene(fd).then((response)=>{
                console.log(response,"createScene");
                this.showScene1=true;
                this.showLoad1=false;
                this.toCreate=false;
                this.setEmpty();
                this.$router.go(0);
                this.$q.notify({
                    type: 'positive',
                    message: `创建场景学习成功！`,
                    position: 'top'
                });
            }).catch((error)=>{
                console.log(error);
                this.$q.notify({
                    type: 'info',
                    message: `后台正在处理数据，请稍后刷新页面！`,
                    position: 'top'
                });
            })
        }
    },
    watch:{
        currentPage(){
            this.getUserCreateScene();
        }
    },
    created(){
        this.user=JSON.parse(sessionStorage.getItem('user'));
        this.showScene=false;
        this.showLoad=true;
        this.getUserCreateScene();
    }
}
</script>

<style>
.myScene .btn{
    font-family: '楷体';
    font-size: 22px;
    letter-spacing: 1px;
}

.myScene .pag{
    margin-top: 2%;
    margin-bottom: 3%;
    margin-left:35%;
}

.myScene{
    width: 100%;
    height: 100%;
    min-height: 900px;
    position:relative;
    
}

.myScene:before {
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
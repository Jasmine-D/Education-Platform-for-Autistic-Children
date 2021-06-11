<template>
    <q-card class="card shadow-5">
        <q-img
            :src="scene.pic_url"
            style="width: 100%;height: 240px;">
            <div class="absolute-bottom text-h6">
            情景主题：{{scene.scene_name}}
            </div>
        </q-img>

        <q-card-section>
            <div>
                <span class="span1">创建人：{{scene.creator}}</span>
            </div>
            <div style="overflow:hidden;white-space: nowrap;text-overflow:ellipsis;font-weight: bolder;
                font-family: '楷体';font-size: 17px;">
                词汇：{{scene.tags}}
            </div>
            <div v-if="cardType==0" style="margin-left:-3%;margin-top:2%;">
                <div>
                    <q-toggle
                    v-model="scene.is_visible"
                    checked-icon="lock_open"
                    color="orange-9"
                    unchecked-icon="lock"
                    size="lg"
                    style="font-size:18px;"
                    :label="scene.is_visible?'公开':'私有'"
                    @input="toggle"
                    :disable="scene.is_visible"
                    /><span v-if="!scene.is_visible" style="font-size:16px;font-family:'楷体'"> (密码：{{scene.pwd}})</span>
                </div>
                <div style="margin-left:4%;margin-top:2%">
                    <q-btn dense outline rounded size="md" color="primary" v-if="scene.is_visible" @click="setPassword()" style="font-size:16px;font-family:'楷体'">设置密码</q-btn>
                    <q-btn dense outline rounded size="md" color="positive" v-else @click="setPassword()" style="font-size:16px;font-family:'楷体'">修改密码</q-btn>
                    <q-btn dense outline rounded size="md" color="negative" @click="deleteCard()" style="font-size:16px;font-family:'楷体';margin-left:5%;">删除场景</q-btn>
                </div>
                
            </div>
            <div v-if="!scene.is_visible">
                
            </div>
        </q-card-section>
        <q-card-actions align="right" class="q-gutter-md">
            <q-btn outline rounded color="blue-6" class="enter" @click="enterScene()">
                <q-icon v-if="!scene.is_visible" left size="1.5em" name="lock" />
                <div>进入场景学习</div>
            </q-btn>
            <q-btn flat round :color="scene.is_collect?'red':'grey'" icon="favorite" @click="collectNot()"/>
        </q-card-actions>   
    </q-card>
    
</template>

<script>
import { collectOrCancel,deleteScene,setPublic} from '../api/scene'
export default {
    name:'SceneCard',
    props:{
        scene:{
            type:Object,
        },
        index:{
            type:Number,
            default:-1
        },
        setPrompt:{
            type:Function
        },
        openPassword:{
            type:Function,
            default:null
        },
        refresh:{
            type:Function,
            default:null
        },
        cardType:{
            type:Number
        },
        /*makeCollect:{
            type:Boolean,
            default:false
        }*/
    },
    data(){
        return{
            //is_visible:this.scene.is_visible
        }
    },
    methods:{
        enterScene(){
            sessionStorage.setItem("flag","0");
            var user=JSON.parse(sessionStorage.getItem('user'));
            if(this.scene.is_visible||this.scene.is_collect||this.scene.creator==user.username){
                this.openScene();
                //console.log("direct");
            }else{
                this.$emit("setIndex",this.index);
                console.log("validate");
                console.log(this.index);
                this.setPrompt(true);
            }
        },
        openScene(){
            sessionStorage.setItem("scene_id",this.scene.scene_id);
            this.$router.push("/Scene");
        },
        async collectNot(){
            var user=JSON.parse(sessionStorage.getItem('user'));
            if(!this.scene.is_visible&&!this.scene.is_collect&&this.scene.creator!==user.username){
                this.$emit("setIndex",this.index);
                console.log("validate");
                console.log(this.index);
                sessionStorage.setItem("flag","1");
                this.setPrompt(true);
            }else{
                collectOrCancel({
                user_id:user.user_id,
                scene_id:this.scene.scene_id
                }).then((response)=>{
                    console.log(response);
                    if(response.data.is_collect==0){
                        this.scene.is_collect=false;
                        this.$q.notify({
                            type: 'positive',
                            message: `取消收藏成功！`,
                            position: 'top'
                        });
                    }else{
                        this.scene.is_collect=true;
                        this.$q.notify({
                            type: 'positive',
                            message: `收藏成功！`,
                            position: 'top'
                        });
                    }
                    if(this.cardType==2){
                        this.refresh();
                    }
                });
            }
            
            
        },
        setPassword(){
            this.$emit("setIndex",this.index);
            this.openPassword(true);
        },
        async deleteCard(){
            let fd=new FormData();
            fd.append('scene_id',this.scene.scene_id);
            deleteScene(fd).then((response)=>{
                console.log(response,"deleteScene");
                this.$q.notify({
                    type: 'positive',
                    message: `删除成功！`,
                    position: 'top'
                });
                this.refresh();
            });
            
        },
        toggle(a,b){
            console.log(a);
            console.log(b);
            if(this.scene.is_visible){//原来不可见
                let fd=new FormData();
                fd.append('scene_id',this.scene.scene_id);
                setPublic(fd).then((response)=>{
                    console.log(response,"setPublic");
                    this.$q.notify({
                        type: 'positive',
                        message: `场景已公开！`,
                        position: 'top'
                    });
                    this.refresh();
                });
            }
        }
    },
    watch:{
        
    },
    created(){
        sessionStorage.setItem("flag","0");
    }
    
}
</script>

<style scoped>
.card{
    margin:5% 10%;
}

.card .span1{
    font-weight: bolder;
    font-family: '楷体';
    font-size: 20px;
}
.card .span2{
    font-weight: bolder;
    font-family: '楷体';
    font-size: 17px;
}
.card .enter{
    font-size: 16px;
    font-family: '楷体';
}

.card .q-field__control{
    height:50px;
}

</style>
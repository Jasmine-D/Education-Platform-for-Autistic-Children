<template>
    <q-dialog v-model="prompt" persistent>
        <q-card style="min-width: 375px;min-height:210px" v-if="scene">
            <q-card-section>
                <div class="text-h6">请输入密码：</div>
                <div style="font-size:12px" v-if="scene.is_visible">(说明：设置密码后，场景将不公开,此场景将被移出其他用户收藏夹)</div>
                <div style="font-size:12px" v-else>(说明：修改密码后，此场景将被移出其他用户收藏夹)</div>
            </q-card-section>

            <q-card-section  class="q-pt-none">
                <q-input rounded outlined dense v-model="pwd" autofocus clearable type="text" @keyup.enter="submitPassword()"
                :rules="[(val) => (val.length==6) || '密码长度为6位']">
                    <template v-slot:append>
                        <q-btn round dense flat icon="lock" @click="submitPassword()"/>
                    </template>
                    
                </q-input>
            </q-card-section>

            <q-card-actions align="right" class="text-primary">
                <q-btn flat label="取消" @click="close()" size="lg"/>
            </q-card-actions>
        </q-card>
    </q-dialog>
</template>

<script>
import {setPassword} from '../api/scene'
export default {
    name:'setScenePassword',
    props:{
        prompt:{
            type:Boolean,
            default:false
        },
        scene:{
            type:Object
        },
        closeDialog:{
            type:Function
        },
        refresh:{
            type:Function
        }
    },
    data(){
        return{
            pwd:'',
        }
    },
    methods:{
        close(){
            this.closeDialog(false);
        },
        async submitPassword(){
            if(this.pwd.length==6){
                let fd=new FormData();
                fd.append('scene_id',this.scene.scene_id);
                fd.append('pwd',this.pwd);
                setPassword(fd).then((response)=>{
                    console.log(response,"setPassword");
                    this.$q.notify({
                        type: 'positive',
                        message: `设置/修改密码成功！`,
                        position: 'top'
                    });
                    this.close();
                    this.refresh();
                    this.pwd='';
                });
                
            }
            
        }
    }
}
</script>

<style scoped>
.btn{
    font-size: 16px;
    font-family: '楷体';
    letter-spacing: 2px;
}
</style>
<template>
    <q-dialog v-model="prompt" persistent>
        <q-card style="min-width: 375px;min-height:210px">
            <q-card-section>
                <div class="text-h6">您选择的情景是加密的！</div>
                <div class="text-h6">请输入密码解锁：</div>
                <div style="font-size:14px">(解锁后将自动收藏)</div>
            </q-card-section>

            <q-card-section  class="q-pt-none">
                <q-input rounded outlined dense v-model="pwd" autofocus clearable type="password" @keyup.enter="validateCode()">
                    <template v-slot:append>
                        <q-btn round dense flat icon="lock_open" @click="validateCode()"/>
                    </template>
                    
                </q-input>
                <span v-if="!pwdCorrect" class="text-red-8 q-ma-sm">
                    <q-icon name="error_outline"/>密码错误!
                </span>
            </q-card-section>

            <q-card-actions align="right" class="text-primary">
                <q-btn flat label="取消" @click="close()" size="lg"/>
            </q-card-actions>
        </q-card>
    </q-dialog>
</template>

<script>
import { collectOrCancel } from '../api/scene'
export default {
    name:'PasswordValidate',
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
        setCollect:{
            type:Function
        }
    },
    data(){
        return{
            pwdCorrect:true,
            pwd:''
        }
    },
    methods:{
        async validateCode(){
            if(this.scene.pwd===this.pwd){
                this.close();
                var user=JSON.parse(sessionStorage.getItem('user'));
                collectOrCancel({
                    user_id:user.user_id,
                    scene_id:this.scene.scene_id
                }).then((response)=>{
                    console.log(response);
                    if(response.data.is_collect==0){
                        //this.scene.is_collect=false;
                        this.$q.notify({
                            type: 'positive',
                            message: `取消收藏成功！`,
                            position: 'top'
                        });
                    }else{
                        //this.scene.is_collect=true;
                        this.$q.notify({
                            type: 'positive',
                            message: `收藏成功！`,
                            position: 'top'
                        });
                    }
                    this.openScene();
                });
                
            }
            else{
                this.pwdCorrect=false;
            }
        },
        openScene(){
            if(sessionStorage.getItem('flag')=="0"){
                this.pwdCorrect=true;
                this.pwd='';
                sessionStorage.setItem("scene_id",this.scene.scene_id);
                this.$router.push("/Scene");
            }else{
                this.setCollect(true);
                this.pwd='';
            }
            
        },
        close(){
            this.closeDialog(false);
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
<template>
    <div class="login">
        <div class="row">
            <q-card class="card my-card q-px-sm q-pt-md bg-grey-2 col-md-4 col-xs-12 shadow-10">
                <q-card-section class="title">
                    <div class="text-h5">欢迎来到爱语学习</div>
                </q-card-section>
                <q-card-section>
                    <q-form class="q-gutter-xs">
                        <q-select v-model="account.role" :options="roles" label="角色：" class="role"
                        lazy-rules 
                        :rules="[(val) => (val.length > 0&&val!=='') || '请选择角色']">
                            <template v-slot:prepend>
                                <q-icon name="supervisor_account"/>
                            </template>
                        </q-select>
                        <q-input v-model="account.username" label="昵称：" clearable class="username" hint="请输入注册时设置的昵称"
                        lazy-rules
                        :rules="[(val) => (val.length>1&&val.length<13) || '请输入正确昵称']">
                            <template v-slot:prepend>
                                <q-icon name="account_box"></q-icon>
                            </template>
                        </q-input>
                        <q-input v-model="account.password" label="密码：" clearable :type="pwd_seen ? 'text':'password'" class="username"
                        lazy-rules
                        :rules="[(val) => (val.length>5&&val.length<13) || '请输入正确密码']">
                            <template v-slot:prepend>
                                <q-icon name="lock_open"></q-icon>
                            </template>
                            <template v-slot:append>
                                <q-icon :name="pwd_seen ? 'visibility':'visibility_off'" class="cursor-pointer" @click="pwd_seen=!pwd_seen"></q-icon>
                            </template>
                        </q-input>
                        <div class="button">
                            <q-btn :disable="isDisabled" push color="primary" label="登录" class="btn" @click="Login()"/>
                        </div>
                        <div class="jump">
                            <span>还没有账号？马上去</span>
                            <a href="#/Register">注册</a>
                            <span>!</span>
                        </div>
                        <div class="allow">
                            <q-toggle v-model="allowed" class="toggle"/><span class="span">我已阅读相关</span><q-btn dense flat color="primary" size="md" label="注册协议和隐私条例" class="protocol" @click="policy=true"/>
                        </div>
                        
                    </q-form>
                </q-card-section>
            </q-card>
        </div>
        <Policy :policy="policy" :close="closePolicy"></Policy>
    </div>
</template>
<script>
import { login } from '../api/login'
import cookie from 'js-cookie'
import Policy from '@/components/Policy.vue';
export default {
    name:'Login',
    components: { 
        Policy
    },
    data () {
        return {
            pwd_seen:false,
            allowed:false,
            policy:false,
            account: {
                role:'',
                username:'',
                password:''
            },
            roles:['老师','学生']

        }
    },
    methods:{
        async Login(){
            var role;
            if(this.account.role=='老师'){
                role=0;
            }else{
                role=1;
            }
            login({
                  role:role,
                  username:this.account.username,
                  password:this.account.password}
                ).then((response)=>{
                    console.log(response,"login");
                    if(response.msg=="输入的用户名或者密码有误"){
                        this.$q.notify({
                            type: 'negative',
                            message: `输入的用户名或者密码有误！`,
                            position: 'top'
                        });
                        return;
                    }else{
                        cookie.set('username', response.data.username);
                        cookie.set('user_id', response.data.user_id);
                        sessionStorage.setItem('user',JSON.stringify(response.data));
                         this.$q.notify({
                            type: 'positive',
                            message: `登录成功！`,
                            position: 'top'
                        }); 
                        this.$router.push({ path: '/Home' })
                    }
            });
        },
        closePolicy(){
            this.policy=false;
            this.allowed=true;
        }
    },
    watch:{

    },
    computed:{
        isDisabled(){
            if(this.allowed&&this.account.role!==''&&this.account.username.length>1&&this.account.username.length<13
            &&this.account.password.length>5&&this.account.password.length<13){
                return false;
            }else return true;
        }
    },
    created(){

    }
}
</script>
<style>
.login{
    width: 100%;
    height: 100%;
    padding: 5% 0% 10% 30%;
    background-size: cover;
    background-image: url(../assets/bg1.png);
    min-width: 1000px;
    min-height: 730px;
    background-color: #fff;
}

.login .card{
     opacity: 0.8;
}
.login .title{
    text-align: center;
    font-family: '楷体';
    letter-spacing: 2px;
    color: #598EFC;
   
}

.login .role{
    width:60%;
    font-size: 16px;
    letter-spacing: 1px;
}

.login .username{
    font-size: 16px;
    letter-spacing: 1px;
}

.login .button{
    text-align: center;
    padding:5% 5% 0% 5%;

}

.login .btn{
    width: 100%;
    letter-spacing: 10px;
    font-size: 18px;
}

.login .jump{
    font-size: 16px;
    font-family: '楷体';
    text-align: center;
    color:#49A0F3;
}

.login .allow{
    font-size: 15px;
    font-family: '楷体';
    text-align: center;
}

.login .protocol{
    margin-top: -3px;
}

.login .toggle{
    margin-top: -4px;
}
</style>
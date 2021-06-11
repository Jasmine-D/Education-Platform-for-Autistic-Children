<template>
    <div class="register">
        <div class="row">
            <q-card class="card my-card q-px-sm q-pt-md bg-grey-2 col-md-5 col-xs-12 shadow-10">
                <q-card-section class="title">
                    <div class="text-h5">欢迎来到爱语学习</div>
                </q-card-section>
                <q-card-section>
                    <q-form class="q-gutter-xs">
                        <q-select v-model="account.role" :options="roles" label="选择角色：" class="role" 
                        :rules="[(val) => (val.length > 0&&val!=='') || '请选择角色']">
                            <template v-slot:prepend>
                                <q-icon name="supervisor_account"/>
                            </template>
                        </q-select>
                        <q-input v-model="account.username" label="设置昵称：" clearable class="username" :loading="isCheckingName"
                        :rules="[(val) => (val.length>1&&val.length<13) || '昵称长度为2~12个字符']" @blur="checkName">
                            <template v-slot:prepend>
                                <q-icon name="account_box"></q-icon>
                            </template>
                            <template v-if="haveBlured1" v-slot:label>
                                <q-icon
                                :name="nameOK ? 'check_circle_outline' : 'error_outline'"
                                :color="nameOK ? 'green-8' : 'red-8'"/>
                                <span :class="nameOK ? 'text-green-8' : 'text-red-8'">{{
                                nameOK ? "该昵称可以使用" : "该昵称已经被占用"
                                }}</span>
                            </template>
                        </q-input>
                        <q-input
                            clearable
                            v-model="account.phone"
                            label="绑定手机号:"
                            lazy-rules
                            :rules="[(val) => checkMobile(val) || '请输入正确的手机号']" class="username"
                            :loading="isCheckingPhone"
                            @blur="checkPhoneExist"
                            prefix="+86">   
                            <template v-slot:prepend>
                                <q-icon name="smartphone"></q-icon>
                            </template>
                            <!--<template v-slot:after>
                                <q-btn
                                    dense
                                    flat
                                    color="primary"
                                    icon="send"
                                    :disable="sentDisabled"
                                    :label="sentAuthCodeText"
                                    @click="sentAuthCode()"
                                />
                            </template>-->
                            <template v-if="haveBlured2" v-slot:label>
                                <q-icon
                                :name="phoneOK ? 'check_circle_outline' : 'error_outline'"
                                :color="phoneOK ? 'green-8' : 'red-8'"/>
                                <span :class="phoneOK ? 'text-green-8' : 'text-red-8'">{{
                                phoneOK ? "该手机号可以使用" : "该手机号已经被占用"
                                }}</span>
                            </template>
                        </q-input>
                        <!--<q-input mask="  #  -  #  -  #  -  #  "
                            v-model="account.authCode"
                            label="手机验证码:"
                            lazy-rules
                            :rules="[(val) => (val !== null && val !== '') || '请输入验证码']" class="authCode">
                            <template v-slot:prepend>
                                <q-icon name="vpn_key"></q-icon>
                            </template>
                        </q-input>-->
                        <q-input
                        v-model="account.password"
                        :type="pwd_seen ? 'text':'password'"
                        label="设置密码：" clearable
                        :rules="[(val) => (val.length>5&&val.length<13) || '密码长度为6~12位']">
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
                        <q-input
                        v-model="password"
                        :type="spwd_seen ? 'text':'password'"
                        label="确认密码：" clearable
                        :rules="[(val) => val === this.account.password || '两次密码不同']">
                            <template v-slot:prepend>
                                <q-icon name="check_box"></q-icon>
                            </template>
                            <template v-slot:append>
                                <q-icon
                                :name="spwd_seen ? 'visibility':'visibility_off'"
                                class="cursor-pointer"
                                @click="spwd_seen = !spwd_seen"
                                />
                            </template>
                        </q-input>
                        <div class="button">
                            <q-btn :disable="isDisabled" push color="primary" label="注册" class="btn" @click="Register()"/>
                        </div>
                        <div class="jump">
                            <span>已有账号？马上去</span>
                            <a href="#/Login">登录</a>
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
import { checkUsername,checkPhoneNumber,register} from '../api/register'
import Policy from '@/components/Policy.vue';
export default {
    name:'Register',
    components: { 
        Policy
    },
    data(){
        return{
            account:{
                role:'',
                username:'',
                phone:'',
                //authCode:'',
                password:''

            },
            roles:['老师','学生'],
            timer: null,
            countDown:120,
            pwd_seen:false,
            spwd_seen:false,
            password:'',
            isCheckingName: false,
            isCheckingPhone:false,
            haveBlured1:false,
            haveBlured2:false,
            nameOK:false,
            phoneOK:false,
            allowed:false,
            policy:false
        }
    },
    methods:{
        /*disposeAuthCode(val){
            return val.slice(2, 3) +val.slice(8, 9) +val.slice(14, 15) +val.slice(20, 21);
        },*/
        checkMobile(s) {
            var length = s.length;
            if (length == 11 && /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(14[0-9]{1})|)+\d{8})$/.test(s)) {
                return true;
            } else {
                return false;
            }
        },
        async checkPhoneExist(){
            if(!this.checkMobile(this.account.phone)){
                return;
            }
            this.isCheckingPhone=true;
            checkPhoneNumber({phone:this.account.phone}).then((response)=>{
                console.log(response,"checkPhoneNumber");
                if(response.data){
                    this.phoneOK=true;
                    this.haveBlured2=true;
                }else{
                    this.phoneOK=false;
                    this.haveBlured2=true;
                }
                
            });
            this.isCheckingPhone=false;
            /*var count=5;
            var countTime=setInterval(()=>{
                if(count==0){
                    countTime=clearInterval(countTime);
                    count=5;
                    this.phoneOK=false;
                    this.haveBlured2 = true;
                    this.isCheckingPhone = false;
                }else{
                    count-=1;
                }
            },1000);*/
        },
        /*sentAuthCode() {
            if (this.isSendingAuthCode) {
                this.$q.notify({
                position: "center",
                type: "warning",
                message: `验证码已发送，请耐心等待一会儿`,
                });
            }else if(this.phoneOK){
                // 设置倒计时
                this.timer = setInterval(() => {
                    if (this.countDown == 0) {
                        this.timer = clearInterval(this.timer);
                        this.countDown = 120;
                    } else {
                        this.countDown -= 1;
                    }
                }, 1000);
            }
        },*/
        async checkName(){//检查昵称是否重复
            if (!this.account.username||this.account.username.length<2||this.account.username.length>12) {
                return;
            }
            this.isCheckingName=true;
            checkUsername({username:this.account.username}).then((response)=>{
                console.log(response,"checkUsername");
                if(response.data){
                    this.nameOK=true;
                    this.haveBlured1 = true;
                }else{
                    this.nameOK=false;
                    this.haveBlured1 = true;
                }
            });
            this.isCheckingName = false;
            /*var count=5;
            var countTime=setInterval(()=>{
                if(count==0){
                    countTime=clearInterval(countTime);
                    count=5;
                    this.nameOK=false;
                    this.haveBlured1 = true;
                    this.isCheckingName = false;
                }else{
                    count-=1;
                }
            },1000);*/
            
        },
        async Register(){
            var role;
            if(this.account.role=='老师'){
                role=0;
            }else{
                role=1;
            }
            register({
                role:role,
                username:this.account.username,
                phone:this.account.password,
                password:this.password
            }).then((response)=>{
                console.log(response,"register");
                this.$q.notify({
                    type: 'positive',
                    message: `注册成功！`,
                    position: 'top'
                });
                this.$router.push({ path: '/Login' });
            }).catch((error)=>{
                console.log(error,"register error");
                this.$q.notify({
                    type: 'negative',
                    message: `注册失败！`,
                    position: 'top'
                });
            })
        },
        closePolicy(){
            this.policy=false;
            this.allowed=true;
        }

    },
    watch:{

    },
    computed:{
        /*sentAuthCodeText() {
            if (!this.timer) {
                return "发送验证码";
            } else {
                return this.countDown + "s后再试";
            }
        },
        sentDisabled() {
            if (this.checkMobile(this.account.phone) && !this.timer) {
                return false;
            }
            return true;
        },
        isSendingAuthCode() {
            if (this.timer) {
                return true;
            } else {
                return false;
            }
        },*/
        isDisabled(){
            if(this.allowed&&this.account.role!==''&&this.account.username.length>1&&this.account.username.length<13
            &&this.account.password.length>5&&this.account.password.length<13&&this.password===this.account.password
            &&this.checkMobile(this.account.phone)&&this.nameOK&&this.phoneOK){
                return false;
            }else{
                return true;
            }
        }

    },
    created(){

    }
}
</script>

<style>
.register{
    width: 100%;
    height: 100%;
    padding: 2% 0% 2% 30%;
    background-size: cover;
    background-image: url(../assets/bg1.png);
    min-width: 1000px;
    min-height: 730px;
    background-color: #fff;
}

.register .card{
     opacity: 0.8;
}

.register .title{
    text-align: center;
    font-family: '楷体';
    letter-spacing: 2px;
    color: #598EFC;
}

.register .role{
    width:60%;
    font-size: 16px;
    letter-spacing: 1px;
}

.register .username{
    font-size: 16px;
    letter-spacing: 1px;
}

.register .authCode{
    font-size: 16px;
    letter-spacing: 1px;
    width:70%;
}

.register .button{
    text-align: center;
    padding:5% 5% 0% 5%;

}

.register .btn{
    width: 100%;
    letter-spacing: 10px;
    font-size: 18px;
}

.register .jump{
    font-size: 16px;
    font-family: '楷体';
    text-align: center;
    color:#49A0F3;
}

.register .allow{
    font-size: 15px;
    font-family: '楷体';
    text-align: center;
}

.register .protocol{
    margin-top: -3px;
}

.register .toggle{
    margin-top: -4px;
}
</style>
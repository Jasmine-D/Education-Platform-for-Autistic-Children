<template>
  <div class="bg_img">
    <Header :tab="headerName"/>
    <div style="margin-left:18%;">
      <div style="float:left;width:20%;">
        <Menu :link="link"/>
      </div>
      <div style="float:left;width:60%; height:fit-content; text-align:center;margin-top:4%">
        <div style="width:80%;margin-left:15%;">
        <div class="my-card q-pa-md q-mb-lg shadow-2 rounded-borders bg-white">
          <div class="row" style="justify-content:center">
            <q-icon name="o_person" size="lg" style="display:inline"/>
            <div class="text-h5" style="display:inline; padding-top:2px">个人资料</div>
            <br/><br/>
          </div>
          <div class="row q-mt-md">
            <div class="col-6">
              <p class="text-subtitle1 text-teal-4" style="display:inline">用户名：</p>
              <p class="text-subtitle1 text-teal-4" style="display:inline">{{userInfo.username}}</p>
            </div>
            <div class="col-auto">
              <p class="text-subtitle1 text-teal-4" style="display:inline">手机号：</p>
              <p class="text-subtitle1 text-teal-4" style="display:inline">{{userInfo.phone}}</p>
            </div>
          </div>
        </div>
        <div class="my-card q-pa-md q-mb-lg shadow-2 rounded-borders bg-white">
          <div class="row" style="justify-content:center">
            <q-icon name="o_upload" size="lg" style="display:inline"/>
            <div class="text-h5" style="display:inline; padding-top:2px">上传头像</div>
            <br/><br/>
          </div>
          <div class="row q-mt-md" style="justify-content:center">
            <q-uploader
              style="max-width: 300px"
              hide-upload-btn
              label="上传头像(图片大小不超过1MB)："
              color="purple"
              max-files="1"
              flat
              bordered
              accept=".jpg, .png"
              :filter="checkFile"
              @rejected="onRejected"
              @removed="deleteFile"
              @added="addFile"
            />
          </div>
          <div style="margin-top:2%;">
            <q-btn push color="purple" label="上传头像" icon-right="backup" :disable="avatar==''" @click="uploadAvatar()"/>
          </div>
        </div>
        <div class="my-card q-pa-md q-mb-lg shadow-2 rounded-borders bg-white">
          <div class="row" style="justify-content:center">
            <q-icon name="o_edit" size="lg" style="display:inline"/>
            <div class="text-h5" style="display:inline; padding-top:2px">修改密码</div>
            <br/><br/>
          </div>
          <div style="text-align:center;margin:0% 25%;">
            
            <q-form
              @submit="onSubmit"
              @reset="onReset"
              class="q-gutter-md q-field"
            >
              <!--<q-input
                v-model="oldKey"
                dense
                Placeholder="当前密码 *"
                hint="请输入当前密码"
                lazy-rules
                :rules="[ val => val && val.length > 0 || '不能为空']"
                class="q-placeholder"
              />-->

              <q-input
                v-model="newKey"
                :type="pwd_seen ? 'text':'password'"
                dense clearable
                Placeholder="新密码 *"
                hint="请输入新密码"
                lazy-rules
                :rules="[(val) => (val.length>5&&val.length<13) || '密码长度为6~12位']"
              >
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
                v-model="newKey_2"
                :type="spwd_seen ? 'text':'password'"
                dense clearable
                Placeholder="确认新密码 *"
                hint="请再次输入新密码"
                lazy-rules
                :rules="[(val) => val === this.newKey || '两次密码不同']"
              >
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

              <div>
                <q-btn label="提交" type="submit" color="purple"/>
                <q-btn label="重置" type="reset" color="purple" flat class="q-ml-sm" />
              </div>
            </q-form>

          </div>
        </div>
        </div>
      </div>
      <div style="clear:both;"></div>
    </div>
    
  </div>
</template>

<style scoped>
  .q-field {
    font-size:16px;
  }
  .my-card {
    width: 100%;
    text-align: center;
  }

  .bg_img {
    width: 100%;
    height: 100%;
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
import { getUserInfo,modifyAvatar,modifyPassword} from '../api/account'
export default {
  components: { 
    Header,
    Menu
  },
  data () {
    return {
      //oldKey: null,
      headerName:'Account',
      link:'Account',
      newKey: '',
      newKey_2: '',
      userInfo:'',
      user:'',
      avatar:'',
      pwd_seen:false,
      spwd_seen:false,
    }
  },
  methods: {
    checkFile(files){
      return files.filter(file=>file.size/1024/1024<1);
    },
    
    onRejected (rejectedEntries) {
      this.$q.notify({
        type: 'negative',
        message: `请上传1MB以内的jpg或png格式的图片！`,
        position: 'top'
      });
    },
    deleteFile(files){
      console.log(files,"avatar pic removed");
      this.avatar='';
    },
    addFile(files){
      console.log(files,"avatar pic added");
      this.avatar=files[0];
    },

    onSubmit () {
      let fd = new FormData();
      fd.append('user_id',this.user.user_id);
      fd.append('password',this.newKey);
      modifyPassword(fd).then((response)=>{
        console.log(response,"modifyPassword");
        this.$q.notify({
            type: 'positive',
            message: `修改密码成功！请用新密码重新登录！`,
            position: 'top'
        });
        this.$router.push({ path: '/Login' })
      })
    },

    onReset () {
      //this.oldKey = null
      this.newKey = ''
      this.newKey_2 = ''
    },
    isDisabled(){
      if(this.newKey.length>5&&this.newKey.length<13&&this.newKey===this.newKey_2){
        return false;
      }
      return true;
    },
    async getUserInformation(){
      getUserInfo({user_id:this.user.user_id}).then((response)=>{
        console.log(response,"getUserInfo");
        this.userInfo=response.data.user;
      })
    },
    async uploadAvatar(){
      let fd = new FormData();
      fd.append('user_id',this.user.user_id);
      fd.append('file',this.avatar);
      modifyAvatar(fd).then((response)=>{
        this.$q.notify({
          type: 'positive',
          message: `修改头像成功！`,
          position: 'top'
        });
        console.log(response,"modifyAvatar");
        this.user.avatar=response.data.avatar;
        sessionStorage.setItem('user',JSON.stringify(this.user));
        
      });
      this.$router.go(0);
    }
  },
  created(){
    this.user=JSON.parse(sessionStorage.getItem('user'));
    this.getUserInformation();
  }
}
</script>
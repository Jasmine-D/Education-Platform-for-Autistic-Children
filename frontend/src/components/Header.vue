<template>
  <div>
    <q-bar class="bg-purple-3 shadow-2" style="padding:1.5% 10%">
      <div>欢迎您来到济梦爱语~</div>
      <q-space />
        <q-btn round>
          <q-avatar size="150%">
            <img :src="user.avatar"/>
          </q-avatar>
          <q-menu>
            <q-list style="min-width: 100px">
              <q-item clickable v-close-popup>
                <q-item-section @click="toAccount()">个人中心</q-item-section>
              </q-item>
              <q-item clickable v-close-popup>
                <q-item-section @click="logOut()">退出登录</q-item-section>
              </q-item>
            </q-list>
          </q-menu>
        </q-btn>
      <div>{{user.username}}</div>
    </q-bar>
    
    <q-toolbar class="bg-white text-black shadow-2 row" style="padding:0% 8%">
      <q-btn flat shrink class="col-2">
        <img src="../assets/logo1.png" style="max-height:65px">
      </q-btn>
      <q-space />
      <!--
        notice shrink property since we are placing it
        as child of QToolbar
      -->
      <div class="col-4 adjust" >
          <q-input v-model="text" rounded outlined :placeholder="holder" v-if="show_input" color="purple-3" bg-color="grey-1" clearable
           @keyup.enter="JumptoSearch()">
            <template v-slot:prepend>
                <q-select rounded outlined v-model="model" :options="options" class="selectType" color="purple-3" bg-color="grey-2"></q-select>
            </template>
          </q-input>
      </div>
      

      <q-tabs v-model="tab1" class="text-black" shrink>
        <q-tab name="Search" icon="r_search" label="搜索" @click="changeStatus()"/>
        <q-tab name="Home" icon="o_home" label="主页" @click="toHome()"/>
        <q-tab name="SceneList" icon="o_face" label="情景交流" @click="toScene()"/>
        <q-tab name="WordSet" icon="o_library_books" label="单词集" @click="toWordSet()"/>
        <q-tab name="Account" icon="o_account_circle" label="个人" @click="toAccount()"/>
      </q-tabs>
    </q-toolbar>
    
    

  </div>
</template>

<script>

export default {
  name: 'Header',
  props:{
    searchSceneByName:{
      type:Function,
      default:null
    },
    tab:{
      type:String,
      default:null
    },

  },
  data () {
    return {
      user:'',
      tab1:this.tab,
      model: '情景交流场景',
      text:'',
      options: [
        '情景交流场景', '单词集'
      ],
      holder: '请输入场景名进行搜索',
      show_select: false,
      show_input: false
    }
  },
  watch: {
    model: function () {
      if (this.model === '情景交流场景') {
        this.holder = '请输入场景名进行搜索'
      } else if (this.model === '单词集') {
        this.holder = '请输入单词集名进行搜索'
      }
    }
  },
  methods: {
    changeStatus () {
      if(this.show_select == false && this.show_input == false) {
        this.show_select = true,
        this.show_input = true
      }
      else {
        this.show_select = false,
        this.show_input = false
      }    
    },
    toScene(){
      //console.log(this.$router.path)
      if(this.$route.path=="/SceneList"){
        this.$router.go(0);
      }else{
        this.$router.push({ path: '/SceneList' });
      }
      
      //this.$router.go(0);
    },

    JumptoSearch(){
      if(this.model==='情景交流场景'){
        sessionStorage.setItem('searchScene',this.text);
        if(this.$route.path=="/SceneList"){
          //console.log(1);
          this.$router.go(0);
        }
        else{
          this.$router.push('/SceneList');
        }
        
      }
      if(this.model==='单词集'){  
        sessionStorage.setItem('search',this.text);
        if (this.$route.path !== '/WordList') {
          console.log("jump to wordlist")
          this.$router.push({
            path: '/WordList',
            name: 'WordList'
          })
        } else {
          console.log("stay in current page")
          this.$emit('showPage')
        }
      }

    },

    toAccount(){
      this.$router.push('/Account');
    },
    toHome(){
      this.$router.push('/Home');
    },
    toWordSet(){
      if (this.$route.path !== '/WordList') {
          console.log("jump to wordlist")
          this.$router.push({
            path: '/WordList',
            name: 'WordList'
          })
        } else {
          console.log("stay in current page")
          this.$emit('showPage')
        }
    },
    logOut(){
      
      this.$q.notify({
          type: 'positive',
          message: `退出登录成功！`,
          position: 'top'
      });
      sessionStorage.clear();
      this.$router.push('/Login');
    }
  },
  created(){
    this.user=JSON.parse(sessionStorage.getItem('user'));
  }
}
</script>

<style scoped>
  .q-avatar {
    height: 2em;
    width: 2em;
  }
  .adjust{
    /**margin-top:10px;*/
    margin-right: 5px;
  }
  .selectType{
    margin-left: -12px;
  }
</style>
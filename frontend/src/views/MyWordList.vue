<template>
  <div class="bg_img">
    <Header :tab="headerName"/>
    <div style="margin-left:18%;">
      <div style="float:left;width:20%;">
        <Menu :link="link"/>
      </div>
      <div style="float:left;width:80%;">
      <div class="q-my-lg" style="width:80%; height:fit-content; margin-left:10%; text-align:center">
        <div class="q-pb-lg">
          <q-btn push rounded color="purple-5" icon="backup" class="btn" @click="createWordSet = true">创建单词集</q-btn>
        </div>
        <q-dialog v-model="createWordSet">
          <div class="my-card q-pa-md q-mb-lg shadow-2 rounded-borders bg-yellow-1">
            <q-form
              @submit="onSubmit"
              @reset="onReset"
              class="q-gutter-md q-field"
            >
              <q-input
                v-model="name"
                dense
                Placeholder="单词集名称"
                hint="请输入单词集名称(不超过20字)"
                counter
                maxlength="20"
                color="purple"  
                :rules="[val => !!val || '不能为空']"             
              >
                <template v-slot:append>
                  <q-icon v-if="name !== ''" name="close" @click="name = ''" class="cursor-pointer" />
                </template>
              </q-input>

              <q-input
                v-model="intro"
                dense
                Placeholder="单词集简介"
                hint="请输入单词集简介(不超过40字)"
                counter
                maxlength="40"
                color="purple"
                :rules="[val => !!val || '不能为空']"   
              >
                <template v-slot:append>
                  <q-icon v-if="intro !== ''" name="close" @click="intro = ''" class="cursor-pointer" />
                </template>
              </q-input>

              <div style="display:flex;justify-content:center">
                <q-toggle v-model="isLock" label="是否加密？" color="purple" style="float:left"/>
                <q-input 
                  v-model="password" 
                  filled 
                  dense 
                  color="purple" 
                  :type="isPwd ? 'password' : 'text'" 
                  Placeholder="请输入密码"
                  v-if="this.isLock===true"
                  :rules="[val => !!val || '不能为空']"   
                >
                  <template v-slot:append>
                    <q-icon
                      :name="isPwd ? 'visibility_off' : 'visibility'"
                      class="cursor-pointer"
                      @click="isPwd = !isPwd"
                    />
                  </template>
                </q-input>

              </div>

              <div>
                <q-btn label="提交" type="submit" color="purple" />
                <q-btn label="重置" type="reset" color="purple" flat class="q-ml-sm" />
              </div>
            </q-form>
          </div>
        </q-dialog>
        <div class="q-pb-lg row" style="display:flex;justify-content:center" v-for="(setInfo,index) in setListInfo" :key="index">
          <WordSet
            :isVisible="setInfo.is_visible"
            :setName="setInfo.set_name"
            :setCreator="setInfo.set_creator"
            :cardNum="setInfo.card_num"
            :setContent="setInfo.set_intro"
            :setId="setInfo.set_id"
            :isStar="setInfo.is_star"
            :type=0>
          </WordSet>
        </div>
        <div class="q-pa-lg flex flex-center">
          <q-pagination
            v-model="curPage"
            :max="totalPage"
            :max-pages="5"
            color="primary"
            :boundary-numbers="true"
            round push
            size="20px"
          >
          </q-pagination>
        </div>
      </div>
      </div>
      <div style="clear:both;"></div> 
    </div>
  </div>
</template>

<style scoped>
  .my-card {
     width: 100%;
     text-align: center;
  }

  .bg_img {
    width: 100%;
    min-height: 800px;
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

  .btn{
    font-family: '楷体';
    font-size: 22px;
    letter-spacing: 1px;
  } 
 
</style>

<script>
import Header from '@/components/Header.vue'
import Menu from '@/components/Menu.vue'
import WordSet from '@/components/WordSet.vue'
import{mySet, uploadSet}from '../api/wordset'

export default {
  inject: ['reload'],

  components: {
    Header,
    Menu,
    WordSet,
  },

  data() {
    return {
      headerName:'Account',
      link:'MyWord',
      isStar: false,
      curPage: 1,
      totalNum: 0,
      totalPage: 0,
      user:{},
      pageSize:5,
      setListInfo:[],
      createWordSet: false,
      name: '',
      intro: '',

      isLock: false,
      isVisible:true,
      password: '',
      isPwd: true,
      result: false

    }
  },

  watch:{
    curPage(){
      this.showPage();
    }
  },

  mounted () {
    
    console.log("In the my word list")
    this.user=JSON.parse(sessionStorage.getItem('user'))
    this.showPage()
  },

  methods: {

    showPage() {
      mySet(this.curPage,this.pageSize,this.user.user_id)
        .then((response)=>{
          this.setListInfo=response.data.myWordSet;
          this.totalNum=response.data.total;
          this.totalPage=Math.ceil(this.totalNum/this.pageSize)
        })
        .catch((error)=>{
          console.log(error)
      })
    },
    

    onSubmit () {
      if(this.isLock===false) this.isVisible=true
      else this.isVisible=false

      if(this.isVisible===true) this.password='#'
      let fd=new  FormData();
      fd.append('isVisible',this.isVisible);
      fd.append('pwd',this.password);
      fd.append('setIntro',this.intro);
      fd.append('setName',this.name);
      fd.append('userId',this.user.user_id);
      uploadSet(fd)
        .then((response)=>{
          console.log(response);
          this.result=response.data.isSucceed;
          if(this.result===true){
            this.$q.notify({
              color: 'green-4',
              textColor: 'white',
              icon: 'cloud_done',
              message: '提交成功！'
            })
            this.createWordSet=false
            this.reload()
          } else {           
              this.$q.notify({
                color: 'red-4',
                textColor: 'white',
                icon: 'cloud_off',
                message: '提交失败！'
              })       
          }
        })
        .catch((error)=>{
          console.log(error)
      })      
    },

    onReset () {
      this.name = ''
      this.intro = ''
    }
  }
}
</script>
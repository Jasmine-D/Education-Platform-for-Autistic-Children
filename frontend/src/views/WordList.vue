<template>
  <div class="bg_img">
    <Header :tab="headerName" @showPage="showPage"/>
    <div class="q-px-xs row" style="padding-left:18%; padding-top:1.5%">
        <p style="float:left; font-weight:bold">共搜索出</p>
        <p style="float:left; font-weight:bold">{{totalNum}}</p>
        <p style="float:left; font-weight:bold">个单词集</p>
    </div>
    <div class="q-pb-lg row" style="display:flex;justify-content:center" v-for="(setInfo,index) in setListInfo" :key="index">
      <WordSet
        :isVisible="setInfo.is_visible"
        :setName="setInfo.set_name"
        :setCreator="setInfo.set_creator"
        :cardNum="setInfo.card_num"
        :setContent="setInfo.set_intro"
        :setId="setInfo.set_id"
        :isStar="setInfo.is_star"
        :type=1>
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
</template>

<style scoped>

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
 
</style>

<script>
import Header from '@/components/Header.vue'
import WordSet from '@/components/WordSet.vue'
import{searchSet,getAllSet}from '../api/wordset'
export default {
  inject: ['reload'],

  components: { 
    Header,
    WordSet,
  },

  data() {
    return {
      headerName:'WordSet',
      isStar: false,
      curPage: 1,
      user:{},
      pageSize:5,
      setListInfo:[],
      totalNum:0,
      totalPage:0,
      searchName:''
    }
  },

  watch:{
    curPage(){
      this.showPage();
    }
  },

  mounted () {  
    sessionStorage.setItem('search','')
    this.showPage()
    console.log("In the word list")
  },

  methods: {
    showPage () {
      this.user=JSON.parse(sessionStorage.getItem('user'));
      this.searchName = sessionStorage.getItem('search')
      console.log('searchName', this.searchName)
      if (this.searchName !== '') {
        console.log('searchName', this.searchName)
        searchSet(this.user.user_id,this.searchName,this.pageSize,this.curPage)
          .then((response)=>{
            this.setListInfo=response.data.wordSet;
            this.totalNum=response.data.total;
            this.totalPage=Math.ceil(this.totalNum/this.pageSize)
          })
          .catch((error)=>{
            console.log(error)
        })
        sessionStorage.setItem('search','')
      }
      else{
        console.log('searchName', this.searchName)
        getAllSet(this.curPage,this.pageSize,this.user.user_id)
          .then((response)=>{
            this.setListInfo=response.data.allSet;
            this.totalNum=response.data.total;
            this.totalPage=Math.ceil(this.totalNum/this.pageSize)
          })
          .catch((error)=>{
            console.log(error)
        })
        sessionStorage.setItem('search','')
      }
    },
  },
}
</script>

<style scoped>
  .my-card {
     width: 55%;
     text-align: center;
  }
</style>
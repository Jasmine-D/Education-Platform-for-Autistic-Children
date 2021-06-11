<template>
  <div class="bg_img">
    <Header :tab="headerName"/>
    <div class="q-px-xs row" style="padding-left:18%; padding-top:1.5%">
        <p style="float:left; font-weight:bold">共含有</p>
        <p style="float:left; font-weight:bold">{{totalNum}}</p>
        <p style="float:left; font-weight:bold">个单词卡</p>
    </div>
    <div class="q-pb-lg" style="display:flex;justify-content:center" v-for="(cardInfo,index) in cardListInfo" :key="index">
      <WordCard
        :cardId="cardInfo.card_id"
        :cardName="cardInfo.card_name"
        :cardContent="cardInfo.card_content"
        :picUrl="cardInfo.pic_url"
        :audioUrl="cardInfo.audio_url"
        :type=1>
      </WordCard>
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

<script>
import Header from '@/components/Header.vue'
import WordCard from '@/components/WordCard.vue'
import{searchCard}from '../api/wordcard'
export default {
  components: { 
      Header,
      WordCard,
  },
  data() {
    return {
      headerName:'WordSet',
      curPage: 1,
      pageSize: 5,
      setId: 0,
      totalNum: 0,
      totalPage: 0,
      cardListInfo: []
    }
  },

  watch:{
    curPage(){
      this.showPage();
    }
  },

  mounted () {
    this.setId = this.$route.query.setId
    this.showPage()
  },
  methods: {
    showPage(){
      searchCard(this.curPage, this.pageSize, this.setId)
        .then((response)=>{
          this.cardListInfo=response.data.wordCard;
          this.totalNum=response.data.total;
          this.totalPage=Math.ceil(this.totalNum/this.pageSize)
        })
        .catch((error)=>{
          console.log(error)
      })
    }
  }
}
</script>

<style scoped>
  .my-card {
     width: 55%;
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
</style>
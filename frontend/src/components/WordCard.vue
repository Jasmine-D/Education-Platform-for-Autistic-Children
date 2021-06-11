<template>
  <q-card class="my-card row q-py-xm" style="text-align:left">
    <div class="col-3 q-pa-sm text-h5 text-purple" style="text-align:center">{{cardName}}</div>
    <q-separator vertical/>
    <div class="col-5 q-pa-sm text-subtitle1" style="word-break:break-all">
      {{cardContent}}
    </div>
    <div class="col-3 q-pa-sm" style="text-align:center">
      <img :src="picUrl" style="width: auto; height: auto; max-width: 100%; max-height: 80px" />
    </div>
    <div class="col-auto q-py-sm" v-if="type===1">
      <q-btn
          flat
          round
          icon="o_volume_up"
          style="color:purple" 
          @click="playAudio"
        />
    </div>
    <div class="col-auto q-py-sm" v-if="type===0">
      <q-btn 
        flat
        round
        icon="o_volume_up"
        style="color:purple; display:block" 
        @click="playAudio"
      />
      <q-btn 
        flat
        round
        icon="o_delete"
        style="color:purple; display:block"
        @click="delCard"
      />
    </div>
  </q-card>
  
</template>

<script>
import{deleteCard}from '../api/wordcard'
export default {
  inject: ['reload'],

  name: 'WordCard',
  props: {
    cardId: {
      type: Number,
      required: true
    },
    cardName: {
      type: String,
      required: true
    },
    cardContent: {
      type: String,
      required: true
    },
    picUrl: {
      type: String,
      required: true
    },
    audioUrl: {
      type: String,
      required: true
    },
    type: {
      type: Number,
      required: true
    }
  },
  methods: {
    playAudio(){
      let audio = new Audio()
      audio.src = this.audioUrl
      audio.play();
    },
    delCard(){
      deleteCard(this.cardId)
        .then((response)=>{
          this.result=response.data.isSucceed;
          if(this.result == true){
            this.$q.notify({
              color: 'green-4',
              textColor: 'white',
              icon: 'cloud_done',
              message: '删除成功！'
            })
            this.reload()
          } else {
            this.$q.notify({
              color: 'red-4',
              textColor: 'white',
              icon: 'cloud_off',
              message: '删除失败！'
            })
          }
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
</style>
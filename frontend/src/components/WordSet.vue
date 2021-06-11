<template>
  <q-card v-ripple class="my-card cursor-pointer q-hoverable" @click="JumptoDetail" v-if="isExist">
    <span class="q-focus-helper"></span>
    <q-card-section class="q-pa-xs row">
        <q-badge color="purple-4" style="margin:1%; padding:1%">{{lockType}}</q-badge>
      <div class="col q-pa-xs" style="display:flex;justify-content:center">
        <div class="text-h6 text-orange" style="margin-left:5%; float:left">{{setName}}</div>
        <div class="text-h6 text-grey" style="margin-left:5%; float:left">by</div>
        <div class="text-h6 text-grey" style="margin-left:5%; float:left">{{setCreator}}</div>
      </div>
      <div class="col-auto q-pa-xs row no-wrap" style="text-align:center">
          <div class="text-brown text-subtitle1" style="float:left">{{cardNum}}</div>
          <div class="text-brown text-subtitle1" style="float:left">个单词</div>
      </div>
    </q-card-section>

    <q-separator horizontal/>

    <q-card-section class="q-pa-xs row" v-if="this.type===1">
      <div class="text-subtitle1 col q-pt-sm q-px-md" style="text-align:left;word-break:break-all">
        <p style="display:inline">单词集简介: </p>
        <p style="display:inline">{{setContent}}</p>
      </div>
      <q-btn 
        flat
        round
        icon="o_star"
        class="col-auto" 
        :style="{ color: isStar ? 'purple' : 'gray' }"           
        @click="updateStar()"
        v-on:click.stop="doThis"
      />
    </q-card-section>

    <q-card-section class="q-pa-xs row" v-if="this.type===0">
      <div class="text-subtitle1 col-10 q-pt-sm q-px-md" style="text-align:left;word-break:break-all">
        <p style="display:inline">单词集简介: </p>
        <p style="display:inline">{{setContent}}</p>
      </div>
      <div class="col-1">
        <q-btn 
          flat
          round
          icon="o_star"
          class="col-auto" 
          :style="{ color: isStar ? 'purple' : 'gray' }"           
          @click="updateStar()"
          v-on:click.stop="doThis"
        />
      </div>
      <div class="col-1">
        <q-btn 
          flat
          round
          icon="o_delete"
          class="col-auto" 
          style="color:purple"   
          @click="delSet()"       
          v-on:click.stop="doThis"
        />
      </div>

      
    </q-card-section>
  </q-card>

</template>

<style scoped>
  .my-card {
     width: 75%;
     text-align: center;
  }
</style>

<script>
import{notStarWordSet,starWordSet}from '../api/wordset'
import{deleteSet}from '../api/wordset'
export default {

  inject: ['reload'],
  name: 'WordSet',
  props: {
    isVisible: {
      type: Boolean,
      required: true
    },
    setName: {
      type: String,
      required: true
    },
    setCreator: {
      type: String,
      required: true
    },
    cardNum: {
      type: Number,
      required: true
    },
    setContent: {
      type: String,
      required: true
    },
    isStar: {
      type: Boolean,
      required: true
    },
    setId: {
      type: Number,
      required: true
    },
    type: {
      type: Number,
      required: true
    }
  },
  data () {
    return {
      user:{},
      lockType:'',
      result:false,
      isExist:true,
    }
  },

  mounted() {
    this.user=JSON.parse(sessionStorage.getItem('user'));
    if(this.user.username==this.setCreator){
      this.lockType='我的'
    } else{
      if(this.isVisible==true){
        this.lockType='公开'
      } else{
        if(this.isStar==true){
          this.lockType='已解锁'
        } else{
          this.lockType='待解锁'
        }
      }
    }   
  },

  methods: {
    updateStar() {
      if(this.isStar == true) {
        notStarWordSet(this.setId,this.user.user_id)
          .then((response)=>{
            this.result=response.data.isSucceed;
            if(this.result == true){
              this.isStar = false
            }           
          })
          .catch((error)=>{
            console.log(error)
        })            
      }
      else {
        starWordSet(this.setId,this.user.user_id)
          .then((response)=>{
            this.result=response.data.isSucceed;
            if(this.result == true){
              this.isStar = true
            }  
          })
          .catch((error)=>{
            console.log(error)
        })
      }
    },

    delSet() {
      deleteSet(this.setId)
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
    },

    JumptoDetail: function () {
      if(this.type===1){
        this.$router.push({
          path: '/WordDetail',
          name: 'WordDetail',
          query: {
            setId: this.setId,
          }
        })
      } else{
        this.$router.push({
          path: '/MyWordDetail',
          name: 'MyWordDetail',
          query: {
            setId: this.setId,
          }
        })
      }     
    }
  }
}
</script>
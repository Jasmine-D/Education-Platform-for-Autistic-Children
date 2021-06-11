<template>
  <div class="bg_img">
    <Header :tab="headerName"/>
    <div style="margin-left:18%;">
      <div style="float:left;width:20%;">
      <Menu :link="link"/>
      </div>
      <div style="float:left;width:80%;">
      <div class="col-9 q-my-lg" style="width:80%; height:fit-content; margin-left:10%; text-align:center">
        <div class="q-pb-lg">
          <q-btn push rounded color="purple-5" icon="backup" class="btn" @click="createWord = true">创建单词卡</q-btn>
        </div>
        <q-dialog v-model="createWord">
          <div class="new-card q-pa-md q-mb-lg shadow-2 rounded-borders bg-yellow-1">
            <transition
                    appear
                    enter-active-class="animated fadeIn"
                    leave-active-class="animated fadeOut"
            >
            <q-form
              @submit="onSubmit"
              @reset="onReset"
              class="q-field q-col-gutter-y-md"
              v-show="showScene1"
              style="min-height:400px;"
            >
              <div style="width:100%; display:flex; justify-content:center">
                <q-input 
                  bottom-slots 
                  v-model="name" 
                  Placeholder="单词卡名称" 
                  counter 
                  maxlength="10" 
                  dense 
                  color="purple"
                  :rules="[val => !!val || '不能为空']"
                >
                  <template v-slot:append>
                    <q-icon v-if="name !== ''" name="close" @click="name = ''" class="cursor-pointer" />
                  </template>
                  <template v-slot:after>
                    <q-btn 
                      dense 
                      flat 
                      rounded
                      stack
                      icon="o_mic" 
                      :label="recordLabel" 
                      :style="{ color: isRecord ? 'purple' : 'gray' }"                               
                      @click="updateMic()"
                    />
                    <q-btn 
                      dense 
                      flat 
                      rounded
                      stack
                      icon="o_play_arrow" 
                      :label="playLabel"  
                      :style="{ color: isPlay ? 'purple' : 'gray' }"           
                      @click="updatePlay()"
                      v-if="isPlayBtn"
                    />
                  </template>
                </q-input>
              </div>

              <div style="width:100%; justify-content:center">
                <q-input
                  v-model="content"
                  rounded
                  outlined
                  type="textarea"
                  Placeholder="单词卡内容" 
                  style="word-break:break-all"
                  counter
                  maxlength="50"
                  color="purple"
                  :rules="[val => !!val || '不能为空']"
                />
              </div>

              <q-file 
                color="purple" 
                filled 
                clearable 
                dense
                v-model="file"  
                accept=".jpg, image/*"
                @rejected="onRejected"
                bottom-slots
                counter
              >
                <template v-slot:prepend>
                  <q-icon name="cloud_upload" />
                </template>
                <template v-slot:hint>
                  上传图片
                </template>
              </q-file>

              <div>
                <q-btn label="提交" type="submit" color="purple"/>
                <q-btn label="重置" type="reset" color="purple" flat class="q-ml-sm" />
              </div>
            </q-form>
            </transition>
              <q-inner-loading :showing="showLoad1">
                  <q-spinner-cube
                      color="orange"
                      size="5.5em"
                  />
                  <div>正在处理数据，请耐心等待~~</div>
              </q-inner-loading>
          </div>
        </q-dialog>
        <div class="q-pb-lg" style="display:flex;justify-content:center" v-for="(cardInfo,index) in cardListInfo" :key="index">
          <WordCard
            :cardId="cardInfo.card_id"
            :cardName="cardInfo.card_name"
            :cardContent="cardInfo.card_content"
            :picUrl="cardInfo.pic_url"
            :audioUrl="cardInfo.audio_url"
            :type=0>
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

  .new-card {
    width: 280px;
    text-align: center;
  }

  .bg_img {
    width: 100%;
    height: 100%;
    position:relative;
    min-height:750px;
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
import WordCard from '@/components/WordCard.vue'
import Recorder from 'js-audio-recorder'
import{searchCard, uploadCard}from '../api/wordcard'

export default {
  inject: ['reload'],

  components: {
    Header,
    Menu,
    WordCard
  },
  data() {
    return {
      headerName:'Account',
      link:'MyWord',
      showScene1:true,
      showLoad1:false,
      createWord: false,
      name: '',
      content: '',
      file: null,
      isRecord: false,
      recordLabel:'开始',
      isPlayBtn: false,
      isPlay: false,
      playLabel: '播放',
      playTime: 0,
      recorder: null,
      timer: null,
      audioFile:null,

      cardListInfo:[],
      curPage: 1,
      pageSize: 5,
      setId: 0,
      totalNum: 0,
      totalPage: 0,
    }
  },

  mounted () {
    this.setId = this.$route.query.setId
    this.showPage()
  },

  watch:{
    curPage(){
      this.showPage();
    }
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
    },

    updateMic() {
      if(this.isRecord == false) {
        this.isRecord = true
        this.recordLabel = '停止'
        //开始录音
        this.recorder = new Recorder()
        Recorder.getPermission().then(() => {
          console.log('开始录音')
          this.recorder.start() // 开始录音
        }, (error) => {
          this.$Message.info('请先允许该网页使用麦克风')
          console.log(`${error.name} : ${error.message}`)
        })
      }
      else {
        this.isRecord = false
        this.recordLabel = '开始'
        this.isPlayBtn = true
        //停止录音
        console.log('停止录音')
        this.recorder.stop() // 停止录音
      }
    },

    updatePlay() {
      if(this.isPlay == false) {
        this.isPlay = true
        this.playLabel = '停止'
        console.log('this.isPlay:'+this.isPlay)
        // 播放录音
        console.log('播放录音')
        this.recorder.play() // 播放录音
        // 播放时长
        this.timer = setInterval(() => {
          try {
            this.playTime = this.recorder.getPlayTime()
            console.log('this.playTime:'+this.playTime)
            if (this.playTime > this.recorder.duration || this.isPlay == false) {
              this.isPlay = false;
              this.playLabel = '播放'
              console.log('停止播放')
              this.recorder.stopPlay() // 停止播放
              console.log('this.isPlay:'+this.isPlay)
              clearInterval(this.timer)
            }
          } catch (error) {
            this.timer = null
          }
        }, 100)
      }
      else {
        this.isPlay = false;
        this.playLabel = '播放'
        // 停止播放
        console.log('停止播放')
        this.recorder.stopPlay() // 停止播放

        // 播放时长
        this.playTime = this.recorder.getPlayTime()
        clearInterval(this.timer)
      
      }
    },

    onSubmit () {
      if (this.recorder == null || this.recorder.duration === 0) {
        this.$q.notify({
            type: 'negative',
            message: `请先录音！`,
            position: 'top'
        });
        return;
      }
      this.recorder.pause() // 暂停录音
      this.timer = null
      console.log('上传录音')// 上传录音
      var blob = this.recorder.getWAVBlob()//获取wav格式音频数据
	  //此处获取到blob对象后需要设置fileName满足当前项目上传需求，其它项目可直接传把blob作为file塞入formData
      var newbolb = new Blob([blob], { type: 'audio/wav' })
      this.audioFile = new File([newbolb], new Date().getTime() + '.wav')
      this.showScene1=false;
      this.showLoad1=true;
      let fd=new  FormData();
      fd.append('audiofile',this.audioFile);
      fd.append('cardContent',this.content);
      fd.append('cardName',this.name);
      fd.append('picfile',this.file);
      fd.append('setId',this.setId);
      uploadCard(fd)
        .then((response)=>{
          console.log(response);
          this.result=response.data.isSucceed;
          if(this.result===true){
            this.showScene1=true;
            this.showLoad1=false;
            this.createWord=false
            this.reload();
            this.$q.notify({
              color: 'green-4',
              textColor: 'white',
              icon: 'cloud_done',
              message: '提交成功！',
              position: 'top'
            })
            
          } else {           
              this.$q.notify({
                color: 'red-4',
                textColor: 'white',
                icon: 'cloud_off',
                message: '提交失败！',
                position: 'top'
              })       
          }
        })
        .catch((error)=>{
          console.log(error)
      })
    },

    onReset () {
      this.name = ''
      this.content = ''
    },

    onRejected (rejectedEntries) {
      // Notify plugin needs to be installed
      // https://quasar.dev/quasar-plugins/notify#Installation
      this.$q.notify({
        type: 'negative',
        message: `${rejectedEntries.length} 文件格式错误，上传失败！`
      })
    }
  }
}
</script>
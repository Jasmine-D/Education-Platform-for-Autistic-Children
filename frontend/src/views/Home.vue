<template>
  <div>
    <Header :tab="headerName"/>
    <div class="q-pa-xs" style="position:relative">
      <q-carousel
        animated
        v-model="slide"
        navigation
        infinite
        :autoplay="autoplay"
        arrows
        transition-prev="slide-right"
        transition-next="slide-left"
        @mouseenter="autoplay = false"
        @mouseleave="autoplay = true"
        height="800px"
      >
        <q-carousel-slide :name="1" :img-src="home1">
          <div class="custom-caption" style="width: 46%; top:60%; left:5%">
            <div class="text-h2">基于场景的交流学习</div>
            <div class="text-subtitle1" style="text-align: left">在特定场景中学习词语，在交流中练习发音，动画与图片结合，增加趣味性。</div>
          </div>
        </q-carousel-slide>
        <q-carousel-slide :name="2" :img-src="home2">
          <div class="custom-caption" style="width: 45%; top:70%; left:50%">
            <div class="text-h2">单词集的针对性学习</div>
            <div class="text-subtitle1" style="text-align: left">传统单词卡学习词语，包含音频、例句、图片等，多方位丰富词语的含义，便于理解和巩固。</div>
          </div>
        </q-carousel-slide>
        <q-carousel-slide :name="3" :img-src="home3">
          <div class="custom-caption" style="width: 55%; top:70%; left:25%">
            <div class="text-h2">DIY:自定义场景和单词集</div>
            <div class="text-subtitle1" style="text-align: left">根据不同用户的需求，可以DIY制作场景和单词集，只需填写必要内容，即可一键生成。用户还可选择发布到平台，所有用户都可以搜索使用。</div>
          </div>
        </q-carousel-slide>
        <q-carousel-slide :name="4" :img-src="home4">
          <div class="custom-caption" style="width: 46%; top:60%; left:5%">
            <div class="text-h2">来自星星的孩子</div>
            <div class="text-subtitle1" style="text-align: left">一切为了能更好地辅助老师教学，如有任何建议或者反馈，请及时联系我们！</div>
          </div>
        </q-carousel-slide>
      </q-carousel>
    </div>
  </div>
</template>

<script>
import Header from '@/components/Header.vue'
import cookie from 'js-cookie'
export default {
  components: { 
    Header,
  },
  data () {
    return {
      headerName:'Home',
      slide: 1,
      autoplay: true,
      home1: require("../assets/home1.jpg"),
      home2: require("../assets/home2.jpg"),
      home3: require("../assets/home3.png"),
      home4: require("../assets/home4.jpg")
    }
  },
  created(){
    console.log(unescape(cookie.get('username')));
    console.log(cookie.get('user_id'));
  }
}
</script>

<style  scoped>
.custom-caption {
  padding: 12px;
  color: white;
  background-color: rgba(0, 0, 0, .6);
  position: absolute;
  text-align: center;
}
 
</style>

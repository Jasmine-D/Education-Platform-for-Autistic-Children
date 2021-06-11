<template>  
  <q-card class="my-card text-black bg-purple-3 q-pa-md q-my-xl q-gutter-xs col-3" :style="user.role==0?'text-align:center; height:450px':'text-align:center; height:400px'">     
    <q-avatar size="100px">
      <img :src="user.avatar"/>
    </q-avatar>
    <div class="text-subtitle2">{{user.username}}</div>
    <q-list bordered padding class="rounded-borders">
      <q-item
        clickable
        v-ripple
        :active="link === 'Account'"
        @click="toAccount()"
        active-class="my-menu-link"
      >
        <q-item-section avatar class="q-item__section--avatar">
          <q-icon name="o_settings" />
        </q-item-section>

        <q-item-section>账号设置</q-item-section>
      </q-item>

      <q-item
        clickable
        v-ripple
        :active="link === 'Report'"
        @click="toReport()"
        active-class="my-menu-link"
      >
        <q-item-section avatar class="q-item__section--avatar q-item__section--side">
          <q-icon name="o_description" />
        </q-item-section>

        <q-item-section>学习报告</q-item-section>
      </q-item>

      <q-item
        clickable
        v-ripple
        :active="link === 'MyCollection'"
        @click="toMyCollection()"
        active-class="my-menu-link"
      >
        <q-item-section avatar class="q-item__section--avatar q-item__section--side">
          <q-icon name="o_star_outline" />
        </q-item-section>

        <q-item-section >我的收藏</q-item-section>
      </q-item>

      <q-item
        clickable
        v-ripple
        :active="link === 'MyScene'"
        @click="toMyScene()"
        active-class="my-menu-link"
        v-if="user.role==0"
      >
        <q-item-section avatar class="q-item__section--avatar q-item__section--side">
          <q-icon name="o_face_retouching_natural" />
        </q-item-section>

        <q-item-section >我的场景</q-item-section>
      </q-item>

      <q-item
        clickable
        v-ripple
        :active="link === 'MyWord'"
        @click="toMyWord()"
        active-class="my-menu-link"
      >
        <q-item-section avatar class="q-item__section--avatar q-item__section--side">
          <q-icon name="o_dns" />
        </q-item-section>

        <q-item-section>我的单词集</q-item-section>
      </q-item>
    </q-list>

  </q-card>

</template>

<style scoped>
.my-card {
  width: 100%;
  max-width: 250px;
}
.my-menu-link {
  color: white;
  background: #F2C037;
}
.q-item__section--avatar {
  min-width: 30px;
}
.q-item__section--side {
  padding-right: 2px;
}
</style>

<script>
export default {
  name:'Menu',
  props:{
    link:{
      type:String,
      default:null
    }
  },
  data () {
    return {
      //link: 'inbox',
      user:''
    }
  },
  methods:{
    toAccount(){
      this.$router.push('/Account');
    },
    toMyCollection(){

      this.$router.push('/MyCollection');
    },
    toReport(){
      this.$router.push('/Record');
    },
    toMyScene(){
      this.$router.push('/MyScene');
    },
    toMyWord(){
      this.$router.push('/MyWordList')
    }
  },
  created(){
    this.user=JSON.parse(sessionStorage.getItem('user'));
  }
}
</script>
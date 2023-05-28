<template>
  <div>
    <link rel="stylesheet" href="https://g.alicdn.com/de/prismplayer/2.8.1/skins/default/aliplayer-min.css">
    <script charset="utf-8" type="text/javascript" src="https://g.alicdn.com/de/prismplayer/2.8.1/aliplayer-min.js"/>
    <div id="J_prismPlayer" class="prism-player"/>
  </div>
</template>

<script>
import vod from '@/api/vod'

export default {
  layout: 'c_video',
  data() {
    return {
      vid: "",
      playAuth: ""
    }
  },
  created() {
  },
  /**
   * 页面渲染完成时：此时js脚本已加载，Aliplayer已定义，可以使用
   * 如果在created生命周期函数中使用，Aliplayer is not defined错误
   */
  mounted() {
    this.vid = this.$route.params.vid;
    vod.getPlayAuth(this.vid).then(response => {
      this.playAuth = response.data.data.playAuth;
    }).then(() => {
      new Aliplayer({
        id: 'J_prismPlayer',
        vid: this.vid, // 视频id
        playauth: this.playAuth, // 播放凭证
        encryptType: '1', // 如果播放加密视频，则需设置encryptType=1，非加密视频无需设置此项
        width: '100%',
        height: '500px',
        autoplay: true,
        preload: true,
        controlBarVisibility: "hover",
        useH5Prism: true
      }, function (player) {
        console.log('播放器创建成功')
      })
    })
  },
}
</script>

<style scoped>

</style>


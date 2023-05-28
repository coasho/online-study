<template>
  <div class="cart py-container">
    <!--主内容-->
    <div class="checkout py-container  pay">
      <div class="checkout-tit">
        <h4 class="fl tit-txt"><span class="success-icon"></span>
          <span v-if="isGetCode" class="success-info">订单提交成功，请您及时付款！订单号：{{ payObj.out_trade_no }}</span>
        </h4>
        <span v-if="isGetCode" class="fr"><em class="sui-lead">应付金额：</em><em class="orange money">￥{{ payObj.total_fee }}</em></span>
        <div class="clearfix"></div>
      </div>
      <div class="checkout-steps">
        <div class="fl weixin">微信支付</div>
        <div class="fl sao">
          <span v-if="!isGetCode">正在加载微信二维码...</span>
          <p v-else class="red">请使用微信扫一扫。</p>
          <div v-if="isGetCode" class="fl code">
            <qriously  :value="payObj.code_url" :size="338"/>
            <div class="saosao">
              <p>请使用微信扫一扫</p>
              <p>扫描二维码支付</p>
            </div>
          </div>
        </div>
        <div class="clearfix"></div>
        <!-- <p><a href="pay.html" target="_blank">> 其他支付方式</a></p> -->

      </div>
    </div>
  </div>
</template>

<script>
import orderApi from '@/api/order'

export default {
  data() {
    return {
      payObj: null,
      timer1: null,  // 定时器名称
      isGetCode: false
    }
  },
  created() {
    //根据订单id生成微信支付二维码
    orderApi.createNative(this.$route.params.oid, 1).then(response => {
      this.payObj = response.data.data
      this.isGetCode = true
      console.log("*****************************")
    })
  },
  mounted() {
    //在页面渲染之后执行
    //每隔三秒，去查询一次支付状态
    console.log("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
    if (this.timer1 == null) {
      this.timer1 = setInterval(() => {
        if (this.payObj != undefined) {
          this.getPayStatus(this.payObj.out_trade_no)
        }
      }, 3000);
    }
  },
  methods: {
    //查询支付状态的方法
    getPayStatus(out_trade_no) {
      orderApi.queryPayStatus(out_trade_no).then(response => {
        if (response != undefined && response.data.success) {
          //如果支付成功，清除定时器
          clearInterval(this.timer1)
          this.$message({
            type: 'success',
            message: '支付成功!'
          })
          //跳转到课程详情页面观看视频
          this.$router.push({path: '/course/' + this.payObj.course_id})
        }
      })
    }
  }
}
</script>

<style scoped>

</style>

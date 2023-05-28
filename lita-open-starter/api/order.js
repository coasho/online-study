import request from '@/utils/request'

export default {

  //1、创建订单
  createOrder(courseId) {
    return request({
      url: `/serviceorder/order/createOrder/${courseId}`,
      method: 'post'
    })
  },
  //2、根据id获取订单
  getById(orderId) {
    return request({
      url: `/serviceorder/order/getOrder/${orderId}`,
      method: 'get'
    })
  },
  //3、生成微信支付二维码
  createNative(orderId,payType) {
    return request({
      url: `/serviceorder/pay-log/createNative/${orderId}/${payType}`,
      method: 'get'
    })
  },
  //4、根据id获取订单支付状态
  queryPayStatus(out_trade_no) {
    return request({
      url: `/serviceorder/pay-log/queryPayStatus/${out_trade_no}`,
      method: 'get'
    })
  }
}

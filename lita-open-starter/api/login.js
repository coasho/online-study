import request from '@/utils/request'

export default {
  //登录
  submitLogin(userInfo) {
    return request({
      url: `/eduucenter/login`,
      method: 'post',
      data: userInfo
    })
  },
  //微信扫码登录
  getWXLoginURL() {
    return request({
      url: `/eduucenter/login`,
      method: 'get',
    })
  },
  //根据token获取用户信息
  getLoginInfo() {
    return request({
      url: `/eduucenter/getLoginInfo`,
      method: 'get',
    })
  }
}

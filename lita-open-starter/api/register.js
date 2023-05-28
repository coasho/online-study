import request from '@/utils/request'
export default {
  //根据手机号码发送短信
  getMobile(mobile) {
    return request({
      url: `/edumsm/send/${mobile}`,
      method: 'get'
    })
  },
  //用户注册
  submitRegister(formItem) {
    return request({
      url: `/eduucenter/register`,
      method: 'post',
      data: formItem
    })
  }
}

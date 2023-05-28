import request from '@/utils/request'
export default {
  getList() {
    return request({
      url: `/serviceedu/front/index`,
      method: 'get'
    })
  }
}

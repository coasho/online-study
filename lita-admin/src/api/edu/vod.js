import request from '@/utils/request'

export default {
  removeById(videoId) {
    return request({
      url: `/eduvod/video/delete/${videoId}`,
      method: 'delete'
    })
  }
}

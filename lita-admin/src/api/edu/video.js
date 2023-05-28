import request from "@/utils/request";
export default {
  addVideo(videoInfo) {
    return request({
      url: `/serviceedu/edu-video/addVideo`,
      method: 'post',
      data: videoInfo
    })
  },
  updateVideo(videoInfo) {
    return request({
      url: `/serviceedu/edu-video/updateVideo`,
      method: 'post',
      data: videoInfo
    })
  },
  deleteVideoById(videoId) {
    return request({
      url: `/serviceedu/edu-video/deleteVideo/${videoId}`,
      method: 'delete'
    })
  },

}

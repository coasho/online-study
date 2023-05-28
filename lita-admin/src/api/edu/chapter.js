import request from '@/utils/request'

export default {
  getChapterList(courseId) {
    return request({
      url: `/serviceedu/edu-chapter/getChapter/${courseId}`,
      method: 'get'
    })
  },
  addChapter(chapterInfo) {
    return request({
      url: `/serviceedu/edu-chapter/addChapter`,
      method: 'post',
      data: chapterInfo
    })
  }, updateChapter(chapterInfo) {
    return request({
      url: `/serviceedu/edu-chapter/updateChapter`,
      method: 'post',
      data: chapterInfo
    })
  }, deleteChapterById(chapterId) {
    return request({
      url: `/serviceedu/edu-chapter/deleteChapter/${chapterId}`,
      method: 'delete'
    })
  },
}

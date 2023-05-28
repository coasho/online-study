import request from '@/utils/request'

export default {
  saveCourseInfo(courseInfo) {
    return request({
      url: `/serviceedu/edu-course/addCourseInfo`,
      method: 'post',
      data: courseInfo
    })
  },
  getCourseInfo(courseId) {
    return request({
      url: `/serviceedu/edu-course/getCourseInfo/${courseId}`,
      method: 'get'
    })
  },
  updateCourseInfo(courseInfo) {
    return request({
      url: `/serviceedu/edu-course/updateCourseInfo`,
      method: 'post',
      data: courseInfo
    })
  },
  getCoursePublish(courseId) {
    return request({
      url: `/serviceedu/edu-course/course-publish-info/${courseId}`,
      method: 'get'
    })
  },
  publishCourse(courseId) {
    return request({
      url: `/serviceedu/edu-course/publish-course/${courseId}`,
      method: 'put'
    })
  },
  getCourseListByCondition(current, limit, courseInfo) {
    return request({
      url: `/serviceedu/edu-course/getCourseListByCondition/${current}/${limit}`,
      method: 'post',
      data: courseInfo
    })
  },
  deleteCourseById(courseId) {
    return request({
      url: `/serviceedu/edu-course/deleteCourseById/${courseId}`,
      method: 'delete'
    })
  },
}

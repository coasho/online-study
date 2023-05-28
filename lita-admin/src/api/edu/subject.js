import request from '@/utils/request'
const api_name = '/admin/edu/subject'
export default {
  getSubjectList() {
    return request({
      url: `/serviceedu/edu-subject/getSubjectList`,
      method: 'get'
    })
  }
}

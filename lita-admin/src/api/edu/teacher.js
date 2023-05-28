import request from '@/utils/request'


export default {
  //分页查询讲师（条件查询）
  getListPageCondition(current, limit, teacherQuery) {
    return request({
      url: `/serviceedu/eduteacher/findCondition/${current}/${limit}`,
      method: 'post',
      data: teacherQuery
    })
  },
  removeById(id) {
    return request({
      url: `/serviceedu/eduteacher/delete/${id}`,
      method: 'delete'
    })
  },

  save(teacher) {
    return request({
      url: '/serviceedu/eduteacher/add',
      method: 'post',
      data: teacher
    })
  },
  getById(id) {
    return request({
      url: `/serviceedu/eduteacher/findbyid/${id}`,
      method: 'get'
    })
  },
  updateById(id, teacher) {
    return request({
      url: `/serviceedu/eduteacher/updatebyid/${id}`,
      method: 'put',
      data: teacher
    })
  },
  getAll() {
    return request({
      url: `/serviceedu/eduteacher/findAll`,
      method: 'get'
    })
  }

}

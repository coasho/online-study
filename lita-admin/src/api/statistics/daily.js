import request from '@/utils/request'
const api_name = '/servicestatistics/'
export default {
  createStatistics(day) {
    return request({
      url: `${api_name}/statistics-daily/${day}`,
      method: 'post'
    })
  },
  showChart(searchObj) {
    return request({
      url: `${api_name}/show-chart/${searchObj.begin}/${searchObj.end}/${searchObj.type}`,
      method: 'get'
    })
  }
}

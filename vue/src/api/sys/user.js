import request from "../../utils/request";

export function pageList(query){
  return request({
    url: '/system/user/pageList',
    method: 'get',
    params: query
  })
}

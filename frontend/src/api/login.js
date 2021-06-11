import request from '@/utils/request'

export function login (logInfo) {
  return request({
    url: 'User/Login',
    method: 'get',
    params: logInfo
  })
}
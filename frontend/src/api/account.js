import request from '@/utils/request'
export function getUserInfo (info) {
    return request({
      url: 'User/GetUserInfo',
      method: 'get',
      params: info
    });
  }

export function modifyAvatar(info){
    return request({
        url:'User/ModifyAvatar',
        method:'post',
        data:info
    })
}

export function modifyPassword(info){
  return request({
    url:'User/ModifyPassword',
    method:'post',
    data:info
  })  
}
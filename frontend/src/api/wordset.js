import request from '@/utils/request'

export function searchSet (userId, setName, pageNum, curPage) {
  return request({
    url: 'wordSet/SearchByName?curPage=' + curPage + '&pageNum=' + pageNum + '&setName=' + setName + '&userId=' + userId,
    method: 'get'
  })
}

export function notStarWordSet (setId,userId) {
  return request({
    url: 'wordSet/NotStarWordSet?setId=' + setId + '&userId=' + userId,
    method: 'delete'
  })
}

export function starWordSet (setId,userId) {
  return request({
    url: 'wordSet/StarWordSet?setId=' + setId + '&userId=' + userId,
    method: 'post'
  })
}

export function getAllSet (curPage,pageNum,userId) {
  return request({
    url: 'wordSet/GetAllSet?curPage=' + curPage + '&pageNum=' + pageNum + '&userId=' + userId,
    method: 'get'
  })
}

export function mySet (curPage,pageNum,userId) {
  return request({
    url: 'myWordSet/MyWordSet?curPage=' + curPage + '&pageNum=' + pageNum + '&userId=' + userId,
    method: 'get'
  })
}

export function myStarSet (curPage,pageNum,userId) {
  return request({
    url: 'myWordSet/MyStarWordSet?curPage=' + curPage + '&pageNum=' + pageNum + '&userId=' + userId,
    method: 'get'
  })
}

export function uploadSet (fd) {
  return request({
    url: 'myWordSet/UploadWordSet',
    method: 'post',
    data:fd
  })
}

export function deleteSet (setId) {
  return request({
    url: 'myWordSet/DeleteWordSet?setId=' + setId,
    method: 'delete'
  })
}
import request from '@/utils/request'

export function searchCard (curPage, pageNum, setId) {
  return request({
    url: 'wordCard/SearchBySetId?curPage=' + curPage + '&pageNum=' + pageNum + '&setId=' + setId,
    method: 'get'
  })
}

export function uploadCard (fd) {
  return request({
    url: 'wordCard/UploadWordCard',
    method: 'post',
    data:fd
  })
}

export function deleteCard (cardId) {
  return request({
    url: 'wordCard/DeleteWordCard?cardId=' + cardId,
    method: 'delete'
  })
}

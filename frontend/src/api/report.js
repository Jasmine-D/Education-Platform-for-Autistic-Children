import request from '@/utils/request'

export function getSomeReport(info){
    return request({
        url:'Report/getSomeReport',
        method:'get',
        params: info
    });
}

export function createReport(info){
    return request({
        url:'Report/createReport',
        method:'post',
        data:info
    });
}

export function modifyReport(info){
    return request({
        url:'Report/modifyReport',
        method:'post',
        data:info,
    })
}

export function getAllReport(user_id){
    return request({
        url:'Report/getAllReport',
        method:'get',
        params: user_id
    });
}
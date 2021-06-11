import request from '@/utils/request'

export function checkUsername(username){
    return request({
        url: 'User/CheckName',
        method: 'get',
        params: username
    });
}

export function checkPhoneNumber(phone){
    return request({
        url: 'User/CheckPhone',
        method: 'get',
        params: phone
    });
}

export function register(userInfo){
    return request({
        url: 'User/Register',
        method: 'get',
        params: userInfo
    });
}
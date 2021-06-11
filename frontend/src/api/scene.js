import request from '@/utils/request'

export function getAllScene(pageInfo){
    return request({
        url: 'Scene/getAllScene',
        method: 'get',
        params: pageInfo
    });
}

export function collectOrCancel(info){
    return request({
        url:"Scene/collectOrCancel",
        method:'get',
        params:info
    });
}

export function searchScene(info){
    return request({
        url:"Scene/searchScene",
        method:'get',
        params:info
    });
}

export function getScenePic(sceneInfo){
    return request({
        url:"Scene/getScenePic",
        method:'get',
        params:sceneInfo
    });
}

export function getSceneInformation(info){
    return request({
        url:"Scene/getSceneInfo",
        method:'get',
        params:info
    });
}

export function getSceneQuestion(sceneInfo){
    return request({
        url:"Scene/getSceneQuestion",
        method:'get',
        params:sceneInfo
    });
}

export function getCollectScene(pageInfo){
    return request({
        url:"Scene/getCollectScene",
        method:'get',
        params:pageInfo
    });
}

export function getCreatedScene(pageInfo){
    return request({
        url:"Scene/getCreatedScene",
        method:'get',
        params:pageInfo
    });
}

export function deleteScene(info){
    return request({
        url:"Scene/deleteScene",
        method:'post',
        data:info
    });
}

export function setPublic(info){
    return request({
        url:"Scene/setPublic",
        method:'post',
        data:info
    });
}

export function setPassword(info){
    return request({
        url:"Scene/setPassword",
        method:'post',
        data:info
    });
}

export function createScene(sceneInfo){
    return request({
        url:"Scene/createScene",
        method:'post',
        data:sceneInfo
    });
}
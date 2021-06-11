import axios from 'axios'
import qs from 'qs'
import cookie from 'js-cookie'

// 创建axios实例
const service = axios.create({
    baseURL: 'http://localhost:8080', // base_url
    timeout: 20000 // 请求超时时间
});

var isLoading = 0
const CancelToken = axios.CancelToken;
var cancel;
var cancelToken = new CancelToken(
  function executor (c) {
    cancel = c;
});

// 第三步 创建拦截器  http request 拦截器
service.interceptors.request.use(
    config => {
      // debugger
      // 判断cookie里面是否有名称是 token数据
      if (cookie.get('token')) {
        // 把获取cookie值放到header里面
        // headers: {
        //  Authorization: 'Bearer' + ' ' + cookie.get('token')
        // }
        config.headers.Authorization = 'Bearer' + ' ' + cookie.get('token')
      }
      config.paramsSerializer = params => {
        return qs.stringify(params, { indices: false })
      }
      config.cancelToken = cancelToken
      isLoading++
      return config
    },
    err => {
      /// console.log('oh!!!!')
      return Promise.reject(err)
    });
  
// http response 拦截器
service.interceptors.response.use(
    // 这将会在之后统一进行配置
    response => {
      if (response.data.data && response.data.data.token) {
        cookie.set('token', response.data.data.token)
      }
      isLoading--
      return response.data

    },
    error => {
      isLoading--
      if (error.response.status === 401) {
        // console.log('no!!!!')
        // 未登录或token过期
        if (cookie.get('token')) {
            cookie.remove('cookie')
            this.$q.notify({
                type: 'negative',
                message: `您需要重新登录！`,
                position: 'top'
            })
            this.$router.push("/Login");

         
        } else {
            this.$q.notify({
                type: 'negative',
                message: `您还未登录，需要去登录！`,
                position: 'top'
              })
            this.$router.push("/Login");
            
        }
      }
      return Promise.reject(error.response)
      // 返回接口返回的错误信息
    })
  
  // 取消所有请求
export function RequestCancel () {
    if (isLoading > 0) {
      cancel('cancel')
      cancelToken = new CancelToken(function executor (c) {
        cancel = c
      })
    }
}
  
export default service
  


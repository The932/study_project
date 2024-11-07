//定制请求的实例

//导入axios  npm install axios
import axios from 'axios';

import { ElMessage } from 'element-plus'
//定义一个变量,记录公共的前缀  ,  baseURL
const baseURL = '/api';
const instance = axios.create({baseURL})

import { useTokenStore } from '@/stores/token';
//添加请求拦截器
instance.interceptors.request.use(
    config=>{
        //请求成功回调
        //添加token
        const tokenStore = useTokenStore();
        //判断有没有token
        if(tokenStore.token){
            config.headers.Authorization = tokenStore.token;
        }
        return config;
    },
    err=>{
        //请求错误回调
        Promuse.reject(err);
    }
)

// import { useRoute } from 'vue-router';
// const route = useRoute();
import router from '@/router';

//添加响应拦截器
instance.interceptors.response.use(
    result=>{
        //判断业务状态码
        if(result.data.code === 0){
            return result.data;
        }
        ElMessage.error(result.data.msg?result.data.msg:'服务异常');
        return Promise.reject(result.data);
    },
    err=>{
        //判断响应的状态码，如果是401，跳转到登录页面
        if(err.response.status === 401){
            ElMessage.error('请先登录');
            //跳转到登录页面
            router.push('/login');
        }else{
            ElMessage.error('服务异常');
        }
    }
)

export default instance;
import request from "@/utils/request";


// 登录接口
export function login(query){
    return request({
        url: '/users/login',
        method: 'post',
        data: query
    })
}
// 注册接口
export function register(query){
    return request({
        url: '/users/register',
        method: 'post',
        data: query
    })
}

// 退出登录接口
export function logout(){
    return request({
        url:'users/logout',
        method:'post',
        headers:{
            isToken:true
        }
    })
}

// 获取用户信息
export function getUserInfoById(id){
    return request({
        url:`/users/userInfo/${id}`,
        method:'get',
        headers:{
            isToken:true
        }
    })
}

// 更新用户信息
export function updateUserInfo(query){
    return request({
        url:`/users/update`,
        method:'post',
        headers:{
            isToken:true
        },
        data:query
    })
}

// 获取在线用户
export function getOnlineUser(studioId){
    return request({
        url:`/myServer/online/${studioId}`,
        method:'get',
        headers:{
            isToken:true
        }
    })
}




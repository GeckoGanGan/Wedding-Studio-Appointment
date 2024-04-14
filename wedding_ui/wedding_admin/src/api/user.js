import request from "@/utils/request";



// 登录接口
export function login(query){
    return request({
        url: '/users/login',
        method: 'post',
        data: query,
        isToken: false
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

// 获取全部用户信息
export function getAllUsers(queryParam){
    return request({
        url: '/users/page',
        method: 'post',
        data: queryParam,
        isToken: true
    })
}
import request from "@/utils/request";


// 发送验证码接口
export function sendCode(sms){
    return request({
        url:`/sms/sendMsg`,
        method:'post',
        headers:{
            isToken:false
        },
        data:sms
    })
}
// 验证码登录

export function smsLogin(sms){
    return request({
        url:`/sms/login`,
        method:'post',
        data:sms
    })
}

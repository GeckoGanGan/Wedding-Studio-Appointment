import request from "@/utils/request";



// 邮件发送接口
export function contact(query) {
    return request({
        url: '/email/contact',
        method: 'post',
        headers: {
          isToken:false
        },
        data: query
    })
}

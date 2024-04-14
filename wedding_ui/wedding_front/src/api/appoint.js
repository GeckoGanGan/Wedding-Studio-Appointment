import request from '@/utils/request'


// 查询预约信息
export function getAppointList(query){
    return request({
        url:'/appointments/page',
        method:'post',
        headers:{
            isToken:true
        },
        data:query
    })
}
// 取消预约
export function cancelAppoint(id){
    return request({
        url:`/appointments/cancel/${id}`,
        method:'delete',
        headers:{
            isToken:true
        },
    })
}

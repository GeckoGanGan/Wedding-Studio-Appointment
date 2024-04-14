import request from "@/utils/request";

// 获取影楼套餐信息
export function getStudioPackageInfo(id){
    return request({
        url: `/package/${id}`,
        method: 'get',
        data:id
    })
}

// 预约影楼套餐
export function addStudioPackageOrder(data){
    return request({
        url: `/appointments/appoint`,
        method: 'post',
        headers: {
            isToken:true
        },
        data:data
    })
}

// 用户查询个人预约信息
export function getUserAppointmentInfo(){

    return request({
        url: `/appointments/list`,
        method: 'get',
        headers: {
            isToken:true
        },
    })
}

// 用户取消影楼套餐预约
export function cancelStudioPackageOrder(id){
    return request({
        url: `/appointments/cancel/${id}`,
        method: 'get',
        headers: {
            isToken:true
        },
    })
}

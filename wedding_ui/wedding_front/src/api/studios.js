import request from "@/utils/request";


// 通过id查询银影楼详情
export function getStudioDetailsById(query){
    return request({
        method: 'get',
        url: `/studios/studioDetails/${query}`,
    })
}

// 分页查询影楼详情

export function getStudioDetailsByPage(query){
    return request({
        method: 'post',
        url: `/studios/page`,
        data: query,
        headers:{
            isToken:false
        }
    })
}
// 点赞

export function collect(param) {
    return request({
        url: `/studios/collect`,
        method: "post",
        headers:{
            isToken:true,
        },
        data:param
    });

}

// 点赞
export function view(id) {
    return request({
        url: `/studios/view/${id}`,
        method: "get",
        headers:{
            isToken:true,
        }
    });
}

// 查询热榜排行
export function getHotStudio(type){
    return request({
        url: `/studios/hot/${type}`,
        method: "get",
        headers:{
            isToken:false,
        }
    });
}

// 查询个人收藏
export function getCollect(param){
    return request({
        url: `/studios/getCollectByUserId`,
        method: "post",
        headers:{
            isToken:true,
        },
        data:param
    });
}


//取消个人收藏
export function cancelCollect(collectId){
    return request({
        url: `/studios/collect/cancel`,
        method: "post",
        headers:{
            isToken:true,
        },
        params:{
            collectId:collectId
        }
    });
}

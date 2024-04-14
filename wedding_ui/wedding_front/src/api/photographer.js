import request from "@/utils/request";

// 查询摄影师列表
export function listPhotographer() {
    return request({
        url: "/photographers/list",
        method: "get",
        headers:{
          isToken:false,
        },
    });
}
// 查询点赞量
export function getPhotographerLike() {
    return request({
        url: "/photographers/getLike",
        method: "get",
        headers:{
            isToken:false,
        }
    });
}

// 点赞

export function likePhotographer(id) {
    return request({
        url: `/photographers/like/${id}`,
        method: "get",
        headers:{
            isToken:true,
        }
    });
}



// 查询摄影师个人信息
export function getPhotographerInfo(id) {
    return request({
        url: `/photographers/info`,
        method: "get",
        headers:{
            isToken:false,
        },
        params:{
            id:id
        }
    });
}

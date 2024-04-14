import request from '@/utils/request'

// 发送文章评论
export function sendComment(type,studioId,rootId,toCommentId,toCommentUserId,content) {
    return request({
        url: '/comment/add',
        method: 'post',
        data: {"studioId":studioId,"type":type,"rootId":rootId,"toCommentId":toCommentId,"toCommentUserId":toCommentUserId,"content":content}
    })
}


export function getStudioComment(query) {
    return request({
        url: '/comment/commentList',
        method: 'get',
        headers: {
          isToken: false
        },
        params: query
    })
}


export function getLinkComment(query) {
    return request({
        url: '/comment/linkCommentList',
        method: 'get',
        params: query
    })
}

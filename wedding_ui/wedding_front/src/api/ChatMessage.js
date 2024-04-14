import request from "@/utils/request";


// 查询历史聊天记录
export function getChatHistory(data) {
    return request({
        url: "/chat/history",
        method: "post",
        headers: {
            isToken:true
        },
        data: data
    });
}

package com.wedding.domain.vo;


import lombok.Data;

/**
 * @author zhudake
 * @date 2018/11/14
 * @description 返回结果包装类
 */
@Data
public class ResultVO<T> {
    // 返回的code
    private Integer resCode;
    // 返回的数据
    private T resData;
    // 返回的消息提示
    private String resMsg;

    public ResultVO() {
        this.resCode = 200;
        this.resData = null;
        this.resMsg = "操作成功";
    }

    public ResultVO(Integer resCode, T resData, String resMsg) {
        if (resCode == null) {
            this.resCode = 200;
        } else {
            this.resCode = resCode;
        }
        this.resData = resData;
        if (resMsg == null) {
            this.resMsg = "操作成功";
        } else {
            this.resMsg = resMsg;
        }
    }
}

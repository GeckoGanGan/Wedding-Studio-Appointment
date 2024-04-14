package com.wedding.domain.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuqi
 * @Date: 2024/03/16/20:48
 * @Description:
 */
@Getter
@Setter
public class CommentCobyVo {
    private Long id;
    private Date createTime;
    private String content;
    private Long parentId;

    private Long uid;
    private CommentUserVo user;

    private Reply reply;

}

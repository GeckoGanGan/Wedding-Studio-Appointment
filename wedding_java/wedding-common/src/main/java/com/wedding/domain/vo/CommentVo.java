package com.wedding.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVo implements Serializable {
    private Long id;

    //文章id
    private Long studioId;
    //根评论id
    private Long rootId;
    //评论内容
    private String content;
    //所回复的目标评论的userid
    private Long toCommentUserId;
    //回复目标评论id
    private String toCommentUserName;

    private Long toCommentId;
    private Long createdBy;

    private Date createdTime;

    private String username;
    //子评论
    private List<CommentVo> children;
    // 头像
    private String avatar;
}

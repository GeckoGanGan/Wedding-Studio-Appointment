package com.wedding.domain.entity;

import java.util.Date;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 评价表(Reviews)表实体类
 *
 * @author zhudake
 * @since 2024-03-01 19:52:24
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("reviews")
public class Reviews implements Serializable {
    //主键唯一标识
    @TableId(type = IdType.AUTO)
    private Long id;

    //类型
    private String type;
    //所评论对象id
    private Long entityId;
    //根评论id
    private Long rootId;
    //评论内容
    private String content;
    //所回复目标评论的userid
    private Long toCommentUserId;
    //回复目标评论id
    private Long toCommentId;
    //评分（1-5）
    private Integer rating;
    //创建人
    @TableField(fill = FieldFill.INSERT)
    private Long createdBy;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;
    //更新人
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updatedBy;
    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedTime;
    //备注
    private String remark;
    //逻辑删除
    private Integer delFlag;

}


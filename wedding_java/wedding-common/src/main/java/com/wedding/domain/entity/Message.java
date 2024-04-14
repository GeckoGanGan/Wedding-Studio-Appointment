package com.wedding.domain.entity;

import java.util.Date;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 留言表(Message)表实体类
 *
 * @author zhudake
 * @since 2024-03-24 18:58:56
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("message")
public class Message implements Serializable  {

    //主键标识
    @TableId(type = IdType.AUTO)
    private Long id;
    //邮箱
    private String email;
    //消息
    private String content;
    //创建人
    private Long createdBy;
    //创建时间
    private Date createdTime;
    //更新人
    private Long updatedBy;
    //更新时间
    private Date updatedTime;
    //备注
    private String remark;
    //逻辑删除标志（0正常、1删除）
    @TableLogic
    private Integer delFlag;

}


package com.wedding.domain.entity;

import java.util.Date;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 预约摄影师关联表(AppointPhotographer)表实体类
 *
 * @author zhudake
 * @since 2024-03-24 15:57:50
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("appoint_photographer")
public class AppointPhotographer implements Serializable {
    //主键标识@TableId
    @TableId(type = IdType.AUTO)
    private Long id;

    //预约表id
    private Long appointId;
    //摄影师id
    private Long photographerId;
    //创建人
    @TableField(fill = FieldFill.INSERT)
    private Long createdBy;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;
    //更新人
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updatedBy;
    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedTime;
    // 逻辑删除
    @TableLogic
    private Integer delFlag;

}


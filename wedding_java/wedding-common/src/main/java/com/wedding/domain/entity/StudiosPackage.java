package com.wedding.domain.entity;

import java.util.Date;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 影楼套餐表(StudiosPackage)表实体类
 *
 * @author zhudake
 * @since 2024-03-23 14:21:12
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("studios_package")
public class StudiosPackage implements Serializable {
    //主键表示@TableId
    @TableId(type = IdType.AUTO)
    private Long id;
    //影楼id
    private Long studioId;
    //套餐名称
    private String packageName;
    //套餐描述
    private String packageDesc;
    //套餐价格
    private Integer packagePrice;
    //套餐拍摄时长
    private Integer packageDuration;
    //套餐服务项目
    private String packageIncludes;
    //套餐区别（如摄影套餐、化妆造型套餐）
    private Integer packageType;
    //套餐使用最大人数
    private Integer packageCapacity;
    //是否推荐(0、1)
    private Integer isRecommend;
    // 套餐缩略图
    private String packageThumbnail;
    //套餐状态（0正常、1停用）
    private Integer status;
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
    //逻辑删除标志（0正常、1删除）
    private Integer delFlag;

}


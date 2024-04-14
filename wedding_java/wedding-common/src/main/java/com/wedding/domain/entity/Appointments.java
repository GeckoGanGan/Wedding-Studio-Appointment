package com.wedding.domain.entity;

import java.util.Date;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 预约表(Appointments)表实体类
 *
 * @author zhudake
 * @since 2024-03-01 19:52:23
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("appointments")
public class Appointments implements Serializable {
    //主键唯一标识
    // 设置主键自增策略
    @TableId(type = IdType.AUTO)
    private Long id;
    //用户id
    private Long userId;
    //影楼id
    private Long studioId;
    // 影楼名称
    private String studioName;
    //预约时间
    private Date appointTime;
    //用户预约要求
    private String requirements;

    // 套餐id
    private Long packageId;

    //创建人
    @TableField(fill =  FieldFill.INSERT)
    private Long createdBy;
    //创建时间
    @TableField(fill =  FieldFill.INSERT)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;
    //更新人
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updatedBy;
    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedTime;
    //预约状态（待确认、已确认、已完成）
    private String status;
    //备注
    private String remark;
    //逻辑删除
    @TableLogic
    private Integer delFlag;
    @TableField(exist = false)
    // 禁用FastJSON循环引用
    @JSONField(serialzeFeatures = {SerializerFeature.DisableCircularReferenceDetect})
    private Studios studio;

    @TableField(exist = false)
    @JSONField(serialzeFeatures = {SerializerFeature.DisableCircularReferenceDetect})// 禁用FastJSON循环引用
    private List<Photographers> photographers;
    @TableField(exist = false)
    @JSONField(serialzeFeatures = {SerializerFeature.DisableCircularReferenceDetect})// 禁用FastJSON循环引用
    private StudiosPackage studiosPackage;

}


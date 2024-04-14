package com.wedding.domain.entity;

import java.util.Date;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 聊天信息表(ChatMessages)表实体类
 *
 * @author zhudake
 * @since 2024-03-01 19:52:24
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("chat_messages")
public class ChatMessages implements Serializable {
    //主键，聊天信息唯一标识
    @TableId(type = IdType.AUTO)
    private Long messageId;
    // 影楼Id
    private Long studioId;
    //发送者id
    private Long senderId;
    // 消息发送者名称
    private String senderName;
    //接收者id
    private Long receiverId;
    // 消息接受这名字
    private String receiverName;
    //聊天信息内推
    private String messageText;
    //消息时间戳
    private Date timestamp;
    //标记消息是否已读
    private String isRead;
    //创建人
    private Long createdBy;
    //创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;
    //更新人
    private Long updatedBy;
    //更新时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedTime;
    //备注
    private String remark;
    //逻辑删除标志
    @TableLogic
    private Integer delFlag;
    // 消息发送者头像
    @TableField(exist = false)
    private String senderAvatar;
    // 消息接收者头像
    @TableField(exist = false)
    private String receiverAvatar;

}


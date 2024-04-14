/*
 Navicat Premium Data Transfer

 Source Server         : Mysql
 Source Server Type    : MySQL
 Source Server Version : 80033
 Source Host           : localhost:3306
 Source Schema         : gr_wedding

 Target Server Type    : MySQL
 Target Server Version : 80033
 File Encoding         : 65001

 Date: 11/04/2024 22:46:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for appoint_photographer
-- ----------------------------
DROP TABLE IF EXISTS `appoint_photographer`;
CREATE TABLE `appoint_photographer`  (
  `ID` bigint NOT NULL AUTO_INCREMENT COMMENT '主键标识',
  `APPOINT_ID` bigint NULL DEFAULT NULL COMMENT '预约表id',
  `PHOTOGRAPHER_ID` bigint NULL DEFAULT NULL COMMENT '摄影师id',
  `CREATED_BY` bigint NULL DEFAULT NULL COMMENT '创建人',
  `CREATED_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATED_BY` bigint NULL DEFAULT NULL COMMENT '更新人',
  `UPDATED_TIME` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `DEL_FLAG` int NULL DEFAULT 0 COMMENT '逻辑删除标志（0正常、1删除）',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '预约摄影师关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of appoint_photographer
-- ----------------------------
INSERT INTO `appoint_photographer` VALUES (32, 19, 3, 1, '2024-04-03 13:13:37', 1, '2024-04-03 13:13:37', 0);
INSERT INTO `appoint_photographer` VALUES (33, 19, 2, 1, '2024-04-03 13:13:37', 1, '2024-04-03 13:13:37', 0);
INSERT INTO `appoint_photographer` VALUES (34, 20, 3, 1, '2024-04-03 13:20:47', 1, '2024-04-03 13:20:47', 0);
INSERT INTO `appoint_photographer` VALUES (35, 21, 3, 1, '2024-04-03 13:21:32', 1, '2024-04-03 13:21:32', 0);
INSERT INTO `appoint_photographer` VALUES (36, 22, 1, 1, '2024-04-03 13:22:18', 1, '2024-04-03 13:22:18', 0);
INSERT INTO `appoint_photographer` VALUES (37, 23, 3, 1, '2024-04-03 13:22:57', 1, '2024-04-03 13:22:57', 0);
INSERT INTO `appoint_photographer` VALUES (38, 24, 2, 1, '2024-04-03 13:23:27', 1, '2024-04-03 13:23:27', 0);
INSERT INTO `appoint_photographer` VALUES (39, 25, 3, 1, '2024-04-03 14:52:56', 1, '2024-04-03 14:52:56', 0);
INSERT INTO `appoint_photographer` VALUES (40, 26, 3, 6, '2024-04-05 23:17:06', 6, '2024-04-05 23:17:06', 0);

-- ----------------------------
-- Table structure for appointments
-- ----------------------------
DROP TABLE IF EXISTS `appointments`;
CREATE TABLE `appointments`  (
  `ID` bigint NOT NULL AUTO_INCREMENT COMMENT '主键唯一标识',
  `USER_ID` bigint NULL DEFAULT NULL COMMENT '用户id',
  `STUDIO_ID` bigint NULL DEFAULT NULL COMMENT '影楼id',
  `APPOINT_TIME` datetime NULL DEFAULT NULL COMMENT '预约日期',
  `REQUIREMENTS` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户预约要求',
  `CREATED_BY` bigint NULL DEFAULT NULL COMMENT '创建人',
  `CREATED_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATED_BY` bigint NULL DEFAULT NULL COMMENT '更新人',
  `UPDATED_TIME` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `STATUS` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '预约状态（待确认、已确认、已完成）',
  `REMARK` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  `DEL_FLAG` int NULL DEFAULT 0 COMMENT '逻辑删除',
  `PACKAGE_ID` bigint NULL DEFAULT NULL COMMENT '套餐id',
  `STUDIO_NAME` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '影楼名称',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '预约表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of appointments
-- ----------------------------
INSERT INTO `appointments` VALUES (19, 1, 1, '2024-03-22 12:00:00', '撒大苏打', 1, '2024-04-03 13:13:37', 1, '2024-04-03 13:13:37', '0', NULL, 1, 2, '影楼1');
INSERT INTO `appointments` VALUES (20, 1, 1, '2024-03-22 12:00:00', '', 1, '2024-04-03 13:20:47', 1, '2024-04-03 13:20:47', '0', NULL, 0, 3, '影楼1');
INSERT INTO `appointments` VALUES (21, 1, 1, '2024-03-22 12:00:00', '', 1, '2024-04-03 13:21:32', 1, '2024-04-03 13:21:32', '0', NULL, 1, 1, '影楼1');
INSERT INTO `appointments` VALUES (22, 1, 1, '2024-03-22 12:00:00', '', 1, '2024-04-03 13:22:18', 1, '2024-04-03 13:22:18', '0', NULL, 1, 1, '影楼1');
INSERT INTO `appointments` VALUES (23, 1, 2, '2024-03-22 12:00:00', '', 1, '2024-04-03 13:22:57', 1, '2024-04-03 13:22:57', '0', NULL, 1, 5, '影楼2');
INSERT INTO `appointments` VALUES (24, 1, 2, '2024-03-22 12:00:00', '', 1, '2024-04-03 13:23:27', 1, '2024-04-03 13:23:27', '0', NULL, 1, 6, '影楼2');
INSERT INTO `appointments` VALUES (25, 1, 1, '2024-03-22 12:00:00', 'as的', 1, '2024-04-03 14:52:56', 1, '2024-04-03 14:52:56', '0', NULL, 1, 3, '影楼1');
INSERT INTO `appointments` VALUES (26, 6, 1, '2024-03-22 12:00:00', '', 6, '2024-04-05 23:17:05', 6, '2024-04-05 23:17:05', '0', NULL, 1, 1, '影楼1');

-- ----------------------------
-- Table structure for chat_messages
-- ----------------------------
DROP TABLE IF EXISTS `chat_messages`;
CREATE TABLE `chat_messages`  (
  `MESSAGE_ID` bigint NOT NULL AUTO_INCREMENT COMMENT '主键，聊天信息唯一标识',
  `SENDER_ID` bigint NULL DEFAULT NULL COMMENT '发送者id',
  `RECEIVER_ID` bigint NULL DEFAULT NULL COMMENT '接收者id',
  `MESSAGE_TEXT` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '聊天信息内容',
  `TIMESTAMP` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '消息时间戳',
  `IS_READ` int NULL DEFAULT NULL COMMENT '标记消息是否已读',
  `CREATED_BY` bigint NULL DEFAULT NULL COMMENT '创建人',
  `CREATED_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATED_BY` bigint NULL DEFAULT NULL COMMENT '更新人',
  `UPDATED_TIME` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `REMARK` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  `DEL_FLAG` int NULL DEFAULT 0 COMMENT '逻辑删除标志',
  `SENDER_NAME` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息发送者名称',
  `RECEIVER_NAME` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息接收者名字',
  `STUDIO_ID` int NULL DEFAULT NULL COMMENT '影楼id',
  PRIMARY KEY (`MESSAGE_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 118 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '聊天信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of chat_messages
-- ----------------------------
INSERT INTO `chat_messages` VALUES (5, 1, 7, 'a三大', '2024-03-31 21:32:43', NULL, NULL, '2024-03-31 21:32:44', NULL, '2024-03-31 21:32:44', NULL, 0, 'zhudake', 'zhangsan', 4);
INSERT INTO `chat_messages` VALUES (6, 1, 7, '阿达', '2024-03-31 21:41:32', NULL, NULL, '2024-03-31 21:41:33', NULL, '2024-03-31 21:41:33', NULL, 0, 'zhudake', 'zhangsan', 4);
INSERT INTO `chat_messages` VALUES (7, 7, 1, 'g环境', '2024-03-31 21:41:36', NULL, NULL, '2024-03-31 21:41:37', NULL, '2024-03-31 21:41:37', NULL, 0, 'zhangsan', 'zhudake', 4);
INSERT INTO `chat_messages` VALUES (8, 1, 7, 'a三大', '2024-03-31 21:45:30', NULL, NULL, '2024-03-31 21:45:30', NULL, '2024-03-31 21:45:30', NULL, 0, 'zhudake', 'zhangsan', 4);
INSERT INTO `chat_messages` VALUES (9, 7, 1, 'a三大', '2024-03-31 21:45:33', NULL, NULL, '2024-03-31 21:45:34', NULL, '2024-03-31 21:45:34', NULL, 0, 'zhangsan', 'zhudake', 4);
INSERT INTO `chat_messages` VALUES (10, 1, 7, '阿松大', '2024-03-31 21:47:21', NULL, NULL, '2024-03-31 21:47:22', NULL, '2024-03-31 21:47:22', NULL, 0, 'zhudake', 'zhangsan', 4);
INSERT INTO `chat_messages` VALUES (11, 7, 1, '阿松大', '2024-03-31 21:47:25', NULL, NULL, '2024-03-31 21:47:25', NULL, '2024-03-31 21:47:25', NULL, 0, 'zhangsan', 'zhudake', 4);
INSERT INTO `chat_messages` VALUES (12, 1, 7, '阿松大', '2024-03-31 21:50:17', NULL, NULL, '2024-03-31 21:50:17', NULL, '2024-03-31 21:50:17', NULL, 0, 'zhudake', 'zhangsan', 4);
INSERT INTO `chat_messages` VALUES (13, 7, 1, 'a三大', '2024-03-31 21:50:21', NULL, NULL, '2024-03-31 21:50:22', NULL, '2024-03-31 21:50:22', NULL, 0, 'zhangsan', 'zhudake', 4);
INSERT INTO `chat_messages` VALUES (14, 1, 7, '阿松大', '2024-03-31 21:51:50', NULL, NULL, '2024-03-31 21:51:51', NULL, '2024-03-31 21:51:51', NULL, 0, 'zhudake', 'zhangsan', 4);
INSERT INTO `chat_messages` VALUES (15, 7, 1, '士大夫', '2024-03-31 21:51:53', NULL, NULL, '2024-03-31 21:51:53', NULL, '2024-03-31 21:51:53', NULL, 0, 'zhangsan', 'zhudake', 4);
INSERT INTO `chat_messages` VALUES (16, 1, 7, '阿松大', '2024-03-31 21:54:45', NULL, NULL, '2024-03-31 21:54:45', NULL, '2024-03-31 21:54:45', NULL, 0, 'zhudake', 'zhangsan', 4);
INSERT INTO `chat_messages` VALUES (17, 7, 1, 's带份', '2024-03-31 21:54:47', NULL, NULL, '2024-03-31 21:54:47', NULL, '2024-03-31 21:54:47', NULL, 0, 'zhangsan', 'zhudake', 4);
INSERT INTO `chat_messages` VALUES (18, 1, 7, '手动阀手动阀', '2024-03-31 21:54:51', NULL, NULL, '2024-03-31 21:54:51', NULL, '2024-03-31 21:54:51', NULL, 0, 'zhudake', 'zhangsan', 4);
INSERT INTO `chat_messages` VALUES (19, 1, 7, '大苏打a撒旦', '2024-03-31 22:57:52', NULL, NULL, '2024-03-31 22:57:53', NULL, '2024-03-31 22:57:53', NULL, 0, 'zhudake', 'zhangsan', NULL);
INSERT INTO `chat_messages` VALUES (20, 1, 7, '啊大大大', '2024-04-01 00:09:52', NULL, NULL, '2024-04-01 00:09:53', NULL, '2024-04-01 00:09:53', NULL, 0, 'zhudake', 'zhangsan', 4);
INSERT INTO `chat_messages` VALUES (21, 1, 7, '阿三大苏打', '2024-04-01 00:17:06', NULL, NULL, '2024-04-01 00:17:07', NULL, '2024-04-01 00:17:07', NULL, 0, 'zhudake', 'zhangsan', 4);
INSERT INTO `chat_messages` VALUES (22, 1, 7, '撒大苏打', '2024-04-01 00:17:48', NULL, NULL, '2024-04-01 00:17:48', NULL, '2024-04-01 00:17:48', NULL, 0, 'zhudake', 'zhangsan', 4);
INSERT INTO `chat_messages` VALUES (23, 7, 1, '好看好看', '2024-04-01 00:18:03', NULL, NULL, '2024-04-01 00:18:03', NULL, '2024-04-01 00:18:03', NULL, 0, 'zhangsan', 'zhudake', 4);
INSERT INTO `chat_messages` VALUES (24, 1, 7, 'as达到', '2024-04-01 00:18:45', NULL, NULL, '2024-04-01 00:18:45', NULL, '2024-04-01 00:18:45', NULL, 0, 'zhudake', 'zhangsan', 4);
INSERT INTO `chat_messages` VALUES (25, 7, 1, '法国德国', '2024-04-01 00:18:48', NULL, NULL, '2024-04-01 00:18:48', NULL, '2024-04-01 00:18:48', NULL, 0, 'zhangsan', 'zhudake', 4);
INSERT INTO `chat_messages` VALUES (26, 1, 6, '阿萨大大', '2024-04-01 00:19:01', NULL, NULL, '2024-04-01 00:19:01', NULL, '2024-04-01 00:19:01', NULL, 0, 'zhudake', 'zhuwener', 4);
INSERT INTO `chat_messages` VALUES (27, 6, 1, 'asdad ', '2024-04-01 00:19:06', NULL, NULL, '2024-04-01 00:19:07', NULL, '2024-04-01 00:19:07', NULL, 0, 'zhuwener', 'zhudake', 4);
INSERT INTO `chat_messages` VALUES (28, 1, 6, '哈哈哈哈哈哈哈哈哈哈哈哈哈哈', '2024-04-01 00:19:14', NULL, NULL, '2024-04-01 00:19:14', NULL, '2024-04-01 00:19:14', NULL, 0, 'zhudake', 'zhuwener', 4);
INSERT INTO `chat_messages` VALUES (29, 6, 1, 'sdfsf ', '2024-04-01 00:19:21', NULL, NULL, '2024-04-01 00:19:22', NULL, '2024-04-01 00:19:22', NULL, 0, 'zhuwener', 'zhudake', 4);
INSERT INTO `chat_messages` VALUES (30, 1, 6, '撒谎万一', '2024-04-01 00:20:06', NULL, NULL, '2024-04-01 00:20:06', NULL, '2024-04-01 00:20:06', NULL, 0, 'zhudake', 'zhuwener', 4);
INSERT INTO `chat_messages` VALUES (31, 6, 1, '为啥美哦有名字啊', '2024-04-01 00:20:16', NULL, NULL, '2024-04-01 00:20:16', NULL, '2024-04-01 00:20:16', NULL, 0, 'zhuwener', 'zhudake', 4);
INSERT INTO `chat_messages` VALUES (32, 6, 7, '阿三大苏打', '2024-04-01 00:20:21', NULL, NULL, '2024-04-01 00:20:22', NULL, '2024-04-01 00:20:22', NULL, 0, 'zhuwener', 'zhangsan', 4);
INSERT INTO `chat_messages` VALUES (33, 7, 6, '啥啊', '2024-04-01 00:20:28', NULL, NULL, '2024-04-01 00:20:28', NULL, '2024-04-01 00:20:28', NULL, 0, 'zhangsan', 'zhuwener', 4);
INSERT INTO `chat_messages` VALUES (34, 7, 6, 's带份', '2024-04-01 00:21:09', NULL, NULL, '2024-04-01 00:21:09', NULL, '2024-04-01 00:21:09', NULL, 0, 'zhangsan', 'zhuwener', 4);
INSERT INTO `chat_messages` VALUES (35, 1, 7, '为啥没名字啊', '2024-04-01 00:22:20', NULL, NULL, '2024-04-01 00:22:20', NULL, '2024-04-01 00:22:20', NULL, 0, 'zhudake', 'zhangsan', 4);
INSERT INTO `chat_messages` VALUES (36, 7, 1, '不知道欸', '2024-04-01 00:22:30', NULL, NULL, '2024-04-01 00:22:31', NULL, '2024-04-01 00:22:31', NULL, 0, 'zhangsan', 'zhudake', 4);
INSERT INTO `chat_messages` VALUES (37, 1, 7, '试一下呗', '2024-04-01 00:23:14', NULL, NULL, '2024-04-01 00:23:15', NULL, '2024-04-01 00:23:15', NULL, 0, 'zhudake', 'zhangsan', 4);
INSERT INTO `chat_messages` VALUES (38, 1, 7, '有名字了', '2024-04-01 00:27:55', NULL, NULL, '2024-04-01 00:27:56', NULL, '2024-04-01 00:27:56', NULL, 0, 'zhudake', 'zhangsan', 4);
INSERT INTO `chat_messages` VALUES (39, 7, 1, '确实', '2024-04-01 00:28:04', NULL, NULL, '2024-04-01 00:28:05', NULL, '2024-04-01 00:28:05', NULL, 0, 'zhangsan', 'zhudake', 4);
INSERT INTO `chat_messages` VALUES (40, 1, 6, '来咯', '2024-04-01 00:28:45', NULL, NULL, '2024-04-01 00:28:46', NULL, '2024-04-01 00:28:46', NULL, 0, 'zhudake', 'zhuwener', 4);
INSERT INTO `chat_messages` VALUES (41, 6, 1, '小老弟，没实力', '2024-04-01 00:28:54', NULL, NULL, '2024-04-01 00:28:54', NULL, '2024-04-01 00:28:54', NULL, 0, 'zhuwener', 'zhudake', 4);
INSERT INTO `chat_messages` VALUES (42, 1, 6, '我去你丫的', '2024-04-01 00:29:07', NULL, NULL, '2024-04-01 00:29:07', NULL, '2024-04-01 00:29:07', NULL, 0, 'zhudake', 'zhuwener', 4);
INSERT INTO `chat_messages` VALUES (43, 7, 6, '是否是', '2024-04-01 00:40:30', NULL, NULL, '2024-04-01 00:40:31', NULL, '2024-04-01 00:40:31', NULL, 0, 'zhangsan', 'zhuwener', 4);
INSERT INTO `chat_messages` VALUES (44, 6, 7, '不是', '2024-04-01 00:40:45', NULL, NULL, '2024-04-01 00:40:46', NULL, '2024-04-01 00:40:46', NULL, 0, 'zhuwener', 'zhangsan', 4);
INSERT INTO `chat_messages` VALUES (45, 7, 1, '傻逼', '2024-04-01 00:41:58', NULL, NULL, '2024-04-01 00:41:58', NULL, '2024-04-01 00:41:58', NULL, 0, 'zhangsan', 'zhudake', 4);
INSERT INTO `chat_messages` VALUES (46, 1, 7, '啊啊？', '2024-04-01 00:42:06', NULL, NULL, '2024-04-01 00:42:06', NULL, '2024-04-01 00:42:06', NULL, 0, 'zhudake', 'zhangsan', 4);
INSERT INTO `chat_messages` VALUES (47, 1, 7, '咋hi是啊', '2024-04-01 00:43:17', NULL, NULL, '2024-04-01 00:43:18', NULL, '2024-04-01 00:43:18', NULL, 0, 'zhudake', 'zhangsan', 4);
INSERT INTO `chat_messages` VALUES (48, 1, 7, '为啥啊', '2024-04-01 00:45:02', NULL, NULL, '2024-04-01 00:45:02', NULL, '2024-04-01 00:45:02', NULL, 0, 'zhudake', 'zhangsan', 4);
INSERT INTO `chat_messages` VALUES (49, 7, 1, '不知道啊', '2024-04-01 00:45:12', NULL, NULL, '2024-04-01 00:45:12', NULL, '2024-04-01 00:45:12', NULL, 0, 'zhangsan', 'zhudake', 4);
INSERT INTO `chat_messages` VALUES (50, 1, 7, '好奇怪啊', '2024-04-01 00:50:19', NULL, NULL, '2024-04-01 00:50:19', NULL, '2024-04-01 00:50:19', NULL, 0, 'zhudake', 'zhangsan', 4);
INSERT INTO `chat_messages` VALUES (51, 7, 1, '确实啊', '2024-04-01 00:50:31', NULL, NULL, '2024-04-01 00:50:32', NULL, '2024-04-01 00:50:32', NULL, 0, 'zhangsan', 'zhudake', 4);
INSERT INTO `chat_messages` VALUES (52, 7, 6, '好奇怪啊', '2024-04-01 00:56:32', NULL, NULL, '2024-04-01 00:56:32', NULL, '2024-04-01 00:56:32', NULL, 0, 'zhangsan', 'zhuwener', 4);
INSERT INTO `chat_messages` VALUES (53, 7, 1, '不知道为什么', '2024-04-01 00:56:46', NULL, NULL, '2024-04-01 00:56:46', NULL, '2024-04-01 00:56:46', NULL, 0, 'zhangsan', 'zhudake', 4);
INSERT INTO `chat_messages` VALUES (54, 1, 7, '噫噫噫', '2024-04-01 00:57:03', NULL, NULL, '2024-04-01 00:57:04', NULL, '2024-04-01 00:57:04', NULL, 0, 'zhudake', 'zhangsan', 4);
INSERT INTO `chat_messages` VALUES (55, 7, 1, '？？？', '2024-04-01 00:57:10', NULL, NULL, '2024-04-01 00:57:11', NULL, '2024-04-01 00:57:11', NULL, 0, 'zhangsan', 'zhudake', 4);
INSERT INTO `chat_messages` VALUES (56, 1, 7, '我知道为什么了', '2024-04-01 00:58:45', NULL, NULL, '2024-04-01 00:58:45', NULL, '2024-04-01 00:58:45', NULL, 0, 'zhudake', 'zhangsan', 4);
INSERT INTO `chat_messages` VALUES (57, 1, 6, 'ad', '2024-04-01 11:35:14', NULL, NULL, '2024-04-01 11:35:15', NULL, '2024-04-01 11:35:15', NULL, 0, 'zhudake', 'zhuwener', 5);
INSERT INTO `chat_messages` VALUES (58, 6, 1, '啥啊', '2024-04-01 11:35:30', NULL, NULL, '2024-04-01 11:35:30', NULL, '2024-04-01 11:35:30', NULL, 0, 'zhuwener', 'zhudake', 5);
INSERT INTO `chat_messages` VALUES (59, 6, 1, '为什么你的头像会编程我的头像啊', '2024-04-01 11:35:48', NULL, NULL, '2024-04-01 11:35:48', NULL, '2024-04-01 11:35:48', NULL, 0, 'zhuwener', 'zhudake', 5);
INSERT INTO `chat_messages` VALUES (60, 1, 6, 'guizhidaoa ', '2024-04-01 11:35:53', NULL, NULL, '2024-04-01 11:35:53', NULL, '2024-04-01 11:35:53', NULL, 0, 'zhudake', 'zhuwener', 5);
INSERT INTO `chat_messages` VALUES (61, 1, 6, '鬼知道啊', '2024-04-01 11:36:01', NULL, NULL, '2024-04-01 11:36:02', NULL, '2024-04-01 11:36:02', NULL, 0, 'zhudake', 'zhuwener', 5);
INSERT INTO `chat_messages` VALUES (62, 6, 1, '好奇怪噢', '2024-04-01 11:36:41', NULL, NULL, '2024-04-01 11:36:42', NULL, '2024-04-01 11:36:42', NULL, 0, 'zhuwener', 'zhudake', 5);
INSERT INTO `chat_messages` VALUES (63, 6, 1, '我找了半天也没找到Bug', '2024-04-01 11:36:50', NULL, NULL, '2024-04-01 11:36:50', NULL, '2024-04-01 11:36:50', NULL, 0, 'zhuwener', 'zhudake', 5);
INSERT INTO `chat_messages` VALUES (64, 1, 6, '你就跟个得儿一样', '2024-04-01 11:37:16', NULL, NULL, '2024-04-01 11:37:16', NULL, '2024-04-01 11:37:16', NULL, 0, 'zhudake', 'zhuwener', 5);
INSERT INTO `chat_messages` VALUES (65, 1, 6, '这都搞不定', '2024-04-01 11:37:25', NULL, NULL, '2024-04-01 11:37:26', NULL, '2024-04-01 11:37:26', NULL, 0, 'zhudake', 'zhuwener', 5);
INSERT INTO `chat_messages` VALUES (66, 6, 1, '唉，太菜了啊，没办法', '2024-04-01 11:37:37', NULL, NULL, '2024-04-01 11:37:38', NULL, '2024-04-01 11:37:38', NULL, 0, 'zhuwener', 'zhudake', 5);
INSERT INTO `chat_messages` VALUES (67, 6, 1, '你的还得练练啊', '2024-04-01 11:38:05', NULL, NULL, '2024-04-01 11:38:05', NULL, '2024-04-01 11:38:05', NULL, 0, 'zhuwener', 'zhudake', 5);
INSERT INTO `chat_messages` VALUES (68, 1, 6, '练就练，谁怕谁', '2024-04-01 11:38:17', NULL, NULL, '2024-04-01 11:38:17', NULL, '2024-04-01 11:38:17', NULL, 0, 'zhudake', 'zhuwener', 5);
INSERT INTO `chat_messages` VALUES (69, 1, 6, '你再试试', '2024-04-01 11:39:41', NULL, NULL, '2024-04-01 11:39:42', NULL, '2024-04-01 11:39:42', NULL, 0, 'zhudake', 'zhuwener', 5);
INSERT INTO `chat_messages` VALUES (70, 1, 6, '遭不住了', '2024-04-01 11:43:16', NULL, NULL, '2024-04-01 11:43:16', NULL, '2024-04-01 11:43:16', NULL, 0, 'zhudake', 'zhuwener', 5);
INSERT INTO `chat_messages` VALUES (71, 1, 6, '哟西，名字bug解决了', '2024-04-01 11:48:06', NULL, NULL, '2024-04-01 11:48:06', NULL, '2024-04-01 11:48:06', NULL, 0, 'zhudake', 'zhuwener', 5);
INSERT INTO `chat_messages` VALUES (72, 6, 1, '加油，你阔以滴', '2024-04-01 11:48:25', NULL, NULL, '2024-04-01 11:48:26', NULL, '2024-04-01 11:48:26', NULL, 0, 'zhuwener', 'zhudake', 5);
INSERT INTO `chat_messages` VALUES (73, 1, 6, '阿松大', '2024-04-01 11:50:23', NULL, NULL, '2024-04-01 11:50:24', NULL, '2024-04-01 11:50:24', NULL, 0, 'zhudake', 'zhuwener', 5);
INSERT INTO `chat_messages` VALUES (74, 1, 6, '再试试', '2024-04-01 11:55:17', NULL, NULL, '2024-04-01 11:55:18', NULL, '2024-04-01 11:55:18', NULL, 0, 'zhudake', 'zhuwener', 5);
INSERT INTO `chat_messages` VALUES (75, 6, 1, '卧槽，好奇奇怪啊', '2024-04-01 11:55:25', NULL, NULL, '2024-04-01 11:55:26', NULL, '2024-04-01 11:55:26', NULL, 0, 'zhuwener', 'zhudake', 5);
INSERT INTO `chat_messages` VALUES (76, 1, 6, '真奇怪', '2024-04-01 11:58:07', NULL, NULL, '2024-04-01 11:58:08', NULL, '2024-04-01 11:58:08', NULL, 0, 'zhudake', 'zhuwener', 5);
INSERT INTO `chat_messages` VALUES (77, 6, 1, '确实', '2024-04-01 11:58:50', NULL, NULL, '2024-04-01 11:58:51', NULL, '2024-04-01 11:58:51', NULL, 0, 'zhuwener', 'zhudake', 5);
INSERT INTO `chat_messages` VALUES (78, 1, 6, '吃饭去了，不说了', '2024-04-01 12:09:58', NULL, NULL, '2024-04-01 12:09:59', NULL, '2024-04-01 12:09:59', NULL, 0, 'zhudake', 'zhuwener', 5);
INSERT INTO `chat_messages` VALUES (79, 6, 1, '给我带点', '2024-04-01 12:10:20', NULL, NULL, '2024-04-01 12:10:21', NULL, '2024-04-01 12:10:21', NULL, 0, 'zhuwener', 'zhudake', 5);
INSERT INTO `chat_messages` VALUES (80, 1, 6, '？？？', '2024-04-01 13:09:46', NULL, NULL, '2024-04-01 13:09:46', NULL, '2024-04-01 13:09:46', NULL, 0, 'zhudake', 'zhuwener', 4);
INSERT INTO `chat_messages` VALUES (81, 6, 1, 'weisha a', '2024-04-01 13:11:29', NULL, NULL, '2024-04-01 13:11:30', NULL, '2024-04-01 13:11:30', NULL, 0, 'zhuwener', 'zhudake', 4);
INSERT INTO `chat_messages` VALUES (82, 1, 6, '怎么感觉有bug', '2024-04-01 13:12:00', NULL, NULL, '2024-04-01 13:12:00', NULL, '2024-04-01 13:12:00', NULL, 0, 'zhudake', 'zhuwener', 4);
INSERT INTO `chat_messages` VALUES (83, 6, 1, '啥bug?', '2024-04-01 13:12:11', NULL, NULL, '2024-04-01 13:12:11', NULL, '2024-04-01 13:12:11', NULL, 0, 'zhuwener', 'zhudake', 4);
INSERT INTO `chat_messages` VALUES (84, 1, 6, '你看看聊天记录，好像有问题啊', '2024-04-01 13:12:46', NULL, NULL, '2024-04-01 13:12:47', NULL, '2024-04-01 13:12:47', NULL, 0, 'zhudake', 'zhuwener', 4);
INSERT INTO `chat_messages` VALUES (85, 6, 1, '好像是噢', '2024-04-01 13:13:02', NULL, NULL, '2024-04-01 13:13:02', NULL, '2024-04-01 13:13:02', NULL, 0, 'zhuwener', 'zhudake', 4);
INSERT INTO `chat_messages` VALUES (86, 6, 1, '？', '2024-04-01 13:15:34', NULL, NULL, '2024-04-01 13:15:35', NULL, '2024-04-01 13:15:35', NULL, 0, 'zhuwener', 'zhudake', 4);
INSERT INTO `chat_messages` VALUES (87, 1, 6, '？？', '2024-04-01 13:18:27', NULL, NULL, '2024-03-01 13:18:27', NULL, '2024-03-01 13:18:27', NULL, 0, 'zhudake', 'zhuwener', 4);
INSERT INTO `chat_messages` VALUES (88, 1, 6, '？', '2024-04-01 13:18:43', NULL, NULL, '2024-03-01 13:18:43', NULL, '2024-03-01 13:18:43', NULL, 0, 'zhudake', 'zhuwener', 4);
INSERT INTO `chat_messages` VALUES (89, 1, 6, '？？', '2024-04-01 13:27:59', NULL, NULL, '2024-03-01 13:27:59', NULL, '2024-03-01 13:27:59', NULL, 0, 'zhudake', 'zhuwener', 4);
INSERT INTO `chat_messages` VALUES (90, 1, 6, '再试试', '2024-04-01 13:30:51', NULL, NULL, '2024-03-01 13:30:51', NULL, '2024-03-01 13:30:51', NULL, 0, 'zhudake', 'zhuwener', 4);
INSERT INTO `chat_messages` VALUES (91, 6, 1, '好像好了', '2024-04-01 13:30:56', NULL, NULL, '2024-03-01 13:30:56', NULL, '2024-03-01 13:30:56', NULL, 0, 'zhuwener', 'zhudake', 4);
INSERT INTO `chat_messages` VALUES (92, 6, 1, '真的？', '2024-04-01 13:35:35', NULL, NULL, '2024-03-01 13:35:35', NULL, '2024-03-01 13:35:35', NULL, 0, 'zhuwener', 'zhudake', 4);
INSERT INTO `chat_messages` VALUES (93, 1, 6, '是的', '2024-04-01 13:35:41', NULL, NULL, '2024-03-01 13:35:41', NULL, '2024-03-01 13:35:41', NULL, 0, 'zhudake', 'zhuwener', 4);
INSERT INTO `chat_messages` VALUES (94, 1, 6, '之前的聊天记录好奇怪啊', '2024-04-01 13:42:45', NULL, NULL, '2024-03-01 13:42:45', NULL, '2024-03-01 13:42:45', NULL, 0, 'zhudake', 'zhuwener', 4);
INSERT INTO `chat_messages` VALUES (95, 6, 1, '确实', '2024-04-01 13:42:58', NULL, NULL, '2024-03-01 13:42:58', NULL, '2024-03-01 13:42:58', NULL, 0, 'zhuwener', 'zhudake', 4);
INSERT INTO `chat_messages` VALUES (96, 1, 6, '你走没', '2024-04-01 13:44:14', NULL, NULL, '2024-03-01 13:44:14', NULL, '2024-03-01 13:44:14', NULL, 0, 'zhudake', 'zhuwener', 4);
INSERT INTO `chat_messages` VALUES (97, 6, 1, '没有啊', '2024-04-01 14:04:52', NULL, NULL, '2024-03-01 14:04:52', NULL, '2024-03-01 14:04:52', NULL, 0, 'zhuwener', 'zhudake', 4);
INSERT INTO `chat_messages` VALUES (98, 1, 6, '很好', '2024-04-01 14:11:42', NULL, NULL, '2024-03-01 14:11:42', NULL, '2024-03-01 14:11:42', NULL, 0, 'zhudake', 'zhuwener', 2);
INSERT INTO `chat_messages` VALUES (99, 6, 1, '哟西', '2024-04-01 14:11:46', NULL, NULL, '2024-03-01 14:11:46', NULL, '2024-03-01 14:11:46', NULL, 0, 'zhuwener', 'zhudake', 2);
INSERT INTO `chat_messages` VALUES (100, 1, 6, '八个雅鹿', '2024-04-01 17:18:50', NULL, NULL, '2024-03-01 17:18:50', NULL, '2024-03-01 17:18:50', NULL, 0, 'zhudake', 'zhuwener', 4);
INSERT INTO `chat_messages` VALUES (101, 6, 1, '什么牛马', '2024-04-01 17:19:12', NULL, NULL, '2024-03-01 17:19:12', NULL, '2024-03-01 17:19:12', NULL, 0, 'zhuwener', 'zhudake', 4);
INSERT INTO `chat_messages` VALUES (102, 1, 6, '傻逼', '2024-04-01 22:49:23', NULL, NULL, '2024-03-01 22:49:23', NULL, '2024-03-01 22:49:23', NULL, 0, 'zhudake', 'zhuwener', 4);
INSERT INTO `chat_messages` VALUES (103, 6, 1, '你什么牛马', '2024-04-01 22:49:32', NULL, NULL, '2024-03-01 22:49:32', NULL, '2024-03-01 22:49:32', NULL, 0, 'zhuwener', 'zhudake', 4);
INSERT INTO `chat_messages` VALUES (104, 6, 1, '你吃饭每', '2024-04-02 18:47:45', NULL, NULL, '2024-03-02 18:47:45', NULL, '2024-03-02 18:47:45', NULL, 0, 'zhuwener', 'zhudake', 4);
INSERT INTO `chat_messages` VALUES (105, 1, 6, '刚吃完', '2024-04-02 18:48:01', NULL, NULL, '2024-03-02 18:48:01', NULL, '2024-03-02 18:48:01', NULL, 0, 'zhudake', 'zhuwener', 4);
INSERT INTO `chat_messages` VALUES (106, 6, 1, 'ohayo', '2024-04-03 10:30:22', NULL, NULL, '2024-03-03 10:30:22', NULL, '2024-03-03 10:30:22', NULL, 0, 'zhuwener', 'zhudake', 1);
INSERT INTO `chat_messages` VALUES (107, 1, 6, 'hhhhh', '2024-04-03 10:30:32', NULL, NULL, '2024-03-03 10:30:32', NULL, '2024-03-03 10:30:32', NULL, 0, 'zhudake', 'zhuwener', 1);
INSERT INTO `chat_messages` VALUES (108, 6, 1, 'hhh', '2024-04-03 12:02:25', NULL, NULL, '2024-03-03 12:02:25', NULL, '2024-03-03 12:02:25', NULL, 0, 'zhuwener', 'zhudake', 5);
INSERT INTO `chat_messages` VALUES (109, 6, 1, 'asdas', '2024-04-03 14:55:22', NULL, NULL, '2024-03-03 14:55:22', NULL, '2024-03-03 14:55:22', NULL, 0, 'zhuwener', 'zhudake', 1);
INSERT INTO `chat_messages` VALUES (110, 1, 6, 'ashkdhashdjkl', '2024-04-10 19:10:32', NULL, NULL, '2024-03-10 19:10:32', NULL, '2024-03-10 19:10:32', NULL, 0, 'zhudake', 'zhuwener', 1);
INSERT INTO `chat_messages` VALUES (111, 6, 1, 'shabi ', '2024-04-10 19:10:37', NULL, NULL, '2024-03-10 19:10:37', NULL, '2024-03-10 19:10:37', NULL, 0, 'zhuwener', 'zhudake', 1);
INSERT INTO `chat_messages` VALUES (112, 1, 6, '阿松大', '2024-04-10 21:45:33', NULL, NULL, '2024-03-10 21:45:33', NULL, '2024-03-10 21:45:33', NULL, 0, 'zhudake', 'zhuwener', 1);
INSERT INTO `chat_messages` VALUES (113, 1, 6, '点三公分', '2024-04-10 21:48:47', NULL, NULL, '2024-03-10 21:48:47', NULL, '2024-03-10 21:48:47', NULL, 0, 'zhudake', 'zhuwener', 1);
INSERT INTO `chat_messages` VALUES (114, 6, 1, 'asdas', '2024-04-11 16:28:19', NULL, NULL, '2024-03-11 16:28:19', NULL, '2024-03-11 16:28:19', NULL, 0, 'zhuwener', 'zhudake', 1);
INSERT INTO `chat_messages` VALUES (115, 1, 6, 'asd', '2024-04-11 16:28:22', NULL, NULL, '2024-03-11 16:28:22', NULL, '2024-03-11 16:28:22', NULL, 0, 'zhudake', 'zhuwener', 1);
INSERT INTO `chat_messages` VALUES (116, 7, 1, 'asdasd', '2024-04-11 17:14:24', NULL, NULL, '2024-03-11 17:14:24', NULL, '2024-03-11 17:14:24', NULL, 0, 'zhangsan', 'zhudake', 5);
INSERT INTO `chat_messages` VALUES (117, 1, 7, 'asdas', '2024-04-11 17:14:26', NULL, NULL, '2024-03-11 17:14:26', NULL, '2024-03-11 17:14:26', NULL, 0, 'zhudake', 'zhangsan', 5);

-- ----------------------------
-- Table structure for collect
-- ----------------------------
DROP TABLE IF EXISTS `collect`;
CREATE TABLE `collect`  (
  `ID` bigint NOT NULL AUTO_INCREMENT COMMENT '主键标识',
  `TYPE` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '收藏类型',
  `COLLECT_ID` bigint NULL DEFAULT NULL COMMENT '收藏id',
  `NAME` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '收藏名称',
  `CREATED_BY` bigint NULL DEFAULT NULL COMMENT '创建人',
  `CREATED_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATED_BY` bigint NULL DEFAULT NULL COMMENT '更新人',
  `UPDATED_TIME` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `DEL_FLAG` int NULL DEFAULT 0 COMMENT '逻辑删除标志（0正常、1删除）',
  `USER_ID` bigint NULL DEFAULT NULL COMMENT '收藏者id',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '收藏表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of collect
-- ----------------------------
INSERT INTO `collect` VALUES (1, '0', 4, '无敌牛逼影楼', 1, '2024-04-06 22:38:17', 1, '2024-04-06 22:38:17', 1, 1);
INSERT INTO `collect` VALUES (2, '0', 3, '朱文二超级影楼', 1, '2024-04-06 22:48:45', 1, '2024-04-06 22:48:45', 1, 1);
INSERT INTO `collect` VALUES (3, '0', 1, '影楼1', 1, '2024-04-06 22:50:05', 1, '2024-04-06 22:50:05', 1, 1);
INSERT INTO `collect` VALUES (4, '0', 4, '无敌牛逼影楼', 1, '2024-04-09 21:35:14', 1, '2024-04-09 21:35:14', 1, 1);
INSERT INTO `collect` VALUES (5, '0', 1, '影楼1', 1, '2024-04-10 19:14:39', 1, '2024-04-10 19:14:39', 1, 1);
INSERT INTO `collect` VALUES (9, '1', 1, 'zhudake', 1, '2024-04-10 19:31:28', 1, '2024-04-10 19:31:28', 1, 1);
INSERT INTO `collect` VALUES (10, '1', 1, 'zhudake', 1, '2024-04-10 19:41:19', 1, '2024-04-10 19:41:19', 1, 1);
INSERT INTO `collect` VALUES (11, '0', 4, '无敌牛逼影楼', 1, '2024-04-10 19:43:07', 1, '2024-04-10 19:43:07', 1, 1);
INSERT INTO `collect` VALUES (12, '1', 2, 'zhuxiaoke', 1, '2024-04-10 19:43:11', 1, '2024-04-10 19:43:11', 1, 1);
INSERT INTO `collect` VALUES (13, '1', 3, '张三', 1, '2024-04-10 19:43:46', 1, '2024-04-10 19:43:46', 1, 1);
INSERT INTO `collect` VALUES (14, '1', 2, 'zhuxiaoke', 1, '2024-04-10 19:43:47', 1, '2024-04-10 19:43:47', 0, 1);
INSERT INTO `collect` VALUES (15, '1', 1, 'zhudake', 1, '2024-04-10 19:43:49', 1, '2024-04-10 19:43:49', 0, 1);
INSERT INTO `collect` VALUES (16, '0', 5, '想不出来名儿', 7, '2024-04-11 17:20:39', 7, '2024-04-11 17:20:39', 1, 7);
INSERT INTO `collect` VALUES (17, '1', 1, 'zhudake', 7, '2024-04-11 17:21:04', 7, '2024-04-11 17:21:04', 0, 7);
INSERT INTO `collect` VALUES (18, '0', 4, '无敌牛逼影楼', 7, '2024-04-11 17:21:14', 7, '2024-04-11 17:21:14', 0, 7);
INSERT INTO `collect` VALUES (19, '0', 3, '朱文二超级影楼', 1, '2024-04-11 21:23:52', 1, '2024-04-11 21:23:52', 1, 1);
INSERT INTO `collect` VALUES (20, '0', 4, '无敌牛逼影楼', 1, '2024-04-11 21:28:42', 1, '2024-04-11 21:28:42', 0, 1);

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '评论类型（0代表文章评论，1代表友链评论）',
  `studio_id` bigint NULL DEFAULT NULL COMMENT '文章id',
  `root_id` bigint NULL DEFAULT -1 COMMENT '根评论id',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '评论内容',
  `to_comment_user_id` bigint NULL DEFAULT -1 COMMENT '所回复的目标评论的userid',
  `to_comment_id` bigint NULL DEFAULT -1 COMMENT '回复目标评论id',
  `created_by` bigint NULL DEFAULT NULL,
  `created_time` datetime NULL DEFAULT NULL,
  `updated_by` bigint NULL DEFAULT NULL COMMENT '更新人id',
  `updated_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 90 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '评论表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (1, '0', 1, -1, 'hhhh', -1, -1, 1, '2024-03-18 22:04:39', 1, '2024-03-18 22:04:46', 0);
INSERT INTO `comment` VALUES (2, '0', 1, 1, 'afadsf', 1, 1, 6, '2024-03-18 22:05:23', 6, '2024-03-18 22:05:29', 0);
INSERT INTO `comment` VALUES (3, '0', 1, -1, '阿松大梵蒂冈阿松大', -1, -1, 1, '2024-03-18 23:14:35', 6, '2024-03-18 23:14:52', 0);
INSERT INTO `comment` VALUES (4, '0', 1, 3, 'fafadfadf', 1, 3, 6, '2024-03-18 23:14:37', 6, '2024-03-18 23:14:53', 0);
INSERT INTO `comment` VALUES (5, '0', 2, -1, '阿松大hhhhhhhhhhhhhhhhhhhhhhhhhh哈哈哈哈哈哈哈哈红红火火恍恍惚惚', -1, -1, 1, '2024-03-19 20:46:34', 1, '2024-03-19 20:46:47', 0);
INSERT INTO `comment` VALUES (6, '0', 2, -1, '啊啊啊', -1, -1, 1, '2024-03-19 20:46:35', 1, '2024-03-19 20:46:48', 0);
INSERT INTO `comment` VALUES (7, '0', 2, -1, '水水水水', -1, -1, 1, '2024-03-19 20:46:37', 1, '2024-03-19 20:46:49', 0);
INSERT INTO `comment` VALUES (8, '0', 2, -1, '阿松大', -1, -1, 1, '2024-03-19 20:46:38', 1, '2024-03-19 20:44:59', 0);
INSERT INTO `comment` VALUES (9, '0', 1, -1, '阿松大', -1, -1, 1, '2024-03-19 20:53:46', 1, '2024-03-19 20:53:46', 0);
INSERT INTO `comment` VALUES (10, '0', 1, 9, 'a说的', -1, 9, 1, '2024-03-19 20:53:51', 1, '2024-03-19 20:53:51', 0);
INSERT INTO `comment` VALUES (11, '0', 1, 1, '阿松大', -1, 2, 1, '2024-03-19 20:54:00', 1, '2024-03-19 20:54:00', 0);
INSERT INTO `comment` VALUES (12, '0', 1, 9, '阿松大', -1, 10, 1, '2024-03-19 20:54:12', 1, '2024-03-19 20:54:12', 0);
INSERT INTO `comment` VALUES (13, '0', 2, 5, '会换行', -1, 5, 1, '2024-03-19 20:54:53', 1, '2024-03-19 20:54:53', 0);
INSERT INTO `comment` VALUES (14, '0', 2, 5, '嘀哒哒', -1, 13, 1, '2024-03-19 20:54:59', 1, '2024-03-19 20:54:59', 0);
INSERT INTO `comment` VALUES (15, '0', 2, 5, '我要吃饭', 1, 14, 1, '2024-03-19 20:55:10', 1, '2024-03-19 20:55:10', 0);
INSERT INTO `comment` VALUES (16, '0', 2, 5, '测试三级评论', -1, 15, 1, '2024-03-19 20:59:38', 1, '2024-03-19 20:59:38', 0);
INSERT INTO `comment` VALUES (17, '0', 2, 5, '牛逼', 1, 16, 1, '2024-03-19 21:10:51', 1, '2024-03-19 21:10:51', 0);
INSERT INTO `comment` VALUES (18, '0', 2, -1, '我要吃饭！\n', -1, -1, 1, '2024-03-19 21:14:34', 1, '2024-03-19 21:14:34', 0);
INSERT INTO `comment` VALUES (19, '0', 2, 18, '别吃了\n', -1, 18, 1, '2024-03-19 21:14:42', 1, '2024-03-19 21:14:42', 0);
INSERT INTO `comment` VALUES (20, '0', 2, 18, '吃屎吧你', 1, 19, 1, '2024-03-19 21:14:50', 1, '2024-03-19 21:14:50', 0);
INSERT INTO `comment` VALUES (21, '0', 1, 3, '按时', 6, 4, 1, '2024-03-19 22:04:34', 1, '2024-03-19 22:04:34', 0);
INSERT INTO `comment` VALUES (22, '0', 1, -1, '八个雅鹿', -1, -1, 1, '2024-03-19 22:05:02', 1, '2024-03-19 22:05:02', 0);
INSERT INTO `comment` VALUES (23, '0', 2, 5, '哟哟哟', 1, 16, 1, '2024-03-19 22:08:54', 1, '2024-03-19 22:08:54', 0);
INSERT INTO `comment` VALUES (24, '0', 1, 1, '阿松大', -1, 1, 1, '2024-03-19 22:25:35', 1, '2024-03-19 22:25:35', 0);
INSERT INTO `comment` VALUES (25, '0', 2, -1, '灌灌灌灌', -1, -1, 1, '2024-03-20 20:57:31', 1, '2024-03-20 20:57:31', 0);
INSERT INTO `comment` VALUES (26, '0', 2, -1, '八个雅鹿，你的瓦达西沙喇哈伊', -1, -1, 1, '2024-03-20 20:58:03', 1, '2024-03-20 20:58:03', 0);
INSERT INTO `comment` VALUES (27, '0', 2, -1, '实打实大苏打阿三大苏打阿三大苏打实打实大苏打DFSFDAFASFADSFX书法大赛发撒发顺实打实主打尔', -1, -1, 1, '2024-03-20 20:58:45', 1, '2024-03-20 20:58:45', 0);
INSERT INTO `comment` VALUES (28, '0', 2, -1, '哟西', -1, -1, 1, '2024-03-20 20:59:00', 1, '2024-03-20 20:59:00', 0);
INSERT INTO `comment` VALUES (29, '0', 2, -1, '牛滴，干什么思密达', -1, -1, 1, '2024-03-20 20:59:40', 1, '2024-03-20 20:59:40', 0);
INSERT INTO `comment` VALUES (30, '0', 2, -1, '是单独发顺丰的', -1, -1, 1, '2024-03-20 20:59:47', 1, '2024-03-20 20:59:47', 0);
INSERT INTO `comment` VALUES (31, '0', 2, -1, '以后', -1, -1, 1, '2024-03-20 20:59:59', 1, '2024-03-20 20:59:59', 0);
INSERT INTO `comment` VALUES (32, '0', 2, -1, '阿三大苏打', -1, -1, 1, '2024-03-20 21:04:37', 1, '2024-03-20 21:04:37', 0);
INSERT INTO `comment` VALUES (33, '0', 1, 9, '是撒比', 1, 12, 1, '2024-03-20 21:14:37', 1, '2024-03-20 21:14:37', 0);
INSERT INTO `comment` VALUES (34, '0', 1, 9, '规划局', 1, 33, 1, '2024-03-20 21:14:46', 1, '2024-03-20 21:14:46', 0);
INSERT INTO `comment` VALUES (35, '0', 1, -1, 'asd', -1, -1, 1, '2024-03-20 22:01:34', 1, '2024-03-20 22:01:34', 0);
INSERT INTO `comment` VALUES (36, '0', 1, 9, 'niubi', 1, 34, 1, '2024-03-20 22:01:44', 1, '2024-03-20 22:01:44', 0);
INSERT INTO `comment` VALUES (37, '0', 2, 32, 'ASD', 1, 32, 1, '2024-03-20 22:33:54', 1, '2024-03-20 22:33:54', 0);
INSERT INTO `comment` VALUES (38, '0', 2, -1, 'asd', -1, -1, 1, '2024-03-21 20:29:42', 1, '2024-03-21 20:29:42', 0);
INSERT INTO `comment` VALUES (39, '0', 2, -1, 'dsaf', -1, -1, 8, '2024-03-21 23:16:56', 8, '2024-03-21 23:16:56', 0);
INSERT INTO `comment` VALUES (40, '0', 2, -1, '吃饭了吗', -1, -1, 8, '2024-03-21 23:17:20', 8, '2024-03-21 23:17:20', 0);
INSERT INTO `comment` VALUES (41, '0', 1, -1, 'sad', -1, -1, 1, '2024-03-22 12:12:07', 1, '2024-03-22 12:12:07', 0);
INSERT INTO `comment` VALUES (42, '0', 2, -1, '王老五无敌', -1, -1, 9, '2024-03-22 13:33:05', 9, '2024-03-22 13:33:05', 0);
INSERT INTO `comment` VALUES (43, '0', 2, 42, '无敌个屁', 9, 42, 9, '2024-03-22 13:33:15', 9, '2024-03-22 13:33:15', 0);
INSERT INTO `comment` VALUES (44, '0', 2, -1, '八个亚硫', -1, -1, 1, '2024-03-22 20:16:23', 1, '2024-03-22 20:16:23', 0);
INSERT INTO `comment` VALUES (45, '0', 2, 44, '哈哈哈哈', 1, 44, 1, '2024-03-22 20:16:29', 1, '2024-03-22 20:16:29', 0);
INSERT INTO `comment` VALUES (46, '0', 2, -1, '八个雅鹿', -1, -1, 1, '2024-03-22 20:47:19', 1, '2024-03-22 20:47:19', 0);
INSERT INTO `comment` VALUES (47, '0', 2, 46, '哈哈哈哈', 1, 46, 1, '2024-03-22 20:47:25', 1, '2024-03-22 20:47:25', 0);
INSERT INTO `comment` VALUES (48, '0', 2, 46, '牛蛙', 1, 47, 1, '2024-03-22 20:47:31', 1, '2024-03-22 20:47:31', 0);
INSERT INTO `comment` VALUES (49, '0', 2, -1, '会换行', -1, -1, 1, '2024-03-22 21:21:09', 1, '2024-03-22 21:21:09', 0);
INSERT INTO `comment` VALUES (50, '0', 2, -1, 'hhh', -1, -1, 1, '2024-03-22 22:59:46', 1, '2024-03-22 22:59:46', 0);
INSERT INTO `comment` VALUES (51, '0', 2, -1, '发送', -1, -1, 1, '2024-03-22 23:25:28', 1, '2024-03-22 23:25:28', 0);
INSERT INTO `comment` VALUES (52, '0', 2, 42, '的方式水水水水水水水水水水水水水水水水士大夫发生发射点发射点发射点法大师傅', 9, 43, 1, '2024-03-22 23:25:50', 1, '2024-03-22 23:25:50', 0);
INSERT INTO `comment` VALUES (53, '0', 2, -1, '？？？', -1, -1, 1, '2024-03-23 18:33:27', 1, '2024-03-23 18:33:27', 0);
INSERT INTO `comment` VALUES (54, '0', 1, -1, 'asd', -1, -1, 1, '2024-03-23 23:48:09', 1, '2024-03-23 23:48:09', 0);
INSERT INTO `comment` VALUES (55, '0', 2, -1, 'ddd', -1, -1, 1, '2024-03-25 23:43:16', NULL, NULL, 0);
INSERT INTO `comment` VALUES (56, '0', 2, 55, 'ddd', 1, 55, 1, '2024-03-25 23:43:19', NULL, NULL, 0);
INSERT INTO `comment` VALUES (57, '0', 2, -1, 'kkk', -1, -1, 1, '2024-03-26 20:00:31', NULL, NULL, 0);
INSERT INTO `comment` VALUES (58, '0', 1, 54, '撒', 1, 54, 1, '2024-03-26 20:41:37', NULL, NULL, 0);
INSERT INTO `comment` VALUES (59, '0', 3, -1, '大', -1, -1, 1, '2024-03-27 21:49:18', NULL, NULL, 0);
INSERT INTO `comment` VALUES (60, '0', 3, 59, 'adas', 1, 59, 1, '2024-03-28 20:01:04', NULL, NULL, 0);
INSERT INTO `comment` VALUES (61, '0', 3, 59, '啥？\n', 1, 60, 1, '2024-03-28 20:01:14', NULL, NULL, 0);
INSERT INTO `comment` VALUES (62, '0', 5, -1, '哟西', -1, -1, 1, '2024-03-30 11:37:16', NULL, NULL, 0);
INSERT INTO `comment` VALUES (63, '0', 5, 62, '你的，八格牙u路思密达', 1, 62, 1, '2024-03-30 11:37:29', NULL, NULL, 0);
INSERT INTO `comment` VALUES (64, '0', 4, -1, '的发放阿发阿斯弗', -1, -1, 1, '2024-03-30 16:55:17', NULL, NULL, 0);
INSERT INTO `comment` VALUES (65, '0', 4, -1, '士大夫', -1, -1, 7, '2024-03-31 21:26:42', NULL, NULL, 0);
INSERT INTO `comment` VALUES (66, '0', 3, 59, '哦哈哟，奥利将', 1, 60, 1, '2024-04-01 17:22:46', NULL, NULL, 0);
INSERT INTO `comment` VALUES (67, '0', 3, -1, '会换行', -1, -1, 1, '2024-04-01 17:23:07', NULL, NULL, 0);
INSERT INTO `comment` VALUES (69, '0', 4, -1, '阿松大', -1, -1, 1, '2024-04-02 14:44:22', 1, '2024-04-02 14:44:22', 0);
INSERT INTO `comment` VALUES (70, '0', 4, -1, '阿松大', -1, -1, 1, '2024-04-02 14:54:30', 1, '2024-04-02 14:54:30', 0);
INSERT INTO `comment` VALUES (71, '0', 4, -1, '？？？', -1, -1, 1, '2024-04-02 14:57:44', 1, '2024-04-02 14:57:44', 0);
INSERT INTO `comment` VALUES (72, '0', 4, -1, '在v', -1, -1, 1, '2024-04-02 14:59:04', 1, '2024-04-02 14:59:04', 0);
INSERT INTO `comment` VALUES (73, '0', 5, 62, '噫噫噫\n', 1, 63, 1, '2024-04-02 15:02:22', 1, '2024-04-02 15:02:22', 0);
INSERT INTO `comment` VALUES (74, '0', 4, 64, '哟西\n', 1, 64, 1, '2024-04-02 17:08:39', 1, '2024-04-02 17:08:39', 0);
INSERT INTO `comment` VALUES (77, '1', 1, -1, '阿松大', -1, -1, 1, '2024-04-02 17:23:01', 1, '2024-04-02 17:23:01', 0);
INSERT INTO `comment` VALUES (78, '1', 1, 77, '以西', 1, 77, 1, '2024-04-02 17:23:37', 1, '2024-04-02 17:23:37', 0);
INSERT INTO `comment` VALUES (79, '1', 1, 77, '八个压力', 1, 78, 1, '2024-04-02 17:27:58', 1, '2024-04-02 17:27:58', 0);
INSERT INTO `comment` VALUES (80, '1', 3, -1, '这个狼真不错', -1, -1, 1, '2024-04-02 17:29:25', 1, '2024-04-02 17:29:25', 0);
INSERT INTO `comment` VALUES (81, '1', 1, -1, '？？\n', -1, -1, 1, '2024-04-02 17:42:12', 1, '2024-04-02 17:42:12', 0);
INSERT INTO `comment` VALUES (82, '1', 2, -1, '今天干啥勒', -1, -1, 1, '2024-04-03 00:47:30', 1, '2024-04-03 00:47:30', 0);
INSERT INTO `comment` VALUES (83, '0', 5, -1, '天空一声巨响，duck三辆登场', -1, -1, 21, '2024-04-03 11:08:51', 21, '2024-04-03 11:08:51', 0);
INSERT INTO `comment` VALUES (84, '0', 1, -1, '斤斤计较\n', -1, -1, 1, '2024-04-03 14:52:04', 1, '2024-04-03 14:52:04', 0);
INSERT INTO `comment` VALUES (85, '0', 1, 84, '哈哈哈\n', 1, 84, 1, '2024-04-03 14:52:11', 1, '2024-04-03 14:52:11', 0);
INSERT INTO `comment` VALUES (86, '0', 3, -1, '？？\n', -1, -1, 6, '2024-04-05 23:18:08', 6, '2024-04-05 23:18:08', 0);
INSERT INTO `comment` VALUES (87, '0', 4, -1, '很好\n', -1, -1, 6, '2024-04-05 23:32:47', 6, '2024-04-05 23:32:47', 0);
INSERT INTO `comment` VALUES (88, '0', 4, -1, 'asda ', -1, -1, 1, '2024-04-11 15:56:21', 1, '2024-04-11 15:56:21', 0);
INSERT INTO `comment` VALUES (89, '0', 4, 88, 'asdas', 1, 88, 1, '2024-04-11 15:56:25', 1, '2024-04-11 15:56:25', 0);

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer`  (
  `ID` bigint NOT NULL AUTO_INCREMENT COMMENT '主键唯一标识',
  `STUDIO_ID` mediumtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '影楼id',
  `USERNAME` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `PASSWORD` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '密码',
  `USER_EMAIL` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `PHONE_NUMBER` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '电话',
  `SEX` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '性别',
  `AVATAR` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '头像',
  `STATUS` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '状态',
  `CREATED_BY` bigint NULL DEFAULT NULL COMMENT '创建人',
  `CREATED_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATED_BY` bigint NULL DEFAULT NULL COMMENT '更新人',
  `UPDATED_TIME` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `REMARK` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  `DEL_FLAG` int NULL DEFAULT 0 COMMENT '逻辑删除标志（0正常 1删除）',
  `NICK_NAME` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '昵称',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '客服表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES (1, '1', 'cu1', '$2a$10$Jnq31rRkNV3RNzXe0REsEOSKaYK8UgVZZqlNlNXqn.JeVcj2NdeZy', 'cu12131', '31', '0', 'http://qixiaopang.top/2024/03/26/6fb57b47c5c1403c863e74e8d3ca75a9.jpg', '0', NULL, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO `customer` VALUES (2, '1', 'cu2', '$2a$10$Jnq31rRkNV3RNzXe0REsEOSKaYK8UgVZZqlNlNXqn.JeVcj2NdeZy', 'cu22131', '123', '1', 'http://qixiaopang.top/2024/03/26/6fb57b47c5c1403c863e74e8d3ca75a9.jpg', '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for menus
-- ----------------------------
DROP TABLE IF EXISTS `menus`;
CREATE TABLE `menus`  (
  `MENU_ID` bigint NOT NULL AUTO_INCREMENT COMMENT '主键唯一标识',
  `MENU_NAME` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `PARENT_ID` bigint NULL DEFAULT NULL COMMENT '父菜单id，标识菜单的层次结构',
  `PATH` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '路由地址',
  `COMPONENT` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '组件地址',
  `IS_FRAME` int NULL DEFAULT NULL COMMENT '是否为外链（0是 1否）',
  `MENU_TYPE` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `VISIBLE` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '菜单状态（0显示 1隐藏）',
  `STATUS` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '状态（0正常 1停用）',
  `PERMS` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `ICON` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '图标',
  `CREATED_BY` bigint NULL DEFAULT NULL COMMENT '创建人',
  `CREATED_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATED_BY` bigint NULL DEFAULT NULL COMMENT '更新人',
  `UPDATED_TIME` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `REMARK` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  `DEL_FLAG` int NULL DEFAULT 0 COMMENT '逻辑删除标志',
  PRIMARY KEY (`MENU_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menus
-- ----------------------------
INSERT INTO `menus` VALUES (1, '11', 1, '/users/index', 'index', 1, 'F', '0', '0', 'user:admin', NULL, 1, '2024-03-09 23:56:56', 1, '2024-03-09 23:56:59', 'Wu', 0);
INSERT INTO `menus` VALUES (2, '11', 1, '/user/res', 'index', 1, 'F', '0', '0', 'user:normal', NULL, 1, '2024-03-17 20:06:37', 1, '2024-03-17 20:06:40', NULL, 0);

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `ID` bigint NOT NULL AUTO_INCREMENT COMMENT '主键标识',
  `EMAIL` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `CONTENT` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '消息',
  `CREATED_BY` bigint NULL DEFAULT NULL COMMENT '创建人',
  `CREATED_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATED_BY` bigint NULL DEFAULT NULL COMMENT '更新人',
  `UPDATED_TIME` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `REMARK` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  `DEL_FLAG` int NULL DEFAULT 0 COMMENT '逻辑删除标志（0正常、1删除）',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '留言表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (1, '2908947100@qq.com', '便宜点呗', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `message` VALUES (2, 'zhudake321@163.com', 'hhhh', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `message` VALUES (3, '2908947100@qq.com', 'asdasd', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `message` VALUES (4, 'zhudake321@163.com', 'yhdfgh', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `message` VALUES (5, '2908947100@qq.com', '嘎嘎嘎嘎嘎嘎嘎', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `message` VALUES (6, '2908947100@qq.com', '阿大撒', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `message` VALUES (7, '2908947100@qq.com', '大苏打', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `message` VALUES (8, '2908947100@qq.com', '阿松大', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `message` VALUES (9, '2908947100@qq.com', 'aDSa', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `message` VALUES (10, '2908947100@qq.com', '打算', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `message` VALUES (11, 'zhudake321@163.com', '21312', NULL, NULL, NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for photographers
-- ----------------------------
DROP TABLE IF EXISTS `photographers`;
CREATE TABLE `photographers`  (
  `ID` bigint NOT NULL AUTO_INCREMENT COMMENT '主键唯一标识;主键id',
  `PHOTOGRAPHER_NAME` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '摄影师姓名',
  `STYLE` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '摄影师风格特点',
  `PHOTOGRAPHER_EMAIL` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '摄影师邮箱地址',
  `DESCRIPTION` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '摄影师介绍',
  `PHOTO` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '摄影师照片',
  `SEX` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '0' COMMENT '性别',
  `STATUS` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '状态',
  `CREATED_BY` bigint NULL DEFAULT NULL COMMENT '创建人',
  `CREATED_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATED_BY` bigint NULL DEFAULT NULL COMMENT '更新人',
  `UPDATED_TIME` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `REMARK` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  `DEL_FLAG` int NULL DEFAULT 0 COMMENT '逻辑删除标志（0正常 1删除）',
  `AGE` int NULL DEFAULT 18 COMMENT '年龄',
  `LIKES` bigint NULL DEFAULT NULL COMMENT '点赞量',
  `EXPERIENCE` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '生涯',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '摄影师表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of photographers
-- ----------------------------
INSERT INTO `photographers` VALUES (1, 'zhudake', '清新，日系', 'zhudake321@163.com', '\"专业摄影师，捕捉瞬间美丽。\"', 'https://cdn.pixabay.com/photo/2021/07/05/09/34/man-6388637_640.jpg', '1', '0', 1, '2024-03-14 20:23:20', -1, '2024-04-11 22:40:00', '牛', 0, 18, 112, '自然，摄影师的职业生涯是一段充满激情和创造力的旅程。我一直被摄影的魅力所吸引，因此选择了这个职业。我的摄影生涯始于年少时期，当时我用简单的相机捕捉身边的美好瞬间。随着时间的推移，我不断学习和成长，探索各种摄影风格和技术。我发现，摄影不仅仅是记录图像，更是表达情感和故事的方式。\r\n\r\n在我职业生涯的早期阶段，我尝试着拍摄各种不同的主题和场景，从风景到人物，从动物到建筑。逐渐地，我发现自己对人像摄影产生了特别的兴趣。通过与人交流，我学会了如何捕捉他们真实的情感和表情，让照片更富有生命力。\r\n\r\n随着技术的进步和经验的积累，我开始在商业摄影领域取得一些成就。我拍摄了许多商业活动、产品和广告，与各种客户合作，为他们呈现出独特而引人注目的形象。\r\n\r\n但即使在商业领域取得了一些成功，我仍然保持着对摄影艺术的热爱。我喜欢在自己的时间里探索新的拍摄主题和技术，保持创造力的源泉不竭。对我来说，摄影不仅是一种职业，更是一种生活方式，一种表达自我的方式。我期待着未来，继续在这个充满挑战和机遇的行业中不断成长和进步。');
INSERT INTO `photographers` VALUES (2, 'zhuxiaoke', '八个雅鹿', 'zhuxiaoke@163.com', '\"专业摄影师，捕捉瞬间美丽。\"', 'https://cdn.pixabay.com/photo/2017/04/18/11/47/marry-2238764_640.jpg', '0', '0', 1, '2024-03-14 20:24:38', -1, '2024-04-11 22:40:00', '牛', 0, 18, 104, '自然，摄影师的职业生涯是一段充满激情和创造力的旅程。我一直被摄影的魅力所吸引，因此选择了这个职业。我的摄影生涯始于年少时期，当时我用简单的相机捕捉身边的美好瞬间。随着时间的推移，我不断学习和成长，探索各种摄影风格和技术。我发现，摄影不仅仅是记录图像，更是表达情感和故事的方式。\r\n\r\n在我职业生涯的早期阶段，我尝试着拍摄各种不同的主题和场景，从风景到人物，从动物到建筑。逐渐地，我发现自己对人像摄影产生了特别的兴趣。通过与人交流，我学会了如何捕捉他们真实的情感和表情，让照片更富有生命力。\r\n\r\n随着技术的进步和经验的积累，我开始在商业摄影领域取得一些成就。我拍摄了许多商业活动、产品和广告，与各种客户合作，为他们呈现出独特而引人注目的形象。\r\n\r\n但即使在商业领域取得了一些成功，我仍然保持着对摄影艺术的热爱。我喜欢在自己的时间里探索新的拍摄主题和技术，保持创造力的源泉不竭。对我来说，摄影不仅是一种职业，更是一种生活方式，一种表达自我的方式。我期待着未来，继续在这个充满挑战和机遇的行业中不断成长和进步。');
INSERT INTO `photographers` VALUES (3, '张三', '八格亚努', 'zhangsan@163.com', '\"专业摄影师，捕捉瞬间美丽。\"', 'https://cdn.pixabay.com/photo/2023/10/18/06/03/wedding-8322982_640.jpg', '0', '0', 1, '2024-03-14 20:25:39', -1, '2024-04-11 22:40:00', '牛哇', 0, 18, 72, '自然，摄影师的职业生涯是一段充满激情和创造力的旅程。我一直被摄影的魅力所吸引，因此选择了这个职业。我的摄影生涯始于年少时期，当时我用简单的相机捕捉身边的美好瞬间。随着时间的推移，我不断学习和成长，探索各种摄影风格和技术。我发现，摄影不仅仅是记录图像，更是表达情感和故事的方式。\r\n\r\n在我职业生涯的早期阶段，我尝试着拍摄各种不同的主题和场景，从风景到人物，从动物到建筑。逐渐地，我发现自己对人像摄影产生了特别的兴趣。通过与人交流，我学会了如何捕捉他们真实的情感和表情，让照片更富有生命力。\r\n\r\n随着技术的进步和经验的积累，我开始在商业摄影领域取得一些成就。我拍摄了许多商业活动、产品和广告，与各种客户合作，为他们呈现出独特而引人注目的形象。\r\n\r\n但即使在商业领域取得了一些成功，我仍然保持着对摄影艺术的热爱。我喜欢在自己的时间里探索新的拍摄主题和技术，保持创造力的源泉不竭。对我来说，摄影不仅是一种职业，更是一种生活方式，一种表达自我的方式。我期待着未来，继续在这个充满挑战和机遇的行业中不断成长和进步。');

-- ----------------------------
-- Table structure for reviews
-- ----------------------------
DROP TABLE IF EXISTS `reviews`;
CREATE TABLE `reviews`  (
  `ID` bigint NOT NULL AUTO_INCREMENT COMMENT '主键唯一标识',
  `TYPE` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '类型',
  `ENTITY_ID` bigint NULL DEFAULT NULL COMMENT '所评论对象id',
  `ROOT_ID` bigint NULL DEFAULT NULL COMMENT '根评论id',
  `CONTENT` varchar(900) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '评论内容',
  `TO_COMMENT_USER_ID` bigint NULL DEFAULT NULL COMMENT '所回复目标评论的userid',
  `TO_COMMENT_ID` bigint NULL DEFAULT NULL COMMENT '回复目标评论id',
  `RATING` int NULL DEFAULT NULL COMMENT '评分（1-5）',
  `CREATED_BY` bigint NULL DEFAULT NULL COMMENT '创建人',
  `CREATED_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATED_BY` bigint NULL DEFAULT NULL COMMENT '更新人',
  `UPDATED_TIME` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `REMARK` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  `DEL_FLAG` int NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '评价表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of reviews
-- ----------------------------

-- ----------------------------
-- Table structure for role_menus
-- ----------------------------
DROP TABLE IF EXISTS `role_menus`;
CREATE TABLE `role_menus`  (
  `ID` bigint NOT NULL AUTO_INCREMENT COMMENT '主键唯一标识',
  `ROLE_ID` bigint NULL DEFAULT NULL COMMENT '角色id',
  `MENU_ID` bigint NULL DEFAULT NULL COMMENT '菜单id',
  `CREATED_BY` bigint NULL DEFAULT NULL COMMENT '创建人',
  `CREATED_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATED_BY` bigint NULL DEFAULT NULL COMMENT '更新人',
  `UPDATED_TIME` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `REMARK` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  `DEL_FLAG` int NULL DEFAULT 0 COMMENT '逻辑删除标志',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_menus
-- ----------------------------
INSERT INTO `role_menus` VALUES (1, 1, 1, 1, '2024-03-09 23:57:30', 1, '2024-03-09 23:57:33', '五', 0);
INSERT INTO `role_menus` VALUES (2, 2, 2, 1, '2024-03-17 20:05:11', 1, '2024-03-17 20:05:16', '无', 0);

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
  `ROLE_ID` bigint NOT NULL AUTO_INCREMENT COMMENT '主键唯一标识',
  `ROLE_NAME` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `ROLE_KEY` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '角色权限字符',
  `STATUS` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '角色状态（0正常 1停用）',
  `CREATED_BY` bigint NULL DEFAULT NULL COMMENT '创建人',
  `CREATED_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATED_BY` bigint NULL DEFAULT NULL COMMENT '更新人',
  `UPDATED_TIME` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `REMARK` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  `DEL_FLAG` int NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`ROLE_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES (1, '管理员', 'admin', '0', 1, '2024-03-09 23:54:19', 1, '2024-03-09 23:54:24', '超级管理员', 0);
INSERT INTO `roles` VALUES (2, '普通用户', 'normal', '0', 1, '2024-03-17 20:00:57', 1, '2024-03-17 20:01:02', '普通用户', 0);
INSERT INTO `roles` VALUES (3, '客服', 'customer', '0', 1, '2024-04-11 16:31:35', 1, '2024-04-11 16:31:38', '影楼客服', 0);

-- ----------------------------
-- Table structure for samples
-- ----------------------------
DROP TABLE IF EXISTS `samples`;
CREATE TABLE `samples`  (
  `ID` bigint NOT NULL AUTO_INCREMENT COMMENT '主键唯一标识',
  `STUDIO_ID` bigint NULL DEFAULT NULL COMMENT '影楼id',
  `IMAGE_URL` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '样片地址',
  `CREATED_BY` bigint NULL DEFAULT NULL COMMENT '创建人',
  `CREATED_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATED_BY` bigint NULL DEFAULT NULL COMMENT '更新人',
  `UPDATED_TIME` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `REMARK` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  `DEL_FLAG` int NULL DEFAULT 0 COMMENT '逻辑删除标志',
  `TYPE` int NULL DEFAULT NULL COMMENT '类型（0是影楼、1是摄影师）',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '影楼样片关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of samples
-- ----------------------------
INSERT INTO `samples` VALUES (1, 1, 'https://cdn.pixabay.com/photo/2017/04/18/11/46/marry-2238755_1280.jpg', 1, '2024-03-14 22:07:22', 1, '2024-03-14 22:07:26', NULL, 0, 0);
INSERT INTO `samples` VALUES (2, 1, 'https://cdn.pixabay.com/photo/2022/12/19/11/55/snapito-7665462_640.jpg', 1, '2024-03-14 22:08:01', 1, '2024-03-14 22:08:06', NULL, 0, 0);
INSERT INTO `samples` VALUES (3, 1, 'https://cdn.pixabay.com/photo/2015/05/11/10/54/moody-762257_640.jpg', 1, '2024-03-14 22:08:33', 1, '2024-03-14 22:08:40', NULL, 0, 0);
INSERT INTO `samples` VALUES (4, 1, 'https://cdn.pixabay.com/photo/2017/04/18/11/47/marry-2238759_640.jpg', 1, '2024-03-14 22:09:01', 1, '2024-03-14 22:09:07', NULL, 0, 0);
INSERT INTO `samples` VALUES (5, 2, 'https://cdn.pixabay.com/photo/2016/06/29/04/39/bride-1486004_640.jpg', 1, '2024-03-22 19:39:32', 1, '2024-03-22 19:39:35', NULL, 0, 0);
INSERT INTO `samples` VALUES (6, 2, 'https://cdn.pixabay.com/photo/2019/11/10/11/13/couple-4615557_640.jpg', 1, '2024-03-22 19:39:59', 1, '2024-03-22 19:40:03', NULL, 0, 0);
INSERT INTO `samples` VALUES (7, 2, 'https://cdn.pixabay.com/photo/2016/11/22/19/05/couple-1850073_640.jpg', 1, '2024-03-22 19:40:20', 1, '2024-03-22 19:40:21', NULL, 0, 0);
INSERT INTO `samples` VALUES (8, 3, 'https://cdn.pixabay.com/photo/2016/11/22/19/05/couple-1850073_640.jpg', 1, '2024-03-26 22:40:35', 1, '2024-03-26 22:40:45', NULL, 0, 0);
INSERT INTO `samples` VALUES (9, 3, 'https://cdn.pixabay.com/photo/2016/11/22/19/05/couple-1850073_640.jpg', 1, '2024-03-26 22:40:36', 1, '2024-03-26 22:40:46', NULL, 0, 0);
INSERT INTO `samples` VALUES (10, 3, 'https://cdn.pixabay.com/photo/2016/11/22/19/05/couple-1850073_640.jpg', 1, '2024-03-26 22:40:37', 1, '2024-03-26 22:40:47', NULL, 0, 0);
INSERT INTO `samples` VALUES (11, 4, 'https://cdn.pixabay.com/photo/2015/11/16/22/39/balloons-1046658_960_720.jpg', 1, '2024-03-26 22:43:35', 1, '2024-03-26 22:43:42', NULL, 0, 0);
INSERT INTO `samples` VALUES (12, 4, 'https://cdn.pixabay.com/photo/2016/10/16/13/44/young-woman-1745173_640.jpg', 1, '2024-03-26 22:43:36', 1, '2024-03-26 22:44:40', NULL, 0, 0);
INSERT INTO `samples` VALUES (13, 4, 'https://cdn.pixabay.com/photo/2020/04/17/12/28/cloud-5055011_640.jpg', 1, '2024-03-26 22:43:37', 1, '2024-03-26 22:43:43', NULL, 0, 0);
INSERT INTO `samples` VALUES (14, 5, 'https://cdn.pixabay.com/photo/2017/03/21/18/43/couple-2162950_640.jpg', 1, '2024-03-26 22:44:46', 1, '2024-03-26 22:44:51', NULL, 0, 0);
INSERT INTO `samples` VALUES (15, 5, 'https://cdn.pixabay.com/photo/2016/11/23/17/56/flowers-1854075_640.jpg', 1, '2024-03-26 22:44:47', 1, '2024-03-26 22:44:52', NULL, 0, 0);
INSERT INTO `samples` VALUES (16, 1, 'https://cdn.pixabay.com/photo/2016/11/23/17/55/beach-1854072_640.jpg', 1, '2024-03-26 22:44:48', 1, '2024-03-26 22:44:54', NULL, 0, 1);
INSERT INTO `samples` VALUES (17, 1, 'https://cdn.pixabay.com/photo/2016/11/26/23/45/dog-1861839_640.jpg', 1, '2024-04-02 16:40:15', 1, '2024-04-02 16:40:25', NULL, 0, 1);
INSERT INTO `samples` VALUES (18, 1, 'https://cdn.pixabay.com/photo/2016/10/12/02/56/girl-1733352_640.jpg', 1, '2024-04-02 16:40:17', 1, '2024-04-02 16:40:26', NULL, 0, 1);
INSERT INTO `samples` VALUES (19, 1, 'https://cdn.pixabay.com/photo/2016/11/22/20/10/dog-1850465_640.jpg', 1, '2024-04-02 16:40:18', 1, '2024-04-02 16:40:27', NULL, 0, 1);
INSERT INTO `samples` VALUES (20, 2, 'https://cdn.pixabay.com/photo/2019/07/30/05/53/dog-4372036_640.jpg', 1, '2024-04-02 16:47:31', 1, '2024-04-02 16:47:35', NULL, 0, 1);
INSERT INTO `samples` VALUES (21, 2, 'https://cdn.pixabay.com/photo/2020/03/31/19/20/dog-4988985_640.jpg', 1, '2024-04-02 16:47:26', 1, '2024-04-02 16:47:39', NULL, 0, 1);
INSERT INTO `samples` VALUES (22, 2, 'https://cdn.pixabay.com/photo/2020/06/30/22/34/dog-5357794_640.jpg', 1, '2024-04-02 16:48:09', 1, '2024-04-02 16:48:13', NULL, 0, 1);
INSERT INTO `samples` VALUES (23, 3, 'https://cdn.pixabay.com/photo/2019/09/17/14/24/wolf-4483675_640.jpg', 1, '2024-04-02 16:48:56', 1, '2024-04-02 16:49:01', NULL, 0, 1);
INSERT INTO `samples` VALUES (24, 3, 'https://cdn.pixabay.com/photo/2017/08/01/09/04/dog-2563759_640.jpg', 1, '2024-04-02 16:49:25', 1, '2024-04-02 16:49:29', NULL, 0, 1);
INSERT INTO `samples` VALUES (25, 3, 'https://cdn.pixabay.com/photo/2021/01/27/06/54/nova-scotia-duck-tolling-retriever-5953883_640.jpg', 1, '2024-04-02 16:50:27', 1, '2024-04-02 16:50:31', NULL, 0, 1);

-- ----------------------------
-- Table structure for studio_photographers
-- ----------------------------
DROP TABLE IF EXISTS `studio_photographers`;
CREATE TABLE `studio_photographers`  (
  `ID` bigint NOT NULL AUTO_INCREMENT COMMENT '主键唯一标识',
  `STUDIO_ID` bigint NULL DEFAULT NULL COMMENT '影楼id',
  `PHOTOGRAPHER_ID` bigint NULL DEFAULT NULL COMMENT '摄影师id',
  `CREATED_BY` bigint NULL DEFAULT NULL COMMENT '创建人',
  `CREATED_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATED_BY` bigint NULL DEFAULT NULL COMMENT '更新人',
  `UPDATED_TIME` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `REMARK` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  `DEL_FLAG` int NULL DEFAULT 0 COMMENT '逻辑删除标志',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '影楼摄影师关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of studio_photographers
-- ----------------------------
INSERT INTO `studio_photographers` VALUES (1, 1, 1, 1, '2024-03-14 20:26:33', 1, '2024-03-14 20:26:41', '牛', 0);
INSERT INTO `studio_photographers` VALUES (2, 1, 2, 1, '2024-03-14 20:27:03', 1, '2024-03-14 20:27:10', '牛', 0);
INSERT INTO `studio_photographers` VALUES (3, 1, 3, 1, '2024-03-14 20:27:36', 1, '2024-03-14 20:27:42', '牛', 0);
INSERT INTO `studio_photographers` VALUES (4, 2, 1, 1, '2024-03-14 20:27:57', 1, '2024-03-14 20:28:06', '牛', 0);
INSERT INTO `studio_photographers` VALUES (5, 2, 2, 1, '2024-03-14 20:28:30', 1, '2024-03-14 20:28:38', '牛', 0);
INSERT INTO `studio_photographers` VALUES (6, 2, 3, 1, '2024-03-14 20:28:59', 1, '2024-03-14 20:29:06', '牛', 0);
INSERT INTO `studio_photographers` VALUES (7, 3, 1, 1, '2024-03-26 22:45:25', 1, '2024-03-26 22:45:31', NULL, 0);
INSERT INTO `studio_photographers` VALUES (8, 3, 2, 1, '2024-03-26 22:45:26', 1, '2024-03-26 22:45:32', NULL, 0);
INSERT INTO `studio_photographers` VALUES (9, 3, 3, 1, '2024-03-26 22:45:27', 1, '2024-03-26 22:45:33', NULL, 0);
INSERT INTO `studio_photographers` VALUES (10, 4, 1, 1, '2024-03-26 22:45:53', 1, '2024-03-05 22:45:59', NULL, 0);
INSERT INTO `studio_photographers` VALUES (11, 4, 2, 1, '2024-03-26 22:45:54', 1, '2024-03-26 22:46:12', NULL, 0);
INSERT INTO `studio_photographers` VALUES (12, 4, 3, 1, '2024-03-26 22:45:55', 1, '2024-03-26 22:46:14', NULL, 0);
INSERT INTO `studio_photographers` VALUES (13, 5, 2, 1, '2024-03-26 22:46:35', 1, '2024-03-26 22:46:40', NULL, 0);
INSERT INTO `studio_photographers` VALUES (14, 5, 3, 1, '2024-03-26 22:46:36', 1, '2024-03-26 22:46:41', NULL, 0);

-- ----------------------------
-- Table structure for studios
-- ----------------------------
DROP TABLE IF EXISTS `studios`;
CREATE TABLE `studios`  (
  `ID` bigint NOT NULL AUTO_INCREMENT COMMENT '主键唯一标识',
  `STUDIO_NAME` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '影楼名称',
  `STUDIO_LOCATION` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '影楼地理位置',
  `STUDIO_SERVICE` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '影楼提供的服务项目',
  `STUDIO_THUMBNAIL` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '缩略图',
  `DESCRIPTION` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '描述',
  `STATUS` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '状态',
  `RATE` float UNSIGNED ZEROFILL NULL DEFAULT NULL COMMENT '打分',
  `START_TIME` datetime NULL DEFAULT NULL COMMENT '营业时间',
  `END_TIME` datetime NULL DEFAULT NULL COMMENT '营业结束时间',
  `MAX_RESERVATION` int NULL DEFAULT 1000 COMMENT '剩余最大与约数量',
  `CREATED_BY` bigint NULL DEFAULT NULL COMMENT '创建人',
  `CREATED_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATED_TIME` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `REMARK` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  `DEL_FLAG` int NULL DEFAULT 0 COMMENT '逻辑删除标志（0正常 1删除）',
  `UPDATED_BY` bigint NULL DEFAULT NULL COMMENT '更新人',
  `VIEW_COUNT` bigint NULL DEFAULT NULL COMMENT '浏览量',
  `COLLECT_COUNT` bigint NULL DEFAULT NULL COMMENT '收藏量',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '影楼表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of studios
-- ----------------------------
INSERT INTO `studios` VALUES (1, '影楼1', '贵州省遵义市凤冈县新建镇新建村', '化妆+摄影+修图', 'https://tse3-mm.cn.bing.net/th/id/OIP-C.BAyzqQrRDCIyjQTD4uHNiQHaEo?w=285&h=180&c=7&r=0&o=5&dpr=1.1&pid=1.7', '欢迎来到我们的影楼！我们是一家专业的影楼，致力于为客户提供高品质的摄影服务。无论您是要拍摄婚纱照、家庭照、个人写真还是其他特殊场合，我们都能满足您的需求。\r\n\r\n我们拥有一支经验丰富、技艺精湛的摄影团队，他们将用心捕捉每一个珍贵的瞬间，为您打造出令人惊艳的影像作品。我们采用最先进的摄影设备和后期处理技术，确保照片的质量达到最佳水平。\r\n\r\n除了专业的摄影服务外，我们还提供多样化的场景和服装选择，让您能够根据自己的喜好和需求进行选择，打造出独一无二的照片。而且我们的工作人员都经过专业培训，能够为您提供周到的服务和建议，让拍摄过程更加顺利愉快。\r\n\r\n无论您是想要留下美好的回忆，还是展示自己的风采，我们都会竭尽全力为您实现。欢迎您来到我们的影楼，让我们一同创造出令人难忘的影像之旅！', '0', 0000000004.5, '2024-03-22 09:00:00', '2024-03-22 18:00:00', 1000, 1, '2024-03-04 20:41:45', '2024-04-11 22:40:00', NULL, 0, -1, 1025, 1007);
INSERT INTO `studios` VALUES (2, '影楼2', '天津市西青区大学城朱大可摄影店', '摄影+修图', 'https://cdn.pixabay.com/photo/2016/11/22/20/11/photography-1850469_1280.jpg', '欢迎来到我们的影楼！我们是一家专业的影楼，致力于为客户提供高品质的摄影服务。无论您是要拍摄婚纱照、家庭照、个人写真还是其他特殊场合，我们都能满足您的需求。\r\n\r\n我们拥有一支经验丰富、技艺精湛的摄影团队，他们将用心捕捉每一个珍贵的瞬间，为您打造出令人惊艳的影像作品。我们采用最先进的摄影设备和后期处理技术，确保照片的质量达到最佳水平。\r\n\r\n除了专业的摄影服务外，我们还提供多样化的场景和服装选择，让您能够根据自己的喜好和需求进行选择，打造出独一无二的照片。而且我们的工作人员都经过专业培训，能够为您提供周到的服务和建议，让拍摄过程更加顺利愉快。\r\n\r\n无论您是想要留下美好的回忆，还是展示自己的风采，我们都会竭尽全力为您实现。欢迎您来到我们的影楼，让我们一同创造出令人难忘的影像之旅！', '0', 0000000004.7, '2024-03-22 09:00:00', '2024-03-22 18:00:00', 1004, 1, '2024-03-05 19:55:06', '2024-04-11 22:40:00', NULL, 0, -1, 1012, 1003);
INSERT INTO `studios` VALUES (3, '朱文二超级影楼', '天津市西青区大学城朱文二摄影店', '摄影+修图', 'https://cdn.pixabay.com/photo/2019/06/28/01/19/photo-studio-4303312_640.jpg', '欢迎来到我们的影楼！我们是一家专业的影楼，致力于为客户提供高品质的摄影服务。无论您是要拍摄婚纱照、家庭照、个人写真还是其他特殊场合，我们都能满足您的需求。\r\n\r\n我们拥有一支经验丰富、技艺精湛的摄影团队，他们将用心捕捉每一个珍贵的瞬间，为您打造出令人惊艳的影像作品。我们采用最先进的摄影设备和后期处理技术，确保照片的质量达到最佳水平。\r\n\r\n除了专业的摄影服务外，我们还提供多样化的场景和服装选择，让您能够根据自己的喜好和需求进行选择，打造出独一无二的照片。而且我们的工作人员都经过专业培训，能够为您提供周到的服务和建议，让拍摄过程更加顺利愉快。\r\n\r\n无论您是想要留下美好的回忆，还是展示自己的风采，我们都会竭尽全力为您实现。欢迎您来到我们的影楼，让我们一同创造出令人难忘的影像之旅！', '0', 0000000004.9, '2024-03-26 22:36:25', '2024-06-26 22:36:28', 1001, 1, '2024-03-26 22:36:42', '2024-04-11 22:40:00', NULL, 0, -1, 897, 1001);
INSERT INTO `studios` VALUES (4, '无敌牛逼影楼', '天津市西青区大学城张三摄影店', '化妆+摄影+修图', 'https://cdn.pixabay.com/photo/2015/03/12/14/37/studio-670283_640.jpg', '欢迎来到我们的影楼！我们是一家专业的影楼，致力于为客户提供高品质的摄影服务。无论您是要拍摄婚纱照、家庭照、个人写真还是其他特殊场合，我们都能满足您的需求。\r\n\r\n我们拥有一支经验丰富、技艺精湛的摄影团队，他们将用心捕捉每一个珍贵的瞬间，为您打造出令人惊艳的影像作品。我们采用最先进的摄影设备和后期处理技术，确保照片的质量达到最佳水平。\r\n\r\n除了专业的摄影服务外，我们还提供多样化的场景和服装选择，让您能够根据自己的喜好和需求进行选择，打造出独一无二的照片。而且我们的工作人员都经过专业培训，能够为您提供周到的服务和建议，让拍摄过程更加顺利愉快。\r\n\r\n无论您是想要留下美好的回忆，还是展示自己的风采，我们都会竭尽全力为您实现。欢迎您来到我们的影楼，让我们一同创造出令人难忘的影像之旅！', '0', 000000000005, '2024-03-26 22:37:50', '2024-08-26 22:37:52', 999, 1, '2024-03-26 22:38:02', '2024-04-11 22:40:00', NULL, 0, -1, 811, 1012);
INSERT INTO `studios` VALUES (5, '想不出来名儿', '天津市西青区大学城王麻子摄影店', '化妆+摄影+修图', 'https://cdn.pixabay.com/photo/2019/05/21/18/02/camera-4219677_640.jpg', '欢迎来到我们的影楼！我们是一家专业的影楼，致力于为客户提供高品质的摄影服务。无论您是要拍摄婚纱照、家庭照、个人写真还是其他特殊场合，我们都能满足您的需求。\r\n\r\n我们拥有一支经验丰富、技艺精湛的摄影团队，他们将用心捕捉每一个珍贵的瞬间，为您打造出令人惊艳的影像作品。我们采用最先进的摄影设备和后期处理技术，确保照片的质量达到最佳水平。\r\n\r\n除了专业的摄影服务外，我们还提供多样化的场景和服装选择，让您能够根据自己的喜好和需求进行选择，打造出独一无二的照片。而且我们的工作人员都经过专业培训，能够为您提供周到的服务和建议，让拍摄过程更加顺利愉快。\r\n\r\n无论您是想要留下美好的回忆，还是展示自己的风采，我们都会竭尽全力为您实现。欢迎您来到我们的影楼，让我们一同创造出令人难忘的影像之旅！', '0', 000000000005, '2024-03-26 22:39:27', '2024-04-26 22:39:29', 1000, 1, '2024-03-26 22:39:41', '2024-04-11 22:40:00', NULL, 0, -1, 681, 1002);

-- ----------------------------
-- Table structure for studios_package
-- ----------------------------
DROP TABLE IF EXISTS `studios_package`;
CREATE TABLE `studios_package`  (
  `ID` bigint NOT NULL AUTO_INCREMENT COMMENT '主键表示',
  `STUDIO_ID` bigint NULL DEFAULT NULL COMMENT '影楼id',
  `PACKAGE_NAME` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '套餐名称',
  `PACKAGE_DESC` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '套餐描述',
  `PACKAGE_PRICE` int NULL DEFAULT NULL COMMENT '套餐价格',
  `PACKAGE_DURATION` int NULL DEFAULT NULL COMMENT '套餐拍摄时长',
  `PACKAGE_INCLUDES` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '套餐服务项目',
  `PACKAGE_TYPE` int NULL DEFAULT NULL COMMENT '套餐区别（如摄影套餐、化妆造型套餐）',
  `PACKAGE_CAPACITY` int NULL DEFAULT NULL COMMENT '套餐使用最大人数',
  `IS_RECOMMEND` int NULL DEFAULT NULL COMMENT '是否推荐(0、1)',
  `PACKAGE_THUMBNAIL` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '套餐缩略图',
  `STATUS` int NULL DEFAULT NULL COMMENT '套餐状态（0正常、1停用）',
  `CREATED_BY` bigint NULL DEFAULT NULL COMMENT '创建人',
  `CREATED_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATED_BY` bigint NULL DEFAULT NULL COMMENT '更新人',
  `UPDATED_TIME` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `DEL_FLAG` int NULL DEFAULT 0 COMMENT '逻辑删除标志（0正常、1删除）',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '影楼套餐表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of studios_package
-- ----------------------------
INSERT INTO `studios_package` VALUES (1, 1, '套餐二', '尽享美好时刻，我们为您提供精美场景，专业摄影团队，打造永恒回忆。', 1000, 3, '包含室内外拍摄、精美婚纱礼服、专业化妆造型、个性化后期处理。', 1, 2, 0, 'https://cdn.pixabay.com/photo/2015/03/30/12/35/sunset-698501_640.jpg', 0, 1, '2024-03-23 16:03:48', 1, '2024-03-23 16:03:52', 0);
INSERT INTO `studios_package` VALUES (2, 1, '套餐二', '尽享美好时刻，我们为您提供精美场景，专业摄影团队，打造永恒回忆。', 2000, 4, '包含室内外拍摄、精美婚纱礼服', 1, 4, 0, 'https://cdn.pixabay.com/photo/2016/06/29/04/39/wedding-dresses-1486005_640.jpg', 0, 1, '2024-03-23 16:05:19', 1, '2024-03-23 16:05:25', 0);
INSERT INTO `studios_package` VALUES (3, 1, '套餐三', '尽享美好时刻，我们为您提供精美场景，专业摄影团队，打造永恒回忆。', 3000, 1, '包含室内外拍摄、精美婚纱礼服', 1, 2, 1, 'https://cdn.pixabay.com/photo/2016/10/16/13/44/young-woman-1745173_640.jpg', 0, 1, '2024-03-23 16:07:46', 1, '2024-03-23 16:07:49', 0);
INSERT INTO `studios_package` VALUES (4, 2, '高级套餐', '无敌牛逼八个雅鹿', 4000, 1, '包含室内外拍摄、精美婚纱礼服', 1, 2, 1, 'https://cdn.pixabay.com/photo/2016/06/29/04/17/wedding-dress-1485984_640.jpg', 0, 1, '2024-03-23 16:09:11', 1, '2024-03-23 16:09:14', 0);
INSERT INTO `studios_package` VALUES (5, 2, '中等套餐', '摩西摩西', 2999, 2, '包含室内外拍摄、精美婚纱礼服', 1, 2, 0, 'https://cdn.pixabay.com/photo/2016/11/14/04/25/bride-1822587_640.jpg', 0, 1, '2024-03-23 16:11:50', 1, '2024-03-23 16:11:58', 0);
INSERT INTO `studios_package` VALUES (6, 2, '基础套餐', '走过路过不要错误', 1999, 1, '包含室内外拍摄、精美婚纱礼服', 1, 2, 0, 'https://cdn.pixabay.com/photo/2016/11/29/04/28/wedding-1867318_640.jpg', 0, 1, '2024-03-23 16:11:52', 1, '2024-03-23 16:11:59', 0);
INSERT INTO `studios_package` VALUES (7, 3, '无敌套餐', '走过路过不要错误', 1111, 2, '包含室内外拍摄、精美婚纱礼服', 2, 2, 1, 'https://cdn.pixabay.com/photo/2016/11/29/04/28/wedding-1867318_640.jpg', 0, 1, '2024-03-26 22:48:17', 1, '2024-03-26 22:48:21', 0);

-- ----------------------------
-- Table structure for user_roles
-- ----------------------------
DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles`  (
  `ID` bigint NOT NULL AUTO_INCREMENT COMMENT '主键唯一标识',
  `USER_ID` bigint NULL DEFAULT NULL COMMENT '用户id',
  `ROLE_ID` bigint NULL DEFAULT NULL COMMENT '角色id',
  `CREATED_BY` bigint NULL DEFAULT NULL COMMENT '创建人',
  `CREATED_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATED_BY` bigint NULL DEFAULT NULL COMMENT '更新人',
  `UPDATED_TIME` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `REMARK` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  `DEL_FLAG` int NULL DEFAULT 0 COMMENT '逻辑删除标志',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_roles
-- ----------------------------
INSERT INTO `user_roles` VALUES (1, 1, 1, 1, '2024-03-09 23:55:08', 1, '2024-03-09 23:55:12', '第一条数据', 0);
INSERT INTO `user_roles` VALUES (2, 2, 1, 1, '2024-03-17 18:46:35', 1, '2024-03-17 18:46:39', NULL, 0);
INSERT INTO `user_roles` VALUES (3, 6, 2, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `user_roles` VALUES (4, 7, 2, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `user_roles` VALUES (5, 8, 2, -1, '2024-03-21 22:31:59', NULL, NULL, NULL, 0);
INSERT INTO `user_roles` VALUES (6, 9, 2, -1, '2024-03-22 11:27:11', NULL, NULL, NULL, 0);
INSERT INTO `user_roles` VALUES (7, 10, 2, -1, '2024-03-22 11:50:24', NULL, NULL, NULL, 0);
INSERT INTO `user_roles` VALUES (8, 11, 2, -1, '2024-03-22 20:17:43', NULL, NULL, NULL, 0);
INSERT INTO `user_roles` VALUES (9, 12, 2, -1, '2024-03-22 20:18:08', NULL, NULL, NULL, 0);
INSERT INTO `user_roles` VALUES (10, 13, 2, -1, '2024-03-22 20:18:27', NULL, NULL, NULL, 0);
INSERT INTO `user_roles` VALUES (11, 14, 2, -1, '2024-03-22 20:20:54', NULL, NULL, NULL, 0);
INSERT INTO `user_roles` VALUES (12, 15, 2, -1, '2024-03-22 20:21:26', NULL, NULL, NULL, 0);
INSERT INTO `user_roles` VALUES (13, 16, 2, -1, '2024-03-22 20:23:52', NULL, NULL, NULL, 0);
INSERT INTO `user_roles` VALUES (14, 17, 2, -1, '2024-03-22 20:31:56', NULL, NULL, NULL, 0);
INSERT INTO `user_roles` VALUES (15, 18, 2, -1, '2024-03-22 20:32:56', NULL, NULL, NULL, 0);
INSERT INTO `user_roles` VALUES (16, 19, 2, -1, '2024-03-22 20:33:23', NULL, NULL, NULL, 0);
INSERT INTO `user_roles` VALUES (17, 20, 2, -1, '2024-03-22 20:48:47', NULL, NULL, NULL, 0);
INSERT INTO `user_roles` VALUES (18, 21, 2, -1, '2024-04-03 11:07:53', -1, '2024-04-03 11:07:53', NULL, 0);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `ID` bigint NOT NULL AUTO_INCREMENT COMMENT '主键唯一标识',
  `USERNAME` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `PASSWORD` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '密码',
  `USER_EMAIL` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `PHONE_NUMBER` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '电话',
  `SEX` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
  `AVATAR` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `NICK_NAME` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `STATUS` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '0正常 1停用',
  `CREATED_BY` bigint NULL DEFAULT NULL COMMENT '创建人',
  `CREATED_TIME` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATED_BY` bigint NULL DEFAULT NULL COMMENT '更新人',
  `UPDATED_TIME` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `REMARK` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  `DEL_FLAG` int NULL DEFAULT 0 COMMENT '逻辑删除标志（0正常 1删除）',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'zhudake', '$2a$10$Jnq31rRkNV3RNzXe0REsEOSKaYK8UgVZZqlNlNXqn.JeVcj2NdeZy', 'zhudake312@163.com', '18166906472', '0', 'http://qixiaopang.top/2024/03/26/6fb57b47c5c1403c863e74e8d3ca75a9.jpg', '朱大可', '0', 1, '2024-03-09 23:39:03', 1, '2024-03-26 20:18:16', '牛', 0);
INSERT INTO `users` VALUES (6, 'zhuwener', '$2a$10$3cgr6jE1mn8bcVud0aVDRugglc0zRnHiLpFgHoWKI0n9mN2H0xcKO', 'zhuwener@163.com', '15485475487', '1', 'http://qixiaopang.top/2023/12/26/69338426214c457d9c810b2183393b3c.png', '文二', '0', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `users` VALUES (7, 'zhangsan', '$2a$10$INf16yxTuOJzt5kR.bvAdej4USCBlWLmV4bBYN7mOIViwruJtfNVu', 'zhangsan@163.com', '15122458746', '0', 'http://qixiaopang.top/2023/12/26/69338426214c457d9c810b2183393b3c.png', '王老五', '0', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `users` VALUES (8, 'liuwu', '$2a$10$9nbSUiD0ijIKsM90JkIKoe4dSOyuOxy1JoycD/AV3QJqT1EXORETe', 'zhudake312@qq.com', '18166906478', '0', 'http://qixiaopang.top/2023/12/26/69338426214c457d9c810b2183393b3c.png', '王老五', '0', -1, '2024-03-21 22:31:59', NULL, NULL, NULL, 0);
INSERT INTO `users` VALUES (9, 'wangwu', '$2a$10$bl16F4mMgYZ5fDtM9yvXEukjlk4095QXNOJtO/5SGlAh1SPldz7py', 'wangwu@163.com', '18166906473', '', 'http://qixiaopang.top/2023/12/26/69338426214c457d9c810b2183393b3c.png', '王老五', '0', -1, '2024-03-22 11:27:11', NULL, NULL, NULL, 0);
INSERT INTO `users` VALUES (10, 'zhudale', '$2a$10$hIjfBBC1DKbRoMuUl1ESiO1hNolkPfMHhozc.Snn.1ev8HiJt6LSe', 'zhudake321@163.com', '', '1', 'http://qixiaopang.top/2023/12/26/69338426214c457d9c810b2183393b3c.png', '王老五', '0', -1, '2024-03-22 11:50:24', NULL, NULL, NULL, 0);
INSERT INTO `users` VALUES (11, 'wangmazi', '$2a$10$9t5JdeAa9ZFyH/6aHLUpg.GfLKBIXvtDa/vVDlM59TlzJ5Nu.RX/q', 'wangmazi@163.com', '18166906480', '0', 'http://qixiaopang.top/2023/12/26/69338426214c457d9c810b2183393b3c.png', '王老五', '0', -1, '2024-03-22 20:17:43', NULL, NULL, NULL, 0);
INSERT INTO `users` VALUES (12, 'wangmazi1', '$2a$10$Hx/dHL/J0eF.cBjzXRwudukXyqB63lsqZDn2B/rXqTUqC/20cgEIi', 'wangmazi1@163.com', '18166906481', '0', 'http://qixiaopang.top/2023/12/26/69338426214c457d9c810b2183393b3c.png', '王老五', '0', -1, '2024-03-22 20:18:08', NULL, NULL, NULL, 0);
INSERT INTO `users` VALUES (13, 'wangmazi2', '$2a$10$NNBXJIvPHTQSV9TiNV1rJOABzdQtfXZN2AGocv/HV440PPWFNyn7q', 'wangmazi1@163.com', '18166906482', '0', 'http://qixiaopang.top/2023/12/26/69338426214c457d9c810b2183393b3c.png', '王老五', '0', -1, '2024-03-22 20:18:26', NULL, NULL, NULL, 0);
INSERT INTO `users` VALUES (14, 'asas', '$2a$10$ql.M3wZs71.GiVXzWPyxr.Y95rNyrDiM3mYoL41BpdC3kdd3aRceq', 'zhudake@163.com', '', '0', 'http://qixiaopang.top/2023/12/26/69338426214c457d9c810b2183393b3c.png', '王老五', '0', -1, '2024-03-22 20:20:54', NULL, NULL, NULL, 0);
INSERT INTO `users` VALUES (15, 'adas', '$2a$10$5PlUIsQx0jJ2mAjEpcTStOaJAFQxrjz.XS.psMYICt78VKYGTXCqO', 'zhudake@163.com', '', '0', 'http://qixiaopang.top/2023/12/26/69338426214c457d9c810b2183393b3c.png', '王老五', '0', -1, '2024-03-22 20:21:26', NULL, NULL, NULL, 0);
INSERT INTO `users` VALUES (16, 'asdasd', '$2a$10$vPwLAe3sTyPx3YPPDg4g3OyRoU0kVkgS0YQTA.3MpisgREdLJrsu2', 'zhudake@163.com', '', '0', 'http://qixiaopang.top/2023/12/26/69338426214c457d9c810b2183393b3c.png', '王老五', '0', -1, '2024-03-22 20:23:52', NULL, NULL, NULL, 0);
INSERT INTO `users` VALUES (17, 'asdasdfdasd', '$2a$10$jH.RFqgqcbM4JqM/RNG72eOB9jK0tiMAYHBYbMa4phsMenF.VAIya', 'zhudake@163.com', '', '0', 'http://qixiaopang.top/2023/12/26/69338426214c457d9c810b2183393b3c.png', '王老五', '0', -1, '2024-03-22 20:31:56', NULL, NULL, NULL, 0);
INSERT INTO `users` VALUES (18, 'asdasdasda', '$2a$10$/lPX.IJstpIXsVWxczIzHObKLUAMOZQ5NiXpaTmQQI3ECZb2CKIVK', 'zhudake@163.com', '', '1', 'http://qixiaopang.top/2023/12/26/69338426214c457d9c810b2183393b3c.png', '王老五', '0', -1, '2024-03-22 20:32:56', NULL, NULL, NULL, 0);
INSERT INTO `users` VALUES (19, 'asdasdasdasd', '$2a$10$y7QrhmZcp0ROarf84wAXieuyJanK8Xg93MjlzzrHwN/oMdi/JsLxm', 'wangwu@163.com', '', '1', 'http://qixiaopang.top/2023/12/26/69338426214c457d9c810b2183393b3c.png', '王老五', '0', -1, '2024-03-22 20:33:23', NULL, NULL, NULL, 0);
INSERT INTO `users` VALUES (20, '牛二娘', '$2a$10$G3gOhbDdSPdPqwT/fPlBZ.WSKZUgeM.nmt/hjI2BaAPD6PAfF3qHm', 'zhudake@163.com', '18166906483', '0', 'http://qixiaopang.top/2024/03/22/15768f31e734435fbdfdd920548b633a.jpg', '牛二娘', '0', -1, '2024-03-22 20:48:47', NULL, NULL, NULL, 0);
INSERT INTO `users` VALUES (21, 'duck', '$2a$10$9RZiCYv0wF1rSi7.gGMuP.FWl61jwddiQMW/Al8prt83xC9/VW9YG', 'duck321@163.com', '12215457854', '0', 'http://qixiaopang.top/2023/12/26/69338426214c457d9c810b2183393b3c.png', '王老五', '0', -1, '2024-04-03 11:07:53', -1, '2024-04-03 11:07:53', NULL, 0);

SET FOREIGN_KEY_CHECKS = 1;

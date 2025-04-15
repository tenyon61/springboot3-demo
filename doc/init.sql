/*
 Navicat Premium Dump SQL

 Source Server         : mysql8.4.4
 Source Server Type    : MySQL
 Source Server Version : 80404 (8.4.4)
 Source Host           : 127.0.0.1:3306
 Source Schema         : demo

 Target Server Type    : MySQL
 Target Server Version : 80404 (8.4.4)
 File Encoding         : 65001

 Date: 15/04/2025 14:11:41
*/

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`            bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户id',
    `account`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '账号',
    `password`      varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
    `name`          varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户昵称',
    `avatar`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户头像',
    `email`         varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
    `phone`         varchar(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '手机号',
    `profile`       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户简介',
    `sex`           tinyint NULL DEFAULT NULL COMMENT '性别 0为男性，1为女性',
    `open_id`       char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '微信openid用户标识',
    `active_status` tinyint NULL DEFAULT 2 COMMENT '在线状态 1在线 2离线',
    `last_opt_time` datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后上下线时间',
    `ip_info`       json NULL COMMENT 'ip信息',
    `item_id`       bigint NULL DEFAULT NULL COMMENT '佩戴的徽章id',
    `user_role`     varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'user' COMMENT '用户角色：user/admin/ban',
    `status`        int NULL DEFAULT 0 COMMENT '使用状态 0.正常 1拉黑',
    `create_time`   datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `is_delete`     tinyint                                                      NOT NULL DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uniq_open_id`(`open_id` ASC) USING BTREE,
    UNIQUE INDEX `uniq_name`(`name` ASC) USING BTREE,
    INDEX           `idx_create_time`(`create_time` ASC) USING BTREE,
    INDEX           `idx_update_time`(`update_time` ASC) USING BTREE,
    INDEX           `idx_active_status_last_opt_time`(`active_status` ASC, `last_opt_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1908127282626453506 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user`
VALUES (1, 'admin', '492a65bef0ab2fac75758f004f3eaf35', '管理员', 'https://api.oss.cqbo.com/tenyon/assets/default.png',
        NULL, NULL, 'admin', NULL, NULL, 2, '2025-04-03 12:30:09', NULL, NULL, 'admin', 0, '2025-04-03 12:30:09',
        '2025-04-15 14:11:28', 0);
INSERT INTO `user`
VALUES (2, 'user', '492a65bef0ab2fac75758f004f3eaf35', '用户', 'https://api.oss.cqbo.com/tenyon/assets/default.png',
        NULL, NULL, 'user', NULL, NULL, 2, '2025-04-15 14:11:12', NULL, NULL, 'user', 0, '2025-04-15 14:11:12',
        '2025-04-15 14:11:26', 0);

SET
FOREIGN_KEY_CHECKS = 1;

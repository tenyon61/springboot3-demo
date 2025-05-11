/*
 Navicat Premium Dump SQL

 Source Server         : mysql8
 Source Server Type    : MySQL
 Source Server Version : 80405 (8.4.5)
 Source Host           : 127.0.0.1:3306
 Source Schema         : demo

 Target Server Type    : MySQL
 Target Server Version : 80405 (8.4.5)
 File Encoding         : 65001

 Date: 16/04/2025 17:12:59
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
    `id`            bigint                                                        NOT NULL AUTO_INCREMENT COMMENT 'id',
    `userAccount`   varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账号',
    `userPassword`  varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
    `unionId`       varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '微信开放平台id',
    `mpOpenId`      varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '公众号openId',
    `userName`      varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户昵称',
    `userAvatar`    varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户头像',
    `userProfile`   varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户简介',
    `sex`           tinyint NULL DEFAULT NULL COMMENT '性别 0为男性，1为女性',
    `email`         varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
    `phone`         varchar(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '手机号',
    `userRole`      varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL DEFAULT 'user' COMMENT '用户角色：user/admin/ban',
    `vipNumber`     bigint NULL DEFAULT NULL COMMENT '会员编号',
    `vipCode`       varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '会员兑换码',
    `vipExpireTime` datetime NULL DEFAULT NULL COMMENT '会员过期时间',
    `shareCode`     varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '分享码',
    `inviteUser`    bigint NULL DEFAULT NULL COMMENT '邀请用户id',
    `editTime`      datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '编辑时间',
    `createTime`    datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updateTime`    datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `isDelete`      tinyint                                                       NOT NULL DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX           `idx_unionId`(`unionId` ASC) USING BTREE,
    INDEX           `uk_userAccount`(`userAccount` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1877314116692004867 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '系统-用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user`
VALUES (1, 'admin', '492a65bef0ab2fac75758f004f3eaf35', '', 'mpOpenId', '系统管理员',
        'https://api.oss.cqbo.com/tenyon/assets/default.png', 'admin', 0, 'tenyon@cqbo.com', '13713173611', 'admin',
        NULL, NULL, NULL, NULL, NULL, '2024-11-28 14:50:35', '2024-11-28 14:50:35', '2025-04-16 16:20:31', 0);
INSERT INTO `user`
VALUES (2, 'user', '492a65bef0ab2fac75758f004f3eaf35', '', '', '用户',
        'https://api.oss.cqbo.com/tenyon/assets/default.png', 'user', 1, NULL, NULL, 'user', NULL, NULL, NULL, NULL,
        NULL, '2024-11-28 14:50:35', '2024-11-28 14:50:35', '2025-04-16 16:23:34', 0);

SET
FOREIGN_KEY_CHECKS = 1;

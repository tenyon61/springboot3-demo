package com.tenyon.web.user.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 */
@TableName(value = "user")
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements Serializable {
    /**
     * 用户id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户昵称
     */
    private String name;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机
     */
    private String phone;

    /**
     * 简介
     */
    private String profile;

    /**
     * 性别 1为男性，2为女性
     */
    private Integer sex;

    /**
     * 微信openid用户标识
     */
    private String openId;

    /**
     * 在线状态 1在线 2离线
     */
    private Integer activeStatus;

    /**
     * 最后上下线时间
     */
    private Date lastOptTime;

    /**
     * ip信息
     */
    private Object ipInfo;

    /**
     * 佩戴的徽章id
     */
    private Long itemId;

    /**
     * 用户角色：user/admin/ban
     */
    private String userRole;

    /**
     * 使用状态 0.正常 1拉黑
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    @Serial
    private static final long serialVersionUID = 1L;
}
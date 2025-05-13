package com.tenyon.common.constant;

/**
 * 用户常量
 */
public interface UserConstant {

    /**
     * 用户登录态键
     */
    String USER_LOGIN_STATE = "user_login";

    //  region 权限

    /**
     * 默认角色
     */
    String DEFAULT_ROLE_KEY = "user";

    /**
     * 管理员角色
     */
    String ADMIN_ROLE_KEY = "admin";

    /**
     * 会员
     */
    String VIP_ROLE_KEY = "vip";

    /**
     * 超级会员
     */
    String SVIP_ROLE_KEY = "svip";

    /**
     * 被封号
     */
    String BAN_ROLE_KEY = "ban";

    // endregion
}

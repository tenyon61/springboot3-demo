package com.tenyon.web.common.constant;

/**
 * 通用常量
 */
public interface BmsConstant {

    /**
     * 1
     */
    Integer ONE = 1;

    /**
     * 0
     */
    Integer ZERO = 0;

    /**
     * 10
     */
    Integer TEN = 10;

    /**
     * 6
     */
    Integer six = 6;

    /**
     * 12
     */
    Integer twelve = 12;

    /**
     * 加密盐
     */
    String ENCRYPT_SALT = "abcdefg";

    /**
     * 密码的正则
     */
    String REGEX_PASSWORD = "^(?=.*\\d)(?=.*[a-zA-Z])[\\da-zA-Z~!@#$^&*_.]{8,18}$|^11111$";

    /**
     * 邮箱的正则
     */
    String REGEX_EMAIL = "^[a-zA-Z0-9._%+-]+@(qq|163|gmail)\\.com$";

    /**
     * 手机号的正则
     */
    String REGEX_PHONE = "^1[3-9]\\d{9}$";

    /**
     * 1分钟的过期时间 s
     */
    Long EXPIRES_ONE_MIN = 60L;

    /**
     * 1天过期时间 s
     */
    Long EXPIRES_ONE_DAY = EXPIRES_ONE_MIN * 60 * 24;

}

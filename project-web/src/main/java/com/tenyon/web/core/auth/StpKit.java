package com.tenyon.web.core.auth;

import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;

/**
 * StpLogic 门面类，管理项目中所有的 StpLogic 账号体系
 */
public class StpKit {

    public static final String BMS_TYPE = "bms";
    /**
     * 默认原生会话对象
     */
    public static final StpLogic DEFAULT = StpUtil.getStpLogic();

    /**
     * 代替原生默认会话
     */
    public static final StpLogic BMS = new StpLogic(BMS_TYPE);
}

package com.tenyon.frame.core.auth;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.tenyon.common.constant.UserConstant;
import com.tenyon.web.domain.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * sa-token
 *
 * @author yovvis
 * @date 2025/1/4
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return null;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        // 本 list 仅做模拟，实际项目中要根据具体业务逻辑来查询角色
        List<String> list = new ArrayList<>();
        User currentUser = (User) StpUtil.getSession().get(UserConstant.USER_LOGIN_STATE);
        if (currentUser != null) {
            list.add(currentUser.getUserRole());
        }
        return list;
    }
}

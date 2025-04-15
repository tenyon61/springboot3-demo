package com.tenyon.web.common.aop;

import cn.hutool.core.util.StrUtil;
import com.tenyon.web.common.annotation.AuthCheck;
import com.tenyon.web.common.exception.BusinessException;
import com.tenyon.web.common.exception.ErrorCode;
import com.tenyon.web.user.domain.entity.User;
import com.tenyon.web.user.domain.enums.UserRoleEnum;
import com.tenyon.web.user.service.UserService;
import jakarta.annotation.Resource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


/**
 * 权限校验 AOP
 */
@Aspect
@Component
public class AuthInterceptor {

    @Resource
    private UserService userService;

    /**
     * 执行拦截
     *
     * @param joinPoint
     * @param authCheck
     * @return
     */
    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        String mustRole = authCheck.mustRole();
        // 当前登录用户
        User loginUser = userService.getLoginUser();
        // 必须有该权限才通过
        if (StrUtil.isNotBlank(mustRole)) {
            UserRoleEnum mustUserRoleEnum = UserRoleEnum.of(mustRole);
            if (mustUserRoleEnum == null) {
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
            }
            String userRole = loginUser.getUserRole();
            // 如果被封号，直接拒绝
            if (UserRoleEnum.BAN.equals(mustUserRoleEnum)) {
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
            }
            // 必须有管理员权限
            if (UserRoleEnum.ADMIN.equals(mustUserRoleEnum)) {
                if (!mustRole.equals(userRole)) {
                    throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
                }
            }
        }
        // 通过权限校验，放行
        return joinPoint.proceed();
    }
}


package com.tenyon.web.controller;

import com.tenyon.web.common.domain.vo.resp.RtnData;
import com.tenyon.web.common.exception.BusinessException;
import com.tenyon.web.common.exception.ErrorCode;
import com.tenyon.web.common.exception.ThrowUtils;
import com.tenyon.web.domain.dto.UserLoginDTO;
import com.tenyon.web.domain.dto.UserRegisterDTO;
import com.tenyon.web.domain.entity.User;
import com.tenyon.web.domain.vo.user.LoginUserVO;
import com.tenyon.web.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 授权管理
 *
 * @author tenyon
 * @date 2025/1/6
 */
@Tag(name = "AuthController", description = "通用授权接口")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Resource
    private UserService userService;

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public RtnData<LoginUserVO> login(@RequestBody @Valid UserLoginDTO userLoginDTO) {
        if (userLoginDTO == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String account = userLoginDTO.getAccount();
        String password = userLoginDTO.getPassword();
        LoginUserVO loginUserVO = userService.login(account, password);
        return RtnData.success(loginUserVO);
    }

    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public RtnData<Long> register(@RequestBody @Valid UserRegisterDTO userRegisterDTO) {
        if (userRegisterDTO == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String account = userRegisterDTO.getAccount();
        String password = userRegisterDTO.getPassword();
        String checkPassword = userRegisterDTO.getCheckPassword();
        long userId = userService.register(account, password, checkPassword);
        return RtnData.success(userId);
    }

    @Operation(summary = "用户注销")
    @PostMapping("/logout")
    public RtnData<Boolean> logout() {
        boolean res = userService.logout();
        ThrowUtils.throwIf(!res, ErrorCode.OPERATION_ERROR);
        return RtnData.success(res);
    }

    @Operation(summary = "获取当前登录用户")
    @GetMapping("/getLoginUser")
    public RtnData<LoginUserVO> getLoginUser() {
        User loginUser = userService.getLoginUser();
        return RtnData.success(userService.getLoginUserVO(loginUser));
    }

}

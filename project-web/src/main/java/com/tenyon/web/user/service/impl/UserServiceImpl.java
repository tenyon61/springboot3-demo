package com.tenyon.web.user.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tenyon.web.common.constant.BmsConstant;
import com.tenyon.web.common.constant.UserConstant;
import com.tenyon.web.common.exception.BusinessException;
import com.tenyon.web.common.exception.ErrorCode;
import com.tenyon.web.common.exception.ThrowUtils;
import com.tenyon.web.common.utils.SqlUtils;
import com.tenyon.web.core.auth.StpKit;
import com.tenyon.web.user.domain.dto.UserQueryDTO;
import com.tenyon.web.user.domain.entity.User;
import com.tenyon.web.user.domain.enums.UserRoleEnum;
import com.tenyon.web.user.domain.vo.user.LoginUserVO;
import com.tenyon.web.user.domain.vo.user.UserVO;
import com.tenyon.web.user.mapper.UserMapper;
import com.tenyon.web.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户服务实现
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Override
    public long register(String account, String password, String checkPassword) {
        // 1. 校验
        // 密码和校验密码相同
        if (!password.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }
        synchronized (account.intern()) {
            // 1.账户不能重复
            LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.eq(User::getAccount, account);
            long count = this.count(queryWrapper);
            if (count > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
            }
            // 2.加密
            String encryptPassword = DigestUtils.md5DigestAsHex((BmsConstant.ENCRYPT_SALT + password).getBytes());
            // 3.插入数据
            User user = new User();
            user.setAccount(account);
            user.setPassword(encryptPassword);
            user.setUserRole(UserRoleEnum.USER.getValue());
            boolean res = this.save(user);
            ThrowUtils.throwIf(!res, ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
            return user.getId();
        }
    }

    @Transactional
    @Override
    public long register(User user) {
        user.setUserRole("user");
        boolean res = this.save(user);
        ThrowUtils.throwIf(!res, ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
        return user.getId();
    }

    @Override
    public LoginUserVO login(String account, String password) {
        // 1. 校验
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((BmsConstant.ENCRYPT_SALT + password).getBytes());
        // 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", account);
        queryWrapper.eq("password", encryptPassword);
        User user = this.getOne(queryWrapper);
        // 用户不存在
        if (user == null) {
            log.info("user login failed, account cannot match password");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在或密码错误");
        }
        // 3. 记录用户的登录态
        StpKit.BMS.login(user.getId());
        StpKit.BMS.getSession().set(UserConstant.USER_LOGIN_STATE, user);
        return this.getLoginUserVO(user);
    }

    /**
     * 获取当前登录用户
     *
     * @return
     */
    @Override
    public User getLoginUser() {
        // 先判断是否已登录
        ThrowUtils.throwIf(!StpKit.BMS.isLogin(), ErrorCode.NOT_LOGIN_ERROR);
        Object userObj = StpKit.BMS.getSession().get(UserConstant.USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        ThrowUtils.throwIf(!StpKit.BMS.isLogin(), ErrorCode.NOT_LOGIN_ERROR);
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 从数据库查询（追求性能的话可以注释，直接走缓存）
//        long userId = currentUser.getId();
//        currentUser = userService.getById(userId);
//        if (currentUser == null) {
//            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
//        }
        return currentUser;
    }

    @Override
    public boolean logout() {
        ThrowUtils.throwIf(!StpKit.BMS.isLogin(), ErrorCode.NOT_LOGIN_ERROR);
        // 移除登录态
        StpKit.BMS.getSession().removeTerminal(UserConstant.USER_LOGIN_STATE);
        StpKit.BMS.logout();
        return true;
    }

    @Override
    public LoginUserVO getLoginUserVO(User user) {
        if (user == null) {
            return null;
        }
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtils.copyProperties(user, loginUserVO);
        // 组装token
        SaTokenInfo tokenInfo = StpKit.BMS.getTokenInfo();
        loginUserVO.setToken(tokenInfo.tokenValue);
        return loginUserVO;
    }

    @Override
    public UserVO getUserVO(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    @Override
    public List<UserVO> getUserVOList(List<User> userList) {
        if (CollectionUtils.isEmpty(userList)) {
            return new ArrayList<>();
        }
        return userList.stream().map(this::getUserVO).collect(Collectors.toList());
    }

    @Override
    public QueryWrapper<User> getQueryWrapper(UserQueryDTO userQueryRequest) {
        if (userQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        Long id = userQueryRequest.getId();
        String name = userQueryRequest.getName();
        String account = userQueryRequest.getAccount();
        String profile = userQueryRequest.getProfile();
        String userRole = userQueryRequest.getUserRole();
        String sortField = userQueryRequest.getSortField();
        String sortOrder = userQueryRequest.getSortOrder();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id), "id", id);
        queryWrapper.eq(StrUtil.isNotBlank(account), "account", account);
        queryWrapper.eq(StrUtil.isNotBlank(userRole), "user_role", userRole);
        queryWrapper.like(StrUtil.isNotBlank(profile), "profile", profile);
        queryWrapper.like(StrUtil.isNotBlank(name), "name", name);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals("ascend"), sortField);
        return queryWrapper;
    }

    @Override
    public User getUserByMpOpenId(String mpOpenId) {
        return lambdaQuery().eq(User::getMpOpenId, mpOpenId).one();
    }

    @Override
    public String login(Long uid) {
        User user = this.getById(uid);
        // 用户不存在
        if (user == null) {
            log.info("user login failed, account cannot match password");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在或密码错误");
        }
        // 3. 记录用户的登录态
        StpKit.BMS.login(user.getId());
        StpKit.BMS.getSession().set(UserConstant.USER_LOGIN_STATE, user);
        SaTokenInfo tokenInfo = StpKit.BMS.getTokenInfo();
        return tokenInfo.tokenValue;
    }
}

package com.tenyon.web.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tenyon.common.constant.BmsConstant;
import com.tenyon.common.constant.UserConstant;
import com.tenyon.common.exception.BusinessException;
import com.tenyon.common.exception.ErrorCode;
import com.tenyon.common.exception.ThrowUtils;
import com.tenyon.common.utils.SqlUtils;
import com.tenyon.web.domain.dto.user.UserQueryDTO;
import com.tenyon.web.domain.entity.User;
import com.tenyon.web.domain.enums.UserRoleEnum;
import com.tenyon.web.domain.vo.user.LoginUserVO;
import com.tenyon.web.domain.vo.user.UserVO;
import com.tenyon.web.mapper.UserMapper;
import com.tenyon.web.service.UserService;
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
    public Long register(String userAccount, String userPassword, String checkPassword) {
        // 1. 校验
        // 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }
        synchronized (userAccount.intern()) {
            // 1.账户不能重复
            LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.eq(User::getUserAccount, userAccount);
            long count = this.count(queryWrapper);
            if (count > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
            }
            // 2.加密
            String encryptPassword = DigestUtils.md5DigestAsHex((BmsConstant.ENCRYPT_SALT + userPassword).getBytes());
            // 3.插入数据
            User user = new User();
            user.setUserAccount(userAccount);
            user.setUserPassword(encryptPassword);
            user.setUserRole(UserRoleEnum.USER.getValue());
            boolean res = this.save(user);
            ThrowUtils.throwIf(!res, ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
            return user.getId();
        }
    }

    @Transactional
    @Override
    public Long register(User user) {
        user.setUserRole("user");
        boolean res = this.save(user);
        ThrowUtils.throwIf(!res, ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
        return user.getId();
    }

    @Override
    public LoginUserVO login(String userAccount, String userPassword) {
        // 1. 校验
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((BmsConstant.ENCRYPT_SALT + userPassword).getBytes());
        // 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = this.getOne(queryWrapper);
        // 用户不存在
        if (user == null) {
            log.info("user login failed, account cannot match password");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在或密码错误");
        }
        // 3. 记录用户的登录态
        StpUtil.login(user.getId());
        StpUtil.getSession().set(UserConstant.USER_LOGIN_STATE, user);
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
        ThrowUtils.throwIf(!StpUtil.isLogin(), ErrorCode.NOT_LOGIN_ERROR);
        User currentUser = (User) StpUtil.getSession().get(UserConstant.USER_LOGIN_STATE);
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
    public Boolean logout() {
        ThrowUtils.throwIf(!StpUtil.isLogin(), ErrorCode.NOT_LOGIN_ERROR);
        // 移除登录态
        StpUtil.getSession().removeTerminal(UserConstant.USER_LOGIN_STATE);
        StpUtil.logout();
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
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
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
        String userName = userQueryRequest.getUserName();
        String userAccount = userQueryRequest.getUserAccount();
        String userProfile = userQueryRequest.getUserProfile();
        String userRole = userQueryRequest.getUserRole();
        String sortField = userQueryRequest.getSortField();
        String sortOrder = userQueryRequest.getSortOrder();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(id), "id", id);
        queryWrapper.eq(StrUtil.isNotBlank(userAccount), "userAcount", userAccount);
        queryWrapper.eq(StrUtil.isNotBlank(userRole), "userRole", userRole);
        queryWrapper.like(StrUtil.isNotBlank(userProfile), "userProfile", userProfile);
        queryWrapper.like(StrUtil.isNotBlank(userName), "userName", userName);
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
        StpUtil.login(user.getId());
        StpUtil.getSession().set(UserConstant.USER_LOGIN_STATE, user);
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        return tokenInfo.tokenValue;
    }
}

package com.tenyon.web.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tenyon.web.user.domain.dto.UserQueryDTO;
import com.tenyon.web.user.domain.entity.User;
import com.tenyon.web.user.domain.vo.user.LoginUserVO;
import com.tenyon.web.user.domain.vo.user.UserVO;

import java.util.List;

/**
 * 用户服务
 */
public interface UserService extends IService<User> {

    /**
     * 用户登录
     *
     * @param account  用户账户
     * @param password 用户密码
     * @return 脱敏后的用户信息
     */
    LoginUserVO login(String account, String password);

    /**
     * 用户注册
     *
     * @param account       用户账户
     * @param password      用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
    long register(String account, String password, String checkPassword);


    /**
     * 用户注册
     *
     * @param user 用户实体
     * @return 新用户 id
     */
    long register(User user);

    /**
     * 获取当前登录用户
     *
     * @return 当前登录用户
     */
    User getLoginUser();

    /**
     * 用户注销
     *
     * @return 空
     */
    boolean logout();

    /**
     * 获取脱敏的已登录用户信息
     *
     * @return 当前登录用户信息
     */
    LoginUserVO getLoginUserVO(User user);

    /**
     * 获取脱敏的用户信息
     *
     * @param user 用户实体
     * @return 脱敏的用户
     */
    UserVO getUserVO(User user);

    /**
     * 获取脱敏的用户信息
     *
     * @param userList
     * @return
     */
    List<UserVO> getUserVOList(List<User> userList);

    /**
     * 获取查询条件
     *
     * @param userQueryRequest 用户查询请求
     * @return sql 查询条件
     */
    QueryWrapper<User> getQueryWrapper(UserQueryDTO userQueryRequest);

    /**
     * 根据openId获取用户
     *
     * @param mpOpenId 微信开放平台 id
     * @return 用户
     */
    User getUserByMpOpenId(String mpOpenId);

    String login(Long uid);
}

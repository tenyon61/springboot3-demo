package com.tenyon.web.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tenyon.web.domain.dto.user.UserQueryDTO;
import com.tenyon.web.domain.entity.User;
import com.tenyon.web.domain.vo.user.LoginUserVO;
import com.tenyon.web.domain.vo.user.UserVO;

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
    Long register(String account, String password, String checkPassword);


    /**
     * 用户注册
     *
     * @param user 用户实体
     * @return 新用户 id
     */
    Long register(User user);

    /**
     * 获取当前登录用户
     *
     * @return 当前登录用户
     */
    User getLoginUser();

    /**
     * 用户注销
     *
     * @return Boolean
     */
    Boolean logout();

    /**
     * 获取脱敏的已登录用户信息
     *
     * @return LoginUserVO
     */
    LoginUserVO getLoginUserVO(User user);

    /**
     * 获取脱敏的用户信息
     *
     * @param user 用户实体
     * @return UserVO
     */
    UserVO getUserVO(User user);

    /**
     * 获取脱敏的用户信息
     *
     * @param userList 用户列表
     * @return List<UserVO>
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
     * @return User
     */
    User getUserByMpOpenId(String mpOpenId);

    String login(Long uid);
}

package com.tenyon.web.user.service.convert;

import cn.hutool.core.util.RandomUtil;
import com.tenyon.web.user.domain.entity.User;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;

/**
 * 用户转换
 *
 * @author tenyon
 * @date 2025/4/4
 */
public class UserConvert {
    public static User buildAuthorizeUser(Long uid, WxOAuth2UserInfo userInfo) {
        User user = new User();
        user.setId(uid);
        user.setAvatar(userInfo.getHeadImgUrl());
        user.setName(userInfo.getNickname());
        user.setSex(userInfo.getSex());
        if (userInfo.getNickname().length() > 6) {
            user.setName("名字过长" + RandomUtil.randomInt(100000));
        } else {
            user.setName(userInfo.getNickname());
        }
        return user;
    }
}

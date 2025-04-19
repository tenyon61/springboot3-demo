package com.tenyon.web.domain.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Schema(description = "用户更新个人信息请求体")
@Data
public class UserUpdateMyDTO implements Serializable {

    @Schema(description = "用户昵称")
    private String name;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "简介")
    private String profile;

    @Serial
    private static final long serialVersionUID = 1L;
}
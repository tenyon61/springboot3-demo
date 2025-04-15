package com.tenyon.web.user.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Schema(description = "更新用户请求体")
@Data
public class UserUpdateDTO implements Serializable {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "用户昵称")
    private String name;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "简介")
    private String profile;

    @Schema(description = "用户角色")
    private String userRole;

    @Serial
    private static final long serialVersionUID = 1L;
}
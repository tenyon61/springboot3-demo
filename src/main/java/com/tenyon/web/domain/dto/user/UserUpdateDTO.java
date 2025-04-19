package com.tenyon.web.domain.dto.user;

import com.tenyon.web.common.constant.BmsConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Schema(description = "更新用户请求体")
@Data
public class UserUpdateDTO implements Serializable {

    @NotNull
    @Schema(description = "id")
    private Long id;

    @Schema(description = "用户昵称")
    private String name;

    @Schema(description = "账号")
    private String account;

    @Schema(description = "性别")
    private Integer sex;

    @Pattern(regexp = BmsConstant.REGEX_EMAIL, message = "邮箱格式不正确")
    @Schema(description = "邮箱")
    private String email;

    @Pattern(regexp = BmsConstant.REGEX_PHONE, message = "手机号格式不正确")
    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "用户角色")
    private String userRole;
    @Serial
    private static final long serialVersionUID = 1L;
}
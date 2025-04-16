package com.tenyon.web.user.domain.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@Schema(description = "用户视图（脱敏）")
public class UserVO implements Serializable {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "账号")
    private String account;

    @Schema(description = "用户昵称")
    private String name;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "用户简介")
    private String profile;

    @Schema(description = "性别")
    private Integer sex;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "用户角色")
    private String userRole;

    @Schema(description = "创建时间")
    private Date createTime;

    @Serial
    private static final long serialVersionUID = 1L;

}
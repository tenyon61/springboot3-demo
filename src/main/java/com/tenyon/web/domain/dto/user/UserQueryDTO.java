package com.tenyon.web.domain.dto.user;

import com.tenyon.common.domain.vo.req.PageReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "用户查询请求体")
@Data
public class UserQueryDTO extends PageReq implements Serializable {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "用户昵称")
    private String userName;

    @Schema(description = "用户账号")
    private String userAccount;

    @Schema(description = "简介")
    private String userProfile;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "用户角色")
    private String userRole;

    @Serial
    private static final long serialVersionUID = 1L;

}
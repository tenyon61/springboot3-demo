package com.tenyon.web.common.domain.vo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(description = "删除请求参数")
public class DeleteReq implements Serializable {

    @Schema(description = "id")
    private Long id;

    @Serial
    private static final long serialVersionUID = 1L;

}
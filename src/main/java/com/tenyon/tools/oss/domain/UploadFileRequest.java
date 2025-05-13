package com.tenyon.tools.oss.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(description = "文件上传请求参数")
public class UploadFileRequest implements Serializable {

    @Schema(description = "业务字段")
    private String biz;

    @Serial
    private static final long serialVersionUID = 1L;
}
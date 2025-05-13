package com.tenyon.tools.oss.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OssResp {

    @Schema(description = "上传的临时url")
    private String uploadUrl;

    @Schema(description = "成功后能够下载的url")
    private String downloadUrl;
}

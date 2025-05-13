package com.tenyon.common.domain.vo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "分页请求参数")
public class PageReq {

    @Schema(description = "当前页号")
    private int current = 1;

    @Schema(description = "页面大小")
    private int pageSize = 10;

    @Schema(description = "排序字段")
    private String sortField;

    @Schema(description = "默认升序 ascend/descend")
    private String sortOrder = "ascend";
}

package com.tenyon.tools.oss.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "图片上传结果")
public class UploadPictureResult {

    @Schema(description = "图片地址")
    private String url;

    @Schema(description = "缩略图地址")
    private String thumbnailUrl;

    @Schema(description = "图片名称")
    private String picName;

    @Schema(description = "文件体积")
    private Long picSize;

    @Schema(description = "图片宽度")
    private int picWidth;

    @Schema(description = "图片高度")
    private int picHeight;

    @Schema(description = "图片宽高比")
    private Double picScale;

    @Schema(description = "图片格式")
    private String picFormat;

    @Schema(description = "源图片格式")
    private String originFormat;

    @Schema(description = "图片主色调")
    private String picColor;

}
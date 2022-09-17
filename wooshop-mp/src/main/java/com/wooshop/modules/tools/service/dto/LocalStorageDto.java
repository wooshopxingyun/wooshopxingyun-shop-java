package com.wooshop.modules.tools.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.wooshop.base.CommonDto;

import java.io.Serializable;

/**
* @author jinjin
* @date 2020-09-27
*/
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class LocalStorageDto extends CommonDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String realName;

    private String name;

    private String suffix;

    private String path;

    private String type;

    private String size;

    private Integer categoryId;

//    @ApiModelProperty(value = "管理分类表id")
    private String categoryPath;

//    @ApiModelProperty(value = "存储类型")
    private Integer storageType;


//    @ApiModelProperty(value = "Bucket 识别符")
    private String bucket;
}

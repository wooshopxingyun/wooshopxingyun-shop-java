package com.wooshop.modules.service.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
* @author fanglei
* @date 2021-08-11
*/
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CodeScrewConfigDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 防止精度丢失 */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long configId;

    @ApiModelProperty(value = "版本号")
    private String version;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "忽略表名")
    private String ignoreTableName;

    @ApiModelProperty(value = "忽略表前缀")
    private String ignoreTablePrefix;

    @ApiModelProperty(value = "忽略表后缀")
    private String ignoreTableSuffix;

    @ApiModelProperty(value = "指定表名生成")
    private String designatedTableName;

    @ApiModelProperty(value = "指定表前缀生成")
    private String designatedTablePrefix;

    @ApiModelProperty(value = "指定表后缀生成")
    private String designatedTableSuffix;
}

package ${package}.service.dto;


import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
<#if hasDateTime??>
import java.util.Date;
</#if>
<#if !auto && pkColumnType == 'Long'>
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
</#if>
<#if hasBigDecimal>
import java.math.BigDecimal;
</#if>


/**
* @author ${author}
* @date ${date}
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/



@Data
public class ${className}Dto implements Serializable {
<#if columns??>
    <#list columns as column>

        <#if column.remark != ''>
    @ApiModelProperty(value = "${column.remark}")
        </#if>
        <#if column.columnKey = 'PRI'>
            <#if !auto && pkColumnType = 'Long'>
    /** 防止精度丢失 */
    @JsonSerialize(using= ToStringSerializer.class)
            </#if>
        </#if>
        <#if column.changeColumnName != 'delFlag'>
    private ${column.columnType} ${column.changeColumnName};
        </#if>
    </#list>
</#if>
}

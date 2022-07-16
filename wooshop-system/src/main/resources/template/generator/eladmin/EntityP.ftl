

package ${package}.domain;

<#if hasBigDecimal>
    import java.math.BigDecimal;
</#if>
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.hutool.core.bean.BeanUtil;

import com.wooshop.domain.BaseDomain;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
<#if isNotNullColumns??>
import javax.validation.constraints.*;
</#if>
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
<#if hasDateAnnotation>
</#if>

<#if hasDateTime?? >
import java.util.Date;
</#if>



/**
* @author ${author}
* @date ${date}
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("${tableName}")
public class ${className} extends BaseDomain {
<#if columns??>
    <#list columns as column>
    <#if column.changeColumnName != 'isDel' && column.changeColumnName != 'createTime' && column.changeColumnName != 'updateTime' >
    <#if column.remark != ''>
    @ApiModelProperty(value = "${column.remark}")
    </#if>
    <#if column.columnKey = 'PRI'>
    @TableId(value = "${column.columnName}",type= IdType.AUTO)
    </#if>
    <#if column.istNotNull && column.columnKey != 'PRI'>
        <#if column.columnType = 'String'>
    @NotBlank
        <#else>
    @NotNull
        </#if>
    </#if>
    private ${column.columnType} ${column.changeColumnName};
    </#if>

    </#list>
</#if>

    public void copy(${className} source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}

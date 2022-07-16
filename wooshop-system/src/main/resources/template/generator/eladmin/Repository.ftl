


package ${package}.repository;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.data.jpa.repository.JpaRepository;



/**
* @author ${author}
* @date ${date}
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/
import ${package}.domain.${className};

public interface ${className}Repository extends JpaRepository<${className}, ${pkColumnType}>, JpaSpecificationExecutor<${className}> {
<#if columns??>
    <#list columns as column>
        <#if column.columnKey = 'UNI'>
    /**
    * 根据 ${column.capitalColumnName} 查询
    * @param ${column.columnName} /
    * @return /
    */
    ${className} findBy${column.capitalColumnName}(${column.columnType} ${column.columnName});
        </#if>
    </#list>
</#if>
}

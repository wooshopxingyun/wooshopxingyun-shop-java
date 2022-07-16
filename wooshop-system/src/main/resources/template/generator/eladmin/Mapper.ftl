

package ${package}.service.mapper;

import ${package}.service.dto.${className}Dto;


import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ${package}.domain.${className};

import com.wooshop.base.BaseMapper;



/**
* @author ${author}
* @date ${date}
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ${className}Mapper extends BaseMapper<${className}Dto, ${className}> {

}



package com.wooshop.modules.template.service.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.wooshop.modules.template.domain.WooshopFreightTemplatePinkage;
import com.wooshop.common.mapper.CoreMapper;


/**
* @author woo
* @date 2021-11-20
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Repository
public interface WooshopFreightTemplatePinkageMapper extends CoreMapper<WooshopFreightTemplatePinkage> {

    @Select("DELETE FROM wooshop_freight_template_pinkage WHERE template_id=#{template_id}")
    void delByTempId(@Param("template_id")Long tempId);

}

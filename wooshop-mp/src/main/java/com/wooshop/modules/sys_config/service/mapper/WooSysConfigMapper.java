

package com.wooshop.modules.sys_config.service.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.wooshop.modules.sys_config.domain.WooSysConfig;
import com.wooshop.common.mapper.CoreMapper;

import java.util.List;


/**
* @author woo
* @date 2021-11-09
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Repository
public interface WooSysConfigMapper extends CoreMapper<WooSysConfig> {

    @Select("SELECT menu_name as menuName,id ,value,enabled FROM woo_sys_config  ${ew.customSqlSegment}")
    List<WooSysConfig> selectListWRAPPER(@Param(Constants.WRAPPER) QueryWrapper queryWrapper);
}

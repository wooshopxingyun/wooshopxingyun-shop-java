

package com.wooshop.modules.money_record.service.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.wooshop.modules.money_record.domain.WooshopMoneyRecord;
import com.wooshop.common.mapper.CoreMapper;

import java.math.BigDecimal;


/**
* @author woo
* @date 2021-12-20
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Repository
public interface WooshopMoneyRecordMapper extends CoreMapper<WooshopMoneyRecord> {

     @Select("SELECT sum(money+give_money) FROM wooshop_money_record ${ew.customSqlSegment}")
     BigDecimal getUserCountMoney(@Param(Constants.WRAPPER) QueryWrapper queryWrapper);
}

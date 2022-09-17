

package com.wooshop.modules.relation.service.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.wooshop.modules.relation.service.dto.WooshopGoodsRelationDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.wooshop.modules.relation.domain.WooshopGoodsRelation;
import com.wooshop.common.mapper.CoreMapper;

import java.util.List;


/**
* @author woo
* @date 2022-01-11
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Repository
public interface WooshopGoodsRelationMapper extends CoreMapper<WooshopGoodsRelation> {

     @Select("DELETE FROM wooshop_goods_relation ${ew.customSqlSegment}")
    void removeByCriteria(@Param(Constants.WRAPPER) QueryWrapper queryWrapper );

    @Select("SELECT g.* from wooshop_goods_relation r INNER JOIN wooshop_store_goods g on r.goods_id=g.id ${ew.customSqlSegment}")
    List<WooshopGoodsRelationDto> queryByUidList(@Param(Constants.WRAPPER) QueryWrapper queryWrapper);
}

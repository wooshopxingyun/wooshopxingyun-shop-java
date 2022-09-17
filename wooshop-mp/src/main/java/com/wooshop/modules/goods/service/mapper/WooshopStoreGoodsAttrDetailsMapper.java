

package com.wooshop.modules.goods.service.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.wooshop.modules.goods.domain.WooshopStoreGoodsAttrDetails;
import com.wooshop.common.mapper.CoreMapper;


/**
* @author woo
* @date 2021-12-05
* 注意：
* 本软件为www.wooshopxingyun.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Repository
public interface WooshopStoreGoodsAttrDetailsMapper extends CoreMapper<WooshopStoreGoodsAttrDetails> {

     @Select("DELETE FROM wooshop_store_goods_attr_details WHERE goods_id=#{goods_id} and activity_type=#{activity_type}")
    void delByGoodsAndType(@Param("goods_id")Long goodsId,@Param("activity_type")Integer activityType);
}

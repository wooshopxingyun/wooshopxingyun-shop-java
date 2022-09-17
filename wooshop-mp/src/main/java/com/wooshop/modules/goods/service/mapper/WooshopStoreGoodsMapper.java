

package com.wooshop.modules.goods.service.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.wooshop.modules.goods.domain.WooshopStoreGoods;
import com.wooshop.common.mapper.CoreMapper;

import java.util.List;


/**
* @author woo
* @date 2021-11-30
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Repository
public interface WooshopStoreGoodsMapper extends CoreMapper<WooshopStoreGoods> {


     @Select("select * from wooshop_store_goods where is_del=1")
     List<WooshopStoreGoods> selectDelData();


     @Select("select * from wooshop_store_goods where id=#{id}")
     WooshopStoreGoods selectGoodsAll(@Param("id")Long id);
}

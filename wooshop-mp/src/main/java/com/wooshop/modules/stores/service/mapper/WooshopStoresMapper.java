

package com.wooshop.modules.stores.service.mapper;

import com.wooshop.modules.stores.service.dto.WooshopStoresDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.wooshop.modules.stores.domain.WooshopStores;
import com.wooshop.common.mapper.CoreMapper;

import java.util.List;


/**
* @author woo
* @date 2021-12-21
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Repository
public interface WooshopStoresMapper extends CoreMapper<WooshopStores> {


    @Select("SELECT *, (round(6367000 * 2 * asin(sqrt(pow(sin(((latitude * pi()) / 180 - (${latitude} * pi()) / 180) / 2), 2) + cos((${latitude} * pi()) / 180) * cos((latitude * pi()) / 180) * pow(sin(((longitude * pi()) / 180 - (${longitude} * pi()) / 180) / 2), 2))))) AS storesDistance FROM wooshop_stores WHERE is_start = 1 and is_del = 0 ORDER BY storesDistance asc")
   List<WooshopStoresDto> getNearStoresList(@Param("latitude")String latitude , @Param("longitude")String  longitude);

}

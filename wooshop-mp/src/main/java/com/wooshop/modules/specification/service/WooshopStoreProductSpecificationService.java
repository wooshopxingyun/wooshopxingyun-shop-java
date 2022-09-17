
package com.wooshop.modules.specification.service;


import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.io.IOException;
import com.wooshop.modules.specification.service.dto.WooshopStoreProductSpecificationDto;
import org.springframework.data.domain.Pageable;
import com.wooshop.modules.service.BaseService;

import com.wooshop.domain.PageResult;
import com.wooshop.modules.specification.domain.WooshopStoreProductSpecification;

import com.wooshop.modules.specification.service.dto.WooshopStoreProductSpecificationQueryCriteria;

/**
* @author woo
* @date 2021-11-25
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


public interface WooshopStoreProductSpecificationService  extends BaseService<WooshopStoreProductSpecification>{

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    PageResult<WooshopStoreProductSpecificationDto>  queryAll(WooshopStoreProductSpecificationQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<WooshopStoreProductSpecificationDto>
    */
    List<WooshopStoreProductSpecification> queryAll(WooshopStoreProductSpecificationQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<WooshopStoreProductSpecificationDto> all, HttpServletResponse response) throws IOException;
}

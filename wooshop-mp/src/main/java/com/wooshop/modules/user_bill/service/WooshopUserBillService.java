
package com.wooshop.modules.user_bill.service;


import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.io.IOException;
import com.wooshop.modules.user_bill.service.dto.WooshopUserBillDto;
import org.springframework.data.domain.Pageable;
import com.wooshop.modules.service.BaseService;

import com.wooshop.domain.PageResult;
import com.wooshop.modules.user_bill.domain.WooshopUserBill;

import com.wooshop.modules.user_bill.service.dto.WooshopUserBillQueryCriteria;

/**
* @author woo
* @date 2022-02-11
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


public interface WooshopUserBillService  extends BaseService<WooshopUserBill>{

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    PageResult<WooshopUserBillDto>  queryAll(WooshopUserBillQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<WooshopUserBillDto>
    */
    List<WooshopUserBill> queryAll(WooshopUserBillQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<WooshopUserBillDto> all, HttpServletResponse response) throws IOException;

    /**
    * 包含新增和更新功能
    * @param resources
    * @return
    */
    PageResult<WooshopUserBillDto> addAndUpdate(WooshopUserBill resources);


    /**
    * 根据id 进行删除
    * @param id
    */
    void cacheRemoveById(Long id);

}

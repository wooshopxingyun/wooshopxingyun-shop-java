
package com.wooshop.modules.money_record.service;


import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.io.IOException;
import com.wooshop.modules.money_record.service.dto.WooshopMoneyRecordDto;
import org.springframework.data.domain.Pageable;
import com.wooshop.modules.service.BaseService;

import com.wooshop.domain.PageResult;
import com.wooshop.modules.money_record.domain.WooshopMoneyRecord;

import com.wooshop.modules.money_record.service.dto.WooshopMoneyRecordQueryCriteria;

/**
* @author woo
* @date 2021-12-20
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


public interface WooshopMoneyRecordService  extends BaseService<WooshopMoneyRecord>{

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    PageResult<WooshopMoneyRecordDto>  queryAll(WooshopMoneyRecordQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<WooshopMoneyRecordDto>
    */
    List<WooshopMoneyRecord> queryAll(WooshopMoneyRecordQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<WooshopMoneyRecordDto> all, HttpServletResponse response) throws IOException;

}

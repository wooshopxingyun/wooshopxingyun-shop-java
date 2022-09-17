
package com.wooshop.modules.brokerage_record.service;


import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.io.IOException;
import java.util.Map;

import com.wooshop.modules.brokerage_record.service.dto.WooshopUserBrokerageRecordDto;
import org.springframework.data.domain.Pageable;
import com.wooshop.modules.service.BaseService;

import com.wooshop.domain.PageResult;
import com.wooshop.modules.brokerage_record.domain.WooshopUserBrokerageRecord;

import com.wooshop.modules.brokerage_record.service.dto.WooshopUserBrokerageRecordQueryCriteria;

/**
* @author woo
* @date 2022-04-22
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


public interface WooshopUserBrokerageRecordService  extends BaseService<WooshopUserBrokerageRecord>{

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    PageResult<WooshopUserBrokerageRecordDto>  queryAll(WooshopUserBrokerageRecordQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<WooshopUserBrokerageRecordDto>
    */
    List<WooshopUserBrokerageRecord> queryAll(WooshopUserBrokerageRecordQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<WooshopUserBrokerageRecordDto> all, HttpServletResponse response) throws IOException;

    /**
    * 包含新增和更新功能
    * @param resources
    * @return
    */
    PageResult<WooshopUserBrokerageRecordDto> addAndUpdate(WooshopUserBrokerageRecord resources);


    /**
    * 根据id 进行删除
    * @param id
    */
    void cacheRemoveById(Long id);



    /**
     * 通过用户id和关联linkId查询数据
     * @param uid 用户id
     * @param linkId 关联id
     * @return
     */
    List<WooshopUserBrokerageRecord> findByUidAndLinkId(Long uid, Long linkId);



    /**
     * 获取推广记录列表
     * @param uid 用户uid
     * @param pageable 分页参数
     * @return List
     */
    PageResult<WooshopUserBrokerageRecordDto> findBrokerageOrderListByUid(Long uid, Pageable pageable);

    /**
     * 获取月份对应的推广订单数
     * @param uid 用户uid
     * @param monthList 月份列表
     * @return Map
     */
    Map<String, Long> getBrokerageCountByUidAndMonth(Long uid, List<String> monthList);

}

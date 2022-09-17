
package com.wooshop.modules.brokerage_record.rest;

import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.PageInfo;
import com.wooshop.modules.brokerage_record.service.dto.WooshopUserBrokerageRecordQueryCriteria;

import java.util.*;

import com.wooshop.modules.brokerage_record.service.vo.BrokerageOrderAppVo;
import com.wooshop.modules.brokerage_record.service.vo.UserBrokerageOrderAppVo;
import com.wooshop.modules.user.WooUserService;

import com.wooshop.utils.WooshopDateUtil;
import com.wooshop.utils.enums.WooshopConstants;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import com.wooshop.dozer.service.IGenerator;
import javax.servlet.http.HttpServletResponse;
import com.wooshop.domain.PageResult;
import com.wooshop.modules.brokerage_record.domain.WooshopUserBrokerageRecord;
import com.wooshop.modules.brokerage_record.service.WooshopUserBrokerageRecordService;


import com.wooshop.modules.brokerage_record.service.dto.WooshopUserBrokerageRecordDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import com.wooshop.annotation.Log;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import java.util.stream.Collectors;


/**
* @author woo
* @date 2022-04-22
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@AllArgsConstructor
@Api(tags = "会员佣金记录表管理")
@RestController
@RequestMapping("/api/wooshopUserBrokerageRecord")
public class WooshopUserBrokerageRecordController {

    private final WooshopUserBrokerageRecordService wooshopUserBrokerageRecordService;
    private final IGenerator generator;
//    private final WooshopOrderService orderService;
    private final WooUserService userService;



    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','wooshopUserBrokerageRecord:list')")
    public void download(HttpServletResponse response, WooshopUserBrokerageRecordQueryCriteria criteria) throws IOException {
        wooshopUserBrokerageRecordService.download(generator.convert(wooshopUserBrokerageRecordService.queryAll(criteria), WooshopUserBrokerageRecordDto.class), response);
    }

    @GetMapping
    @Log("查询会员佣金记录表")
    @ApiOperation("查询会员佣金记录表")
    @PreAuthorize("@el.check('admin','wooshopUserBrokerageRecord:list')")
    public ResponseEntity<PageResult<WooshopUserBrokerageRecordDto>> getWooshopUserBrokerageRecords(WooshopUserBrokerageRecordQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(wooshopUserBrokerageRecordService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增会员佣金记录表")
    @ApiOperation("新增会员佣金记录表")
    @PreAuthorize("@el.check('admin','wooshopUserBrokerageRecord:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody WooshopUserBrokerageRecord resources){
        return new ResponseEntity<>(wooshopUserBrokerageRecordService.addAndUpdate(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改会员佣金记录表")
    @ApiOperation("修改会员佣金记录表")
    @PreAuthorize("@el.check('admin','wooshopUserBrokerageRecord:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody WooshopUserBrokerageRecord resources){
        wooshopUserBrokerageRecordService.addAndUpdate(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除会员佣金记录表")
    @ApiOperation("删除会员佣金记录表")
    @PreAuthorize("@el.check('admin','wooshopUserBrokerageRecord:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        Arrays.asList(ids).forEach(id->{
            wooshopUserBrokerageRecordService.cacheRemoveById(id);
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping(value = "/brokerageOrderRecords")
    @Log("查询用户分销订单")
    @ApiOperation("查询用户分销订单")
    @PreAuthorize("@el.check('admin','wooshopUserBrokerageRecord:list')")
    public ResponseEntity<PageResult<BrokerageOrderAppVo>> getUserBrokerageOrderRecords(WooshopUserBrokerageRecordQueryCriteria criteria, Pageable pageable){
        // 获取推广订单记录，分页
        PageResult<WooshopUserBrokerageRecordDto> userBrPr = wooshopUserBrokerageRecordService.findBrokerageOrderListByUid(criteria.getUid(), pageable);
        // 获取对应的订单信息
        List<String> orderNoList = userBrPr.getContent().stream().map(WooshopUserBrokerageRecordDto::getLinkId).collect(Collectors.toList());

        if (orderNoList.size()<1){
            return null;
        }

        List<BrokerageOrderAppVo> userSpreadOrderItemResponseList = new ArrayList<>();
        List<String> monthList = CollUtil.newArrayList();
        userBrPr.getContent().forEach(broRecord -> {
            UserBrokerageOrderAppVo userBrokerageOrderAppVo = new UserBrokerageOrderAppVo();
            userBrokerageOrderAppVo.setOrderId(broRecord.getLinkId());
            userBrokerageOrderAppVo.setBroTime(broRecord.getUpdateTime());
            userBrokerageOrderAppVo.setBroNumber(broRecord.getBroPrice());
            userBrokerageOrderAppVo.setOrderType("返佣");

            String dateToStr = WooshopDateUtil.dateToStr(broRecord.getUpdateTime(), WooshopConstants.DATE_FORMAT_MONTH);
            if (monthList.contains(dateToStr)) {
                //如果在已有的数据中找到当前月份数据则追加
                for (BrokerageOrderAppVo brokerageOrderAppVo : userSpreadOrderItemResponseList) {
                    if (brokerageOrderAppVo.getBroTime().equals(dateToStr)) {
                        brokerageOrderAppVo.getChildUserBroOrder().add(userBrokerageOrderAppVo);
                        break;
                    }
                }
            } else {// 不包含此月份
                //创建一个
                BrokerageOrderAppVo brokerageOrderAppVo = new BrokerageOrderAppVo();
                brokerageOrderAppVo.setBroTime(dateToStr);
                brokerageOrderAppVo.getChildUserBroOrder().add(userBrokerageOrderAppVo);
                userSpreadOrderItemResponseList.add(brokerageOrderAppVo);
                monthList.add(dateToStr);
            }
        });
        // 获取月份总订单数
        Map<String, Long> countByUidAndMonth = wooshopUserBrokerageRecordService.getBrokerageCountByUidAndMonth(criteria.getUid(), monthList);
        for (BrokerageOrderAppVo userSpreadOrderItemResponse: userSpreadOrderItemResponseList) {
            userSpreadOrderItemResponse.setCount(countByUidAndMonth.get(userSpreadOrderItemResponse.getBroTime()).intValue());
        }
        // 组装分页数据
        PageInfo<BrokerageOrderAppVo> page = new PageInfo<>(userSpreadOrderItemResponseList);
        PageResult<BrokerageOrderAppVo> brokerageOrderAppVoPageResult = generator.convertPageInfo(page, BrokerageOrderAppVo.class);
        brokerageOrderAppVoPageResult.setTotalElements(userBrPr.getTotalElements());
        return new ResponseEntity<>(brokerageOrderAppVoPageResult,HttpStatus.OK);
    }
}

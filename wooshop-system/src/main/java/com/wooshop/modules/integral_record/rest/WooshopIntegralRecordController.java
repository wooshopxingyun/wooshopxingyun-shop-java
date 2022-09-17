
package com.wooshop.modules.integral_record.rest;

import com.wooshop.modules.integral_record.service.dto.WooshopIntegralRecordQueryCriteria;
import java.util.Arrays;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import com.wooshop.dozer.service.IGenerator;
import javax.servlet.http.HttpServletResponse;
import com.wooshop.domain.PageResult;
import com.wooshop.modules.integral_record.domain.WooshopIntegralRecord;
import com.wooshop.modules.integral_record.service.WooshopIntegralRecordService;


import com.wooshop.modules.integral_record.service.dto.WooshopIntegralRecordDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import com.wooshop.annotation.Log;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;




/**
* @author woo
* @date 2021-12-19
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@AllArgsConstructor
@Api(tags = "积分记录管理")
@RestController
@RequestMapping("/api/wooshopIntegralRecord")
public class WooshopIntegralRecordController {

    private final WooshopIntegralRecordService wooshopIntegralRecordService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','wooshopIntegralRecord:list','wooSysConfig:list')")
    public void download(HttpServletResponse response, WooshopIntegralRecordQueryCriteria criteria) throws IOException {
        wooshopIntegralRecordService.download(generator.convert(wooshopIntegralRecordService.queryAll(criteria), WooshopIntegralRecordDto.class), response);
    }

    @GetMapping
    @Log("查询积分记录")
    @ApiOperation("查询积分记录")
    @PreAuthorize("@el.check('admin','wooshopIntegralRecord:list','wooSysConfig:list')")
    public ResponseEntity<PageResult<WooshopIntegralRecordDto>> getWooshopIntegralRecords(WooshopIntegralRecordQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(wooshopIntegralRecordService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增积分记录")
    @ApiOperation("新增积分记录")
    @PreAuthorize("@el.check('admin','wooshopIntegralRecord:list','wooSysConfig:list')")
    public ResponseEntity<Object> create(@Validated @RequestBody WooshopIntegralRecord resources){
        return new ResponseEntity<>(wooshopIntegralRecordService.save(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改积分记录")
    @ApiOperation("修改积分记录")
    @PreAuthorize("@el.check('admin','wooshopIntegralRecord:list','wooSysConfig:list')")
    public ResponseEntity<Object> update(@Validated @RequestBody WooshopIntegralRecord resources){
        wooshopIntegralRecordService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除积分记录")
    @ApiOperation("删除积分记录")
    @PreAuthorize("@el.check('admin','wooshopIntegralRecord:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        Arrays.asList(ids).forEach(id->{
            wooshopIntegralRecordService.removeById(id);
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

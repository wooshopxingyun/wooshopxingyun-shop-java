
package com.wooshop.modules.money_record.rest;

import com.wooshop.modules.money_record.service.dto.WooshopMoneyRecordQueryCriteria;
import java.util.Arrays;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import com.wooshop.dozer.service.IGenerator;
import javax.servlet.http.HttpServletResponse;
import com.wooshop.domain.PageResult;
import com.wooshop.modules.money_record.domain.WooshopMoneyRecord;
import com.wooshop.modules.money_record.service.WooshopMoneyRecordService;


import com.wooshop.modules.money_record.service.dto.WooshopMoneyRecordDto;
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
* @date 2021-12-20
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@AllArgsConstructor
@Api(tags = "会员充值记录管理")
@RestController
@RequestMapping("/api/wooshopMoneyRecord")
public class WooshopMoneyRecordController {

    private final WooshopMoneyRecordService wooshopMoneyRecordService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','wooshopMoneyRecord:list')")
    public void download(HttpServletResponse response, WooshopMoneyRecordQueryCriteria criteria) throws IOException {
        wooshopMoneyRecordService.download(generator.convert(wooshopMoneyRecordService.queryAll(criteria), WooshopMoneyRecordDto.class), response);
    }

    @GetMapping
    @Log("查询会员充值记录")
    @ApiOperation("查询会员充值记录")
    @PreAuthorize("@el.check('admin','wooshopMoneyRecord:list')")
    public ResponseEntity<PageResult<WooshopMoneyRecordDto>> getWooshopMoneyRecords(WooshopMoneyRecordQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(wooshopMoneyRecordService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增会员充值记录")
    @ApiOperation("新增会员充值记录")
    @PreAuthorize("@el.check('admin','wooshopMoneyRecord:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody WooshopMoneyRecord resources){
        return new ResponseEntity<>(wooshopMoneyRecordService.save(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改会员充值记录")
    @ApiOperation("修改会员充值记录")
    @PreAuthorize("@el.check('admin','wooshopMoneyRecord:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody WooshopMoneyRecord resources){
        wooshopMoneyRecordService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除会员充值记录")
    @ApiOperation("删除会员充值记录")
    @PreAuthorize("@el.check('admin','wooshopMoneyRecord:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        Arrays.asList(ids).forEach(id->{
            wooshopMoneyRecordService.removeById(id);
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

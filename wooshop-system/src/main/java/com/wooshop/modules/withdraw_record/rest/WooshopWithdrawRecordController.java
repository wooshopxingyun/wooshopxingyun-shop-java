
package com.wooshop.modules.withdraw_record.rest;

import com.wooshop.modules.withdraw_record.service.dto.WooshopWithdrawRecordQueryCriteria;
import java.util.Arrays;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import com.wooshop.dozer.service.IGenerator;
import javax.servlet.http.HttpServletResponse;
import com.wooshop.domain.PageResult;
import com.wooshop.modules.withdraw_record.domain.WooshopWithdrawRecord;
import com.wooshop.modules.withdraw_record.service.WooshopWithdrawRecordService;


import com.wooshop.modules.withdraw_record.service.dto.WooshopWithdrawRecordDto;
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
@Api(tags = "提现记录管理")
@RestController
@RequestMapping("/api/wooshopWithdrawRecord")
public class WooshopWithdrawRecordController {

    private final WooshopWithdrawRecordService wooshopWithdrawRecordService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','wooshopWithdrawRecord:list')")
    public void download(HttpServletResponse response, WooshopWithdrawRecordQueryCriteria criteria) throws IOException {
        wooshopWithdrawRecordService.download(generator.convert(wooshopWithdrawRecordService.queryAll(criteria), WooshopWithdrawRecordDto.class), response);
    }

    @GetMapping
    @Log("查询提现记录")
    @ApiOperation("查询提现记录")
    @PreAuthorize("@el.check('admin','wooshopWithdrawRecord:list')")
    public ResponseEntity<PageResult<WooshopWithdrawRecordDto>> getWooshopWithdrawRecords(WooshopWithdrawRecordQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(wooshopWithdrawRecordService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增提现记录")
    @ApiOperation("新增提现记录")
    @PreAuthorize("@el.check('admin','wooshopWithdrawRecord:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody WooshopWithdrawRecord resources){
        return new ResponseEntity<>(wooshopWithdrawRecordService.save(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改提现记录")
    @ApiOperation("修改提现记录")
    @PreAuthorize("@el.check('admin','wooshopWithdrawRecord:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody WooshopWithdrawRecord resources){
        wooshopWithdrawRecordService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除提现记录")
    @ApiOperation("删除提现记录")
    @PreAuthorize("@el.check('admin','wooshopWithdrawRecord:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        Arrays.asList(ids).forEach(id->{
            wooshopWithdrawRecordService.removeById(id);
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

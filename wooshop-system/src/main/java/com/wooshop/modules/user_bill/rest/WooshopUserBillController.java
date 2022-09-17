
package com.wooshop.modules.user_bill.rest;

import com.wooshop.modules.user_bill.service.dto.WooshopUserBillQueryCriteria;
import java.util.Arrays;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import com.wooshop.dozer.service.IGenerator;
import javax.servlet.http.HttpServletResponse;
import com.wooshop.domain.PageResult;
import com.wooshop.modules.user_bill.domain.WooshopUserBill;
import com.wooshop.modules.user_bill.service.WooshopUserBillService;


import com.wooshop.modules.user_bill.service.dto.WooshopUserBillDto;
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
* @date 2022-02-11
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@AllArgsConstructor
@Api(tags = "会员记录表管理")
@RestController
@RequestMapping("/api/wooshopUserBill")
public class WooshopUserBillController {

    private final WooshopUserBillService wooshopUserBillService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','wooshopUserBill:list')")
    public void download(HttpServletResponse response, WooshopUserBillQueryCriteria criteria) throws IOException {
        wooshopUserBillService.download(generator.convert(wooshopUserBillService.queryAll(criteria), WooshopUserBillDto.class), response);
    }

    @GetMapping
    @Log("查询会员记录表")
    @ApiOperation("查询会员记录表")
    @PreAuthorize("@el.check('admin','wooshopUserBill:list')")
    public ResponseEntity<PageResult<WooshopUserBillDto>> getWooshopUserBills(WooshopUserBillQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(wooshopUserBillService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增会员记录表")
    @ApiOperation("新增会员记录表")
    @PreAuthorize("@el.check('admin','wooshopUserBill:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody WooshopUserBill resources){
        return new ResponseEntity<>(wooshopUserBillService.addAndUpdate(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改会员记录表")
    @ApiOperation("修改会员记录表")
    @PreAuthorize("@el.check('admin','wooshopUserBill:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody WooshopUserBill resources){
        wooshopUserBillService.addAndUpdate(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除会员记录表")
    @ApiOperation("删除会员记录表")
    @PreAuthorize("@el.check('admin','wooshopUserBill:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        Arrays.asList(ids).forEach(id->{
            wooshopUserBillService.cacheRemoveById(id);
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

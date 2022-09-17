
package com.wooshop.modules.user_visit_record.rest;

import com.wooshop.modules.user_visit_record.service.dto.WooshopUserVisitRecordQueryCriteria;
import java.util.Arrays;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import com.wooshop.dozer.service.IGenerator;
import javax.servlet.http.HttpServletResponse;
import com.wooshop.domain.PageResult;
import com.wooshop.modules.user_visit_record.domain.WooshopUserVisitRecord;
import com.wooshop.modules.user_visit_record.service.WooshopUserVisitRecordService;


import com.wooshop.modules.user_visit_record.service.dto.WooshopUserVisitRecordDto;
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
* @date 2022-03-24
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@AllArgsConstructor
@Api(tags = "用户浏览记录管理")
@RestController
@RequestMapping("/api/wooshopUserVisitRecord")
public class WooshopUserVisitRecordController {

    private final WooshopUserVisitRecordService wooshopUserVisitRecordService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','wooshopUserVisitRecord:list')")
    public void download(HttpServletResponse response, WooshopUserVisitRecordQueryCriteria criteria) throws IOException {
        wooshopUserVisitRecordService.download(generator.convert(wooshopUserVisitRecordService.queryAll(criteria), WooshopUserVisitRecordDto.class), response);
    }

    @GetMapping
    @Log("查询用户浏览记录")
    @ApiOperation("查询用户浏览记录")
    @PreAuthorize("@el.check('admin','wooshopUserVisitRecord:list')")
    public ResponseEntity<PageResult<WooshopUserVisitRecordDto>> getWooshopUserVisitRecords(WooshopUserVisitRecordQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(wooshopUserVisitRecordService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增用户浏览记录")
    @ApiOperation("新增用户浏览记录")
    @PreAuthorize("@el.check('admin','wooshopUserVisitRecord:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody WooshopUserVisitRecord resources){
        return new ResponseEntity<>(wooshopUserVisitRecordService.addAndUpdateAsync(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改用户浏览记录")
    @ApiOperation("修改用户浏览记录")
    @PreAuthorize("@el.check('admin','wooshopUserVisitRecord:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody WooshopUserVisitRecord resources){
        wooshopUserVisitRecordService.addAndUpdateAsync(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除用户浏览记录")
    @ApiOperation("删除用户浏览记录")
    @PreAuthorize("@el.check('admin','wooshopUserVisitRecord:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        Arrays.asList(ids).forEach(id->{
            wooshopUserVisitRecordService.cacheRemoveById(id);
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

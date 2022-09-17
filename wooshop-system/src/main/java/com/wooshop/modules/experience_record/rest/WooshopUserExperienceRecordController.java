
package com.wooshop.modules.experience_record.rest;

import com.wooshop.modules.experience_record.service.dto.WooshopUserExperienceRecordQueryCriteria;
import java.util.Arrays;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import com.wooshop.dozer.service.IGenerator;
import javax.servlet.http.HttpServletResponse;
import com.wooshop.domain.PageResult;
import com.wooshop.modules.experience_record.domain.WooshopUserExperienceRecord;
import com.wooshop.modules.experience_record.service.WooshopUserExperienceRecordService;


import com.wooshop.modules.experience_record.service.dto.WooshopUserExperienceRecordDto;
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
* @date 2022-03-02
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@AllArgsConstructor
@Api(tags = "会员经验记录表管理")
@RestController
@RequestMapping("/api/wooshopUserExperienceRecord")
public class WooshopUserExperienceRecordController {

    private final WooshopUserExperienceRecordService wooshopUserExperienceRecordService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','wooshopUserExperienceRecord:list')")
    public void download(HttpServletResponse response, WooshopUserExperienceRecordQueryCriteria criteria) throws IOException {
        wooshopUserExperienceRecordService.download(generator.convert(wooshopUserExperienceRecordService.queryAll(criteria), WooshopUserExperienceRecordDto.class), response);
    }

    @GetMapping
    @Log("查询会员经验记录表")
    @ApiOperation("查询会员经验记录表")
    @PreAuthorize("@el.check('admin','wooshopUserExperienceRecord:list')")
    public ResponseEntity<PageResult<WooshopUserExperienceRecordDto>> getWooshopUserExperienceRecords(WooshopUserExperienceRecordQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(wooshopUserExperienceRecordService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增会员经验记录表")
    @ApiOperation("新增会员经验记录表")
    @PreAuthorize("@el.check('admin','wooshopUserExperienceRecord:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody WooshopUserExperienceRecord resources){
        return new ResponseEntity<>(wooshopUserExperienceRecordService.addAndUpdate(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改会员经验记录表")
    @ApiOperation("修改会员经验记录表")
    @PreAuthorize("@el.check('admin','wooshopUserExperienceRecord:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody WooshopUserExperienceRecord resources){
        wooshopUserExperienceRecordService.addAndUpdate(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除会员经验记录表")
    @ApiOperation("删除会员经验记录表")
    @PreAuthorize("@el.check('admin','wooshopUserExperienceRecord:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        Arrays.asList(ids).forEach(id->{
            wooshopUserExperienceRecordService.cacheRemoveById(id);
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }
}


package com.wooshop.modules.template.rest;

import com.wooshop.modules.template.service.dto.WooshopFreightTemplatePinkageQueryCriteria;
import java.util.Arrays;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import com.wooshop.dozer.service.IGenerator;
import javax.servlet.http.HttpServletResponse;
import com.wooshop.domain.PageResult;
import com.wooshop.modules.template.domain.WooshopFreightTemplatePinkage;
import com.wooshop.modules.template.service.WooshopFreightTemplatePinkageService;


import com.wooshop.modules.template.service.dto.WooshopFreightTemplatePinkageDto;
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
* @date 2021-11-20
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@AllArgsConstructor
@Api(tags = "运费包邮模板管理")
@RestController
@RequestMapping("/api/wooshopFreightTemplatePinkage")
public class WooshopFreightTemplatePinkageController {

    private final WooshopFreightTemplatePinkageService wooshopFreightTemplatePinkageService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','wooshopFreightTemplatePinkage:list')")
    public void download(HttpServletResponse response, WooshopFreightTemplatePinkageQueryCriteria criteria) throws IOException {
        wooshopFreightTemplatePinkageService.download(generator.convert(wooshopFreightTemplatePinkageService.queryAll(criteria), WooshopFreightTemplatePinkageDto.class), response);
    }

    @GetMapping
    @Log("查询运费包邮模板")
    @ApiOperation("查询运费包邮模板")
    @PreAuthorize("@el.check('admin','wooshopFreightTemplatePinkage:list')")
    public ResponseEntity<PageResult<WooshopFreightTemplatePinkageDto>> getWooshopFreightTemplatePinkages(WooshopFreightTemplatePinkageQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(wooshopFreightTemplatePinkageService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增运费包邮模板")
    @ApiOperation("新增运费包邮模板")
    @PreAuthorize("@el.check('admin','wooshopFreightTemplatePinkage:list')")
    public ResponseEntity<Object> create(@Validated @RequestBody WooshopFreightTemplatePinkage resources){
        return new ResponseEntity<>(wooshopFreightTemplatePinkageService.save(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改运费包邮模板")
    @ApiOperation("修改运费包邮模板")
    @PreAuthorize("@el.check('admin','wooshopFreightTemplatePinkage:list')")
    public ResponseEntity<Object> update(@Validated @RequestBody WooshopFreightTemplatePinkage resources){
        wooshopFreightTemplatePinkageService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除运费包邮模板")
    @ApiOperation("删除运费包邮模板")
    @PreAuthorize("@el.check('admin','wooshopFreightTemplatePinkage:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        Arrays.asList(ids).forEach(id->{
            wooshopFreightTemplatePinkageService.removeById(id);
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }
}


package com.wooshop.modules.template.rest;

import com.wooshop.modules.template.service.dto.WooshopFreightTemplateAssignQueryCriteria;
import java.util.Arrays;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import com.wooshop.dozer.service.IGenerator;
import javax.servlet.http.HttpServletResponse;
import com.wooshop.domain.PageResult;
import com.wooshop.modules.template.domain.WooshopFreightTemplateAssign;
import com.wooshop.modules.template.service.WooshopFreightTemplateAssignService;


import com.wooshop.modules.template.service.dto.WooshopFreightTemplateAssignDto;
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
@Api(tags = "运费区域模板管理")
@RestController
@RequestMapping("/api/wooshopFreightTemplateAssign")
public class WooshopFreightTemplateAssignController {

    private final WooshopFreightTemplateAssignService wooshopFreightTemplateAssignService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','wooshopFreightTemplateAssign:list')")
    public void download(HttpServletResponse response, WooshopFreightTemplateAssignQueryCriteria criteria) throws IOException {
        wooshopFreightTemplateAssignService.download(generator.convert(wooshopFreightTemplateAssignService.queryAll(criteria), WooshopFreightTemplateAssignDto.class), response);
    }

    @GetMapping
    @Log("查询运费区域模板")
    @ApiOperation("查询运费区域模板")
    @PreAuthorize("@el.check('admin','wooshopFreightTemplateAssign:list')")
    public ResponseEntity<PageResult<WooshopFreightTemplateAssignDto>> getWooshopFreightTemplateAssigns(WooshopFreightTemplateAssignQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(wooshopFreightTemplateAssignService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增运费区域模板")
    @ApiOperation("新增运费区域模板")
    @PreAuthorize("@el.check('admin','wooshopFreightTemplateAssign:list')")
    public ResponseEntity<Object> create(@Validated @RequestBody WooshopFreightTemplateAssign resources){
        return new ResponseEntity<>(wooshopFreightTemplateAssignService.save(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改运费区域模板")
    @ApiOperation("修改运费区域模板")
    @PreAuthorize("@el.check('admin','wooshopFreightTemplateAssign:list')")
    public ResponseEntity<Object> update(@Validated @RequestBody WooshopFreightTemplateAssign resources){
        wooshopFreightTemplateAssignService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除运费区域模板")
    @ApiOperation("删除运费区域模板")
    @PreAuthorize("@el.check('admin','wooshopFreightTemplateAssign:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        Arrays.asList(ids).forEach(id->{
            wooshopFreightTemplateAssignService.removeById(id);
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

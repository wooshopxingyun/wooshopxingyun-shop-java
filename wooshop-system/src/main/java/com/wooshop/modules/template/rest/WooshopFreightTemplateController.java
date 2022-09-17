
package com.wooshop.modules.template.rest;

import com.wooshop.modules.template.service.WooshopFreightTemplateAssignService;
import com.wooshop.modules.template.service.WooshopFreightTemplatePinkageService;
import com.wooshop.modules.template.service.dto.WooshopFreightTemplateQueryCriteria;
import java.util.Arrays;

import com.wooshop.modules.template.vo.WooshopFreightTemplateVo;
import com.wooshop.utils.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import com.wooshop.dozer.service.IGenerator;
import javax.servlet.http.HttpServletResponse;
import com.wooshop.domain.PageResult;
import com.wooshop.modules.template.domain.WooshopFreightTemplate;
import com.wooshop.modules.template.service.WooshopFreightTemplateService;


import com.wooshop.modules.template.service.dto.WooshopFreightTemplateDto;
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
@Api(tags = "运费模板管理")
@RestController
@RequestMapping("/api/wooshopFreightTemplate")
public class WooshopFreightTemplateController {

    private final WooshopFreightTemplateService wooshopFreightTemplateService;
    private final WooshopFreightTemplateAssignService wooshopFreightTemplateAssignService;
    private final WooshopFreightTemplatePinkageService wooshopfreighttemplatePinkageService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','wooshopFreightTemplate:list')")
    public void download(HttpServletResponse response, WooshopFreightTemplateQueryCriteria criteria) throws IOException {
        wooshopFreightTemplateService.download(generator.convert(wooshopFreightTemplateService.queryAll(criteria), WooshopFreightTemplateDto.class), response);
    }

    @GetMapping
    @Log("查询运费模板")
    @ApiOperation("查询运费模板")
    @PreAuthorize("@el.check('admin','wooshopFreightTemplate:list')")
    public ResponseEntity<PageResult<WooshopFreightTemplateVo>> getWooshopFreightTemplates(WooshopFreightTemplateQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(wooshopFreightTemplateService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增运费模板")
    @ApiOperation("新增运费模板")
    @PreAuthorize("@el.check('admin','wooshopFreightTemplate:list')")
    public ResponseEntity<Object> create(@Validated @RequestBody WooshopFreightTemplateVo resources){
        resources.setUid(SecurityUtils.getCurrentUserId());//设置用户id
        return new ResponseEntity<>(wooshopFreightTemplateService.saveAndUpdate(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改运费模板")
    @ApiOperation("修改运费模板")
    @PreAuthorize("@el.check('admin','wooshopFreightTemplate:list')")
    public ResponseEntity<Object> update(@Validated @RequestBody WooshopFreightTemplate resources){
        wooshopFreightTemplateService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除运费模板")
    @ApiOperation("删除运费模板")
    @PreAuthorize("@el.check('admin','wooshopFreightTemplate:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        Arrays.asList(ids).forEach(id->{
            wooshopFreightTemplateService.deleteById(id);
//            wooshopFreightTemplateAssignService.pinkageRemoveByTempId(id);//物理删除
//            wooshopfreighttemplatePinkageService.pinkageRemoveByTempId(id);//物理删除
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

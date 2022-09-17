
package com.wooshop.modules.wechat_template.rest;

import com.wooshop.modules.wechat_template.service.dto.WooshopWechatTemplateQueryCriteria;
import java.util.Arrays;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import com.wooshop.dozer.service.IGenerator;
import javax.servlet.http.HttpServletResponse;
import com.wooshop.domain.PageResult;
import com.wooshop.modules.wechat_template.domain.WooshopWechatTemplate;
import com.wooshop.modules.wechat_template.service.WooshopWechatTemplateService;


import com.wooshop.modules.wechat_template.service.dto.WooshopWechatTemplateDto;
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
* @date 2022-02-22
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@AllArgsConstructor
@Api(tags = "微信模板管理")
@RestController
@RequestMapping("/api/wooshopWechatTemplate")
public class WooshopWechatTemplateController {

    private final WooshopWechatTemplateService wooshopWechatTemplateService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','wooshopWechatTemplate:list')")
    public void download(HttpServletResponse response, WooshopWechatTemplateQueryCriteria criteria) throws IOException {
        wooshopWechatTemplateService.download(generator.convert(wooshopWechatTemplateService.queryAll(criteria), WooshopWechatTemplateDto.class), response);
    }

    @GetMapping
    @Log("查询微信模板")
    @ApiOperation("查询微信模板")
    @PreAuthorize("@el.check('admin','wooshopWechatTemplate:list')")
    public ResponseEntity<PageResult<WooshopWechatTemplateDto>> getWooshopWechatTemplates(WooshopWechatTemplateQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(wooshopWechatTemplateService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增微信模板")
    @ApiOperation("新增微信模板")
    @PreAuthorize("@el.check('admin','wooshopWechatTemplate:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody WooshopWechatTemplate resources){
        return new ResponseEntity<>(wooshopWechatTemplateService.addAndUpdate(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改微信模板")
    @ApiOperation("修改微信模板")
    @PreAuthorize("@el.check('admin','wooshopWechatTemplate:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody WooshopWechatTemplate resources){
        wooshopWechatTemplateService.addAndUpdate(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除微信模板")
    @ApiOperation("删除微信模板")
    @PreAuthorize("@el.check('admin','wooshopWechatTemplate:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        Arrays.asList(ids).forEach(id->{
            wooshopWechatTemplateService.cacheRemoveById(id);
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

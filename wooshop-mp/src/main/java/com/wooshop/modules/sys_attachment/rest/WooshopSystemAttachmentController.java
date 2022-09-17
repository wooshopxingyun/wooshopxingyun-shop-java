
package com.wooshop.modules.sys_attachment.rest;

import com.wooshop.modules.sys_attachment.service.dto.WooshopSystemAttachmentQueryCriteria;
import java.util.Arrays;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import com.wooshop.dozer.service.IGenerator;
import javax.servlet.http.HttpServletResponse;
import com.wooshop.domain.PageResult;
import com.wooshop.modules.sys_attachment.domain.WooshopSystemAttachment;
import com.wooshop.modules.sys_attachment.service.WooshopSystemAttachmentService;


import com.wooshop.modules.sys_attachment.service.dto.WooshopSystemAttachmentDto;
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
* @date 2022-06-02
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@AllArgsConstructor
@Api(tags = "附件管理管理")
@RestController
@RequestMapping("/api/wooshopSystemAttachment")
public class WooshopSystemAttachmentController {

    private final WooshopSystemAttachmentService wooshopSystemAttachmentService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','wooshopSystemAttachment:list')")
    public void download(HttpServletResponse response, WooshopSystemAttachmentQueryCriteria criteria) throws IOException {
        wooshopSystemAttachmentService.download(generator.convert(wooshopSystemAttachmentService.queryAll(criteria), WooshopSystemAttachmentDto.class), response);
    }

    @GetMapping
    @Log("查询附件管理")
    @ApiOperation("查询附件管理")
    @PreAuthorize("@el.check('admin','wooshopSystemAttachment:list')")
    public ResponseEntity<PageResult<WooshopSystemAttachmentDto>> getWooshopSystemAttachments(WooshopSystemAttachmentQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(wooshopSystemAttachmentService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增附件管理")
    @ApiOperation("新增附件管理")
    @PreAuthorize("@el.check('admin','wooshopSystemAttachment:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody WooshopSystemAttachment resources){
        return new ResponseEntity<>(wooshopSystemAttachmentService.addAndUpdate(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改附件管理")
    @ApiOperation("修改附件管理")
    @PreAuthorize("@el.check('admin','wooshopSystemAttachment:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody WooshopSystemAttachment resources){
        wooshopSystemAttachmentService.addAndUpdate(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除附件管理")
    @ApiOperation("删除附件管理")
    @PreAuthorize("@el.check('admin','wooshopSystemAttachment:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        Arrays.asList(ids).forEach(id->{
            wooshopSystemAttachmentService.cacheRemoveById(id);
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

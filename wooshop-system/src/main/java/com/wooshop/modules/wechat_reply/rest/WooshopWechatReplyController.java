
package com.wooshop.modules.wechat_reply.rest;

import com.wooshop.modules.wechat_reply.service.dto.WooshopWechatReplyQueryCriteria;
import java.util.Arrays;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import com.wooshop.dozer.service.IGenerator;
import javax.servlet.http.HttpServletResponse;
import com.wooshop.domain.PageResult;
import com.wooshop.modules.wechat_reply.domain.WooshopWechatReply;
import com.wooshop.modules.wechat_reply.service.WooshopWechatReplyService;


import com.wooshop.modules.wechat_reply.service.dto.WooshopWechatReplyDto;
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
* @date 2022-02-23
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@AllArgsConstructor
@Api(tags = "微信公众号回复表管理")
@RestController
@RequestMapping("/api/wooshopWechatReply")
public class WooshopWechatReplyController {

    private final WooshopWechatReplyService wooshopWechatReplyService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','wooshopWechatReply:list')")
    public void download(HttpServletResponse response, WooshopWechatReplyQueryCriteria criteria) throws IOException {
        wooshopWechatReplyService.download(generator.convert(wooshopWechatReplyService.queryAll(criteria), WooshopWechatReplyDto.class), response);
    }

    @GetMapping
    @Log("查询微信公众号回复表")
    @ApiOperation("查询微信公众号回复表")
    @PreAuthorize("@el.check('admin','wooshopWechatReply:list')")
    public ResponseEntity<PageResult<WooshopWechatReplyDto>> getWooshopWechatReplys(WooshopWechatReplyQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(wooshopWechatReplyService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增微信公众号回复表")
    @ApiOperation("新增微信公众号回复表")
    @PreAuthorize("@el.check('admin','wooshopWechatReply:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody WooshopWechatReply resources){
        return new ResponseEntity<>(wooshopWechatReplyService.addAndUpdate(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改微信公众号回复表")
    @ApiOperation("修改微信公众号回复表")
    @PreAuthorize("@el.check('admin','wooshopWechatReply:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody WooshopWechatReply resources){
        wooshopWechatReplyService.addAndUpdate(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除微信公众号回复表")
    @ApiOperation("删除微信公众号回复表")
    @PreAuthorize("@el.check('admin','wooshopWechatReply:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        Arrays.asList(ids).forEach(id->{
            wooshopWechatReplyService.cacheRemoveById(id);
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

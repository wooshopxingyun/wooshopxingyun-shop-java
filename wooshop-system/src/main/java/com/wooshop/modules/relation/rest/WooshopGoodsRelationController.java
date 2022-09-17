
package com.wooshop.modules.relation.rest;

import com.wooshop.modules.relation.service.dto.WooshopGoodsRelationQueryCriteria;
import java.util.Arrays;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import com.wooshop.dozer.service.IGenerator;
import javax.servlet.http.HttpServletResponse;
import com.wooshop.domain.PageResult;
import com.wooshop.modules.relation.domain.WooshopGoodsRelation;
import com.wooshop.modules.relation.service.WooshopGoodsRelationService;


import com.wooshop.modules.relation.service.dto.WooshopGoodsRelationDto;
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
* @date 2022-01-11
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@AllArgsConstructor
@Api(tags = "收藏/推荐表管理")
@RestController
@RequestMapping("/api/wooshopGoodsRelation")
public class WooshopGoodsRelationController {

    private final WooshopGoodsRelationService wooshopGoodsRelationService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','wooshopGoodsRelation:list')")
    public void download(HttpServletResponse response, WooshopGoodsRelationQueryCriteria criteria) throws IOException {
        wooshopGoodsRelationService.download(generator.convert(wooshopGoodsRelationService.queryAll(criteria), WooshopGoodsRelationDto.class), response);
    }

    @GetMapping
    @Log("查询收藏/推荐表")
    @ApiOperation("查询收藏/推荐表")
    @PreAuthorize("@el.check('admin','wooshopGoodsRelation:list')")
    public ResponseEntity<PageResult<WooshopGoodsRelationDto>> getWooshopGoodsRelations(WooshopGoodsRelationQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(wooshopGoodsRelationService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增收藏/推荐表")
    @ApiOperation("新增收藏/推荐表")
    @PreAuthorize("@el.check('admin','wooshopGoodsRelation:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody WooshopGoodsRelation resources){
        return new ResponseEntity<>(wooshopGoodsRelationService.addAndUpdate(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改收藏/推荐表")
    @ApiOperation("修改收藏/推荐表")
    @PreAuthorize("@el.check('admin','wooshopGoodsRelation:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody WooshopGoodsRelation resources){
        wooshopGoodsRelationService.addAndUpdate(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除收藏/推荐表")
    @ApiOperation("删除收藏/推荐表")
    @PreAuthorize("@el.check('admin','wooshopGoodsRelation:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        Arrays.asList(ids).forEach(id->{
            wooshopGoodsRelationService.cacheRemoveById(id);
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

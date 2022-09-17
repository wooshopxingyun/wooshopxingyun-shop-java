
package com.wooshop.modules.stores.rest;

import com.wooshop.modules.stores.service.dto.WooshopStoresQueryCriteria;
import java.util.Arrays;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import com.wooshop.dozer.service.IGenerator;
import javax.servlet.http.HttpServletResponse;
import com.wooshop.domain.PageResult;
import com.wooshop.modules.stores.domain.WooshopStores;
import com.wooshop.modules.stores.service.WooshopStoresService;


import com.wooshop.modules.stores.service.dto.WooshopStoresDto;
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
* @date 2021-12-21
* 注意：
* 本软件为www.wooshopxingyun.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@AllArgsConstructor
@Api(tags = "店铺列表管理")
@RestController
@RequestMapping("/api/wooshopStores")

public class WooshopStoresController {

    private final WooshopStoresService wooshopStoresService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','wooshopStores:list')")
    public void download(HttpServletResponse response, WooshopStoresQueryCriteria criteria) throws IOException {
        wooshopStoresService.download(generator.convert(wooshopStoresService.queryAll(criteria), WooshopStoresDto.class), response);
    }

    @GetMapping
    @Log("查询店铺列表")
    @ApiOperation("查询店铺列表")
    @PreAuthorize("@el.check('admin','wooshopStores:list')")
    public ResponseEntity<PageResult<WooshopStoresDto>> getWooshopStoress(WooshopStoresQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(wooshopStoresService.queryAllPage(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增店铺列表")
    @ApiOperation("新增店铺列表")
    @PreAuthorize("@el.check('admin','wooshopStores:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody WooshopStores resources){

        return new ResponseEntity<>(wooshopStoresService.addAndUpdate(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改店铺列表")
    @ApiOperation("修改店铺列表")
    @PreAuthorize("@el.check('admin','wooshopStores:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody WooshopStores resources){
//        wooshopStoresService.updateById(resources);
        wooshopStoresService.addAndUpdate(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除店铺列表")
    @ApiOperation("删除店铺列表")
    @PreAuthorize("@el.check('admin','wooshopStores:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        Arrays.asList(ids).forEach(id->{
            wooshopStoresService.cacheRemoveById(id);
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

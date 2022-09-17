
package com.wooshop.modules.promise.rest;

import com.wooshop.modules.promise.service.dto.WooshopStoreProductPromiseQueryCriteria;
import java.util.Arrays;

import com.wooshop.utils.SecurityUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import com.wooshop.dozer.service.IGenerator;
import javax.servlet.http.HttpServletResponse;
import com.wooshop.domain.PageResult;
import com.wooshop.modules.promise.domain.WooshopStoreProductPromise;
import com.wooshop.modules.promise.service.WooshopStoreProductPromiseService;


import com.wooshop.modules.promise.service.dto.WooshopStoreProductPromiseDto;
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
* @date 2021-11-25
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@AllArgsConstructor
@Api(tags = "服务承诺管理")
@RestController
@RequestMapping("/api/wooshopStoreProductPromise")
@CacheConfig(cacheNames = "ProductPromise")
public class WooshopStoreProductPromiseController {

    private final WooshopStoreProductPromiseService wooshopStoreProductPromiseService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','wooshopStoreProductPromise:list')")
    public void download(HttpServletResponse response, WooshopStoreProductPromiseQueryCriteria criteria) throws IOException {
        wooshopStoreProductPromiseService.download(generator.convert(wooshopStoreProductPromiseService.queryAll(criteria), WooshopStoreProductPromiseDto.class), response);
    }

    @GetMapping
    @Log("查询服务承诺")
    @ApiOperation("查询服务承诺")
    @PreAuthorize("@el.check('admin','wooshopStoreProductPromise:list')")
    public ResponseEntity<PageResult<WooshopStoreProductPromiseDto>> getWooshopStoreProductPromises(WooshopStoreProductPromiseQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(wooshopStoreProductPromiseService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增服务承诺")
    @ApiOperation("新增服务承诺")
    @PreAuthorize("@el.check('admin','wooshopStoreProductPromise:list')")
    @CacheEvict(key = "'Promise:'+'*'",allEntries = true)
    public ResponseEntity<Object> create(@Validated @RequestBody WooshopStoreProductPromise resources){
        resources.setUid(SecurityUtils.getCurrentUserId());//设置用户id
        return new ResponseEntity<>(wooshopStoreProductPromiseService.save(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改服务承诺")
    @ApiOperation("修改服务承诺")
    @PreAuthorize("@el.check('admin','wooshopStoreProductPromise:list')")
    @CacheEvict(key = "'Promise:'+'*'",allEntries = true)
    public ResponseEntity<Object> update(@Validated @RequestBody WooshopStoreProductPromise resources){
        wooshopStoreProductPromiseService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除服务承诺")
    @ApiOperation("删除服务承诺")
    @PreAuthorize("@el.check('admin','wooshopStoreProductPromise:del')")
    @DeleteMapping
    @CacheEvict(key = "'Promise:'+'*'",allEntries = true)
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        Arrays.asList(ids).forEach(id->{
            wooshopStoreProductPromiseService.removeById(id);
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

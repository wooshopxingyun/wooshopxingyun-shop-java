
package com.wooshop.modules.unit.rest;

import com.wooshop.modules.unit.service.dto.WooshopStoreProductUnitQueryCriteria;

import java.util.Arrays;

import com.wooshop.utils.SecurityUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import com.wooshop.dozer.service.IGenerator;
import javax.servlet.http.HttpServletResponse;
import com.wooshop.domain.PageResult;
import com.wooshop.modules.unit.domain.WooshopStoreProductUnit;
import com.wooshop.modules.unit.service.WooshopStoreProductUnitService;


import com.wooshop.modules.unit.service.dto.WooshopStoreProductUnitDto;
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
* @date 2021-11-24
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@AllArgsConstructor
@Api(tags = "计量单位管理")
@RestController
@RequestMapping("/api/wooshopStoreProductUnit")
@CacheConfig(cacheNames = "ProductUnit")
public class WooshopStoreProductUnitController {

    private final WooshopStoreProductUnitService wooshopStoreProductUnitService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','wooshopStoreProductUnit:list')")
    public void download(HttpServletResponse response, WooshopStoreProductUnitQueryCriteria criteria) throws IOException {
        wooshopStoreProductUnitService.download(generator.convert(wooshopStoreProductUnitService.queryAll(criteria), WooshopStoreProductUnitDto.class), response);
    }

    @GetMapping
    @Log("查询计量单位")
    @ApiOperation("查询计量单位")
    @PreAuthorize("@el.check('admin','wooshopStoreProductUnit:list')")
    public ResponseEntity<PageResult<WooshopStoreProductUnitDto>> getWooshopStoreProductUnits(WooshopStoreProductUnitQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(wooshopStoreProductUnitService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增计量单位")
    @ApiOperation("新增计量单位")
    @PreAuthorize("@el.check('admin','wooshopStoreProductUnit:list')")
    @CacheEvict(key = "'Unit:'+'*'",allEntries = true)
    public ResponseEntity<Object> create(@Validated @RequestBody WooshopStoreProductUnit resources){
        resources.setUid(SecurityUtils.getCurrentUserId());//设置用户id
        try {
            wooshopStoreProductUnitService.save(resources);
        }catch (Exception e){
            System.out.println(e);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改计量单位")
    @ApiOperation("修改计量单位")
    @PreAuthorize("@el.check('admin','wooshopStoreProductUnit:list')")
    @CacheEvict(key = "'Unit:'+'*'",allEntries = true)
    public ResponseEntity<Object> update(@Validated @RequestBody WooshopStoreProductUnit resources){
        wooshopStoreProductUnitService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除计量单位")
    @ApiOperation("删除计量单位")
    @PreAuthorize("@el.check('admin','wooshopStoreProductUnit:del')")
    @DeleteMapping
    @CacheEvict(key = "'Unit:'+'*'",allEntries = true)
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        Arrays.asList(ids).forEach(id->{
            wooshopStoreProductUnitService.removeById(id);
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

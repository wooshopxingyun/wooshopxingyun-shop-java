
package com.wooshop.modules.syscity.rest;

import com.wooshop.modules.syscity.service.dto.WooshopSysCityQueryCriteria;
import java.util.Arrays;

import com.wooshop.modules.syscity.service.vo.WooshopSysCityVo;
import com.wooshop.utils.CacheKey;
import com.wooshop.utils.RedisUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import com.wooshop.dozer.service.IGenerator;
import javax.servlet.http.HttpServletResponse;
import com.wooshop.domain.PageResult;
import com.wooshop.modules.syscity.domain.WooshopSysCity;
import com.wooshop.modules.syscity.service.WooshopSysCityService;


import com.wooshop.modules.syscity.service.dto.WooshopSysCityDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import com.wooshop.annotation.Log;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import java.util.List;


/**
* @author woo
* @date 2021-11-20
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@AllArgsConstructor
@Api(tags = "城市配置表管理")
@RestController
@RequestMapping("/api/wooshopSysCity")
public class WooshopSysCityController {

    private final WooshopSysCityService wooshopSysCityService;
    private final IGenerator generator;
    private final RedisUtils redisUtils;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','wooshopSysCity:list')")
    public void download(HttpServletResponse response, WooshopSysCityQueryCriteria criteria) throws IOException {
        wooshopSysCityService.download(generator.convert(wooshopSysCityService.queryAll(criteria), WooshopSysCityDto.class), response);
    }

    @GetMapping
    @Log("查询城市配置表")
    @ApiOperation("查询城市配置表")
    @PreAuthorize("@el.check('admin','wooshopSysCity:list')")
    public ResponseEntity<PageResult<WooshopSysCityDto>> getWooshopSysCitys( WooshopSysCityQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(wooshopSysCityService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @GetMapping(value = "/gettree")
    @Log("查询树形结构城市配置表")
    @ApiOperation("查询树形结构城市配置表")
    @PreAuthorize("@el.check('admin','wooshopSysCity:list')")
    public ResponseEntity<List<WooshopSysCityVo>> getWooshopSysCitysTree(){
        return new ResponseEntity<>(wooshopSysCityService.getCityTree(),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增城市配置表")
    @ApiOperation("新增城市配置表")
    @PreAuthorize("@el.check('admin','wooshopSysCity:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody WooshopSysCity resources){
        return new ResponseEntity<>(wooshopSysCityService.addAndUpdate(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改城市配置表")
    @ApiOperation("修改城市配置表")
    @PreAuthorize("@el.check('admin','wooshopSysCity:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody WooshopSysCity resources){
//        wooshopSysCityService.updateById(resources);
        wooshopSysCityService.addAndUpdate(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除城市配置表")
    @ApiOperation("删除城市配置表")
    @PreAuthorize("@el.check('admin','wooshopSysCity:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        Arrays.asList(ids).forEach(id->{
            wooshopSysCityService.removeById(id);
        });
        redisUtils.del(CacheKey.WOOSHOP_CITYTREE);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

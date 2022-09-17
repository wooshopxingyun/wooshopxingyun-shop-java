
package com.wooshop.modules.goods.rest;

import com.wooshop.modules.goods.service.dto.WooshopStoreGoodsAttrDetailsQueryCriteria;
import java.util.Arrays;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import com.wooshop.dozer.service.IGenerator;
import javax.servlet.http.HttpServletResponse;
import com.wooshop.domain.PageResult;
import com.wooshop.modules.goods.domain.WooshopStoreGoodsAttrDetails;
import com.wooshop.modules.goods.service.WooshopStoreGoodsAttrDetailsService;


import com.wooshop.modules.goods.service.dto.WooshopStoreGoodsAttrDetailsDto;
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
* @date 2021-12-05
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@AllArgsConstructor
@Api(tags = "属性详情管理")
@RestController
@RequestMapping("/api/wooshopStoreGoodsAttrDetails")
public class WooshopStoreGoodsAttrDetailsController {

    private final WooshopStoreGoodsAttrDetailsService wooshopStoreGoodsAttrDetailsService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','wooshopStoreGoodsAttrDetails:list')")
    public void download(HttpServletResponse response, WooshopStoreGoodsAttrDetailsQueryCriteria criteria) throws IOException {
        wooshopStoreGoodsAttrDetailsService.download(generator.convert(wooshopStoreGoodsAttrDetailsService.queryAll(criteria), WooshopStoreGoodsAttrDetailsDto.class), response);
    }

    @GetMapping
    @Log("查询属性详情")
    @ApiOperation("查询属性详情")
    @PreAuthorize("@el.check('admin','wooshopStoreGoodsAttrDetails:list')")
    public ResponseEntity<PageResult<WooshopStoreGoodsAttrDetailsDto>> getWooshopStoreGoodsAttrDetailss(WooshopStoreGoodsAttrDetailsQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(wooshopStoreGoodsAttrDetailsService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增属性详情")
    @ApiOperation("新增属性详情")
    @PreAuthorize("@el.check('admin','wooshopStoreGoodsAttrDetails:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody WooshopStoreGoodsAttrDetails resources){
        return new ResponseEntity<>(wooshopStoreGoodsAttrDetailsService.save(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改属性详情")
    @ApiOperation("修改属性详情")
    @PreAuthorize("@el.check('admin','wooshopStoreGoodsAttrDetails:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody WooshopStoreGoodsAttrDetails resources){
        wooshopStoreGoodsAttrDetailsService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除属性详情")
    @ApiOperation("删除属性详情")
    @PreAuthorize("@el.check('admin','wooshopStoreGoodsAttrDetails:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        Arrays.asList(ids).forEach(id->{
            wooshopStoreGoodsAttrDetailsService.removeById(id);
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }
}


package com.wooshop.modules.goods.rest;

import com.wooshop.modules.goods.service.dto.WooshopStoreGoodsSukQueryCriteria;
import java.util.Arrays;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import com.wooshop.dozer.service.IGenerator;
import javax.servlet.http.HttpServletResponse;
import com.wooshop.domain.PageResult;
import com.wooshop.modules.goods.domain.WooshopStoreGoodsSuk;
import com.wooshop.modules.goods.service.WooshopStoreGoodsSukService;


import com.wooshop.modules.goods.service.dto.WooshopStoreGoodsSukDto;
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
* @date 2021-12-01
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@AllArgsConstructor
@Api(tags = "商品规格 suk管理")
@RestController
@RequestMapping("/api/wooshopStoreGoodsSuk")
public class WooshopStoreGoodsSukController {

    private final WooshopStoreGoodsSukService wooshopStoreGoodsSukService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','wooshopStoreGoodsSuk:list')")
    public void download(HttpServletResponse response, WooshopStoreGoodsSukQueryCriteria criteria) throws IOException {
        wooshopStoreGoodsSukService.download(generator.convert(wooshopStoreGoodsSukService.queryAll(criteria), WooshopStoreGoodsSukDto.class), response);
    }

    @GetMapping
    @Log("查询商品规格 suk")
    @ApiOperation("查询商品规格 suk")
    @PreAuthorize("@el.check('admin','wooshopStoreGoodsSuk:list')")
    public ResponseEntity<PageResult<WooshopStoreGoodsSukDto>> getWooshopStoreGoodsSuks(WooshopStoreGoodsSukQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(wooshopStoreGoodsSukService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增商品规格 suk")
    @ApiOperation("新增商品规格 suk")
    @PreAuthorize("@el.check('admin','wooshopStoreGoodsSuk:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody WooshopStoreGoodsSuk resources){
        return new ResponseEntity<>(wooshopStoreGoodsSukService.save(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改商品规格 suk")
    @ApiOperation("修改商品规格 suk")
    @PreAuthorize("@el.check('admin','wooshopStoreGoodsSuk:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody WooshopStoreGoodsSuk resources){
        wooshopStoreGoodsSukService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除商品规格 suk")
    @ApiOperation("删除商品规格 suk")
    @PreAuthorize("@el.check('admin','wooshopStoreGoodsSuk:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        Arrays.asList(ids).forEach(id->{
            wooshopStoreGoodsSukService.removeById(id);
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }
}


package com.wooshop.modules.specification.rest;

import com.wooshop.annotation.Log;
import com.wooshop.domain.PageResult;
import com.wooshop.dozer.service.IGenerator;
import com.wooshop.modules.specification.domain.WooshopStoreProductSpecification;
import com.wooshop.modules.specification.service.WooshopStoreProductSpecificationService;
import com.wooshop.modules.specification.service.dto.WooshopStoreProductSpecificationDto;
import com.wooshop.modules.specification.service.dto.WooshopStoreProductSpecificationQueryCriteria;
import com.wooshop.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;


/**
* @author woo
* @date 2021-11-25
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@AllArgsConstructor
@Api(tags = "商品规格管理")
@RestController
@RequestMapping("/api/wooshopStoreProductSpecification")
@CacheConfig(cacheNames = "product")
public class WooshopStoreProductSpecificationController {

    private final WooshopStoreProductSpecificationService wooshopStoreProductSpecificationService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','wooshopStoreProductSpecification:list')")
    public void download(HttpServletResponse response, WooshopStoreProductSpecificationQueryCriteria criteria) throws IOException {
        wooshopStoreProductSpecificationService.download(generator.convert(wooshopStoreProductSpecificationService.queryAll(criteria), WooshopStoreProductSpecificationDto.class), response);
    }

    @GetMapping
    @Log("查询商品规格")
    @ApiOperation("查询商品规格")
    @PreAuthorize("@el.check('admin','wooshopStoreProductSpecification:list')")
    @ResponseBody
    public ResponseEntity<PageResult<WooshopStoreProductSpecificationDto>> getWooshopStoreProductSpecifications(WooshopStoreProductSpecificationQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(wooshopStoreProductSpecificationService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增商品规格")
    @ApiOperation("新增商品规格")
    @PreAuthorize("@el.check('admin','wooshopStoreProductSpecification:list')")
    @CacheEvict(key = "'specification:'",allEntries = true)
    public ResponseEntity<Object> create(@Validated @RequestBody WooshopStoreProductSpecification resources){
        resources.setUid(SecurityUtils.getCurrentUserId());//设置用户id
        return new ResponseEntity<>(wooshopStoreProductSpecificationService.save(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改商品规格")
    @ApiOperation("修改商品规格")
    @PreAuthorize("@el.check('admin','wooshopStoreProductSpecification:list')")
    @CacheEvict(key = "'specification:'",allEntries = true)
    public ResponseEntity<Object> update(@Validated @RequestBody WooshopStoreProductSpecification resources){
        wooshopStoreProductSpecificationService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除商品规格")
    @ApiOperation("删除商品规格")
    @PreAuthorize("@el.check('admin','wooshopStoreProductSpecification:del')")
    @DeleteMapping
    @CacheEvict(key = "'specification:'",allEntries = true)
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        Arrays.asList(ids).forEach(id->{
            wooshopStoreProductSpecificationService.removeById(id);
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

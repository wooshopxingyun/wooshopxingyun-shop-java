
package com.wooshop.modules.category.rest;

import com.wooshop.aspect.CacheRemove;
import com.wooshop.modules.category.service.dto.WooshopConfigCategoryQueryCriteria;

import java.util.Arrays;

import com.wooshop.modules.tools.service.LocalStorageService;
import com.wooshop.modules.tools.service.vo.LocalStorageVo;
import com.wooshop.utils.CacheKey;
import com.wooshop.utils.RedisUtils;
import com.wooshop.utils.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import com.wooshop.dozer.service.IGenerator;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import com.wooshop.domain.PageResult;
import com.wooshop.modules.category.domain.WooshopConfigCategory;
import com.wooshop.modules.category.service.WooshopConfigCategoryService;


import com.wooshop.modules.category.service.dto.WooshopConfigCategoryDto;
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
 * @date 2021-11-15
 * 注意：
 * 本软件为www.wooshop.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */


@AllArgsConstructor
@Api(tags = "系统分类管理")
@RestController
@RequestMapping("/api/wooshopConfigCategory")
//@CacheConfig(cacheNames = "wooshop")
public class WooshopConfigCategoryController {

    private final WooshopConfigCategoryService wooshopConfigCategoryService;
    private final IGenerator generator;
    private final RedisUtils redisUtils;
    private final LocalStorageService localStorageService;


    //获得图片分类
    @Log("获取图片分类")
    @ApiOperation("获取图片分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "类型ID | 类型，1 附件分类，2 文章分类", example = "1"),
            @ApiImplicitParam(name = "enabled", value = "-1=全部，0=未启用，1=已启用", example = "1"),
            @ApiImplicitParam(name = "name", value = "模糊搜索"),
            @ApiImplicitParam(name = "path", value = "模糊搜索路径")
    })
    @GetMapping(value = "/getimgcategory")
    @PreAuthorize("@el.check('admin','wooshopConfigCategory:list')")
    public ResponseEntity<WooshopConfigCategoryDto> getCategory(@RequestParam(name = "type") Integer type,
                                                                @RequestParam(name = "enabled", defaultValue = "1") Integer enabled,
                                                                @RequestParam(name = "name", required = false) String name,
                                                                @RequestParam(name = "path", required = false) String path) throws IOException {
        return new ResponseEntity(wooshopConfigCategoryService.getCategory(type, enabled, name, path, null), HttpStatus.OK);
    }

    //获得全部图片 和所有分类
    @Log("获得全部图片 和所有分类")
    @ApiOperation("获得全部图片 和所有分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "类型ID | 类型，1 附件分类，2 文章分类", example = "1"),
            @ApiImplicitParam(name = "enabled", value = "-1=全部，0=未启用，1=已启用", example = "1"),
            @ApiImplicitParam(name = "name", value = "模糊搜索"),
            @ApiImplicitParam(name = "path", value = "模糊搜索路径")
    })
    @GetMapping(value = "/getimgcategory/bycategoryall")
    @PreAuthorize("@el.check('admin','wooshopConfigCategory:list')")
    public ResponseEntity<LocalStorageVo> getCategoryAll(@RequestParam(name = "page",defaultValue = "1", required = false) int page,
                                                         @RequestParam(name = "limit", defaultValue = "10", required = false) int limit,
                                                         @RequestParam(name = "categoryPath", required = false) String categoryPath,
                                                         @RequestParam(name = "categoryId", defaultValue = "0", required = false) Integer categoryId,
                                                         @RequestParam(name = "realName", defaultValue = "", required = false) String realName) throws IOException {
        return new ResponseEntity(localStorageService.queryAllImg(page,limit,categoryPath,categoryId,realName), HttpStatus.OK);
    }

    @ApiOperation("新增图片分类,返回图片分类")
    @GetMapping(value = "/addcategory")
    @PreAuthorize("@el.check('admin','wooshopConfigCategory:list')")
    @CacheRemove(value = CacheKey.WOOSHOP_CATEGORY,key = "*")
    public ResponseEntity<WooshopConfigCategoryDto> addCategory(@NotNull(message = "父类id不能为空") @RequestParam(name = "categoryId") Integer categoryId,
                                                                @NotNull(message = "分类名称不能为空") @RequestParam(name = "name") String name,
                                                                @NotNull(message = "序号不能为空") @RequestParam(name = "sort") Integer sort,
                                                                @NotNull(message = "分类类型不能为空") @RequestParam(name = "type") Integer type) throws IOException {
        wooshopConfigCategoryService.addCategory(categoryId, name, sort, type);
//        redisUtils.del(CacheKey.WOOSHOP_CATEGORY);
        return new ResponseEntity(wooshopConfigCategoryService.getCategory(type, 1, null, null, null), HttpStatus.OK);
    }

    /**
     * 所有新增分类入口
     * @param resources
     * @return
     */
/*    @ApiOperation("新增分类")
    @PostMapping(value = "/addcategory/add")
    @PreAuthorize("@el.check('admin','wooshopConfigCategory:list')")
    public ResponseEntity<Object> addNewCategory(@Validated @RequestBody WooshopConfigCategory resources) {
        return new ResponseEntity<>(wooshopConfigCategoryService.save(resources), HttpStatus.CREATED);
    }*/



    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','wooshopConfigCategory:list')")
    public void download(HttpServletResponse response, WooshopConfigCategoryQueryCriteria criteria) throws IOException {
        wooshopConfigCategoryService.download(generator.convert(wooshopConfigCategoryService.queryAll(criteria), WooshopConfigCategoryDto.class), response);
    }

    @GetMapping
    @Log("查询系统分类")
    @ApiOperation("查询系统分类")
    @PreAuthorize("@el.check('admin','wooshopConfigCategory:list')")
    public ResponseEntity<PageResult<WooshopConfigCategoryDto>> getWooshopConfigCategorys(WooshopConfigCategoryQueryCriteria criteria, Pageable pageable) {
        if (criteria.getTypes()!=null){
            criteria.setType(criteria.getTypes());
        }
        return new ResponseEntity<>(wooshopConfigCategoryService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增系统分类")
    @ApiOperation("新增系统分类")
    @PreAuthorize("@el.check('admin','wooshopConfigCategory:add')")
    @CacheRemove(value = CacheKey.WOOSHOP_CATEGORY,key = "*")
    public ResponseEntity<Object> create(@Validated @RequestBody WooshopConfigCategory resources) {
        resources.setCreateUid(SecurityUtils.getCurrentUserId());//设置用户id
//        redisUtils.del(CacheKey.WOOSHOP_CATEGORY);
        return new ResponseEntity<>(wooshopConfigCategoryService.addNewCategory(resources), HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改系统分类")
    @ApiOperation("修改系统分类")
    @PreAuthorize("@el.check('admin','wooshopConfigCategory:list')")
    @CacheRemove(value = CacheKey.WOOSHOP_CATEGORY,key = "*")
    public ResponseEntity<Object> update(@Validated @RequestBody WooshopConfigCategory resources) {
//        redisUtils.del(CacheKey.WOOSHOP_CATEGORY+"*");
        wooshopConfigCategoryService.updateById(resources);
//        redisUtils.del(CacheKey.WOOSHOP_CATEGORY);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除系统分类")
    @ApiOperation("删除系统分类")
    @PreAuthorize("@el.check('admin','wooshopConfigCategory:del')")
    @DeleteMapping
    @CacheRemove(value = CacheKey.WOOSHOP_CATEGORY,key = "*")
    public ResponseEntity<Object> deleteAll(@RequestBody Integer[] ids) {
//        redisUtils.del(CacheKey.WOOSHOP_CATEGORY );
        Arrays.asList(ids).forEach(id -> {
            wooshopConfigCategoryService.removeById(id);
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

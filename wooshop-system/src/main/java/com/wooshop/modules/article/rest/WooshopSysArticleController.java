
package com.wooshop.modules.article.rest;

import com.wooshop.modules.article.service.dto.WooshopSysArticleQueryCriteria;
import java.util.Arrays;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import com.wooshop.dozer.service.IGenerator;
import javax.servlet.http.HttpServletResponse;
import com.wooshop.domain.PageResult;
import com.wooshop.modules.article.domain.WooshopSysArticle;
import com.wooshop.modules.article.service.WooshopSysArticleService;


import com.wooshop.modules.article.service.dto.WooshopSysArticleDto;
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
* @date 2021-12-19
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@AllArgsConstructor
@Api(tags = "新闻文章管理")
@RestController
@RequestMapping("/api/wooshopSysArticle")
public class WooshopSysArticleController {

    private final WooshopSysArticleService wooshopSysArticleService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','wooshopSysArticle:list')")
    public void download(HttpServletResponse response, WooshopSysArticleQueryCriteria criteria) throws IOException {
        wooshopSysArticleService.download(generator.convert(wooshopSysArticleService.queryAll(criteria), WooshopSysArticleDto.class), response);
    }

    @GetMapping
    @Log("查询新闻文章")
    @ApiOperation("查询新闻文章")
    @PreAuthorize("@el.check('admin','wooshopSysArticle:list')")
    public ResponseEntity<PageResult<WooshopSysArticleDto>> getWooshopSysArticles(WooshopSysArticleQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(wooshopSysArticleService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增新闻文章")
    @ApiOperation("新增新闻文章")
    @PreAuthorize("@el.check('admin','wooshopSysArticle:list')")
    public ResponseEntity<Object> create(@Validated @RequestBody WooshopSysArticle resources){
        return new ResponseEntity<>(wooshopSysArticleService.save(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改新闻文章")
    @ApiOperation("修改新闻文章")
    @PreAuthorize("@el.check('admin','wooshopSysArticle:list')")
    public ResponseEntity<Object> update(@Validated @RequestBody WooshopSysArticle resources){
        wooshopSysArticleService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除新闻文章")
    @ApiOperation("删除新闻文章")
    @PreAuthorize("@el.check('admin','wooshopSysArticle:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        Arrays.asList(ids).forEach(id->{
            wooshopSysArticleService.removeById(id);
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

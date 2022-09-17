
package com.wooshop.modules.goodsEvaluation.rest;

import com.wooshop.modules.goodsEvaluation.service.dto.WooshopGoodsEvaluationQueryCriteria;
import java.util.Arrays;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import com.wooshop.dozer.service.IGenerator;
import javax.servlet.http.HttpServletResponse;
import com.wooshop.domain.PageResult;
import com.wooshop.modules.goodsEvaluation.domain.WooshopGoodsEvaluation;
import com.wooshop.modules.goodsEvaluation.service.WooshopGoodsEvaluationService;


import com.wooshop.modules.goodsEvaluation.service.dto.WooshopGoodsEvaluationDto;
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
* @date 2022-01-17
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@AllArgsConstructor
@Api(tags = "评价表管理")
@RestController
@RequestMapping("/api/wooshopGoodsEvaluation")
public class WooshopGoodsEvaluationController {

    private final WooshopGoodsEvaluationService wooshopGoodsEvaluationService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','wooshopGoodsEvaluation:list')")
    public void download(HttpServletResponse response, WooshopGoodsEvaluationQueryCriteria criteria) throws IOException {
        wooshopGoodsEvaluationService.download(generator.convert(wooshopGoodsEvaluationService.queryAll(criteria), WooshopGoodsEvaluationDto.class), response);
    }

    @GetMapping
    @Log("查询评价表")
    @ApiOperation("查询评价表")
    @PreAuthorize("@el.check('admin','wooshopGoodsEvaluation:list')")
    public ResponseEntity<PageResult<WooshopGoodsEvaluationDto>> getWooshopGoodsEvaluations(WooshopGoodsEvaluationQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(wooshopGoodsEvaluationService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增评价表")
    @ApiOperation("新增评价表")
    @PreAuthorize("@el.check('admin','wooshopGoodsEvaluation:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody WooshopGoodsEvaluation resources){
        return new ResponseEntity<>(wooshopGoodsEvaluationService.addAndUpdate(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改评价表")
    @ApiOperation("修改评价表")
    @PreAuthorize("@el.check('admin','wooshopGoodsEvaluation:edit','wooshopGoodsEvaluation:submit')")
    public ResponseEntity<Object> update(@Validated @RequestBody WooshopGoodsEvaluation resources){
        wooshopGoodsEvaluationService.addAndUpdate(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除评价表")
    @ApiOperation("删除评价表")
    @PreAuthorize("@el.check('admin','wooshopGoodsEvaluation:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        Arrays.asList(ids).forEach(id->{
            wooshopGoodsEvaluationService.cacheRemoveById(id);
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

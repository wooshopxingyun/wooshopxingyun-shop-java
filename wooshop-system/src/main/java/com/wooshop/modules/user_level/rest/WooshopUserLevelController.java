
package com.wooshop.modules.user_level.rest;

import com.wooshop.modules.user_level.service.dto.WooshopUserLevelQueryCriteria;
import java.util.Arrays;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import com.wooshop.dozer.service.IGenerator;
import javax.servlet.http.HttpServletResponse;
import com.wooshop.domain.PageResult;
import com.wooshop.modules.user_level.domain.WooshopUserLevel;
import com.wooshop.modules.user_level.service.WooshopUserLevelService;


import com.wooshop.modules.user_level.service.dto.WooshopUserLevelDto;
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
* @date 2022-04-16
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@AllArgsConstructor
@Api(tags = "会员等级记录管理")
@RestController
@RequestMapping("/api/wooshopUserLevel")
public class WooshopUserLevelController {

    private final WooshopUserLevelService wooshopUserLevelService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','wooshopUserLevel:list')")
    public void download(HttpServletResponse response, WooshopUserLevelQueryCriteria criteria) throws IOException {
        wooshopUserLevelService.download(generator.convert(wooshopUserLevelService.queryAll(criteria), WooshopUserLevelDto.class), response);
    }

    @GetMapping
    @Log("查询会员等级记录")
    @ApiOperation("查询会员等级记录")
    @PreAuthorize("@el.check('admin','wooshopUserLevel:list')")
    public ResponseEntity<PageResult<WooshopUserLevelDto>> getWooshopUserLevels(WooshopUserLevelQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(wooshopUserLevelService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增会员等级记录")
    @ApiOperation("新增会员等级记录")
    @PreAuthorize("@el.check('admin','wooshopUserLevel:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody WooshopUserLevel resources){
        return new ResponseEntity<>(wooshopUserLevelService.addAndUpdate(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改会员等级记录")
    @ApiOperation("修改会员等级记录")
    @PreAuthorize("@el.check('admin','wooshopUserLevel:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody WooshopUserLevel resources){
        wooshopUserLevelService.addAndUpdate(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除会员等级记录")
    @ApiOperation("删除会员等级记录")
    @PreAuthorize("@el.check('admin','wooshopUserLevel:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        Arrays.asList(ids).forEach(id->{
            wooshopUserLevelService.cacheRemoveById(id);
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

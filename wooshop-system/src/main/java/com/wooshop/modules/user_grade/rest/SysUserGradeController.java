
package com.wooshop.modules.user_grade.rest;

import com.wooshop.modules.user_grade.service.dto.SysUserGradeQueryCriteria;
import java.util.Arrays;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import com.wooshop.dozer.service.IGenerator;
import javax.servlet.http.HttpServletResponse;
import com.wooshop.domain.PageResult;
import com.wooshop.modules.user_grade.domain.SysUserGrade;
import com.wooshop.modules.user_grade.service.SysUserGradeService;


import com.wooshop.modules.user_grade.service.dto.SysUserGradeDto;
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
* @date 2021-12-13
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@AllArgsConstructor
@Api(tags = "会员等级管理")
@RestController
@RequestMapping("/api/sysUserGrade")
public class SysUserGradeController {

    private final SysUserGradeService sysUserGradeService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','sysUserGrade:list')")
    public void download(HttpServletResponse response, SysUserGradeQueryCriteria criteria) throws IOException {
        sysUserGradeService.download(generator.convert(sysUserGradeService.queryAll(criteria), SysUserGradeDto.class), response);
    }

    @GetMapping
    @Log("查询会员等级")
    @ApiOperation("查询会员等级")
    @PreAuthorize("@el.check('admin','sysUserGrade:list')")
    public ResponseEntity<PageResult<SysUserGradeDto>> getSysUserGrades(SysUserGradeQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(sysUserGradeService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增会员等级")
    @ApiOperation("新增会员等级")
    @PreAuthorize("@el.check('admin','sysUserGrade:list')")
    public ResponseEntity<Object> create(@Validated @RequestBody SysUserGrade resources){
        return new ResponseEntity<>(sysUserGradeService.save(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改会员等级")
    @ApiOperation("修改会员等级")
    @PreAuthorize("@el.check('admin','sysUserGrade:list')")
    public ResponseEntity<Object> update(@Validated @RequestBody SysUserGrade resources){
        sysUserGradeService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除会员等级")
    @ApiOperation("删除会员等级")
    @PreAuthorize("@el.check('admin','sysUserGrade:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        Arrays.asList(ids).forEach(id->{
            sysUserGradeService.removeById(id);
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

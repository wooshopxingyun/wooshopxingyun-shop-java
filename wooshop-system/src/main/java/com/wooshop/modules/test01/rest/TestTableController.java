
package com.wooshop.modules.test01.rest;

import com.wooshop.modules.test001.service.dto.TestTableQueryCriteria;
import java.util.Arrays;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import com.wooshop.dozer.service.IGenerator;
import javax.servlet.http.HttpServletResponse;
import com.wooshop.domain.PageResult;
import com.wooshop.modules.test001.domain.TestTable;
import com.wooshop.modules.test001.service.TestTableService;


import com.wooshop.modules.test001.service.dto.TestTableDto;
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
* @date 2021-10-26
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@AllArgsConstructor
@Api(tags = "测试代码生成管理")
@RestController
@RequestMapping("/api/testTable")
public class TestTableController {

    private final TestTableService testTableService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','testTable:list')")
    public void download(HttpServletResponse response, TestTableQueryCriteria criteria) throws IOException {
        testTableService.download(generator.convert(testTableService.queryAll(criteria), TestTableDto.class), response);
    }

    @GetMapping
    @Log("查询测试代码生成")
    @ApiOperation("查询测试代码生成")
    @PreAuthorize("@el.check('admin','testTable:list')")
    public ResponseEntity<PageResult<TestTableDto>> getTestTables(TestTableQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(testTableService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增测试代码生成")
    @ApiOperation("新增测试代码生成")
    @PreAuthorize("@el.check('admin','testTable:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody TestTable resources){
        return new ResponseEntity<>(testTableService.save(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改测试代码生成")
    @ApiOperation("修改测试代码生成")
    @PreAuthorize("@el.check('admin','testTable:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody TestTable resources){
        testTableService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除测试代码生成")
    @ApiOperation("删除测试代码生成")
    @PreAuthorize("@el.check('admin','testTable:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        Arrays.asList(ids).forEach(id->{
            testTableService.removeById(id);
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

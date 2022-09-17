package com.wooshop.modules.test.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import com.wooshop.annotation.Log;
import com.wooshop.modules.test.service.Table1Service;
import com.wooshop.modules.test.service.dto.Table1Dto;
import com.wooshop.modules.test.service.dto.Table1QueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
* @author jinjin
* @date 2020-10-01
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "测试管理")
@RequestMapping("/api/table1")
public class Table1Controller {

    private final Table1Service table1Service;

    @GetMapping
    @Log("查询测试")
    @ApiOperation("查询测试")
    @PreAuthorize("@el.check('table1:list')")
    public ResponseEntity query(Table1QueryParam query, Pageable pageable){
        return new ResponseEntity<>(table1Service.queryAll(query,pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增测试")
    @ApiOperation("新增测试")
    @PreAuthorize("@el.check('table1:add')")
    public ResponseEntity create(@Validated @RequestBody Table1Dto resources){
        return new ResponseEntity<>(table1Service.insert(resources), HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改测试")
    @ApiOperation("修改测试")
    @PreAuthorize("@el.check('table1:edit')")
    public ResponseEntity update(@Validated @RequestBody Table1Dto resources){
        table1Service.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除测试")
    @ApiOperation("删除测试")
    @PreAuthorize("@el.check('table1:del')")
    public ResponseEntity delete(@RequestBody Set<Long> ids) {
        table1Service.removeByIds(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    @Log("导出测试")
    @ApiOperation("导出测试")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('table1:list')")
    public void download(HttpServletResponse response, Table1QueryParam query) throws IOException {
        table1Service.download(table1Service.queryAll(query), response);
    }*/

}

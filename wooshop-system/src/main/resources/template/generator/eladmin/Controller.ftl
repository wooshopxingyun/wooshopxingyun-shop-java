
package ${package}.rest;

import com.wooshop.annotation.Log;

import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import ${package}.service.dto.${className}QueryCriteria;
import org.springframework.http.ResponseEntity;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import ${package}.domain.${className};
import org.springframework.validation.annotation.Validated;

import ${package}.service.${className}Service;
import io.swagger.annotations.*;



/**
* Copyright (C) 2021
* @author ${author}
* @date ${date}
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/

@Api(tags = "${apiAlias}管理")
@RestController
@RequestMapping("/api/${changeClassName}")
public class ${className}Controller {

    private final ${className}Service ${changeClassName}Service;

    public ${className}Controller(${className}Service ${changeClassName}Service) {
        this.${changeClassName}Service = ${changeClassName}Service;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('${changeClassName}:list')")
    public void download(HttpServletResponse response, ${className}QueryCriteria criteria) throws IOException {
        ${changeClassName}Service.download(${changeClassName}Service.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询${apiAlias}")
    @ApiOperation("查询${apiAlias}")
    @PreAuthorize("@el.check('${changeClassName}:list')")
    public ResponseEntity<Object> get${className}s(${className}QueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(${changeClassName}Service.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增${apiAlias}")
    @ApiOperation("新增${apiAlias}")
    @PreAuthorize("@el.check('${changeClassName}:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody ${className} resources){
        return new ResponseEntity<>(${changeClassName}Service.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改${apiAlias}")
    @ApiOperation("修改${apiAlias}")
    @PreAuthorize("@el.check('${changeClassName}:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody ${className} resources){
        ${changeClassName}Service.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除${apiAlias}")
    @ApiOperation("删除${apiAlias}")
    @PreAuthorize("@el.check('${changeClassName}:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody ${pkColumnType}[] ids) {
        ${changeClassName}Service.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

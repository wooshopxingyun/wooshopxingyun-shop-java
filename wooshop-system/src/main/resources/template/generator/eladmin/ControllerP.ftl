
package ${package}.rest;

import ${package}.service.dto.${className}QueryCriteria;
import java.util.Arrays;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import com.wooshop.dozer.service.IGenerator;
import javax.servlet.http.HttpServletResponse;
import com.wooshop.domain.PageResult;
import ${package}.domain.${className};
import ${package}.service.${className}Service;


import ${package}.service.dto.${className}Dto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import com.wooshop.annotation.Log;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;




/**
* @author ${author}
* @date ${date}
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@AllArgsConstructor
@Api(tags = "${apiAlias}管理")
@RestController
@RequestMapping("/api/${changeClassName}")
public class ${className}Controller {

    private final ${className}Service ${changeClassName}Service;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','${changeClassName}:list')")
    public void download(HttpServletResponse response, ${className}QueryCriteria criteria) throws IOException {
        ${changeClassName}Service.download(generator.convert(${changeClassName}Service.queryAll(criteria), ${className}Dto.class), response);
    }

    @GetMapping
    @Log("查询${apiAlias}")
    @ApiOperation("查询${apiAlias}")
    @PreAuthorize("@el.check('admin','${changeClassName}:list')")
    public ResponseEntity<PageResult<${className}Dto>> get${className}s(${className}QueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(${changeClassName}Service.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增${apiAlias}")
    @ApiOperation("新增${apiAlias}")
    @PreAuthorize("@el.check('admin','${changeClassName}:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody ${className} resources){
        return new ResponseEntity<>(${changeClassName}Service.addAndUpdate(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改${apiAlias}")
    @ApiOperation("修改${apiAlias}")
    @PreAuthorize("@el.check('admin','${changeClassName}:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody ${className} resources){
        ${changeClassName}Service.addAndUpdate(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除${apiAlias}")
    @ApiOperation("删除${apiAlias}")
    @PreAuthorize("@el.check('admin','${changeClassName}:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody ${pkColumnType}[] ids) {
        Arrays.asList(ids).forEach(id->{
            ${changeClassName}Service.cacheRemoveById(id);
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

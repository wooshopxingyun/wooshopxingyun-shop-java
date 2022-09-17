package com.wooshop.modules.log.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import com.wooshop.modules.service.CodeScrewConfigService;
import com.wooshop.modules.service.dto.CodeScrewConfigDto;
import com.wooshop.modules.service.dto.CodeScrewConfigQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
* @author fanglei
* @date 2021-08-11
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "screw数据库表结构文档管理")
@RequestMapping("/api/codeScrewConfig")
public class CodeScrewConfigController {

    private final CodeScrewConfigService codeScrewConfigService;

    @GetMapping("query")
    @ApiOperation("查询screw数据库表结构文档")
    public ResponseEntity queryConfig(){
        return new ResponseEntity<>(codeScrewConfigService.findById(1L), HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation("查询screw数据库表结构文档")
    @PreAuthorize("@el.check('codeScrewConfig:list')")
    public ResponseEntity query(CodeScrewConfigQueryParam query, Pageable pageable){
        return new ResponseEntity<>(codeScrewConfigService.queryAll(query,pageable), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("新增screw数据库表结构文档")
    @PreAuthorize("@el.check('codeScrewConfig:add')")
    public ResponseEntity create(@Validated @RequestBody CodeScrewConfigDto resources){
        return new ResponseEntity<>(codeScrewConfigService.insert(resources), HttpStatus.CREATED);
    }

    @PutMapping
    @ApiOperation("修改screw数据库表结构文档")
    @PreAuthorize("@el.check('codeScrewConfig:edit')")
    public ResponseEntity update(@Validated @RequestBody CodeScrewConfigDto resources){
        codeScrewConfigService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @ApiOperation("删除screw数据库表结构文档")
    @PreAuthorize("@el.check('codeScrewConfig:del')")
    public ResponseEntity delete(@RequestBody Set<Long> ids) {
        codeScrewConfigService.removeByIds(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("导出screw数据库表结构文档")
    @PostMapping(value = "/download")
    public void download(HttpServletResponse response, @RequestParam(value = "fileType", defaultValue = "word") String fileType) throws IOException {
        codeScrewConfigService.download(codeScrewConfigService.findById(1L), fileType, response);
    }
    /*
    @Log("导出screw数据库表结构文档")
    @ApiOperation("导出screw数据库表结构文档")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('codeScrewConfig:list')")
    public void download(HttpServletResponse response, CodeScrewConfigQueryParam query) throws IOException {
        codeScrewConfigService.download(codeScrewConfigService.queryAll(query), response);
    }*/

}

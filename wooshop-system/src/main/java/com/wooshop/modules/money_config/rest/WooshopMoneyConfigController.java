
package com.wooshop.modules.money_config.rest;

import com.wooshop.modules.money_config.service.dto.WooshopMoneyConfigQueryCriteria;
import java.util.Arrays;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import com.wooshop.dozer.service.IGenerator;
import javax.servlet.http.HttpServletResponse;
import com.wooshop.domain.PageResult;
import com.wooshop.modules.money_config.domain.WooshopMoneyConfig;
import com.wooshop.modules.money_config.service.WooshopMoneyConfigService;


import com.wooshop.modules.money_config.service.dto.WooshopMoneyConfigDto;
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
* @date 2021-12-20
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@AllArgsConstructor
@Api(tags = "充值金额配置管理")
@RestController
@RequestMapping("/api/wooshopMoneyConfig")
public class WooshopMoneyConfigController {

    private final WooshopMoneyConfigService wooshopMoneyConfigService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','wooshopMoneyConfig:list')")
    public void download(HttpServletResponse response, WooshopMoneyConfigQueryCriteria criteria) throws IOException {
        wooshopMoneyConfigService.download(generator.convert(wooshopMoneyConfigService.queryAll(criteria), WooshopMoneyConfigDto.class), response);
    }

    @GetMapping
    @Log("查询充值金额配置")
    @ApiOperation("查询充值金额配置")
    @PreAuthorize("@el.check('admin','wooshopMoneyConfig:list')")
    public ResponseEntity<PageResult<WooshopMoneyConfigDto>> getWooshopMoneyConfigs(WooshopMoneyConfigQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(wooshopMoneyConfigService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增充值金额配置")
    @ApiOperation("新增充值金额配置")
    @PreAuthorize("@el.check('admin','wooshopMoneyConfig:list')")
    public ResponseEntity<Object> create(@Validated @RequestBody WooshopMoneyConfig resources){
        return new ResponseEntity<>(wooshopMoneyConfigService.save(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改充值金额配置")
    @ApiOperation("修改充值金额配置")
    @PreAuthorize("@el.check('admin','wooshopMoneyConfig:list')")
    public ResponseEntity<Object> update(@Validated @RequestBody WooshopMoneyConfig resources){
        wooshopMoneyConfigService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除充值金额配置")
    @ApiOperation("删除充值金额配置")
    @PreAuthorize("@el.check('admin','wooshopMoneyConfig:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        Arrays.asList(ids).forEach(id->{
            wooshopMoneyConfigService.removeById(id);
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }
}


package com.wooshop.modules.sysconfig.rest;

import cn.hutool.json.JSONObject;
import com.github.pagehelper.PageInfo;
import com.wooshop.config.FileProperties;
import com.wooshop.modules.sys_config.service.dto.WooSysConfigQueryCriteria;

import java.util.Arrays;

import com.wooshop.modules.sys_config.vo.SysConfigHomeVo;
import com.wooshop.modules.utils.RedisUache;
import com.wooshop.utils.RedisUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import com.wooshop.dozer.service.IGenerator;
import javax.servlet.http.HttpServletResponse;

import com.wooshop.domain.PageResult;
import com.wooshop.modules.sys_config.domain.WooSysConfig;
import com.wooshop.modules.sys_config.service.WooSysConfigService;


import com.wooshop.modules.sys_config.service.dto.WooSysConfigDto;
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
* @date 2021-11-09
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@AllArgsConstructor
@Api(tags = "系统配置管理")
@RestController
@RequestMapping("/api/wooSysConfig")
@CacheConfig(cacheNames = "sysConfig")
public class WooSysConfigController {

    private final WooSysConfigService wooSysConfigService;
    private final IGenerator generator;
    private final FileProperties properties;
    private final RedisUtils redisUtils;
    private final RedisUache redisUache;
//    @Value("${spring.profiles.active}")
//    private final String profiles_active;


    @Log("新增:阿里云短信配置")
    @ApiOperation("新增:阿里云短信配置")
    @PostMapping(value = "/aliyunsms")
    @PreAuthorize("@el.check('admin','wooSysConfig:list')")
    public ResponseEntity<String> aliyunsms(HttpServletResponse response,@RequestBody String aliyunsmsJson) throws IOException {
        JSONObject json = new JSONObject(aliyunsmsJson);
        WooSysConfig wooSysConfig = new WooSysConfig();
        if (json.getStr("menuName").equals("WOOSHOP_SYSCONFIG_ALIYUNSMS")) {
/*            System.out.println(json.getBigInteger("menuId"));
            System.out.println(json.getStr("signName"));
            System.out.println(json.getStr("templateId"));
            System.out.println(json.getStr("accessKey"));
            System.out.println(json.getStr("region"));
            System.out.println(json.getStr("accessKeySecret"));*/
            wooSysConfig.setMenuName(json.getStr("menuName"));
            wooSysConfig.setId(json.getInt("id"));
            wooSysConfig.setValue(json.toString());
            wooSysConfig.setEnabled(json.getInt("enabled"));
            wooSysConfigService.saveOrUpdate(wooSysConfig);
        }else if (json.getStr("menuName").equals("WOOSHOP_SYSCONFIG_DISTRIBUTION")){
            wooSysConfig.setMenuName(json.getStr("menuName"));
            wooSysConfig.setId(json.getInt("id"));
            wooSysConfig.setValue(json.toString());
            wooSysConfig.setEnabled(json.getInt("enabled"));
            wooSysConfigService.saveOrUpdate(wooSysConfig);
        }else if (json.getStr("menuName").equals("WOOSHOP_SYSCONFIG_INTEGRAL")){
            wooSysConfig.setMenuName(json.getStr("menuName"));
            wooSysConfig.setId(json.getInt("id"));
            wooSysConfig.setValue(json.toString());
            wooSysConfig.setEnabled(json.getInt("enabled"));
            wooSysConfigService.saveOrUpdate(wooSysConfig);
        }else if (json.getStr("menuName").equals("WOOSHOP_SYSCONFIG_SIGNCONFIG")){
            wooSysConfig.setMenuName(json.getStr("menuName"));
            wooSysConfig.setId(json.getInt("id"));
            wooSysConfig.setValue(json.toString());
            wooSysConfig.setEnabled(json.getInt("enabled"));
            wooSysConfigService.saveOrUpdate(wooSysConfig);
        }

        return ResponseEntity.ok("保存成功");
    }

    @PostMapping(value = "/queryaliyunsms")
    @Log("根据条件查询系统配置")
    @ApiOperation("根据条件查询系统配置")
    @PreAuthorize("@el.check('admin','wooSysConfig:list')")
    public ResponseEntity<WooSysConfigDto> queryAliyunconfig( @RequestBody String menuName){

        System.out.println(menuName);
        WooSysConfigDto wooSysConfigDto = wooSysConfigService.queryByMenuName(menuName);
        return new ResponseEntity<>(wooSysConfigDto,HttpStatus.OK);
    }

    @PostMapping(value = "/querWeixinMP")
    @Log("微信小程序：根据条件查询系统配置")
    @ApiOperation("微信小程序：根据条件查询系统配置")
    @PreAuthorize("@el.check('admin','wooSysConfig:list')")
    public ResponseEntity<WooSysConfigDto> queryWeixinMP( @RequestBody String menuName){
        System.out.println(menuName);
        return new ResponseEntity<>(wooSysConfigService.queryByMenuName(menuName),HttpStatus.OK);
    }

    @Log("微信小程序：新增阿里云短信配置")
    @ApiOperation("微信小程序：新增阿里云短信配置")
    @PostMapping(value = "/getweixinMPConfig")
    @PreAuthorize("@el.check('admin','wooSysConfig:list')")
    public ResponseEntity<String> getweixinMPConfig(HttpServletResponse response,@RequestBody String aliyunsmsJson) throws IOException {
//        wooSysConfigService.download(generator.convert(wooSysConfigService.queryAll(criteria), WooSysConfigDto.class), response);
        JSONObject json = new JSONObject(aliyunsmsJson);
        System.out.println( json.getBigInteger("id"));
        System.out.println( json.getStr("appId"));
        System.out.println( json.getStr("templateId"));
        System.out.println( json.getStr("appSecret"));
        System.out.println( json.getStr("enabled"));


        WooSysConfig wooSysConfig=new WooSysConfig();
        wooSysConfig.setMenuName(json.getStr("menuName"));
        wooSysConfig.setId(json.getInt("id"));
        wooSysConfig.setValue(json.toString());
        wooSysConfig.setEnabled(json.getInt("enabled"));
        wooSysConfigService.saveOrUpdate(wooSysConfig);


//        wooSysConfigService.s
        return ResponseEntity.ok("保存成功");
    }



//    ---------------------------------------------------------------------------------


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','wooSysConfig:list')")
    public void download(HttpServletResponse response, WooSysConfigQueryCriteria criteria) throws IOException {
        wooSysConfigService.download(generator.convert(wooSysConfigService.queryAll(criteria), WooSysConfigDto.class), response);
    }

    @GetMapping
    @Log("查询系统配置")
    @ApiOperation("查询系统配置")
    @PreAuthorize("@el.check('admin','wooSysConfig:list')")
    public ResponseEntity<PageResult<WooSysConfigDto>> getWooSysConfigs(WooSysConfigQueryCriteria criteria, Pageable pageable){
//        PageResult<WooSysConfigDto> wooSysConfigDtoPageResult = wooSysConfigService.queryAll(criteria, pageable);
        return new ResponseEntity<>(redisUache.getWooSysConfigs(criteria,pageable),HttpStatus.OK);
    }

    @GetMapping("/getapphome")
    @Log("查询移动页面装饰配置值数据")
    @ApiOperation("查询移动页面装饰配置值数据")
    @PreAuthorize("@el.check('admin','wooSysConfig:list')")
    public ResponseEntity<PageResult<SysConfigHomeVo>> getAppHome(){
//        PageInfo<SysConfigHomeVo> page = new PageInfo<>(wooSysConfigService.getSysAppHome());
        PageInfo<SysConfigHomeVo> page = new PageInfo<>(redisUache.getSysAppHome());

        return new ResponseEntity<>(generator.convertPageInfo(page, SysConfigHomeVo.class),HttpStatus.OK);
    }

    @PostMapping(value = "/weixinpaysys")
    @Log("新增微信支付配置")
    @ApiOperation("新增微信支付配置")
    @PreAuthorize("@el.check('admin','wooSysConfig:list')")
    public ResponseEntity<Object> createWeiXinPaySys(@Validated @RequestBody WooSysConfig resources){
        redisUtils.del(redisUache.getWooshopWeiXinPayService());
        return new ResponseEntity<>(wooSysConfigService.addSaveOrUpdate(resources),HttpStatus.CREATED);
    }

    @PostMapping
    @Log("新增系统配置")
    @ApiOperation("新增系统配置")
    @PreAuthorize("@el.check('admin','wooSysConfig:list')")
    public ResponseEntity<Object> create(@Validated @RequestBody WooSysConfig resources){
        /*JSONObject json = new JSONObject(resources.getValue());
        wooSysConfigService.save(resources);
        json.set("menuId",resources.getMenuId());
        json.set("enabled",resources.getEnabled());
        json.set("sort",resources.getSort());
        resources.setValue(json.toString());
        return new ResponseEntity<>(wooSysConfigService.updateById(resources),HttpStatus.CREATED);*/
        return new ResponseEntity<>(wooSysConfigService.addSaveOrUpdate(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改系统配置")
    @ApiOperation("修改系统配置")
    @PreAuthorize("@el.check('admin','wooSysConfig:list')")
    public ResponseEntity<Object> update(@Validated @RequestBody WooSysConfig resources){
        /*JSONObject json = new JSONObject(resources.getValue());
        json.set("menuId",resources.getMenuId());
        json.set("enabled",resources.getEnabled());
        json.set("sort",resources.getSort());
        resources.setValue(json.toString());
        wooSysConfigService.updateById(resources);*/
        return new ResponseEntity<>(wooSysConfigService.addSaveOrUpdate(resources),HttpStatus.NO_CONTENT);
    }

    @Log("删除系统配置")
    @ApiOperation("删除系统配置")
    @PreAuthorize("@el.check('admin','wooSysConfig:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Integer[] ids) {
        Arrays.asList(ids).forEach(id->{
            wooSysConfigService.cacheRemoveById(id);
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

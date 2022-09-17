
package com.wooshop.modules.wooshop_wechat_menu.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wooshop.exception.BadRequestException;
import com.wooshop.modules.wooshop_wechat_menu.service.dto.WooshopWechatMenuQueryCriteria;
import java.util.Arrays;

import com.wooshop.utils.enums.WooshopConstants;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import com.wooshop.dozer.service.IGenerator;
import javax.servlet.http.HttpServletResponse;

import com.wooshop.modules.wooshop_wechat_menu.domain.WooshopWechatMenu;
import com.wooshop.modules.wooshop_wechat_menu.service.WooshopWechatMenuService;


import com.wooshop.modules.wooshop_wechat_menu.service.dto.WooshopWechatMenuDto;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import com.wooshop.annotation.Log;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;




/**
* @author woo
* @date 2022-07-09
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@AllArgsConstructor
@Api(tags = "微信公众号菜单管理")
@RestController
@RequestMapping("/api/wooshopWechatMenu")
@SuppressWarnings("unchecked")
public class WooshopWechatMenuController {

    private final WooshopWechatMenuService wooshopWechatMenuService;
    private final IGenerator generator;


    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('admin','wooshopWechatMenu:list')")
    public void download(HttpServletResponse response, WooshopWechatMenuQueryCriteria criteria) throws IOException {
        wooshopWechatMenuService.download(generator.convert(wooshopWechatMenuService.queryAll(criteria), WooshopWechatMenuDto.class), response);
    }

    @GetMapping
    @Log("查询微信公众号菜单")
    @ApiOperation("查询微信公众号菜单")
    @PreAuthorize("@el.check('admin','wooshopWechatMenu:list')")
    public ResponseEntity<Object> getWooshopWechatMenus(){
        return new ResponseEntity<>(wooshopWechatMenuService.getOne(new LambdaQueryWrapper<WooshopWechatMenu>()
                .eq(WooshopWechatMenu::getNameKey, WooshopConstants.WECHAT_MENUS)),HttpStatus.OK);
    }

   /* @PostMapping
    @Log("新增微信公众号菜单")
    @ApiOperation("新增微信公众号菜单")
    @PreAuthorize("@el.check('admin','wooshopWechatMenu:list')")
    public ResponseEntity<Object> create(@RequestBody String jsonStr){
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        String jsonButton = jsonObject.get("buttons").toString();
        WooshopWechatMenu wooshopWechatMenu = new WooshopWechatMenu();
        Boolean isExist = wooshopWechatMenuService.isExist(WooshopConstants.WECHAT_MENUS);
        WxMenu menu = JSONObject.parseObject(jsonStr, WxMenu.class);

        WxMpService wxService = WeiXinMpConfiguration.getWxMpService();
        if (isExist) {
            wooshopWechatMenu.setNameKey(WooshopConstants.WECHAT_MENUS);
            wooshopWechatMenu.setValueResult(jsonButton);
            wooshopWechatMenu.setId(1l);
            wooshopWechatMenuService.saveOrUpdate(wooshopWechatMenu);
        } else {
            wooshopWechatMenu.setNameKey(WooshopConstants.WECHAT_MENUS);
            wooshopWechatMenu.setValueResult(jsonButton);
            wooshopWechatMenu.setAddTime(Integer.valueOf(String.valueOf(System.currentTimeMillis() / 1000)));
            wooshopWechatMenuService.save(wooshopWechatMenu);
        }
        //进行创建菜单
        try {
            wxService.getMenuService().menuDelete();
            wxService.getMenuService().menuCreate(menu);
        } catch (WxErrorException e) {
            throw new BadRequestException(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }*/

    @PutMapping
    @Log("修改微信公众号菜单")
    @ApiOperation("修改微信公众号菜单")
    @PreAuthorize("@el.check('admin','wooshopWechatMenu:list')")
    public ResponseEntity<Object> update(@Validated @RequestBody WooshopWechatMenu resources){
        wooshopWechatMenuService.addAndUpdate(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除微信公众号菜单")
    @ApiOperation("删除微信公众号菜单")
    @PreAuthorize("@el.check('admin','wooshopWechatMenu:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody String[] ids) {
        Arrays.asList(ids).forEach(id->{
            wooshopWechatMenuService.cacheRemoveById(id);
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

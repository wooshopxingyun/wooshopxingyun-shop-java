/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.wooshop.modules.log.rest;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import com.wooshop.annotation.Log;
import com.wooshop.config.FileProperties;
import com.wooshop.domain.PageResult;
import com.wooshop.exception.BadRequestException;
import com.wooshop.modules.sys_config.service.WooSysConfigService;
import com.wooshop.modules.sys_config.service.dto.WooSysConfigDto;
import com.wooshop.modules.sys_config.service.dto.WooSysConfigQueryCriteria;
import com.wooshop.modules.tools.domain.LocalStorage;
import com.wooshop.modules.tools.service.LocalStorageService;
import com.wooshop.modules.tools.service.dto.LocalStorageQueryParam;
import com.wooshop.utils.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
//import me.zhengjie.annotation.Log;
//import me.zhengjie.domain.LocalStorage;
//import me.zhengjie.exception.BadRequestException;
//import me.zhengjie.service.LocalStorageService;
//import me.zhengjie.service.dto.LocalStorageQueryParam;
//import me.zhengjie.utils.FileUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
* @author Zheng Jie
* @date 2019-09-05
*/
@RestController
@RequiredArgsConstructor
@Api(tags = "工具：本地存储管理")
@RequestMapping("/api/localStorage")
public class LocalStorageController {

    private final LocalStorageService localStorageService;
    private final FileProperties properties;
    private final WooSysConfigService sysConfigService;

    @ApiOperation("查询文件")
    @GetMapping
    @PreAuthorize("@el.check('storage:list')")
    public ResponseEntity<Object> query(LocalStorageQueryParam criteria, Pageable pageable){
        return new ResponseEntity<>(localStorageService.queryAll(criteria,pageable), HttpStatus.OK);
    }

    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('storage:list')")
    public void download(HttpServletResponse response, LocalStorageQueryParam criteria) throws IOException {
        localStorageService.download(localStorageService.queryAll(criteria), response);
    }

    @ApiOperation("上传文件")
    @PostMapping
//    @PreAuthorize("@el.checkIsUser(),@el.check('storage:add')")
    @PreAuthorize("@el.checkIsUser()")
    public ResponseEntity<Object> create(@RequestParam String name,@RequestParam Integer categoryId, @RequestParam("file") MultipartFile file){
        System.out.println("打印文件上传"+categoryId);

        return new ResponseEntity<>(localStorageService.create(name, file,categoryId),HttpStatus.CREATED);
    }

    @ApiOperation("微信支付文件加密文件上传")
    @PostMapping(value = "/weixinpayfile")
    @PreAuthorize("@el.check('storage:add')")
    public ResponseEntity<Object> weiXinPayFile(@RequestParam("file") MultipartFile file){
        WooSysConfigQueryCriteria criteria=new WooSysConfigQueryCriteria();
        criteria.setMenuName("WOOSHOP_SYSCONFIG_BASICSSHOP");
        criteria.setEnabled(1);
        PageResult<WooSysConfigDto> wooSysConfigDtoPageResult = sysConfigService.queryAll(criteria, Pageable.ofSize(10).withPage(0));
        JSONObject jsonObject=new JSONObject(wooSysConfigDtoPageResult.getContent().get(0).getValue());
        String adminUrl = jsonObject.getStr("adminUrl");//获取后端地址
//        System.out.println("打印文件上传"+file);
        FileUtil.checkSize(properties.getMaxSize(), file.getSize());
        String suffix = FileUtil.getExtensionName(file.getOriginalFilename());
        String type = FileUtil.getFileType(suffix);
        File files = FileUtil.uploadWeiXinPay(file, properties.getPath().getPath() );
        StringBuilder url = new StringBuilder();
        if(ObjectUtil.isNull(files)){
            throw new BadRequestException("上传失败");
        }
        url = url.append(adminUrl + "/file/" + file.getOriginalFilename());
        return new ResponseEntity<>(url,HttpStatus.CREATED);
    }

    @PostMapping("/pictures")
    @ApiOperation("上传图片")
    public ResponseEntity<Object> upload(@RequestParam MultipartFile file){
        // 判断文件是否为图片
        String suffix = FileUtil.getExtensionName(file.getOriginalFilename());
        if(!FileUtil.IMAGE.equals(FileUtil.getFileType(suffix))){
            throw new BadRequestException("只能上传图片");
        }
        LocalStorage localStorage = localStorageService.create(null, file, 1);
        return new ResponseEntity<>(localStorage, HttpStatus.OK);
    }

    @Log("修改文件")
    @ApiOperation("修改文件")
    @PutMapping
    @PreAuthorize("@el.check('storage:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody LocalStorage resources){
        localStorageService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除文件")
    @DeleteMapping
    @ApiOperation("多选删除")
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        localStorageService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

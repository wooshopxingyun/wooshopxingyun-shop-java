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
package com.wooshop.modules.tools.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.wooshop.modules.service.impl.BaseServiceImpl;
import com.wooshop.domain.PageResult;
import com.wooshop.dozer.service.IGenerator;
import com.wooshop.modules.category.domain.WooshopConfigCategory;
import com.wooshop.modules.category.service.WooshopConfigCategoryService;
import com.wooshop.modules.category.service.dto.WooshopConfigCategoryQueryCriteria;
import com.wooshop.modules.sys_config.service.WooSysConfigService;
import com.wooshop.modules.sys_config.service.dto.WooSysConfigDto;
import com.wooshop.modules.sys_config.service.dto.WooSysConfigQueryCriteria;
import com.wooshop.modules.tools.domain.LocalStorage;
import com.wooshop.modules.tools.domain.QiniuConfig;
import com.wooshop.modules.tools.domain.QiniuContent;
import com.wooshop.modules.tools.service.LocalStorageService;
import com.wooshop.modules.tools.service.QiNiuService;
import com.wooshop.modules.tools.service.dto.LocalStorageDto;
import com.wooshop.modules.tools.service.dto.LocalStorageQueryParam;
import com.wooshop.modules.tools.service.mapper.LocalStorageMapper;
import com.wooshop.modules.tools.service.vo.LocalStorageVo;
import lombok.RequiredArgsConstructor;
import com.wooshop.base.QueryHelpMybatisPlus;
import com.wooshop.config.FileProperties;
import com.wooshop.exception.BadRequestException;
import com.wooshop.utils.ConvertUtil;
import com.wooshop.utils.FileUtil;
import com.wooshop.utils.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
* @author Zheng Jie
* @date 2019-09-05
*/
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class LocalStorageServiceImpl extends BaseServiceImpl<LocalStorageMapper, LocalStorage> implements LocalStorageService {

    private final IGenerator generator;
    private final FileProperties properties;
    private final LocalStorageMapper localStorageMapper;
    private final WooshopConfigCategoryService wooshopConfigCategoryService;
    private final QiNiuService qiNiuService;
    private final WooSysConfigService sysConfigService;

    @Override
    public Object queryAll(LocalStorageQueryParam query, Pageable pageable){
//        IPage<LocalStorage> page = PageUtil.toMybatisPage(pageable);
//        IPage<LocalStorage> pageList = localStorageMapper.selectPage(page, QueryHelpMybatisPlus.getPredicate(query));
        getPage(pageable);
        PageInfo<LocalStorage> pageList = new PageInfo<>(localStorageMapper.selectList( QueryHelpMybatisPlus.getPredicate(query)));
//        return ConvertUtil.convertPage(pageList, LocalStorageDto.class);
        PageResult<LocalStorageDto> localStorageDtoPageResult = generator.convertPageInfo(pageList, LocalStorageDto.class);
        return localStorageDtoPageResult;
    }

    @Override
    public List<LocalStorageDto> queryAll(LocalStorageQueryParam query){
        return ConvertUtil.convertList(localStorageMapper.selectList(QueryHelpMybatisPlus.getPredicate(query)), LocalStorageDto.class);
    }

    @Override
    public LocalStorageDto findById(Long id){
        return ConvertUtil.convert(getById(id), LocalStorageDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LocalStorage create(String name, MultipartFile multipartFile, Integer categoryId) {
        WooSysConfigQueryCriteria criteria=new WooSysConfigQueryCriteria();
        criteria.setMenuName("WOOSHOP_SYSCONFIG_BASICSSHOP");
        criteria.setEnabled(1);
        PageResult<WooSysConfigDto> wooSysConfigDtoPageResult = sysConfigService.queryAll(criteria, Pageable.ofSize(10).withPage(0));
        JSONObject jsonObject=new JSONObject(wooSysConfigDtoPageResult.getContent().get(0).getValue());
        String adminUrl = jsonObject.getStr("adminUrl");//获取后端地址

        FileUtil.checkSize(properties.getMaxSize(), multipartFile.getSize());
        String suffix = FileUtil.getExtensionName(multipartFile.getOriginalFilename());
        String type = FileUtil.getFileType(suffix);
        File file = FileUtil.upload(multipartFile, properties.getPath().getPath() + type +  File.separator);
//        System.out.println("http://localhost:8001/file/pic/"+file.getPath().substring(file.getPath().lastIndexOf(File.separator)+1,file.getPath().length()));
        StringBuilder url = new StringBuilder();
        if(ObjectUtil.isNull(file)){
            throw new BadRequestException("上传失败");
        }
        try {
            WooshopConfigCategoryQueryCriteria category=new WooshopConfigCategoryQueryCriteria();
            category.setId(Integer.toUnsignedLong(categoryId));
            List<WooshopConfigCategory> byId = wooshopConfigCategoryService.queryAll(category);//查询分类表
            name = StringUtils.isBlank(name) ? FileUtil.getFileNameNoEx(multipartFile.getOriginalFilename()) : name;
            url = url.append(adminUrl + "/file/" + type + "/" + file.getName());
            LocalStorage localStorage = new LocalStorage(
                    file.getName(),
                    name,
                    suffix,
                    url.toString(),
                    type,
                    FileUtil.getSize(multipartFile.getSize()),
                    categoryId
            );
            localStorage.setCategoryPath(byId.get(0).getPath());
            localStorage.setStorageType(1);//1本地、2七牛云
            localStorageMapper.insert(localStorage);
            return localStorage;
        }catch (Exception e){
            FileUtil.del(file);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(LocalStorage resources) {
        updateById(resources);
        // delCaches(resources.id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(Long[] ids) {
        QiniuConfig qiniuConfig = qiNiuService.find();
        for (Long id : ids) {
            LocalStorage storage = getById(id);
            if (storage.getStorageType()==2){
                //七牛云
                qiNiuService.localMergeQiniuDelete(generator.convert(storage, QiniuContent.class),qiniuConfig);
            }else {
                //本地
                FileUtil.del(storage.getPath());
                removeById(id);
            }

        }
    }

    @Override
    public void download(List<LocalStorageDto> queryAll, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (LocalStorageDto localStorageDTO : queryAll) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("文件名", localStorageDTO.getRealName());
            map.put("备注名", localStorageDTO.getName());
            map.put("文件类型", localStorageDTO.getType());
            map.put("文件大小", localStorageDTO.getSize());
            map.put("创建者", localStorageDTO.getCreateBy());
            map.put("创建日期", localStorageDTO.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public PageResult<LocalStorageVo> queryAllImg(int page, int limit, String categoryPath, Integer categoryId, String realName) {
        setPageNotOrder(page,limit);
        LambdaQueryWrapper<LocalStorage> lambdaQueryWrapper = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(categoryPath)){
            lambdaQueryWrapper.likeRight(LocalStorage::getCategoryPath,categoryPath);
        }
        if (categoryId>0){
            lambdaQueryWrapper.eq(LocalStorage::getCategoryId,categoryId);
        }
        if (StringUtils.isNotBlank(realName)){
            lambdaQueryWrapper.like(LocalStorage::getRealName,realName);
        }
        List<LocalStorage> localStorages = baseMapper.selectList(lambdaQueryWrapper);
        PageInfo<LocalStorage> pageList = new PageInfo<>(localStorages);
        PageResult<LocalStorageVo> localStorageDtoPageResult = generator.convertPageInfo(pageList, LocalStorageVo.class);
        return localStorageDtoPageResult;
    }
}

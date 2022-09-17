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

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import com.wooshop.modules.category.domain.WooshopConfigCategory;
import com.wooshop.modules.category.service.WooshopConfigCategoryService;
import com.wooshop.modules.tools.domain.LocalStorage;
import com.wooshop.modules.tools.domain.QiniuConfig;
import com.wooshop.modules.tools.domain.QiniuContent;
import com.wooshop.modules.tools.service.QiNiuService;
import com.wooshop.modules.tools.service.dto.QiniuContentDto;
import com.wooshop.modules.tools.service.dto.QiniuContentQueryParam;
import com.wooshop.modules.tools.service.mapper.LocalStorageMapper;
import com.wooshop.modules.tools.service.mapper.QiniuConfigMapper;
import com.wooshop.modules.tools.service.mapper.QiniuContentMapper;
import com.wooshop.modules.tools.utils.QiNiuUtil;
import lombok.RequiredArgsConstructor;
import com.wooshop.base.PageInfo;
import com.wooshop.base.QueryHelpMybatisPlus;
import com.wooshop.base.impl.CommonServiceImpl;
import com.wooshop.exception.BadRequestException;
import com.wooshop.utils.ConvertUtil;
import com.wooshop.utils.FileUtil;
import com.wooshop.utils.PageUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Zheng Jie
 * @date 2018-12-31
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "qiNiu")
public class QiNiuServiceImpl extends CommonServiceImpl<QiniuContentMapper, QiniuContent> implements QiNiuService {

    private final QiniuConfigMapper qiniuConfigMapper;
    private final QiniuContentMapper qiniuContentMapper;
    private final WooshopConfigCategoryService wooshopConfigCategoryService;
    private final LocalStorageMapper localStorageMapper;

    @Value("${qiniu.max-size}")
    private Long maxSize;

    @Override
    @Cacheable(key = "'config'")
    public QiniuConfig find() {
        return qiniuConfigMapper.selectById(1L);
    }

    @Override
    @CachePut(key = "'config'")
    @Transactional(rollbackFor = Exception.class)
    public QiniuConfig config(QiniuConfig qiniuConfig) {
        qiniuConfig.setId(1L);
        String http = "http://", https = "https://";
        if (!(qiniuConfig.getHost().toLowerCase().startsWith(http)||qiniuConfig.getHost().toLowerCase().startsWith(https))) {
            throw new BadRequestException("外链域名必须以http://或者https://开头");
        }
        qiniuConfigMapper.updateById(qiniuConfig);
        return qiniuConfig;
    }

    @Override
    public PageInfo<?> queryAll(QiniuContentQueryParam query, Pageable pageable){
        /*IPage<QiniuContent> page = PageUtil.toMybatisPage(pageable);
        IPage<QiniuContent> pageList = qiniuContentMapper.selectPage(page, QueryHelpMybatisPlus.getPredicate(query));*/

        IPage<LocalStorage> page = PageUtil.toMybatisPage(pageable);
        IPage<LocalStorage> pageList = localStorageMapper.selectPage(page, QueryHelpMybatisPlus.getPredicate(query));
        return ConvertUtil.convertPage(pageList, QiniuContentDto.class);
    }

    @Override
    public List<QiniuContent> queryAll(QiniuContentQueryParam query) {
        return qiniuContentMapper.selectList(QueryHelpMybatisPlus.getPredicate(query));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LocalStorage /*QiniuContent*/ upload(MultipartFile file, QiniuConfig qiniuConfig,Integer categoryId) {
        FileUtil.checkSize(maxSize, file.getSize());
        if(qiniuConfig.getId() == null){
            throw new BadRequestException("请先添加相应配置，再操作");
        }
        // 构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(QiNiuUtil.getRegion(qiniuConfig.getZone()));
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(qiniuConfig.getAccessKey(), qiniuConfig.getSecretKey());
        String upToken = auth.uploadToken(qiniuConfig.getBucket());
        try {
            String key = file.getOriginalFilename();
            if(qiniuContentMapper.findByKey(key) != null) {
                key = QiNiuUtil.getKey(key);
            }
            Response response = uploadManager.put(file.getBytes(), key, upToken);
            //解析上传成功的结果

            DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
//            QiniuContent content = qiniuContentMapper.findByKey(FileUtil.getFileNameNoEx(putRet.key));
            LocalStorage content = localStorageMapper.findByKey(FileUtil.getFileNameNoEx(putRet.key));
            if(content == null){
                //存入数据库
                WooshopConfigCategory byId = wooshopConfigCategoryService.getById(categoryId);
               /* QiniuContent qiniuContent = new QiniuContent();
                qiniuContent.setSuffix(FileUtil.getExtensionName(putRet.key));
                qiniuContent.setBucket(qiniuConfig.getBucket());
                qiniuContent.setType(qiniuConfig.getType());
                qiniuContent.setName(FileUtil.getFileNameNoEx(putRet.key));
                qiniuContent.setUrl(qiniuConfig.getHost()+"/"+putRet.key);
                qiniuContent.setSize(FileUtil.getSize(Integer.parseInt(file.getSize()+"")));
                qiniuContent.setCategoryId(categoryId);
                qiniuContentMapper.insert(qiniuContent);*/
                 LocalStorage localStorage = new LocalStorage();
                localStorage.setSuffix(FileUtil.getExtensionName(putRet.key));
                localStorage.setBucket(qiniuConfig.getBucket());
                localStorage.setType(qiniuConfig.getType());
                localStorage.setName(FileUtil.getFileNameNoEx(putRet.key));
                localStorage.setPath(qiniuConfig.getHost()+"/"+putRet.key);
                localStorage.setSize(FileUtil.getSize(Integer.parseInt(file.getSize()+"")));
                localStorage.setCategoryId(categoryId);
                localStorage.setCategoryPath(byId.getPath());//设置分类路径
                localStorage.setStorageType(2);//设置类型

                localStorageMapper.insert(localStorage);

                return localStorage;
            }
            return content;
        } catch (Exception e) {
           throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public QiniuContent findByContentId(Long id) {
        return qiniuContentMapper.selectById(id);
    }

    @Override
    public String download(QiniuContent content,QiniuConfig config){
        String finalUrl;
        String type = "公开";
        if(type.equals(content.getType())){
            finalUrl  = content.getUrl();
        } else {
            Auth auth = Auth.create(config.getAccessKey(), config.getSecretKey());
            // 1小时，可以自定义链接过期时间
            long expireInSeconds = 3600;
            finalUrl = auth.privateDownloadUrl(content.getUrl(), expireInSeconds);
        }
        return finalUrl;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(QiniuContent content, QiniuConfig config) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(QiNiuUtil.getRegion(config.getZone()));
        Auth auth = Auth.create(config.getAccessKey(), config.getSecretKey());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(content.getBucket(), content.getName() + "." + content.getSuffix());
            qiniuContentMapper.deleteById(content);
        } catch (QiniuException ex) {
            qiniuContentMapper.deleteById(content);
        }
    }

    /**
     * 整合 七牛云删除
     * @param content 文件
     * @param config 配置
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void localMergeQiniuDelete(QiniuContent content, QiniuConfig config) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(QiNiuUtil.getRegion(config.getZone()));
        Auth auth = Auth.create(config.getAccessKey(), config.getSecretKey());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(content.getBucket(), content.getName() + "." + content.getSuffix());
            localStorageMapper.deleteById(content);
        } catch (QiniuException ex) {
            localStorageMapper.deleteById(content);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void synchronize(QiniuConfig config) {
        if(config.getId() == null){
            throw new BadRequestException("请先添加相应配置，再操作");
        }
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(QiNiuUtil.getRegion(config.getZone()));
        Auth auth = Auth.create(config.getAccessKey(), config.getSecretKey());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        //文件名前缀
        String prefix = "";
        //每次迭代的长度限制，最大1000，推荐值 1000
        int limit = 1000;
        //指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
        String delimiter = "";
        //列举空间文件列表
        BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(config.getBucket(), prefix, limit, delimiter);
        while (fileListIterator.hasNext()) {
            //处理获取的file list结果
//            QiniuContent qiniuContent;
            LocalStorage localStorage;
            FileInfo[] items = fileListIterator.next();
            if (items == null || items.length == 0) {
                continue;
            }
            for (FileInfo item : items) {
//                if(qiniuContentMapper.findByKey(FileUtil.getFileNameNoEx(item.key)) == null){
                if(localStorageMapper.findByKey(FileUtil.getFileNameNoEx(item.key)) == null){
                    /*qiniuContent = new QiniuContent();
                    qiniuContent.setSize(FileUtil.getSize(Integer.parseInt(item.fsize+"")));
                    qiniuContent.setSuffix(FileUtil.getExtensionName(item.key));
                    qiniuContent.setName(FileUtil.getFileNameNoEx(item.key));
                    qiniuContent.setType(config.getType());
                    qiniuContent.setBucket(config.getBucket());
                    qiniuContent.setUrl(config.getHost()+"/"+item.key);
                    qiniuContent.setCategoryId(2);//七牛云资源分类表id 默认为2
                    qiniuContentMapper.insert(qiniuContent);*/
                    localStorage = new LocalStorage();
                    localStorage.setSize(FileUtil.getSize(Integer.parseInt(item.fsize+"")));
                    localStorage.setSuffix(FileUtil.getExtensionName(item.key));
                    localStorage.setName(FileUtil.getFileNameNoEx(item.key));
                    localStorage.setType(config.getType());
                    localStorage.setBucket(config.getBucket());
                    localStorage.setPath(config.getHost()+"/"+item.key);
                    localStorage.setCategoryId(2);//七牛云资源分类表id 默认为2
                    localStorage.setCategoryPath("/1/");//七牛云资源分类表id 默认为2
                    localStorage.setStorageType(2);//七牛云资源分类表id 默认为2
                    localStorageMapper.insert(localStorage);


                }
            }
        }
    }

    @Override
    public void deleteAll(Long[] ids, QiniuConfig config) {
        for (Long id : ids) {
            delete(findByContentId(id), config);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(String type) {
        qiniuConfigMapper.updateType(type);
    }

    @Override
    public void downloadList(List<QiniuContent> queryAll, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (QiniuContent content : queryAll) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("文件名", content.getName());
            map.put("文件类型", content.getSuffix());
            map.put("空间名称", content.getBucket());
            map.put("文件大小", content.getSize());
            map.put("空间类型", content.getType());
            map.put("创建日期", content.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}

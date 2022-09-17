


package com.wooshop.modules.category.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wooshop.common.QueryHelpPlus;
import com.wooshop.utils.CacheKey;
import com.wooshop.utils.FileUtil;
import com.wooshop.utils.PageUtil;
import com.wooshop.utils.RedisUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Pageable;
import com.wooshop.modules.service.impl.BaseServiceImpl;
import lombok.AllArgsConstructor;
import java.io.IOException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import com.wooshop.domain.PageResult;
import javax.servlet.http.HttpServletResponse;

import com.wooshop.dozer.service.IGenerator;

import java.util.stream.Collectors;

import com.wooshop.modules.category.domain.WooshopConfigCategory;
import org.springframework.stereotype.Service;
import com.wooshop.modules.category.service.dto.WooshopConfigCategoryQueryCriteria;
import com.wooshop.modules.category.service.mapper.WooshopConfigCategoryMapper;
import com.wooshop.modules.category.service.WooshopConfigCategoryService;
import com.wooshop.modules.category.service.dto.WooshopConfigCategoryDto;


/**
* @author woo
* @date 2021-11-15
* 注意：
* 本软件为www.wooshopxingyun.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
//@CacheConfig(cacheNames = "wooshop")
public class WooshopConfigCategoryServiceImpl extends BaseServiceImpl<WooshopConfigCategoryMapper, WooshopConfigCategory> implements WooshopConfigCategoryService {

    private final IGenerator generator;
    private final RedisUtils redisUtils;
//    private final LocalStorageService localStorageService;

    @Override
//    @Cacheable(key = "'category:'+#criteria.type+'-'+#criteria.enabled+'-'+#criteria.getName()+'-'+#criteria.path")
    public PageResult<WooshopConfigCategoryDto> queryAll(WooshopConfigCategoryQueryCriteria criteria, Pageable pageable) {
//        setPageAndOrder(pageable.getPageNumber(), pageable.getPageSize(),pageable.getSort().toString());

//        PageInfo<WooshopConfigCategory> page = new PageInfo<>(queryAll(criteria));generator.convertPageInfo(page, WooshopConfigCategoryDto.class)
//        return generator.convertPageInfo(page,WooshopConfigCategoryDto.class);
        List<WooshopConfigCategoryDto> category = getCategory(criteria.getType(), criteria.getEnabled(), criteria.getName(), criteria.getPath(), null);

//        PageInfo<WooshopConfigCategoryDto> page = new PageInfo<>(category);
        PageResult<WooshopConfigCategoryDto> pageResult = new PageResult<>();
        pageResult.setTotalElements(category.size());
        pageResult.setContent(PageUtil.startPage(category,pageable.getPageNumber()+1,pageable.getPageSize()));
        return pageResult;
    }


    @Override
    @Caching(cacheable = {@Cacheable(cacheNames = CacheKey.WOOSHOP_CATEGORY_ID,key = "#criteria.id",
            condition = "#criteria.id!=null&&#criteria.enabled==null&&#criteria.name==null&&#criteria.path==null&&#criteria.parentPid==null&&#criteria.type==null")})
    public List<WooshopConfigCategory> queryAll(WooshopConfigCategoryQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(WooshopConfigCategory.class, criteria));
    }


    @Override
    public void download(List<WooshopConfigCategoryDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WooshopConfigCategoryDto wooshopConfigCategory : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("分类父级ID", wooshopConfigCategory.getParentPid());
            map.put("分类路径", wooshopConfigCategory.getPath());
            map.put("分类名称", wooshopConfigCategory.getName());
            map.put("分类类型，1 附件分类，2 文章分类", wooshopConfigCategory.getType());
            map.put("分类地址，", wooshopConfigCategory.getUrl());
            map.put("分类扩展字段 ", wooshopConfigCategory.getExtra());
            map.put("分类状态, 1启用，0失效", wooshopConfigCategory.getEnabled());
            map.put("分类排序", wooshopConfigCategory.getSort());
            map.put("创建时间", wooshopConfigCategory.getCreateTime());
//            map.put("更新时间", wooshopConfigCategory.getUpdateTime());
//            map.put(" createUid",  wooshopConfigCategory.getCreateUid());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }


    @Override
//    @Cacheable(key = "'category:'+#p1+'-'+#p0+'-'+#p2+'-'+#p3+'-'+#p4")
    public List<WooshopConfigCategoryDto> getCategory(Integer type, Integer status, String name,String path, List<Integer> categoryIdList) {
        List<WooshopConfigCategoryDto> configCategoryDtoList = (List<WooshopConfigCategoryDto>)redisUtils.get(CacheKey.WOOSHOP_CATEGORY + type + "-" + status + "-" + name + "-" + path + "-" + categoryIdList);
        if (configCategoryDtoList==null){
            configCategoryDtoList = queryCategory(type, status, name, null, path);
            redisUtils.set(CacheKey.WOOSHOP_CATEGORY+type+"-"+status+"-"+name+"-"+path+"-"+categoryIdList,configCategoryDtoList);
        }
        return configCategoryDtoList;
    }

    @Override
    public void addCategory(Integer categoryId, String name, Integer sort, Integer type) {
        WooshopConfigCategory wooshopConfigCategory = baseMapper.selectById(categoryId);
        WooshopConfigCategory insertCategory=new WooshopConfigCategory();
        insertCategory.setParentPid(wooshopConfigCategory.getId());//设置父类id
        insertCategory.setPath(wooshopConfigCategory.getPath()+wooshopConfigCategory.getId()+"/");//设置分类路径
        insertCategory.setName(name);//设置分类名称
        insertCategory.setType(type);//1附件、2文章分类
        insertCategory.setSort(sort);//排序
        baseMapper.insert(insertCategory);
    }

    /**
     * 所有新增分类入口
     * @param configCategory
     * @return
     */
    @Override
    public boolean addNewCategory(WooshopConfigCategory configCategory) {
//        redisUtils.del(CacheKey.WOOSHOP_CATEGORY );
        // 判断是一级分类 还是二级分类
        if (configCategory.getParentPid()==null||configCategory.getParentPid()==0){
            int insert = baseMapper.insert(configCategory);
            return insert>0;
        }else {
            // 是二级 先去数据库查询下
            WooshopConfigCategory wooshopConfigCategory = baseMapper.selectById(configCategory.getParentPid());
            WooshopConfigCategory insertCategory=new WooshopConfigCategory();
            insertCategory.setParentPid(wooshopConfigCategory.getId());//设置父类id
            insertCategory.setPath(wooshopConfigCategory.getPath()+wooshopConfigCategory.getId()+"/");//设置分类路径
            insertCategory.setName(configCategory.getName());//设置分类名称
            insertCategory.setType(configCategory.getType());//1附件、2商品分类
            insertCategory.setSort(configCategory.getSort());//排序
            insertCategory.setExtra(configCategory.getExtra());//扩展字段
            insertCategory.setUrl(configCategory.getUrl());//地址
            insertCategory.setCreateUid(configCategory.getCreateUid());//用户id
            int insert = baseMapper.insert(insertCategory);
            return insert>0;
        }

    }


    /**
     * 树形结构分类
     * @param type 类型
     * @param enabled 状态
     * @param name 分类表id
     * @param categoryIdList  id
     * @param path 根据路径
     * @return
     */

    public List<WooshopConfigCategoryDto> queryCategory(Integer type, Integer enabled,String name, List<Integer> categoryIdList,String path) {
        //循环数据，把数据对象变成带list结构的vo
        List<WooshopConfigCategoryDto> treeList = new ArrayList<>();

        LambdaQueryWrapper<WooshopConfigCategory> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(WooshopConfigCategory::getType, type);

        if(null != categoryIdList && categoryIdList.size() > 0){
            lambdaQueryWrapper.in(WooshopConfigCategory::getId, categoryIdList);
        }

        if(enabled != null){
            lambdaQueryWrapper.eq(WooshopConfigCategory::getEnabled, enabled);
        }
        if(StringUtils.isNotBlank(name)){ // 根据名称模糊搜索
            lambdaQueryWrapper.like(WooshopConfigCategory::getName,name);
        }
        if(StringUtils.isNotBlank(path)){ // 排除那些路径
            lambdaQueryWrapper.likeRight(WooshopConfigCategory::getPath,path);
        }

//        lambdaQueryWrapper.orderByDesc(WooshopConfigCategory::getSort);
//        lambdaQueryWrapper.orderByAsc(WooshopConfigCategory::getId);
        lambdaQueryWrapper.orderByAsc(WooshopConfigCategory::getSort);
        List<WooshopConfigCategory> allTree = baseMapper.selectList(lambdaQueryWrapper);
        if(allTree == null){
            return null;
        }
        // 根据名称搜索特殊处理 这里仅仅处理两层搜索后有子父级关系的数据
        if(StringUtils.isNotBlank(name) && allTree.size() >0){
            List<WooshopConfigCategory> searchCategory = new ArrayList<>();
            List<Integer> categoryIds = allTree.stream().map(WooshopConfigCategory::getId).collect(Collectors.toList());

            List<Integer> pidList = allTree.stream().filter(c -> c.getParentPid() > 0 && !categoryIds.contains(c.getParentPid()))
                    .map(WooshopConfigCategory::getParentPid).distinct().collect(Collectors.toList());
            if (CollUtil.isNotEmpty(pidList)) {
                pidList.forEach(pid -> {
                    searchCategory.add(baseMapper.selectById(pid));
                });
            }
            allTree.addAll(searchCategory);
        }

        for (WooshopConfigCategory category: allTree) {
            WooshopConfigCategoryDto categoryTreeVo = new WooshopConfigCategoryDto();
            BeanUtils.copyProperties(category, categoryTreeVo);
            treeList.add(categoryTreeVo);
        }


        //返回
        Map<Integer, WooshopConfigCategoryDto> map = new HashMap<>();
        //ID 为 key 存储到map 中
        for (WooshopConfigCategoryDto categoryTreeVo1 : treeList) {
            map.put(categoryTreeVo1.getId(), categoryTreeVo1);
        }

        List<WooshopConfigCategoryDto> list = new ArrayList<>();
        for (WooshopConfigCategoryDto tree : treeList) {
            //子集ID返回对象，有则添加。
            WooshopConfigCategoryDto tree1 = map.get(tree.getParentPid());
            if(tree1 != null){
                tree1.getChildClass().add(tree);
            }else {
                list.add(tree);
            }
        }
        System.out.println("无限极分类 : getTree:" + JSON.toJSONString(list));
        return list;
    }

}

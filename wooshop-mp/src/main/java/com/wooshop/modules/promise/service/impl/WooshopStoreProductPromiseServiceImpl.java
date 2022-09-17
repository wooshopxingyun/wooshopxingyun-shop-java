


package com.wooshop.modules.promise.service.impl;

import com.github.pagehelper.PageInfo;
import com.wooshop.common.QueryHelpPlus;
import com.wooshop.utils.FileUtil;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import com.wooshop.modules.service.impl.BaseServiceImpl;
import lombok.AllArgsConstructor;
import java.io.IOException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.LinkedHashMap;
import com.wooshop.domain.PageResult;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import com.wooshop.dozer.service.IGenerator;

import java.util.List;
import java.util.Map;
import com.wooshop.modules.promise.domain.WooshopStoreProductPromise;
import org.springframework.stereotype.Service;
import com.wooshop.modules.promise.service.dto.WooshopStoreProductPromiseQueryCriteria;
import com.wooshop.modules.promise.service.mapper.WooshopStoreProductPromiseMapper;
import com.wooshop.modules.promise.service.WooshopStoreProductPromiseService;
import com.wooshop.modules.promise.service.dto.WooshopStoreProductPromiseDto;


/**
* @author woo
* @date 2021-11-25
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
@CacheConfig(cacheNames = "ProductPromise")
public class WooshopStoreProductPromiseServiceImpl extends BaseServiceImpl<WooshopStoreProductPromiseMapper, WooshopStoreProductPromise> implements WooshopStoreProductPromiseService {

    private final IGenerator generator;

    @Override
    @Cacheable(key = "'Promise:'+#criteria.name+'-'+#criteria.overview+'-'+#criteria.isStart+'-'+#pageable.pageNumber+'-'+#pageable.pageSize+'-'+#pageable.sort.toString().replace(':','')")
    public PageResult<WooshopStoreProductPromiseDto> queryAll(WooshopStoreProductPromiseQueryCriteria criteria, Pageable pageable) {
        setPageAndOrder(pageable.getPageNumber(), pageable.getPageSize(),pageable.getSort().toString());
        PageInfo<WooshopStoreProductPromise> page = new PageInfo<>(queryAll(criteria));
        return generator.convertPageInfo(page,WooshopStoreProductPromiseDto.class);
    }


    @Override
    public List<WooshopStoreProductPromise> queryAll(WooshopStoreProductPromiseQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(WooshopStoreProductPromise.class, criteria));
    }


    @Override
    public void download(List<WooshopStoreProductPromiseDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WooshopStoreProductPromiseDto wooshopStoreProductPromise : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("服务名称", wooshopStoreProductPromise.getName());
            map.put("概述", wooshopStoreProductPromise.getOverview());
            map.put("状态：1开启、0关闭", wooshopStoreProductPromise.getIsStart());
            map.put("删除：1删除", wooshopStoreProductPromise.getIsDel());
            map.put("创建时间", wooshopStoreProductPromise.getCreateTime());
            map.put("更新时间", wooshopStoreProductPromise.getUpdateTime());
            map.put("创建人", wooshopStoreProductPromise.getUid());
            map.put(" storeId",  wooshopStoreProductPromise.getStoreId());
            map.put("排序", wooshopStoreProductPromise.getSort());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}




package com.wooshop.modules.stores.service.impl;

import cn.hutool.json.JSONArray;
import com.github.pagehelper.PageInfo;
import com.wooshop.aspect.CacheRemove;
import com.wooshop.common.QueryHelpPlus;
import com.wooshop.modules.syscity.domain.WooshopSysCity;
import com.wooshop.modules.syscity.service.WooshopSysCityService;
import com.wooshop.utils.CacheKey;
import com.wooshop.utils.RedisUtils;
import com.wooshop.utils.FileUtil;
import org.springframework.cache.annotation.*;
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

import com.wooshop.modules.stores.domain.WooshopStores;
import org.springframework.stereotype.Service;
import com.wooshop.modules.stores.service.dto.WooshopStoresQueryCriteria;
import com.wooshop.modules.stores.service.mapper.WooshopStoresMapper;
import com.wooshop.modules.stores.service.WooshopStoresService;
import com.wooshop.modules.stores.service.dto.WooshopStoresDto;


/**
 * @author woo
 * @date 2021-12-21
 * 注意：
 * 本软件为www.wooshopxingyun.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */


@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
//@CacheConfig(cacheNames = "wooshop")
public class WooshopStoresServiceImpl extends BaseServiceImpl<WooshopStoresMapper, WooshopStores> implements WooshopStoresService {

    private final IGenerator generator;
    private final WooshopSysCityService cityService;
    private final RedisUtils redisUtils;

    @Override
    @Caching(cacheable = {@Cacheable(cacheNames=CacheKey.WOOSHOP_STORES_QUERY,key = "#criteria.id+'-'+#criteria.telephone+'-'+#criteria.storesName+'-'+#criteria.isStart+'-'+#pageable.pageNumber+'-'+#pageable.pageSize+'-'+#pageable.sort.toString().replace(':','')")
            , @Cacheable(cacheNames=CacheKey.WOOSHOP_STORES_ID,key = "#p0.id", condition = "#criteria.id!=null&&#criteria.telephone==null&&#criteria.storesName==null&&#criteria.isStart==null")})
    public PageResult<WooshopStoresDto> queryAllPage(WooshopStoresQueryCriteria criteria, Pageable pageable) {
        setPageAndOrder(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort().toString());
        PageInfo<WooshopStores> page = new PageInfo<>(queryAll(criteria));
        PageResult<WooshopStoresDto> wooshopStoresDtoPageResult = generator.convertPageInfo(page, WooshopStoresDto.class);
        wooshopStoresDtoPageResult.getContent().forEach(item -> {
            JSONArray jsonArray = new JSONArray(item.getStoresAddress());
            WooshopSysCity byId = cityService.getById(jsonArray.getInt(2));
            item.setStoresAddressName(byId.getMergersName() + "/" + byId.getName());
        });
        return wooshopStoresDtoPageResult;
    }


    @Override
    public List<WooshopStores> queryAll(WooshopStoresQueryCriteria criteria) {
        return baseMapper.selectList(QueryHelpPlus.getPredicate(WooshopStores.class, criteria));
    }


    @Override
    public void download(List<WooshopStoresDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WooshopStoresDto wooshopStores : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("店铺名称", wooshopStores.getStoresName());
            map.put("店铺信息", wooshopStores.getStoresInfo());
            map.put("店铺联系手机号码", wooshopStores.getTelephone());
            map.put("店铺联系称呼", wooshopStores.getContactName());
            map.put("省市区", wooshopStores.getStoresAddress());
            map.put("详细地址", wooshopStores.getDetailedAddress());
            map.put("门店logo", wooshopStores.getStoresLogo());
            map.put("店铺地址纬度", wooshopStores.getLatitude());
            map.put("店铺地址经度", wooshopStores.getLongitude());
            map.put("允许核销有效日期", wooshopStores.getValidTime());
            map.put("营业时间", wooshopStores.getDayTime());
            map.put("店铺状态:1开启 0隐藏", wooshopStores.getIsStart());
            map.put("是否删除", wooshopStores.getIsDel());
            map.put("是否允许自提:1允许 0不允许", wooshopStores.getIsTake());
            map.put("添加时间", wooshopStores.getCreateTime());
            map.put("更新时间", wooshopStores.getUpdateTime());
            map.put("管理用户id", wooshopStores.getUid());
            map.put("0 需要审核 并且待审核，1 不需要审核 2需要审核 且审核通过 3 需要审核 且审核未通过", wooshopStores.getIsAuth());
            map.put("审核信息", wooshopStores.getAuthMessage());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    @CachePut(cacheNames=CacheKey.WOOSHOP_STORES_ID,key = "#p0.id")
    @CacheRemove(value = CacheKey.WOOSHOP_STORES_QUERY, key = "*")
    public PageResult<WooshopStoresDto> addAndUpdate(WooshopStores resources) {
        if (resources.getId() == null) {
            save(resources);
        } else {
            updateById(resources);
        }
        List<WooshopStores> resList=new ArrayList<>();
        resList.add(resources);
        PageInfo<WooshopStores> page = new PageInfo<>(resList);
//        PageResult<WooshopStoresDto> dtoPageResult =
        return generator.convertPageInfo(page, WooshopStoresDto.class);
    }

    @Override
//    @CacheEvict(cacheNames=CacheKey.WOOSHOP_STORES_ID,key = "#p0.id")
    @CacheRemove(value = CacheKey.WOOSHOP_STORES_QUERY, key = "*")
    public void cacheRemoveById(Long id) {
        removeById(id);
    }

}

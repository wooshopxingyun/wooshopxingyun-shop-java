


package com.wooshop.modules.unit.service.impl;

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
import com.wooshop.modules.unit.domain.WooshopStoreProductUnit;
import org.springframework.stereotype.Service;
import com.wooshop.modules.unit.service.dto.WooshopStoreProductUnitQueryCriteria;
import com.wooshop.modules.unit.service.mapper.WooshopStoreProductUnitMapper;
import com.wooshop.modules.unit.service.WooshopStoreProductUnitService;
import com.wooshop.modules.unit.service.dto.WooshopStoreProductUnitDto;


/**
* @author woo
* @date 2021-11-24
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
@CacheConfig(cacheNames = "ProductUnit")
public class WooshopStoreProductUnitServiceImpl extends BaseServiceImpl<WooshopStoreProductUnitMapper, WooshopStoreProductUnit> implements WooshopStoreProductUnitService {

    private final IGenerator generator;

    @Override
    @Cacheable(key = "'Unit:'+#criteria.name+'-'+#criteria.isStart+'-'+#pageable.pageNumber+'-'+#pageable.pageSize+'-'+#pageable.sort.toString().replace(':','')")
    public PageResult<WooshopStoreProductUnitDto> queryAll(WooshopStoreProductUnitQueryCriteria criteria, Pageable pageable) {
        setPageAndOrder(pageable.getPageNumber(), pageable.getPageSize(),pageable.getSort().toString());
        PageInfo<WooshopStoreProductUnit> page = new PageInfo<>(queryAll(criteria));
        return generator.convertPageInfo(page,WooshopStoreProductUnitDto.class);
    }


    @Override
    public List<WooshopStoreProductUnit> queryAll(WooshopStoreProductUnitQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(WooshopStoreProductUnit.class, criteria));
    }


    @Override
    public void download(List<WooshopStoreProductUnitDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WooshopStoreProductUnitDto wooshopStoreProductUnit : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("单位名称", wooshopStoreProductUnit.getName());
            map.put("状态：1开启、0关闭", wooshopStoreProductUnit.getIsStart());
            map.put("删除：1删除", wooshopStoreProductUnit.getIsDel());
            map.put("创建时间", wooshopStoreProductUnit.getCreateTime());
            map.put("更新时间", wooshopStoreProductUnit.getUpdateTime());
            map.put("创建人", wooshopStoreProductUnit.getUid());
            map.put("storeId",  wooshopStoreProductUnit.getStoreId());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}

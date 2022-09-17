


package com.wooshop.modules.specification.service.impl;

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
import com.wooshop.modules.specification.domain.WooshopStoreProductSpecification;
import org.springframework.stereotype.Service;
import com.wooshop.modules.specification.service.dto.WooshopStoreProductSpecificationQueryCriteria;
import com.wooshop.modules.specification.service.mapper.WooshopStoreProductSpecificationMapper;
import com.wooshop.modules.specification.service.WooshopStoreProductSpecificationService;
import com.wooshop.modules.specification.service.dto.WooshopStoreProductSpecificationDto;


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
@CacheConfig(cacheNames = "product")
public class WooshopStoreProductSpecificationServiceImpl extends BaseServiceImpl<WooshopStoreProductSpecificationMapper, WooshopStoreProductSpecification> implements WooshopStoreProductSpecificationService {

    private final IGenerator generator;

    @Override
    @Cacheable(key = "'specification:'+#criteria.name+'-'+#criteria.isStart+'-'+#pageable.pageNumber+'-'+#pageable.pageSize+'-'+#pageable.sort.toString().replace(':','')")
    public PageResult<WooshopStoreProductSpecificationDto> queryAll(WooshopStoreProductSpecificationQueryCriteria criteria, Pageable pageable) {
        setPageAndOrder(pageable.getPageNumber(), pageable.getPageSize(),pageable.getSort().toString());
        PageInfo<WooshopStoreProductSpecification> page = new PageInfo<>(queryAll(criteria));
        PageResult<WooshopStoreProductSpecificationDto> wooshopStoreProductSpecificationDtoPageResult = generator.convertPageInfo(page, WooshopStoreProductSpecificationDto.class);
        System.out.println("json"+wooshopStoreProductSpecificationDtoPageResult);
        return wooshopStoreProductSpecificationDtoPageResult;
    }


    @Override
    public List<WooshopStoreProductSpecification> queryAll(WooshopStoreProductSpecificationQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(WooshopStoreProductSpecification.class, criteria));
    }


    @Override
    public void download(List<WooshopStoreProductSpecificationDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WooshopStoreProductSpecificationDto wooshopStoreProductSpecification : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("规格名称", wooshopStoreProductSpecification.getName());
            map.put("规格值", wooshopStoreProductSpecification.getParams());
            map.put("排序", wooshopStoreProductSpecification.getSort());
            map.put("逻辑删除：1删除", wooshopStoreProductSpecification.getIsDel());
            map.put("创建时间", wooshopStoreProductSpecification.getCreateTime());
            map.put("更新时间", wooshopStoreProductSpecification.getUpdateTime());
            map.put("开启状态：1开启、0关闭", wooshopStoreProductSpecification.getIsStart());
            map.put("创建人", wooshopStoreProductSpecification.getUid());
            map.put(" storeId",  wooshopStoreProductSpecification.getStoreId());
            map.put("单位名称", wooshopStoreProductSpecification.getUnitName());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}

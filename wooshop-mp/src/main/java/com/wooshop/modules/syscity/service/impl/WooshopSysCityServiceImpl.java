


package com.wooshop.modules.syscity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.wooshop.common.QueryHelpPlus;
import com.wooshop.modules.syscity.service.vo.WooshopSysCityVo;
import com.wooshop.utils.CacheKey;
import com.wooshop.utils.FileUtil;
import com.wooshop.utils.RedisUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
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

import com.wooshop.modules.syscity.domain.WooshopSysCity;
import org.springframework.stereotype.Service;
import com.wooshop.modules.syscity.service.dto.WooshopSysCityQueryCriteria;
import com.wooshop.modules.syscity.service.mapper.WooshopSysCityMapper;
import com.wooshop.modules.syscity.service.WooshopSysCityService;
import com.wooshop.modules.syscity.service.dto.WooshopSysCityDto;


/**
* @author woo
* @date 2021-11-20
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
@CacheConfig(cacheNames = "wooshop")
public class WooshopSysCityServiceImpl extends BaseServiceImpl<WooshopSysCityMapper, WooshopSysCity> implements WooshopSysCityService {

    private final IGenerator generator;
    private final RedisUtils redisUtils;

    @Override
    /*@Caching(cacheable= {@Cacheable(key = "'menuName:' + #p0.menuName"),
            @Cacheable(key = "'menuName:' +#pageable.getPageNumber()+'-'+ #pageable.getPageSize()",
                    condition="#criteria.menuName!=null&&#criteria.menuId!=null&&#criteria.enabled!=null")})*/
    public PageResult<WooshopSysCityDto> queryAll(WooshopSysCityQueryCriteria criteria, Pageable pageable) {
        setPageAndOrder(pageable.getPageNumber(), pageable.getPageSize(),pageable.getSort().toString());
        PageInfo<WooshopSysCity> page = new PageInfo<>(queryAll(criteria));
        return generator.convertPageInfo(page,WooshopSysCityDto.class);
    }


    @Override
    public List<WooshopSysCity> queryAll(WooshopSysCityQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(WooshopSysCity.class,criteria));
    }


    @Override
    public void download(List<WooshopSysCityDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WooshopSysCityDto wooshopSysCity : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("城市id", wooshopSysCity.getCityId());
            map.put("省市级别", wooshopSysCity.getCityLevel());
            map.put("父级id", wooshopSysCity.getParentId());
            map.put("区号", wooshopSysCity.getAreaCode());
            map.put("名称", wooshopSysCity.getName());
            map.put("合并名称", wooshopSysCity.getMergerName());
            map.put("经度", wooshopSysCity.getLng());
            map.put("纬度", wooshopSysCity.getLat());
            map.put("1展示 0不展示", wooshopSysCity.getIsStart());
            map.put("国标编码", wooshopSysCity.getStandardCode());
            map.put("邮编", wooshopSysCity.getPostcode());
            map.put("创建时间", wooshopSysCity.getCreateTime());
            map.put("更新时间", wooshopSysCity.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    @Cacheable(key = "'CityTree:'")
    public List<WooshopSysCityVo> getCityTree() {
        List<WooshopSysCityVo> sysCitytreeList = new ArrayList<>();
        QueryWrapper<WooshopSysCity> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("is_start",true);
        List<WooshopSysCity> allTree = baseMapper.selectList(queryWrapper);
        if (allTree == null) {
            return sysCitytreeList;
        }
        for (WooshopSysCity sysCity : allTree) {

            WooshopSysCityVo convert = generator.convert(sysCity, WooshopSysCityVo.class);
            sysCitytreeList.add(convert);
        }
        //返回
        Map<Integer, WooshopSysCityVo> map = new HashMap<>();
        //cityId 为 key 存储到map 中
        for (WooshopSysCityVo sysCityTreeVo1 : sysCitytreeList) {
            map.put(sysCityTreeVo1.getCityId(), sysCityTreeVo1);
        }
        List<WooshopSysCityVo> wooshopSysCityVolist = new ArrayList<>();
        for (WooshopSysCityVo sysTree : sysCitytreeList) {
            //子集ID返回对象，有则添加。
            WooshopSysCityVo getParentId = map.get(sysTree.getParentId());
            if (getParentId != null) {
                getParentId.getChildren().add(sysTree);
            } else {
                wooshopSysCityVolist.add(sysTree);
            }
        }
        return wooshopSysCityVolist;
    }

    @Override
    public WooshopSysCityDto addAndUpdate(WooshopSysCity resources) {
        if (resources.getId()==null){
            save(resources);
        }else {
            updateById(resources);
        }
        redisUtils.del(CacheKey.WOOSHOP_CITYTREE);
        return generator.convert(resources,WooshopSysCityDto.class);
    }
}




package com.wooshop.modules.goods.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageInfo;
import com.wooshop.aspect.CacheRemove;
import com.wooshop.common.QueryHelpPlus;
import com.wooshop.utils.CacheKey;

import com.wooshop.utils.FileUtil;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
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
import com.wooshop.modules.goods.domain.WooshopStoreGoodsAttrDetails;
import org.springframework.stereotype.Service;
import com.wooshop.modules.goods.service.dto.WooshopStoreGoodsAttrDetailsQueryCriteria;
import com.wooshop.modules.goods.service.mapper.WooshopStoreGoodsAttrDetailsMapper;
import com.wooshop.modules.goods.service.WooshopStoreGoodsAttrDetailsService;
import com.wooshop.modules.goods.service.dto.WooshopStoreGoodsAttrDetailsDto;


/**
* @author woo
* @date 2021-12-05
* 注意：
* 本软件为www.wooshopxingyun.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
@CacheConfig(cacheNames = "AttrDetails")
public class WooshopStoreGoodsAttrDetailsServiceImpl extends BaseServiceImpl<WooshopStoreGoodsAttrDetailsMapper, WooshopStoreGoodsAttrDetails> implements WooshopStoreGoodsAttrDetailsService {

    private final IGenerator generator;

    @Override
    public PageResult<WooshopStoreGoodsAttrDetailsDto> queryAll(WooshopStoreGoodsAttrDetailsQueryCriteria criteria, Pageable pageable) {
        setPageAndOrder(pageable.getPageNumber(), pageable.getPageSize(),pageable.getSort().toString());
        PageInfo<WooshopStoreGoodsAttrDetails> page = new PageInfo<>(queryAll(criteria));
        return generator.convertPageInfo(page,WooshopStoreGoodsAttrDetailsDto.class);
    }


    @Override
    @Cacheable(key = "'id:'+#criteria.activityType+'-'+#criteria.goodsId",condition ="#criteria.activityType!=null&&#criteria.goodsId!=null&&#criteria.id==null" )
    public List<WooshopStoreGoodsAttrDetails> queryAll(WooshopStoreGoodsAttrDetailsQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(WooshopStoreGoodsAttrDetails.class, criteria));
    }


    @Override
    public void download(List<WooshopStoreGoodsAttrDetailsDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WooshopStoreGoodsAttrDetailsDto wooshopStoreGoodsAttrDetails : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("关联商品id", wooshopStoreGoodsAttrDetails.getGoodsId());
            map.put("属性参数 json", wooshopStoreGoodsAttrDetails.getAttrText());
            map.put("活动类型 0普通商品 1秒杀 2砍价 3拼团", wooshopStoreGoodsAttrDetails.getActivityType());
            map.put("逻辑删除 1删除", wooshopStoreGoodsAttrDetails.getIsDel());
            map.put("创建时间", wooshopStoreGoodsAttrDetails.getCreateTime());
            map.put("更新时间", wooshopStoreGoodsAttrDetails.getUpdateTime());
            map.put("规格原始数据", wooshopStoreGoodsAttrDetails.getSpecorig());
            map.put("规格参数", wooshopStoreGoodsAttrDetails.getSprcificationparlams());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    /**
     * 物理删除
     * @param goodsId 商品id
     * @param activityType 活动类型
     */
    @Override
    @CacheEvict(key = "'id:'+#activityType+'-'+#goodsId",allEntries = true)
    public void delByGoodsAndType(Long goodsId, Integer activityType) {
        baseMapper.delByGoodsAndType(goodsId,activityType);
    }

}

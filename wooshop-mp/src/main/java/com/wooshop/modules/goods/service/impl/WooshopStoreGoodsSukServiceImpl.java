


package com.wooshop.modules.goods.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
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
import com.wooshop.modules.goods.domain.WooshopStoreGoodsSuk;
import org.springframework.stereotype.Service;
import com.wooshop.modules.goods.service.dto.WooshopStoreGoodsSukQueryCriteria;
import com.wooshop.modules.goods.service.mapper.WooshopStoreGoodsSukMapper;
import com.wooshop.modules.goods.service.WooshopStoreGoodsSukService;
import com.wooshop.modules.goods.service.dto.WooshopStoreGoodsSukDto;


/**
* @author woo
* @date 2021-12-01
* 注意：
* 本软件为www.wooshopxingyun.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
@CacheConfig(cacheNames = "suk")
public class WooshopStoreGoodsSukServiceImpl extends BaseServiceImpl<WooshopStoreGoodsSukMapper, WooshopStoreGoodsSuk> implements WooshopStoreGoodsSukService {

    private final IGenerator generator;

    @Override
    public PageResult<WooshopStoreGoodsSukDto> queryAll(WooshopStoreGoodsSukQueryCriteria criteria, Pageable pageable) {
        setPageAndOrder(pageable.getPageNumber(), pageable.getPageSize(),pageable.getSort().toString());
        PageInfo<WooshopStoreGoodsSuk> page = new PageInfo<>(queryAll(criteria));
        return generator.convertPageInfo(page,WooshopStoreGoodsSukDto.class);
    }


    @Override
    @Cacheable(key = "'id:'+#criteria.activityType+'-'+#criteria.goodsId",condition ="#criteria.activityType!=null&&#criteria.goodsId!=null&&#criteria.id==null" )
    public List<WooshopStoreGoodsSuk> queryAll(WooshopStoreGoodsSukQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(WooshopStoreGoodsSuk.class, criteria));
    }


    @Override
    public void download(List<WooshopStoreGoodsSukDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WooshopStoreGoodsSukDto wooshopStoreGoodsSuk : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("属性对应", wooshopStoreGoodsSuk.getSpecificationValue());
            map.put("商品id", wooshopStoreGoodsSuk.getGoodsId());
            map.put("商品属性索引值", wooshopStoreGoodsSuk.getSuk());
            map.put("库存", wooshopStoreGoodsSuk.getQuantity());
            map.put("销售量", wooshopStoreGoodsSuk.getSales());
            map.put("金额", wooshopStoreGoodsSuk.getPrice());
            map.put("规格图片", wooshopStoreGoodsSuk.getSukImg());
            map.put("成本", wooshopStoreGoodsSuk.getCost());
            map.put("编码", wooshopStoreGoodsSuk.getGoodsCode());
            map.put("原价", wooshopStoreGoodsSuk.getOriginalPrice());
            map.put("商品重量", wooshopStoreGoodsSuk.getWeight());
            map.put("商品体积", wooshopStoreGoodsSuk.getVolume());
            map.put("一级分销佣金", wooshopStoreGoodsSuk.getDistribution());
            map.put("二级分销佣金", wooshopStoreGoodsSuk.getDistributionSecond());
            map.put("活动类型 0普通商品 1秒杀 2砍价 3拼团", wooshopStoreGoodsSuk.getActivityType());
            map.put("活动限购数量", wooshopStoreGoodsSuk.getRestrictions());
            map.put("显示限购的数量", wooshopStoreGoodsSuk.getRestrictionsShow());
            map.put("逻辑删除 1删除", wooshopStoreGoodsSuk.getIsDel());
            map.put("创建时间", wooshopStoreGoodsSuk.getCreateTime());
            map.put("更新时间", wooshopStoreGoodsSuk.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    /**
     *  物理删除
     * @param goodsId 商品id
     * @param activityType 活动类型 0普通商品 1秒杀 2砍价 3拼团
     * @return
     */
    @Override
    @CacheEvict(key = "'id:'+#activityType+'-'+#goodsId",allEntries = true)
//    @CacheRemove(value = CacheKey.WOOSHOP_SUK_ID,key = {"#activityType+'-' +#goodsId"})
    public void delByGoodsAndType(Long goodsId, Integer activityType) {
         baseMapper.delByGoodsAndType(goodsId,activityType);
    }

}

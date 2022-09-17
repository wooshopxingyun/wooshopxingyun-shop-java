


package com.wooshop.modules.relation.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.github.pagehelper.PageInfo;
import com.wooshop.common.QueryHelpPlus;
import com.wooshop.modules.goods.service.WooshopStoreGoodsService;
import com.wooshop.utils.CacheKey;
import com.wooshop.utils.FileUtil;
import com.wooshop.utils.enums.MenuType;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
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
import com.wooshop.utils.RedisUtils;
import com.wooshop.modules.relation.domain.WooshopGoodsRelation;
import org.springframework.stereotype.Service;
import com.wooshop.modules.relation.service.dto.WooshopGoodsRelationQueryCriteria;
import com.wooshop.modules.relation.service.mapper.WooshopGoodsRelationMapper;
import com.wooshop.modules.relation.service.WooshopGoodsRelationService;
import com.wooshop.modules.relation.service.dto.WooshopGoodsRelationDto;


/**
* @author woo
* @date 2022-01-11
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class WooshopGoodsRelationServiceImpl extends BaseServiceImpl<WooshopGoodsRelationMapper, WooshopGoodsRelation> implements WooshopGoodsRelationService {

    private final IGenerator generator;
    private final RedisUtils redisUtils;
    private final WooshopStoreGoodsService storeGoodsService;


    @Override
    public PageResult<WooshopGoodsRelationDto> queryAll(WooshopGoodsRelationQueryCriteria criteria, Pageable pageable) {
        setPageAndOrder(pageable.getPageNumber(), pageable.getPageSize(),pageable.getSort().toString());
        PageInfo<WooshopGoodsRelation> page = new PageInfo<>(queryAll(criteria));
        return generator.convertPageInfo(page,WooshopGoodsRelationDto.class);
    }


    @Override
    public List<WooshopGoodsRelation> queryAll(WooshopGoodsRelationQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(WooshopGoodsRelation.class, criteria));
    }


    @Override
    public void download(List<WooshopGoodsRelationDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WooshopGoodsRelationDto wooshopGoodsRelation : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("用户id", wooshopGoodsRelation.getUid());
            map.put("商品id", wooshopGoodsRelation.getGoodsId());
            map.put("收藏类型:1商品收藏、2推荐商品、3点赞", wooshopGoodsRelation.getRelationType());
            map.put("商品类型:1普通商品、2限时抢购商品、3团购商品、4拼团商品", wooshopGoodsRelation.getGoodsType());
            map.put("创建时间", wooshopGoodsRelation.getCreateTime());
            map.put("更新时间", wooshopGoodsRelation.getUpdateTime());
            map.put("逻辑删除:1表示删除", wooshopGoodsRelation.getIsDel());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
//    @CachePut(cacheNames=CacheKey.WOOSHOP_RELATION_QUERY,key = "#p0.uid+#p0.goodsId+#p0.relationType+#p0.goodsType")
    //@CacheRemove(value = CacheKey.WOOSHOP_STORES_QUERY, key = "*")
    public PageResult<WooshopGoodsRelationDto> addAndUpdate(WooshopGoodsRelation resources) {
        /**if (resources.getId()==null){
        save(resources);
        }else {
        updateById(resources);
        }**/
        saveOrUpdate(resources);
        List<WooshopGoodsRelation> resList=new ArrayList<>();
        resList.add(resources);
        PageInfo<WooshopGoodsRelation> page = new PageInfo<>(resList);
        return generator.convertPageInfo(page, WooshopGoodsRelationDto.class);
    }


    @Override
    //@CacheEvict(cacheNames=CacheKey.WOOSHOP_STORES_ID,key = "#p0.id")
    //@CacheRemove(value = CacheKey.WOOSHOP_STORES_QUERY, key = "*")
    public void cacheRemoveById(Long id) {
        removeById(id);
    }

}




package com.wooshop.modules.goodsEvaluation.service.impl;

import cn.hutool.json.JSONArray;
import com.github.pagehelper.PageInfo;
import com.wooshop.aspect.CacheRemove;
import com.wooshop.common.QueryHelpPlus;
import com.wooshop.modules.user.WooUserService;
import com.wooshop.modules.user.domain.WooUser;
import com.wooshop.utils.CacheKey;
import com.wooshop.utils.FileUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
import com.wooshop.modules.goodsEvaluation.domain.WooshopGoodsEvaluation;
import org.springframework.stereotype.Service;
import com.wooshop.modules.goodsEvaluation.service.dto.WooshopGoodsEvaluationQueryCriteria;
import com.wooshop.modules.goodsEvaluation.service.mapper.WooshopGoodsEvaluationMapper;
import com.wooshop.modules.goodsEvaluation.service.WooshopGoodsEvaluationService;
import com.wooshop.modules.goodsEvaluation.service.dto.WooshopGoodsEvaluationDto;


/**
* @author woo
* @date 2022-01-17
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class WooshopGoodsEvaluationServiceImpl extends BaseServiceImpl<WooshopGoodsEvaluationMapper, WooshopGoodsEvaluation> implements WooshopGoodsEvaluationService {

    private final IGenerator generator;
    private final RedisUtils redisUtils;
    private final WooUserService userService;

    @Override
    @Caching(cacheable= {@Cacheable(cacheNames = CacheKey.WOOSHOP_GOODSEVALUATION_QUERY,
            key = "#criteria.uid+'-'+#criteria.orderId+'-'+#criteria.goodsId+'-'+#criteria.goodsType+'-'+#criteria.sellerId+'-'+#pageable.pageNumber+'-'+#pageable.pageSize+'-'+#pageable.sort.toString().replace(':','')")
    })
    public PageResult<WooshopGoodsEvaluationDto> queryAll(WooshopGoodsEvaluationQueryCriteria criteria, Pageable pageable) {
        setPageAndOrder(pageable.getPageNumber(), pageable.getPageSize(),pageable.getSort().toString());
        PageInfo<WooshopGoodsEvaluation> page = new PageInfo<>(queryAll(criteria));
        PageResult<WooshopGoodsEvaluationDto> wooshopGoodsEvaluationDtoPageResult = generator.convertPageInfo(page, WooshopGoodsEvaluationDto.class);
        wooshopGoodsEvaluationDtoPageResult.getContent().forEach(item->{
            WooUser wooUser = userService.byUserId(item.getUid());
            item.setAvatar(wooUser.getAvatarName());
            item.setNickname(wooUser.getNickName());
            JSONArray jsonArray=new JSONArray(item.getEvaluationImg());
                    item.setImgPath(jsonArray.toList(String.class));
        });
        return wooshopGoodsEvaluationDtoPageResult;
    }


    @Override
    public List<WooshopGoodsEvaluation> queryAll(WooshopGoodsEvaluationQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(WooshopGoodsEvaluation.class, criteria));
    }


    @Override
    public void download(List<WooshopGoodsEvaluationDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WooshopGoodsEvaluationDto wooshopGoodsEvaluation : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("用户ID", wooshopGoodsEvaluation.getUid());
            map.put("订单ID", wooshopGoodsEvaluation.getOrderId());
            map.put("商品规格唯一id", wooshopGoodsEvaluation.getGoodsAttrUnique());
            map.put("商品id", wooshopGoodsEvaluation.getGoodsId());
            map.put("商品类型(1普通商品、2秒杀商品、3团购商品、4砍价商品）", wooshopGoodsEvaluation.getGoodsType());
            map.put("商品分数", wooshopGoodsEvaluation.getGoodsScore());
            map.put("服务分数", wooshopGoodsEvaluation.getServiceScore());
            map.put("评论内容", wooshopGoodsEvaluation.getComment());
            map.put("评论图片", wooshopGoodsEvaluation.getEvaluationImg());
            map.put("管理员回复内容", wooshopGoodsEvaluation.getAdminReplyContent());
            map.put("管理员回复时间", wooshopGoodsEvaluation.getAdminReplyTime());
            map.put("逻辑删除:0未删除1已删除", wooshopGoodsEvaluation.getIsDel());
            map.put("0未回复1已回复", wooshopGoodsEvaluation.getIsReply());
            map.put("用户名称", wooshopGoodsEvaluation.getNickname());
            map.put("用户头像", wooshopGoodsEvaluation.getAvatar());
            map.put("创建时间", wooshopGoodsEvaluation.getCreateTime());
            map.put("更新时间", wooshopGoodsEvaluation.getUpdateTime());
            map.put("商品规格属性值,多个,号隔开", wooshopGoodsEvaluation.getGoodsSku());
            map.put("店铺id", wooshopGoodsEvaluation.getSellerId());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    //@CachePut(cacheNames=CacheKey.WOOSHOP_STORES_ID,key = "#p0.id")
    @CacheRemove(value = CacheKey.WOOSHOP_GOODSEVALUATION, key = "*")
    public PageResult<WooshopGoodsEvaluationDto> addAndUpdate(WooshopGoodsEvaluation resources) {
        /**if (resources.getId()==null){
        save(resources);
        }else {
        updateById(resources);
        }**/
        saveOrUpdate(resources);
        List<WooshopGoodsEvaluation> resList=new ArrayList<>();
        resList.add(resources);
        PageInfo<WooshopGoodsEvaluation> page = new PageInfo<>(resList);
        return generator.convertPageInfo(page, WooshopGoodsEvaluationDto.class);
    }

    @Override
    //@CacheEvict(cacheNames=CacheKey.WOOSHOP_STORES_ID,key = "#p0.id")
    //@CacheRemove(value = CacheKey.WOOSHOP_STORES_QUERY, key = "*")
    public void cacheRemoveById(Long id) {
        removeById(id);
    }
}




package com.wooshop.modules.goods.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.github.pagehelper.PageInfo;
import com.wooshop.aspect.CacheRemove;
import com.wooshop.common.QueryHelpPlus;
import com.wooshop.modules.goods.domain.WooshopStoreGoodsAttrDetails;
import com.wooshop.modules.goods.domain.WooshopStoreGoodsSuk;
import com.wooshop.modules.goods.service.WooshopStoreGoodsAttrDetailsService;
import com.wooshop.modules.goods.service.WooshopStoreGoodsSukService;
import com.wooshop.modules.goods.service.dto.*;
import com.wooshop.utils.CacheKey;
import com.wooshop.utils.FileUtil;
import com.wooshop.utils.enums.MenuType;
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
import com.wooshop.modules.goods.domain.WooshopStoreGoods;
import org.springframework.stereotype.Service;
import com.wooshop.modules.goods.service.mapper.WooshopStoreGoodsMapper;
import com.wooshop.modules.goods.service.WooshopStoreGoodsService;


/**
* @author woo
* @date 2021-11-30
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class WooshopStoreGoodsServiceImpl extends BaseServiceImpl<WooshopStoreGoodsMapper, WooshopStoreGoods> implements WooshopStoreGoodsService {

    private final IGenerator generator;
    private final WooshopStoreGoodsSukService wooshopStoreGoodsSukService;
    private final WooshopStoreGoodsAttrDetailsService goodsAttrDetailsService;

    @Override
    @Caching(cacheable = {@Cacheable(cacheNames=CacheKey.WOOSHOP_GOODS_QUERY,
            key = "#criteria.id+'-'+#criteria.goodsCode+'-'+#criteria.isStart+'-'+#criteria.isNew+'-'+#criteria.isDel+'-'+#criteria.quantity+'-'+#criteria.goodsName+'-'+#criteria.categoryId+'-'+#criteria.goodStart+'-'+#pageable.pageNumber+'-'+#pageable.pageSize+'-'+#pageable.sort.toString().replace(':','')"
            /*,condition ="#criteria.goodsName==null||#criteria.goodsName==''" */)
            /*, @Cacheable(cacheNames=CacheKey.WOOSHOP_STORES_ID,key = "#p0.id", condition = "#criteria.id!=null&&#criteria.telephone==null&&#criteria.storesName==null&&#criteria.isStart==null")*/})
    public PageResult<WooshopStoreGoodsDto> queryAll(WooshopStoreGoodsQueryCriteria criteria, Pageable pageable) {
        setPageAndOrder(pageable.getPageNumber(), pageable.getPageSize(),pageable.getSort().toString());
        PageInfo<WooshopStoreGoods> page = new PageInfo<>(queryAll(criteria));
        PageResult<WooshopStoreGoodsDto> wooshopStoreGoodsDtoPageResult = generator.convertPageInfo(page, WooshopStoreGoodsDto.class);
        wooshopStoreGoodsDtoPageResult.getContent().forEach(item->{
            //查询商品规格属性
            WooshopStoreGoodsSukQueryCriteria goodsSukQueryCriteria=new WooshopStoreGoodsSukQueryCriteria();
            goodsSukQueryCriteria.setGoodsId(item.getId());
            goodsSukQueryCriteria.setActivityType(0);
            List<WooshopStoreGoodsSuk> wooshopStoreGoodsSuks = wooshopStoreGoodsSukService.queryAll(goodsSukQueryCriteria);
            List<WooshopStoreGoodsSukDto> goodsSukDtos1 = generator.convert(wooshopStoreGoodsSuks, WooshopStoreGoodsSukDto.class);
            item.setSpecTypeListData(goodsSukDtos1);
            //查询商品属性详情表
            WooshopStoreGoodsAttrDetailsQueryCriteria goodsAttrDetailsQueryCriteria=new WooshopStoreGoodsAttrDetailsQueryCriteria();
            goodsAttrDetailsQueryCriteria.setGoodsId(item.getId());
            goodsAttrDetailsQueryCriteria.setActivityType(0);
            List<WooshopStoreGoodsAttrDetails> goodsAttrDetailsList = goodsAttrDetailsService.queryAll(goodsAttrDetailsQueryCriteria);
            if (goodsAttrDetailsList.size()>0){
                item.setAttrDetailsId(goodsAttrDetailsList.get(0).getId());
                item.setSpecTypeListDataOrig(goodsAttrDetailsList.get(0).getSprcificationparlams());//商品规格属性
                item.setSprcificationParlams(goodsAttrDetailsList.get(0).getSpecorig());//规格
            }

        });
        return wooshopStoreGoodsDtoPageResult;
    }


    @Override
    public List<WooshopStoreGoods> queryAll(WooshopStoreGoodsQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(WooshopStoreGoods.class, criteria));
    }


    @Override
    public void download(List<WooshopStoreGoodsDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WooshopStoreGoodsDto wooshopStoreGoods : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("商品介绍", wooshopStoreGoods.getGoodsInfo());
            map.put("商品名称", wooshopStoreGoods.getGoodsName());
            map.put("商品主图片", wooshopStoreGoods.getCoverImage());
            map.put("seo关键字", wooshopStoreGoods.getMetaKeywords());
            map.put("商品条码（一维码）", wooshopStoreGoods.getBarCode());
            map.put("商品分类id", wooshopStoreGoods.getCategoryId());
            map.put("商品价格", wooshopStoreGoods.getPrice());
            map.put("市场价格", wooshopStoreGoods.getMktprice());
            map.put("谁承担运费0：买家承担，1：卖家承担", wooshopStoreGoods.getGoodsTransfeeCharge());
            map.put("计量单位", wooshopStoreGoods.getGoodsUnit());
            map.put("购买数量", wooshopStoreGoods.getBuyCount());
            map.put("总的库存数量", wooshopStoreGoods.getQuantity());
            map.put("是否优惠  1是", wooshopStoreGoods.getIsBenefit());
            map.put("是否热卖 1热卖", wooshopStoreGoods.getIsHot());
            map.put("是否精品 1是", wooshopStoreGoods.getIsBest());
            map.put("是否新品 1是", wooshopStoreGoods.getIsNew());
            map.put("购买商品获得积分", wooshopStoreGoods.getGiveIntegral());
            map.put("积分抵扣状态:0不开启、1开启抵扣", wooshopStoreGoods.getIsIntegral());
            map.put("成本价格", wooshopStoreGoods.getCost());
            map.put("秒杀状态 0未开启 1已开启", wooshopStoreGoods.getSeckillStart());
            map.put("砍价状态 0未开启 1开启", wooshopStoreGoods.getBargainStart());
            map.put("是否优质商品推荐 0否 1是", wooshopStoreGoods.getGoodStart());
            map.put("是否单独分销佣金 1独立 0非独立 默认0", wooshopStoreGoods.getDistributionStart());
            map.put("虚拟销量", wooshopStoreGoods.getFictitiousVolume());
            map.put("浏览数量", wooshopStoreGoods.getViewCount());
            map.put("商品二维码地址(用户小程序海报)", wooshopStoreGoods.getCodePath());
            map.put("运费模板id", wooshopStoreGoods.getTemplateId());
            map.put("规格类型 0单规格 1多规格 默认0", wooshopStoreGoods.getSpecType());
            map.put("是否是自营商品 0 不是 1是", wooshopStoreGoods.getSelfOperated());
            map.put("0 需要审核 并且待审核，1 不需要审核 2需要审核 且审核通过 3 需要审核 且审核未通过", wooshopStoreGoods.getIsAuth());
            map.put("审核信息", wooshopStoreGoods.getAuthMessage());
            map.put("下架原因", wooshopStoreGoods.getUnderMessage());
            map.put("评论数量", wooshopStoreGoods.getCommentNum());
            map.put("商品好评率", wooshopStoreGoods.getGrade());
            map.put("服务承诺", wooshopStoreGoods.getPromiseId());
            map.put("店铺id", wooshopStoreGoods.getSellerId());
            map.put("创建人", wooshopStoreGoods.getUid());
            map.put("逻辑删除 1表示删除", wooshopStoreGoods.getIsDel());
            map.put("创建时间", wooshopStoreGoods.getCreateTime());
            map.put("更新时间", wooshopStoreGoods.getUpdateTime());
            map.put("商品排序", wooshopStoreGoods.getSort());
            map.put("商品编码", wooshopStoreGoods.getGoodsCode());
            map.put("状态（0：未上架，1：上架）", wooshopStoreGoods.getIsStart());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    /**
     * 物理删除
     * @param goods
     */
    @Override
    public void delByGoodsId(WooshopStoreGoods goods,Integer activityType) {
        baseMapper.deleteById(goods.getId());
        wooshopStoreGoodsSukService.delByGoodsAndType(goods.getId(), activityType);
    }

    @Override
    @Caching(cacheable = {@Cacheable(cacheNames=CacheKey.WOOSHOP_GOODS_ID,
            key = "#p0",condition ="#id!=null" )})
    public WooshopStoreGoodsDto qeuryByIdInfo(Long id) {
        WooshopStoreGoodsDto convert = generator.convert(baseMapper.selectById(id), WooshopStoreGoodsDto.class);
        //查询商品规格属性
        WooshopStoreGoodsSukQueryCriteria goodsSukQueryCriteria=new WooshopStoreGoodsSukQueryCriteria();
        goodsSukQueryCriteria.setGoodsId(convert.getId());
        goodsSukQueryCriteria.setActivityType(0);
        List<WooshopStoreGoodsSuk> wooshopStoreGoodsSuks = wooshopStoreGoodsSukService.queryAll(goodsSukQueryCriteria);
        List<WooshopStoreGoodsSukDto> goodsSukDtos1 = generator.convert(wooshopStoreGoodsSuks, WooshopStoreGoodsSukDto.class);
        convert.setSpecTypeListData(goodsSukDtos1);
        //查询商品属性详情表
        WooshopStoreGoodsAttrDetailsQueryCriteria goodsAttrDetailsQueryCriteria=new WooshopStoreGoodsAttrDetailsQueryCriteria();
        goodsAttrDetailsQueryCriteria.setGoodsId(convert.getId());
        goodsAttrDetailsQueryCriteria.setActivityType(0);
        List<WooshopStoreGoodsAttrDetails> goodsAttrDetailsList = goodsAttrDetailsService.queryAll(goodsAttrDetailsQueryCriteria);
        if (goodsAttrDetailsList.size()>0){
            convert.setAttrDetailsId(goodsAttrDetailsList.get(0).getId());
            convert.setSpecTypeListDataOrig(goodsAttrDetailsList.get(0).getSprcificationparlams());//商品规格属性
            convert.setSprcificationParlams(goodsAttrDetailsList.get(0).getSpecorig());//规格
        }
        return convert;
    }

}

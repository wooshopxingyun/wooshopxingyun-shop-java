


package com.wooshop.modules.sys_config.service.impl;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.github.pagehelper.PageInfo;

import com.wooshop.aspect.CacheRemove;
import com.wooshop.constant.SysConfigEnum;
import com.wooshop.modules.sys_config.vo.SysConfigHomeVo;
import com.wooshop.utils.CacheKey;
import com.wooshop.utils.FileUtil;
import com.wooshop.utils.RedisUtils;
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
import com.wooshop.modules.sys_config.domain.WooSysConfig;
import org.springframework.stereotype.Service;
import com.wooshop.modules.sys_config.service.dto.WooSysConfigQueryCriteria;
import com.wooshop.modules.sys_config.service.mapper.WooSysConfigMapper;
import com.wooshop.modules.sys_config.service.WooSysConfigService;
import com.wooshop.modules.sys_config.service.dto.WooSysConfigDto;


/**
* @author woo
* @date 2021-11-09
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
//@CacheConfig(cacheNames = "sysConfig")
public class WooSysConfigServiceImpl extends BaseServiceImpl<WooSysConfigMapper, WooSysConfig> implements WooSysConfigService {

    private final IGenerator generator;
    private final RedisUtils redisUtils;
//    private final RedisUache redisUache;
//    private final WooSysConfigService sysConfigService;

    @Override
//    @Cacheable(key = "'menuName:' + #p0.menuName")
    @Caching(cacheable= {@Cacheable(cacheNames = CacheKey.SYSCONFIG_MENU_NAME,key = "#criteria.menuName+'-'+#criteria.enabled",condition="#criteria.menuName!=null&&#criteria.id==null"),
            @Cacheable(cacheNames = CacheKey.SYSCONFIG_MENU_QUERY,
                    key = "#criteria.menuName+'-'+#criteria.enabled+'-'+#criteria.id+'-'+#pageable.pageNumber+'-'+ #pageable.pageSize+'-'+ #pageable.sort.toString().replace(':','')")})
    public PageResult<WooSysConfigDto> queryAll(WooSysConfigQueryCriteria criteria, Pageable pageable) {
        setPageAndOrder(pageable.getPageNumber(), pageable.getPageSize(),pageable.getSort().toString());
        PageInfo<WooSysConfig> page = new PageInfo<>(queryAll(criteria));
        return generator.convertPageInfo(page, WooSysConfigDto.class);
    }


    @Override
    public List<WooSysConfig> queryAll(WooSysConfigQueryCriteria criteria){
        QueryWrapper<WooSysConfig> queryWrapper=new QueryWrapper<>();
        if (criteria.getEnabled()!=null){
            queryWrapper.eq("enabled",criteria.getEnabled());
        }
        if (criteria.getId()!=null){
            queryWrapper.eq("id",criteria.getId());
        }
        if (criteria.getMenuName()!=null){
            queryWrapper.eq("menu_name",criteria.getMenuName());
        }
        queryWrapper.eq("is_del",0);
        return baseMapper.selectListWRAPPER(queryWrapper);
    }


    @Override
    public void download(List<WooSysConfigDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WooSysConfigDto wooSysConfig : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("名称", wooSysConfig.getMenuName());
            map.put("值", wooSysConfig.getValue());
            map.put("状态：1启用、0禁用", wooSysConfig.getEnabled());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }


    @Override
    public WooSysConfigDto queryByMenuName(String menName) {
        QueryWrapper<WooSysConfig> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("menu_name",menName);
        return generator.convert(baseMapper.selectList(queryWrapper).get(0),WooSysConfigDto.class);
    }

    @Override
//    @Caching(evict = {@CacheEvict(cacheNames = CacheKey.SYSCONFIG_MENU,allEntries = true)})
    @CacheRemove(value =CacheKey.SYSCONFIG_MENU,key = "*")
    public PageResult<WooSysConfigDto> addSaveOrUpdate(WooSysConfig resources) {
        JSONObject json = new JSONObject(resources.getValue());
        if (resources.getId()==null){
            //新增 id为空的情况下
            boolean save = save(resources);
            json.set("id",resources.getId());
            json.set("enabled",resources.getEnabled());
            json.set("sort",resources.getSort());
            resources.setValue(json.toString());
            boolean b = updateById(resources);
        }else {
            json.set("id",resources.getId());
            json.set("enabled",resources.getEnabled());
            json.set("sort",resources.getSort());
            resources.setValue(json.toString());
            updateById(resources);
        }
        List<WooSysConfig> resList=new ArrayList<>();
        resList.add(resources);
        PageInfo<WooSysConfig> page = new PageInfo<>(resList);
        return generator.convertPageInfo(page, WooSysConfigDto.class);
    }

//    @CacheRemove(value =CacheKey.SYSCONFIG_MENU,key = "*:*")
//    @Caching(evict = {@CacheEvict(cacheNames =CacheKey.SYSCONFIG_MENU_NAME,allEntries = true ),@CacheEvict(cacheNames =CacheKey.SYSCONFIG_MENU_QUERY,allEntries = true )})
    @CacheEvict(cacheNames =CacheKey.SYSCONFIG_MENU,allEntries = true )
    public void cacheRemoveById(Integer id) {
        removeById(id);
    }

    /**
     * 查找移动端页面装修数据
     * @return
     */
    @Override
    @Caching(cacheable= {@Cacheable(cacheNames = CacheKey.SYSCONFIG_MENU_UNIAPP_HOME)})
    public List<SysConfigHomeVo> getSysAppHome() {
        SysConfigHomeVo sysConfigHome=new SysConfigHomeVo();

        LambdaQueryChainWrapper<WooSysConfig> lamQuerChainWr=new LambdaQueryChainWrapper<>(baseMapper);
        //轮播图
        lamQuerChainWr.eq(WooSysConfig::getMenuName, SysConfigEnum.WOOSHOP_SYSCONFIG_HOME_SWIPERIMG.toString());
        WooSysConfig swiperImg = lamQuerChainWr.one();
        sysConfigHome.setSwiperImg(swiperImg);
        //主页菜单
        LambdaQueryChainWrapper<WooSysConfig> menulamQuerChainWr=new LambdaQueryChainWrapper<>(baseMapper);
        menulamQuerChainWr.eq(WooSysConfig::getMenuName, SysConfigEnum.WOOSHOP_SYSCONFIG_HOME_MENU.toString());
        WooSysConfig menu = menulamQuerChainWr.one();
        sysConfigHome.setMenu(menu);
        //优惠图
        LambdaQueryChainWrapper<WooSysConfig> discountslamQuerChainWr=new LambdaQueryChainWrapper<>(baseMapper);
        discountslamQuerChainWr.eq(WooSysConfig::getMenuName, SysConfigEnum.WOOSHOP_SYSCONFIG_HOME_DISCOUNTS.toString());
        WooSysConfig discounts = discountslamQuerChainWr.one();
        sysConfigHome.setDiscounts(discounts);
        //推荐商品
        LambdaQueryChainWrapper<WooSysConfig> putGoodslamQuerChainWr=new LambdaQueryChainWrapper<>(baseMapper);
        putGoodslamQuerChainWr.eq(WooSysConfig::getMenuName, SysConfigEnum.WOOSHOP_SYSCONFIG_HOME_PUTGOODS.toString());
        WooSysConfig putGoods = putGoodslamQuerChainWr.one();
        sysConfigHome.setPutGoods(putGoods);
        //广告横幅
        LambdaQueryChainWrapper<WooSysConfig> advBannerlamQuerChainWr=new LambdaQueryChainWrapper<>(baseMapper);
        advBannerlamQuerChainWr.eq(WooSysConfig::getMenuName, SysConfigEnum.WOOSHOP_SYSCONFIG_HOME_ADVBANNER.toString());
        WooSysConfig advBanner = advBannerlamQuerChainWr.one();
        sysConfigHome.setAdvBanner(advBanner);
        //秒杀活动
        LambdaQueryChainWrapper<WooSysConfig> seckilllamQuerChainWr=new LambdaQueryChainWrapper<>(baseMapper);
        seckilllamQuerChainWr.eq(WooSysConfig::getMenuName, SysConfigEnum.WOOSHOP_SYSCONFIG_HOME_SECKILL.toString());
        WooSysConfig seckill= seckilllamQuerChainWr.one();
        sysConfigHome.setSeckill(seckill);
        //拼团活动
        LambdaQueryChainWrapper<WooSysConfig> groupBuyinglamQuerChainWr=new LambdaQueryChainWrapper<>(baseMapper);
        groupBuyinglamQuerChainWr.eq(WooSysConfig::getMenuName, SysConfigEnum.WOOSHOP_SYSCONFIG_HOME_GROUPBUYING.toString());
        WooSysConfig groupBuying= groupBuyinglamQuerChainWr.one();
        sysConfigHome.setGroupBuying(groupBuying);
        //砍价活动
        LambdaQueryChainWrapper<WooSysConfig> bargainlamQuerChainWr=new LambdaQueryChainWrapper<>(baseMapper);
        bargainlamQuerChainWr.eq(WooSysConfig::getMenuName, SysConfigEnum.WOOSHOP_SYSCONFIG_HOME_BARGAIN.toString());
        WooSysConfig bargain= bargainlamQuerChainWr.one();
        sysConfigHome.setBargain(bargain);
        //商品排行榜
        LambdaQueryChainWrapper<WooSysConfig> topGoodslamQuerChainWr=new LambdaQueryChainWrapper<>(baseMapper);
        topGoodslamQuerChainWr.eq(WooSysConfig::getMenuName, SysConfigEnum.WOOSHOP_SYSCONFIG_HOME_TOPGOODS.toString());
        WooSysConfig topGoods= topGoodslamQuerChainWr.one();
        sysConfigHome.setTopGoods(topGoods);
        //推荐栏
        LambdaQueryChainWrapper<WooSysConfig> recomlamQuerChainWr=new LambdaQueryChainWrapper<>(baseMapper);
        recomlamQuerChainWr.eq(WooSysConfig::getMenuName, SysConfigEnum.WOOSHOP_SYSCONFIG_HOME_RECOM.toString());
        WooSysConfig recom= recomlamQuerChainWr.one();
        sysConfigHome.setRecom(recom);
        List<SysConfigHomeVo> list=new ArrayList<>();
        list.add(sysConfigHome);
        return list;
    }
}

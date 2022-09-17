


package com.wooshop.modules.user_visit_record.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.wooshop.common.QueryHelpPlus;
import com.wooshop.utils.FileUtil;
import com.wooshop.utils.WooshopDateUtil;
import com.wooshop.utils.enums.WooshopConstants;
import org.springframework.data.domain.Pageable;
import com.wooshop.modules.service.impl.BaseServiceImpl;
import lombok.AllArgsConstructor;
import java.io.IOException;

import org.springframework.scheduling.annotation.Async;
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
import com.wooshop.modules.user_visit_record.domain.WooshopUserVisitRecord;
import org.springframework.stereotype.Service;
import com.wooshop.modules.user_visit_record.service.dto.WooshopUserVisitRecordQueryCriteria;
import com.wooshop.modules.user_visit_record.service.mapper.WooshopUserVisitRecordMapper;
import com.wooshop.modules.user_visit_record.service.WooshopUserVisitRecordService;
import com.wooshop.modules.user_visit_record.service.dto.WooshopUserVisitRecordDto;


/**
* @author woo
* @date 2022-03-24
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class WooshopUserVisitRecordServiceImpl extends BaseServiceImpl<WooshopUserVisitRecordMapper, WooshopUserVisitRecord> implements WooshopUserVisitRecordService {

    private final IGenerator generator;
    private final RedisUtils redisUtils;

    @Override
    public PageResult<WooshopUserVisitRecordDto> queryAll(WooshopUserVisitRecordQueryCriteria criteria, Pageable pageable) {
        setPageAndOrder(pageable.getPageNumber(), pageable.getPageSize(),pageable.getSort().toString());
        PageInfo<WooshopUserVisitRecord> page = new PageInfo<>(queryAll(criteria));
        return generator.convertPageInfo(page,WooshopUserVisitRecordDto.class);
    }


    @Override
    public List<WooshopUserVisitRecord> queryAll(WooshopUserVisitRecordQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(WooshopUserVisitRecord.class, criteria));
    }


    @Override
    public void download(List<WooshopUserVisitRecordDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WooshopUserVisitRecordDto wooshopUserVisitRecord : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("创建时期", wooshopUserVisitRecord.getCreateTime());
            map.put("用户uid", wooshopUserVisitRecord.getUid());
            map.put("访问类型:1-首页，2-详情页，3-营销活动详情页，4-个人中心，5-购物车、", wooshopUserVisitRecord.getVisitType());
            map.put("更新时间", wooshopUserVisitRecord.getUpdateTime());
            map.put("设备类型", wooshopUserVisitRecord.getClienttype());
            map.put("0", wooshopUserVisitRecord.getIsDel());
            map.put("商品id", wooshopUserVisitRecord.getGoodsId());
            map.put("商品类型:0非商品、1普通商品、2砍价商品、3团购商品、4秒杀商品、5积分商品", wooshopUserVisitRecord.getGoodsType());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Async
    @Override
    //@CachePut(cacheNames=CacheKey.WOOSHOP_STORES_ID,key = "#p0.id")
    //@CacheRemove(value = CacheKey.WOOSHOP_STORES_QUERY, key = "*")
    public PageResult<WooshopUserVisitRecordDto> addAndUpdateAsync(WooshopUserVisitRecord resources) {
        /**if (resources.getId()==null){
        save(resources);
        }else {
        updateById(resources);
        }**/
        saveOrUpdate(resources);
        List<WooshopUserVisitRecord> resList=new ArrayList<>();
        resList.add(resources);
        PageInfo<WooshopUserVisitRecord> page = new PageInfo<>(resList);
        return generator.convertPageInfo(page, WooshopUserVisitRecordDto.class);
    }


    @Override
    //@CacheEvict(cacheNames=CacheKey.WOOSHOP_STORES_ID,key = "#p0.id")
    //@CacheRemove(value = CacheKey.WOOSHOP_STORES_QUERY, key = "*")
    public void cacheRemoveById(Long id) {
        removeById(id);
    }

    /**
     * 获取当天访问量
     * @return
     */
    @Override
    public Integer todayPageviews() {
        //获取当前时间
        String date = WooshopDateUtil.nowDate(WooshopConstants.DATE_FORMAT_START);
        QueryWrapper<WooshopUserVisitRecord> lqcw=new QueryWrapper<>();
        lqcw.select("distinct create_ip");
        lqcw.gt("create_time",date);
        return baseMapper.selectCount(lqcw);
    }
}

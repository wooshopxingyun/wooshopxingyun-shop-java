


package com.wooshop.modules.brokerage_record.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.github.pagehelper.PageInfo;

import com.wooshop.common.QueryHelpPlus;
import com.wooshop.utils.*;
import com.wooshop.utils.enums.MenuType;
import com.wooshop.utils.enums.WooshopConstants;
import org.springframework.data.domain.Pageable;
import com.wooshop.modules.service.impl.BaseServiceImpl;
import lombok.AllArgsConstructor;
import java.io.IOException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

import com.wooshop.domain.PageResult;
import javax.servlet.http.HttpServletResponse;

import com.wooshop.dozer.service.IGenerator;
import com.wooshop.modules.brokerage_record.domain.WooshopUserBrokerageRecord;
import org.springframework.stereotype.Service;
import com.wooshop.modules.brokerage_record.service.dto.WooshopUserBrokerageRecordQueryCriteria;
import com.wooshop.modules.brokerage_record.service.mapper.WooshopUserBrokerageRecordMapper;
import com.wooshop.modules.brokerage_record.service.WooshopUserBrokerageRecordService;
import com.wooshop.modules.brokerage_record.service.dto.WooshopUserBrokerageRecordDto;


/**
* @author woo
* @date 2022-04-22
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class WooshopUserBrokerageRecordServiceImpl extends BaseServiceImpl<WooshopUserBrokerageRecordMapper, WooshopUserBrokerageRecord> implements WooshopUserBrokerageRecordService {

    private final IGenerator generator;
    private final RedisUtils redisUtils;

    @Override
    public PageResult<WooshopUserBrokerageRecordDto> queryAll(WooshopUserBrokerageRecordQueryCriteria criteria, Pageable pageable) {
        setPageAndOrder(pageable.getPageNumber(), pageable.getPageSize(),pageable.getSort().toString());
        PageInfo<WooshopUserBrokerageRecord> page = new PageInfo<>(queryAll(criteria));
        return generator.convertPageInfo(page,WooshopUserBrokerageRecordDto.class);
    }


    @Override
    public List<WooshopUserBrokerageRecord> queryAll(WooshopUserBrokerageRecordQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(WooshopUserBrokerageRecord.class, criteria));
    }


    @Override
    public void download(List<WooshopUserBrokerageRecordDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WooshopUserBrokerageRecordDto wooshopUserBrokerageRecord : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("用户uid", wooshopUserBrokerageRecord.getUid());
            map.put("关联id（orderNo,提现id）", wooshopUserBrokerageRecord.getLinkId());
            map.put("关联类型（order,extract，yue）", wooshopUserBrokerageRecord.getLinkType());
            map.put("类型：1-增加，2-扣减（提现）", wooshopUserBrokerageRecord.getBroType());
            map.put("标题", wooshopUserBrokerageRecord.getBroTitle());
            map.put("金额", wooshopUserBrokerageRecord.getBroPrice());
            map.put("剩余", wooshopUserBrokerageRecord.getBroBalance());
            map.put("备注", wooshopUserBrokerageRecord.getBroMark());
            map.put("状态：1-订单创建，2-冻结期，3-完成，4-失效（订单退款），5-提现申请", wooshopUserBrokerageRecord.getIsStart());
            map.put("冻结期时间（天）", wooshopUserBrokerageRecord.getFrozenTime());
            map.put("解冻时间", wooshopUserBrokerageRecord.getThawTime());
            map.put("添加时间", wooshopUserBrokerageRecord.getCreateTime());
            map.put("更新时间", wooshopUserBrokerageRecord.getUpdateTime());
            map.put("分销等级", wooshopUserBrokerageRecord.getBrokerageLevel());
            map.put("逻辑删除:1表示删除、0默认", wooshopUserBrokerageRecord.getIsDel());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    //@CachePut(cacheNames=CacheKey.WOOSHOP_STORES_ID,key = "#p0.id")
    //@CacheRemove(value = CacheKey.WOOSHOP_STORES_QUERY, key = "*")
    public PageResult<WooshopUserBrokerageRecordDto> addAndUpdate(WooshopUserBrokerageRecord resources) {
        /**if (resources.getId()==null){
        save(resources);
        }else {
        updateById(resources);
        }**/
        saveOrUpdate(resources);
        List<WooshopUserBrokerageRecord> resList=new ArrayList<>();
        resList.add(resources);
        PageInfo<WooshopUserBrokerageRecord> page = new PageInfo<>(resList);
        return generator.convertPageInfo(page, WooshopUserBrokerageRecordDto.class);
    }

    @Override
    //@CacheEvict(cacheNames=CacheKey.WOOSHOP_STORES_ID,key = "#p0.id")
    //@CacheRemove(value = CacheKey.WOOSHOP_STORES_QUERY, key = "*")
    public void cacheRemoveById(Long id) {
        removeById(id);
    }




    /**
     * 通过用户id和关联linkId查询数据
     * @param uid 用户id
     * @param linkId 关联id
     * @return
     */
    @Override
    public List<WooshopUserBrokerageRecord> findByUidAndLinkId(Long uid, Long linkId) {
        LambdaQueryChainWrapper<WooshopUserBrokerageRecord> lqw=new LambdaQueryChainWrapper<>(baseMapper);
        lqw.eq(WooshopUserBrokerageRecord::getUid,uid).eq(WooshopUserBrokerageRecord::getLinkId,linkId);
        return lqw.list();
    }


    /**
     * 获取推广记录列表
     * @param uid 用户uid
     * @param pageable 分页参数
     * @return List
     */
    @Override
    public PageResult<WooshopUserBrokerageRecordDto> findBrokerageOrderListByUid(Long uid, Pageable pageable) {
        setPageAndOrder(pageable.getPageNumber(), pageable.getPageSize(),pageable.getSort().toString());
        LambdaQueryChainWrapper<WooshopUserBrokerageRecord> lqw = new LambdaQueryChainWrapper<>(baseMapper);
        lqw.eq(WooshopUserBrokerageRecord::getUid, uid).eq(WooshopUserBrokerageRecord::getIsDel,MenuType.IS_DEL_STATUS_0.getValue());
        lqw.eq(WooshopUserBrokerageRecord::getLinkType, WooshopConstants.USER_BROKERAGE_RECORD_LINK_TYPE_ORDER);
        lqw.eq(WooshopUserBrokerageRecord::getIsStart, MenuType.BROKERAGE_RECORD_IS_START_3.getValue());
        lqw.orderByDesc(WooshopUserBrokerageRecord::getUpdateTime);
        PageInfo<WooshopUserBrokerageRecord> page = new PageInfo<>(lqw.list());
        return generator.convertPageInfo(page,WooshopUserBrokerageRecordDto.class);
    }

    /**
     * 获取月份对应的推广订单数
     * @param uid 用户uid
     * @param monthList 月份列表
     * @return Map
     */
    @Override
    public Map<String, Long> getBrokerageCountByUidAndMonth(Long uid, List<String> monthList) {
        QueryWrapper<WooshopUserBrokerageRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("count(uid) as uid, update_time");
        queryWrapper.eq("uid", uid);
        queryWrapper.eq("link_type", WooshopConstants.USER_BROKERAGE_RECORD_LINK_TYPE_ORDER);
        queryWrapper.eq("is_start", MenuType.BROKERAGE_RECORD_IS_START_3.getValue());
        queryWrapper.apply(StrUtil.format("left(update_time, 7) in ({})", WooshopArrayUtil.sqlStrListToSqlJoin(monthList)));
        queryWrapper.groupBy("left(update_time, 7)");
        List<WooshopUserBrokerageRecord> list = baseMapper.selectList(queryWrapper);
        Map<String, Long> map = new HashMap<>();
        if (CollUtil.isEmpty(list)) {
            return map;
        }
        list.forEach(record -> {
            map.put(WooshopDateUtil.dateToStr(record.getUpdateTime(), WooshopConstants.DATE_FORMAT_MONTH), record.getUid());
        });
        return map;
    }
}

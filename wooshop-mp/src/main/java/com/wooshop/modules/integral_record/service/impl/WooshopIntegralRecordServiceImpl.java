


package com.wooshop.modules.integral_record.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.github.pagehelper.PageInfo;
import com.wooshop.common.QueryHelpPlus;
import com.wooshop.utils.WooshopDateUtil;
import com.wooshop.utils.FileUtil;
import com.wooshop.utils.enums.MenuType;
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

import com.wooshop.modules.integral_record.domain.WooshopIntegralRecord;
import org.springframework.stereotype.Service;
import com.wooshop.modules.integral_record.service.dto.WooshopIntegralRecordQueryCriteria;
import com.wooshop.modules.integral_record.service.mapper.WooshopIntegralRecordMapper;
import com.wooshop.modules.integral_record.service.WooshopIntegralRecordService;
import com.wooshop.modules.integral_record.service.dto.WooshopIntegralRecordDto;


/**
 * @author woo
 * @date 2021-12-19
 * 注意：
 * 本软件为www.wooshop.com开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */


@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class WooshopIntegralRecordServiceImpl extends BaseServiceImpl<WooshopIntegralRecordMapper, WooshopIntegralRecord> implements WooshopIntegralRecordService {

    private final IGenerator generator;

    @Override
    public PageResult<WooshopIntegralRecordDto> queryAll(WooshopIntegralRecordQueryCriteria criteria, Pageable pageable) {
        setPageAndOrder(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort().toString());
        PageInfo<WooshopIntegralRecord> page = new PageInfo<>(queryAll(criteria));
        return generator.convertPageInfo(page, WooshopIntegralRecordDto.class);
    }


    @Override
    public List<WooshopIntegralRecord> queryAll(WooshopIntegralRecordQueryCriteria criteria) {
        return baseMapper.selectList(QueryHelpPlus.getPredicate(WooshopIntegralRecord.class, criteria));
    }


    @Override
    public void download(List<WooshopIntegralRecordDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WooshopIntegralRecordDto wooshopIntegralRecord : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("用户uid", wooshopIntegralRecord.getUid());
            map.put("关联订单id，2签到、3系统默认为0", wooshopIntegralRecord.getIntegralId());
            map.put("关联类型: 1订单积分、2签到积分、3系统添加", wooshopIntegralRecord.getIntegralType());
            map.put("积分类型：1-增加积分，2-扣减积分", wooshopIntegralRecord.getIntegralRecordType());
            map.put("积分名称", wooshopIntegralRecord.getIntegralTitle());
            map.put("积分", wooshopIntegralRecord.getIntegral());
            map.put("剩余积分", wooshopIntegralRecord.getBeforeIntegral());
            map.put("积分备注", wooshopIntegralRecord.getRemarks());
            map.put("积分状态：1-订单创建，2-冻结期，3-完成，4-失效（订单退款）", wooshopIntegralRecord.getIsState());
            map.put("积分冻结期时间（天）", wooshopIntegralRecord.getFreezeDate());
            map.put("积分解冻时间", wooshopIntegralRecord.getThawDate());
            map.put("添加时间", wooshopIntegralRecord.getCreateTime());
            map.put("更新时间", wooshopIntegralRecord.getUpdateTime());
            map.put("逻辑删除", wooshopIntegralRecord.getIsDel());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }


    @Override
    //@CachePut(cacheNames=CacheKey.WOOSHOP_STORES_ID,key = "#p0.id")
    //@CacheRemove(value = CacheKey.WOOSHOP_STORES_QUERY, key = "*")
    public PageResult<WooshopIntegralRecordDto> addAndUpdate(WooshopIntegralRecord resources) {
        /**if (resources.getId()==null){
         save(resources);
         }else {
         updateById(resources);
         }**/
        saveOrUpdate(resources);
        List<WooshopIntegralRecord> resList = new ArrayList<>();
        resList.add(resources);
        PageInfo<WooshopIntegralRecord> page = new PageInfo<>(resList);
        return generator.convertPageInfo(page, WooshopIntegralRecordDto.class);
    }
}

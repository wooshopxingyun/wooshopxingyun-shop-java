


package com.wooshop.modules.experience_record.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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

import com.wooshop.utils.RedisUtils;
import com.wooshop.modules.experience_record.domain.WooshopUserExperienceRecord;
import org.springframework.stereotype.Service;
import com.wooshop.modules.experience_record.service.dto.WooshopUserExperienceRecordQueryCriteria;
import com.wooshop.modules.experience_record.service.mapper.WooshopUserExperienceRecordMapper;
import com.wooshop.modules.experience_record.service.WooshopUserExperienceRecordService;
import com.wooshop.modules.experience_record.service.dto.WooshopUserExperienceRecordDto;


/**
* @author woo
* @date 2022-03-02
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class WooshopUserExperienceRecordServiceImpl extends BaseServiceImpl<WooshopUserExperienceRecordMapper, WooshopUserExperienceRecord> implements WooshopUserExperienceRecordService {

    private final IGenerator generator;
    private final RedisUtils redisUtils;

    @Override
    public PageResult<WooshopUserExperienceRecordDto> queryAll(WooshopUserExperienceRecordQueryCriteria criteria, Pageable pageable) {
        setPageAndOrder(pageable.getPageNumber(), pageable.getPageSize(),pageable.getSort().toString());
        PageInfo<WooshopUserExperienceRecord> page = new PageInfo<>(queryAll(criteria));
        return generator.convertPageInfo(page,WooshopUserExperienceRecordDto.class);
    }


    @Override
    public List<WooshopUserExperienceRecord> queryAll(WooshopUserExperienceRecordQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(WooshopUserExperienceRecord.class, criteria));
    }


    @Override
    public void download(List<WooshopUserExperienceRecordDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WooshopUserExperienceRecordDto wooshopUserExperienceRecord : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("用户uid", wooshopUserExperienceRecord.getUid());
            map.put("关联id-orderNo,(sign,system默认为0）", wooshopUserExperienceRecord.getLinkId());
            map.put("关联类型（order,sign,system）", wooshopUserExperienceRecord.getLinkType());
            map.put("类型：1-增加，2-扣减", wooshopUserExperienceRecord.getRecordType());
            map.put("标题", wooshopUserExperienceRecord.getRecordTitle());
            map.put("经验", wooshopUserExperienceRecord.getExperience());
            map.put("剩余", wooshopUserExperienceRecord.getBalance());
            map.put("备注", wooshopUserExperienceRecord.getMark());
            map.put("状态：1-成功（保留字段）0-失败", wooshopUserExperienceRecord.getIsStatus());
            map.put("添加时间", wooshopUserExperienceRecord.getCreateTime());
            map.put("更新时间", wooshopUserExperienceRecord.getUpdateTime());
            map.put("逻辑删除: 1-删除", wooshopUserExperienceRecord.getIsDel());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    //@CachePut(cacheNames=CacheKey.WOOSHOP_STORES_ID,key = "#p0.id")
    //@CacheRemove(value = CacheKey.WOOSHOP_STORES_QUERY, key = "*")
    public PageResult<WooshopUserExperienceRecordDto> addAndUpdate(WooshopUserExperienceRecord resources) {
        /**if (resources.getId()==null){
        save(resources);
        }else {
        updateById(resources);
        }**/
        saveOrUpdate(resources);
        List<WooshopUserExperienceRecord> resList=new ArrayList<>();
        resList.add(resources);
        PageInfo<WooshopUserExperienceRecord> page = new PageInfo<>(resList);
        return generator.convertPageInfo(page, WooshopUserExperienceRecordDto.class);
    }

    @Override
    //@CacheEvict(cacheNames=CacheKey.WOOSHOP_STORES_ID,key = "#p0.id")
    //@CacheRemove(value = CacheKey.WOOSHOP_STORES_QUERY, key = "*")
    public void cacheRemoveById(Long id) {
        removeById(id);
    }

}

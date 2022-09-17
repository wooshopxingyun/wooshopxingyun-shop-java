


package com.wooshop.modules.user_level.service.impl;

import com.github.pagehelper.PageInfo;
import com.wooshop.common.QueryHelpPlus;
import com.wooshop.utils.FileUtil;
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
import com.wooshop.modules.user_level.domain.WooshopUserLevel;
import org.springframework.stereotype.Service;
import com.wooshop.modules.user_level.service.dto.WooshopUserLevelQueryCriteria;
import com.wooshop.modules.user_level.service.mapper.WooshopUserLevelMapper;
import com.wooshop.modules.user_level.service.WooshopUserLevelService;
import com.wooshop.modules.user_level.service.dto.WooshopUserLevelDto;


/**
* @author woo
* @date 2022-04-16
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class WooshopUserLevelServiceImpl extends BaseServiceImpl<WooshopUserLevelMapper, WooshopUserLevel> implements WooshopUserLevelService {

    private final IGenerator generator;
    private final RedisUtils redisUtils;

    @Override
    public PageResult<WooshopUserLevelDto> queryAll(WooshopUserLevelQueryCriteria criteria, Pageable pageable) {
        setPageAndOrder(pageable.getPageNumber(), pageable.getPageSize(),pageable.getSort().toString());
        PageInfo<WooshopUserLevel> page = new PageInfo<>(queryAll(criteria));
        return generator.convertPageInfo(page,WooshopUserLevelDto.class);
    }


    @Override
    public List<WooshopUserLevel> queryAll(WooshopUserLevelQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(WooshopUserLevel.class, criteria));
    }


    @Override
    public void download(List<WooshopUserLevelDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WooshopUserLevelDto wooshopUserLevel : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("用户uid", wooshopUserLevel.getUid());
            map.put("等级vip", wooshopUserLevel.getLevelId());
            map.put("会员等级", wooshopUserLevel.getGrade());
            map.put("0:禁止,1:正常", wooshopUserLevel.getIsStart());
            map.put("备注", wooshopUserLevel.getMark());
            map.put("是否已通知", wooshopUserLevel.getRemind());
            map.put("是否删除,0=未删除,1=删除", wooshopUserLevel.getIsDel());
            map.put("享受折扣", wooshopUserLevel.getDiscount());
            map.put("创建时间", wooshopUserLevel.getCreateTime());
            map.put("更新时间", wooshopUserLevel.getUpdateTime());
            map.put("过期时间", wooshopUserLevel.getExpiredTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    //@CachePut(cacheNames=CacheKey.WOOSHOP_STORES_ID,key = "#p0.id")
    //@CacheRemove(value = CacheKey.WOOSHOP_STORES_QUERY, key = "*")
    public PageResult<WooshopUserLevelDto> addAndUpdate(WooshopUserLevel resources) {
        /**if (resources.getId()==null){
        save(resources);
        }else {
        updateById(resources);
        }**/
        saveOrUpdate(resources);
        List<WooshopUserLevel> resList=new ArrayList<>();
        resList.add(resources);
        PageInfo<WooshopUserLevel> page = new PageInfo<>(resList);
        return generator.convertPageInfo(page, WooshopUserLevelDto.class);
    }

    @Override
    //@CacheEvict(cacheNames=CacheKey.WOOSHOP_STORES_ID,key = "#p0.id")
    //@CacheRemove(value = CacheKey.WOOSHOP_STORES_QUERY, key = "*")
    public void cacheRemoveById(Long id) {
        removeById(id);
    }




}

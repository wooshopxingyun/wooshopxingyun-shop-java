package com.wooshop.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import com.wooshop.base.PageInfo;
import com.wooshop.base.QueryHelpMybatisPlus;
import com.wooshop.base.impl.CommonServiceImpl;
import com.wooshop.modules.system.domain.Dict;
import com.wooshop.modules.system.domain.DictDetail;
import com.wooshop.modules.system.service.DictDetailService;
import com.wooshop.modules.system.service.dto.DictDetailDto;
import com.wooshop.modules.system.service.dto.DictDetailQueryParam;
import com.wooshop.modules.system.service.mapper.DictDetailMapper;
import com.wooshop.modules.system.service.mapper.DictMapper;
import com.wooshop.utils.CacheKey;
import com.wooshop.utils.ConvertUtil;
import com.wooshop.utils.PageUtil;
import com.wooshop.utils.RedisUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;

/**
* @author jinjin
* @date 2020-09-24
*/
@Service
@AllArgsConstructor
@CacheConfig(cacheNames = "dictDetail")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DictDetailServiceImpl extends CommonServiceImpl<DictDetailMapper, DictDetail> implements DictDetailService {

    private final DictDetailMapper dictDetailMapper;
    private final DictMapper dictMapper;
    private final RedisUtils redisUtils;

    @Override
    //@Cacheable
    public PageInfo<DictDetailDto> queryAll(DictDetailQueryParam query, Pageable pageable) {
        IPage<DictDetail> page = PageUtil.toMybatisPage(pageable);
        IPage<DictDetail> pageList = dictDetailMapper.selectPage(page, QueryHelpMybatisPlus.getPredicate(query));
        return ConvertUtil.convertPage(pageList, DictDetailDto.class);
    }

    @Override
    //@Cacheable
    public List<DictDetailDto> queryAll(DictDetailQueryParam query){
        return ConvertUtil.convertList(dictDetailMapper.selectList(QueryHelpMybatisPlus.getPredicate(query)), DictDetailDto.class);
    }

    @Override
    public List<DictDetailDto> getDictByName(String dictName) {
        Dict dict = dictMapper.lambdaQuery().eq(Dict::getName, dictName).one();
        List<DictDetailDto> ret = dictDetailMapper.getDictDetailsByDictName(dictName);
        redisUtils.set(CacheKey.DICTDEAIL_DICTID + dict.getId(), ret);
        return ret;
    }

    @Override
    public PageInfo<DictDetailDto> getDictByName(String dictName, Pageable pageable) {
        IPage<DictDetailDto> page = PageUtil.toMybatisPage(pageable, true);
        QueryWrapper<DictDetail> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("dictName",dictName);
        return ConvertUtil.convertPage(dictDetailMapper.getDictDetailsByDictName(page,dictName), DictDetailDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(DictDetailDto resources) {
        DictDetail detail = ConvertUtil.convert(resources, DictDetail.class);
        boolean ret = dictDetailMapper.updateById(detail) > 0;
        // 清理缓存
        delCaches(detail.getDictId());
        return ret;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(DictDetailDto resources){
        DictDetail detail = ConvertUtil.convert(resources, DictDetail.class);
        detail.setDictId(resources.getDict().getId());
        boolean ret = dictDetailMapper.insert(detail) > 0;
        // 清理缓存
        delCaches(detail.getDictId());
        return ret;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(Long id) {
        DictDetail dictDetail = dictDetailMapper.selectById(id);
        boolean ret = dictDetailMapper.deleteById(id) > 0;
        // 清理缓存
        delCaches(dictDetail.getDictId());
        return ret;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByDictId(Long id) {
        boolean ret = lambdaUpdate().eq(DictDetail::getDictId, id).remove();
        delCaches(id);
        return ret;
    }

    private void delCaches(Long dictId){
        redisUtils.del(CacheKey.DICTDEAIL_DICTID + dictId);
    }
}

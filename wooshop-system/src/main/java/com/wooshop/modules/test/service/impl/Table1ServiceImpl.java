package com.wooshop.modules.test.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import com.wooshop.base.PageInfo;
import com.wooshop.base.QueryHelpMybatisPlus;
import com.wooshop.modules.test.domain.Table1;
import com.wooshop.modules.test.service.Table1Service;
import com.wooshop.modules.test.service.dto.Table1Dto;
import com.wooshop.modules.test.service.dto.Table1QueryParam;
import com.wooshop.modules.test.service.mapper.Table1Mapper;
import com.wooshop.utils.ConvertUtil;
import com.wooshop.utils.PageUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;

/**
* @author jinjin
* @date 2020-10-01
*/
@Service
@AllArgsConstructor
// @CacheConfig(cacheNames = Table1Service.CACHE_KEY)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class Table1ServiceImpl implements Table1Service {

    // private final RedisUtils redisUtils;
    private final Table1Mapper table1Mapper;

    @Override
    public PageInfo<Table1Dto> queryAll(Table1QueryParam query, Pageable pageable) {
        IPage<Table1> queryPage = PageUtil.toMybatisPage(pageable);
        IPage<Table1> page = table1Mapper.selectPage(queryPage, QueryHelpMybatisPlus.getPredicate(query));
        return ConvertUtil.convertPage(page, Table1Dto.class);
    }

    @Override
    public List<Table1Dto> queryAll(Table1QueryParam query){
        return ConvertUtil.convertList(table1Mapper.selectList(QueryHelpMybatisPlus.getPredicate(query)), Table1Dto.class);
    }

    @Override
    public Table1 getById(Long id) {
        return table1Mapper.selectById(id);
    }

    @Override
    // @Cacheable(key = "'id:' + #p0")
    public Table1Dto findById(Long id) {
        return ConvertUtil.convert(getById(id), Table1Dto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(Table1Dto resources) {
        Table1 entity = ConvertUtil.convert(resources, Table1.class);
        return table1Mapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateById(Table1Dto resources){
        Table1 entity = ConvertUtil.convert(resources, Table1.class);
        int ret = table1Mapper.updateById(entity);
        // delCaches(resources.id);
        return ret;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeByIds(Set<Long> ids){
        // delCaches(ids);
        return table1Mapper.deleteBatchIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeById(Long id){
        Set<Long> set = new HashSet<>(1);
        set.add(id);
        return this.removeByIds(set);
    }

    /*
    private void delCaches(Long id) {
        redisUtils.delByKey(CACHE_KEY + "::id:", id);
    }

    private void delCaches(Set<Long> ids) {
        for (Long id: ids) {
            delCaches(id);
        }
    }*/

    /*
    @Override
    public void download(List<Table1Dto> all, HttpServletResponse response) throws IOException {
      List<Map<String, Object>> list = new ArrayList<>();
      for (Table1Dto table1 : all) {
        Map<String,Object> map = new LinkedHashMap<>();
              map.put("姓名", table1.getName());
              map.put("年龄", table1.getAge());
                map.put(" createTime",  table1.getCreateTime());
                map.put(" createBy",  table1.getCreateBy());
        list.add(map);
      }
      FileUtil.downloadExcel(list, response);
    }*/
}

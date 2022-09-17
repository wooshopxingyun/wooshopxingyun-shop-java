package com.wooshop.modules.mnt.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.wooshop.base.PageInfo;
import com.wooshop.base.QueryHelpMybatisPlus;
import com.wooshop.base.impl.CommonServiceImpl;
import com.wooshop.modules.mnt.domain.Database;
import com.wooshop.modules.mnt.mapper.DatabaseMapper;
import com.wooshop.modules.mnt.service.DatabaseService;
import com.wooshop.modules.mnt.service.dto.DatabaseDto;
import com.wooshop.modules.mnt.service.dto.DatabaseQueryParam;
import com.wooshop.modules.mnt.util.SqlUtils;
import com.wooshop.utils.ConvertUtil;
import com.wooshop.utils.FileUtil;
import com.wooshop.utils.PageUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;

/**
* @author jinjin
* @date 2020-09-27
*/
@Slf4j
@Service
@AllArgsConstructor
// @CacheConfig(cacheNames = DatabaseService.CACHE_KEY)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DatabaseServiceImpl extends CommonServiceImpl<DatabaseMapper, Database> implements DatabaseService {

    // private final RedisUtils redisUtils;
    private final DatabaseMapper databaseMapper;

    @Override
    public PageInfo<DatabaseDto> queryAll(DatabaseQueryParam query, Pageable pageable) {
        IPage<Database> page = PageUtil.toMybatisPage(pageable);
        IPage<Database> pageList = databaseMapper.selectPage(page, QueryHelpMybatisPlus.getPredicate(query));
        return ConvertUtil.convertPage(pageList, DatabaseDto.class);
    }

    @Override
    public List<DatabaseDto> queryAll(DatabaseQueryParam query){
        return ConvertUtil.convertList(databaseMapper.selectList(QueryHelpMybatisPlus.getPredicate(query)), DatabaseDto.class);
    }

    @Override
    public Database getById(String id) {
        return databaseMapper.selectById(id);
    }

    @Override
    // @Cacheable(key = "'id:' + #p0")
    public DatabaseDto findById(String id) {
        return ConvertUtil.convert(getById(id), DatabaseDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(Database resources) {
        return databaseMapper.insert(resources) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(Database resources){
        int ret = databaseMapper.updateById(resources);
        // delCaches(resources.id);
        return ret > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByIds(Set<String> ids){
        // delCaches(ids);
        return databaseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(String id){
        Set<String> set = new HashSet<>(1);
        set.add(id);
        return this.removeByIds(set);
    }

    /*
    private void delCaches(String id) {
        redisUtils.delByKey(CACHE_KEY + "::id:", id);
    }

    private void delCaches(Set<String> ids) {
        for (String id: ids) {
            delCaches(id);
        }
    }*/

    @Override
    public boolean testConnection(Database resources) {
        try {
            return SqlUtils.testConnection(resources.getJdbcUrl(), resources.getUserName(), resources.getPwd());
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public void download(List<DatabaseDto> all, HttpServletResponse response) throws IOException {
      List<Map<String, Object>> list = new ArrayList<>();
      for (DatabaseDto database : all) {
        Map<String,Object> map = new LinkedHashMap<>();
              map.put("名称", database.getName());
              map.put("jdbc连接", database.getJdbcUrl());
              map.put("账号", database.getUserName());
              map.put("密码", database.getPwd());
              map.put("创建者", database.getCreateBy());
              map.put("更新者", database.getUpdateBy());
              map.put("创建时间", database.getCreateTime());
              map.put("更新时间", database.getUpdateTime());
        list.add(map);
      }
      FileUtil.downloadExcel(list, response);
    }
}

package com.wooshop.modules.test.service;

import com.wooshop.base.PageInfo;
import com.wooshop.modules.test.domain.Table1;
import com.wooshop.modules.test.service.dto.Table1Dto;
import com.wooshop.modules.test.service.dto.Table1QueryParam;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

/**
* @author jinjin
* @date 2020-10-01
*/
public interface Table1Service {

    static final String CACHE_KEY = "table1";

    /**
    * 查询数据分页
    * @param query 条件
    * @param pageable 分页参数
    * @return PageInfo<Table1Dto>
    */
    PageInfo<Table1Dto> queryAll(Table1QueryParam query, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param query 条件参数
    * @return List<Table1Dto>
    */
    List<Table1Dto> queryAll(Table1QueryParam query);

    Table1 getById(Long id);
    Table1Dto findById(Long id);

    /**
     * 插入一条新数据。
     */
    int insert(Table1Dto resources);
    int updateById(Table1Dto resources);
    int removeById(Long id);
    int removeByIds(Set<Long> ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    // void download(List<Table1Dto> all, HttpServletResponse response) throws IOException;
}

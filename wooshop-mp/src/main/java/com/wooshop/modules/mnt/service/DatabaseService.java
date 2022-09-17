package com.wooshop.modules.mnt.service;

import com.wooshop.base.CommonService;
import com.wooshop.base.PageInfo;
import com.wooshop.modules.mnt.domain.Database;
import com.wooshop.modules.mnt.service.dto.DatabaseDto;
import com.wooshop.modules.mnt.service.dto.DatabaseQueryParam;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
* @author jinjin
* @date 2020-09-27
*/
public interface DatabaseService  extends CommonService<Database>{

    static final String CACHE_KEY = "database";

    /**
    * 查询数据分页
    * @param query 条件
    * @param pageable 分页参数
    * @return PageInfo<DatabaseDto>
    */
    PageInfo<DatabaseDto> queryAll(DatabaseQueryParam query, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param query 条件参数
    * @return List<DatabaseDto>
    */
    List<DatabaseDto> queryAll(DatabaseQueryParam query);

    Database getById(String id);
    DatabaseDto findById(String id);

    /**
     * 插入一条新数据。
     */
    boolean save(Database resources);
    boolean updateById(Database resources);
    boolean removeById(String id);
    boolean removeByIds(Set<String> ids);

    boolean testConnection(Database resources);
    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<DatabaseDto> all, HttpServletResponse response) throws IOException;
}

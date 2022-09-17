package com.wooshop.modules.mnt.service;

import com.wooshop.base.CommonService;
import com.wooshop.base.PageInfo;
import com.wooshop.modules.mnt.domain.App;
import com.wooshop.modules.mnt.service.dto.AppDto;
import com.wooshop.modules.mnt.service.dto.AppQueryParam;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
* @author jinjin
* @date 2020-09-27
*/
public interface AppService  extends CommonService<App>{

    static final String CACHE_KEY = "app";

    /**
    * 查询数据分页
    * @param query 条件
    * @param pageable 分页参数
    * @return PageInfo<AppDto>
    */
    PageInfo<AppDto> queryAll(AppQueryParam query, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param query 条件参数
    * @return List<AppDto>
    */
    List<AppDto> queryAll(AppQueryParam query);

    AppDto findById(Long id);

    /**
     * 插入一条新数据。
     */
    @Override
    boolean save(App resources);
    @Override
    boolean updateById(App resources);
    boolean removeById(Long id);
    boolean removeByIds(Set<Long> ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<AppDto> all, HttpServletResponse response) throws IOException;
}

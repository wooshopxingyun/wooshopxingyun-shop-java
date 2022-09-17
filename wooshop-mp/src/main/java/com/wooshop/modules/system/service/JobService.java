package com.wooshop.modules.system.service;

import com.wooshop.base.CommonService;
import com.wooshop.base.PageInfo;
import com.wooshop.modules.system.domain.Job;
import com.wooshop.modules.system.service.dto.JobDto;
import com.wooshop.modules.system.service.dto.JobQueryParam;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
* @author jinjin
* @date 2020-09-25
*/
public interface JobService  extends CommonService<Job>{

    PageInfo<JobDto> queryAll(JobQueryParam query, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param query 条件参数
    * @return List<JobDto>
    */
    List<JobDto> queryAll(JobQueryParam query);

    List<JobDto> queryAll();

    Job getById(Long id);

    /**
     * 插入一条新数据。
     */
    boolean save(Job resources);
    boolean updateById(Job resources);
    boolean removeById(Long id);
    boolean removeByIds(Set<Long> ids);

    void verification(Set<Long> ids);
    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<JobDto> all, HttpServletResponse response) throws IOException;
}

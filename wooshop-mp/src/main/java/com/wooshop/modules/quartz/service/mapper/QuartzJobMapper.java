package com.wooshop.modules.quartz.service.mapper;

import com.wooshop.base.CommonMapper;
import com.wooshop.modules.quartz.domain.QuartzJob;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author jinjin
* @date 2020-09-27
*/
@Repository
public interface QuartzJobMapper extends CommonMapper<QuartzJob> {

//    @Select("select * from sys_quartz_job")
    List<QuartzJob> findByIsPauseIsFalse();
}

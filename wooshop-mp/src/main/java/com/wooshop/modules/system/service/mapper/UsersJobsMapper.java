package com.wooshop.modules.system.service.mapper;

import com.wooshop.base.CommonMapper;
import com.wooshop.modules.system.domain.UsersJobs;
import com.wooshop.dto.JobSmallDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
* @author jinjin
* @date 2020-09-25
*/
@Repository
public interface UsersJobsMapper extends CommonMapper<UsersJobs> {


     @Select("SELECT j.job_id as id,j.`name` AS `name` FROM sys_users_jobs uj INNER JOIN sys_job j ON uj.job_id=j.job_id WHERE uj.user_id=#{userId}")
     Set<JobSmallDto> selectSetJobSma(@Param("userId") Long userId);
}

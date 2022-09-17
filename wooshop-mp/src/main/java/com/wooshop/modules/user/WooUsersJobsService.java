package com.wooshop.modules.user;

import com.wooshop.base.CommonService;
import com.wooshop.modules.system.domain.UsersJobs;
import com.wooshop.dto.JobSmallDto;

import java.util.Set;

/**
 * 岗位业务类
 */
public interface WooUsersJobsService extends CommonService<UsersJobs> {


     /**
      * 通过用户id查询关联岗位
      * @param userId
      * @return
      */
     Set<JobSmallDto> selectByUserId(Long userId );

     /**
      * 新增
      * @param usersJobs
      * @return
      */
    int insert(UsersJobs usersJobs );
}

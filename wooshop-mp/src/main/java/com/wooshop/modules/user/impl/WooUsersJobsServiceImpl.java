package com.wooshop.modules.user.impl;

import com.wooshop.base.impl.CommonServiceImpl;
import com.wooshop.modules.system.domain.UsersJobs;
import com.wooshop.dto.JobSmallDto;
import com.wooshop.modules.system.service.mapper.UsersJobsMapper;
import com.wooshop.modules.user.WooUsersJobsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@AllArgsConstructor
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class WooUsersJobsServiceImpl extends CommonServiceImpl<UsersJobsMapper, UsersJobs> implements WooUsersJobsService {



    @Override
    public Set<JobSmallDto> selectByUserId(Long userId) {
        return baseMapper.selectSetJobSma(userId);
    }


    @Override
    public int insert(UsersJobs usersJobs) {
        return baseMapper.insert(usersJobs);
    }
}

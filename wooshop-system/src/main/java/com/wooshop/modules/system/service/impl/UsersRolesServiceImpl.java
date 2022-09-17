package com.wooshop.modules.system.service.impl;

import lombok.AllArgsConstructor;
import com.wooshop.base.impl.CommonServiceImpl;
import com.wooshop.modules.system.domain.UsersRoles;
import com.wooshop.modules.system.service.UsersRolesService;
import com.wooshop.modules.system.service.mapper.UsersRolesMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jinjin on 2020-09-25.
 */
@AllArgsConstructor
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UsersRolesServiceImpl extends CommonServiceImpl<UsersRolesMapper, UsersRoles> implements UsersRolesService {

    private final UsersRolesMapper usersRolesMapper;

    @Override
    public List<Long> queryUserIdByRoleId(Long id) {
        return lambdaQuery().eq(UsersRoles::getRoleId, id).list().stream().map(UsersRoles::getUserId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> queryRoleIdByUserId(Long id) {
        return lambdaQuery().eq(UsersRoles::getUserId, id).list().stream().map(UsersRoles::getRoleId)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByRoleId(Long id) {
        return lambdaUpdate().eq(UsersRoles::getRoleId, id).remove();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByUserId(Long id) {
        return lambdaUpdate().eq(UsersRoles::getUserId, id).remove();
    }

}

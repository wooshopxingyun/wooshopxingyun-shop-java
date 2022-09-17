package com.wooshop.modules.user.impl;

import com.wooshop.modules.service.impl.BaseServiceImpl;
import com.wooshop.modules.system.domain.UsersRoles;
import com.wooshop.dto.RoleSmallDto;
import com.wooshop.modules.system.service.mapper.UsersRolesMapper;
import com.wooshop.modules.user.WooUsersRolesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;


@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class WooUsersRolesServiceImpl extends BaseServiceImpl<UsersRolesMapper, UsersRoles> implements WooUsersRolesService {


    @Override
    public Set<RoleSmallDto> selectByUserId(Long userId) {
        return baseMapper.selectSetRoleSma(userId);
    }


    @Override
    public int insert(UsersRoles usersRoles) {
        return baseMapper.insert(usersRoles);
    }
}

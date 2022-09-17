package com.wooshop.modules.user;

import com.wooshop.modules.service.BaseService;
import com.wooshop.modules.system.domain.UsersRoles;
import com.wooshop.dto.RoleSmallDto;

import java.util.Set;


/**
 * 用户角色业务类
 */
public interface WooUsersRolesService extends BaseService<UsersRoles> {


    /**
     * 通过用户id查询用户角色关联
     * @param userId
     * @return
     */
    Set<RoleSmallDto> selectByUserId(Long userId);

    /**
     * 新增
     * @param usersRoles
     * @return
     */
    int insert(UsersRoles usersRoles );
}

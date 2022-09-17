package com.wooshop.modules.system.service.mapper;

import com.wooshop.base.CommonMapper;
import com.wooshop.modules.system.domain.UsersRoles;
import com.wooshop.dto.RoleSmallDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
* @author jinjin
* @date 2020-09-25
*/
@Repository
public interface UsersRolesMapper extends CommonMapper<UsersRoles> {


     @Select("SELECT r.role_id as id,r.`name` AS `name`,r.`level` AS `level`,r.data_scope AS dataScope FROM sys_users_roles ur INNER JOIN sys_role r ON ur.role_id=r.role_id WHERE ur.user_id=#{userId}")
     Set<RoleSmallDto> selectSetRoleSma(@Param("userId") Long userId);
}

package com.wooshop.modules.system.service.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.wooshop.base.CommonMapper;
import com.wooshop.modules.system.domain.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
* @author jinjin
* @date 2020-09-25
*/
@Repository
public interface UserMapper extends CommonMapper<User> {

    /**
     * 查询一个用户信息
     * @param query
     * @return
     */
    @Select("select * from sys_user ${ew.customSqlSegment}")
    User selectUser(@Param(Constants.WRAPPER) Wrapper<User> query);

    /**
     * ${ew.customSqlSegment}” （自定义sql段），wrapper不能为null
     */
    @Results({
            @Result(column = "dept_id", property = "dept", one = @One(select = "com.wooshop.modules.system.service.mapper.DeptMapper.selectLink")),
            @Result(column = "job_id", property = "job", one = @One(select = "com.wooshop.modules.system.service.mapper.JobMapper.selectLink")), })
    @Select("select u.user_id as id, u.* from sys_user u ${ew.customSqlSegment}")
    User selectLink(@Param(Constants.WRAPPER) Wrapper<User> query);

    /**
     * 根据角色查询用户
     *
     * @param roleId /
     * @return /
     */
    @Select("SELECT u.user_id as id, u.* FROM sys_user u, sys_users_roles r WHERE" + " u.user_id = r.user_id AND r.role_id = #{roleId}")
    List<User> findByRoleId(@Param("roleId") Long roleId);

    /**
     * 根据角色中的部门查询
     *
     * @param roleId /
     * @return /
     */
    @Select("SELECT u.user_id as id, u.* FROM sys_user u, sys_users_roles r, sys_roles_depts d WHERE "
            + "u.user_id = r.user_id AND r.role_id = d.role_id AND r.role_id = #{roleId} group by u.user_id")
    List<User> findByDeptRoleId(@Param("roleId") Long roleId);

    /**
     * 根据菜单查询
     *
     * @param menuId 菜单ID
     * @return /
     */
    @Select("SELECT u.user_id as id, u.* FROM sys_user u, sys_users_roles ur, sys_roles_menus rm WHERE "
            + "u.user_id = ur.user_id AND ur.role_id = rm.role_id AND rm.menu_id = #{menuId} group by u.user_id")
    List<User> findByMenuId(@Param("menuId") Long menuId);

    /**
     * 根据岗位查询
     *
     * @param ids /
     * @return /
     */
    @Select("<script>SELECT count(1) FROM sys_user u, sys_users_jobs j WHERE u.user_id = j.user_id AND j.job_id IN "
            + "<foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'> #{item} </foreach>"
            + "</script>")
    int countByJobs(@Param("ids") Set<Long> ids);

    /**
     * 根据部门查询
     *
     * @param deptIds /
     * @return /
     */
    @Select("<script>SELECT count(1) FROM sys_user u WHERE u.dept_id IN "
            + "<foreach item='item' index='index' collection='deptIds' open='(' separator=',' close=')'> #{item} </foreach>"
            + "</script>")
    int countByDepts(@Param("deptIds") Set<Long> deptIds);

    /**
     * 根据角色查询
     *
     * @return /
     */
    @Select("<script>SELECT count(1) FROM sys_user u, sys_users_roles r WHERE "
            + "u.user_id = r.user_id AND r.role_id in "
            + "<foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'> #{item} </foreach>"
            + "</script>")
    int countByRoles(@Param("ids") Set<Long> ids);

    /**
     * 根据角色中的部门查询
     * @param deptId /
     * @return /
     */
    @Select("SELECT u.user_id as id, u.* FROM sys_user u, sys_users_roles r, sys_roles_depts d WHERE "
            + "u.user_id = r.user_id AND r.role_id = d.role_id AND u.dept_id = #{deptId} group by u.user_id")
    List<User> findByRoleDeptId(@Param("deptId") Long deptId);
}

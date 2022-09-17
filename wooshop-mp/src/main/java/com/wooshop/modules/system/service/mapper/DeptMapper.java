package com.wooshop.modules.system.service.mapper;

import com.wooshop.base.CommonMapper;
import com.wooshop.modules.system.domain.Dept;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
* @author jinjin
* @date 2020-09-25
*/
@Repository
public interface DeptMapper extends CommonMapper<Dept> {

    @Select("select d.dept_id as id, d.* from sys_dept d where dept_id = #{id}")
    Dept selectLink(Long id);

    @Select("SELECT d.dept_id as id, d.* FROM sys_dept d LEFT OUTER JOIN sys_roles_depts rd ON d.dept_id=rd.dept_id WHERE rd.role_id=#{roleId}")
    Set<Dept> selectByRoleId(Long roleId);

}

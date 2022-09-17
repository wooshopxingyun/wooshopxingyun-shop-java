package com.wooshop.modules.system.service;

import com.wooshop.base.CommonService;
import com.wooshop.modules.system.domain.Dept;
import com.wooshop.modules.system.service.dto.DeptDto;
import com.wooshop.modules.system.service.dto.DeptQueryParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
* @author jinjin
* @date 2020-09-25
*/
public interface DeptService  extends CommonService<Dept>{
    /**
    * 查询所有数据不分页
    * @param query 条件参数
    * @return List<DeptDto>
    */
    List<DeptDto> queryAll(DeptQueryParam query, Boolean isQuery);

    List<DeptDto> queryAll();

    List<Dept> findByPid(long pid);

    Set<DeptDto> findByRoleId(Long roleId);

    /**
     * 获取待删除的部门
     * @param deptList /
     * @param deptIds /
     * @return /
     */
    Set<Long> getDeleteDepts(List<Dept> deptList, Set<Long> deptIds);

    /**
     * 根据ID获取同级与上级数据
     * @param deptDto /
     * @param depts /
     * @return /
     */
    List<DeptDto> getSuperior(DeptDto deptDto, List<Dept> depts);

    /**
     * 构建树形数据
     * @param deptDtos /
     * @return /
     */
    Object buildTree(List<DeptDto> deptDtos);

    /**
     * 获取
     * @param deptId
     * @param deptList
     * @return
     */
    List<Long> getDeptChildren(Long deptId, List<Dept> deptList);

    /**
     * 验证是否被角色或用户关联
     * @param deptIds /
     */
    void verification(Set<Long> deptIds);

    Dept getById(Long id);
    DeptDto findById(Long id);

    /**
     * 插入一条新数据。
     */
    boolean save(Dept resources);
    boolean updateById(Dept resources);
    boolean removeById(Long id);
    boolean removeByIds(Set<Long> ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<DeptDto> all, HttpServletResponse response) throws IOException;
}

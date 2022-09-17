package com.wooshop.modules.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.wooshop.dto.RoleSmallDto;
import lombok.RequiredArgsConstructor;
import com.wooshop.base.PageInfo;
import com.wooshop.base.QueryHelpMybatisPlus;
import com.wooshop.base.impl.CommonServiceImpl;
import com.wooshop.exception.BadRequestException;
import com.wooshop.exception.EntityExistException;
import com.wooshop.modules.security.service.UserCacheClean;
import com.wooshop.modules.system.domain.*;
import com.wooshop.modules.system.service.*;
import com.wooshop.modules.system.service.dto.*;
import com.wooshop.modules.system.service.mapper.*;
import com.wooshop.utils.*;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;

/**
* @author jinjin
* @date 2020-09-25
*/
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "role")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RoleServiceImpl extends CommonServiceImpl<RoleMapper, Role> implements RoleService {

    private final RoleMapper roleMapper;
    private final MenuMapper menuMapper;
    private final DeptService deptService;
    private final UserMapper userMapper;
    private final RolesMenusService rolesMenusService;
    private final RolesDeptsService rolesDeptsService;
    private final RolesDeptsMapper rolesDeptsMapper;
    private final RolesMenusMapper rolesMenusMapper;

    private final RedisUtils redisUtils;

    private final UserCacheClean userCacheClean;

    @Override
    public PageInfo<RoleDto> queryAll(RoleQueryParam query, Pageable pageable) {
        IPage<Role> page = PageUtil.toMybatisPage(pageable);
        IPage<Role> pageList = roleMapper.selectPage(page, QueryHelpMybatisPlus.getPredicate(query));
        List<RoleDto> roleDtos = ConvertUtil.convertList(pageList.getRecords(), RoleDto.class);
        roleDtos.forEach(role -> {
            role.setMenus(ConvertUtil.convertSet(menuMapper.selectLink(role.getId()), MenuDto.class));
            role.setDepts(deptService.findByRoleId(role.getId()));
        });
        return new PageInfo<>(pageList.getTotal(), roleDtos);
    }

    @Override
    public List<RoleDto> queryAll(RoleQueryParam query){
        return ConvertUtil.convertList(roleMapper.selectList(QueryHelpMybatisPlus.getPredicate(query)), RoleDto.class);
    }

    @Override
    public List<RoleDto> queryAll() {
        List<RoleDto> list = ConvertUtil.convertList(lambdaQuery().orderByAsc(Role::getLevel).list(), RoleDto.class);
        list.forEach(role -> {
            role.setMenus(ConvertUtil.convertSet(menuMapper.selectLink(role.getId()), MenuDto.class));
            role.setDepts(deptService.findByRoleId(role.getId()));
        });
        return ConvertUtil.convertList(list, RoleDto.class);
    }

    @Override
    public Role getById(Long id) {
        return roleMapper.selectById(id);
    }

    @Override
    @Cacheable(key = "'id:' + #p0")
    public RoleDto findById(Long id) {
        RoleDto role = ConvertUtil.convert(getById(id), RoleDto.class);
        role.setMenus(ConvertUtil.convertSet(menuMapper.selectLink(role.getId()), MenuDto.class));
        role.setDepts(deptService.findByRoleId(role.getId()));
        return role;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(RoleDto resources) {
        if (lambdaQuery().eq(Role::getName, resources.getName()).one() != null) {
            throw new EntityExistException(Role.class, "name", resources.getName());
        }
        Role newRole = ConvertUtil.convert(resources, Role.class);
        int ret = roleMapper.insert(newRole);

        if (resources.getDepts() != null) {
            resources.getDepts().forEach(dept -> {
                RolesDepts rd = new RolesDepts();
                rd.setRoleId(newRole.getId());
                rd.setDeptId(dept.getId());
                rolesDeptsMapper.insert(rd);
            });
        }
        return ret > 0;
    }

    @Override
    @CacheEvict(key = "'id:' + #p0.id")
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(RoleDto resources){
        Role roleOld = getById(resources.getId());
        Role role1 = lambdaQuery().eq(Role::getName, resources.getName()).one();
        if (role1 != null && !role1.getId().equals(resources.getId())) {
            throw new EntityExistException(Role.class, "name", resources.getName());
        }
        roleOld.setName(resources.getName());
        roleOld.setDescription(resources.getDescription());
        roleOld.setDataScope(resources.getDataScope());
        roleOld.setLevel(resources.getLevel());

        int ret = roleMapper.updateById(roleOld);

        rolesDeptsService.removeByRoleId(resources.getId());
        if (resources.getDepts() != null) {
            resources.getDepts().forEach(dept -> {
                RolesDepts rd = new RolesDepts();
                rd.setRoleId(resources.getId());
                rd.setDeptId(dept.getId());
                rolesDeptsMapper.insert(rd);
            });
        }
        return ret > 0;
    }

    @Override
    @CacheEvict(key = "'auth:' + #p0.id")
    public void updateMenu(RoleDto resources) {
        // 清理缓存
        List<User> users = userMapper.findByRoleId(resources.getId());
        Set<Long> userIds = users.stream().map(User::getId).collect(Collectors.toSet());
        redisUtils.delByKeys("menu::user:", userIds);
        redisUtils.del("role::id:" + resources.getId());
        //this.saveOrUpdate(resources);

        RolesMenus rm = new RolesMenus();
        List<Long> oldMenuIds = rolesMenusService.queryMenuIdByRoleId(resources.getId());
        List<Long> menuIds = resources.getMenus().stream().map(MenuDto::getId).collect(Collectors.toList());
        List<Long> deleteList = oldMenuIds.stream().filter(item -> !menuIds.contains(item))
                .collect(Collectors.toList());
        List<Long> addList = menuIds.stream().filter(item -> !oldMenuIds.contains(item)).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(deleteList)) {
            rolesMenusService.lambdaUpdate()
                    .eq(RolesMenus::getRoleId, resources.getId())
                    .in(RolesMenus::getMenuId, deleteList).remove();
        }

        addList.forEach(item -> {
            rm.setMenuId(item);
            rm.setRoleId(resources.getId());
            rolesMenusMapper.insert(rm);
        });
        userCacheClean.cleanAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByIds(Set<Long> ids){
        int ret = roleMapper.deleteBatchIds(ids);
        for (Long id : ids) {
            // 更新相关缓存
            delCaches(id);
            rolesMenusService.removeByRoleId(id);
            rolesDeptsService.removeByRoleId(id);
        }
        return ret > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(Long id){
        Set<Long> ids = new HashSet<>(1);
        ids.add(id);
        return this.removeByIds(ids);
    }

    @Override
    public List<RoleSmallDto> findByUsersId(Long id) {
        List<Role> roles = new ArrayList<Role>();
        roles.addAll(roleMapper.selectLink(id));
        return ConvertUtil.convertList(roles, RoleSmallDto.class);
    }

    @Override
    public Integer findByRoles(Set<Long> roleIds) {
        if (roleIds.size() == 0) {
            return Integer.MAX_VALUE;
        }
        Set<RoleDto> roleDtos = new HashSet<>();
        for (Long id : roleIds) {
            roleDtos.add(findById(id));
        }
        return Collections.min(roleDtos.stream().map(RoleDto::getLevel).collect(Collectors.toList()));
    }

    @Override
    @Cacheable(key = "'auth:' + #p0.id")
    public List<GrantedAuthority> mapToGrantedAuthorities(UserDto user) {
        Set<String> permissions = new HashSet<>();
        // 如果是管理员直接返回
        if (user.getIsAdmin()) {
            permissions.add("admin");
            return permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        }
        Set<Role> roles = roleMapper.selectLink(user.getId());
        Set<Long> roleIds = roles.stream().map(Role::getId).collect(Collectors.toSet());
        Map<Long, List<RolesMenus>> roleMenuIds = rolesMenusService.lambdaQuery().in(RolesMenus::getRoleId, roleIds).list().stream()
                .collect(Collectors.groupingBy(RolesMenus::getRoleId));
        Set<Long> menuIds = roleMenuIds.values().stream().flatMap(item -> item.stream().map(RolesMenus::getMenuId))
                .collect(Collectors.toSet());
        List<Menu> menus = menuMapper.selectBatchIds(menuIds);
        permissions = menus.stream().filter(menu -> StringUtils.isNotBlank(menu.getPermission()))
                .map(Menu::getPermission).collect(Collectors.toSet());
        // permissions.addAll(roles.stream().flatMap(role ->
        // menuMapper.selectLink(role.getId()).stream())
        // .filter(menu ->
        // StringUtils.isNotBlank(menu.getPermission())).map(Menu::getPermission)
        // .collect(Collectors.toSet()));
        if (CollectionUtils.isNotEmpty(roleIds)) {
            permissions.addAll(menuMapper.selectByRoleIds(roleIds).stream()
                    .filter(menu -> StringUtils.isNotBlank(menu.getPermission())).map(Menu::getPermission)
                    .collect(Collectors.toSet()));
        }
        return permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    /**
     * 清理缓存
     *
     * @param id /
     */
    private void delCaches(Long id) {
        List<User> users = userMapper.findByRoleId(id);
        Set<Long> userIds = users.stream().map(User::getId).collect(Collectors.toSet());
        redisUtils.delByKeys("data::user:", userIds);
        redisUtils.delByKeys("menu::user:", userIds);
        redisUtils.delByKeys("role::auth:", userIds);
        redisUtils.del(CacheKey.ROLE_ID + id);
    }

    @Override
    public void verification(Set<Long> ids) {
        if (userMapper.countByRoles(ids) > 0) {
            throw new BadRequestException("所选角色存在用户关联，请解除关联再试！");
        }
    }

    @Override
    public List<Role> findInMenuId(List<Long> menuIds) {
        return roleMapper.findInMenuId(menuIds);
    }

    @Override
    public void download(List<RoleDto> all, HttpServletResponse response) throws IOException {
      List<Map<String, Object>> list = new ArrayList<>();
      for (RoleDto role : all) {
        Map<String,Object> map = new LinkedHashMap<>();
              map.put("名称", role.getName());
              map.put("角色级别", role.getLevel());
              map.put("描述", role.getDescription());
              map.put("数据权限", role.getDataScope());
              map.put("创建者", role.getCreateBy());
              map.put("更新者", role.getUpdateBy());
              map.put("创建日期", role.getCreateTime());
              map.put("更新时间", role.getUpdateTime());
        list.add(map);
      }
      FileUtil.downloadExcel(list, response);
    }
}

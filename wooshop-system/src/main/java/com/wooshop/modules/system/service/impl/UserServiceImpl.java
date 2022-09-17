package com.wooshop.modules.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.wooshop.common.QueryHelpPlus;
import com.wooshop.domain.PageResult;
import com.wooshop.dozer.service.IGenerator;
import com.wooshop.dto.DeptSmallDto;
import com.wooshop.dto.JobSmallDto;
import com.wooshop.dto.RoleSmallDto;
import com.wooshop.modules.security.service.OnlineUserService;
import com.wooshop.modules.security.service.UserCacheClean;
import com.wooshop.modules.service.impl.BaseServiceImpl;
import com.wooshop.modules.sys_config.service.WooSysConfigService;
import com.wooshop.modules.utils.RedisUache;
import lombok.AllArgsConstructor;
import com.wooshop.base.QueryHelpMybatisPlus;
import com.wooshop.config.FileProperties;
import com.wooshop.exception.BadRequestException;
import com.wooshop.exception.EntityExistException;
import com.wooshop.modules.system.domain.User;
import com.wooshop.modules.system.domain.UsersJobs;
import com.wooshop.modules.system.domain.UsersRoles;
import com.wooshop.modules.system.service.*;
import com.wooshop.modules.system.service.dto.*;
import com.wooshop.modules.system.service.mapper.UserMapper;
import com.wooshop.modules.system.service.mapper.UsersJobsMapper;
import com.wooshop.modules.system.service.mapper.UsersRolesMapper;
import com.wooshop.utils.*;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
* @author jinjin
* @date 2020-09-25
*/
@Service
@AllArgsConstructor
@CacheConfig(cacheNames = "user")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {

    private final FileProperties properties;
    private final RedisUtils redisUtils;
    private final UserCacheClean userCacheClean;
    private final OnlineUserService onlineUserService;
    private final IGenerator generator;
    private final UserMapper userMapper;
    private final DeptService deptService;
    private final UsersRolesService usersRolesService;
    private final UsersJobsService usersJobsService;
    private final UsersRolesMapper usersRolesMapper;
    private final UsersJobsMapper usersJobsMapper;
    private final WooSysConfigService sysConfigService;
    private final RedisUache redisUache;

    /**
     * 查询单个用户信息
     * @param userName
     * @return
     */
    @Override
    public User getByUserName(String userName) {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",userName);
        return baseMapper.selectUser(queryWrapper);
    }

    @Override
    //@Cacheable
    public PageResult<UserDto> queryAll(UserQueryParam query, Pageable pageable) {
        setPageAndOrder(pageable.getPageNumber(), pageable.getPageSize(),pageable.getSort().toString());
        com.github.pagehelper.PageInfo<User> pageData = new com.github.pagehelper.PageInfo<>(baseMapper.selectList(QueryHelpPlus.getPredicate(User.class, query)));
        PageResult<UserDto> userDtos = generator.convertPageInfo(pageData, UserDto.class);
        if (userDtos.getContent().size() > 0) {
            Map<Long, DeptDto> deptMap = deptService.queryAll().parallelStream()
                    .collect(Collectors.toMap(DeptDto::getId, Function.identity(), (x,y) -> x));

            Map<Long, Set<UsersRoles>> usersRolesMap = usersRolesService.lambdaQuery()
                    .in(UsersRoles::getUserId, userDtos.getContent().stream().map(UserDto::getId).collect(Collectors.toSet()))
                    .list()
                    .stream()
                    .collect(Collectors.groupingBy(UsersRoles::getUserId, Collectors.toSet()));

            Map<Long, List<UsersJobs>> usersJobsMap = usersJobsService.lambdaQuery()
                    .in(UsersJobs::getUserId, userDtos.getContent().stream().map(UserDto::getId).collect(Collectors.toList()))
                    .list()
                    .stream()
                    .collect(Collectors.groupingBy(UsersJobs::getUserId));

            userDtos.getContent().forEach(user -> {
                user.setDept(ConvertUtil.convert(deptMap.get(user.getDeptId()), DeptSmallDto.class));
                if (usersRolesMap.containsKey(user.getId())) {
                    user.setRoles(usersRolesMap.get(user.getId()).stream().map(ur -> {
                        RoleSmallDto role = new RoleSmallDto();
                        role.setId(ur.getRoleId());
                        return role;
                    }).collect(Collectors.toSet()));
                }
                if (usersJobsMap.containsKey(user.getId())) {
                    user.setJobs(usersJobsMap.get(user.getId()).stream().map(uj -> {
                        JobSmallDto job = new JobSmallDto();
                        job.setId(uj.getJobId());
                        return job;
                    }).collect(Collectors.toSet()));
                }
            });
        }
        System.out.println(pageData.getTotal());
        return userDtos;
    }

    @Override
    public List<UserDto> queryAll(UserQueryParam query){
        return ConvertUtil.convertList(userMapper.selectList(QueryHelpMybatisPlus.getPredicate(query)), UserDto.class);
    }

    @Override
    public User getById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public UserDto findById(Long id) {
        return ConvertUtil.convert(getById(id), UserDto.class);
    }

    @Override
    public User getByUsername(String userName) {
        User user = lambdaQuery().eq(User::getUsername, userName).one();

        return user;
    }

    @Override
    public UserDto findByName(String userName) {
        UserDto dto = ConvertUtil.convert(getByUsername(userName), UserDto.class);
        if (dto == null) {
            return dto;
        }
        dto.setDept(new DeptSmallDto(dto.getDeptId(), deptService.findById(dto.getDeptId()).getName()));
        return dto;
    }

    private User getByEmail(String email) {
        return lambdaQuery().eq(User::getEmail, email).one();
    }


    @Override
    public User getByPhone(String phone) {
        return lambdaQuery().eq(User::getPhone, phone).one();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(UserDto resources) {
        User user = getByUsername(resources.getUsername());
        if (user != null) {//判断用户名称是否相同
            throw new EntityExistException(User.class, "username", user.getUsername());
        }
        user = getByEmail(resources.getEmail());
        if (user != null) {//判断用户邮箱是否相同
            throw new EntityExistException(User.class, "email", resources.getEmail());
        }
        user = getByPhone(resources.getPhone());
        if (user != null) {//判断手机号码是否相同
            throw new EntityExistException(User.class, "phone", resources.getPhone());
        }

        user = ConvertUtil.convert(resources, User.class);
        if (resources.getDept() != null) {//判断用户关联部门不是空 id=18  enabled=true
            user.setDeptId(resources.getDept().getId());
        }
        int ret = userMapper.insert(user);//插入
        final Long userId = user.getId();
        if (CollectionUtils.isNotEmpty(resources.getRoles())) {//判断对象是否非空  id=3
            resources.getRoles().forEach(role -> {
                UsersRoles ur = new UsersRoles();
                ur.setUserId(userId);
                ur.setRoleId(role.getId());
                usersRolesMapper.insert(ur);
            });
        }
        if (CollectionUtils.isNotEmpty(resources.getJobs())) {//判断用户关联工作岗位 id=13
            resources.getJobs().forEach(job -> {
                UsersJobs uj = new UsersJobs();
                uj.setUserId(userId);
                uj.setJobId(job.getId());
                usersJobsMapper.insert(uj);
            });
        }
        return ret > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(UserDto resources) throws Exception {
        User user = getById(resources.getId());
        User user1 = getByUsername(user.getUsername());
        User user2 = getByEmail(user.getEmail());
        User user3 = getByPhone(user.getPhone());
        if (user1 != null && !user.getId().equals(user1.getId())) {
            throw new EntityExistException(User.class, "username", user.getUsername());
        }
        if (user2 != null && !user.getId().equals(user2.getId())) {
            throw new EntityExistException(User.class, "email", user.getEmail());
        }
        if (user3 != null && !user.getId().equals(user3.getId())) {
            throw new EntityExistException(User.class, "phone", user.getPhone());
        }

            redisUtils.del(CacheKey.DATA_USER + resources.getId());
            redisUtils.del(CacheKey.MENU_USER + resources.getId());
            redisUtils.del(CacheKey.ROLE_AUTH + resources.getId());

        // 如果用户名称修改
        if(!resources.getUsername().equals(user.getUsername())){
            throw new BadRequestException("不能修改用户名");
        }
        // 如果用户被禁用，则清除用户登录信息
        if(!resources.getEnabled()){
            onlineUserService.kickOutForUsername(resources.getUsername());
        }
        if (CollectionUtils.isNotEmpty(resources.getRoles())) {
            usersRolesService.removeByUserId(resources.getId());
            resources.getRoles().stream().forEach(role -> {
                UsersRoles ur = new UsersRoles();
                ur.setUserId(resources.getId());
                ur.setRoleId(role.getId());
                usersRolesMapper.insert(ur);
            });
        }

        if (CollectionUtils.isNotEmpty(resources.getJobs())) {
            usersJobsService.removeByUserId(resources.getId());
            resources.getJobs().stream().forEach(job -> {
                UsersJobs uj = new UsersJobs();
                uj.setUserId(resources.getId());
                uj.setJobId(job.getId());
                usersJobsMapper.insert(uj);
            });
        }

        user.setUsername(resources.getUsername());
        user.setEmail(resources.getEmail());
        user.setEnabled(resources.getEnabled());
        if (resources.getDept() != null) {
            user.setDeptId(resources.getDept().getId());
        } else {
            user.setDeptId(null);
        }
        user.setPhone(resources.getPhone());
        user.setNickName(resources.getNickName());
        user.setSex(resources.getSex());

        delCaches(user.getId(), user.getUsername());
        return userMapper.updateById(user) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePass(String username, String encryptPassword) {
        User user = new User();
        user.setPassword(encryptPassword);
        user.setPwdResetTime(new Date());
        lambdaUpdate().eq(User::getUsername, username).update(user);
        flushCache(username);
    }

    @Override
    public Map<String, String> updateAvatar(MultipartFile multipartFile) {

        String adminUrl = redisUache.getBasicsshop_adminUrl();


        // 文件大小验证
        FileUtil.checkSize(properties.getAvatarMaxSize(), multipartFile.getSize());
        // 验证文件上传的格式
        String image = "gif jpg png jpeg";
        String fileType = FileUtil.getExtensionName(multipartFile.getOriginalFilename());
        String type = FileUtil.getFileType(fileType);
        if(fileType != null && !image.contains(fileType)){
            throw new BadRequestException("文件格式错误！, 仅支持 " + image +" 格式");
        }
        User user = getByUsername(SecurityUtils.getCurrentUsername());
        String oldPath = user.getAvatarPath();
        File file = FileUtil.upload(multipartFile, properties.getPath().getAvatar());
        user.setAvatarName(adminUrl + "/file/" + type + "/" + file.getName());
        user.setAvatarPath(adminUrl + "/file/" + type + "/" + file.getName());
        userMapper.updateById(user);
        if (StrUtil.isNotBlank(oldPath)) {
            FileUtil.del(oldPath);
        }
        return new HashMap<String, String>() {
            {
//                put("avatar", file.getName());
                put("avatar", adminUrl + "/file/" + type + "/" + file.getName());
            }
        };
    }

    @Override
    public void updateEmail(String username, String email) {
        User user = getByUsername(username);
        User user2 = getByEmail(email);
        if (ObjectUtil.notEqual(user.getId(), user2.getId())) {
            throw new EntityExistException(User.class, "email", email);
        }
        User userUpdate = new User();
        userUpdate.setEmail(email);
        lambdaUpdate().eq(User::getUsername, username).update(userUpdate);
    }

    @Override
    public void updateCenter(User resources) {
        User user2 = getByPhone(resources.getPhone());
        if (ObjectUtil.notEqual(resources.getId(), user2.getId())) {
            throw new EntityExistException(User.class, "phone", resources.getPhone());
        }
        User userUpdate = new User();
        userUpdate.setPhone(resources.getPhone());
        userUpdate.setSex(resources.getSex());
        userUpdate.setNickName(resources.getNickName());
        lambdaUpdate().eq(User::getId, resources.getId()).update(userUpdate);
        redisUtils.del("user::username:" + resources.getUsername());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByIds(Set<Long> ids){
        for (Long id: ids) {
            User user = getById(id);
            delCaches(user.getId(), user.getUsername());
            usersRolesService.removeByUserId(id);
            usersJobsService.removeByUserId(id);
        }
        return userMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    @Transactional
    public boolean removeById(Long id){
        Set<Long> ids = new HashSet<>(1);
        ids.add(id);
        return this.removeByIds(ids);
    }

    @Override
    public void download(List<UserDto> all, HttpServletResponse response) throws IOException {
      List<Map<String, Object>> list = new ArrayList<>();
      for (UserDto user : all) {
        Map<String,Object> map = new LinkedHashMap<>();
              map.put("部门名称", user.getDeptId());
              map.put("用户名", user.getUsername());
              map.put("昵称", user.getNickName());
              map.put("性别", user.getSex());
              map.put("手机号码", user.getPhone());
              map.put("邮箱", user.getEmail());
              map.put("头像地址", user.getAvatarName());
              map.put("头像真实路径", user.getAvatarPath());
              map.put("密码", user.getPassword());
              map.put("是否为admin账号", user.getIsAdmin());
              map.put("状态：1启用、0禁用", user.getEnabled());
              map.put("创建者", user.getCreateBy());
              map.put("更新着", user.getUpdateBy());
              map.put("修改密码的时间", user.getPwdResetTime());
              map.put("创建日期", user.getCreateTime());
              map.put("更新时间", user.getUpdateTime());
        list.add(map);
      }
      FileUtil.downloadExcel(list, response);
    }


    /**
     * 清理缓存
     *
     * @param id /
     */
    public void delCaches(Long id, String username) {
        redisUtils.del(CacheKey.USER_ID + id);
        flushCache(username);
    }

    /**
     * 清理 登陆时 用户缓存信息
     *
     * @param username /
     */
    private void flushCache(String username) {
        userCacheClean.cleanUserCache(username);
    }

    /**
     * 获取用户好友
     * @param userId 用户id
     * @return
     */
    @Override
    public List<User> getUserFriends(Long userId) {
        List<User> userList = new ArrayList<>();
        User currUser = baseMapper.selectById(userId);
        if (currUser.getSpreadUid() > 0) {
            User spUser1 = baseMapper.selectById(currUser.getSpreadUid());
            if (null != spUser1) {
                userList.add(spUser1);
                if (spUser1.getSpreadUid() > 0) {
                    User spUser2 = baseMapper.selectById(spUser1.getSpreadUid());
                    if (null != spUser2) {
                        userList.add(spUser2);
                    }
                }
            }
        }
        return userList;
    }
}

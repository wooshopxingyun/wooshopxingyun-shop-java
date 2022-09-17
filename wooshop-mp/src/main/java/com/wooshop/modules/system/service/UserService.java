package com.wooshop.modules.system.service;

import com.wooshop.base.CommonService;
import com.wooshop.base.PageInfo;
import com.wooshop.domain.PageResult;
import com.wooshop.modules.system.domain.User;
import com.wooshop.modules.system.service.dto.UserDto;
import com.wooshop.modules.system.service.dto.UserQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
* @author jinjin
* @date 2020-09-25
*/
public interface UserService  extends CommonService<User>{


    /**
     * 通过用户名查询用户信息
     * @param userName  用户名称
     * @return
     */
    User getByUserName(String userName);

    /**
    * 查询数据分页
    * @param query 条件
    * @param pageable 分页参数
    * @return PageInfo<UserDto>
    */
    PageResult<UserDto> queryAll(UserQueryParam query, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param query 条件参数
    * @return List<UserDto>
    */
    List<UserDto> queryAll(UserQueryParam query);


    /**
     * 通过用户id过去用户信息
     * @param id
     * @return
     */
    User getById(Long id);

    /**
     * 查询用户信息
     * @param uid 用户id
     * @return
     */
    UserDto findById(Long uid);

    /**
     * 根据用户名查询
     * @param userName /
     * @return /
     */
    User getByUsername(String userName);
    UserDto findByName(String userName);
    /**
     * 插入一条新数据。
     */
    boolean save(UserDto resources);
    boolean updateById(UserDto resources) throws Exception;
    boolean removeById(Long id);
    boolean removeByIds(Set<Long> ids);

    /**
     * 修改密码
     * @param username 用户名
     * @param encryptPassword 密码
     */
    void updatePass(String username, String encryptPassword);

    /**
     * 修改头像
     * @param file 文件
     * @return /
     */
    Map<String, String> updateAvatar(MultipartFile file);

    /**
     * 修改邮箱
     * @param username 用户名
     * @param email 邮箱
     */
    void updateEmail(String username, String email);

    /**
     * 用户自助修改资料
     * @param resources /
     */
    void updateCenter(User resources);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<UserDto> all, HttpServletResponse response) throws IOException;

    /**
     * 获取用户好友
     * @param userId 用户id
     * @return
     */
    List<User> getUserFriends(Long userId);

    /**
     * 通过手机号码获取用户信息
     * @param phone 用户手机号码
     * @return
     */
    User getByPhone(String phone);
}

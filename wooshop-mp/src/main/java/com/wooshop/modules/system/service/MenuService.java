package com.wooshop.modules.system.service;

import com.wooshop.base.CommonService;
import com.wooshop.modules.system.domain.Menu;
import com.wooshop.modules.system.service.dto.MenuDto;
import com.wooshop.modules.system.service.dto.MenuQueryParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
* @author jinjin
* @date 2020-09-25
*/
public interface MenuService  extends CommonService<Menu>{

    /**
    * 查询数据分页
    * @param query 条件
    * @return PageInfo<MenuDto>
    */
    List<MenuDto> queryAll(MenuQueryParam query, boolean isQuery);

    /**
    * 查询所有数据不分页
    * @param query 条件参数
    * @return List<MenuDto>
    */
    List<MenuDto> queryAll(MenuQueryParam query);

    Menu getById(Long id);
    MenuDto findById(Long id);

    /**
     * 插入一条新数据。
     */
    boolean save(Menu resources);
    boolean updateById(Menu resources);
    boolean removeById(Long id);
    boolean removeByIds(Set<Long> ids);

    /**
     * 获取待删除的菜单
     * @param menuList /
     * @param menuSet /
     * @return /
     */
    Set<Menu> getDeleteMenus(List<Menu> menuList, Set<Menu> menuSet);

    /**
     * 获取所有子节点，包含自身ID
     * @param menuList /
     * @param menuSet /
     * @return /
     */
    Set<Menu> getChildMenus(List<Menu> menuList, Set<Menu> menuSet);


    /**
     * 构建菜单树
     * @param menuDtos 原始数据
     * @return /
     */
    List<MenuDto> buildTree(List<MenuDto> menuDtos);

    /**
     * 构建菜单树
     * @param menuDtos /
     * @return /
     */
    Object buildMenus(List<MenuDto> menuDtos);

    /**
     * 懒加载菜单数据
     * @param pid /
     * @return /
     */
    List<MenuDto> getMenus(Long pid);

    /**
     * 根据ID获取同级与上级数据
     * @param menuDto /
     * @param objects /
     * @return /
     */
    List<MenuDto> getSuperior(MenuDto menuDto, List<Menu> objects);

    /**
     * 根据当前用户获取菜单
     * @param currentUserId /
     * @return /
     */
    List<MenuDto> findByUser(Long currentUserId);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<MenuDto> all, HttpServletResponse response) throws IOException;
}
